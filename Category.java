public class Category extends Object{

    public Category() {
        super();
    }

    public void changeColour(int Category){
        for(int i=0;i<pixels.size();i++){
            if(Category==0){
                pixels.get(i).changeColorRed();
            }
            else if(Category==1){
                pixels.get(i).changeColorBlue();
            }
            else if(Category==2){
                pixels.get(i).changeColorGreen();
            }
        }
    }
}
