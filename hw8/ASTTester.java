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
 * Tester ASTTester for AST class
 */
public class ASTTester { //this is the tester

  //initialize private fields
  private List<String> emp;
  private AST product;
  private AST quotient;
  private AST sum;
  private AST difference;
  private AST power;
  private AST negation;
  private AST normalize;
  private AST define;
  private AST value;
  private AST node1;
  private AST node2;

  @Before
  public void setup() { //this will be run before each test
	  
	emp = Collections.<String>emptyList();
	
    node1 = new Value(new Quantity(2.0,  Arrays.asList("s"), emp));
    node2 = new Value(new Quantity(1.0, Arrays.asList("s"), emp)); 
 
    product  = new Product(node1, node2);
    quotient = new Quotient(node1, node2);
    sum = new Sum(node1,node2);
    difference = new Difference(node1,node2);
    power = new Power(node1, 2);
    negation = new Negation(node1);
    define = new Define("smooth", node1);
  }

  /** Test method eval for Product class 
   */
  @Test
  public void test_Product_eval() {
	  
    Quantity correct = new Quantity(2.0, Arrays.asList("s", "s"), emp);
    Quantity result = product.eval(QuantityDB.getDB());
    
    assertEquals("check product", true, result.equals(correct));
  }

  /** Test method eval for Quotient class 
   */
  @Test
  public void test_Quotient_eval() {
	  
    Quantity correct = new Quantity(2.0,  emp, emp);
    Quantity result = quotient.eval(QuantityDB.getDB());
    
    assertEquals("check quotient", true, result.equals(correct));
  }

  /** Test method eval for Sum class 
   */
  @Test
  public void test_Sum_eval() {
	  
    Quantity correct = new Quantity(3.0, Arrays.asList("s"), emp);
    Quantity result = sum.eval(QuantityDB.getDB());
    
    assertEquals("check sum", true, result.equals(correct));
  }

  /** Test method eval for Difference class 
   */
  @Test
  public void test_Difference_eval() {
	  
    Quantity correct = new Quantity(1.0, Arrays.asList("s"), emp);
    Quantity result = difference.eval(QuantityDB.getDB());
    
    assertEquals("check difference", true, result.equals(correct));
  }

  /** Test method eval for Power class 
   */
  @Test
  public void test_Power_eval() {
	  
    Quantity correct = new Quantity(4.0, Arrays.asList("s", "s"), emp);
    Quantity result = power.eval(QuantityDB.getDB());
    
    assertEquals("check power", true, result.equals(correct));
  }

  /** Test method eval for Negation class 
   */
   @Test
  public void test_Negation_eval() {
	   
    Quantity correct = new Quantity(-2.0, Arrays.asList("s"), emp);
    Quantity result = negation.eval(QuantityDB.getDB());
    
    assertEquals("check negation", true, result.equals(correct));
  }

   /** Test method eval for Normalize class 
    */
   @Test
  public void test_Normalize_eval() {

    AST node = new Value(new Quantity(1.0,  Arrays.asList("hr"), emp));
    normalize = new Normalize(node);
    Quantity correct = new Quantity(3600.0, Arrays.asList("second"), emp);
    Quantity result = normalize.eval(QuantityDB.getDB());

    assertEquals("check normalize", true, result.equals(correct));
  }

   /** Test method eval for Define class 
    */
   @Test
  public void test_Define_eval() {
	   
    Quantity correct = new Quantity(2.0, Arrays.asList("s"), emp);
    Quantity result = define.eval(QuantityDB.getDB());
    
    assertEquals("check define", true, result.equals(correct));
  }

   /** Test method eval for Value class 
    */
  @Test
  public void test_Value_eval() {
	  
     Quantity correct = new Quantity(2.0, Arrays.asList("s"), emp);
     Quantity result = node1.eval(QuantityDB.getDB());
     
     assertEquals("check value", true, result.equals(correct));
  }
}
