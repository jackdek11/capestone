/** 
* This class changes the colours of various circles according to which category they belong to
* @author Rifqah Francis
* @version 1.0
* @since 28/08/2020
*/
import java.util.ArrayList;
import java.awt.*;

public class Circle{
    public int radius;
    public ArrayList<Pixel> pixels;
    private Color color;
    
    /**
    * The constructor of the Circle class
    * @param pixels Initializes the pixels variable
    */
    public Circle(ArrayList<Pixel> pixels) {
		 this.pixels = pixels;
	}

    /**
    * This method changes the colour of each pixel of a circle according to which category they belong to
    * @param Category This is the category number
    */
    public void changeColour(int Category){
        for(int i=0;i<pixels.size();i++){
            if(Category==0){
                pixels.get(i).changeColorRed();
            }
            else if(Category==1){
                pixels.get(i).changeColorBlue();
            }
            else if(Category==2){
                pixels.get(i).changeColorGreen();
            }
        }
    }

    /**
    * This method returns the category colour
    * @return int Returns the category colour in the form of an integer
    */
    public int getCategoryColor(){
        return color.getRGB();
    }

        // radius=longestPixelChain/2;
//         if(radius<4){
//             changeColour(0);
//         }
//         else if(4<=radius&&radius<8){
//             changeColour(1);
//         }
//         else if(8<=radius){
//             changeColour(2);
//         }
    // }
    
      /**
      * This method sets the colours of discs/circles in a particular category
      * @param cat This is an array of type Category with all the discs that needs their colour set
      */
      public void setColor(ArrayList<Category> cat){
            for(Category c : cat){
                if (c.getMin <= radius){
                    if(c.getMax >= radius){
                        color = c.getCategoryColor();
                        return;
                    }
                }
            }
        color = Color.YELLOW; //circle does not fall into category
      }

      /**
      * This method sets the method to the longest pixel chain
      * @param longestPixelChain This is used to set to radius
      */
      public void setLongestPixelChain(int longestPixelChain){
            radius = longestPixelChain;
      }
}
