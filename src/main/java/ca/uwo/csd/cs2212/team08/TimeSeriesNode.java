package ca.uwo.csd.cs2212.team08;

//series.add(new Minute( minutes, hours, day, month, 2016), value);
/**
 * a class that creates a time series data object
 */
public class TimeSeriesNode {
	
	String minute;
	String hour;
	String day;
	String month;
	String year;
	String value;
	
	/**
	 * the constructor for creating a new time series
	 * @param minute pass in the current minute
	 * @param hours pass in the current hours
	 * @param date pass in the date
	 * @param value pass in the current value
	 */
	public TimeSeriesNode(String minute, String hours, String date, String value) {
		this.minute = minute;
		this.hour = hours;
		String[] dateArray = date.split("-");
		this.day = dateArray[2];
		this.month = dateArray[1];
		this.year = dateArray[0];
		this.value = value;
	}

	/**
	 * @return the minutes
	 */
	public String getMinute() {
		return minute;
	}

	/**
	 * @return the hours
	 */
	public String getHour() {
		return hour;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


}
