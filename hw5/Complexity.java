//NAMES: Qiao jia, Lu, Dan Mu
//IDs: A13638993, A14719967
//EMAILS: qjlj@ucsd.edu, d1mu@ucsd.edu
//Login: cs12fbo, cs12rnj

/**
 * This is a class to check the running time
 * @author Qiao jia, Lu, Dan Mu
 * @version 1
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ListIterator;

/**
 * a class that tests time
 */
public class Complexity
{
	//a method to remove element in order
	public static <T> void removeAll( Collection <T> list1, ListIterator list2 ) {
		while (list2.hasNext()) {
		  list1.remove(list2.next());
		}
	  
	}
	
	/**
	 * A method that excutes the code
	 * @param args a list of argument that 
	 * are passed in
	 */
    public static void main(String[] args) 
    {
        //local variable
        int increment = 2000;
        int numSteps = 40;
        int size = 5000;
        int numToFind = 100;
        
        long totalTime;
        long startTime;
         
        System.out.println("STARTING BENCHMARKING");
        System.out.println("N,Time");
        
        //increase increment for 40 times
        for (int step = 0; step < numSteps; step++) {
        	
        	//create lists
            LinkedList<Integer> list1 = new LinkedList<Integer>();
            ArrayList<Integer> list2 = new ArrayList<Integer>();
            
            // Put a bunch of elements, sorted, into an
            // List
            for (int i = 0; i < size; i++) {
                list1.add(i);
                list2.add(i);
            }
            
            //shuffle the list
            //Collections.shuffle(list2);
	    ListIterator<Integer> iter = list2.listIterator();

            startTime = System.nanoTime();
            
            //remove element
            Complexity.removeAll(list1,iter);
            
            //calculate average time
            totalTime = (System.nanoTime() - startTime)/size;
            System.out.println(size + ", " + totalTime);
            size += increment;
        }
    }
}
