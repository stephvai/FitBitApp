package ca.uwo.csd.cs2212.team08;

import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.apis.service.FitbitOAuth20ServiceImpl;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import com.github.scribejava.core.model.*; //Request Verb

//TODO Make sure it returns false whenever the application crashes
//TODO Remove all writing to console and instead save it to log.txt

/**
 * APIData Class that gets data from the fitbit servers and parses it to variables that can be 
 * gotten from the main class
 */
public class APIData {

  //Instance variables for daily user data to be used on the daily dashboard 
  private int userDailySteps;
  private double userDailyDistance;
  private int userDailyCalories;
  private int userDailyFloorsClimbed;
  private int userDailySendentaryMinutes;
  private int userDailyLightlyActiveMinutes;
  private int userDailyFairlyActiveMinutes;
  private int userDailyVeryActiveMinutes;
  //Instance variables for total values
  private double totalDistance;
  private int totalFloors;
  private int totalSteps;
  //Instance variables for best values
  private double bestDistance;
  private int bestFloors;
  private int bestSteps;
  
  //Instance variables used when getting data from APIData
  private static String CALL_BACK_URI="http://localhost:8080";
  private static int CALL_BACK_PORT=8080;
  
  //values used for checking if responses are valid
  final int successfulResponse = 1;
  final int badRequest = -1;
  final int expiredToken = -2;
  final int rateLimitExceded = -3;
  final int otherResponse = -4;
  
  //String representation of the date in YYYY-MM-DD format
  String currentDate = "2016-02-26";
  String requestUrlPrefix = "https://api.fitbit.com/1/user/3WGW2P/";
  
  /**
   * Constructor for API data
   */
  public APIData() {
  }
  
  /********************************************************
   *        Main Refresh Method for daily dashboard	      *
   ********************************************************/
  
  /**
   * Method that refreshes all data for the daily dashboard from the API and saves it to instance variables
   * @param date the date value in "YYYY-MM-DD" format to fetch data for
   * @return true if refreshing the data was successful, false otherwise.
   */
  public Boolean refreshDailyDashBoardData(String date) {
	currentDate = date;
    //read credentials from a file
    BufferedReader bufferedReader=null;
    // This will reference one line at a time
    String line = null;
    //Need to save service credentials for Fitbit
    String apiKey = null;
    String apiSecret = null;
    String clientID = null;
    //holder for all the elements we will need to make an access token ( information about an authenticated session )
    String accessTokenItself =  null;
    String tokenType = null;
    String refreshToken = null;
    Long expiresIn = null;
    String rawResponse = null;
    //This is the only scope you have access to currently
        String scope = "activity%20heartrate";
        try {
            // File with service credentials.
            FileReader fileReader =
                    new FileReader("src/main/resources/Team8Credentials.txt");
            bufferedReader = new BufferedReader(fileReader);
            clientID= bufferedReader.readLine();
            apiKey= bufferedReader.readLine();
            apiSecret = bufferedReader.readLine();
            bufferedReader.close();
            fileReader = new FileReader("src/main/resources/Team8Tokens.txt");
            bufferedReader = new BufferedReader(fileReader);

            accessTokenItself = bufferedReader.readLine();
            tokenType = bufferedReader.readLine();
            refreshToken = bufferedReader.readLine();
            expiresIn = Long.parseLong(bufferedReader.readLine());
            rawResponse = bufferedReader.readLine();
        }
        catch(FileNotFoundException ex) {
            return false;
        }
        catch(IOException ex) {
            return false;
        }
        finally{
            try{
                if (bufferedReader!=null)
                    // Always close files.
                    bufferedReader.close();
            }
            catch(Exception e){
                System.out.println(
                        "Error closing file\n"+e.getMessage());
                return false;
            }
        }
        //  Create the Fitbit service - you will ask this to ask for access/refresh pairs
        //     and to add authorization information to the requests to the API
        FitbitOAuth20ServiceImpl service = (FitbitOAuth20ServiceImpl) new ServiceBuilder()
                .apiKey(clientID)       //fitbit uses the clientID here
                .apiSecret(apiSecret)
                .callback("http://localhost:8080")
                .scope(scope)
                .grantType("authorization_code")
                .build(FitbitApi20.instance());
        //  The access token contains everything you will need to authenticate your requests
        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                accessTokenItself,
                tokenType,
                refreshToken,
                expiresIn,
                rawResponse);
        
        //GETTING AND PARSING ACTIVITY SUMMARY 
        
