package hw2;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *  Title: class LinkedListTester
 *  Description: JUnit test class for LinkedList class
 *  @author Qiao Jia Lu(Joseph)
 *  @version 3.0 05-April-2015
 *
 * cs12fbo
 * */

/*
 * You should modify the information above to add your names and CSE12 accounts.
 * 
 * We have provided 7 tests to help you get familiar. 
 *
 * As a part of the Milestone submission, you have to add 10 new tests.
 * These tests will ONLY be graded against the correct implementation of MyLinkedList.
 * However, you will also be given feedback on how your tests behave on the 
 * buggy implementation of MyLinkedList. 
 *
 * After the Milestone submission, you will be able to view each other's tests which you
 * can use to add or improve your tests for the final submission. At the final submission,
 * if you are using someone else's tests, we expect you to give the required attribution.
 * We also expect you to write a README file that explains in greater detail why you used
 * the test. 
 * 
 * For the Final Submission, your tests will be graded against both the correct and
 * buggy implementation of MyLinkedList. At the time of final submission, you will be
 * given feedback on how your tests behave against few but not all, buggy implementations.
 *
 * Finally, when your tester is complete, you will rename it MyLinkedListTester.java 
 * (renaming LinkedList to MyLinkedList everywhere in the file) so that you can
 * use it to test your MyLinkedList and MyListIterator classes.
 */
public class MyLinkedListTester
{
  //declaration
  private MyLinkedList<Integer> empty;
  private MyLinkedList<Integer> one;
  private MyLinkedList<Integer> several;
  private MyLinkedList<String>  slist;
  private MyLinkedList<Integer> noElem;
  private MyLinkedList<Integer> oneElem;
  private MyLinkedList<Integer> severalElem;
  private ListIterator<Integer> iterNo;
  private ListIterator<Integer> iterOne;
  private ListIterator<Integer> iterSeveral;

  static final int DIM = 5;
  static final int FIBMAX = 30;
  
  /**
   * Standard Test Fixture. An empty list, a list with one entry (0) and 
   * a list with several entries (0,1,2)
   */ 
  @Before
  public void setUp()
  {
    empty = new MyLinkedList<Integer>();
    one = new MyLinkedList<Integer>();
    one.add(0,new Integer(0));
    several = new MyLinkedList<Integer>() ;
    // List: 1,2,3,...,Dim
    for (int i = DIM; i > 0; i--) 
      several.add(0,new Integer(i));

    // List: "First","Last"
    slist = new MyLinkedList<String>();
    slist.add(0,"First");
    slist.add(1,"Last");

    noElem  = new MyLinkedList<Integer>();
    oneElem  = new MyLinkedList<Integer>();
    severalElem  = new MyLinkedList<Integer>();

    iterNo = noElem.listIterator();
    iterOne = oneElem.listIterator();
    iterSeveral = severalElem.listIterator();
    
    iterOne.add(0);

    for (int i = DIM; i > 0; i--) {
       iterSeveral.add(i);
    }

  }
  
  /** Test if heads of the lists are correct */
  @Test
  public void testGetHead()
  {
    assertEquals("Check 0",new Integer(0),one.get(0)) ;
    assertEquals("Check 0",new Integer(1),several.get(0)) ;
  }
  
  /** Test if size of lists are correct */
  @Test
  public void testListSize()
  {
    assertEquals("Check Empty Size",0,empty.size()) ;
    assertEquals("Check One Size",1,one.size()) ;
    assertEquals("Check Several Size",DIM,several.size());

  }
  
  /** Test setting a specific entry */
  @Test
  public void testSet()
  {
    slist.set(1,"Final");
    assertEquals("Setting specific value", "Final",slist.get(1));
  }
  
  /** Test isEmpty */
  @Test
  public void testEmpty()
  {
    assertTrue("empty is empty",empty.isEmpty()) ;
    assertTrue("one is not empty",!one.isEmpty()) ;
    assertTrue("several is not empty",!several.isEmpty()) ;
  }

