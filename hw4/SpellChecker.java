//NAMES: Qiao jia, Lu, Dan Mu
//IDs: A13638993, A14719967
//EMAILS: qjlj@ucsd.edu, d1mu@ucsd.edu
//Login: cs12fbo, cs12rnj


package hw4;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

/** 
 * a class that check user spelling
 * and give suggested words
 */
public class SpellChecker {
	
  //declaration
  HashTable dictionary;
  String line;
  String[] suggest;
  LinkedList<String> suggestions;
  
  /**
   * set the dictionary and
   * use it as dictionary
   */
  public SpellChecker() {
    dictionary = new HashTable(1);
  }

  /**
   * reads each line in the text and puts it
   * in the dictionary.
   * @throws FileNotFoundException
   * @param dicFileReader the file to read
   */
  public void readDictionary(Reader dicFileReader) 
		     throws FileNotFoundException {
 
    //throws exception if the file is not there
    if (dicFileReader == null) {
      throw new FileNotFoundException("File Not Found");
    }

    //scan file
    Scanner scanner = new Scanner(dicFileReader);
   
    //add to dictionary 
    while (scanner.hasNext()) {
          
      dictionary.insert(scanner.next());

    }
  }

  /**
   * Create a Linkedlist and check the word
   * @param word that contains wrong letters 
   * @return suggestion
   */
  public String[] checkWord(String word) {
    
    //found word
    if (dictionary.contains(word)) {
      return null;
    }
 
    //create new LinkedList
    suggestions = new LinkedList<String>(); 
    
    //check word
    wrongLetter(word);
    insertLetter(word);
    deleteLetter(word);
    adjacentLetter(word);
    pairWithSpace(word);
    
    //Linklist to array
    toArray();

    return suggest;
  }

  
  /**
   * Suggest if there is wrong char
   * @param the word if one of the
   * character is wrong
   */
  public void wrongLetter(String word) {
	  
    //local variable
    int a = 97;
    int z = 123;
   
    //change to lower cases
    word = word.toLowerCase();

    //check wrong letter by changing letter
    for (int i = 0; i < word.length(); i++) {
      StringBuilder wordCheck = new StringBuilder(word);

    for (int j = a; j < z; j++) {
      wordCheck.setCharAt(i,(char)j);

      if (dictionary.contains(wordCheck.toString()) && 
        !suggestions.contains(wordCheck.toString())) {

        suggestions.add(wordCheck.toString());
        }
      } 
    }
  }

  /**
   * Suggest if there is an extra letter
   * @param the word if one of 
   * the letter is wrong
   *
   */
  public void insertLetter(String word) {

    //change to lower cases
    word = word.toLowerCase();

    //check wrong letter by deleting letter
    for (int i = 0; i < word.length(); i++) {

      //reset word and delete a character
      StringBuilder wordCheck = new StringBuilder(word);
      wordCheck.deleteCharAt(i);

      //check whether the word is in the dictionary
      //if the word is in the dictionary, add it
      if (dictionary.contains(wordCheck.toString()) && 
	    !suggestions.contains(wordCheck.toString())) {

	  suggestions.add(wordCheck.toString());
        }
    }
  }
  
  /**
   * Suggest if there is a missing letter
   * @param the word if one of the 
   * letter is missing 
   */
  public void deleteLetter(String word) {
    
    //local variable
    int a = 97;
    int z = 123;
    
    //change to lower cases
    word = word.toLowerCase();

    //check wrong letter by changing letter
    for (int i = 0; i <= word.length(); i++) {
      for (int j = a; j < z; j++) {

        //reset word and insert a character
        StringBuilder wordCheck = new StringBuilder(word);
	wordCheck.insert(i,(char)j);
         
	//check whether the word is in the dictionary
        //if the word is in the dictionary, add it
        if (dictionary.contains(wordCheck.toString()) && 
	    !suggestions.contains(wordCheck.toString())) {

	  suggestions.add(wordCheck.toString());
        }
      } 
    }
  }

  /**
   * Suggest if there are two letters need to switch their positions
   * @param the word if two letters need to
   * switch their positions
   */
  public void adjacentLetter(String word) {
    
    //change to lower cases
    word = word.toLowerCase();

    //check wrong letter by changing letter
    for (int i = 0; i < word.length() - 1; i++) {
	    
      //reset word and switch the words
      StringBuilder wordCheck = new StringBuilder(word);
      char temp1 = word.charAt(i);
      char temp2 = word.charAt(i + 1);
      wordCheck.setCharAt(i,temp2);
      wordCheck.setCharAt(i + 1, temp1); 
      
      //check whether the word is in the dictionary
      //if the word is in the dictionary, add it
      if (dictionary.contains(wordCheck.toString()) && 
	  !suggestions.contains(wordCheck.toString())) {
	      
        suggestions.add(wordCheck.toString());
      }
    }
  }
 
  /**
   * Suggest if there is no space between two words
   * @param the word with no space
   */
  public void pairWithSpace(String word) {
	  
    //change to lower cases
    word = word.toLowerCase();
	  
    //check wrong letter by changing letter
    for (int i = 1; i < word.length(); i++) {

      //reset word and add a space
      StringBuilder wordCheck = new StringBuilder(word);
      wordCheck.insert(i," ");
      String first = wordCheck.substring(0, i);
      String second = wordCheck.substring(i + 1, word.length() + 1);

      //check whether the word is in the dictionary
      //if the word is in the dictionary, add it
      if (dictionary.contains(first) && dictionary.contains(second) && 
          !suggestions.contains(first) && !suggestions.contains(second)) {
	      
        suggestions.add(wordCheck.toString());
      }
    }
  }

  /**
   * Convert the array to linkedlist
   */
  public void toArray() {
    //local variable
    suggest = new String[suggestions.size()];

    //put elements into array
    for (int i = 0; i < suggestions.size(); i++) {
      suggest[i] = suggestions.get(i);
    }
  }
}



