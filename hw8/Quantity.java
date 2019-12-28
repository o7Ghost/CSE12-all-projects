//NAMES: Qiao jia, Lu
//IDs: A13638993
//EMAILS: qjlu@ucsd.edu
//Login: cs12fbo

package hw8;
import java.lang.Math;
import java.util.*;
import java.text.DecimalFormat;

/**
 * a class that represents quantities with
 * their associated units and also does arthmetric
 */
public class Quantity {
  
  //fields
  private double value;
  private HashMap<String,Integer> units;

  /**
   * a constructor that contains
   * the value one and empty map
   */
  public Quantity() {
    value = 1.0;
    units = new HashMap<String,Integer>();
  }

  /**
   * a constrcutor that makes a deep copy of the quantity
   * that the user has put it
   *
   * @param original
   * the quantity that is gonna be deep copied
   */
  public Quantity(Quantity original) {

    HashMap<String,Integer> copy = new HashMap<String,Integer>();
    this.value = original.value;

    for (Map.Entry<String, Integer> i : original.units.entrySet()) {
 
      copy.put(i.getKey(),i.getValue());
    }

    this.units = copy;
  }
  
  /**
   * a constructor that takes in 3 parameters and make up a quantity
   *
   * @param numeric the value
   * @param numerator the units on top
   * @param denominator the units on the bottom
   */
  public Quantity(double numeric, List<String> numerator, 
		  List<String> denominator) {

    //instance variable initialization
    value = numeric;
    units = new HashMap<String, Integer>();

    //check Illegal argument
    if (numerator == null || denominator == null) {
       throw new IllegalArgumentException("value is null");
    }

    //check numerator
    for (String i : numerator) {
      if (!units.containsKey(i)) {
        units.put(i,1);
      }

      else {

	Integer nPower = units.get(i);
        int n = nPower + 1;
        units.put(i, new Integer(n));     
      }
    }

    //check denominator
    for (String j : denominator) {
      if (!units.containsKey(j)) {
        units.put(j,-1);
      }

      else {

        Integer dPower = units.get(j);

        int d = dPower - 1;
	dPower = new Integer(d);   
	units.put(j, dPower);

        if (dPower.equals(new Integer(0))) {
           units.remove(j, dPower);
        }
      }
    }
  }

  /**
   * multiple two quantity together
   *
   * @param multi the quantity that will be multiplied
   * @throws IllegalArgumentException
   * @return an new Quantity
   */
  public Quantity mul(Quantity multi){

    if (multi == null) {
      
      throw new IllegalArgumentException("value is null");
    }
    
    //product that is returned
    Quantity product = new Quantity();

    //the value that is multipled
    product.value = this.value * multi.value;

     for (Map.Entry<String, Integer> i : this.units.entrySet()) {
 
      if (multi.units.containsKey(i.getKey())) {
        Integer rpower = multi.units.get(i.getKey());
	Integer lpower = i.getValue();
	int p = rpower + lpower;
	if ( p != 0 ) {
          product.units.put(i.getKey(), new Integer(p));
        }

	if ( p == 0 ) {
          product.units.remove(i.getKey(), new Integer(p));

        }
      }

      else {
	
	product.units.put(i.getKey(), i.getValue());
      }

    }

    
    for (Map.Entry<String, Integer> j : multi.units.entrySet()) {

      if (!this.units.containsKey(j.getKey())) {
        
        product.units.put(j.getKey(), j.getValue());
      }
      
    }
    return product;
  }

  public Quantity div(Quantity division) throws IllegalArgumentException {

     if (division == null || division.value == 0) {
      
      throw new IllegalArgumentException("value is null");
    }

    //product that is returned
    Quantity product = new Quantity();

    //the value that is multipled
    product.value = this.value / division.value;

     for (Map.Entry<String, Integer> i : this.units.entrySet()) {
 
      if (division.units.containsKey(i.getKey())) {
        Integer rpower = division.units.get(i.getKey());
	Integer lpower = i.getValue();
	int p = lpower - rpower;
	if ( p != 0 ) {
          product.units.put(i.getKey(), new Integer(p));
        }

	if ( p == 0 ) {
          product.units.remove(i.getKey(), new Integer(p));

        }
      }

      else {
	
	product.units.put(i.getKey(), i.getValue());
      }

    }

    
    for (Map.Entry<String, Integer> j : division.units.entrySet()) {

      if (!this.units.containsKey(j.getKey())) {
        
	int k2 = -(j.getValue());
        product.units.put(j.getKey(), new Integer(k2));
      }
      
    }

    return product; 
  }