        String requestUrl = activitySummaryRequestBuilder(requestUrlPrefix);
        OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        int checkResponse = checkStatus(response.getCode());
        if (checkResponse == expiredToken) {
        	//refresh token then send it again
        	System.out.println("refreshing");
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
            System.out.println("HTTP response code after refresh: "+response.getCode());
        }
        saveTokens(accessToken);
        
        if (checkResponse == successfulResponse) {
        	System.out.println("Succesful Response - Summary");
        	JSONObject obj = new JSONObject(response.getBody());
        	parseSummary(obj);
        }
        
        else {
        	System.out.println("Error getting fitbit summary data");
        	return false;
        }
        
        //GETTING AND PARSING BEST DAYS/LIFETIME TOTALS
        
        requestUrl = bestDayLifeTimeTotalRequestBuilder(requestUrlPrefix);
        request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        response = request.send();
        checkResponse = checkStatus(response.getCode());
        
        if (checkResponse == expiredToken) {
        	//refresh token then send it again
        	System.out.println("refreshing");
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
            System.out.println("HTTP response code after refresh: "+response.getCode());
        }
        saveTokens(accessToken);
        
        if (checkResponse == successfulResponse) {
        	System.out.println("Succesfful Response - Total/BestDay");
        	JSONObject obj = new JSONObject(response.getBody());
        	parseLifeTimeTotal(obj);
        	parseBestDays(obj);
        }
        
