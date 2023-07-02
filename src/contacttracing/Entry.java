package contacttracing;

/**
 * @author Chris Taylor taycr003@mymail.unisa.edu.au
 * Generic entry to be used in hashmap
 */
public class Entry<K, V> {
       
	K key;
    V value;
    Entry<K, V> next;

     public Entry(K key, V value, Entry<K, V> next) {
           
    	   this.key = key;
           this.value = value;
           this.next = next;
      }

	@Override
	public String toString() {
		return "Entry [key=" + key + ", value=" + value + ", next=" + next + "]";
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	
}

