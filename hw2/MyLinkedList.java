/**
 * Qiao Jia Lu
 * A13638993
 * 10/14/17
 * cs12fbo
 */

package hw2;
import java.util.*;

public class MyLinkedList<E> extends AbstractList<E> {
  
  private int nelems;
  private Node head;
  private Node tail;
  
  protected class Node {
    E data;
    Node next;
    Node prev;
    
    /** Constructor to create singleton Node */
    public Node(E element) {
      data = element;
    }
    /** Constructor to create singleton link it between previous and next 
      *   @param element Element to add, can be null
      *   @param prevNode predecessor Node, can be null
      *   @param nextNode successor Node, can be null 
      */
    public Node(E element, Node prevNode, Node nextNode) {
      data = element;
      next = nextNode;
      prev = prevNode;

      prev.next = this;
      next.prev = this;
    }
    
    /** Remove this node from the list. Update previous and next nodes */
    public void remove() {
      prev.next = next;
      next.prev = prev;
      next = null;
      prev = null;
    }
    /** Set the previous node in the list
      *  @param p new previous node
      */
    public void setPrev(Node p) {
      prev = p;
    }
	
    /** Set the next node in the list
      *  @param n new next node
      */
    public void setNext(Node n) {
      next = n;
	}
  
    /** Set the element 
      *  @param e new element 
      */
    public void setElement(E e) {
      data = e;
    }
    
    /** Accessor to get the next Node in the list */
    public Node getNext()
    {
      return (Node) next;
    }
    /** Accessor to get the prev Node in the list */
    public Node getPrev()
    {
      return (Node) prev;
    } 
    /** Accessor to get the Nodes Element */
    public E getElement()
    {
      return (E) data;
    } 
  }
  
  /** ListIterator implementation */ 
  protected class MyListIterator implements ListIterator<E> {
    
    private boolean forward;
    private boolean canRemove;
    private Node left,right; // Cursor sits between these two nodes
    private int idx;        // Tracks current position. what next() would
    // return 
   
    /**
     * constructor that creates the head and the tail
     * and assign it to iterator's rigth and left
     */
    public MyListIterator() {
      left = head;
      right = left.getNext();    
	}
    
    /**
     * Insert the given item into the list immediately before whatever 
     * would have been returned by a call to next()
     * 
     * @param e the data that is going to be added in
     * @throws IllegalArgumentException
     */
    @Override
    public void add(E e) throws  IllegalArgumentException {
      if ( e == null ) {
        throw new IllegalArgumentException("input is null");
      }
      
      Node newNode = new Node(e, left, right);
      left = newNode;
      
      //nelems++;
      idx++;
      canRemove = false;
	}
    
    /**
     * Return true if there are more elements when 
     * going in the forward direction. 
     *
     * @return if it has a next element then
     * return 
     */
    @Override
    public boolean hasNext() {

      boolean checkNext = false;
      
      if (right.getNext() != null) {
        checkNext = true;
      }
      return checkNext;
    }
    
    /**
     * return true if there are more elements when going in the 
     * reverse direction.
     */    
    @Override
    public boolean hasPrevious() {

      boolean checkNext = false;
      if (left.getPrev() != null) {
        checkNext = true;
      }
      return checkNext;
    }
    
    /**
     * move the iterator forward
     * @return return the next element in the list that is
     * forward
     * @throws NoSuchElementException
     */
    @Override
    public E next() throws NoSuchElementException {

      if (right.getNext() == null) {
        throw new NoSuchElementException("no element exists");
      }
      
      right = right.getNext();
      left = left.getNext();
      canRemove = true;
      forward = true;
      idx++;
     
      return (E) left.getElement();
    }
    
    /** 
     * Return the index of the element that would be returned by a call to next() 
     * and returns the size 
     *
     * @return the list size or the index from next()
     */
    @Override
    public int nextIndex() {
      
      return idx;
    }

    /**
     * Return the next element in the list when going backwards.
     * @return return the data of that element
     * @throws NoSuchElementException
     */
    @Override
    public E previous() throws NoSuchElementException {

      if (left.getPrev() == null) {
        throw new NoSuchElementException("no element exists");
      }
      
      left = left.getPrev();
      right = right.getPrev();
      canRemove = true;
      forward = false;
      idx--;

      return (E) right.getElement();
    }
    
    /**
     * Return the index of the element that would be returned by a call to previous()
     * @return return the index from previous or -1 if out of bound
     */
    @Override
    public int previousIndex() {
      int previdx = 0;

      if (left.prev == null) {
        previdx = -1;
      }
      
      else {
        previdx = idx - 1;
      }
      
      return previdx;  
    }
    
    /**
     * Remove the last element returned by the most recent call to either next/previous
     * @throws IllegalStateException
     */
    @Override
    public void remove() throws IllegalStateException {

      if (canRemove == false) {
        throw new IllegalStateException("neither next or previous"
                                         + "has been called");
      }
      
      if (canRemove == true) {
        if (forward == true) {
          left.getNext().setPrev(left.getPrev());
          left.getPrev().setNext(left.getNext());
          left = right.getPrev();
        }
        
        else {
          right.getNext().setPrev(right.getPrev());
          right.getPrev().setNext(right.getNext());
          right = left.getNext();
        }
      }
      canRemove = false;
	}
    
