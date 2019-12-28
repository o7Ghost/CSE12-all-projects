//package hw3;
import java.util.*;
/**
 * a class that adds objects from the front
 * and remove from the front.
 */
public class QueueWorklist implements SearchWorklist {
  
  private LinkedList<Square> queue;

  public QueueWorklist() {
    queue = new LinkedList<Square>();
  }

  
  /** Add a Square to the worklist, as appropriate 
   * @param The Square to add 
   */
  public void add(Square s) {
    queue.add(s);
  }

  /** Removes and returns the next Square to be explored 
   * @return The next Square to explore */
  public Square getNext() {
    return queue.removeFirst();
  }

  /** isEmpty
   * @return true if the worklist is empty, false otherwise
   */
  public boolean isEmpty() {
    return queue.isEmpty();
  }
    
  /** size of the worklist
   * @return The number of elements in the worklist
   */
   public int size() {
     return queue.size();   
   }
}