        else {
        	System.out.println("Error getting fitbit best/total data");
        	return false;
        }
        //Return true if everything went well and the program got all data for the daily dashboard
        return true;
  }
  
  /********************************************************
   * 	 		  API Request helper methods			  *
   ********************************************************/
  
  /**
   * A method that given a request URL prefix it generates the activity summary API URL
   * @param requestURLPrefix prefix for the users API request URLS
   * @return returns the complete request URL for the activity summary API call
   */
  private String activitySummaryRequestBuilder(String requestURLPrefix) {
	  return requestURLPrefix + "activities/date/" + currentDate + ".json";
  }
  
  /**
   * A method that given a request URL prefix it generates the bestdays/lifetimetotal API URL
   * @param requestUrlPrefix prefix for the users API request URLS
   * @return returns the complete request URL for the best days and life time total API call
   */
  private String bestDayLifeTimeTotalRequestBuilder(String requestUrlPrefix) {
	  return requestUrlPrefix + "activities.json";
  }
  
  /**
   * Method that writes the current token to the token storage file
   * @param accessToken the currently active token to write out
   */
  private void saveTokens(OAuth2AccessToken accessToken) {
	  BufferedWriter bufferedWriter=null;
      //  Save the current accessToken information for next time
      try {
    	  System.out.println("Saving new tokens");
          FileWriter fileWriter;
          fileWriter =
                  new FileWriter("src/main/resources/Team8Tokens.txt");
          bufferedWriter = new BufferedWriter(fileWriter);
          bufferedWriter.write(accessToken.getToken());
          bufferedWriter.newLine();
          bufferedWriter.write(accessToken.getTokenType());
          bufferedWriter.newLine();
          bufferedWriter.write(accessToken.getRefreshToken());
          bufferedWriter.newLine();
          bufferedWriter.write(accessToken.getExpiresIn().toString() );
          bufferedWriter.newLine();
          bufferedWriter.write(accessToken.getRawResponse());
          bufferedWriter.newLine();
          bufferedWriter.close();
      }
      catch(FileNotFoundException ex) {
          System.out.println(
                  "Unable to open file\n"+ex.getMessage());
      }
      catch(IOException ex) {
          System.out.println(
                  "Error reading/write file\n"+ex.getMessage());
      }
      finally{
          try{
              if (bufferedWriter!=null)
                  bufferedWriter.close();
          }
          catch(Exception e){
              System.out.println(
                      "Error closing file\n"+e.getMessage());
          }
      }//end try
  }
  
  /**
   * Method that takes a HTTP status code and returns a code for what kind of response occured
   * @param statusCode the HTTP status code provided in the response
   * @return a value that represents what kind of response has occured 
   */
  private int checkStatus(int statusCode) {
	  switch(statusCode){
      case 200:
          return successfulResponse;
      case 400:
          //Bad Request - may have to talk to Beth
          return badRequest;
      case 401:
          //Likely Expired Token
    	  return expiredToken;
      case 429:
          //Rate limit exceeded
          return rateLimitExceded;
      default:
    	  //Some other code was returned
          return otherResponse;
	  }
  }
  
  /**
   * A method that parses the fitbits activties summary and gets all the activity data ans saves it to variables
   * @param obj a JSON Object representing the FitBit activities summary
   */
  private void parseSummary(JSONObject obj) {
	  JSONObject summary = obj.getJSONObject("summary");
	  userDailyCalories = summary.getInt("caloriesOut");
	  userDailyDistance= summary.getJSONArray("distances").getJSONObject(0).getDouble("distance");
	  userDailyFloorsClimbed = summary.getInt("floors");
	  userDailySteps = summary.getInt("steps");
	  userDailySendentaryMinutes = summary.getInt("sedentaryMinutes");
	  userDailyLightlyActiveMinutes = summary.getInt("lightlyActiveMinutes");
	  userDailyFairlyActiveMinutes = summary.getInt("fairlyActiveMinutes");
	  userDailyVeryActiveMinutes = summary.getInt("veryActiveMinutes");
	  
  }
  
  /**
   * A method that takes a JSON object that contains the lifetime totals and returns the lifetime distance, floors and steps 
   * @param obj a JSON object that contains the lifetime totals
   */
  private void parseLifeTimeTotal(JSONObject obj) {
	  JSONObject lifetimeTotal = obj.getJSONObject("lifetime").getJSONObject("total");
	  totalDistance = lifetimeTotal.getDouble("distance");
	  totalFloors = lifetimeTotal.getInt("floors");
	  totalSteps = lifetimeTotal.getInt("steps");
  }
  
  /**
   * A method that takes a JSON object that contains the best days and returns the best distance, floors and steps
   * @param obj a JSON object that contains best days
   */
  private void parseBestDays(JSONObject obj) {
	  JSONObject bestDays = obj.getJSONObject("best").getJSONObject("total");
	  bestDistance = bestDays.getJSONObject("distance").getDouble("value");
	  bestFloors = bestDays.getJSONObject("floors").getInt("value");
	  bestSteps = bestDays.getJSONObject("steps").getInt("value");
  }
  
  /********************************************************
   * 						Getters						  *
   ********************************************************/
  
  /**
   * getter that returns the users daily steps for day
   * @return number of steps the user has walked for the day
   */
  public int getSteps() {
    return this.userDailySteps;
  } 
  /**
   * getter that returns the users daily distance for the day
   * @return total distance the user has traveled for the day
   */
  public double getDistance() {
    return this.userDailyDistance;
  } 
  /**
   * getter that returns the users daily distance for the day
   * @return total calories the user has burned for the day
   */
  public int getCalories() {
    return this.userDailyCalories;
  }  
  /**
   * getter that returns the users daily floors climbed for the day
   * @return total number of floors the user has climbed for the day
   */
  public int getFloorsClimbed() {
    return this.userDailyFloorsClimbed;
  }
  /**
   * getter that returns the number of sendentary minutes for that day 
   * @return total number of sendentary minutes for the day
   */
  public int getSendentaryMinutes() {
    return this.userDailySendentaryMinutes;
  }
  /**
   * getter that returns the number of very active minutes for the day
   * @return total number of very active minutes for the day
   */
  public int getVeryActiveMin() {
	  return this.userDailyVeryActiveMinutes;
  }
  /**
   * getter that returns the number of fairly active minutes for the day
   * @return total number of fairly active minutes for the day
   */
  public int getFairlyActiveMin() {
	  return this.userDailyFairlyActiveMinutes;
  }
  /**
   * getter that returns the number of lightly active minutes for the day
   * @return total number of lightly active minutes for the day
   */
  public int getLightlyActiveMin() {
	  return this.userDailyLightlyActiveMinutes;
  }
  /**
   * getter that returns the total lifetime distance
   * @return users total lifetime distance
   */
  public double getTotalDistance() {
	  return this.totalDistance;
  }
  /**
   * getter that returns the total lifetime floors climbed
   * @return users total lifetime floors climbed
   */
  public int getTotalFloors() {
	  return this.totalFloors;
  }
  /**
   * getter that returns total lifetime steps taken
   * @return users total lifetime steps taken
   */
  public int getTotalSteps() {
	  return this.totalSteps;
  } 
  /**
   * getter that returns the best distance
   * @return users best recorded distance
   */
  public double getBestDistance() {
	  return this.bestDistance;
  }
  /**
   * getter that returns the best floors climbed
   * @return users best recorded floors climbed
   */
  public int getBestFloors() {
	  return this.bestFloors;
  } 
  /**
   * getter that returns the best steps
   * @return users best recorded steps taken
   */
  public int getBestSteps() {
	  return this.bestSteps;
  }
}
