import java.util.ArrayList;

public class Circle{
    public int radius;
    public ArrayList<Pixel> pixels;
    
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

 //    public void getColour(){
//         radius=longestPixelChain/2;
//         if(radius<4){
//             changeColour(0);
//         }
//         else if(4<=radius&&radius<8){
//             changeColour(1);
//         }
//         else if(8<=radius){
//             changeColour(2);
//         }
//     }
}
