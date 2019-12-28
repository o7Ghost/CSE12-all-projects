/** 
 * Class RockPaperScissors.  
 * @author Qiao jia Lu
 * @10/6/17
 */

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

/**
 * RockPaperScissors :a game of rock paper scissors 
 * with the computer
 */
public class RockPaperScissors {

  private static int userMCount;
  private static double youWin;
  private static double youLose;
  private static double tie;
  private static int arraySize;

  /**
   * checkNexpand: Helper method for expanding array
   *
   * @Counter the size of the current array
   * @userM the array and its elements
   *
   * @return the new array
   */
  public static String[] checkNexpand(int Counter, String[] userM) {
    String[] temp = new String[Counter];
    for (int i = 0; i < userM.length; i++) {
      temp[i] = userM[i];
    }
    userM = temp;

    return userM;
  }
  
  /**
   * calStats: calculate and display the results
   *
   * @winC the win count of the user
   * @loseC the lose count of the user
   * @tieC the tie count of user and system
   */
  public static void calStats(double winC, double loseC, double tieC) {

    //local variable
    double totalC = 0.0;
    int PERCENT = 100;
    double youWinStats = 0.0;
    double youLoseStats = 0.0;
    double tieStats = 0.0;
    
    //calculating statistis
    totalC = winC + loseC + tieC;
    youWinStats = winC/totalC * PERCENT;
    youLoseStats = loseC/totalC * PERCENT;
    tieStats =  tieC/totalC * PERCENT;

    //print results
    System.out.println("Our overall stats are: ");
    System.out.println("I won: " + (int)youLoseStats + "%" + "       " + 
		       "You won: " + (int)youWinStats + "%" + "     " + 
		       "We tied: " + (int)tieStats + "%");
  }

  /**
   * recentHistory: display the recent play history
   *
   * @userM the history of user gameplay
   * @systemM the history of system gameplay
   * @userC the total count of user moves
   */
  public static void recentHistory(String[] userM, 
		                   LinkedList<String> systemM, int userC) {
    int maxiumdisplay = userC;
    int DISPLAY_LIMIT = 10;

    if (userC > DISPLAY_LIMIT) {
      maxiumdisplay = DISPLAY_LIMIT;
    }

    System.out.println("Thanks for playing!");
    System.out.println("Our most recent games (in reverse order) were: ");

    for (int i = 0; i < maxiumdisplay; i++) {
      System.out.println("Me: " + 
			  systemM.get((systemM.size() - 1 - i)) 
			  + "   " + 
			  "You: " + userM[userC - 1 - i]);
    }  
  }

  /**
   * rockValid: count both system moves and user moves then match
   * them to see who wins
   *
   * @rngN system move
   * @userMo the history of user moves
   * @systemMo the history of system moves
   */
  public static String[] rockValid (int rngN, String[] userMo,
		                LinkedList<String> systemMo) {
    //delcaration
    int DOUBLE = 2;
    String r = "rock";
    String p = "paper";
    String s = "scissors";
    
    //option for rock vs rock
    if (rngN == 0) {

      System.out.println("I choose rock. It's a tie");
      
      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }
	   
      userMo[userMCount] = r;
      systemMo.add(r);
      userMCount++;
      tie++;  
    }   

    //option for rock vs paper
    else if (rngN == 1) {

      System.out.println("I choose paper. I win!");

      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }

