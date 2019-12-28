//NAMES: Qiao jia, Lu, Dan Mu
//IDs: A13638993, A14719967
//EMAILS: qjlj@ucsd.edu, d1mu@ucsd.edu
//Login: cs12fbo, cs12rnj

package hw4;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * creates a hashtable and its methods
 */
public class HashTable implements IHashTable {
	
  //You will need a HashTable of LinkedLists. 
	
  private int nelems;  //Number of element stored in the hash table
  private int expand;  //Number of times that the table has been expanded
  private int collision;  //Number of collisions since last expansion
  private String statsFileName;     //FilePath for the file to write statistics upon every rehash
  private boolean printStats = false;   //Boolean to decide whether to write statistics to file or not after rehashing
  private LinkedList<String>[] hashArray;
  private float LOAD_FACTOR = (float)2/3;
  private float load;
  
  /**
   * Constructor for hash table
   * @param Initial size of the hash table
   */
  public HashTable(int size) {
	  
    //declaration of array and size
    hashArray = new LinkedList[size];
  }
	
  /**
   * Constructor for hash table
   * @param Initial size of the hash table
   * @param File path to write statistics
   */
  public HashTable(int size, String fileName){
    
    //declaration
    hashArray = new LinkedList[size];
    statsFileName = fileName;
    printStats = true;
    //Set printStats to true and statsFileName to fileName
  }

  @Override
  /**
   * insert a new element in the array
   * @param value the element that needs to be
   * inserted
   * @return tell if element is inserted or can be
   * inserted
   */
  public boolean insert(String value) throws NullPointerException {
   
    //local variable
    boolean inserted = false;

    //throws exception if value is null
    if (value == null) {
      throw new NullPointerException("value is null");
    }
	    
    //load factor after a word has been inserted
    //if not, recalculate load
    if (!contains(value)) {

      load = (float)(nelems + 1)/hashArray.length;
    }

    else {
	    
      load = nelems/hashArray.length;
    }
    
    //check for collision
    if (hashArray[hashCode(value)] != null 
        && !contains(value)) {
	    
      collision++;
    }

    //check if load factor is over
    //if it is, then rehash
    if (load > LOAD_FACTOR) {

      expand++;

      //print statistic 
      if (printStats) {

        try {
		
	  printStatistics();
	}

	catch (IOException e) {

        }
      }

      rehash();
    }
    
    //check if index has been created
    if (hashArray[hashCode(value)] == null) {

      hashArray[hashCode(value)] = new LinkedList<String>();
    }
     
    //check if value has inserted before
    if (!contains(value)) {
     
      //add element
      hashArray[hashCode(value)].add(value);
      nelems++;
      inserted = true;
    }
	
    return inserted;   
  }

  @Override
  /**
   * delete an element if it exist
   * @param the element that needs to be deleted
   * @return tell if element is deleted or has
   * been deleted
   */
  public boolean delete(String value) throws NullPointerException{
    
    //loccal variable
    boolean exists = false;

    //throws exception if value is null
    if (value == null) {
      throw new NullPointerException("value is null");
    }

    //check whether the value is null or not
    //check whether it is the word that needs to be delete
    if (hashArray[hashCode(value)] != null && 
	hashArray[hashCode(value)].contains(value)) {
	    
      hashArray[hashCode(value)].remove(value);
      nelems--;
      exists = true;
    }
   
    return exists;	  
  }

  @Override
  /**
   * check if the element contains a element
   * @param the element that needs to be checked
   * @return tell if the element is contain or not
   */ 
  public boolean contains(String value) throws NullPointerException {

    boolean contains = false;

    //throw exception if the value is null
    if(value == null){
      throw new NullPointerException("value is null");
    }

    //check whether the value is null or not
    //check whether it is the word is contained
    if (hashArray[hashCode(value)] != null &&
	hashArray[hashCode(value)].contains(value)) {
	    
      contains = true;
    }
	
    return contains;    
  } 

