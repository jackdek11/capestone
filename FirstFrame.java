import java.util.ArrayList;

/**
 * @author Jack de Kock
 * @version 1.0
 * @since 2020-08-15
 * FirstFrame object which extends Frame and builds the initial model of the scene
 */

public class FirstFrame extends Frame {

    /**
     * @param ObjectLines
     * This Parameter is an arraylist of arraylists of pixels. It holds all the lines of
     * consecutive pixels above the threshold of 50
     * */

    private int stepRef,index;
    private ArrayList<ArrayList<Pixel>> ObjectLines ;

    /**
     * Super from the Frame constructor. This also calls the readLines() method and initialises the objectLines variable
     * @param fileName This initialises the filename variable
     * @param filepath This initialises the filepath variable
     * @param catArray This initialises the catArray variable
     * @param gui This initialises the gui variable
     */

    public FirstFrame(String filename, String filepath, ArrayList<Category> catArray, GUI gui){
        super(filename, filepath, catArray, gui);
        index=0;
        marchThroughImage(super.getImage());
        stepRef=super.getWidth();
        ObjectLines = new ArrayList<ArrayList<Pixel>>() ;
        readLines();
    }

    /**
     * This method separates the lines from the Pixel array and sends them seperately
     * to the buildlines method. It then calls the merge method to finish linking the
     * pixels in frame.
     * */

    public void readLines(){
        int temp=0;
        for(int i=0;i<super.getHeight();i++){
            temp=Math.max(temp,buildLines((super.getPixelArray()).get(i)));
        }
        stepRef=temp;
        mergeLines() ;
        getObjects();
    }

    /**
    * This method sets the index in the pixel array
    * @param index This is the index to be set
    */
    public void setIndex(int index){
        this.index=index;
    }

    /**
    * This method identifies all the circles in the first frame and adds to the circles arraylist
    */
    public void getObjects(){
        for (int i=0;i<super.getObjectsArray().size();i++){
            if(super.getObjectsArray().get(i).checkCircle()){
                Circle tempCircle=super.getObjectsArray().get(i).circlefy();
                tempCircle.setColor(super.catArray);
                circles.add(tempCircle);
            }
        }
    }

    /**
     * This method checks the proximity of pixels in the middle of the object
     * pixel lines to link them
     * */

    public void mergeLines(){
        for (int i=0;i<ObjectLines.size();i++){
            if(!ObjectLines.get(i).get(0).owned){
                Object newObject = new Object() ;
                newObject.addPixelLine(ObjectLines.get(i)) ;
                Pixel ref = ObjectLines.get(i).get(ObjectLines.get(i).size()/2) ;

                for(int j=i+1;j<ObjectLines.size();j++){
                    if (ref.closeTo(ObjectLines.get(j).get(ObjectLines.get(j).size()/2))){
                        newObject.addPixelLine(ObjectLines.get(j)) ;
                        ref=ObjectLines.get(j).get(ObjectLines.get(j).size()/2) ;
                    }
                }
                objects.add(newObject) ;
            }
        }
    }

    /**
     * This method builds Object lines. When a pixel above a threshold is found, a new line is
     * started. If the next pixel is not also above that threshold, the line is ended
     * @param pixels This is arraylist of pixels that the object lines are built from
     * @return int Returns the temporary offset as an integer
     */
    public int buildLines(ArrayList<Pixel> pixels){
        ArrayList<Pixel> newLine = new ArrayList<Pixel>() ;
        int tempOffset=0;
        for (int i=0;i<width;i++){
            if(pixels.get(i).aboveThreshold(120)){
                newLine.add(pixels.get(i)) ;
                if(i==width-1){
                    tempOffset=Math.max(tempOffset,newLine.size());
                    newLine=new ArrayList<Pixel>();
                }
                else if(!pixels.get(i+1).aboveThreshold(120)){
                    if(!newLine.isEmpty()){
                        ObjectLines.add(newLine) ;
                        newLine = new ArrayList<Pixel>() ;
                    }
                }
            }
        }
        return tempOffset;
    }

    /**
    * This method returns the number of objects 
    * @return int Returns the answer as an integer
    */
    public int getNumberOfObjects(){
        return objects.size();
    }

    /**
    * This method returns the number of circles 
    * @return int Returns the answer as an integer
    */    
    public int getNumberOfCircles(){
        return circles.size();
    }

    /**
    * This method returns the step reference by adding the width and index and subtracting the step reference
    * @return int Returns the answer as an integer
    */
    public int getStepRef(){
        return width+index-stepRef;
    }
}