  public Quantity pow(int p) {

    //product that is returned
    Quantity product = new Quantity(this);

    //the value that is multipled
    product.value = Math.pow(this.value, p);

    
    for (Map.Entry<String, Integer> i : this.units.entrySet()) {
 
        Integer rpower = this.units.get(i.getKey());
	int d = rpower * p;

	product.units.put(i.getKey(), new Integer(d));
  	if ( d == 0 ) {
		
	  product.units.remove(i.getKey(), new Integer(d));
        }   
    }
  
    //System.out.println(product.toString());
    return product;
  }

  public Quantity add(Quantity addition) throws IllegalArgumentException {

    
    if (addition == null) {
      
      throw new IllegalArgumentException("value is null");
    }

    //product that is returned
    //the value that is multipled


    if (!this.units.equals(addition.units)) {
       throw new IllegalArgumentException("unit doesn't match");
    }
    Quantity product = new Quantity(this);

    product.value = this.value + addition.value;


    /*
    for (Map.Entry<String, Integer> i : this.units.entrySet()) {

      if (addition.units.containsKey(i.getKey())) {
        Integer rpower = addition.units.get(i.getKey());
	product.units.put(i.getKey(), rpower);
      }

      else {

         throw new IllegalArgumentException("unit doesn't match");
      }
    }
    */
    return product;
  }

  public Quantity sub(Quantity subtract) {

    if (subtract == null) {
      
      throw new IllegalArgumentException("value is null");
    }

    //product that is returned
    Quantity product = new Quantity();

    //the value that is multipled
    product.value = this.value - subtract.value;

    for (Map.Entry<String, Integer> i : this.units.entrySet()) {

      if (subtract.units.containsKey(i.getKey())) {
        Integer rpower = subtract.units.get(i.getKey());
	product.units.put(i.getKey(), rpower);
      }

      else {

         throw new IllegalArgumentException("unit doesn't match");
      }

    }

    return product;
  }

  public Quantity negate() {

    //product that is returned
    Quantity product = new Quantity();

    //the value that is multipled
    product.value = -(this.value);

    for (Map.Entry<String, Integer> i : this.units.entrySet()) {

      if (this.units.containsKey(i.getKey())) {
        Integer rpower = this.units.get(i.getKey());
	product.units.put(i.getKey(), rpower);
      }
    }

    return product;
  }

  public static Quantity normalizedUnit(String unit, Map<String,Quantity> env) {

    if (!env.containsKey(unit)) {
      return new Quantity(1.0, Arrays.asList(unit), Collections.<String>emptyList());
    }

    else {

     return env.get(unit).normalize(env);

    }

  }

  public Quantity normalize(Map<String,Quantity> env) {

      double tempValue = this.value;
      Quantity result = new Quantity(tempValue, Collections.<String>emptyList(),Collections.<String>emptyList());

      for (Map.Entry<String, Integer> i : this.units.entrySet()) {
        int power = this.units.get(i.getKey());
	
	result = result.mul(normalizedUnit(i.getKey(), env).pow(power));
      }

      return result;
    }
  
  public int hashCode() {
    return this.toString().hashCode();
  }

  public String toString() {
  // XXX You will need to fix these lines to match your fields!
    double valueOfTheQuantity = this.value;
    Map<String,Integer> mapOfTheQuantity = this.units;
 
    // Ensure we get the units in order
    TreeSet<String> orderedUnits = 
	            new TreeSet<String>(mapOfTheQuantity.keySet());
    
    StringBuffer unitsString = new StringBuffer();
    
    for (String key : orderedUnits) {
      int expt = mapOfTheQuantity.get(key);
      unitsString.append(" " + key);
      if (expt != 1)
         unitsString.append("^" + expt);
    }
    
    // Used to convert doubles to a string with a 
    //   fixed maximum number of decimal places.
    DecimalFormat df = new DecimalFormat("0.0####");
    // Put it all together and return.
    return df.format(valueOfTheQuantity) + unitsString.toString();
  }

  public boolean equals(Object q) {
    if(q == null){
      throw new IllegalArgumentException("quantity parameter is null");
    }

    boolean determine = false;

    if (q instanceof Quantity) {
      return this.toString().equals(q.toString());
    }

    return determine;
  }
}
