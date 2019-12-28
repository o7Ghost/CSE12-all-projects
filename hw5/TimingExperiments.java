import java.util.ArrayList;
import java.util.Random;

public class TimingExperiments
{
    public static void main(String[] args) 
    {
        // Test how long it takes to find elements 
        // in a List
        
        int increment = 2000;
        int numSteps = 40;
        int size = 5000;
        int numToFind = 100;
        
        long totalTime;
        long startTime;
        
        Random rand = new Random();
        
        System.out.println("STARTING BENCHMARKING");
        System.out.println("N,Time");
        for (int step = 0; step < numSteps; step++) {
            ArrayList<Integer> list1 = new ArrayList<Integer>();
            // Put a bunch of elements, sorted, into an
            // ArrayList
            for (int i = 0; i < size; i++) {
                list1.add(i);
            }
            startTime = System.nanoTime();
            // Now find the first element numToFind times
            for (int i = 0; i < numToFind; i++) {
                list1.contains(0);
                //list1.contains(size-1);
                //list1.contains(rand.nextInt(size));
            }
            totalTime = System.nanoTime() - startTime;
            System.out.println(size + ", " + totalTime);
            size += increment;
        }
        

        
    }
}
