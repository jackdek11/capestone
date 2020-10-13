public class Pixel {

    private int R,G,B,x,y;
    public boolean owned;

    public Pixel(int R, int G, int B,int x,int y ){
        owned = false;
        this.R=R;
        this.G=G;
        this.B=B;
        this.x=x;
        this.y=y;
    }
    public int getX(){
        return x;
    }

    public int getY() {return y;}

    public void setOwned(){
        owned=true;
    }

    public void changeColorRed(){
        G=0;
        R=180;
        B=0;
    }
    public void changeColorGreen(){
        R=0;
        B=0;
        G=180;
    }
    public void changeColorBlue(){
        R=0;
        B=180;
        G=0;
    }

    public boolean closeTo(Pixel p){
        if (Math.abs(p.getX()-x)<=2&&Math.abs(p.getY()-y)<=2)
            return true;
        return false;
    }

    public boolean aboveThreshold(int x){
        if (R>x||G>x||B>x){
            return true;
        }
        return false;
    }
    public String printPixel(){
        return (""+R);
    }
}

