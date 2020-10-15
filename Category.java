import java.util.ArrayList;
public class Category{
	
	private String colour;
	private int numberOfDisks, min, max, shortestRadius, longestRadius;
	// public Object[] reds, blues, greens;
	private ArrayList<Circle> circlesInCategory;
		
	public Category(String colour, int min, int max) {
			this.colour = colour;
			this.min = min;
			this.max = max;
	}
	
	public String getCategoryColour() {
		return colour;
	}

	public int getMin(){
		return min;
	}

	public int getMax(){
		return max;
	}

	public void addCircleToCat(Circle cir){
		circlesInCategory.add(cir);
	}

	public ArrayList<Circle> getDisksIntCategory(){
		return circlesInCategory;
	}
	
	public int getNumberOfDisksInCat(){
		return circlesInCategory.size();
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

