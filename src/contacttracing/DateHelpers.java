package contacttracing;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Chris Taylor taycr003@mymail.unisa.edu.au
 * Use this class to convert strings to dates, or strings to time
 */
public class DateHelpers {
	
	public static LocalDate CURRENT_DATE = LocalDate.of(2022, 11, 20);
	public static int DAYS_CASE_IS_SICK = 9; //taken from people.csv and used for determining who NEW_CASE caught from

	/**
	   * Converts a string representation of a date to a LocalDate object
	   * @param arg A string representation of a date
	   * @exception Any exception
	   * @return LocalDate
	   */
	public static LocalDate ParseDateString(String date) {
		String[] splitDate = date.split("/");
		int dayOfMonth = Integer.parseInt(splitDate[0]);
		int month = Integer.parseInt(splitDate[1]);
		int year = Integer.parseInt(splitDate[2]);
	
		return LocalDate.of(year, month, dayOfMonth);
	}
	
	/**
	   * Converts a string representation of a time to a LocalTime object
	   * @param arg A string representation of a time of day
	   * @exception Any exception
	   * @return LocalTime
	   */
	public static LocalTime ParseTimeString(String time) {
		String[] splitTime = time.split(":");
		int hour = Integer.parseInt(splitTime[0]);
		int minute = Integer.parseInt(splitTime[1]);
		
		return LocalTime.of(hour, minute);
	}
}

