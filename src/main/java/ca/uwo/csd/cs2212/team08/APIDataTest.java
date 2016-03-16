package ca.uwo.csd.cs2212.team08;

import java.util.Random;


/**
 * Created by Aboudi on 2016-02-29.
 */
public class APIDataTest extends APIData {

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


    //String representation of the date in YYYY-MM-DD format
    String currentDate = "2016-02-26";;

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

    public Boolean refreshDailyDashBoardData(String date) {

        currentDate = date;
        userDailySteps = randInt(1, 4000);
        userDailyDistance = (double)randInt(1, 20);
        userDailyCalories = randInt(1,2000);
        userDailyFloorsClimbed = randInt(1,25);
        userDailySendentaryMinutes = randInt(1,1440);
        userDailyLightlyActiveMinutes = randInt(0,(1440-userDailySendentaryMinutes));
        userDailyFairlyActiveMinutes = randInt(0,(1440-userDailySendentaryMinutes-userDailyLightlyActiveMinutes));
        userDailyVeryActiveMinutes = 1440-userDailySendentaryMinutes-userDailyLightlyActiveMinutes-userDailyFairlyActiveMinutes;

        totalDistance = (double)randInt((int)userDailyDistance, 10000);
        totalFloors = randInt(userDailyFloorsClimbed,700);
        totalSteps = randInt(userDailySteps,100000);

        bestDistance = (double)randInt((int)userDailyDistance, (int)totalDistance);
        bestFloors = randInt(userDailyFloorsClimbed, totalFloors);
        bestSteps= randInt(userDailySteps, totalSteps);

        return true;

    }

    /********************************************************
     * 						Getters						  *
     ********************************************************/

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
        return (float)this.userDailyDistance;
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
        return (float)this.totalDistance;
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
        return (float)this.bestDistance;
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
