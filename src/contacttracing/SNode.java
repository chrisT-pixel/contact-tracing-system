package contacttracing;

import java.time.LocalDate;

public class SNode {
    
	public Visit visit;
	public LocalDate date;
 
    public SNode left;
    public SNode right;
    public SNode up;
    public SNode down;
 
    public SNode(Visit visit) {
        
    	this.visit = visit;
        this.date = visit.getDate();
        
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }
 
    public SNode(SNode lowerLevelNode) {
        this.visit = lowerLevelNode.visit;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = lowerLevelNode;
    }
}
