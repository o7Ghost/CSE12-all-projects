/*
 * NAMES:<Ziyi Wang; Qiao jia, Lu>
 * IDS:<A92032448; A13638993>
 * EMAILS:<ziw125@ucsd.edu; qjlu@ucsd.edu>
 * Login:<cs12ffm, cs12fbo>
 */

package hw8;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tester QuantityTester for Quantity class
 */
public class QuantityTester {  //this is the tester
	
  //initialize private fields
  private Quantity argument1; 
  private Quantity argument2; 
  private Quantity multiple; 
  private Quantity division;
  private List<String> emp;

  @Before
  public void setUp() { //this will be run before each test

	emp = Collections.<String>emptyList();
    argument1 = new Quantity(2.0, Arrays.asList("m"), 
		             Arrays.asList("s", "s"));

    argument2 = new Quantity(4.0, Arrays.asList("s"), emp);

    multiple = new Quantity(8.0, Arrays.asList("m"), 
		            Arrays.asList("s"));   
  }
  
  /** Test no argument constructor
   * This constructor creates a default quantity of value 1 and no units.
   */
  @Test
  public void testNoArgument() {

    Quantity noArgument1 = new Quantity();
    Quantity noArgument2 = new Quantity();
    
    assertTrue("value 1 and no units", noArgument1.toString().equals("1.0"));
    assertEquals("two noArgument Quantity are same", true, 
    		noArgument1.equals(noArgument2));   
  }

  /** Test deep copy constructor
   * This constructor takes a single Quantity argument,
   * and creates new Quantity object
   */
  @Test
  public void testDeepCopy() {
	  
    double five = 5.0;
    Quantity deepArgument1 = new Quantity(five, Arrays.asList("m"), emp);
    Quantity deepArgument2 = new Quantity(deepArgument1);

    assertEquals("the content should be same", deepArgument1.toString(),
    		deepArgument2.toString());	
    assertTrue("the content should be same", 
    		deepArgument1.equals(deepArgument2));
    assertFalse("deep copy are different objects", 
    		deepArgument1 == deepArgument2);	 
  }

  /** Test 3-argument constructor
   * This constructor takes a double, and two List<String>
   */
  @Test
  public void testThreeArgument() {
	  
	  assertTrue("check argument2", argument2.toString().equals("4.0 s"));
	  assertTrue("check argument1", argument1.toString().equals("2.0 m s^-2"));	  
  }
  
  /** Test 3-argument constructor Exception
   * @throws IllegalArgumentException, if either of list arguments is null
   */
  @Test
  public void testThreeArgumentException() {
    
   try {
     Quantity argument2 = new Quantity(9.8, null, null);
     fail("should generate IllegalArgumentException");
   }

   catch (IllegalArgumentException e) {
   }  
  }
  
  /** Test Exception for method mul
   * @throws IllegalArgumentException, if if its argument is null
   */
  @Test
  public void testMul_Exception() {
	  
    try {
      argument1.mul(null);
      fail("Should have generated an IllegalArgumentException");  
      }
    catch(IllegalArgumentException e) {
    }   
  }
  
  /** Test method mul in Quantity
   * It takes a single Quantity argument, multiplies this by the argument,
   *  and returns the result as new Quantity object.
   * check:neither this quantity nor the argument quantity should change!
   */
  @Test
  public void testMul() {

    Quantity argument4 = argument1.mul(argument2);
     
    assertEquals("check mul result", true, multiple.equals(argument4)); 
	assertTrue("check argument4", argument4.toString().equals("8.0 m s^-1"));
	assertTrue("argument1 should not change", argument1.toString().equals("2.0 m s^-2"));
	assertTrue("argument2 should not change", argument2.toString().equals("4.0 s"));	
  }

  /** Test Exception1 for method div
   * @throws IllegalArgumentException, if its argument is null 
   */
  @Test
  public void testDiv_Exception1() {
	  
    try {
      argument1.div(null);
      fail("Should have generated an IllegalArgumentException");  
      }
    catch(IllegalArgumentException e) {
    }    
  }
  
