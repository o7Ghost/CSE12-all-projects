//NAMES: Qiao jia, Lu, Dan Mu
//IDs: A13638993, A14719967
//EMAILS: qjlj@ucsd.edu, d1mu@ucsd.edu
//Login: cs12fbo, cs12rnj

package hw6;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Title: class HeapPQTester
 * Junit test for HeapPQ12
 */
public class HeapPQTester {

  //instance variable
  private HeapPQ12<Integer> heapMaxTest;
  private HeapPQ12<Integer> heapMinTest;
  private HeapPQ12<Integer> heapMaxEmptyTest;
  private HeapPQ12<Integer> heapMinEmptyTest;
  private HeapPQ12<Integer> heapMaxOneTest;
  private HeapPQ12<Integer> heapMinOneTest;
  private Iterator<Integer> iter;

  private int SIZE = 5;

  @Before
  public void setUp() {

    heapMaxTest = new HeapPQ12<Integer>(true);
    heapMinTest = new HeapPQ12<Integer>(false);
    heapMaxEmptyTest = new HeapPQ12<Integer>(true);
    heapMinEmptyTest = new HeapPQ12<Integer>(true);
    heapMaxOneTest = new HeapPQ12<Integer>(true);
    heapMinOneTest = new HeapPQ12<Integer>(false);

    heapMaxOneTest.offer(0);
    heapMinOneTest.offer(0);
    
    for (int i = 0; i < SIZE; i++) {
      heapMaxTest.offer(new Integer(i));
      heapMinTest.offer(new Integer(i));
    }

  }

  /* tests maxHeap size */
  @Test
  public void testMaxHeapSize() {
    assertEquals("incorrect MaxHeap size", 0, heapMaxEmptyTest.size());
    assertEquals("incorrect MaxHeap size", SIZE, heapMaxTest.size());
  }

  /* tests minHeap size */
  @Test
  public void testMinHeapSize() {
    assertEquals("incorrect MaxHeap size", 0, heapMinEmptyTest.size());
    assertEquals("incorrect MinHeap size", SIZE, heapMinTest.size());
  }

  /* test Max peek() */
  @Test
  public void peekMaxTest() {
    int firstque = 4;

    assertEquals("incorrect EmptyMaxHeap peek", null, 
		 heapMaxEmptyTest.peek());
    assertEquals("incorrect MaxHeap peek", new Integer(firstque), 
		 heapMaxTest.peek());
  }

  /* test Min peek() */
  @Test
  public void peekMinTest() {
    
    assertEquals("incorrect EmptyMinHeap peek", null, 
		 heapMinEmptyTest.peek());
    assertEquals("incorrect MinHeap peek", new Integer(0), 
		 heapMinTest.peek());
  }


  /*Test Maxpoll */
  @Test
  public void pollMaxTest() {
    int four = 4;
    
    assertEquals("incorrect EmptyMaxHeap poll", null, 
		  heapMaxEmptyTest.poll());

    assertEquals("incorrect MaxHeap poll removed", 
		 new Integer(four), heapMaxTest.poll());

    assertEquals("incorrect MaxHeap poll", four, heapMaxTest.size());
  }

  /* Test Minpoll */
  @Test
  public void pollMinTest() {
    int four = 4;
    
    assertEquals("incorrect EmptyMinHeap poll", null, 
		 heapMinEmptyTest.poll());

    assertEquals("incorrect MinHeap poll removed", 
		 new Integer(0), heapMinTest.poll());
    
    assertEquals("incorrect MinHeap poll", four, heapMinTest.size());
  }

  /* Test Max offer */
  @Test
  public void offerMaxTest() {
    int six = 6;
   
    heapMaxTest.offer(new Integer(six));
    
    assertEquals("incorrect MaxHeap offer", new Integer(six), 
		  heapMaxTest.peek());

    assertEquals("incorrect MaxHeap offer size", six, heapMaxTest.size());
  }

  /* Test Min offer */
  @Test
  public void offerMinTest() {
    int one = -(1);
    int six = 6;
    
    heapMinTest.offer(new Integer(one));
    
    assertEquals("incorrect MaxHeap offer", 
		 new Integer(one), heapMinTest.peek());

    assertEquals("incorrect MinHeap offer size", six, heapMinTest.size());
  }
 
