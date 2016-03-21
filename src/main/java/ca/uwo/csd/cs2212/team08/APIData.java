package ca.uwo.csd.cs2212.team08;

import com.github.scribejava.apis.FitbitApi20;
import com.github.scribejava.apis.service.FitbitOAuth20ServiceImpl;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.LinkedList;

//TODO Make sure it returns false whenever the application crashes
//TODO Remove all writing to console and instead save it to log.txt

/**
 * APIData Class that gets data from the fitbit servers and parses it to variables that can be 
 * gotten from the main class
 */
public class APIData {

  //Instance variables for daily user data to be used on the daily dashboard 
  private float userDailySteps;
  private float userDailyDistance;
  private float userDailyCalories;
  private float userDailyFloorsClimbed;
  private float userDailySendentaryMinutes;
  private float userDailyLightlyActiveMinutes;
  private float userDailyFairlyActiveMinutes;
  private float userDailyVeryActiveMinutes;
  //Instance variables for total values
  private float totalDistance;
  private float totalFloors;
  private float totalSteps;
  //Instance variables for best values
  private float bestDistance;
  private float bestFloors;
  private float bestSteps;
  //Instance variables for heart rate zones
  private float restingHeartRate;
  private HRZone outOfRange;
  private HRZone fatBurn;
  private HRZone cardio;
  private HRZone peak;
  //Time Series Data
  LinkedList<TimeSeriesNode> hrTimeSeries;
  LinkedList<TimeSeriesNode> stepsTimeSeries;
  LinkedList<TimeSeriesNode> caloriesTimeSeries;
  LinkedList<TimeSeriesNode> distanceTimeSeries;
  
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
            apiKey = bufferedReader.readLine();
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
                    bufferedReader.close();
            }
            catch(Exception e){
                return false;
            }
        }
        //  Create the Fitbit service 
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
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
        }
        if(!saveTokens(accessToken)) {
        	return false;
        }
        if (checkResponse == successfulResponse) {
        	JSONObject obj = new JSONObject(response.getBody());
        	parseSummary(obj);
        }
        else {
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
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
        }
        if(!saveTokens(accessToken)) {
        	return false;
        }
        
        if (checkResponse == successfulResponse) {
        	JSONObject obj = new JSONObject(response.getBody());
        	parseLifeTimeTotal(obj);
        	parseBestDays(obj);
        }
        
        else {
        	return false;
        }
        
        //GETTING AND PARSING HEART RATE ZONES
        requestUrl = heartRateZoneRequestBuilder(requestUrlPrefix);
        request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        response = request.send();
        checkResponse = checkStatus(response.getCode());
        
        if (checkResponse == expiredToken) {
        	//refresh token then send it again
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
        }
        if(!saveTokens(accessToken)) {
        	return false;
        }
        
        if (checkResponse == successfulResponse) {
        	JSONObject obj = new JSONObject(response.getBody());
        	//parseHeartRateZones(obj);
        	
        }
        else {
        	return false;
        }
        
        //GETTING AND PARSING STEPS TIME SERIES
        requestUrl = stepsTimeSeriesRequestBuilder(requestUrlPrefix);
        request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        response = request.send();
        checkResponse = checkStatus(response.getCode());
        
        if (checkResponse == expiredToken) {
        	//refresh token then send it again
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
        }
        if(!saveTokens(accessToken)) {
        	return false;
        }
        
        if (checkResponse == successfulResponse) {
        	JSONObject obj = new JSONObject(response.getBody());
        	parseStepsTimeSeries(obj);
        	
        }  
        else {
        	return false;
        }
        
        //GETTING AND PARSING CALORIES TIME SERIES
        requestUrl = caloriesTimeSeriesRequestBuilder(requestUrlPrefix);
        request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        response = request.send();
        checkResponse = checkStatus(response.getCode());
        
        if (checkResponse == expiredToken) {
        	//refresh token then send it again
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
        }
        if(!saveTokens(accessToken)) {
        	return false;
        }
        
        if (checkResponse == successfulResponse) {
        	JSONObject obj = new JSONObject(response.getBody());
        	parseCaloriesTimeSeries(obj);
        	
        }  
        else {
        	return false;
        }
        
        //GETTING AND PARSING TIME SERIES DATA FOR DISTANCE
        
        requestUrl = distanceTimeSeriesRequestBuilder(requestUrlPrefix);
        request = new OAuthRequest(Verb.GET, requestUrl, service);
        service.signRequest(accessToken, request);
        response = request.send();
        checkResponse = checkStatus(response.getCode());
        
        if (checkResponse == expiredToken) {
        	//refresh token then send it again
        	accessToken = service.refreshOAuth2AccessToken(accessToken);
        	request = new OAuthRequest(Verb.GET, requestUrl, service);
            service.signRequest(accessToken, request);
            response = request.send();
            checkResponse = checkStatus(response.getCode());
        }
        if(!saveTokens(accessToken)) {
        	return false;
        }
        
        if (checkResponse == successfulResponse) {
        	JSONObject obj = new JSONObject(response.getBody());
        	parseDistanceTimeSeries(obj);
        	
        }  
        else {
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
  
  private String heartRateZoneRequestBuilder(String requestUrlPrefix) {
	  //https://api.fitbit.com/1/user/-/activities/heart/date/[date]/1d/[detail-level].json
	  return "https://api.fitbit.com/1/user/3WGW2P/activities/heart/date/" + currentDate + "/1d/1min.json";
  }
  
  private String stepsTimeSeriesRequestBuilder(String requestUrlPrefix) {
	  return "https://api.fitbit.com/1/user/3WGW2P/activities/steps/date/"+ currentDate +"/1d/1min.json";
  }
  
  private String caloriesTimeSeriesRequestBuilder(String requestUrlPrefix) {
	  return "https://api.fitbit.com/1/user/-/activities/calories/date/" + currentDate + "/1d/1min.json";
  }
  
  private String distanceTimeSeriesRequestBuilder(String requestUrlPrefix) {
	  return "https://api.fitbit.com/1/user/-/activities/distance/date/" + currentDate + "/1d/1min.json";
  }
  
  /**
   * Method that writes the current token to the token storage file
   * @param accessToken the currently active token to write out
   */
  private Boolean saveTokens(OAuth2AccessToken accessToken) {
	  BufferedWriter bufferedWriter=null;
      //  Save the current accessToken information for next time
      try {
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
    	  return false;
      }
      catch(IOException ex) {
    	  return false;
      }
      finally{
          try{
              if (bufferedWriter!=null)
                  bufferedWriter.close();
          }
          catch(Exception e){
              return false;
          }
      }//end try
      return true;
  }
  
  /**
   * Method that takes a HTTP status code and returns a code for what kind of response occured
   * @param statusCode the HTTP status code provided in the response
   * @return a value that represents what kind of response has occured 
   */
  private int checkStatus(int statusCode) {
	  System.out.println(statusCode);
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
	  userDailyDistance= (float)summary.getJSONArray("distances").getJSONObject(0).getDouble("distance");
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
	  totalDistance = (float)lifetimeTotal.getDouble("distance");
	  totalFloors = lifetimeTotal.getInt("floors");
	  totalSteps = lifetimeTotal.getInt("steps");
  }
  
  /**
   * A method that takes a JSON object that contains the best days and returns the best distance, floors and steps
   * @param obj a JSON object that contains best days
   */
  private void parseBestDays(JSONObject obj) {
	  JSONObject bestDays = obj.getJSONObject("best").getJSONObject("total");
	  bestDistance = (float)bestDays.getJSONObject("distance").getDouble("value");
	  bestFloors = bestDays.getJSONObject("floors").getInt("value");
	  bestSteps = bestDays.getJSONObject("steps").getInt("value");
  }
  
  private void parseHeartRateZones(JSONObject obj) {
	  JSONObject value = obj.getJSONArray("activities-heart").getJSONObject(0).getJSONObject("value");
	  restingHeartRate = value.getInt("restingHeartRate");
	  JSONArray heartRateZones = value.getJSONArray("heartRateZones");
	  outOfRange = new HRZone(heartRateZones.getJSONObject(0));
	  fatBurn = new HRZone(heartRateZones.getJSONObject(1));
	  cardio = new HRZone(heartRateZones.getJSONObject(2));
	  peak = new HRZone(heartRateZones.getJSONObject(3));
	  
	  hrTimeSeries = new LinkedList<TimeSeriesNode>();
	  
	  JSONArray dataset = obj.getJSONObject("activities-heart-intraday").getJSONArray("dataset");
	  for (int i = 0; i < dataset.length(); i++) {
		  JSONObject datapoint = dataset.getJSONObject(i);
		  String time = datapoint.getString("time");
		  String datasetValue = Integer.toString(datapoint.getInt("value"));
		  String[] timeArray = time.split(":");
		  String hour = timeArray[0];
		  String min = timeArray[1];
		  TimeSeriesNode node = new TimeSeriesNode(min, hour, currentDate, datasetValue);
		  hrTimeSeries.add(node);
	 }
	  
  }
  
  private void parseStepsTimeSeries(JSONObject obj) {
	  
	  stepsTimeSeries = new LinkedList<TimeSeriesNode>();
	  
	  JSONArray dataset = obj.getJSONObject("activities-steps-intraday").getJSONArray("dataset");
	  for (int i = 0; i < dataset.length(); i++) {
		  JSONObject datapoint = dataset.getJSONObject(i);
		  String time = datapoint.getString("time");
		  String datasetValue = Integer.toString(datapoint.getInt("value"));
		  String[] timeArray = time.split(":");
		  String hour = timeArray[0];
		  String min = timeArray[1];
		  TimeSeriesNode node = new TimeSeriesNode(min, hour, currentDate, datasetValue);
		  stepsTimeSeries.add(node);
	 }
	  
  }
  
  private void parseCaloriesTimeSeries(JSONObject obj) {
	  caloriesTimeSeries = new LinkedList<TimeSeriesNode>();
	  
	  JSONArray dataset = obj.getJSONObject("activities-calories-intraday").getJSONArray("dataset");
	  for (int i = 0; i < dataset.length(); i++) {
		  JSONObject datapoint = dataset.getJSONObject(i);
		  String time = datapoint.getString("time");
		  String datasetValue = Integer.toString(datapoint.getInt("value"));
		  String[] timeArray = time.split(":");
		  String hour = timeArray[0];
		  String min = timeArray[1];
		  TimeSeriesNode node = new TimeSeriesNode(min, hour, currentDate, datasetValue);
		  caloriesTimeSeries.add(node);
	 }
  }
  
  private void parseDistanceTimeSeries(JSONObject obj) {
	  distanceTimeSeries = new LinkedList<TimeSeriesNode>();
	  
	  JSONArray dataset = obj.getJSONObject("activities-distance-intraday").getJSONArray("dataset");
	  for (int i = 0; i < dataset.length(); i++) {
		  JSONObject datapoint = dataset.getJSONObject(i);
		  String time = datapoint.getString("time");
		  String datasetValue = Float.toString((float)datapoint.getDouble("value"));
		  String[] timeArray = time.split(":");
		  String hour = timeArray[0];
		  String min = timeArray[1];
		  TimeSeriesNode node = new TimeSeriesNode(min, hour, currentDate, datasetValue);
		  distanceTimeSeries.add(node);
	 }
  }
  
  /********************************************************
   * 						Getters						  *
   ********************************************************/
  
  
  /**
   * @return the distanceTimeSeries
   */
  public LinkedList<TimeSeriesNode> getDistanceTimeSeries() {
  	return distanceTimeSeries;
  }
  
  /**
   * @return the hrTimeSeries
   */
  public LinkedList<TimeSeriesNode> getCaloriesTimeSeries() {
  	return caloriesTimeSeries;
  }
 /**
 * @return the stepsTimeSeries
 */
public LinkedList<TimeSeriesNode> getStepsTimeSeries() {
	return stepsTimeSeries;
}

	
  /**
 * @return the hrTimeSeries
 */
public LinkedList<TimeSeriesNode> getHrTimeSeries() {
	return hrTimeSeries;
}

  /**
 * @return the restingHeartRate
 */
public float getRestingHeartRate() {
	return this.restingHeartRate;
}

/**
 * @return the outOfRange
 */
public HRZone getOutOfRange() {
	return this.outOfRange;
}

/**
 * @return the fatBurn
 */
public HRZone getFatBurn() {
	return this.fatBurn;
}

/**
 * @return the cardio
 */
public HRZone getCardio() {
	return this.cardio;
}

/**
 * @return the peak
 */
public HRZone getPeak() {
	return this.peak;
}

/**
   * getter that returns the users daily steps for day
   * @return number of steps the user has walked for the day
   */
  public float getSteps() {
    return this.userDailySteps;
  } 
  /**
   * getter that returns the users daily distance for the day
   * @return total distance the user has traveled for the day
   */
  public float getDistance() {
    return this.userDailyDistance;
  } 
  /**
   * getter that returns the users daily distance for the day
   * @return total calories the user has burned for the day
   */
  public float getCalories() {
    return this.userDailyCalories;
  }  
  /**
   * getter that returns the users daily floors climbed for the day
   * @return total number of floors the user has climbed for the day
   */
  public float getFloorsClimbed() {
    return this.userDailyFloorsClimbed;
  }
  /**
   * getter that returns the number of sendentary minutes for that day 
   * @return total number of sendentary minutes for the day
   */
  public float getSendentaryMinutes() {
    return this.userDailySendentaryMinutes;
  }
  /**
   * getter that returns the number of very active minutes for the day
   * @return total number of very active minutes for the day
   */
  public float getVeryActiveMin() {
	  return this.userDailyVeryActiveMinutes;
  }
  /**
   * getter that returns the number of fairly active minutes for the day
   * @return total number of fairly active minutes for the day
   */
  public float getFairlyActiveMin() {
	  return this.userDailyFairlyActiveMinutes;
  }
  /**
   * getter that returns the number of lightly active minutes for the day
   * @return total number of lightly active minutes for the day
   */
  public float getLightlyActiveMin() {
	  return this.userDailyLightlyActiveMinutes;
  }
  /**
   * getter that returns the total lifetime distance
   * @return users total lifetime distance
   */
  public float getTotalDistance() {
	  return this.totalDistance;
  }
  /**
   * getter that returns the total lifetime floors climbed
   * @return users total lifetime floors climbed
   */
  public float getTotalFloors() {
	  return this.totalFloors;
  }
  /**
   * getter that returns total lifetime steps taken
   * @return users total lifetime steps taken
   */
  public float getTotalSteps() {
	  return this.totalSteps;
  } 
  /**
   * getter that returns the best distance
   * @return users best recorded distance
   */
  public float getBestDistance() {
	  return this.bestDistance;
  }
  /**
   * getter that returns the best floors climbed
   * @return users best recorded floors climbed
   */
  public float getBestFloors() {
	  return this.bestFloors;
  } 
  /**
   * getter that returns the best steps
   * @return users best recorded steps taken
   */
  public float getBestSteps() {
	  return this.bestSteps;
  }
}