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
//TODO Add Steps,Distance,Calories,Active minutes,sedentary minutes to refresh method
//TODO Handle exceptions to log.txt instead of console from reading/writing files

public class APIData {

  //Instance variables for daily user data to be used on the daily dashboard 
  private int userDailySteps;
  private int userDailyDistance;
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
        
        //The start of all request urls
        String requestUrlPrefix = "https://api.fitbit.com/1/user/3WGW2P/";
        //stores the request URL
        String requestUrl;
        
        //First get the floors
        
        //Create the response, sign it and the send it
        requestUrl = requestUrlPrefix + "activities/floors/date/2016-01-07/1d/1min/time/19:15/19:30.json";
        OAuthRequest request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        Response response = request.send();
        //check the response  and refresh if needed
        System.out.println("HTTP response code: "+response.getCode());
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
        //if we get a successful request
        if (checkResponse == successfulResponse) {
        	System.out.println("Successful Response");
            JSONObject obj = new JSONObject(response.getBody());
            parseFloors(obj);
        }
        
        else {
        	System.out.println("Error getting fitbit data: " + response.getCode());
        }
        
        //Finish Getting the Floors
        
        //Finally save the current tokens
        saveTokes(accessToken);
  }
  
  /********************************************************
   * 	 		  API Request helper methods			  *
   ********************************************************/
  
  private void saveTokes(OAuth2AccessToken accessToken) {
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
  
  /********************************************************
   * 				  JSON Parsing Methods		  		  *
   ********************************************************/
  
  /**
   * Parse a JSON object containing the floors and saves the value to the userDailyFloorsClimbed variable 
   * @param obj pass in a JSON object returned from the API contain the floors climbed 
   */
  private void parseFloors(JSONObject obj) {
	  JSONArray floorsArray = obj.getJSONArray("activities-floors");
	  JSONObject floorsData = floorsArray.getJSONObject(0);
	  this.userDailyFloorsClimbed = floorsData.getInt("value");
  }
  
  /**
   * Parse a JSON object containing the best days and lifetime totals and save the value to the required variables
   * @param obj pass in a JSON object returned from the API that contains the best days
   */
  private void parseBestDaysLifeTimeTotals(JSONObject obj) {
	  JSONObject bestDays = obj.getJSONObject("best").getJSONObject("total");
	  JSONObject lifeTime = obj.getJSONObject("lifetime");
	  
	  //TODO
	  
  }
  
  /**
   * Parse a JSON object containing the total steps and save the value to the userDailySteps variable
   * @param obj pass in a JSON object that contains the daily steps
   */
  private void parseSteps(JSONObject obj) {
	  JSONObject stepsData = obj.getJSONObject("activities-tracker-steps");
	  this.userDailySteps = stepsData.getInt("value");
  }
  
  /**
   * Parse a JSON object containing the total calories and save the value to the userDailyCalories variable
   * @param obj pass in a JSON object that contains the daily calories
   */
  private void parseCalories(JSONObject obj) {
	  JSONObject caloriesData = obj.getJSONObject("activities-activityCalories");
	  this.userDailyCalories = caloriesData.getInt("value");
	  
  }
  
  /**
   * Parse a JSON object containing the total daily distance and save the value to the userDailyDistance variable
   * @param obj pass in a JSON object that contains the daily distanace
   */
  private void parseDistance(JSONObject obj) {
	JSONObject distanceData = obj.getJSONObject("activities-tracker-distance");
	this.userDailyDistance = distanceData.getInt("value");
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
  public int getDistance() {
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
