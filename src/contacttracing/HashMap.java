package contacttracing;
import java.util.Arrays;
import java.util.Objects;


/**
 * 
 * @author Chris Taylor taycr003@mymail.unisa.edu.au
 * Generic Hashmap that accepts key value pairs as 'entries'
 *
 */
public class HashMap<K, V> {
	
	public Entry<K, V>[] table;   //Array of Entry.
    public int capacity = 1;  //Initial capacity of HashMap
    public double loadThreshold;
    public int size = 0;

 
    @SuppressWarnings("unchecked")
    public HashMap(double loadThreshold) {
        table = new Entry[capacity];
        this.loadThreshold = loadThreshold;
		
    }
    
    public Entry<K, V>[] getTable() {
		return table;
	}

    public void setTable(Entry<K, V>[] table) {
		this.table = table;
	}

    /**
     * Method allows for insertion of key-Value pair into Hashmap
     * If the map already contains a mapping for the key, the old Entry is replaced.
     * Note: method does not allows you to put null key though it allows null Entries
     * Implementation allows you to put custom objects as a key as well.
     * IncreaseSize is to be true on initial call, subsequent calls by reHash methods will set as false
     * Key Features: implementation provides you with following features:-
     * >provide complete functionality how to override equals method.
     * >provide complete functionality how to override hashCode method.
     *
     * @param newKey
     * @param data
     * @param increaseSize
     * @return no return value
     */
    public void put(K newKey, V data, boolean increaseSize) {
    	
    	
    	if (newKey == null) {
            
    		System.out.println("Item neds a key so hash can be created");
    		return;    //does not allow to store null.
    		
    	}
    	
    	if(increaseSize) {
         	 this.size++;
         }
      	
      	 if(loadReached()) {
       		reHashLarger(newKey, data);
       		return;
       	}

        //calculate hash of key.
        int hash = hash(newKey);
        
        //create new entry.
        Entry<K, V> newEntry = new Entry<K, V>(newKey, data, null);
        
        //if table location does not contain any entry, store entry there.
        if (table[hash] == null) {
        	
        	table[hash] = newEntry;
        } 
        
        else {
           
        	Entry<K, V> previous = null;
        	Entry<K, V> current = table[hash];

            while (current != null) { //we have reached last entry of bucket.
                
            	if (current.key.equals(newKey)) {
                    
            		if (previous == null) {  //node has to be insert on first of bucket.
                        
            			newEntry.next = current.next;
                        this.table[hash] = newEntry;
                        
                        return;
                    } 
            		
            		else {
                        
            			newEntry.next = current.next;
                        previous.next = newEntry;
                       
                       
                    }
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
        }
        
 
    }

    /**
     * Method returns Object value corresponding to key.
     *
     * @param key
     * @return V 
     */
    public V getValue(K key) {
        
    	int hash = hash(key);
        
    	if (table[hash] == null) {
            
    		return null;
        } 
    	
    	else {
            
    		Entry<K, V> temp = table[hash];
            
    		while (temp != null) {
                
    			if (temp.key.equals(key))
                    
    				return temp.value;
                
    			temp = temp.next; //return Person corresponding to key.
            }
            
    		return null;   //returns null if key is not found.
       
    	}
    }
    
    /**
     * Method returns Entry object corresponding to key.
     *
     * @param key
     * @return Entry
     */
    public Entry<K, V> getEntry(K key) {
        
    	int hash = hash(key);
        
    	if (table[hash] == null) {
            
    		return null;
        } 
    	
    	else {
            
    		Entry<K, V> temp = table[hash];
            
    		while (temp != null) {
                
    			if (temp.key.equals(key))
                    
    				return temp;
                
    			temp = temp.next; //return Person corresponding to key.
            }
            
    		return null;   //returns null if key is not found.
       
    	}
    }


    /**
     * Method removes key-Entry pair from HashMap.
     * decreaseSize set to true on initial call, subsequent calls by reHash functions will set to false
     *
     * @param key
     * @param decreaseSize
     * @return no return value
     */
    public void remove(K deleteKey, boolean decreaseSize) {

        int hash = hash(deleteKey);

        if (table[hash] == null) {
            return;
        } 
        
         else {
            
        	Entry<K, V> previous = null;
            Entry<K, V> current = table[hash];

            while (current != null) { //we have reached last entry node of bucket.
                
            	if (current.key.equals(deleteKey)) {
                    
            		if (previous == null) {  //delete first entry node.
                        
            			table[hash] = table[hash].next;
            			
            			if(decreaseSize) {
            	        	 this.size--;
            	        }
            	     	
            	     	 if(loadSpaceAvailable()) {
            	      		reHashSmaller(deleteKey);
            	      		return;
            	      	}
            			
                        
            			return;
                    } 
            		
            		else {
                        
            			previous.next = current.next;
            			
            			if(decreaseSize) {
            	        	 this.size--;
            	        }
            	     	
            	     	 if(loadSpaceAvailable()) {
            	      		reHashSmaller(deleteKey);
            	      		return;
            	      	}
            			
                        return;
                    }
                }
                
            	previous = current;
                current = current.next;
            }
            
            if(decreaseSize) {
           	 this.size--;
           }
        	
        	 if(loadSpaceAvailable()) {
         		reHashSmaller(deleteKey);
         		return;
         	}
        }

    }


    /**
     * Display contents of hashmap without exposing 
     * underlying data structure
     */
    public void display() {

        for (int i = 0; i < capacity; i++) {
            
        	if (table[i] != null) {
                
        		Entry<K, V> entry = table[i];
                
        		while (entry != null) {
                    System.out.print("{" + entry.key + "=" + entry.value + "}" + " ");
                    entry = entry.next;
                }
            }
        }

    }

    /**
     * Method implements hashing functionality, which helps in finding the appropriate
     * bucket location to store our data.
     *
     * @param key
     * @return int 
     */
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    
    public boolean isEmpty() {
    	
    	if(this.size > 0) {
    		return false;
    	}
    	
    	else {
    		return true;
    	}
    }
    
    @SuppressWarnings("unchecked")
    /**
     * used when size of hashmap needs to increase
     *
     * @param newKey
     * @param data
     * @return no return value
     */
    public void reHashLarger(K newKey, V data) {
    	
    	int newSize = this.table.length * 2;
    	Entry<K, V>[] copyTable = Arrays.copyOf(this.table, this.table.length);
    	
    	this.capacity = newSize;
    	this.setTable(new Entry[newSize]); 
    	
    	for(int i = 0; i < copyTable.length; i++) {
    		
    		if(copyTable[i] != null) {
    			
    			this.put(copyTable[i].key, copyTable[i].value, false);
    			
    			Entry<K, V> current = copyTable[i];
    			
    			while(current.next != null) {
    				
    				this.put(current.next.key, current.next.value, false);
    				current = current.next;
    			}
    			
    		}
    	}
    	
    	this.put(newKey, data, false);
    	
    }
    /**
     * used when size of hashmap needs to decrease
     *
     * @param newKey
     * @param data
     * @return no return value
     */
    public void reHashSmaller(K deleteKey) {
    	
    	double dubSize = (double)this.size;
    	double newSize = dubSize / this.loadThreshold;
    	int newSizeInt = (int)newSize;
    	Entry<K, V>[] copyTable = Arrays.copyOf(this.table, this.table.length);
    	
    	this.capacity = newSizeInt;
    	this.setTable(new Entry[newSizeInt]); 
    	
    	for(int i = 0; i < copyTable.length; i++) {
    		
    		if(copyTable[i] != null) {
    			
    			this.put(copyTable[i].key, copyTable[i].value, false);
    			
    			Entry<K, V> current = copyTable[i];
    			
    			while(current.next != null) {
    				
    				this.put(current.next.key, current.next.value, false);
    				current = current.next;
    			}
    			
    		}
    	}
    	
    	this.remove(deleteKey, false);
    	
    }
    
    /**
     * determines when hashmap must increase in size
     * @return boolean
     */
    public boolean loadReached() {
    	
    	double dubLength = (double)this.table.length;
    	double dubSize = (double)this.size;
    	
    	if(dubSize / dubLength > this.loadThreshold) {
    		
    		return true;
    	}
    	
    	else {
    		return false;
    	}
    }
    
    /**
     * determines when hashmap must decrease in size
     * @return boolean
     */
    public boolean loadSpaceAvailable() {
    	
    	double dubLength = (double)this.table.length;
    	double dubSize = (double)this.size;
    	
    	if(dubSize / dubLength < this.loadThreshold) {
    		return true;
    	}
    	
    	else {
    		return false;
    	}
    }




	@Override
	public int hashCode() {
		return Objects.hash(capacity, loadThreshold, size);
	}


	 /**
     * display hashmap with datastructure information
     * @return String
     */
	public String outputMap() {
		
		String output = "";

        for (int i = 0; i < capacity; i++) {
            
        	if (table[i] != null) {
                
        		Entry<K, V> entry = table[i];
                
        		while (entry != null) {
        			output += "{" + entry.key + "=" + entry.value + "}" + " ";
                    entry = entry.next;
                }
            }
        }
        
        output += ", [ capacity=" + capacity + ", loadThreshold="
				+ loadThreshold + ", size=" + size + " ]";
        
        return output;

    }




	@Override
	public String toString() {
		return "HashMapChaining [table=" + Arrays.toString(table) + ", capacity=" + capacity + ", loadThreshold="
				+ loadThreshold + ", size=" + size + "]";
	}
	
	

}
