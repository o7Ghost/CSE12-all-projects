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
 * Tester UnicalcTester for Unicalc class
 */
public class UnicalcTester { //this is the tester
	
  //initialize private fields
  private List<String> emp;
  Unicalc test;
  Unicalc test1;
  Unicalc test2;
 
  @Before
  public void setup() { //this will be run before each test

	emp = Collections.<String>emptyList();
    test = new Unicalc();
    test1 = new Unicalc();
    test2 = new Unicalc();
  }

  /** Test R() method 
   */
  @Test
  public void test_R() {

    AST node1 = new Value(new Quantity(2.0,  emp, emp));
    AST node2 = new Value(new Quantity(1.0, Arrays.asList("s"), emp));  
    AST product1  = new Product(node1, node2);
    AST correct1 = new Power(product1, 2);
    
    test1.tokenize("(2s)^2");
    test2.tokenize("2");
    
    assertEquals("test R method", true, test1.R().equals(correct1));  
    assertEquals("test R method", true, test2.R().equals(node1)); 
  }
  
  /** Test Q() method 
   */
  @Test
  public void test_Q() {
    
    AST node1 = new Value(new Quantity(2.0,  emp, emp));
    AST node2 = new Value(new Quantity(1.0, Arrays.asList("meter"), emp)); 
    AST correct = new Product(node1, node2);

    test.tokenize("2 meter");

    assertEquals("test Q method", true, test.Q().equals(correct));
  }

  /** Test K() method 
   */
  @Test
  public void test_K() {
    
    AST node1 = new Value(new Quantity(1.0,  emp, emp));
    AST node2 = new Value(new Quantity(1.0, Arrays.asList("s"), emp)); 
    AST product = new Product(node1, node2);
    AST correct = new Negation(product);

    test.tokenize("-1s");

    assertEquals("test K method", true, test.K().equals(correct)); 
  }

  /** Test P() method 
   */
   @Test
  public void test_P() {

    AST node1 = new Value(new Quantity(60.0, emp, emp));
    AST node2 = new Value(new Quantity(30.0, emp, emp));
    AST node3 = new Value(new Quantity(1.0, Arrays.asList("Hz"), emp));
    AST node4 = new Value(new Quantity(1.0, Arrays.asList("s"), emp));
    AST product1 = new Product(node1, node3);
    AST product2 = new Product(node2, node4);

    AST correct1 = new Product(product1, product2);
    AST correct2 = new Quotient(product1, product2);

    test1.tokenize("60 Hz * 30s");
    test2.tokenize("60 Hz / 30s");
    
    assertEquals("test P method", true, test1.P().equals(correct1)); 
    assertEquals("test P method", true, test2.P().equals(correct2)); 
  }

   /** Test E() method 
    */
  @Test
  public void test_E() {

    AST node1 = new Value(new Quantity(14.0, emp, emp));
    AST node2 = new Value(new Quantity(9.0, emp, emp));
    AST node3 = new Value(new Quantity(1.0, Arrays.asList("m"), emp));
    AST product1 = new Product(node1, node3);
    AST product2 = new Product(node2, node3);
    
    AST correct1 = new Sum(product1,product2);
    AST correct2 = new Difference(product1,product2);
   
    test1.tokenize("14m+9m");
    test2.tokenize("14m-9m");
    
    assertEquals("test E method", true, test1.E().equals(correct1)); 
    assertEquals("test E method", true, test2.E().equals(correct2)); 
  }

  /** Test L() method 
   */
  @Test
  public void test_L() {

    AST node1 = new Value(new Quantity(60.0, emp, emp));
    AST node2 = new Value(new Quantity(30.0, emp, emp));
    AST node3 = new Value(new Quantity(1.0, Arrays.asList("Hz"), emp));
    AST node4 = new Value(new Quantity(1.0, Arrays.asList("s"), emp));
    
    AST product1 = new Product(node1, node3);
    AST product2 = new Product(node2, node4);
    AST product3 = new Product(product1, product2);
    AST correct = new Normalize(product3); 

    test.tokenize("#60 Hz * 30s");
    
    assertEquals("test L method", true, test.L().equals(correct)); 
  }

  /** Test S() method 
   */
  @Test
  public void test_S() {

    AST node1 = new Value(new Quantity(67.0, emp, emp));
    AST node2 = new Value(new Quantity(1.0, Arrays.asList("in"), emp));
    AST product1 = new Product(node1, node2);
    AST correct = new Define("smoot", product1);
    
    test.tokenize("def smoot 67 in");

    assertEquals("test S method", true, test.S().equals(correct)); 
  }
}
