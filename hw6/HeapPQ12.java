//NAMES: Qiao jia, Lu, Dan Mu
//IDs: A13638993, A14719967
//EMAILS: qjlj@ucsd.edu, d1mu@ucsd.edu
//Login: cs12fbo, cs12rnj
package hw6;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
/** HeapPQ12 class that implements an unbounded array-backed heap structure and is
 * an extension of the Java Collections AbstractQueue class 
 * <p>
 * The elements of the heap are ordered according to their natural 
 * ordering,  HeapPQ12 does not permit null elements. 
 * The top of this heap is the minimal or maximal element (called min/max)  
 * with respect to the specified natural ordering.  
 * If multiple elements are tied for min/max value, the top is one of 
 * those elements -- ties are broken arbitrarily. 
 * The queue retrieval operations poll and  peek 
 * access the element at the top of the heap.
 * <p>
 * A HeapPQ12 is unbounded, but has an internal capacity governing the size of 
 * an array used to store the elements on the queue. It is always at least as 
 * large as the queue size. As elements are added to a HeapPQ12, its capacity 
 * grows automatically. The details of the growth policy are not specified.
 * <p>
 * This class and its iterator implements the optional methods of the 
 * Iterator interface (including remove()). The Iterator provided in method 
 * iterator() is not guaranteed to traverse the elements of the HeapPQ12 in 
 * any particular order. 
 * <p>
 * Note that this implementation is not synchronized. Multiple threads 
 * should not access a HeapPQ12 instance concurrently if any of the 
 * threads modifies the HeapPQ12. 
 */
