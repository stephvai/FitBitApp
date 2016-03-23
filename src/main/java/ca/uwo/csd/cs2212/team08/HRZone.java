package ca.uwo.csd.cs2212.team08;

import org.json.JSONObject;

public class HRZone {
	
	String name;
	String value;
	
	public HRZone(JSONObject heartRateData) {
		this.name = heartRateData.getString("name");
		this.value = Integer.toString(heartRateData.getInt("minutes"));
	}
	
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
