/**
 *@author Jack de Kock
 *@version 29th August 2020
 *Frame object which extends Component and builds the checks the last pixels for new object pixels
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Frame extends Component {

    public ArrayList<ArrayList<Pixel>> ObjectLines;
    public int width,height;
    public BufferedImage image;
    public LinkedList<Circle> circles;
    public ArrayList<ArrayList<Pixel>> pixelArray;
    public LinkedList<Pixel> objectPixels;
    public ArrayList<Object> objects;

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

    public ArrayList<ArrayList<Pixel>> marchForwardThroughBuffer(ArrayList<ArrayList<Pixel>> pastArray){
        for (int i=0;i<height;i++){
            pastArray.get(i).remove(0);
            int pixel = image.getRGB(width-1,i);
            setPixelARGB(pixel,pastArray.get(i),i,width-1);
        }
        pixelArray=pastArray;
        return pixelArray;
    }

    public void checkPixelBuffer(){
        for(int i=0;i<height;i++){
            if(pixelArray.get(width-1).get(i).aboveThreshold(120)&&(!pixelArray.get(width-1).get(i).owned)){
                objectPixels.add(pixelArray.get(width-1).get(i));
            }
        }
    }

    public Frame(String filename){
        try {
            image =
                    ImageIO.read(this.getClass().getResource(filename));
            width = image.getWidth();
            height = image.getHeight();
            pixelArray = new ArrayList<ArrayList<Pixel>>();
            objects = new ArrayList<Object>();
            ObjectLines = new ArrayList<ArrayList<Pixel>>() ;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

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