public class HeapPQ12<E extends Comparable <? super E>> extends 
  AbstractQueue<E> {
    
  /* -- Define any private member variables here -- */
  /* In particular, you will need an ArrayList<E> to hold the elements
   * in the heap.  You will also want many more variables */
  private int initialCapacity = 5;
  private int nelems;
  private boolean minMax;
  private ArrayList<E> heap;

  /** O-argument constructor. Creates an empty HeapPQ12 with unspecified
   *  initial capacity, and is a min-heap 
   */ 
  public HeapPQ12() {

  //declare minheap and set capacity to 5
    heap = new ArrayList<E>(initialCapacity);
    heap.add((E)null);
  }

  /** 
    * Constructor to build a min or max heap
    * @param isMaxHeap   if true, this is a max-heap, else a min-heap 
    */ 
  public HeapPQ12(boolean isMaxHeap) {
 
    //determine if it's a min or max heap
    minMax = isMaxHeap;
     
    //make a heap with a dummy head
    heap = new ArrayList<E>(initialCapacity);
    heap.add((E)null);
  }

  /** 
    * Constructor to build a with specified initial capacity 
    *  min or max heap
    * @param capacity      initial capacity of the heap.   
    * @param isMaxHeap     if true, this is a max-heap, else a min-heap 
    */ 
  public HeapPQ12(int capacity, boolean isMaxHeap) {

    //determine if it's a min or max heap
    minMax = isMaxHeap;

    //make a heap with a specific capacity and a dummy head
    heap = new ArrayList<E>(capacity);
    heap.add((E)null);
  }
    
  /** Copy constructor. Creates HeapPQ12 with a deep copy of the argument
    * @param toCopy      the heap that should be copied
    */
  public HeapPQ12 (HeapPQ12<E> toCopy) {

    //set variables for copy list
    nelems = toCopy.nelems;
    minMax = toCopy.minMax;
    initialCapacity = toCopy.initialCapacity;
    ArrayList<E> copyContainer = new ArrayList<E>(initialCapacity);

    //deep copy
    copyContainer.add((E)null);

    for (int i = 1; i < toCopy.heap.size(); i++) {
	      
      copyContainer.add(toCopy.heap.get(i));
    }

    heap = copyContainer;
      
  }

  /* The following are defined "stub" methods that provide degenerate
   * implementations of methods defined as abstract in parent classes.
   * These need to be coded properly for HeapPQ12 to function correctly
   */

  /** Size of the heap
    * @return the number of elements stored in the heap
    */
  public int size() {
    return nelems; 
  }

  /** 
    * @return an Iterator for the heap 
    */
  public Iterator<E> iterator() {

    HeapPQ12Iterator iter  = new HeapPQ12Iterator();
    return iter; 
  }

  /** peek - see AbstractQueue for details 
    * 
    * @return Element at top of heap. Do not remove 
    */
  public E peek() {
    E temp = null;

    if (size() > 0) {
      temp = heap.get(1);
    }
    
    return (E) temp;
  }

  /** poll - see AbstractQueue for details 
    * @return Element at top of heap. And remove it from the heap. 
    *  return <tt>null</tt> if the heap is empty
    */
  public E poll() {

    //local variable
    E removed = null;

    if (size() > 0) {

      removed = heap.get(1);
      heap.set(1, (E)null);
      swap(1,nelems);
      nelems--;

      if (minMax) {
        trickingDown(1,1);
      }

      else {
        trickingDown(1,-1);
      }
    }
    return (E) removed;
  }

  /** offer -- see AbstractQueue for details
    * insert an element in the heap
    * @return true
    * @throws NullPointerException 
    *  if the specified element is null    
    */
  public boolean offer (E e) throws NullPointerException {

    if ( e == null) {
      throw new NullPointerException("Null Parameter");
    }

    if (size() + 1 == initialCapacity) {
      expand();
    }

    nelems++;
    heap.add((E)null);
    heap.set(nelems, e);

    //bubbling min or max heap
    if (minMax) {
      bubblingUp(nelems, 1);
    }

    else {
      bubblingUp(nelems, -1);
    }

    return true;
  }

  /* ------ Private Helper Methods ----
   *  DEFINE YOUR HELPER METHODS HERE
   */

  /**
   * bubbling up max heap
   *
   * @param index the position of the
   * element that needs to be bubbled up
   */
  public void bubblingUp(int index, int minMax) {
	
    //local variable
    int two = 2;

    //base cases
    if (size() == 1) {
      return;
    }
   
    //compare objects
    if (index/two > 0) {
      if (heap.get(index).compareTo(heap.get(index/two)) == minMax) {
        swap(index, index/two); 
        bubblingUp(index/two, minMax);
      }
    }
  }

  /**
   * move down the heap depend on min or
   * max heap
   *
   * @param index start of the heap
   * @param minMax min or max heap
   */
  public void trickingDown(int index, int minMax) {

    //local variable
    int two = 2;
    int childIndex = 0;
    
    //base case 1 elem
    if (size() == 1) {
      return;
    }

    //base case leaf
    if (index * two > size()) {
      return;
    }

    //the case if there is only left leaf
    if (index * two + 1 > size()) {
	    
      childIndex = index*two;

      if (heap.get(childIndex).compareTo(heap.get(index)) == minMax) {
	      
        swap(index, childIndex);
        trickingDown(childIndex, minMax);
      }
    }

    //complete tree
    else {

      if (heap.get(index*two).compareTo(heap.get(index*two + 1)) == minMax) {
        childIndex = index*two;
        swap(index, childIndex);
        trickingDown(childIndex, minMax);
      }

      else {

        childIndex = index*two + 1;
        swap(index,childIndex);
        trickingDown(childIndex, minMax);
      }
    }
  }
  

  /**
   * expand the array
   */
  public void expand() {

    //local variable
    int two = 2;
    initialCapacity = two * initialCapacity;
    ArrayList<E> temp = heap;
    ArrayList<E> heap = new ArrayList<E>(initialCapacity);
    heap.add((E)null);
    
    for (int i = 1; i <= size(); i++) {
      heap.add(temp.get(i));
    }  
  }

  /**
   * swap objects
   * @param current index of the current object
   * that needs to be switch
   * @param change index of the object that needs 
   * to be switched with
   */
  public void swap(int current, int change) {
    E temp = heap.get(current);
    heap.set(current, heap.get(change));
    heap.set(change, temp);
  }

  /** Inner Class for an Iterator **/
  /* stub routines */
  private class HeapPQ12Iterator implements Iterator<E> {

    //instance variable
    private boolean canRemove;
    private int left,right;
    private int indx;
    
    /**
     * a constructor that initializes
     * right and left for the iterator
     */
    private HeapPQ12Iterator() {
      left = 0;
      right = 1;
    }

    /** 
     * hasNext() to implement the Iterator<E> interface 
     * check if there is next element in the array list
     * @return return true if there is next element
     * and false if there isn't
     */
    public boolean hasNext() {
      boolean checkNext = false;

      if (indx + 1 <= size()) {
        checkNext = true;
      }
      
      return checkNext;
    }

    /**
     * next() to implement the Iterator<E> interface 
     * return the the element that is next
     * @throws NoSuchElementException
     * @return returns the element that is called
     */
    public E next() throws NoSuchElementException {
      
      if (nelems == 0 || !hasNext() ) {
        throw new NoSuchElementException("no element exists");
      }
      
      left++;
      right++;
      canRemove = true;
      indx++;

      return (E) heap.get(indx);             
    }

    /** 
     * remove() to implement the Iterator<E> interface 
     * remove one of the element in the middle of 
     * the array in any position
     *
     * @throws IllegalStateException 
     */
    public void remove() throws IllegalStateException {

      //check if next has been called
      if (canRemove == false) {
        throw new IllegalStateException("next hasn't been called");
      }
      
      //check if next() has been called
      if (canRemove == true) {

	//remove element and swap them
        heap.set(left, (E)null);
        swap(left,nelems);
        nelems--;

	//tricking down according to min or max heap
        if (minMax) {
          bubblingUp(left,1);
          trickingDown(left,1);
        }

        else {
          bubblingUp(left,-1);
          trickingDown(left,-1);
        }

	//set it back to false
        canRemove = false;
      }   
    }
  }
} 
