package contacttracing;
/* Data Structures Advanced (INFT 2042)
 * University of South Australia
 * Assignment 2, 2022
 * Author: Brandon Matthews
 * Author: Daniel Ablett
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Chris Taylor taycr003@mymail.unisa.edu.au
 */
public class Main {
	
	static HashMap<String, Entry> people;
	static HashMap<String, Entry> locations;
	static ActiveCaseList activeCases = new ActiveCaseList();

	/**
	 * entry point of the program
	 * @param args - passed in as path to test.txt
	 */
	public static void main(String[] args) {
		/* Check if a command file is provided as an input argument and quit if not */
		if (args.length < 1) {
			System.out.println("No Test Command File Provided");
			return;
		}

		/* Tools to read command line and text file instructions */
		BufferedReader reader = null;
		Scanner scanner;
		String command;
		String trimmedLine;
		boolean initialized = false;

		/* Open command file and read line */
		try {
			reader = new BufferedReader(new FileReader(args[0]));

			String line = reader.readLine();

			/* Process and read every line */
			while (line != null) {
				/* Clean up any extra spaces etc. */
				trimmedLine = line;
				while (trimmedLine != null && trimmedLine.trim().equals("")) {
					line = reader.readLine();
					trimmedLine = line;
				}

				/* Ignore any empty lines */
				if (line == null) {
					break;
				}

				/* Filter out comments */
				if (line.contains("#")) {
					String noComments;
					noComments = line.substring(0, line.indexOf("#"));
					/*
					 * If there is text before comment, process it, otherwise ignore the line and
					 * move on.
					 */
					if (noComments.length() > 0) {
						line = noComments;
					} else {
						line = reader.readLine();
						continue;
					}
				}

				/* Store the command component */
				command = line.substring(0, line.indexOf(" "));

				/* Read all the arguments into a scanner */
				scanner = new Scanner(line.substring(line.indexOf(" "), line.length()));
				
				if (!command.equals("INIT") && !initialized) {
					System.out.println("Program has not been initialized, skipping commands until first INIT");
					line = reader.readLine();
					continue;
				}

				switch (command) {
				case "INIT": {
					String currentDate = scanner.next();
					System.out.println("Initializing with 'Current Date' = " + currentDate);
					DateHelpers.CURRENT_DATE = DateHelpers.ParseDateString(currentDate);
					initialized = true;
					break;
				}
				case "LOAD_DATA": {
					
					String peoplePath = scanner.next();
					System.out.println("Loading People Data from " + peoplePath);
					people = LoadPeople(peoplePath);
					System.out.println("Found " + people.size + " people.");
					
					String locationPath = scanner.next();
					System.out.println("Loading Location Data from " + locationPath);
					locations = LoadLocations(locationPath);
					System.out.println("Found " + locations.size + " locations.");
					
					String visitPath = scanner.next();
					System.out.println("Loading Visit Data from " + visitPath);
					Visit[] visits = LoadVisits(visitPath);
					
					for (int i = 0; i < visits.length; i++) {
						  
						  Visit v = visits[i];
						 
						  String visitName = v.getPersonName();
						  String visitLocation = v.getLocationName();
						  
						  //break up date to build custom keys for hashmap
						  LocalDate date = v.getDate();
						  String month = String.valueOf(date.getMonthValue());
						  String year = String.valueOf(date.getYear());
						  String visitsByMonthKey = month + "-" + year;
						  
						  Entry personEntry = people.getValue(visitName);
						  Entry locationEntry = locations.getValue(visitLocation);
						  
						  Object p = personEntry.getValue();
						  Object l = locationEntry.getValue();
					
						  if(p instanceof Person) {
							 
							  ((Person) p).visitsSkip.insert(v);
						  }
						  
						  if(l instanceof Location) {
								 
							  //add to all visits skip list
							  ((Location) l).visitsSkip.insert(v);
							  
							  //add to visits by month list within HashMap for quicker searching via smaller lists
							  if(visitsByMonthKey.equals("10-2022")) {
								  ((Location) l).oct22.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).oct22, true);
							  }
							  
							  else if(visitsByMonthKey.equals("11-2022")) {
								  ((Location) l).nov22.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).nov22, true);
							  }
							  
							  else if(visitsByMonthKey.equals("12-2022")) {
								  ((Location) l).dec22.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).dec22, true);
							  }
							  
							  else if(visitsByMonthKey.equals("1-2023")) {
								  ((Location) l).jan23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).jan23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("2-2023")) {
								  ((Location) l).feb23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).feb23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("3-2023")) {
								  ((Location) l).mar23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).mar23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("4-2023")) {
								  ((Location) l).apr23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).apr23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("5-2023")) {
								  ((Location) l).may23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).may23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("6-2023")) {
								  ((Location) l).jun23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).jun23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("7-2023")) {
								  ((Location) l).jul23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).jul23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("8-2023")) {
								  ((Location) l).aug23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).aug23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("9-2023")) {
								  ((Location) l).sept23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).sept23, true);
							  }
							  
							  else if(visitsByMonthKey.equals("10-2023")) {
								  ((Location) l).oct23.add(v);
								  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).oct23, true);
							  }
							  
							  else {
								  System.out.println("Visit date is further than one year into the future from software launch!");
								  System.out.println("Visit added to all visits list but not monthly list!");
							  }
							  
							  
							 
						  }
						  
						 
						  
						  
						}
					
			 
					break;
				}
				case "ADD_PERSON": {
					
					String addPersonName = scanner.next();
					System.out.println("Adding new Person: " + addPersonName);
					String activeStartDate = scanner.hasNext() ? scanner.next() : null;
					String activeEndDate = scanner.hasNext() ? scanner.next() : null;
					
					LocalDate parsedStart = null;
					LocalDate parsedEnd = null;
					
					if(activeStartDate != null) {
						parsedStart = DateHelpers.ParseDateString(activeStartDate);
					}
					
					if(activeEndDate != null) {
						parsedEnd = DateHelpers.ParseDateString(activeEndDate);
					}
					
					Person p = new Person(addPersonName, parsedStart, parsedEnd);
					Entry e = new Entry(addPersonName, p, null);
					
					if(parsedEnd != null) {
						if(parsedEnd.isAfter(DateHelpers.CURRENT_DATE)){
							//add person to 'active cases' linked list
				        	activeCases.addActivePerson(addPersonName, parsedStart, parsedEnd);
				        	activeCases.size++;
				        	System.out.println("new person added as an active case");
				        }
					}
					
					//add person to 'people' hashmap
					if(people.getEntry(addPersonName) == null) {
						people.put(addPersonName, e, true); //DO increment size of hash map
						System.out.println("NEW PERSON ADDED: " + addPersonName);
					}
					
					else {
						
						System.out.println("PERSON ALREDY EXISTS - NOT OVERRIDING EXISTING ENTRY: " + addPersonName);
					}
					
					
					break;
				}
				case "ADD_LOCATION": {
					
					String addLocationName = scanner.next();
					System.out.println("Adding new Location: " + addLocationName);

					Location l = new Location(addLocationName);
					Entry e = new Entry(addLocationName, l, null);
					
					//add location to 'locations' hashmap
					if(locations.getEntry(addLocationName) == null) {
						locations.put(addLocationName, e, true); //DO increment size of hash map
						System.out.println("NEW LOCATION ADDED: " + addLocationName);
					}
					
					else {
						System.out.println("LOCATION ALREDY EXISTS - NOT OVERRIDING EXISTING ENTRY: " + addLocationName);
					}
					
					
					break;
				}
				case "ADD_VISIT": {
					
					String visitPerson = scanner.next();
					String visitLocation = scanner.next();
					
					System.out.println("Adding Visit by: " + visitPerson + ", to: " + visitLocation);
					
					String visitDate = scanner.next();
					String visitStartTime = scanner.next();
					String visitEndTime = scanner.next();
					
					LocalDate parsedDate = null;
					LocalTime parsedStartTime = null;
					LocalTime parsedEndTime = null;
					
					if(visitDate != null) {
						parsedDate = DateHelpers.ParseDateString(visitDate);
					}
					
					if(visitStartTime != null) {
						parsedStartTime = DateHelpers.ParseTimeString(visitStartTime);
					}
					
					if(visitEndTime != null) {
						parsedEndTime = DateHelpers.ParseTimeString(visitEndTime);
					}
					
					//create new visit object
					Visit v = new Visit(visitPerson, visitLocation, parsedDate, parsedStartTime, parsedEndTime);
					
			
					//break up date to build custom keys for hashmap
					LocalDate date = v.getDate();
					String month = String.valueOf(date.getMonthValue());
					String year = String.valueOf(date.getYear());
					String visitsByMonthKey = month + "-" + year;
					
					Entry personEntry = people.getValue(visitPerson);
					Entry locationEntry = locations.getValue(visitLocation);
					  
					Object p = personEntry.getValue();
					Object l = locationEntry.getValue();
				
					  if(p instanceof Person) {
						  
						  //INSERT INTO NEW SKIPLIST
						  ((Person) p).visitsSkip.insert(v);
						  System.out.println("VISIT ADDED TO PERSON: " + visitPerson + ", to: " + visitLocation);
						 
					  
					  }
					  
					  if(l instanceof Location) {
						
						  //add to all visits skip list 
						  ((Location) l).visitsSkip.insert(v);
						  
						  //add to visits by month list for very fast searching to month of location
						  if(visitsByMonthKey.equals("10-2022")) {
							  ((Location) l).oct22.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).oct22, true);
						  }
						  
						  else if(visitsByMonthKey.equals("11-2022")) {
							  ((Location) l).nov22.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).nov22, true);
						  }
						  
						  else if(visitsByMonthKey.equals("12-2022")) {
							  ((Location) l).dec22.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).dec22, true);
						  }
						  
						  else if(visitsByMonthKey.equals("1-2023")) {
							  ((Location) l).jan23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).jan23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("2-2023")) {
							  ((Location) l).feb23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).feb23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("3-2023")) {
							  ((Location) l).mar23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).mar23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("4-2023")) {
							  ((Location) l).apr23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).apr23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("5-2023")) {
							  ((Location) l).may23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).may23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("6-2023")) {
							  ((Location) l).jun23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).jun23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("7-2023")) {
							  ((Location) l).jul23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).jul23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("8-2023")) {
							  ((Location) l).aug23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).aug23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("9-2023")) {
							  ((Location) l).sept23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).sept23, true);
						  }
						  
						  else if(visitsByMonthKey.equals("10-2023")) {
							  ((Location) l).oct23.add(v);
							  ((Location) l).visitsByMonth.put(visitsByMonthKey, ((Location) l).oct23, true);
						  }
						  
						  else {
							  System.out.println("Visit date is further than one year into the future!");
							  System.out.println("Visit added to all visits list but not monthly list!");
						  }
						  
						  
						  System.out.println("VISIT ADDED TO LOCATION: " + visitPerson + ", to: " + visitLocation);
						 
					  
					  }
					
					  break;
				}
				
				case "GET_PERSON": {
					
					String getPersonName = scanner.next();
					System.out.println("Getting Person: " + getPersonName);
					
					String startDate = scanner.hasNext() ? scanner.next() : null;
					String endDate = scanner.hasNext() ? scanner.next() : null;
					LocalDate parsedStart = null;
					LocalDate parsedEnd = null;
					
					if(startDate != null) {
						parsedStart = DateHelpers.ParseDateString(startDate);
					}
					
					if(endDate != null) {
						parsedEnd = DateHelpers.ParseDateString(endDate);
					}
					
					Entry e = people.getValue(getPersonName);
					
					if(e != null) {
					
						Object p = e.getValue();
						String name = " ";
						ArrayList<Visit> v = new ArrayList<Visit>();
					
						  if(p instanceof Person) {
							 
							  name = ((Person) p).getName();
							  SkipList visitsSkip = ((Person) p).visitsSkip;
							  
							  if(startDate != null && endDate != null) {
								  
								  if(!parsedStart.isBefore(parsedEnd)) {
									  System.out.print("Invalid date range");
								  }
								  
								  v = visitsSkip.searchInRange(parsedStart, parsedEnd);
								  System.out.println("FOUND " + name + " | VISITS BETWEEN " + parsedStart + " AND " + parsedEnd + v.toString());
								  
							  }
							  
							  else {
								  v = visitsSkip.returnAll();
								  System.out.println("FOUND " + name + " | VISITS: " + v.toString());
							  }
						  
						  }
						
					}
					
					else {
						System.out.println(getPersonName + " is not found");
					}
					
					break;
				}
				
				case "GET_LOCATION": {
					
					String getLocationName = scanner.next();
					System.out.println("Getting Location: " + getLocationName);

					String startDate = scanner.hasNext() ? scanner.next() : null;
					String endDate = scanner.hasNext() ? scanner.next() : null;

					LocalDate parsedStart = null;
					LocalDate parsedEnd = null;
					
					if(startDate != null) {
						parsedStart = DateHelpers.ParseDateString(startDate);
					}
					
					if(endDate != null) {
						parsedEnd = DateHelpers.ParseDateString(endDate);
					}
					
					Entry e = locations.getValue(getLocationName);
					
					if(e != null) {
						
						Object l = e.getValue();
						String locationName = " ";
						ArrayList<Visit> v = new ArrayList<Visit>();
					
						  if(l instanceof Location) {
							 
							  locationName = ((Location) l).getName();
							  SkipList visitsSkip = ((Location) l).visitsSkip;
							  
							  if(startDate != null && endDate != null) {
								  
								  if(!parsedStart.isBefore(parsedEnd)) {
									  System.out.print("Invalid date range");
								  }
								  
								  v = visitsSkip.searchInRange(parsedStart, parsedEnd);
								  System.out.println("FOUND " + locationName + " | VISITS BETWEEN " + parsedStart + " AND " + parsedEnd + v.toString());
								  
								 
							  }
							  
							  else {
								  v = visitsSkip.returnAll();
								  System.out.println("FOUND " + locationName + " | VISITS: " + v.toString());
							  }
						  
						  }
						
						  
					}
					
					else {
						System.out.println(getLocationName + " is not found");
					}
					
					break;
				}
				case "LIST_CONTACTS": {
					
					String contactPersonName = scanner.next();
					int n = Integer.parseInt(scanner.next());
					
					System.out.println("Listing all contacts with: " + contactPersonName + ", within " + n + " levels.");
					
					Queue<String> queue = new LinkedList<String> (); 
					HashMap<String, String> namesSeen = new HashMap<String, String>(0.75);
					queue.add(contactPersonName);
					namesSeen.put(contactPersonName, contactPersonName, true);
					
					int levelCounter = 1;
					
					for(int numLevels = n; numLevels >= 1; numLevels--) {
						
						String nextPerson = queue.poll();
						
						Entry e = people.getValue(nextPerson);
						
						if(e != null) {
							
							Object p = e.getValue();
							ArrayList<Visit> allVisits = null; 
							ArrayList<String> visitedLocations = new ArrayList<String>();
						
							//determine all locations the person testing positive has been to 
							if(p instanceof Person) {
								 
								  allVisits = ((Person) p).visitsSkip.returnAll();
								  
								  int allVisitsSize = allVisits.size();
								  
								  for (int i = 0; i < allVisitsSize; i++) {
									  
									  	//build up string so we can target correct location and potential matches 
									  	String name = allVisits.get(i).getLocationName();
									  	LocalDate localDate = allVisits.get(i).getDate();
									  	String date = String.valueOf(localDate);
									  	String entryTime = String.valueOf(allVisits.get(i).getEntryTime());
									  	String exitTime = String.valueOf(allVisits.get(i).getExitTime());
									  	visitedLocations.add(name + " " + date + " " + entryTime + " " + exitTime);
								         
									  
								    	  
								      }   
								  }
							
							
							int visitedLocationsSize = visitedLocations.size();
							  
							  //loop through all locations the person has been to
							  for (int i = 0; i < visitedLocationsSize; i++) {
								  
								  //build up data we need to find mutual visits
								  String locationNameAndTime = visitedLocations.get(i);
								  String[] arr = locationNameAndTime.split(" ");
								  String locationName = arr[0];
								  String locationDate = arr[1];
								  String locationEntryTime = arr[2];
								  String locationExitTime = arr[3];
								  String locationVisitYear = locationDate.substring(0,4);
								  String locationVisitMonth = locationDate.substring(5,7);
								  Entry locationEntry = locations.getValue(locationName);
								  Object l = locationEntry.getValue();
								  
								  String visitsByMonthKey = locationVisitMonth + "-" + locationVisitYear;
								  
								  if(l instanceof Location) {
									  
									  ArrayList locationVisits = ((Location) l).visitsSkip.returnAll();
									  HashMap h = ((Location) l).visitsByMonth;
									  Object v = h.getValue(visitsByMonthKey);
									  
									  if(v != null) {
										  
										  if(v instanceof ArrayList) { //list of this months visits
											  
											  int size = ((ArrayList) v).size();
											  
											  for (int counter = 0; counter < size; counter++) {
										        	
												  Visit visit = (Visit) ((ArrayList) v).get(counter);
												  String name = visit.getPersonName();
												  
												  Entry pers = people.getValue(name);
												  Object persO = pers.getValue();
												  
												  String date = String.valueOf(visit.getDate());
												  LocalTime oldCaseEntryTime = visit.getEntryTime();
												  LocalTime oldCaseExitTime = visit.getExitTime();
												 
												  
												  // contact can not be made with self or on date person was not at location
												  if(!contactPersonName.equals(name) && date.equals(locationDate)) { 
													  	
													  LocalTime parsedNewCaseEntryTime = DateHelpers.ParseTimeString(locationEntryTime);
													  LocalTime parsedNewCaseExitTime = DateHelpers.ParseTimeString(locationExitTime);
													  
													  //check for any overlap
													  if(parsedNewCaseEntryTime.isBefore(oldCaseExitTime) && parsedNewCaseExitTime.isAfter(oldCaseEntryTime)) {
														  
														  LocalTime laterStart;
														  LocalTime earlierExit;
														  boolean latestTimeStartingTogethor = parsedNewCaseEntryTime.isBefore(oldCaseEntryTime);
														  
														  if(latestTimeStartingTogethor) {
															  laterStart = oldCaseEntryTime;
														  }
														  
														  else {
															  laterStart = parsedNewCaseEntryTime;
																	  
														  }
														  
														  boolean earliestTimeLeaving = parsedNewCaseExitTime.isBefore(oldCaseExitTime);
														  
														  if(earliestTimeLeaving) {
															  earlierExit = parsedNewCaseExitTime;
														  }
														  
														  else {
															  earlierExit = oldCaseExitTime;
														  }
														  
														  //calc minutes they were together 
														  long elapsedMinutes = Duration.between(laterStart, earlierExit).toMinutes();
														  
														  
														  if(elapsedMinutes > 0) {
															  
															  String hasTraveresed = namesSeen.getValue(name);
															  
															  if(hasTraveresed == null) {
																  queue.add(name);
																  namesSeen.put(name, name, true);
																  System.out.println(name + " was a level " + levelCounter + " close contact");
															  }
															  
															 
														  }
	
													  
													 }
													  
												  } 
												  
												  
												  
											  }
											  
										  }
										  
										  
									  }
									  
								  }
										  
							  }
						}
						
						levelCounter++;
					
					} //close numLevels loop
					
					break;
				}
				case "NEW_CASE": {
					
					String newCasePersonName = scanner.next();

					System.out.println("Adding new case to: " + newCasePersonName);
					
					Entry e = people.getValue(newCasePersonName);
					
					if(e != null) {
						
						Object p = e.getValue();
						ArrayList<Visit> allVisits = null; 
						ArrayList<String> visitedLocations = new ArrayList<String>();
					
						//determine all locations the person testing positive has been to 
						if(p instanceof Person) {
							 
							  //allVisits = ((Person) p).getVisits();
							  allVisits = ((Person) p).visitsSkip.returnAll();
							  
							  int allVisitsSize = allVisits.size();
							  
							  for (int i = 0; i < allVisitsSize; i++) {
								  
								  	//build up string so we can target correct location and potential matches 
								  	String name = allVisits.get(i).getLocationName();
								  	String date = String.valueOf(allVisits.get(i).getDate());
								  	String entryTime = String.valueOf(allVisits.get(i).getEntryTime());
								  	String exitTime = String.valueOf(allVisits.get(i).getExitTime());
								  	visitedLocations.add(name + " " + date + " " + entryTime + " " + exitTime);
							          
							    	  
							      }   
							  }
						  
						 
						  //get key so we can search through hashmap of current month
						  LocalDate dateTestedPositive = DateHelpers.CURRENT_DATE;
						  int monthsToSearch = 1;
						  
						  int dayOfMonth = dateTestedPositive.getDayOfMonth();
						  
						  //people are generally sick for 9 days, if date of new case is early in current month we may need to search previous month as well
						  //if 100% chance of transmission is not already determined
						  if(dayOfMonth < DateHelpers.DAYS_CASE_IS_SICK) {
							  monthsToSearch = 2;
						  }
						  
						  int monthInt = dateTestedPositive.getMonthValue();
						  int yearInt = dateTestedPositive.getYear();
						  int secondMonthToSearchInt = 0;
						  int secondYearToSearchInt = 0;
						  
						  //we need to search an additional month 
						  if(monthsToSearch == 2) {
							  
							  //and it's not January
							  if(monthInt != 1) {
								  secondMonthToSearchInt = monthInt - 1;
								  secondYearToSearchInt = yearInt;
							  }
							  
							  //it is January - we must search December of previous year
							  else {
								  secondMonthToSearchInt = 12;
								  secondYearToSearchInt = yearInt - 1;
							  }
							  
						  }
						  
						  String month = String.valueOf(monthInt);
						  String year = String.valueOf(yearInt);
						  String visitsByMonthKey = month + "-" + year;
						  String secondVisitsByMonthKey = "";
						  
						  if(monthsToSearch == 2) {
							  
							  String secondMonth = String.valueOf(secondMonthToSearchInt);
							  String secondYear = String.valueOf(secondYearToSearchInt);
							  secondVisitsByMonthKey = secondMonth + "-" + secondYear;
							  
						  }
						  
						  int visitedLocationsSize = visitedLocations.size();
						  
						  //initialize probable old case that new case caught virus from
						  int highestSpreadScore = 0;
						  String highestSpreadPerson = null;
						  LocalDate newCaseActiveStartDate = DateHelpers.CURRENT_DATE;
						  
						  //loop through all locations the person has been to
						  for (int i = 0; i < visitedLocationsSize; i++) {
							  
							  if(highestSpreadScore == 100) {
								  //we have found person with 100% source of transmission - exit loop
								  break;
							  }
							  
							  //build up data we need to find likely infection source
							  String locationNameAndTime = visitedLocations.get(i);
							  String[] arr = locationNameAndTime.split(" ");
							  String locationName = arr[0];
							  String locationDate = arr[1];
							  String locationEntryTime = arr[2];
							  String locationExitTime = arr[3];
							  Entry locationEntry = locations.getValue(locationName);
							  Object l = locationEntry.getValue();
							  
							  //this loop will run 1 or 2 times - depending on number of months required to search
							  for (int monthCounter = 1; monthCounter == monthsToSearch; monthCounter++) {
								  
								  if(l instanceof Location) {
									  
									  //ArrayList locationVisits = ((Location) l).getVisits();
									  ArrayList locationVisits = ((Location) l).visitsSkip.returnAll();
									  HashMap h = ((Location) l).visitsByMonth;
									  Object v = null;
									  
									  if(monthCounter == 1) {
										  v = h.getValue(visitsByMonthKey);
										 
									  }
									  
									  else { //searching second month if second iteration of loop is performed
										  v = h.getValue(secondVisitsByMonthKey);
							
									  }
									  
									  if(v != null) {
										  
										  if(v instanceof ArrayList) { //list of this months visits
											  
											  //sort the current months visits once to reduce length of loop based on comparing dates of visits
											  //one off sort of one locations visits for a month good trade off for loop savings of divide and conquer
											  Collections.sort((ArrayList)v);
											  int counter;
											  
											  int size;
											  int listSize = ((ArrayList) v).size();
											  int midPoint = listSize / 2;
											  Visit midVisit = (Visit)((ArrayList) v).get(midPoint);
											  LocalDate midVisitDate = midVisit.getDate();
											  
											  boolean midListIsAfter = midVisitDate.isAfter(dateTestedPositive);
											  
											  //just search first half of list during first month searching  
											  if(monthCounter == 1 && midListIsAfter) {
												  size = midPoint + 1;
												  counter = 0;
											  }
											  
											 //just search second half of list during first month searching  
											  else if(monthCounter == 1 && !midListIsAfter) {
												  size = listSize;
												  counter = midPoint;
											  }
											  
											  //during second month search cut out first half of list if possible (likely 
											  //as case was only sick for the last 8 days of this month or fewer)
											  else if(monthCounter == 2 && !midListIsAfter) { 
												  
												  counter = midPoint;
												  size = listSize;
											  }
											  
											  else { //catch all = loop through all 
												  size = listSize;
												  counter = 0;
											  }
											  
											  
											  for (int loopCounter = counter; loopCounter < size; loopCounter++) {
										        	
												  Visit visit = (Visit) ((ArrayList) v).get(loopCounter);
												  String name = visit.getPersonName();
												  
												  Entry pers = people.getValue(name);
												  Object persO = pers.getValue();
												  
												  boolean visitWasSick = false;
												  
												  //determine is person was sick at time of visit
												  if(persO instanceof Person) {
												  
													  visitWasSick = ((Person)persO).activeOnDate(dateTestedPositive);
													  
												  }
													 
												  
												  if(visitWasSick) {
												  
												 
													  String date = String.valueOf(visit.getDate());
													  LocalTime oldCaseEntryTime = visit.getEntryTime();
													  LocalTime oldCaseExitTime = visit.getExitTime();
													  
													  // virus cannot be caught from self or on date person was not at location
													  if(!newCasePersonName.equals(name) && date.equals(locationDate)) { 
														  	
														  LocalTime parsedNewCaseEntryTime = DateHelpers.ParseTimeString(locationEntryTime);
														  LocalTime parsedNewCaseExitTime = DateHelpers.ParseTimeString(locationExitTime);
														  
														  //check for any overlap
														  if(parsedNewCaseEntryTime.isBefore(oldCaseExitTime) && parsedNewCaseExitTime.isAfter(oldCaseEntryTime)) {
															  
															  LocalTime laterStart;
															  LocalTime earlierExit;
															  boolean latestTimeStartingTogethor = parsedNewCaseEntryTime.isBefore(oldCaseEntryTime);
															  
															  if(latestTimeStartingTogethor) {
																  laterStart = oldCaseEntryTime;
															  }
															  
															  else {
																  laterStart = parsedNewCaseEntryTime;
																		  
															  }
															  
															  boolean earliestTimeLeaving = parsedNewCaseExitTime.isBefore(oldCaseExitTime);
															  
															  if(earliestTimeLeaving) {
																  earlierExit = parsedNewCaseExitTime;
															  }
															  
															  else {
																  earlierExit = oldCaseExitTime;
															  }
															  
															  //calc minutes they were together 
															  long elapsedMinutes = Duration.between(laterStart, earlierExit).toMinutes();
															  
															  //calc chance of spread from the old case with mutual visit
															  int chanceOfSpread = (int) ((elapsedMinutes * 100) / 60);
															  
															  //probability cannot be greater than 100 
															  if(chanceOfSpread > 100) {
																  chanceOfSpread = 100;
															  }
															  
															  if(chanceOfSpread > highestSpreadScore) {
																  
																  highestSpreadScore = chanceOfSpread;
																  highestSpreadPerson = name;
																  newCaseActiveStartDate = visit.getDate().plusDays(1);
																  
															  }
		
														  
															
														  
														  }
														  
													  } 
													
												  } //close if visit is sick
												  
												 
										        
											  } //close loop through arraylist of respective location visits
										           
										           
											  
										  }
										  
									  }
									  
								  } // close monthCounter loop 
									  
									  
								}
							  
						  
						  } //close loop through all locations that new case visited
						  
						  Object person = people.getValue(newCasePersonName);
						  ((Person) p).setActiveStartDate(newCaseActiveStartDate);
						  activeCases.addActivePerson(newCasePersonName, newCaseActiveStartDate, null);
						  activeCases.size++;
						  
						  if(highestSpreadPerson != null) {
							  
							  System.out.println(highestSpreadScore + "% chance " +  newCasePersonName + " caught the virus from "+ highestSpreadPerson);
							  
						  }
						  
						  else {
							  
							  System.out.println("Probable transmission from existing case cannot be determined");
							  
						  }
						  
						  
						}
					
					
					else {
						System.out.println("Person not found!: " + newCasePersonName);
					}
			
					
					break;
				}
				case "TRACE_PATH": {
					
					String pathTargetName = scanner.next();
					System.out.println("Tracing infection path for: " + pathTargetName);
					System.out.println("------------------------------------------------");
					
					//put person in queue, take out of queue, put highest prob of transmission person in queue, take out of queue, repeat
					Queue<String> queue = new LinkedList<String> (); 
					ArrayList<String> prev = new ArrayList<String>();
					queue.add(pathTargetName);
					prev.add(pathTargetName);
					
					while(!queue.isEmpty()) {
					
						Entry e = people.getValue(queue.poll());
						
						if(e != null) {
							
							Object p = e.getValue();
							ArrayList<Visit> allVisits = null; 
							ArrayList<String> visitedLocations = new ArrayList<String>();
							String currentPathTargetName = "";
							
							LocalDate dateTestedPositive = null;
						
							//determine all locations the person testing positive has been to the DAY BEFORE testing positive
							if(p instanceof Person) {
								 
								  //allVisits = ((Person) p).getVisits();
								  allVisits = ((Person) p).visitsSkip.returnAll();
								  dateTestedPositive = ((Person) p).getActiveStartDate();
								  int allVisitsSize = allVisits.size();
								  currentPathTargetName = ((Person) p).getName();
								  
								  for (int i = 0; i < allVisitsSize; i++) {
									  
									  	//build up string so we can target correct location and potential matches 
									  	String name = allVisits.get(i).getLocationName();
									  	LocalDate visitDate = allVisits.get(i).getDate();
									  	String date = String.valueOf(visitDate);
									  	String entryTime = String.valueOf(allVisits.get(i).getEntryTime());
									  	String exitTime = String.valueOf(allVisits.get(i).getExitTime());
									  	
									  	//add to list of locations to search only if location was visited on day before testing positive
									  	if(dateTestedPositive.minusDays(1).equals(visitDate)) {
									  		visitedLocations.add(name + " " + date + " " + entryTime + " " + exitTime);
									  	}
									  
								  	}   
								  }
							
							  int monthsToSearch = 1;
							  
							  int dayOfMonth = dateTestedPositive.getDayOfMonth();
							  
							  //if it's the first day of month then we need to check prev month as well
							  if(dayOfMonth == 1) {
								  monthsToSearch = 2;
							  }
							  
							  int monthInt = dateTestedPositive.getMonthValue();
							  int yearInt = dateTestedPositive.getYear();
							  int secondMonthToSearchInt = 0;
							  int secondYearToSearchInt = 0;
							  
							  //we need to search an additional month 
							  if(monthsToSearch == 2) {
								  
								  //and it's not January
								  if(monthInt != 1) {
									  secondMonthToSearchInt = monthInt - 1;
									  secondYearToSearchInt = yearInt;
								  }
								  
								  //it is January - we must search December of previous year
								  else {
									  secondMonthToSearchInt = 12;
									  secondYearToSearchInt = yearInt - 1;
								  }
							  
							  }
							  
							  String month = String.valueOf(monthInt);
							  String year = String.valueOf(yearInt);
							  String visitsByMonthKey = month + "-" + year;
							  String secondVisitsByMonthKey = "";
							  
							  if(monthsToSearch == 2) {
								  
								  String secondMonth = String.valueOf(secondMonthToSearchInt);
								  String secondYear = String.valueOf(secondYearToSearchInt);
								  secondVisitsByMonthKey = secondMonth + "-" + secondYear;
								  
							  }
							  
							  int visitedLocationsSize = visitedLocations.size();
							  
							  //initialize probable old case that new case caught virus from
							  int highestSpreadScore = 0;
							  String highestSpreadPerson = null;
							  
							  //loop through all locations the person has been to
							  for (int i = 0; i < visitedLocationsSize; i++) {
								  
								  if(highestSpreadScore == 100) {
									  //we have found person with 100% source of transmission - exit loop
									  boolean alreadySearchedFlag = false;
									  int prevSize = prev.size();
									  
									  for (int counter = 0; counter < prevSize; counter++) { 		      
								          if(prev.get(counter).equals(highestSpreadPerson)) {
								        	  alreadySearchedFlag = true;
								        	  break;
								          }		
								      } 
									  
									  if(!alreadySearchedFlag) {
										  queue.add(highestSpreadPerson);
									  }
									  
									  break;
								  }
								  
								  //build up data we need to find likely infection source
								  String locationNameAndTime = visitedLocations.get(i);
								  String[] arr = locationNameAndTime.split(" ");
								  String locationName = arr[0];
								  String locationDate = arr[1];
								  String locationEntryTime = arr[2];
								  String locationExitTime = arr[3];
								  Entry locationEntry = locations.getValue(locationName);
								  Object l = locationEntry.getValue();
								  
								  //this loop will run 1 or 2 times - depending on number of months required to search
								  for (int monthCounter = 1; monthCounter == monthsToSearch; monthCounter++) {
									  
								  
									  if(l instanceof Location) {
										  
										  //ArrayList locationVisits = ((Location) l).getVisits();
										  ArrayList locationVisits = ((Location) l).visitsSkip.returnAll();
										  HashMap h = ((Location) l).visitsByMonth;
										  Object v = null;
										  
										  if(monthCounter == 1) {
											  v = h.getValue(visitsByMonthKey);
										  }
										  
										  else {
											  v = h.getValue(secondVisitsByMonthKey);
								
										  }
										  
										  if(v != null) {
											  
											  if(v instanceof ArrayList) { //list of this months visits
												  
												  int size = ((ArrayList) v).size();
												  
												  for (int counter = 0; counter < size; counter++) {
											        	
													  Visit visit = (Visit) ((ArrayList) v).get(counter);
													  String name = visit.getPersonName();
													  
													  Entry pers = people.getValue(name);
													  Object persO = pers.getValue();
													  
													  boolean visitWasSick = false;
													  
													  //determine is person was sick at time of visit
													  if(persO instanceof Person) {
													  
														  visitWasSick = ((Person)persO).activeOnDate(dateTestedPositive);
														  
													  }
														 
													  
													  if(visitWasSick) {
													  
													 
														  String date = String.valueOf(visit.getDate());
														  LocalTime oldCaseEntryTime = visit.getEntryTime();
														  LocalTime oldCaseExitTime = visit.getExitTime();
														  
														  // virus cannot be caught from self or on date person was not at location
														  if(!currentPathTargetName.equals(name) && date.equals(locationDate)) { 
															  	
															  LocalTime parsedNewCaseEntryTime = DateHelpers.ParseTimeString(locationEntryTime);
															  LocalTime parsedNewCaseExitTime = DateHelpers.ParseTimeString(locationExitTime);
															  
															  //check for any overlap
															  if(parsedNewCaseEntryTime.isBefore(oldCaseExitTime) && parsedNewCaseExitTime.isAfter(oldCaseEntryTime)) {
																  
																  LocalTime laterStart;
																  LocalTime earlierExit;
																  boolean latestTimeStartingTogethor = parsedNewCaseEntryTime.isBefore(oldCaseEntryTime);
																  
																  if(latestTimeStartingTogethor) {
																	  laterStart = oldCaseEntryTime;
																  }
																  
																  else {
																	  laterStart = parsedNewCaseEntryTime;
																			  
																  }
																  
																  boolean earliestTimeLeaving = parsedNewCaseExitTime.isBefore(oldCaseExitTime);
																  
																  if(earliestTimeLeaving) {
																	  earlierExit = parsedNewCaseExitTime;
																  }
																  
																  else {
																	  earlierExit = oldCaseExitTime;
																  }
																  
																  //calc minutes they were together 
																  long elapsedMinutes = Duration.between(laterStart, earlierExit).toMinutes();
																  
																  //calc chance of spread from the old case with mutual visit
																  int chanceOfSpread = (int) ((elapsedMinutes * 100) / 60);
																  
																  //probability cannot be greater than 100 
																  if(chanceOfSpread > 100) {
																	  chanceOfSpread = 100;
																  }
																  
																  if(chanceOfSpread > highestSpreadScore) {
																	  
																	  highestSpreadScore = chanceOfSpread;
																	  highestSpreadPerson = name;
																	  
																  }
			
															  
																
															  
															  }
															  
														  } 
														
													  } //close if visit is sick
													  
													 
											        
												  } //close loop through arraylist of respective location visits
											           
											           
												  
											  }
											  
										  }
										  
									  } // close monthCounter loop 
										  
										  
									}
								  
							  
							  } //close loop through all locations that new case visited
							  
							  if(highestSpreadPerson != null) {
								  
								  boolean alreadySearchedFlag = false;
								  int prevSize = prev.size();
								  
								  for (int counter = 0; counter < prevSize; counter++) { 		      
							          if(prev.get(counter).equals(highestSpreadPerson)) {
							        	  alreadySearchedFlag = true;
							        	  break;
							          }		
							      } 
								  
								  if(!alreadySearchedFlag) {
								  
									  queue.add(highestSpreadPerson);
									  System.out.println(highestSpreadScore + "% chance " +  currentPathTargetName + " caught the virus from "+ highestSpreadPerson);
									  System.out.println("------------------------------------------------");
									  prev.add(highestSpreadPerson);
								  }
								  
							  }
							  
							  else {
								  
								 
								  break;
								  
							  }
							
							
						}
						
						else {
							System.out.println("Person not found!: " + pathTargetName);
						}
						
						
						
					} // close while queue is not empty loop
					
					 System.out.println("Further transmission from current case in path cannot be determined");
					 System.out.println("------------------------------------------------");
					break;
				}
				case "CURRENTLY_ACTIVE": {
					
					String activeTargetName = scanner.next();
					System.out.println("Listing currently infected people...");
					activeCases.display();
					
					
					break;
				}
				default:
					System.out.println("Invalid Command " + command);
				}
				
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: File could not be found: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error: Unexpected IO exception encountered");
		}
		
		
		
	}
	
	/**
	 * load all people from txt file into hashmap
	 * @param String a path to the csv
	 * @return HashMap
	 */
	private static HashMap LoadPeople(String peoplePath) {
		
		BufferedReader csvReader;
		HashMap<String, Entry> peopleMap = new HashMap<String, Entry>(0.75);

		try {
			
			csvReader = new BufferedReader(new FileReader(peoplePath));

			String row = csvReader.readLine();

			while (row != null) {
				String[] data = row.split(",");

				if (data == null || data.length < 1) {
					row = csvReader.readLine();
					continue;
				}

				String name = data[0];

				Person person = new Person(name, null, null);
				
				if (data.length > 1) {
					
					person.setActiveStartDate(DateHelpers.ParseDateString(data[1]));
					
				}

				if (data.length > 2) {
					
					LocalDate localEndDate = DateHelpers.ParseDateString(data[2]);
					
					person.setActiveEndDate(localEndDate);
					
					if(localEndDate.isAfter(DateHelpers.CURRENT_DATE)){
							
				        	activeCases.addActivePerson(name, localEndDate, localEndDate);
				        	activeCases.size++;
				        }
					
				}
				
				
				Entry entry = new Entry(name, person, null);
				
				
				peopleMap.put(name, entry, true);

				row = csvReader.readLine();
			}

			csvReader.close();

			
			return peopleMap;
		} catch (FileNotFoundException e) {
			
			System.out.println("Error: File could not be found: " + e.getMessage());
			
			return peopleMap;
		
		} catch (IOException e) {
			
			System.out.println("Error: Unexpected IO exception encountered");
			
			return peopleMap;
		}
	}
	
	/**
	 * load all people from txt file into a temp array - from this array
	 * they will be loaded into a hashmap 
	 * @param String a path to the csv
	 * @return array
	 */
	private static Visit[] LoadVisits(String visitPath) {
		
		BufferedReader csvReader;

		Visit[] visitArray = new Visit[0];

		try {
			ArrayList<Visit> visits = new ArrayList<Visit>();
			csvReader = new BufferedReader(new FileReader(visitPath));

			String row = csvReader.readLine();

			while (row != null) {
				String[] data = row.split(",");

				if (data == null || data.length < 1) {
					row = csvReader.readLine();
					continue;
				}

				String personName = data[0];
				String locationName = data[1];

				LocalDate date = DateHelpers.ParseDateString(data[2]);
				LocalTime entryTime = DateHelpers.ParseTimeString(data[3]);
				LocalTime exitTime = DateHelpers.ParseTimeString(data[4]);

				Visit visit = new Visit(personName, locationName, date, entryTime, exitTime);
				
				visits.add(visit);
				

				row = csvReader.readLine();
			}

			csvReader.close();

			visitArray = new Visit[visits.size()];
			visits.toArray(visitArray);
			
			
			
			return visitArray;
		} catch (FileNotFoundException e) {
			System.out.println("Error: File could not be found: " + e.getMessage());
			return visitArray;
		} catch (IOException e) {
			System.out.println("Error: Unexpected IO exception encountered");
			return visitArray;
		}
	}

	/**
	 * load all locations from txt file into hashmap
	 * @param String a path to the csv
	 * @return HashMap
	 */
	private static HashMap LoadLocations(String locationPath) {
		
		BufferedReader csvReader;
		HashMap<String, Entry> locationMap = new HashMap<String, Entry>(0.75);

		try {
			
			csvReader = new BufferedReader(new FileReader(locationPath));

			String row = csvReader.readLine();

			while (row != null) {
				String[] data = row.split(",");

				if (data == null || data.length < 1) {
					row = csvReader.readLine();
					continue;
				}

				Location location = new Location(data[0]);
				
				Entry entry = new Entry(data[0], location, null);
				
				locationMap.put(data[0], entry, true);
				

				row = csvReader.readLine();
			}

			csvReader.close();

			return locationMap;
		
		} catch (FileNotFoundException e) {
			
			System.out.println("Error: File could not be found: " + e.getMessage());
			return locationMap;
		
		} catch (IOException e) {
			System.out.println("Error: Unexpected IO exception encountered");
			return locationMap;
		}
	}
	
	

	
}

