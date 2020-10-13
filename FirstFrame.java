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

    public FirstFrame(String filename){
        super(filename) ;
        marchThroughImage(image);
        ObjectLines = new ArrayList<ArrayList<Pixel>>() ;
        readLines() ;
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
            if(pixels.get(i).aboveThreshold(50)){
                newLine.add(pixels.get(i)) ;
                if((i==(width-1))||(!pixels.get(i+1).aboveThreshold(50))){
                    if(!newLine.isEmpty()){
                            ObjectLines.add(newLine) ;
                            newLine = new ArrayList<Pixel>() ;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        FirstFrame first= new FirstFrame("Dir6/testseq100000.gif");
        for (int i=0;i<first.objects.size();i++){
            if(first.objects.get(i).checkCircle()){
                Circle circle = (Circle) first.objects.get(i);
                first.circles.add(circle);
            }
        }

    }

    /*
    public static void main(String[] args) {
        FirstFrame firstFrame = new FirstFrame("Dir6/testseq100008.gif");
        ArrayList<ArrayList<Pixel>> currentPixels=firstFrame.getPixelArray();
        Frame frame1 = new Frame("Dir6/testseq100009.gif");
        frame1.setPixelArray(currentPixels);
        frame1.marchForwardThroughBuffer();
        Frame frame2 = new Frame("Dir6/testseq100010.gif");
        frame2.setPixelArray(frame1.getPixelArray());
        frame2.marchForwardThroughBuffer();
        Frame frame3 = new Frame("Dir6/testseq100011.gif");
        frame3.setPixelArray(frame2.getPixelArray());
        frame3.marchForwardThroughBuffer();
        for (int i = 0; i < frame3.ObjectLines.size(); i++) {
            for (int j = 0; j < frame3.ObjectLines.get(i).size(); j++) {
                frame3.ObjectLines.get(i).get(j).changeColorBlue();
            }
        }
        for (int i = 0; i < frame3.height; i++) {
            for (int j = 0; j < frame3.width; j++) {
                if (frame3.pixelArray.get(i).get(j).aboveThreshold(50)) {
                    System.out.print("0");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }*/
}
/*
    public static void main(String[] args) {
        FirstFrame frame= new FirstFrame("Dir6/testseq100000.gif") ;
        for(int i =0;i<frame.ObjectLines.size();i++){
            for(int j=0;j<frame.ObjectLines.get(i).size();j++){
                frame.ObjectLines.get(i).get(j).changeColorBlue() ;
            }
        }
        for (int i=0;i<frame.objects.get(5).pixels.size();i++){
            frame.objects.get(5).pixels.get(i).changeColorRed() ;
        }
        for (int i=0;i< frame.height;i++){
            for (int j=0;j< frame.width;j++){
                if (frame.pixelArray[i][j].aboveThreshold(50)){
                    System.out.print(frame.pixelArray[i][j].printPixel()) ;
                }
                else{
                    System.out.print(" ") ;
                }
            }
            System.out.println() ;
        }
    }*/