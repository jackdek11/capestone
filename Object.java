import java.util.ArrayList;

public class Object {
    public ArrayList<Pixel> pixels;
    int longestPixelChain;
    boolean isCircle,isbuilding;
    double CircleAreaWithGivenRadius;
    double CirclePerimeterWithGivenRadius;

    public Object(){
        isCircle=false;
        isbuilding=false;
        CircleAreaWithGivenRadius=0.0;
        longestPixelChain=0;
        pixels = new ArrayList<Pixel>();
        CirclePerimeterWithGivenRadius=0.0;
    }

    public void addPixel(Pixel toBeAdded){
        toBeAdded.setOwned();
        pixels.add(toBeAdded);
    }

    public void addPixelLine(ArrayList<Pixel> pixelLine){
        for(int i=0; i<pixelLine.size();i++){
            pixelLine.get(i).setOwned();
            pixels.add(pixelLine.get(i));
        }
    }

    public boolean isIn(Pixel checkable){
        for (int i =0;i<pixels.size();i++){
            if(pixels.get(i)==checkable)
                return true;
        }
        return false;
    }

    public boolean isPartOf(Pixel p){
        for (int i=0;i<pixels.size();i++){
            if (pixels.get(i).closeTo(p)){
                return true;
            }
        }
        return false;
    }

    public double isCircle(){
        double radius = getLongestPixelChain()/2;
        return radius;
    }

    public double getSurfaceArea(){
        double radius = isCircle();
        CircleAreaWithGivenRadius = (Math.PI*(radius*radius));
        return CircleAreaWithGivenRadius;
    }

    public double getPerimeter() {
        double radius = isCircle();
        CirclePerimeterWithGivenRadius = (2*Math.PI*radius);
        return CirclePerimeterWithGivenRadius;
    }

    public Circle circlefy(){
        Circle toCircle = new Circle(pixels);
        return toCircle;
    }

    public boolean checkCircle(){
        //System.out.println("Meant to be: "+ getSurfaceArea()+"     | is: "+pixels.size());
        if(getSurfaceArea()!=0&&(pixels.size()/getSurfaceArea())>=1) {
            isCircle = true;
            return true;
        }
        return false;
    }


    public int getLongestPixelChain() {
        int min=200;
        int max=0;
        int num=0;
        while(pixels.size()>num){
            Pixel current = pixels.get(num);
            int xVal = current.getX();
            min=Math.min(min,xVal);
            max=Math.max(max,xVal);
            num++;
        }
        longestPixelChain=max-min;
        return longestPixelChain;
    }
}
