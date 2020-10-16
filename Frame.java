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

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
    public Pixel getPixel(int i, int j){ return pixelArray.get(i).get(j); }
    public void setPixelArray(ArrayList<ArrayList<Pixel>> pixelArray){ this.pixelArray=pixelArray; }
    public ArrayList<ArrayList<Pixel>> getPixelArray(){ return pixelArray; }
    public BufferedImage getImage(){return image;}
    public ArrayList<Object> getObjectsArray(){ return objects;}
}