  /** Test Exception2 for method div
   * @throws IllegalArgumentException, if the value in the Quantity
   *  argument is zero.
   */
  @Test
  public void testDiv_Exception2() {
	  
    try {
      argument1.div(new Quantity(0, Arrays.asList("m"), 
    		  Arrays.asList("s", "s")));
      fail("Should have generated an IllegalArgumentException");  
      }
    catch(IllegalArgumentException e) {
    }   
  }
  
  /** Test method div in Quantity
   * It takes a single Quantity argument, divides this by the argument,
   *  and returns the result as new Quantity object.
   * check:neither this quantity nor the argument quantity should change!
   */
  @Test
  public void testDiv() {

    Quantity argument4 = multiple.div(argument2);
    assertEquals("check div result", true, argument1.equals(argument4));
	assertTrue("check argument4", argument4.toString().equals("2.0 m s^-2"));
	assertTrue("multiple should not change", multiple.toString().equals("8.0 m s^-1"));
	assertTrue("argument2 should not change", argument2.toString().equals("4.0 s"));	
  }

  /** Test method power in Quantity
   * It takes a single int argument (positive, negative, or zero!),
   *  raises this to the given power,
   *  and returns the result as new Quantity object.
   *check:this quantity should not change!
   */
  @Test
  public void testPower() {
	  
    Quantity correct1 = new Quantity(16.0, Arrays.asList("s", "s"), emp);
    Quantity correct2 = new Quantity(1.0, emp, emp);
    
    Quantity argument4 = argument2.pow(2);
    Quantity argument5 = argument2.pow(0);

    assertEquals("check pow", true, argument4.equals(correct1));
    assertEquals("check pow zero", true, argument5.equals(correct2));
    assertTrue("check result", argument4.toString().equals("16.0 s^2"));
    assertTrue("check result", argument5.toString().equals("1.0"));
    assertTrue("argument2 should not change", argument2.toString().equals("4.0 s"));
  }

  /** Test Exception1 for method add
   * @throws IllegalArgumentException, if its argument is null 
   */
  @Test
  public void testAdd_Exception1() {
	  
    try {
      argument1.add(null);
      fail("Should have generated an IllegalArgumentException");  
      }
    catch(IllegalArgumentException e) {
    }   
  }
  
  /** Test Exception2 for method add
   * @throws IllegalArgumentException, if the two Quantity objects 
   * do not have the same units.
   */
  @Test
  public void testAdd_Exception2() {
	  
    try {
      argument1.add(new Quantity(8.0, emp, emp));    
      fail("Should have generated an IllegalArgumentException");  
      }
    catch(IllegalArgumentException e) {
    }    
  }
  
  /** Test method add in Quantity
   * It takes a single Quantity argument, adds this to it,
   *  and returns the result as new Quantity object.
   * check:neither this quantity nor the argument quantity should change!
   */
  @Test
  public void testAdd() {
   
    Quantity correct = new Quantity(8.0, Arrays.asList("s"), emp);

    Quantity argument4 = argument2.add(argument2);

    assertEquals("check add result", true, argument4.equals(correct));
	assertTrue("check argument4", argument4.toString().equals("8.0 s"));
	assertTrue("argument2 should not change", argument2.toString().equals("4.0 s"));	
  }


  /** Test Exception1 for method sub
   * @throws IllegalArgumentException, if its argument is null 
   */
  @Test
  public void testSub_Exception1() {
	  
    try {
      argument1.sub(null);
      fail("Should have generated an IllegalArgumentException");  
      }
    catch(IllegalArgumentException e) {
    }   
  }
  
  /** Test Exception2 for method add
   * @throws IllegalArgumentException, if the two Quantity objects 
   * do not have the same units.
   */
  @Test
  public void testSub_Exception2() {
	  
    try {
      argument1.sub(new Quantity(8.0, emp, emp));    
      fail("Should have generated an IllegalArgumentException");  
      }
    catch(IllegalArgumentException e) {
    }   
  } 
  
