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

    public ArrayList<ArrayList<Pixel>> ObjectLines ;

    /**
     * @serial FirstFrame
     * super from the Frame constructor. Added initialization of the ObjectLines
     * Arraylist. This also calls the readLines() method
     * */

    public FirstFrame(String filename, Category[] catArray, GUI gui){
        super(filename, catArray, gui);
        marchThroughImage(image);
        ObjectLines = new ArrayList<ArrayList<Pixel>>() ;
        readLines();
    }

    /**
     * @serial readLines
     * This method separates the lines from the Pixel array and sends them seperately
     * to the buildlines method. It then calls the merge method to finish linking the
     * pixels in frame.
     * */

    public void readLines(){
        for(int i=0;i<height;i++){
            buildLines(pixelArray.get(i)) ;
        }
        mergeLines() ;
        getObjects();
    }

    public void getObjects(){
        System.out.println("Number of objects: "+objects.size());
        for (int i=0;i<objects.size();i++){
            if(objects.get(i).checkCircle()){
                Circle tempCircle=objects.get(i).circlefy();
                circles.add(tempCircle);
            }
        }
        System.out.println("Number of circles: "+circles.size());
    }

    /**
     * @serial mergeLines
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
     * @serial buildLines
     * Builds Object lines. When a pixel above a threshold is found, a new line is
     * started. If the next pixel is not also above that threshold, the line is
     * ended
     * */

    public void buildLines(ArrayList<Pixel> pixels){
        ArrayList<Pixel> newLine = new ArrayList<Pixel>() ;
        for (int i=0;i<width;i++){
            if(pixels.get(i).aboveThreshold(120)){
                newLine.add(pixels.get(i)) ;
                if((i==(width-1))||(!pixels.get(i+1).aboveThreshold(120))){
                    if(!newLine.isEmpty()){
                        ObjectLines.add(newLine) ;
                        newLine = new ArrayList<Pixel>() ;
                    }
                }
            }
        }
    }
}
