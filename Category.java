public class Category{
	
	public String colour;
	public int min, max;
	public Object[] reds, blues, greens;
		
	public Category(String colour, int min, int max, Object[] reds, Object[] blues, Object[] greens) {
			this.colour = colour;
			this.min = min;
			this.max = max;
			this.reds = reds;
			this.blues = blues;
			this.greens = greens;
	}
	
	public String getColour() {
		return colour;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax(){
		return max;
	}
	
	public Object[] getReds(){
		return reds;
	}
	
	public Object[] getBlues(){
		return blues;
	}
	
	public Object[] getGreens(){
		return greens;
	}	
		
	/*
	 * public void changeColour(int Category){ for(int i=0;i<pixels.size();i++){
	 * if(Category==0){ pixels.get(i).changeColorRed(); } else if(Category==1){
	 * pixels.get(i).changeColorBlue(); } else if(Category==2){
	 * pixels.get(i).changeColorGreen(); } } }
	 */
}

