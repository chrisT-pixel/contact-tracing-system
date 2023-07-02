package contacttracing;

import java.time.LocalDate;

/**
 * @author Chris Taylor taycr003@mymail.unisa.edu.au
 * represents a person currently infected with the virus
 */
public class ActivePerson extends Person {
	
	ActivePerson next; 

	public ActivePerson(String name, LocalDate activeStartDate, LocalDate activeEndDate) {
		
		super(name, activeStartDate, activeEndDate);
		this.next = null;
		
	}

}
