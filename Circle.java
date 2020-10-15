import java.util.ArrayList;
import java.awt.*;

public class Circle{
    public int radius;
    public ArrayList<Pixel> pixels;
    private Color color;
    
    public Circle(ArrayList<Pixel> pixels) {
		 this.pixels = pixels;
	}

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
      public void setColor(Category[] cat){
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

      public void setLongestPixelChain(int longestPixelChain){
            radius = longestPixelChain;
      }
}
