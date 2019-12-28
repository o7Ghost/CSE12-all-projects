//NAMES: Qiao jia, Lu, Dan Mu
//IDs: A13638993, A14719967
//EMAILS: qjlj@ucsd.edu, d1mu@ucsd.edu
//Login: cs12fbo, cs12rnj

package hw4;
import org.junit.*;
import static org.junit.Assert.*;

/* test the tashtable is implemented correctly */
public class HashtableTester {
  //declaration
  private HashTable testHashTable1;
  private HashTable testHashTable2;

  @Before
  public void setUp() {
    testHashTable1 = new HashTable(1);
    testHashTable2 = new HashTable(1,"printStats.txt");
  }
  
  @Test
  public void testInsert() {
    assertEquals("checking insert",true,testHashTable1.insert("abc"));
    assertEquals("Checking contains after insert",true,testHashTable1.contains("abc"));
  }

  @Test
  public void testDelete() {
    testHashTable1.insert("abc");
    assertEquals("Checking delete",true,testHashTable1.delete("abc"));
    assertEquals("Checking contains after delete",false,testHashTable1.contains("abc"));
  }

  @Test
  public void testGetSize() {
    testHashTable1.insert("abc");
    testHashTable1.insert("pqr");
    testHashTable1.insert("xyz");
    assertEquals("Checking getSize",new Integer(3),new Integer(testHashTable1.getSize()));
  }

  
  
  /*test insert for same element */
  @Test
  public void insertSameTest() {
    testHashTable1.insert("abc");
    assertEquals("checking insert same element",false,
		  testHashTable1.insert("abc"));
  }

  /*test delete for same element */
  @Test
  public void deleteNoneTest() {
    assertEquals("Checking delete same element",false,
		  testHashTable1.delete("abc")); 
  }
  
  /*Test contain none existing element */
  @Test
  public void containNoneTest() {
    assertEquals("Checking contains after delete",false,
		    testHashTable1.contains("abc"));
  }

  /*Test size for empty elments */
  @Test
  public void sizeEmptyTest() {
    assertEquals("Checking getSize",new Integer(0),
		  new Integer(testHashTable1.getSize()));
  }

  /*Test insert empty String*/
  @Test
  public void insertEmptyString() {
    testHashTable1.insert("");
    assertEquals("Checking empty String",true,testHashTable1.contains(""));
    assertEquals("Checking getSize",new Integer(1),new Integer(testHashTable1.getSize()));
  }
  
  /*test for insertion null exception*/
  @Test
  public void insertExceptionTest() {
    try {
      testHashTable1.insert(null);
      fail("should generate exception");
    }

    catch (NullPointerException e) {
    }
  }

  /*Test for remove null exception */
  @Test
  public void deleteExceptionTest() {
    try {
    testHashTable1.delete(null);
    fail("should generate exception");
    }

    catch (NullPointerException e) {
    }
  }

  /*Test for contains null exception */
  @Test
  public void containException() {
    try {
    testHashTable1.contains(null);
    fail("should generate exception");
    }
    catch (NullPointerException e) {
    }
  }
  
  /*Test for delete after insert*/
  @Test
  public void testDeleteAfter() {
    testHashTable1.insert("a");
    testHashTable1.insert("b");
    testHashTable1.delete("b");
    assertEquals("testing delete after", new Integer(1), new Integer(testHashTable1.getSize()));
    assertEquals("test if contains", false, testHashTable1.contains("abc"));
  }
  
  /*Test for insert after delete*/
  @Test
  public void insertAfterDelete(){
    testHashTable1.insert("a");
    testHashTable1.insert("A");
    testHashTable1.delete("A");
    testHashTable1.insert("B");

    assertEquals("testing insert after delete", false, testHashTable1.contains("aAB")); 
  } 

  /*Test whether it contains value for single character*/
  @Test
  public void containsValue(){
    testHashTable1.insert("a");
    testHashTable1.insert("b");

    assertEquals("test contains value", true, testHashTable1.contains("a"));
  }
}
