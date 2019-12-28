/**
 * TQTree.java
 * A Java class that supports a Binary Tree that plays the game of twenty questions
 * 
 * @author Christine Alvarado
 * @version 1.0
 * @date May 11, 2014
 */
package hw7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Scanner;
import java.util.LinkedList;

public class TQTree {
    private TQNode root;

    /**
     * Inner class that supports a node for a twenty questions tree. You should not
     * need to change this class.
     */
    class TQNode {
        /* You SHOULD NOT add any instance variables to this class */
        TQNode yesChild; // The node's right child
        TQNode noChild; // The node's left child
        String data; // A question (for non-leaf nodes)
                     // or an item (for leaf nodes)

        int idx; // index used for printing

        /**
         * Make a new TWNode
         * 
         * @param data
         *            The question or answer to store in the node.
         */
        public TQNode(String data) {
            this.data = data;
        }

        /**
         * Setter for the noChild
         * 
         * @param noChild
         *            The new left (no) child
         */
        public void setNoChild(TQNode noChild) {
            this.noChild = noChild;
        }

        /**
         * Setter for the yesChild
         * 
         * @param yesChild
         *            The new right (yes) child
         */
        public void setYesChild(TQNode yesChild) {
            this.yesChild = yesChild;
        }

        /**
         * Getter for the yesChild
         * 
         * @return The node's yes (right) child
         */
        public TQNode getYesChild() {
            return this.yesChild;
        }

        /**
         * Getter for the noChild
         * 
         * @return The node's no (left) child
         */
        public TQNode getNoChild() {
            return this.noChild;
        }

        /**
         * Getter for the data
         * 
         * @return The data stored in this node
         */
        public String getData() {
            return this.data;
        }

        /**
         * Setter for the index
         * 
         * @param idx
         *            index of this for printing
         */
        public void setIndex(int idx) {
            this.idx = idx;
        }

        /**
         * get the index
         * 
         * @return idx index of this for printing
         */
        public int getIndex() {
            return this.idx;
        }
    } // End TQNode

