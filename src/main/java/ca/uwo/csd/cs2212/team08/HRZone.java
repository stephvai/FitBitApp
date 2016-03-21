package ca.uwo.csd.cs2212.team08;

import org.json.JSONObject;

public class HRZone {
	
	String name;
	float minutes;
	
	public HRZone(JSONObject heartRateData) {
		this.name = heartRateData.getString("name");
		this.minutes = heartRateData.getInt("minutes");
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
	public float getMinutes() {
		return minutes;
	}

}
