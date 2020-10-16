/**
* This class is used to construct a mass of concurrent pixels, all above the threshold value. 
* This class also store the value for the longest pixel chain in an object, 
* and uses that to determine if an object is a circle or not
* @author Rifqah Francis
* @author Jack de Kock
* @version 1.0
* @since 20/08/2020
*/
import java.util.ArrayList;

public class Object {
    public ArrayList<Pixel> pixels;
    int longestPixelChain;
    boolean isCircle,isbuilding;
    double CircleAreaWithGivenRadius;
    double CirclePerimeterWithGivenRadius;

    /**
    * This is the constructor for the object class
    * Sets isCircle to false
    * Sets isBuilding to false
    * Sets the radius of a circle to 0.0
    * Sets the longest pixel chain to 0
    * Creates an instance of an arraylist of pixels
    */
    public Object(){
        isCircle=false;
        isbuilding=false;
        CircleAreaWithGivenRadius=0.0;
        longestPixelChain=0;
        pixels = new ArrayList<Pixel>();
        CirclePerimeterWithGivenRadius=0.0;
    }

    /**
    * This method adds a pixel to the arraylist of pixels and sets the pixel's owned to true
    * @param toBeAdded This is the pixel to be added to pixels
    */
    public void addPixel(Pixel toBeAdded){
        toBeAdded.setOwned();
        pixels.add(toBeAdded);
    }

    /**
    * This method adds a pixel line as an arraylist to the arraylist of pixels
    * @param pixelLine This is the pixel line to be added to pixels
    */
    public void addPixelLine(ArrayList<Pixel> pixelLine){
        for(int i=0; i<pixelLine.size();i++){
            pixelLine.get(i).setOwned();
            pixels.add(pixelLine.get(i));
        }
    }

    /**
    * This method checks if a pixel is in the arraylist of pixels
    * @param checkable This is the pixel to be checked
    * @return boolean Returns the answer as a boolean
    */
    public boolean isIn(Pixel checkable){
        for (int i =0;i<pixels.size();i++){
            if(pixels.get(i)==checkable)
                return true;
        }
        return false;
    }

    /**
    * This method checks if a pixel is close to the arraylist of pixels
    * @param p This is pixel to be checked
    * @return int Returns the answer as a boolean
    */
    public boolean isPartOf(Pixel p){
        for (int i=0;i<pixels.size();i++){
            if (pixels.get(i).closeTo(p)){
                return true;
            }
        }
        return false;
    }

    /**
    * This method returns the radius of a circle by dividing the longest pixel chain by 2
    * @return double Returns the radius as a double
    */
    public double isCircle(){ //change to getRadius
        double radius = getLongestPixelChain()/2;
        return radius;
    }

    /**
    * This method calculates and returns the surface area of a circle
    * @return double Returns the answer as a double
    */
    public double getSurfaceArea(){
        double radius = isCircle();
        CircleAreaWithGivenRadius = (Math.PI*(radius*radius));
        return CircleAreaWithGivenRadius;
    }

    public double getPerimeter() {
        double radius = isCircle();
        CirclePerimeterWithGivenRadius = (2*Math.PI*radius);
        return CirclePerimeterWithGivenRadius;
    }

    /**
    * This method sets a circle's longest pixel chain after it has been calculated and returns the circle
    * @return Circle Returns the circle as type Circle
    */
    public Circle circlefy(){
        Circle toCircle = new Circle(pixels);
        toCircle.setLongestPixelChain(longestPixelChain);
        return toCircle;
    }

    /**
    * This method checks if an object is a circle by comparing its surface area and number of pixels
    * @return boolean Returns the answer as a boolean value
    */
    public boolean checkCircle(){
        //System.out.println("Meant to be: "+ getSurfaceArea()+"     | is: "+pixels.size());
        if(getSurfaceArea()!=0&&(pixels.size()/getSurfaceArea())>=1) {
            isCircle = true;
            return true;
        }
        return false;
    }

    /**
    * This method calculates and returns the longest pixel chain
    * @return int Returns the answer as an integer
    */
    public int getLongestPixelChain() {
        int min=200;
        int max=0;
        int num=0;
        while(pixels.size()>num){
            Pixel current = pixels.get(num);
            int xVal = current.getX();
            min=Math.min(min,xVal);
            max=Math.max(max,xVal);
            num++;
        }
        longestPixelChain=max-min;
        return longestPixelChain;
    }
}