  /** Test out of bounds exception on get */
  @Test
  public void testGetException()
  {
    try 
    {
      empty.get(0);
      // This is how you can test when an exception is supposed 
      // to be thrown
      fail("Should have generated an exception");  
    }
    catch(IndexOutOfBoundsException e)
    {
      //  normal
    }
  }

  
  /** Test iterator on empty list and several list */
  @Test
  public void testIterator()
  {
    int counter = 0 ;
    ListIterator<Integer> iter;
    for (iter = empty.listIterator() ; iter.hasNext(); )
    {
      fail("Iterating empty list and found element") ;
    }
    counter = 0 ;
    for (iter = several.listIterator() ; iter.hasNext(); iter.next())
      counter++;
    assertEquals("Iterator several count", counter, DIM);
  }
  
  
  /** test Iterator Fibonacci.
    * This is a more holistic test for the iterator.  You should add
    * several unit tests that do more targeted testing of the individual
    * iterator methods.  */
  @Test
  public void testIteratorFibonacci()
  {
    
    MyLinkedList<Integer> fib  = new MyLinkedList<Integer>();
    ListIterator<Integer> iter;
    // List: 0 1 1 2 3 5 8 13 ... 
    // Build the list with integers 1 .. FIBMAX
    int t, p = 0, q = 1;
    fib.add(0,p);
    fib.add(1,q);
    for (int k = 2; k <= FIBMAX; k++)
    {
      t = p+q;
      fib.add(k,t);
      p = q; q = t; 
    }
    // Now iterate through the list to near the middle, read the
    // previous two entries and verify the sum.
    iter = fib.listIterator();
    int sum = 0;
    for (int j = 1; j < FIBMAX/2; j++)
      sum = iter.next();
    iter.previous();
    assertEquals(iter.previous() + iter.previous(),sum);
    // Go forward with the list iterator
    assertEquals(iter.next() + iter.next(),sum);
  }

  /** 1 Test boolean add for empty, one and several */
  @Test
  public void testadd() {

    several.add(6);
    slist.add("appended element");
    assertEquals("checking if there is a element", 0, empty.size());
    assertEquals("checking if there is a more than one element", new Integer(0), one.get(one.size() - 1));
    assertEquals("checking if element is appended", new Integer(6), several.get(5));
    assertEquals("check string is appended to the end of the list", 
		 "appended element", slist.get(slist.size() - 1));
    
  }
  
  /** 2 Test add(int index, T data) if it's inserted in the right positon */
  @Test
  public void testinsert() {
    
    int three = 3;
    int six = 6;
    int five = 5;
    int two = 2;

    //test is empty and one linked list is not inserted
    assertEquals("checking if empty is inserted", 0, empty.size());
    assertEquals("checking if one is inserted", 1, one.size());

    //test if several is insert correctly
    several.add(three, new Integer(six));
    several.remove(three);
    several.add(three, new Integer(five));

    assertEquals("checking if insertion is correct for several", 
		 new Integer(five), several.get(three));

    //test if slist is insert correctly
    slist.add(two, "insert 2nd index");
    slist.remove(two);
    slist.add(two, "insert 2nd index");

    assertEquals("checking if insertion is correct for slist", 
		 "insert 2nd index", slist.get(2));

  }

  /** 3 Test if the get() method is correct for slist and several */
  @Test
  public void testGet() {
    
    int two = 2;
    int three = 3;
    int six = 6;
    
    //test get for several
    several.add(three, new Integer(six));

    assertEquals("checking if the element got is correct for several", 
		 new Integer(six), several.get(three));

    //test get for slist
    
    slist.add(two, "insert 2nd index");

    assertEquals("checking if the element got is correct for slist", 
		 "insert 2nd index", slist.get(two));
  }

