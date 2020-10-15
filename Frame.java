import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Frame extends Component {
    /**
     *@author Jack de Kock
     *@version 29th August 2020
     *Frame object which extends Component and builds the checks the last pixels for new object pixels
     */
    public ArrayList<ArrayList<Pixel>> ObjectLines;
    public int width,height;
    public BufferedImage image;
    public ArrayList<Circle> circles;
    public ArrayList<ArrayList<Pixel>> pixelArray;
    public ArrayList<Object> objects;
    private Category[] categoryArray;
    private GUI gui;
    private String filename;

    public void printPan(int index){
        for (int i =0; i < height; i++) {
            for (int j = index; j < index+width; j++) {
                if (pixelArray.get(i).get(j).aboveThreshold(50)) {
                    System.out.print("0");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void setPixelARGB(int pixel, ArrayList<Pixel> temp,int w,int h) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        temp.add(new Pixel(red,green,blue,w,h));
    }

    public void marchThroughImage(BufferedImage image) {
        for (int i = 0; i < height; i++) {
            ArrayList<Pixel> temp = new ArrayList<Pixel>();
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                setPixelARGB(pixel, temp,i,j);
            }
            pixelArray.add(temp);
        }
    }

    public void marchForwardThroughBuffer(){
        for (int i=0;i<height;i++){
            //pixelArray.get(i).remove(0);
            int pixel = image.getRGB(width-1,i);
            setPixelARGB(pixel,pixelArray.get(i),i,width-1);
        }
    }
    
    public void createProcessedFrame(String outputFolder){ 
         for(int i = 0; i < width; i++){
             for(int k = 0; k < height; k++){
                 for(Circle cir : circles){
                     int colorOfCat = cir.getCategoryColor(); // make method that gets for example Color.green.getRGB() for green cat circle
                     for(Pixel p : cir.pixels){
                         image.setRGB(k,i, colorOfCat);
                     }
                 }
             }
         }
 
         try{
             File processedFrame = new File(outputFolder + filename.substring(0, (filename.length()-4)) + "_processed.gif");
             ImageIO.write(image,".gif", processedFrame);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public Frame(String filename, Category[] catArray, GUI gui){
        try {
            this.filename = filename;
            categoryArray = catArray;
            this.gui = gui;
            image =
                    ImageIO.read(this.getClass().getResource(filename));
            width = image.getWidth();
            height = image.getHeight();
            pixelArray = new ArrayList<ArrayList<Pixel>>();
            objects = new ArrayList<Object>();
            circles=new ArrayList<Circle>();
            ObjectLines = new ArrayList<ArrayList<Pixel>>() ;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public Frame(String filename){
        try {
            image =
                    ImageIO.read(this.getClass().getResource(filename));
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    Frame(){}

    /**
     *@method getObjectPixels()
     *@return LinkedList<Pixel> objectPixels
     */

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public Pixel getPixel(int i, int j){ return pixelArray.get(i).get(j); }
    public void setPixelArray(ArrayList<ArrayList<Pixel>> pixelArray){ this.pixelArray=pixelArray; }
    public ArrayList<ArrayList<Pixel>> getPixelArray(){ return pixelArray; }
}
