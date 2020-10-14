import java.io.File;
import java.util.*;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.text.html.HTMLDocument;

public class Driver extends Thread{
    private static ArrayList<Frame> frames;
    private static ArrayList<ArrayList<Pixel>> currentImage;
    private static File gif;
    private static FirstFrame firstF;
    private static Frame tempFrame;
    private static int width, height, numOfFrames;
    private static boolean foundFirst;
    private static File gifFrames;
    private static Category[] arrayOfCategories;
    private static GUI gui;
    private static String inFolder, outFolder;

    public Driver(String inputFolder, String outputFolder, Category[] catArray, GUI gui){
        inFolder=inputFolder;
        outFolder=outputFolder;
        this.gui=gui;
        gifFrames = new File(inputFolder);
        arrayOfCategories = catArray;
    }

    public static void proofOfConcept(){
        Panarama pan = new Panarama(currentImage,height);
        //pan.printPan();
        System.out.println(pan.objects.size());
    }

    public void LoadVideo(File gifFolder) {
        File[] gif = gifFolder.listFiles();
        Arrays.sort(gif);
        numOfFrames = 0; // was 1
        foundFirst = false;
        for (File g: gif) {
            String pathOfGifFile = gifFolder.getName() + "/" + g.getName();
            //System.out.println(pathOfGifFile);
            if(checkFile(g)) { //check file will check if file is a gif or not
                if (!foundFirst) {
                    firstF = new FirstFrame(pathOfGifFile,arrayOfCategories,gui); //need to parse category array as well
                    currentImage= firstF.getPixelArray();
                    frames.add(firstF);
                    width = firstF.getWidth();
                    height = firstF.getHeight();
                    foundFirst = true;
                }
                else {
                    tempFrame=new Frame(pathOfGifFile);
                    tempFrame.setPixelArray(currentImage);
                    tempFrame.marchForwardThroughBuffer();
                    currentImage = tempFrame.getPixelArray();
                    frames.add(tempFrame); //need to parse category array as well
                }
            }
            else {
                System.out.println("File "+ numOfFrames + " in " + gifFolder.getName()+ " is not a gif.");
            }
            gui.changeNumberOfFramesDetected(numOfFrames); //.dat file adds. need to move num of frame
            numOfFrames++;
        }
        proofOfConcept();
    }

    public static boolean checkFile(File f){
        boolean okay = false;
        if(checkFileFormat(f)) {
            Frame tempFrame = new Frame("Dir6/"+f.getName());
            if(foundFirst){
                if(checkSize(tempFrame)) {
                    okay = true;
                }
            }
            else{
                okay = true;
            }
        }
        return okay;
    }

    public static boolean checkFileFormat(File file) { //checks if frame is a Gif. Important for testing.
        boolean isGif = false;
        String nameOfGif = file.getName();
        if (nameOfGif.substring(nameOfGif.length()-4).equals(".gif")) {
            isGif = true;
        }
        return isGif;
    }

    public static boolean checkSize(Frame file) {
        boolean widthFine = false;
        boolean heightFine = false;
        if (widthOkay(file.getWidth())) {
            widthFine = true;
        }
        if (heightOkay(file.getHeight())) {
            heightFine = true;
        }
        return widthFine && heightFine;
    }

    public static boolean widthOkay(int w) { //ensures frame doesn't have transparent borders
        boolean okay = false;
        if(w == firstF.getWidth()) {
            okay = true;
        }
        return okay;
    }

    public static boolean heightOkay(int h) { //ensures frame doesn't have transparent borders
        boolean okay = false;
        if(h == firstF.getHeight()) {
            okay = true;
        }
        return okay;
    }

    public void run(){
        boolean working = true;
        Image img = Toolkit.getDefaultToolkit().createImage("giphyNew.gif");
        ImageIcon imageI = new ImageIcon(img);
        gui.changeIcon(imageI);
        while(working) {
            frames = new ArrayList<Frame>();
            if(gifFrames.isDirectory()) {
                LoadVideo(gifFrames);
                working = false;
            }

            else {
                System.out.println("Input is not a directory. Please re-enter appropriate directory name.");
            }
        }
    }

    public Driver(String inputFolder, String outputFolder, GUI gui){ //possibly removed later
        gifFrames = new File(inputFolder);
        currentImage=new ArrayList<ArrayList<Pixel>>();
        frames= new ArrayList<Frame>();
        this.gui = gui;
    }
}
