package ca.uwo.csd.cs2212.team08;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * API Test Data Class that generates test data when not online
 *
 */
public class APIDataTest extends APIData {

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
	  private String restingHeartRate;
	  private HRZone outOfRange;
	  private HRZone fatBurn;
	  private HRZone cardio;
	  private HRZone peak;
	  //Time Series Data
	  private LinkedList<TimeSeriesNode> hrTimeSeries;
	  private LinkedList<TimeSeriesNode> stepsTimeSeries;
	  private LinkedList<TimeSeriesNode> caloriesTimeSeries;
	  private LinkedList<TimeSeriesNode> distanceTimeSeries;
    


    //String representation of the date in YYYY-MM-DD format
    String currentDate = "2016-02-26";;
    
    /**
     * constructor for test data
     */
    public APIDataTest(){}

    /**
     * Random Number Generator
     *
     * @param min Minimum Number
     * @param max Maximum Number
     * @return Random Number
     */
    private static int randInt(int min, int max){
        Random rand = new Random();
        int randomNum = rand.nextInt((max-min)+1)+min;

        return randomNum;
    }
    
    /**
     * method that refreshes the dashboard data with test values
     */
    public Boolean refreshDailyDashBoardData(String date) {

        currentDate = date;
        userDailySteps = randInt(1, 4000);
        userDailyDistance = (float)randInt(1, 20);
        userDailyCalories = randInt(1,2000);
        userDailyFloorsClimbed = randInt(1,25);
        
        //total day is 1440
        
        userDailySendentaryMinutes = randInt(600,800);
        userDailyLightlyActiveMinutes = randInt(150,300);
        userDailyFairlyActiveMinutes = randInt(150,300);
        userDailyVeryActiveMinutes = 1440-userDailySendentaryMinutes-userDailyLightlyActiveMinutes-userDailyFairlyActiveMinutes;
        
        
        totalDistance = (float)randInt((int)userDailyDistance, 10000);
        totalFloors = randInt((int)userDailyFloorsClimbed,700);
        totalSteps = randInt((int)userDailySteps,100000);
        bestDistance = (float)randInt((int)userDailyDistance, (int)totalDistance);
        bestFloors = randInt((int)userDailyFloorsClimbed, (int)totalFloors);
        bestSteps= randInt((int)userDailySteps, (int)totalSteps);
        restingHeartRate = Integer.toString(randInt(60, 100));
        
        FileReader fileReader;
		try {
			fileReader = new FileReader("src/main/resources/testdata.txt");
			BufferedReader reader = new BufferedReader(fileReader);
			
			String HRJson = reader.readLine();
			JSONObject obj = new JSONObject(HRJson);
			parseHeartRateZones(obj);
			
			String stepsJson = reader.readLine();
			obj = new JSONObject(stepsJson);
			parseStepsTimeSeries(obj);

			String caloriesJson = reader.readLine();
			obj = new JSONObject(caloriesJson);
			parseCaloriesTimeSeries(obj);

			String distanceJson = reader.readLine();
			obj = new JSONObject(distanceJson);
			parseDistanceTimeSeries(obj);
			
			
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return true;

    }
    
    /**
     * Parses heart rate zone data and creates a linked list for the grpahs
     * @param obj that contains the heart rate zones
     */
    private void parseHeartRateZones(JSONObject obj) {
  		  JSONObject value = obj.getJSONArray("activities-heart").getJSONObject(0).getJSONObject("value");
  		  restingHeartRate = Integer.toString(value.getInt("restingHeartRate"));
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
    
    /**
     * Parse steps data and creates a linked list for the graphs
     * @param obj that contains the steps data
     */
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
    
    /**
     * Parse calories data and creates a linked list for the graphs
     * @param obj that contains the calories data
     */
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
    
    /**
     * Parse distance data and create a linked list for the graphs
     * @param obj Object that contains distance data
     */
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
    
    /**
     * Method to see if the api data is in test mode
     *@return Returns true if it is in test mode
     */
    public Boolean isTestMode() {
  	  return true;
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
  public String getRestingHeartRate() {
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