  /** 4 Test set() is correct for one, slist and several */
  @Test
  public void setTest() {

    int two = 2;
    int four = 4;
    
    //checking set for one LinkedList
    one.set(0, new Integer(two));
    assertEquals("Setting specific value", new Integer(two) , one.get(0));

    //checking set for several LinkedList
    several.set(4, new Integer(two));
    assertEquals("Setting specific value", new Integer(two) , several.get(four));

    //checking set for slist LinkedList
    slist.set(0, "One");
    assertEquals("Setting specific value", "One", slist.get(0));
  }

  /** 5 Test remove method is correct for slist,several and one */
  @Test
  public void removeTest() {
    
    int three = 3;
    int four = 4;
    int five = 5;
    int two = 2;
    
    //Test remove for One LinkedList
    one.remove(0);
    assertEquals("checking if element is removed for One", 
		0, one.size());
 
    //Test remove for several LinkedList
    several.remove(three);
    
    assertEquals("checking if element is removed for several", 
		  4, several.size());
    assertEquals("checking adject elements are correct",
		  new Integer(five), several.get(three));
    assertEquals("checking adject element", 
		 new Integer(three), several.get(two));

    //Test remove for slist LinkList
    slist.remove(1);
    assertEquals("checking if element is removed for several", 
		  1, slist.size());
    assertEquals("checking if adject elements are correct",
	         "First", slist.get(0));
	         
  }

  /** 6 test if clear() method will clear all elements in Linklist */
  @Test
  public void clearTest() {
    
    //Test clear for empty
    empty.clear();
    assertEquals("checking if all elements is removed for empty", 
		0, empty.size());
    
    //Test clear for One
    one.clear();
    assertEquals("checking if all elements is removed for One", 
		0, one.size());

    //Test clear for several
    several.clear();
    assertEquals("checking if all elements is removed for several", 
		0, several.size());
    
    //Test clear for slist
    slist.clear();
    assertEquals("checking if all elements is removed for slist", 
		0, slist.size());
  }


  /** 7 check if Linklist is empty */
  @Test
  public void checkEmptyTest() {
    
    //Test clear for empty
    empty.clear();
    assertTrue("empty should be empty", 
		empty.isEmpty());
    
    //Test clear for One
    one.clear();
    assertTrue("one should be empty", 
		one.isEmpty());

    //Test clear for several
    several.clear();
    assertTrue("several should be empty", 
		several.isEmpty());
    
    //Test clear for slist
    slist.clear();
    assertTrue("slist should be empty", 
		slist.isEmpty());
  }

  /** 8 test size is correct for LinkList slist and several */
  @Test
  public void testSize() {

    int four = 4;
    int six = 6;
    int three = 3;
    
    //test size for several if element is added
    several.add(new Integer(four));
    assertEquals("checking if the size is correct", six, several.size());

    //test size for slist if element is added
    slist.add("Hello");
    assertEquals("checking if the size is correct", three, slist.size());
  }

  /** 9 test exception for add method */
  @Test
  public void addexception() {

    int eight = 8;
    int three = 3;
    int two = 2;
    int six = 6;
    
    //test if empty out of bound
    try {
      empty.add(1, new Integer(eight));
      fail("Should have generated an exception");
    }

    catch(IndexOutOfBoundsException e) {
      //  normal
    }

    
    //test if one is out of bound
    try {
      one.add(two, new Integer(eight));
      fail("Should have generated an exception");
    }

    catch(IndexOutOfBoundsException e) {

    }

          
    //test if several is out of bound
    try {
      several.add(six, new Integer(eight));
      fail("Should have generated an exception");
    }

    catch(IndexOutOfBoundsException e) {

    }

    //test if slist is out of bound
    try { 
      slist.add(three, "hiiii");
      fail("Should have generated an exception");  
    }

    catch(IndexOutOfBoundsException e) {

    }

  }