  @Override
  /**
   * print the table of the array and its elements
   */
  public void printTable() {

    //local variable
    String colliPrint = "";

    //create an array
    for (int i = 0; i < hashArray.length; i++) {

      //check whether the element is null
      if (hashArray[i] != null) {
	
	//check whether the array size is legal
	if (hashArray[i].size() > 1) {

          //print the table and its elements
          for (int j = 0; j < hashArray[i].size() - 1; j++) {
		  
            colliPrint = hashArray[i].get(j) + ", " + colliPrint;	  
          }
	  
            colliPrint = colliPrint + hashArray[i].get(hashArray[i].size() - 1);
	  }
	else {
		
	  colliPrint = hashArray[i].get(0);
	}
      }
      
      System.out.println(i + ": " + colliPrint);
      colliPrint = "";
    }
  }
	
  @Override
  /** 
   * get the size of the element
   * @return returns the number of element
   */
  public int getSize() {

    return nelems;
  }

  /**
   * creates a hashcode for the object
   * @param use string input as unique keys
   * @return returns the hashcode
   */
  public int hashCode(String key) {

    //local variables
    int pow27 = 27;
    int hashVal = 0;
    
    //empty string, set 0
    if (!key.equals("")) { 
      hashVal = key.charAt(0);
    }
    
    //the length is 1, take the ascii value as the index
    if (key.length() == 1) {
      hashVal = hashVal % hashArray.length;
    }

    //any other length, take remainder 
    for (int i = 1; i < key.length(); i++) {

      int letter = key.charAt(i);
      hashVal = (hashVal * pow27 + letter) % hashArray.length;
    }

    return hashVal;
  }

  /**
   * resizes the table and reset the collision count
   */
  public void rehash() {

    //local variable
    int two = 2;

    //create a new linkedlist
    //double the length
    LinkedList<String>[] temp = hashArray;
    hashArray = new LinkedList[temp.length * two];
    
    //insert element into new array
    for (int i = 0; i < temp.length; i++) {

      //check if the array index is not null
      if (temp[i] != null) {

        for (int j = 0; j < temp[i].size(); j++) {

	  //if linkedlist element is null
	  //create a new linkedlist and add elements
	  if (hashArray[hashCode(temp[i].get(j))] == null) {

	    hashArray[hashCode(temp[i].get(j))] = new LinkedList();
            hashArray[hashCode(temp[i].get(j))].add(temp[i].get(j));
	  }

	  //if linkedlist element is not null
	  //add elements
	  else {

	    hashArray[hashCode(temp[i].get(j))].add(temp[i].get(j));
	  }
	}
      }
    }
    
    
    collision = 0;
  }

  /**
   * finds the largest collision china
   * @return returns the largest collision
   * chain
   */
  public int largestChain() {

    //local variable
    int largest = 0;

    //check the largest chain
    for (int i = 0; i < hashArray.length; i++) {

      //if elements index is not null
      //return largest size
      if (hashArray[i] != null) {

      if (largest < hashArray[i].size()) {
        largest = hashArray[i].size();
      }
      }
    }

    return largest;
  }

 
  /**
   * print Statistics on a seperate file
   */ 
  public void printStatistics() throws IOException {

    //throw exception if statesFileName is null
    if (statsFileName == null) {
      throw new IOException("file null or not found");
    }

      //create new file
      File file = new File(statsFileName);
      file.createNewFile();

      //create new writer
      FileWriter writer = new FileWriter(file, true); 
    
      //print file
      writer.write(expand + " resizes, " + load + " load factor, " +
      collision + " collisions, " + largestChain() + " Longest Chain\n");
      writer.close();
    }
  
    /* TODO - Helper methods can make your code more efficient and look neater.
     * We expect AT LEAST the follwoing helper methods in your code
     * rehash() to expand and rehash the items into the table when load factor goes
     * over ththreshold.
	 * printStatistics() to print the statistics after each expansion. This method 
     * will be called from insert/rehash only if printStats=true
     */

}
