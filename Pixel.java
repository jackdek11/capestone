/**
* This class object is used to store all the pixel data information into individual pixel objects. 
* This class can also change the pixel values of the image when identified to be in an object, 
* to be a different colour
* @author Jack de Kock
* @version 1.0
* @since 15/08/202
*/
public class Pixel {

    private int R,G,B,x,y;
    public boolean owned;
    public int index;

    /**
    * The constructor for the Pixel class. Sets owned to false and initialises the index to -1
    * @param R This initialises the R variable
    * @param G This initialises the g variable
    * @param B This initialises the B variable
    * @param x This initialises the x variable
    * @param y This initialises the y variable
    */
    public Pixel(int R, int G, int B,int x,int y ){
        owned = false;
        index=-1;
        this.R=R;
        this.G=G;
        this.B=B;
        this.x=x;
        this.y=y;
    }
    
    /**
    * This method returns the x coordinate of a pixel
    * @return int Returns the x coordinate as an integer
    */
    public int getX(){
        return x;
    }

    /**
    * This method returns the y coordinate of a pixel
    * @return int Returns the y coordinate as an integer
    */    
    public int getY() {return y;}

    /**
    * This method sets owned to true
    */
    public void setOwned(){
        owned=true;
    }

    /**
    * This method changes a pixel's colour red by changing the G and B values to 0 and R to 180
    */
    public void changeColorRed(){
        G=0;
        R=180;
        B=0;
    }
    
    /**
    * This method changes a pixel's colour green by changing the R and B values to 0 and G to 180
    */    
    public void changeColorGreen(){
        R=0;
        B=0;
        G=180;
    }
    
    /**
    * This method changes a pixel's colour blue by changing the G and R values to 0 and B to 180
    */    
    public void changeColorBlue(){
        R=0;
        B=180;
        G=0;
    }

    /** 
    * This method measures the distance from one pixel to another and checks if its close
    * @param p This is the pixel that is used to measure the distance
    * @return boolean Returns the answer as a boolean value
    */
    public boolean closeTo(Pixel p){
        if (Math.abs(p.getX()-x)<=2&&Math.abs(p.getY()-y)<=2)
            return true;
        return false;
    }

    /**
    * This method checks if R, G, B are above a certain threshold 
    * @param x This is the theshold
    * @return boolean Returns the answer as a boolean value
    */
    public boolean aboveThreshold(int x){
        if (R>x||G>x||B>x){
            return true;
        }
        return false;
    }
    
    /** 
    * This method prints the RGB values of a pixel
    * @return String Prints them as a string
    */
    public String printPixel(){
        return (""+R%9);
    }
}
