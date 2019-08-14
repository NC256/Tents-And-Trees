public class CoordinatePair {

    private int x;
    private int y;

    CoordinatePair(int x, int y){
        this.x = x;
        this.y = y;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setX(int x){
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj){
            return true;
        }

        //Also protects against obj being null
        if(!(obj instanceof CoordinatePair)){
            return false;
        }
        else{
            //If our x and y equals its x and y, then it's equal
            return (((CoordinatePair) obj).getX() == this.x && ((CoordinatePair) obj).getY() == this.y);
        }
    }
}