    /**
     * sets the element throught the iterator to
     * the linklist
     * @param e the element that is need to be replace
     */
    @Override
    public void set(E e) 
      throws IllegalArgumentException,IllegalStateException {

      if ( e == null ) {
        throw new IllegalArgumentException("input is null");
      }

      if (canRemove == false) {
        throw new IllegalStateException("neither next or previous"
                                         + "has been called");
      }

      if (canRemove == true) {
        if (forward == true) {
          left.setElement(e);
        }

        else {
          right.setElement(e);
        }
      }  
    }
  }
  
  //  Implementation of the MyLinkedList Class
  
  /**
   * a Linklist that will link all the elements
   * together
   */
  /** Only 0-argument constructor is define */
  public MyLinkedList() {
    
    //create head and tail links
    head = new Node(null);
    tail = new Node(null);
    head.next = tail;
    tail.prev = head;
  }
  
  @Override
  /**
   * check the size of the LinkList
   * @return returns the size of the LinkList
   */
  public int size() {
    return nelems; 
  }
  
  /**
   * get the element from the index
   * @throws IndexOutOfBoundsException
   *
   * @return return of element of the index
   * the user wants the get
   */
  @Override
  public E get(int index) throws IndexOutOfBoundsException {

    if (index < 0 || index >= size()) {
      throw new IndexOutOfBoundsException("index out of bound");
    }
        
    return (E) getNth(index).getElement();  
  }
  
  @Override
  /** Add an element to the list 
    * @param index  where in the list to add
    * @param data data to add
    * @throws IndexOutOfBoundsException
    * @throws IllegalArgumentException
    */ 
    public void add(int index, E data) 
    throws IndexOutOfBoundsException,IllegalArgumentException {
    
      if (index < 0 || index > size()) {
        throw new IndexOutOfBoundsException("index out of bound");
      }

      if (data == null) {
        throw new IllegalArgumentException("input is null");
      }
      
      //local variable
      Node curNode = head;

      //find insertion point
      for (int i = 0; i < index; i++) {
        curNode = curNode.next;
      }

      //creating new node
      Node newNode = new Node(data, curNode ,curNode.next);
      nelems++;
    }
  
  /** Add an element to the end of the list 
    * @param data data to add
    * @throws IllegalArgumentException
    */ 
  public boolean add(E data) throws IllegalArgumentException {
    if (data == null) {
      throw new IllegalArgumentException("input was null");
    }
    //creating new node and link them
    Node newNode = new Node(data,tail.prev,tail);
    
    //increase the size
    nelems++;
        
    return true;
  }
  
  /** Set the element at an index in the list 
    * @param index  where in the list to add
    * @param data data to add
    * @return element that was previously at this index.
    * @throws IndexOutOfBoundsException
    * @throws IllegalArgumentException
    * @throws IllegalStateExceptoin
    */ 
  public E set(int index, E data) 
    throws IndexOutOfBoundsException,IllegalArgumentException {

    if (data == null) {
      throw new IllegalArgumentException("input was null");
    }

    if (index < 0 || index >= size() ) {
      throw new IndexOutOfBoundsException("index out of bound");
    }
   
    //local variable/
    E prevData = null;
       
    prevData = getNth(index).data;
    getNth(index).data = data;
    
    return (E) prevData; 
  }
  
  /** Remove the element at an index in the list 
    * @param index  where in the list to add
    * @return element the data found
    * @throws IndexOutOfBoundsException
    * @throws IllegalStateException
    */ 
  public E remove(int index) throws IndexOutOfBoundsException {
    
    if (index >= size() || index < 0) {
      throw new IndexOutOfBoundsException("index out of bound");
    }

    //save element the data found
    E data = getNth(index).getElement();
    getNth(index).remove();
    
    nelems--;

    return (E) data;
  }
  
  /** Clear the linked list */
  public void clear() {
    head.next = tail;
    tail.prev = head;

    nelems = 0;
  }
  
  /** Determine if the list empty 
    *  @return true if empty, false otherwise */
  public boolean isEmpty() {
    boolean empty = false;

    if (size() == 0) {
      empty = true;
    }
    
    return empty;
  }
  
  /** 
   * overrides construsctor 
   * @returns returns my iterator
   */
  public Iterator<E> iterator() {
    return new MyListIterator();
  }

  /**
   * overrider constructor
   * @return returns the iterator i created
   */
  public ListIterator<E> listIterator() {
    return new MyListIterator();
  }
  
  /**
   * Helper method to get the Node at the Nth index
   * @return return curNode
   */
  private Node getNth(int index) {

    //local variable
    Node curNode = head;
    
    //iteration to the right index
    for (int i = 0; i <= index; i++) {
      curNode = curNode.next;
    }

    return (Node) curNode;  
  }
  
  /*  UNCOMMENT the following when you believe your MyListIterator class is
   functioning correctly
   public Iterator<E> iterator()
   {
   return new MyListIterator();
   }
   public ListIterator<E> listIterator()
   {
   return new MyListIterator();
   }
   */
}

// VIM: set the tabstop and shiftwidth to 4 
// vim:tw=78:ts=4:et:sw=4