      userMo[userMCount] = r;
      systemMo.add(p);
      userMCount++;
      youLose++;	 
    }

    //option for rock vs scissors
    else {

      System.out.println("I choose scissors. You win.");
	 
      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }

      userMo[userMCount] = r;
      systemMo.add(s);
      userMCount++;
      youWin++;
    }

    return userMo;

  }
  
  /**
   * paperValid: count both system moves and user moves then match
   * them to see who wins
   *
   * @rngN system move
   * @userMo the history of user moves
   * @systemMo the history of system moves
   */
  public static String[] paperValid (int rngN, String[] userMo,
		                LinkedList<String> systemMo) {
    //delcaration
    int DOUBLE = 2;
    String r = "rock";
    String p = "paper";
    String s = "scissors";
    
    //option for paper vs rock
    if (rngN == 0) {

      System.out.println("I choose rock. You win.");
	 
      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }
	   
      userMo[userMCount] = p;
      systemMo.add(r);
      userMCount++;
      youWin++;
    }

    //option for paper vs paper
    else if (rngN == 1) {

      System.out.println("I choose paper. It's a tie");
     
      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }
	 
      systemMo.add(p);
      userMo[userMCount] = p;
      userMCount++;
      tie++;
    }

    //option for scissors vs paper
    else {

      System.out.println("I choose scissors. I win!");

      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }
	            
      systemMo.add(s);
      userMo[userMCount] = p;
      userMCount++;
      youLose++;
    }

    return userMo;
  }

  /**
   * scissorsValid: count both system moves and user moves then match
   * them to see who wins
   *
   * @rngN system move
   * @userMo the history of user moves
   * @systemMo the history of system moves
   */
  public static String[] scissorsValid (int rngN, String[] userMo,
		                 LinkedList<String> systemMo) {
    //delcaration
    int DOUBLE = 2;
    String r = "rock";
    String p = "paper";
    String s = "scissors";

    //option for rock vs scissors
    if (rngN == 0) {

      System.out.println("I choose rock. I win!");
	  
      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }

      systemMo.add("rock");
      userMo[userMCount] = "scissors";
      userMCount++;
      youWin++;
    }

    //option for paper vs scissors
    else if (rngN == 1) {

      System.out.println("I choose paper. You win.");
	  
      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }

      systemMo.add("paper");
      userMo[userMCount] = "scissors";
      userMCount++;
      youWin++;
    }

    //option for scissors vs scissors
    else {

      System.out.println("I choose scissors. It's a tie.");
	  
      if (userMCount >=  arraySize) {
        arraySize = arraySize * DOUBLE;
        userMo = checkNexpand(arraySize, userMo);
      }
	   
      systemMo.add("scissors");
      userMo[userMCount] = "scissors";
      userMCount++;
      tie++;
    }

    return userMo;
  }


  /**
   * main :starts the game
   *
   * @Param args a string of arguments that are passed in
   *
   */
  public static void main( String[] args ) {
    int initialCapacity = 5;
    // Store the user's move history
    String[] userMoves = new String[initialCapacity];  
    // Store the System's move history
    LinkedList<String> systemMoves = new LinkedList<String>();
    systemMoves.add("hello");
    systemMoves = new LinkedList<String>();
    systemMoves.add("hi");
    System.out.println(systemMoves.get(0));
      
    
    //declaration
    Random rng = new Random(System.currentTimeMillis());
    Scanner scan = new Scanner(System.in);
    String userInput = "";
    String quit = "q";
    String rock = "r";
    String paper = "p";
    String scissors = "s";
    int aiPlay = 0;
    int seed = 3;
    arraySize = initialCapacity;
    
    //the game and take user input
    while (!userInput.equals(quit)) {

      //print out interface
      System.out.println("Let's play!  What's your move? (r=rock," 
	                  + " p=paper, s=scissors or q to quit");
      
      //random generator and read user input
      userInput = scan.nextLine();
      aiPlay = rng.nextInt(seed);
      
      //option for rock
      if (userInput.equals(rock)){
        userMoves = rockValid(aiPlay, userMoves, systemMoves);
      } 

      // user paper option
      else if (userInput.equals(paper)) {
        userMoves = paperValid(aiPlay, userMoves, systemMoves);
      }

      //user scissor option
      else if (userInput.equals(scissors)) {
	userMoves = scissorsValid(aiPlay, userMoves, systemMoves);
      }

      //score and display statistics
      else if (userInput.equals(quit)) { 
	//results
	recentHistory(userMoves, systemMoves, userMCount);
	calStats(youWin, youLose, tie);
      }

      //print out for invalid inputs
      else {
        System.out.println("That is not a valid move.  Please try again.");
			
      }
    }
    
    // TODO: When the game is done, write the code to print the move history
    //  and stats
  
  }  
  
}
