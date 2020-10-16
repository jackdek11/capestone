import java.io.File;
import java.util.*;
import java.awt.Toolkit;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Driver extends Thread{
    private  ArrayList<Frame> frames;
    private  ArrayList<ArrayList<Pixel>> currentImage;
    private  File gif;
    private  FirstFrame firstF;
    private int numObjects,numCircles;
    private  Frame tempFrame;
    private  int width, height, numOfFrames;
    private  boolean foundFirst;
    private  File gifFrames;
    private  ArrayList<Category> arrayOfCategories;
    private GUI gui;
    private String inFolder, outFolder;

    public Driver(String inputFolder, String outputFolder, ArrayList<Category> catArray, GUI gui){
        inFolder=inputFolder;
        outFolder=outputFolder;
        numCircles=0;
        numObjects=0;
        this.gui=gui;
        gifFrames = new File(inputFolder);
        arrayOfCategories = catArray;
    }

    public void proofOfConcept(){
        System.out.println(firstF.getStepRef());
    }

    public void LoadVideo(File gifFolder) {
        File[] gif = gifFolder.listFiles();
        Arrays.sort(gif);
        numOfFrames = 0; // was 1
        foundFirst = false;
        for (File g: gif) {
            String pathOfGifFile = gifFolder.getName() + "\\" + g.getName();
            if(checkFile(g)) { //check file will check if file is a gif or not
                if (!foundFirst) {
                    firstF = new FirstFrame(g.getName(), pathOfGifFile,arrayOfCategories,gui); //need to parse category array as well
                    currentImage= firstF.getPixelArray();
                    // firstF.createProcessedFrame(outFolder);
                    frames.add(firstF);
                    firstF.setIndex(0);
                    width = firstF.getWidth();
                    height = firstF.getHeight();
                    numCircles=firstF.getNumberOfCircles();
                    numObjects=firstF.getNumberOfObjects();
                    foundFirst = true;
                    // firstF.createProcessedFrame(outFolder);
                }
                else if(numOfFrames==firstF.getStepRef()){
                    firstF=new FirstFrame(g.getName(), pathOfGifFile,arrayOfCategories,gui);
                    firstF.setIndex(numOfFrames);
                    numObjects+=firstF.getNumberOfObjects();
                    numCircles+=firstF.getNumberOfCircles();
                    // firstF.createProcessedFrame(outFolder);
                    frames.add(firstF);
                }
                else {
                    tempFrame=new Frame(pathOfGifFile);
                    tempFrame.setPixelArray(currentImage);
                    tempFrame.marchForwardThroughBuffer();
                    currentImage = tempFrame.getPixelArray();
                    // tempFrame.createProcessedFrame(outFolder);
                    frames.add(tempFrame); //need to parse category array as well
                }
                numOfFrames++;
            }
            else {
                System.out.println("File "+ numOfFrames + " in " + gifFolder.getName()+ " is not a gif.");
            }
            gui.changeNumberOfFramesDetected(numOfFrames);
            gui.changeNumberOfDisksDetected(numCircles);
            gui.changeNumberOfObjectsDetected(numObjects);
            //numOfFrames++;
        }
    }

    public boolean checkFile(File f){
        boolean okay = false;
        if(checkFileFormat(f)) {
            System.out.println(inFolder + "\\" +f.getName());
            Frame tempFrame = new Frame(inFolder + "\\" +f.getName());
            // String fN = "Dir6/" +f.getName();
//             Frame tempFrame = new Frame(fN);
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

    public boolean checkFileFormat(File file) { //checks if frame is a Gif. Important for testing.
        boolean isGif = false;
        String nameOfGif = file.getName();
        if (nameOfGif.substring(nameOfGif.length()-4).equals(".gif")) {
            isGif = true;
        }
        return isGif;
    }

    public boolean checkSize(Frame file) {
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

    public boolean widthOkay(int w) { //ensures frame doesn't have transparent borders
        boolean okay = false;
        if(w == firstF.getWidth()) {
            okay = true;
        }
        return okay;
    }

    public boolean heightOkay(int h) { //ensures frame doesn't have transparent borders
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
