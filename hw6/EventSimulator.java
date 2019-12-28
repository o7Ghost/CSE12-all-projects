//NAMES: Qiao jia, Lu, Dan Mu
//IDs: A13638993, A14719967
//EMAILS: qjlj@ucsd.edu, d1mu@ucsd.edu
//Login: cs12fbo, cs12rnj
package hw6;

import java.util.*;

/** 
 * A class to implement a discrete event simulation. 
 */
public class EventSimulator
{
    private Queue<Event> eventQueue;  // The event queue
    private Queue<Customer> waitLine;  // The waiting customers
    private Random rand;  // The random number generator for the simulation
  
    /** Constructor.  Create a new event simulator with a fixed 
     * random see to start */
    public EventSimulator( int rseed )
    {
        eventQueue = new HeapPQ12<Event>();
        waitLine = new LinkedList<Customer>();

        if (rseed == -1) {
            rand = new Random();
        }
        else {
            rand = new Random(rseed);
        }
    }
    
    /* Algorithm by D. Knuth */
    private int getPoissonRandom(double mean) {
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {p = p * rand.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }   
  
    /** Get a random integer from a Poisson distribution with the specified mean */
    private int getRandomInt( int mean ) 
    {
        return getPoissonRandom((double)mean);
    }
  
    /** Create one new arrival event per customer 
     *  @param numCustomers The number of Customers in the simulation.
     *  @param arrivalMean The mean time between arrivals.
     *  @param serviceMean The mean service time per customer.
     *  @param fixed If fixed is true, there will be no randomness in times */
    public void initializeEvents( int numCustomers, int arrivalMean, 
                                  int serviceMean, boolean fixed )
    {
        // Seed the event queue with customer arrivals
        int clock = 0;
        int customerNum = 0;
        int arrivalDelay, serviceTime;
        for (int i = 0; i < numCustomers; i++) {
            if (fixed) {
                arrivalDelay = arrivalMean;
                serviceTime = serviceMean;
            }
            else {
                arrivalDelay = getRandomInt(arrivalMean);
                serviceTime = getRandomInt(serviceMean);
            }
            clock += arrivalDelay;
            Customer c = new Customer("Customer"+customerNum++, 
                                       clock, serviceTime);
            eventQueue.add(new Event(Event.ARRIVAL, clock, c));
        }
    }
  
  
    /** Print an event. Useful for seeing the events during simulation */
    public void printEvent(Event e)
    {
        System.out.print( e.getName() );
        if (e.getType() == Event.ARRIVAL) {
            System.out.print( " arrives at time " );
        }
        else if (e.getType() == Event.DEPARTURE) {
            System.out.print( " departs at time " ); 
        }
        System.out.println( e.getTime() );
    }
  
    /** Runs a simulation and print the statistics 
     *  TODO: Fill in code to run the simulation and collect statistics. */
    public void runSimulation() 
    {
	//local variables
        int totalCustomers = 0;
        int totalWaitTime = 0;
        int maxWaitTime = 0;
        int maxWaitQueueSize = 0;
        boolean busy = false;
        int clock = 0;
        Event next = null;
	int departureTime = 0;
        Event departure = null;
	Customer cust = null;
	int waitTime = 0;

        HeapPQ12<Integer> findMax = new HeapPQ12<Integer>(true);

	//check if eventQueue is not empty
        while (!eventQueue.isEmpty()) {

  
	//get next event and time
        next = eventQueue.poll();
	clock = next.getTime();

	//check if event is arrival or departure
        if (next.getType() ==  0) {
          totalCustomers++;
	  //help the customer if there is time
	  //System.out.println(busy);
          if (!busy) {

            busy = true;
	    cust = next.getCustomer();
	    departureTime = cust.getServiceTime() + cust.getArrivalTime();
	    departure = new Event(1, departureTime, cust);
	    eventQueue.offer(departure);	      
	  }

	  else {

	    //add to wait line
	    waitLine.add(next.getCustomer());
	  }
        }

	//it is the departure event
	else {

	  //check if wait queue is empty
	  if (!waitLine.isEmpty()) {
	    //help the people in wait queue

	    maxWaitQueueSize = Math.max(maxWaitQueueSize, waitLine.size());    
            Customer nextCust = waitLine.poll();
	    waitTime = departureTime - nextCust.getArrivalTime();
	    findMax.offer(waitTime);
            totalWaitTime = totalWaitTime + waitTime;     
	    departureTime = nextCust.getServiceTime() + nextCust.getArrivalTime() + waitTime;
	    departure = new Event(1, departureTime, nextCust);
	    eventQueue.offer(departure);
	    }

	    else {

	      //set server to not busy
              busy = false;
	    }
	  }
	}	  
       
	if (findMax.peek() != null) {
          maxWaitTime = findMax.peek();
	}

        //TODO: Code to run the simulation and collect statistics goes here.
       	//System.out.println(totalWaitTime);

        System.out.println("\tAverage wait time: " + 
                            ((float)totalWaitTime)/totalCustomers);
        System.out.println("\tMaximum wait time: " + maxWaitTime);
        System.out.println("\tMaximum queue length: " + maxWaitQueueSize);
    }
  
    /** Main method to set up and run the simulation */ 
    public static void main( String[] args )
    {
        if (args.length < 4) {
            System.out.print( "Usage: java EventSimulator numCustomers ");
            System.out.print( "meanTimeBetweenArrivals meanServiceTime ");
            System.out.println( "variableTimes? [randSeed]");
            return;
        }
        
        int numCustomers = Integer.parseInt(args[0]);
        int arrivalMean = Integer.parseInt(args[1]);
        int serviceMean = Integer.parseInt(args[2]);
        int variableInt = Integer.parseInt(args[3]);
        boolean variable = false;
        if (variableInt == 1) {
            variable = true;
        }
        int rseed = -1;
        if ( args.length >= 5 ) {
            rseed = Integer.parseInt(args[4]);
        }
        EventSimulator sim = new EventSimulator( rseed );
        sim.initializeEvents( numCustomers, arrivalMean, serviceMean, !variable );
        System.out.println("Running simulation with " + numCustomers + " customers");
        sim.runSimulation();
    }
}
