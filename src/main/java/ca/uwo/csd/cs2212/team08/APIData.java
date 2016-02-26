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

//TODO ADD try / catch and exception handling for JSON parsing
//TODO Active minutes, sedentary minutes to refresh method
//TODO Handle exceptions to log.txt instead of console from reading/writing files

public class APIData {

  //Instance variables for daily user data to be used on the daily dashboard 
  private int userDailySteps;
  private double userDailyDistance;
  private int userDailyCalories;
  private int userDailyFloorsClimbed;
  private int userDailyActiveMinutes;
  private int userDailySendentaryMinutes;
  
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
  String date = "2016-01-08";
  //Activities categories
  final String calories = "calories";
  final String floors = "floors";
  final String steps = "steps";
  final String distance = "distance";
  
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
   * @param day the day we are getting data for
   * @param month the month we are getting data for
   * @param year the year we are getting data for
   */
  public void refreshDashBoardData(int day, int month, int year) {
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
            System.out.println(
                    "Unable to open file\n"+ex.getMessage());
            System.exit(1);
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading/write file\n"+ex.getMessage());
            System.exit(1);
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
        
        //GETTING AND PARSING CALORIES DATA + REFRESHING TOKENS
        
        String requestUrl = dailyRequestBuilder(calories, date);
        OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        //check the response  and refresh if needed
        int checkResponse = checkStatus(response.getCode());
        //if token is expired refresh token and try again
        if (checkResponse == expiredToken) {
        	//refresh token then send it again
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
            System.out.println("HTTP response code after refresh: "+response.getCode());
        }
        //save the tokens
        saveTokens(accessToken);  
        //if we get a successful request
        if (checkResponse == successfulResponse) {
        	System.out.println("Successful Response - calories");
            JSONObject obj = new JSONObject(response.getBody());
            userDailyCalories = (int)parseDailyData(obj, calories);
        }  
        else {
        	System.out.println("Error getting fitbit calories data: " + response.getCode());
        }
        
        //GETTING AND PARSING FLOOR DATA
        
        requestUrl = dailyRequestBuilder(floors, date);
        request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        response = request.send();
        checkResponse = checkStatus(response.getCode());
        if (checkResponse == successfulResponse) {
        	System.out.println("Successful Response - floors");
        	JSONObject obj = new JSONObject(response.getBody());
        	userDailyFloorsClimbed = (int)parseDailyData(obj, floors);
        }
        else {
        	System.out.println("Error getting fitbit floors data: " + response.getCode());
        }
        
        //GETTING AND PARSING STEPS DATA
        
        requestUrl = dailyRequestBuilder(steps, date);
        request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        response = request.send();
        checkResponse = checkStatus(response.getCode());
        if (checkResponse == successfulResponse) {
        	System.out.println("Successful Response - steps");
        	JSONObject obj = new JSONObject(response.getBody());
        	userDailySteps = (int)parseDailyData(obj, steps);
        }      
        else {
        	System.out.println("Error getting fitbit floors data: " + response.getCode());
        }
        
        //GETTING AND PARSING DISTANCE
        
        requestUrl = dailyRequestBuilder(distance, date);
        request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        response = request.send();
        checkResponse = checkStatus(response.getCode());
        if (checkResponse == successfulResponse) {
        	System.out.println("Successful Response - distance");
        	JSONObject obj = new JSONObject(response.getBody());
        	userDailyDistance = parseDailyData(obj, distance);
        }  
        else {
        	System.out.println("Error getting fitbit floors data: " + response.getCode());
        }
  }
  
  /********************************************************
   * 	 		  API Request helper methods			  *
   ********************************************************/
  
  /**
   * 
   * @param activity the activty you want to get data for
   * @param date String representation in the form YYYY-MM-DD
   * @return returns the appropriate API request url given an activity and the date 
   */
  private String dailyRequestBuilder(String activity, String date) {
      String requestUrlPrefix = "https://api.fitbit.com/1/user/3WGW2P/";
      String requestUrl;
      requestUrl = requestUrlPrefix + "activities/tracker/" + activity + "/date/" + date + "/1d.json";
      return requestUrl;
  }
  
  private void saveTokens(OAuth2AccessToken accessToken) {
	  BufferedWriter bufferedWriter=null;
      //  Save the current accessToken information for next time

      // IF YOU DO NOT SAVE THE CURRENTLY ACTIVE TOKEN INFO YOU WILL NOT BE ABLE TO REFRESH
      //   - contact Beth if this happens and she can reissue you a fresh set

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
   * A method that takes an object with JSONdata returned from the API and a value to get and saves that value to the proper variable
   * @param obj
   * @param activity
   */
  private double parseDailyData(JSONObject obj, String activity) {
	  return obj.getJSONArray("activities-tracker-" + activity).getJSONObject(0).getDouble("value");
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
   * getter that returns the number of active minutes for that day
   * @return total number of active minutes the user has acheived for the day
   */
  public int getActiveMinutes() {
    return this.userDailyActiveMinutes;
  }
  
  /**
   * getter that returns the number of sendentary minutes for that day 
   * @return total number of sendentary minutes for the day
   */
  public int getSendentaryMinutes() {
    return this.userDailySendentaryMinutes;
  }
  
}
