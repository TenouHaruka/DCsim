package app.src.main.java.DCsim.components;

public class Pose {

    private int x, y, rotation, width, height;

    public Pose(int x, int y, int rotation, int width, int height){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.width = width;
        this.height = height;
    } 

    /* Getters */

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getRotation(){
        return this.rotation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /* MOVE functions */
    public void moveX(int x) {
        this.x = x;
    }

    public void moveY(int y) {
        this.y = y;
    }

    public void moveCoordinates(int x, int y) {
        moveX(x);
        moveY(y);
    }

    public void moveRotation(int rotation) {
        this.rotation = rotation;
    }

}
