/**
* This class takes in a file directory to unpack and evaluates all the .gif files within the inputted directory. The driver checks 
* that all the files within the directory are the correct format, and builds Frame objects from them, the first frame to be built 
* being a FirstFrame object. The driver also tells the Frame objects when to start building their objects, as well as and contains 
* all the shared data structures used by the remaining objects
* @author Amy Solomons
* @version 1.0
* @since 15/08/2020
*/
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

    /**
    * Constructor for the Driver class
    * @param inputFolder initialises the inputFolder variable
    * @param outputFolder initialises the outputFolder variable
    * @param catArray initialises the catArray variable
    * @param gui initialises the gui variable    
    */
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

    /**
    * This method loads a gif file from the directory selected by the user
    * @param gifFolder This is the specified folder selected by the user to be loaded
    */
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
                    numCat1=firstF.getCat1();
                    numCat2=firstF.getCat2();
                    numCat3=firstF.getCat3();
                    foundFirst = true;
                    // firstF.createProcessedFrame(outFolder);
                }
                else if(numOfFrames==firstF.getStepRef()){
                    firstF=new FirstFrame(g.getName(), pathOfGifFile,arrayOfCategories,gui);
                    firstF.setIndex(numOfFrames);
                    numObjects+=firstF.getNumberOfObjects();
                    numCircles+=firstF.getNumberOfCircles();
                    numCat1+=firstF.getCat1();
                    numCat2+=firstF.getCat2();
                    numCat3+=firstF.getCat3();
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
            gui.changeNumberDisksInRed(numCat1);
            gui.changeNumberDisksInBlue(numCat2);
            gui.changeNumberDisksInGreen(numCat3);
            //numOfFrames++;
        }
    }

    /**
    * This method checks if the file format and size is suitable to be added to the list of frames.
    * @param f This is the file to be checked
    */
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

    /**
    * This method checks if the loaded file is a GIF or not
    * @param file This is the file to be checked
    * @return boolean Returns the answer as a boolean value
    */
    public boolean checkFileFormat(File file) { 
        boolean isGif = false;
        String nameOfGif = file.getName();
        if (nameOfGif.substring(nameOfGif.length()-4).equals(".gif")) {
            isGif = true;
        }
        return isGif;
    }

    /**
    * This method checks if the size of the file is the same as the size of the first frame. 
    * This ensures that each frame does not have transparent borders or is not smaller than the first frame detected.
    * @param file This is the file to be checked
    * @return Returns the answer as a boolean value
    */
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

    /**
    * This method checks that the width provided as an argument is the same as the width of the first frame.
    * @param w This is the width provided that the width of the first frame is checked against
    * @return boolean Returns the answer as a boolean value
    */
    public boolean widthOkay(int w) { //ensures frame doesn't have transparent borders
        boolean okay = false;
        if(w == firstF.getWidth()) {
            okay = true;
        }
        return okay;
    }

    /**
    * This method checks that the height provided as an argument is the same as the width of the first frame.
    * @param h This is the height provided that the height of the first frame is checked against
    * @return boolean Returns the answer as a boolean value
    */    
    public boolean heightOkay(int h) { //ensures frame doesn't have transparent borders
        boolean okay = false;
        if(h == firstF.getHeight()) {
            okay = true;
        }
        return okay;
    }

    
    /**
    * This method runs the Driver class and starts loading the data from the gifFolder specified
    */
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


    /**
    * Another constructor for the Driver class
    * @param inputFolder initialises the inputFolder variable
    * @param outputFolder initialises the outputFolder variable
    * @param gui initialises the gui variable    
    */
    public Driver(String inputFolder, String outputFolder, GUI gui){ //possibly removed later
        gifFrames = new File(inputFolder);
        currentImage=new ArrayList<ArrayList<Pixel>>();
        frames= new ArrayList<Frame>();
        this.gui = gui;
    }
}