    /**
     * Builds a new TQTree by reading from a file
     * 
     * @param filename
     *            The file containing the tree
     * @throws FileNotFoundException
     *             if the file cannot be found or read.
     */
    public TQTree(String filename) {
        File f = new File(filename);
        LineNumberReader reader;
        try {
            reader = new LineNumberReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file " + filename);
            System.err.println("Building default Question Tree.");
            buildDefaultTree();
            return;
        }

        buildTreeFromFile(reader);
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("An error occured while closing file " + filename);
        }

    }

    // Build the tree from the file that reader is reading from.
    private void buildTreeFromFile(LineNumberReader reader) {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            errorBuildTree("File contains no tree.");
            return;
        }

        if (line == null) {
            errorBuildTree("File contains no tree.");
            return;
        }
        String[] lineSplit = line.split(":", 2);
        if (lineSplit.length < 2) {
            errorBuildTree("Incorrect file format: line 1.");
            return;
        }
        String qOrA = lineSplit[0];
        String data = lineSplit[1];

        if (!qOrA.equals("Q")) {
            errorBuildTree("Incorrect file format: line 1.");
            return;
        }
        root = new TQNode(data);
        try {
            root.setNoChild(buildSubtree(reader));
            root.setYesChild(buildSubtree(reader));
        } catch (ParseException e) {
            errorBuildTree(e.getMessage() + ": line " + +e.getErrorOffset());
        }
    }

    // Print an error message and then build the default tree
    private void errorBuildTree(String message) {
        System.err.println(message);
        System.err.println("Buidling default Question Tree");
        buildDefaultTree();
    }

    /**
     * Recursive method to build a TQTree by reading from a file.
     * 
     * @param reader
     *            A LineNumberReader that reads from the file
     * @return The TQNode at the root of the created tree
     * @throws ParseException
     *             If the file format is incorrect
     */
    private TQNode buildSubtree(LineNumberReader reader) throws ParseException {

        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException("Error reading tree from file: " + e.getMessage(), reader.getLineNumber());
        }

        if (line == null) {
            // We should never be calling this method if we don't have any more input
            throw new ParseException("End of file reached unexpectedly", reader.getLineNumber());
        }

        String[] lineSplit = line.split(":", 2);
        String qOrA = lineSplit[0];
        String data = lineSplit[1];

        TQNode subRoot = new TQNode(data);
        if (qOrA.equals("Q")) {
            subRoot.setNoChild(buildSubtree(reader));
            subRoot.setYesChild(buildSubtree(reader));
        }
        return subRoot;
    }

    /** Builds a starter TQ tree with 1 question and 2 answers */
    public TQTree() {
        buildDefaultTree();
    }

    private void buildDefaultTree() {
        root = new TQNode("Is it bigger than a breadbox?");
        root.setNoChild(new TQNode("spam"));
        root.setYesChild(new TQNode("a computer scientist"));

    }

    /**
     * Play one round of the game of Twenty Questions using this game tree Augments
     * the tree if the computer does not guess the right answer
     *
     * @param input the scanner where it is going to take
     * user inputs
     */
    public void play(Scanner input) {

      //local variables
      String response = "";
      TQNode current = root;
      TQNode previous = null;

      while (current.getNoChild() != null) {
          
        //get previous node
	previous = current;

	//print questions
        System.out.println(current.getData());

	//take a responds from user
	response = input.nextLine();

	//determine which question to ask next
	if (response.equalsIgnoreCase("no")) {

	  current = current.getNoChild();
	}

	else {

          current = current.getYesChild();
	}
      }

      //guess the answer
      System.out.println("Is it " + current.getData());
      String guess = input.nextLine();
	
      //make new questions and answers
      if (guess.trim().equalsIgnoreCase("no") || 
          guess.trim().equalsIgnoreCase("n")) {

        guess(previous, input, response);
      }

      else if (guess.trim().equalsIgnoreCase("yes") || 
	       guess.trim().equalsIgnoreCase("y")) {

        System.out.println("Got It!");
      }
    }

    /**
     * guess user answer and add questions
     * @param previous previous
     * @param input scanner for the userinput
     * @param response user respond
     */
    public void guess(TQNode previous, Scanner input, String response) {

      //save previous answer
      TQNode answer = null;
      
      if (response.trim().equalsIgnoreCase("no") || 
	  response.trim().equalsIgnoreCase("n")) {

        answer = previous.getNoChild();
      }

      else if (response.trim().equalsIgnoreCase("yes") || 
	       response.trim().equalsIgnoreCase("y")) {

        answer = previous.getYesChild();
      }
      
      //ask user what the answer is
      System.out.println("Ok, what is it?");

      String respond = input.nextLine();

      TQNode newAnswer = new TQNode(respond);

      System.out.println("Give me a question that would distinguish " +
		         newAnswer.getData() + " from " + answer.getData());

      respond = input.nextLine();

      TQNode newQuestion = new TQNode(respond);

      System.out.println("And would the answer to the question " + 
		          "for a " + newAnswer.getData() +  " be yes or no?");

      respond = input.nextLine();

      //set pointers for the new question
      if (response.trim().equalsIgnoreCase("no") || 
	  response.trim().equalsIgnoreCase("n")) {

        previous.setNoChild(newQuestion);
	setPointers(respond, answer, newQuestion, newAnswer); 
	        	
      }

      else if (response.trim().equalsIgnoreCase("yes") || 
	       response.trim().equalsIgnoreCase("y")) {

        previous.setYesChild(newQuestion);
	setPointers(respond, answer, newQuestion, newAnswer); 
      }
    }

    /**
     * setpointers for the answers
     * @param respond user response
     * @param answer the old answer
     * @param newAnswer new answer from the user
     * @param newQuestion new question from the user
     */
    public void setPointers(String respond, TQNode answer, 
		            TQNode newQuestion, TQNode newAnswer) {

      //set pointer for the answer
      if (respond.trim().equalsIgnoreCase("no") || 
          respond.trim().equalsIgnoreCase("n")) {

        newQuestion.setYesChild(answer);
        newQuestion.setNoChild(newAnswer);
        	
      }

      else if (respond.trim().equalsIgnoreCase("yes") || 
	       respond.trim().equalsIgnoreCase("y")) {

	newQuestion.setNoChild(answer);
        newQuestion.setYesChild(newAnswer); 
      }
    }
    // PRIVATE HELPER METHODS
    // You will likely want to add a few more private helper methods here.
    // IMPORTANT NOTE: You will want to pass Scanner object to your helper methods
    // if you wish to get input in those methods
    // Make sure you only have calls to 'nextLine()' in your helper methods. Not
    // 'next()'

    public void print() {
        PrintWriter writer = new PrintWriter(System.out);
        printTree(writer, root);
        writer.flush();
    }

    /**
     * method for breadth-first traversal of the tree.
     * 
     * @param The
     *            print writer to write to (usually stdout)
     * @param The
     *            current root from which to write
     */
    private void printTree(PrintWriter writer, TQNode root) {

      //local variable
      LinkedList<TQNode> queue = new LinkedList<TQNode>();
      TQNode parent = null;
      int counter = 0;
	
      //add root to queue
      queue.add(root);

      //search through the tree and add index
      while (!queue.isEmpty()) {

        parent = queue.poll();
	parent.setIndex(counter);

	if (parent.getNoChild() != null) {

	  queue.add(parent.getNoChild());
	  queue.add(parent.getYesChild());
	}

	//increase index
	counter++;
      }

      queue.add(root);

      //go through the tree again and print everything
      while (!queue.isEmpty()) {

        parent = queue.poll();

	if (parent.getNoChild() != null) {

	  System.out.println(parent.getIndex() + ": " + "'" + parent.getData()
	                     + "' " + "no:" + "(" + 
			     parent.getNoChild().getIndex() + ") " 
			     + "yes:" + "(" + 
			     parent.getYesChild().getIndex() + ")");
	}

	//print stats if it is leaf
	else {

          System.out.println(parent.getIndex() +": " + "'" 
			     + parent.getData() + "' " + 
			     "no:" + "(null) " + "yes:"
	                     + "(null)");
	  }

	  if (parent.getNoChild() != null) {
		  
	    queue.add(parent.getNoChild());
	    queue.add(parent.getYesChild());
	  }
        }
      }

    /**
     * Save this Twenty Questions tree to the file with the given name
     * 
     * @param filename
     *            The name of the file to save to
     * @throws FileNotFoundException
     *             If the file cannot be used to save the tree
     */
    public void save(String filename) throws FileNotFoundException {
        File f = new File(filename);
        PrintWriter writer = new PrintWriter(f);
        saveTree(writer, root);
        writer.close();
    }

    /**
     * Recursive helper method to do the preorder traversal of the tree.
     * 
     * @param The
     *            print writer to write to
     * @param The
     *            current root from which to write
     */
    private void saveTree(PrintWriter writer, TQNode currentRoot) {
        if (currentRoot == null) {
            return;
        }
        String toWrite = "";
        if (currentRoot.getNoChild() == null && currentRoot.getYesChild() == null) {
            toWrite = "A:" + currentRoot.getData();
        } else {
            toWrite = "Q:" + currentRoot.getData();
        }
        writer.println(toWrite);
        saveTree(writer, currentRoot.getNoChild());
        saveTree(writer, currentRoot.getYesChild());
    }

}