  /** Test method sub in Quantity
   * It takes a single Quantity argument, subtracts it from this,
   *  and returns the result as new Quantity object.
   * check:neither this quantity nor the argument quantity should change!
   */
  @Test
  public void testSub() {
   
    Quantity correct = new Quantity(0.0, Arrays.asList("s"), emp);
    
    Quantity argument4 = argument2.sub(argument2);
    
    assertEquals("check sub result", true, argument4.equals(correct));
	assertTrue("argument2 should not change", argument2.toString().equals("4.0 s"));	
  }

  /** Test method negate in Quantity
   * It takes no arguments and returns a new Quantity
   *  which is the negation of this Quantity
   * check:this quantity should not change!
   */
  @Test
  public void testNegate() {

    Quantity correct = new Quantity(-4.0, Arrays.asList("s"), emp);

    Quantity argument4 = argument2.negate();

    assertEquals("check Negate result", true, argument4.equals(correct));
    assertTrue("argument2 should not change", argument2.toString().equals("4.0 s"));   
  }
  
  /** Test method equal in Quantity
   * It takes any single Object, 
   * and returns true if and only if that object is a Quantity 
   * whose units are exactly the same as the calling object 
   * and whose value is the same 
   * when rounded to five places after the decimal point.
   */
  @Test
  public void testEquals() {
	 Quantity same = new Quantity(2.000001, Arrays.asList("m"), 
			 Arrays.asList("s", "s"));
	 Quantity diff = new Quantity(2.0004, Arrays.asList("m"), 
			 Arrays.asList("s", "s"));
	  
     assertEquals("check equals", true, argument1.equals(argument1));
     assertEquals("check equals", true, same.equals(argument1));
     assertEquals("check equals", false, diff.equals(argument1));     
  }

  /** Test method hashCode in Quantity
   * It returns an integer, 
   *  such that equal Quantities always return the same integer. 
   */
  @Test
  //test hashCode in Quantity
  public void testHashCode() {

     Quantity correct = new Quantity(2.0, Arrays.asList("m"), 
		            Arrays.asList("s", "s"));

     int a = argument1.hashCode();
     int b = correct.hashCode();

     assertEquals("check hashCode", true, (a == b));
  }
  
  /** Test method normalizeUnit in Quantity
   * It takes a String(name of a unit) and a Map<String,Quantity>(database)
   * It creates a new normalized Quantity equivalent to 1 of the given unit
   */
  
  @Test
  public void testNormalizeUnit() {
	  
    Quantity correct = new Quantity(3600.0, Arrays.asList("second"), emp); 

    Quantity argument4 = Quantity.normalizedUnit("hour",QuantityDB.getDB());
    
    assertEquals("check normalize", true, argument4.equals(correct));   
  }

  /** Test method normalize in Quantity
   * It takes a Map<String,Quantity>(database)
   *  and returns a copy of this but in normalized form 
   */
  @Test
  public void testNormalize() {
    
    Quantity normalize1 = new Quantity(1.0, Arrays.asList("hour"), emp); 
    Quantity normalize2 = new Quantity(60.0, Arrays.asList("Hz"), emp); 
    Quantity normalize3 = new Quantity(30.0, Arrays.asList("s"), emp); 
    Quantity normalize4 = normalize2.mul(normalize3);


    
    Quantity correct1 = new Quantity(3600.0, Arrays.asList("second"), emp); 
    Quantity correct2 = new Quantity(1800.0, emp, emp); 

    Quantity argument4 = normalize1.normalize(QuantityDB.getDB());
    Quantity argument5 = normalize4.normalize(QuantityDB.getDB());
    
    System.out.println(argument5.toString()); 
    assertEquals("check normalize1", true, argument4.equals(correct1));
    assertEquals("check normalize4", true, argument5.equals(correct2));
  }
}  
