package contacttracing;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Contains details about a location.
 * Name is assumed to be unique among all instances of Location in this system.
 * @author Chris Taylor taycr003@mymail.unisa.edu.au
 */
public class Location {

	private String name; // The unique name of the location
	//ArrayList<Visit> visits; // simple data structure to maintain list of all visits
	SkipList visitsSkip;
	HashMap<String, ArrayList<Visit>> visitsByMonth; 
	
	//13 sub lists for months, representing predicted length of pandemic
	//each location will have visits sub-divided by month
	ArrayList<Visit> oct22;
	ArrayList<Visit> nov22;
	ArrayList<Visit> dec22;
	ArrayList<Visit> jan23;
	ArrayList<Visit> feb23;
	ArrayList<Visit> mar23;
	ArrayList<Visit> apr23;
	ArrayList<Visit> may23;
	ArrayList<Visit> jun23;
	ArrayList<Visit> jul23;
	ArrayList<Visit> aug23;
	ArrayList<Visit> sept23;
	ArrayList<Visit> oct23;
	
	public Location(String name) {
		
		this.name = name;
		visitsSkip = new SkipList();
		//visits = new ArrayList<Visit>();
		visitsByMonth = new HashMap<String, ArrayList<Visit>>(0.75);
		
		oct22 = new ArrayList<Visit>();
		nov22 = new ArrayList<Visit>();
		dec22 = new ArrayList<Visit>();
		jan23 = new ArrayList<Visit>();
		feb23 = new ArrayList<Visit>();
		mar23 = new ArrayList<Visit>();
		apr23 = new ArrayList<Visit>();
		may23 = new ArrayList<Visit>();
		jun23 = new ArrayList<Visit>();
		jul23 = new ArrayList<Visit>();
		aug23 = new ArrayList<Visit>();
		sept23 = new ArrayList<Visit>();
		oct23 = new ArrayList<Visit>();
		
		
	}
	
	public HashMap<String, ArrayList<Visit>> getVisitsByMonth() {
		return visitsByMonth;
	}

	public String getName() {
		return name;
	}
	
	/*public ArrayList<Visit> getVisits() {
		return visits;
	}

	public void setVisits(ArrayList<Visit> visits) {
		this.visits = visits;
	}*/

	@Override
	public String toString() {
		return "Location [name=" + name + ", visits=" + visitsSkip.returnAll() + "]";
	}
	
	
	
	
}

