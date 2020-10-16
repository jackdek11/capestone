/** 
* This class sorts the identified discs according to which category of colour they belong to
* @author Rifqah Francis
* @author Amy Solomons
* @version 1.0
* @since 09/10/2020
*/

import java.util.ArrayList;
public class Category{
	
	private String colour;
	private int numberOfDisks, min, max, shortestRadius, longestRadius;
	// public Object[] reds, blues, greens;
	private ArrayList<Circle> circlesInCategory;
	
	/**
	* The constructor for the Category class
	* @param colour This initializes the colour variable
	* @param min This initializes the min variable
	* @param max This initializes the max variable
	*/
	public Category(String colour, int min, int max) {
			this.colour = colour;
			this.min = min;
			this.max = max;
			circlesInCategory = new ArrayList<Circle>();
	}
	
	/** 
	* This returns the category colour
	* @return String Returns the category colour in the form of a string
	*/
	public String getCategoryColour() {
		return colour;
	}

	/**
	* This returns the minimum value for the category
	* @return int Returns the minimum in the form of an integer
	*/	
	public int getMin(){
		return min;
	}

	/**
	* This returns the maximum value for the category
	* @return int Returns the maximum in the form of an integer
	*/		
	public int getMax(){
		return max;
	}

	/** 
	* This method adds a circle to an arraylist according to the category
	* @param cir The circle of type Circle to be added
	*/
	public void addCircleToCat(Circle cir){
		circlesInCategory.add(cir);
	}
	
	/**
	* This method returns all the discs/circles in a particular category
	* @return ArrayList<Circle> Returns all the discs in the category in the form of an arraylist of type Circle
	*/
	public ArrayList<Circle> getDisksIntCategory(){
		return circlesInCategory;
	}
	
	/**
	/* This method returns the number of discs/circles in a particular category by calling the size() method on the circlesInCategory arrayliste
	* @return int Returns the number of discs as an int
	*/
	public int getNumberOfDisksInCat(){
		return circlesInCategory.size();
		// return 0;
	}
	
	// public int getShortest() {
	// 	return shortestRadius
	// }
	
	// public int getLongest(){
	// 	return longestRadius;
	// }
	
	// public Object[] getReds(){
	// 	return reds;
	// }
	
	// public Object[] getBlues(){
	// 	return blues;
	// }
	
	// public Object[] getGreens(){
	// 	return greens;
	// }	
		
	/*
	 * public void changeColour(int Category){ for(int i=0;i<pixels.size();i++){
	 * if(Category==0){ pixels.get(i).changeColorRed(); } else if(Category==1){
	 * pixels.get(i).changeColorBlue(); } else if(Category==2){
	 * pixels.get(i).changeColorGreen(); } } }
	 */
}

