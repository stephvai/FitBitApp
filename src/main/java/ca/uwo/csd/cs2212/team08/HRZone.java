package ca.uwo.csd.cs2212.team08;

import org.json.JSONObject;

/**
 * a class to create a heart rate zone object
 * 
 */
public class HRZone {
	
	String name;
	String value;
	
	/**
	 * a constructor for the hear rate object
	 * @param heartRateData pass in a json object with heartrate data
	 */
	public HRZone(JSONObject heartRateData) {
		this.name = heartRateData.getString("name");
		this.value = Integer.toString(heartRateData.getInt("minutes"));
	}
	
	/**
	 * a 2nd constructor for the heart rate zones
	 * @param name	pass in the name of the HR zone
	 * @param value pass in the value of the HR zone
	 */
	public HRZone(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the minutes
	 */
	public String getValue() {
		return value;
	}

}
