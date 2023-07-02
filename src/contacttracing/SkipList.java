package contacttracing;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
 
public class SkipList {
    
	//starting pointers from top level
    private SNode head;
    private SNode tail;
    
    Visit headNode = new Visit(null, null, LocalDate.MIN, null, null);
    Visit tailNode = new Visit(null, null, LocalDate.MAX, null, null);
 
    public SkipList() {
        
    	head = new SNode(headNode);
        tail = new SNode(tailNode);
 
        head.right = tail;
        tail.left = head;
    }
 
    
    
    
    /*public ArrayList<Visit> searchInRange(LocalDate start, LocalDate finish) {
        
    	ArrayList<Visit> visitsInRange = new ArrayList<Visit>();
    	SNode curr = head.right;
 
        while (curr != null) {
        	
        	System.out.println(curr.right.visit.getDate());
            
        	while (curr.right != null && curr.right.visit.getDate().isAfter(start)) {
                
                curr = curr.right;
                
            }
        	
        	break;
        	
        }
        
        while (curr.down != null) {
            curr = curr.down;
        }
        
        curr = curr.right;
        
        while (curr.right != null) {
        	visitsInRange.add(curr.right.visit);
            curr = curr.right;
        }
        
        return visitsInRange;
    }*/
    
    public ArrayList<Visit> searchInRange(LocalDate start, LocalDate finish){
    	
    	ArrayList<Visit> allVisits = new ArrayList<Visit>();
    	SNode curr = head;
 
    	 while (curr.down != null) {
             curr = curr.down;
         }

         curr = curr.right;

         while (curr.right != null) {
        	 
        	 if(curr.visit.getDate().isAfter(start.minusDays(1)) && curr.visit.getDate().isBefore(finish.plusDays(1)) ){	
        		 allVisits.add(curr.visit);
        	 }
             curr = curr.right;
         }
        
        return allVisits;
    	
    }
    
    public ArrayList<Visit> returnAll() {
        
    	ArrayList<Visit> allVisits = new ArrayList<Visit>();
    	SNode curr = head;
 
    	 while (curr.down != null) {
             curr = curr.down;
         }

         curr = curr.right;

         while (curr.right != null) {
             allVisits.add(curr.visit);
             curr = curr.right;
         }
        
        return allVisits;
    }
    
 
    public boolean insert(Visit val) {
        
    	List<SNode> pointersToUpdate = new ArrayList<>();
 
        SNode curr = head;
        
        while (curr != null) {
            
        	//stop once we have reached the correct date
        	while (curr.right != null && curr.right.visit.compareTo(val) < 0) {
                curr = curr.right;
            }
 
            pointersToUpdate.add(curr);
            curr = curr.down;
        }
 
        // insert after this node.
        int level = 0;
        SNode newNode = null;
 
        while (level == 0 || flipCoin()) {
            // now move up.
            if (newNode == null) {
                newNode = new SNode(val);
            } else {
                newNode = new SNode(newNode);
            }
 
            SNode nodeToUpdate;
 
            if (pointersToUpdate.size() <= level) {
                createNewLayer();
                nodeToUpdate = this.head;
            } else {
                nodeToUpdate = pointersToUpdate.get(pointersToUpdate.size() - level - 1);
            }
 
            // insert
            newNode.right = nodeToUpdate.right;
            newNode.left = nodeToUpdate;
 
            newNode.right.left = newNode;
            nodeToUpdate.right = newNode;
 
            level++;
        }
 
        return true;
    }
 
    private void createNewLayer() {
    	
    	Visit headNodeNew = new Visit(null, null, LocalDate.MIN, null, null);
        Visit tailNodeNew = new Visit(null, null, LocalDate.MAX, null, null);
    	
        SNode newHead = new SNode(headNodeNew);
        SNode newTail = new SNode(tailNodeNew);
 
        newHead.right = newTail;
        newTail.left = newHead;
 
        head.up = newHead;
        newHead.down = head;
        head = newHead;
 
        tail.up = newTail;
        newTail.down = tail;
        tail = newTail;
    }
 
    private boolean flipCoin() {
        return Math.random() >= 0.5;
    }
 
    
}