  /** 10 test excepetion for get method(might not count)*/
  @Test
  public void getexception() {

    int six = 6;
    int three = 3;
    
    //test if one is out of bound
    try {
      one.get(1);
      fail("Should have generated an exception");
    }
    catch(IndexOutOfBoundsException e) {
      //  normal
    }

    //check if several is out of bound
    try {
      several.get(six);
      fail("Should have generated an exception"); 
    }

    catch (IndexOutOfBoundsException e) {
    }

    //check if slist is out of bound
    try {
      slist.get(three);
      fail("Should have generated an exception");  
    }

    catch (IndexOutOfBoundsException e) {
    }
  }
 
  /** 11 test exception for set method */
  @Test
  public void setexception() {

    int six = 6;
    int five = 5;
    int three = 3;
    
    //check empty for out of bound
    try {
      empty.set(1, new Integer(1));
      fail("Should have generated an exception");

    }
    catch(IndexOutOfBoundsException e) {
      //  normal
    }

    //check one for out of bound
    try {
      one.set(1,new Integer(1));
      fail("Should have generated an exception");
    }

    catch(IndexOutOfBoundsException e) {

    }

    //check if several is out of bound
    try {
      several.set(six, new Integer(five));
      fail("Should have generated an exception"); 
    }

    catch(IndexOutOfBoundsException e) {

    }

    //check if slist is out of bound
    try {     
      slist.set(three, "hi");
      fail("Should have generated an exception");  
    }

    catch(IndexOutOfBoundsException e) {

    }

  }

  /** 12 test for remove out of bound exception */
  @Test
  public void removeexception() {

    int six = 6;
    int three = 3;

    //check empty for out of bound
    try {
      empty.remove(0);
      fail("Should have generated an exception");

    }
    catch(IndexOutOfBoundsException e) {
      //  normal
    }

    //check one for out of bound
    try {
      one.remove(1);
      fail("Should have generated an exception");
    }

    catch(IndexOutOfBoundsException e) {

    }

    //check if several is out of bound
    try {
      several.remove(six);
      fail("Should have generated an exception"); 
    }

    catch(IndexOutOfBoundsException e) {

    }

    //check if slist is out of bound
    try {     
      slist.remove(three);
      fail("Should have generated an exception");  
    }

    catch(IndexOutOfBoundsException e) {

    }
  }

  /** 13 test for illegalexception for add method */
  @Test
  public void addillegalexception() {
	
    //check if empty is add null
    try {
      empty.add(null);
      fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }

    //check if one is added null
    try {
      one.add(null);
      fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }

    //check if several is added null
    try {
      several.add(null);
      fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }

    //check if slist is added null
    try {
      slist.add(null);
      fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }
  }

  /** 14 check null exception */
  @Test
  public void insertillegalexception() {	

    int three = 3;

    //check if one is added null
    try {
      one.add(0,null);
      fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }

    //check if several is added null
    try {
      several.add(three,null);
      fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }

    //check if slist is added null
    try {
      slist.add(0,null);
      fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }
  }

  /***********Iterator Test*********************/

  /* iter 1 test if there is a next element in the list */
  @Test
  public void hasNextTest() {	  
    iterOne.previous();
    iterSeveral.previous();

    assertTrue("has no element", !iterNo.hasNext());
    assertTrue("one has element", iterOne.hasNext());
    assertTrue("several has element", iterSeveral.hasNext());
  }

  /* iter 2 test if there is a previous element in the list */
  @Test
  public void hasPrevTest() {
    assertTrue("has no element", !iterNo.hasPrevious());
    assertTrue("one has element", iterOne.hasPrevious());
    assertTrue("several has element",  iterSeveral.hasPrevious());
  }

  /* iter 3 if next() correctly moves forward and return the right element */
  @Test
  public void nextTest() {
    int two = 2;
    iterOne.previous();
 
    iterSeveral.previous();
    iterSeveral.previous();

    assertEquals("check one's next()", new Integer(0), iterOne.next());
    assertEquals("check several next()", new Integer(two), iterSeveral.next());
  }
    
