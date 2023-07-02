package contacttracing;

import java.time.LocalDate;

/**
 * @author Chris Taylor taycr003@mymail.unisa.edu.au
 * list to display all active cases
 */
public class ActiveCaseList {
	
	//Represent the head and tail of the singly linked list of active cases
    public ActivePerson head = null;    
    public ActivePerson tail = null;   
    protected int size;
          
    public void addActivePerson(String name, LocalDate activeStartDate, LocalDate activeEndDate) {    
           
        ActivePerson newActivePerson = new ActivePerson(name, activeStartDate, activeEndDate);    
            
       
        if(head == null) {    
              
            head = newActivePerson;    
            tail = newActivePerson;    
        }    
        else {    
                
            tail.next = newActivePerson;      
            tail = newActivePerson;    
        }    
    }    
    
    public int getSize() {
		return size;
    }
    
    /**
	  * display all active cases & purge all historical (recovered)
	  * cases when called
	  * @exception Any exception
	  * @return No return value
	*/
    public void display() {    
          
    	ActivePerson current = head;   
    	int counter = this.size;
            
        if(head == null) {    
            System.out.println("List is empty");    
            return;    
        }    
        System.out.println("Printing all active cases: "); 
        System.out.print("- ");
        
        while(current != null) {
        	
        	counter--;
        	
        	if(current.currentlyActive()) {
            
        		System.out.print(current.getName() + " - ");    
        		
        	}
        	
        	else {
        		//clean up cases that are no longer sick so list is smaller at subsequent calls
        		this.deleteFromNthEndOfList(counter);
        	}
            
        	current = current.next;    
        }    
       
        System.out.println();    
	

    }
    /**
	  * ran during display to remove cases no longer active
	  * @param n int representing the index from end of list to delete
	  * @exception Any exception
	  * @return boolean
	*/
    /**
     * 
     */ 
    public boolean deleteFromNthEndOfList(int n) {
		
		int size = this.getSize();
		
		if(n > size){
			System.out.print("out of range for currently active list");
			return false;
		}
		
		int indexToDelete = size - n;
		ActivePerson temp = this.head;
		int counter = 1;
		
		while(counter < indexToDelete) {
			temp = temp.next;
			counter++;
		}
		
		temp.next = temp.next.next;
		this.size--;
		
		
		return true;
	}
    
}