  /* Test Max offer boolean */
  @Test
  public void offerMaxTrueTest() {
    int six = 6;
    
    assertEquals("incorrect MaxHeap offer boolean", true, 
		  heapMaxTest.offer(new Integer(six)));
  }

  /* Test Min offer boolean */
  @Test
  public void offerMinTrueTest() {
    int one = -(1);
        
    assertEquals("incorrect MinHeap offer boolean", true, 
		 heapMinTest.offer(new Integer(one)));
  }

  
  @Test
  /* Tests the maxHeap next iterator */
  public void iteratorMaxTest() {
    int four = 4;

    iter = heapMaxTest.iterator();   
    assertEquals("incorrect iterator max next()", 
		  new Integer(four), iter.next());
  }
  
  @Test
  /* Tests the minHeap next iterator */
  public void iteratorMinTest() {

    iter = heapMinTest.iterator();   
    assertEquals("incorrect iterator min next()", 
		  new Integer(0), iter.next());
  }
  
  @Test
  /* Tests iterator hasNext() */
  public void iteratorhasNextTest() {

    Iterator<Integer> iter2;
    
    iter = heapMaxEmptyTest.iterator();   
    iter2 = heapMaxTest.iterator();
    
    for (int i = 0; i < SIZE; i++) {
      iter2.next();
    }
    
    assertEquals("incorrect hasNext empty iterator", false, iter.hasNext());
    assertEquals("incorrect hasNext iterator", false, iter2.hasNext());
  }
  
  @Test
  /* Test iteratorMaxRemove */
  public void iteratorMaxRemoveTest() {
    int three = 3;
    iter = heapMaxTest.iterator();
    iter.next();
    iter.remove();
     
    assertEquals("incorrect heapMaxRemove", new Integer(three), 
		 heapMaxTest.peek());
  }

  @Test
  /* Test iteratorMinRemove */
  public void iteratorMinRemoveTest() {
    iter = heapMinTest.iterator();
    iter.next();
    iter.remove();
     
    assertEquals("incorrect heapMaxRemove", new Integer(1), 
		 heapMinTest.peek());
  }

  @Test
  /* Test iteratorMaxRemove */
  public void iteratorMiddleMaxRemoveTest() {
    int four = 4;
    iter = heapMaxTest.iterator();
    iter.next();
    iter.next();
    iter.remove();
     
    assertEquals("incorrect heapMaxRemove", four, 
		 heapMaxTest.size());
  }

  @Test
  /* Test iteratorMinRemove */
  public void iteratorMiddleMinRemoveTest() {
    int four = 4;
    iter = heapMinTest.iterator();
    iter.next();
    iter.next();
    iter.remove();
     
    assertEquals("incorrect heapMaxRemove", four, 
		 heapMinTest.size());
  }

  @Test
  /*Test remove twice */
  public void removetwice() {
    
    heapMaxOneTest.poll();
    heapMinOneTest.poll();

    assertEquals("poll twice maxHeap fail", null, heapMaxOneTest.poll());
    assertEquals("polltwice minHeap fail", null, heapMaxOneTest.poll());
  }

  @Test
  /* test remove exception in iterator */
  public void removeException() {
    try {
      iter = heapMaxTest.iterator();  
      iter.remove();
      fail("should have generated an exception");
    }

    catch (IllegalStateException e) {

    }
  }

  @Test
  /* test next exception in iterator */
  public void nextIllegalException() {
    try {
      iter = heapMaxEmptyTest.iterator();  
      iter.next();
      fail("should have generated an exception");
    }

    catch (NoSuchElementException e) {

    }
  }

  @Test
  /*Test max null pointer exception */
  public void maxOfferNullException() {
    try {
      heapMaxTest.offer(null);
      fail("should of generated an exception");
    }

    catch (NullPointerException e) {

    }
  }

  @Test
  /* Test Min null pointer exception */
  public void minOfferNullException() {
    try {
      heapMinTest.offer(null);
      fail("should of generated an exception");
    }

    catch (NullPointerException e) {

    }
  }
}
