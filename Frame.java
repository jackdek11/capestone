/**
* This class is given an Object array from the previous image in the sequence and evaluates 
* all the new pixels coming into frame, to see if they are part of an already identified 
* image or if they are part of some new image
* @author Jack de Kock
* @version 1.0
* @since 15/08/2020
*/

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
// import java.io.ByteArrayInputStream;
// import org.opencv.core.Core;
// import org.opencv.core.Mat;
// import org.opencv.core.MatOfByte;
// import org.opencv.core.Point;
// import org.opencv.core.Scalar;
// import org.opencv.imgcodecs.Imgcodecs;
// import org.opencv.imgproc.Imgproc;

public class Frame extends Component {
    /**
     *@author Jack de Kock
     *@version 29th August 2020
     *Frame object which extends Component and builds the checks the last pixels for new object pixels
     */
    protected ArrayList<ArrayList<Pixel>> ObjectLines;
    protected int width,height;
    protected BufferedImage image;
    protected ArrayList<Circle> circles;
    protected ArrayList<ArrayList<Pixel>> pixelArray;
    protected ArrayList<Object> objects;
    protected ArrayList<Category> categoryArray;
    protected GUI gui;
    private String filename, filepath;

    /**
    * This methods prints out 0 if the pixels from a specified index in the pixel array is 
    * above the theshold of 50
    * @param index This is the index it checks from
    */
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

    /**
    * This method sets the ARGB values of of pixel
    * @param pixel This is the pixel to be set
    * @param temp This is a temporary arraylist of type Pixel that the pixel with set ARGB values is added to
    * @param w This is a width to parse to the Pixel class
    * @param h This is a height to parse to the Pixel class
    */
    public void setPixelARGB(int pixel, ArrayList<Pixel> temp,int w,int h) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        temp.add(new Pixel(red,green,blue,w,h));
    }

    /**
    * This method scans an image's pixels
    * @param image This is the image that must be scanned
    */
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

    /**
    * This method continues scanning through an image
    */
    public void marchForwardThroughBuffer(){
        for (int i=0;i<height;i++){
            //pixelArray.get(i).remove(0);
            int pixel = image.getRGB(width-1,i);
            setPixelARGB(pixel,pixelArray.get(i),i,width-1);
        }
    }
    
    /**
    * This method is used to create a processed frame and write to an output folder specified by the user
    * @param outputFolder This is the output folder specified by the user
    */
    public void createProcessedFrame(String outputFolder){ 

        // code to change image color

        try{
             // File processedFrame = new File("C:\\Users\\amyso\\Documents\\Dir6\\hello.gif"); //filename.substring(0, (filename.length()-4)) + "_processed.gif");
             System.out.println(filename.substring(0, (filename.length()-4)));
             File processedFrame = new File(outputFolder + "\\" + filename.substring(0, (filename.length()-4)) + "_processed.gif");
             ImageIO.write(image,"gif", processedFrame);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public int getCat1(){
        return categoryArray.get(0).getNumberOfDisksInCategory;   
    }    
    public int getCat2(){
        return categoryArray.get(1).getNumberOfDisksInCategory;   
    }   
    public int getCat3(){
        return categoryArray.get(2).getNumberOfDisksInCategory;   
    }   

    /**
    * This is the constructor for the Frame class
    * @param filename Initialises the filename variable
    * @param filePath Initialises the filePath variable
    * @param catArray Initialises the catArray variable
    * @param gui Initialises the gui variable
    */
    public Frame(String filename, String filepath, ArrayList<Category> catArray, GUI gui){
        try {
            this.filename = filename;
            this.filepath = filepath;
            categoryArray = catArray;
            this.gui = gui;
            image = ImageIO.read(new File(filepath));
                    // ImageIO.read(this.getClass().getResource(filename));
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

    /**
    * Another constructor for the Frame class
    * @param filename This is the file that needs to be read
    */
    public Frame(String filepath){
        try {
            image = ImageIO.read(new File(filepath));
                    // ImageIO.read(this.getClass().getResource(filename));
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

    /**
    * This method returns the width of a frame
    * @return int Returns the width as an integer
    */
    public int getWidth(){ return width; }
    
    /**
    * This method returns the height of a frame
    * @return int Returns the height as an integer
    */    
    public int getHeight(){ return height; }
    
    /**
    * This method returns all the pixels in the pixelArray
    * @param i This is the index of the row in the pixelArray
    * @param j This is the column in the pixelArray
    * @return Pixel Returns all the pixels as type Pixel
    */
    public Pixel getPixel(int i, int j){ return pixelArray.get(i).get(j); }
    
    /**
    * This method sets the pixelArray
    * @param pixelArray This is the pixel array to be set
    */
    public void setPixelArray(ArrayList<ArrayList<Pixel>> pixelArray){ this.pixelArray=pixelArray; }
    
    /** 
    * This method returns the pixel array
    * @return ArrayList<ArrayList<Pixel>> Returns the pixel array as a nested arraylist of type Pixel
    */
    public ArrayList<ArrayList<Pixel>> getPixelArray(){ return pixelArray; }
    
    /**
    * This method returns an image
    * @param image This is the image to be returned
    * @return BufferedImage Returns the image as type BufferedImage
    */
    public BufferedImage getImage(){return image;}
    
    /**
    * This method returns all the objects in the objects array
    * @return ArrayList<Object> Returns the objects as an arraylist of type Object
    */
    public ArrayList<Object> getObjectsArray(){ return objects;}
}