  /* iter 4 if previous() correctly moves backward and return the right element */
  @Test
  public void previousTest() {
    int three = 3;
    
    iterSeveral.previous();
    iterSeveral.previous();

    assertEquals("check one's next()", new Integer(0), iterOne.previous());
    assertEquals("check several next()", new Integer(three), iterSeveral.previous());
  }
   
  /* test if nextIndex would return the index of the element */
  @Test
  public void nextIndexTest() {
    int five = 5;
    int four = 4;
    
    assertEquals("check one's return size", 1, iterOne.nextIndex());
    assertEquals("check several next()", five, iterSeveral.nextIndex());

    iterSeveral.previous();
    iterSeveral.previous();
    
    iterOne.previous();
    iterOne.next();
    
    iterSeveral.next();
    
    assertEquals("check one next() in the middle", 1, 
		 iterOne.nextIndex());
    assertEquals("check several next() in the middle", four, 
		 iterSeveral.nextIndex());
  }

  /* test if previousIndex would return the index of the element */
  @Test
  public void prevIndexTest() {
    iterOne.previous();
    
    assertEquals("check one's return size", -1, iterOne.previousIndex());
    
    for (int i = DIM; i > 0; i--) { 
      iterSeveral.previous();
    }

     assertEquals("check several next()", -1, iterSeveral.previousIndex());

     iterSeveral.next();
     iterSeveral.next();

     assertEquals("check several previousnext()", 1, iterSeveral.previousIndex());
  }

  /* test if iterator would return the right element */
  @Test
  public void iteratorsetTest() {
    iterOne.previous();
    iterOne.set(1);

    iterSeveral.previous();
    iterSeveral.set(0);
    assertEquals("check several set", new Integer(1) , iterOne.next());
    assertEquals("check several set", new Integer(0) , iterSeveral.next());
  }

  /* test if remove is correct for left and right */
  @Test
  public void iterRemoveTest() {
    iterSeveral.previous();
    iterSeveral.previous();

    iterSeveral.remove();
    
    assertEquals("check remove", new Integer(1) , iterSeveral.next());

    iterSeveral.previous();
    iterSeveral.previous();
    iterSeveral.next();
    iterSeveral.remove();

    assertEquals("check remove", new Integer(4) , iterSeveral.previous());

  }

  /* test if add for iterator is correct */
  @Test
  public void iterAddTest() {
    int four = 4;
    assertEquals("check add", new Integer(0), iterOne.previous());
    assertEquals("check add", new Integer(1), iterSeveral.previous());
  }

  /* check what happens if there is no next element */
  @Test
  public void iterNextException() {
    try {
      iterSeveral.next();
      fail("Should have generated an exception"); 
    }

    catch(NoSuchElementException e) {

    }
  }

  /* check what happens if there is no next element */
  @Test
  public void iterpreviousexception() {
    for (int i = DIM; i > 0; i--) {
      iterSeveral.previous();
    }
    
    try {
      iterSeveral.previous();
      fail("Should have generated an exception"); 
    }

    catch(NoSuchElementException e) {

    }
    
  }

  /* test if null argument are accpeted by set */
  @Test
  public void iterSetNullTest() {
    iterSeveral.previous();

    try {
     iterSeveral.set(null);
     fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }
  }

  /* test if next and prev has been called before set*/
  @Test
  public void iterNextAndPrevException() {
    try {
     iterSeveral.set(0);
     fail("Should have generated an exception"); 
    }

    catch(IllegalStateException e) {

    }
  }

  /*test if next and pre has been called before remove */
  @Test
  public void iterRemoveException() {	  
    try {
     iterSeveral.remove();
     fail("Should have generated an exception"); 
    }

    catch(IllegalStateException e) {

    }
  }

  /* Test for null before iterator adding */
  @Test
  public void iterAddNullTest() {
    try {
     iterSeveral.add(null);    
     fail("Should have generated an exception"); 
    }

    catch(IllegalArgumentException e) {

    }
  }
}
