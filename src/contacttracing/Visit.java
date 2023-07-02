package contacttracing;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Chris Taylor taycr003@mymail.unisa.edu.au
 * represents a visit by a person to a location
 */
public class Visit implements Comparable<Visit> {
	
	private String personName;
	private String locationName;
	private LocalDate date; // Date of the visit
	private LocalTime entryTime; // Entry time
	private LocalTime exitTime; // Exit time
	
	public Visit(String personName, String locationName, LocalDate date, LocalTime entryTime, LocalTime exitTime) {
		
		this.personName = personName;
		this.locationName = locationName;
		this.date = date;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
	}

	
	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	

	@Override
	public String toString() {
		return "Visit [personName=" + personName + ", locationName=" + locationName + ", date=" + date + ", entryTime="
				+ entryTime + ", exitTime=" + exitTime + "]";
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public LocalTime getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(LocalTime entryTime) {
		this.entryTime = entryTime;
	}

	public LocalTime getExitTime() {
		return exitTime;
	}

	public void setExitTime(LocalTime exitTime) {
		this.exitTime = exitTime;
	}
	
	@Override
		//EDIT THIS!!!! to before and afgter
	  public int compareTo(Visit v) {
	    return this.getDate().compareTo(v.getDate());
	  }
	
	

	
}

