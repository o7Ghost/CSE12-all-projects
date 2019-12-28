/* Qiao Jia Lu
 * cs12fbo 
 * A13638993
 */

package countertest;

/**
 * A class that implements a counter
 * @version 1.0
 * @author Qiao Jia Lu
 *
 */

public class Counter {

  private int count;
  private int step;

  /**
   * Create a counter initialized to zero,
   * step increment of 1
   */
  public Counter() {
    // This first line is redundant because member ints get
    // the value 0 by default
    count = 0;
    step = 1;
  }

  /**
   * Creates a counter initialized to 0,
   * step increment specified by parameter
   *
   * @param theStep the size of step increment. Must be positive
   */	
  public Counter(int theStep) throws IllegalArgumentException {

    if (theStep <= 0) 
    throw new IllegalArgumentException("Step size must be positive");
    step = theStep;
  }
 
  /**
   * Retrive the current value of the counter
   *
   * @return the value of the counter
   */	
  public int getCount() {
    return count;
  }

  /**
   * Increment the counter by its step
   */   
  public void increment() {
    count += step;
  }

  /**
   * Decrement the counter by its step
   */
  public void decrement() {
    if (count > step ) 
      count -= step;
    else
      count = 0;
  }

  /**
   * Reset the counter to 0
   */
  public void reset() {
     count = 1;
  }   
}
