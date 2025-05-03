package DCsim.components;

public class Pose {

    private int x, y, rotation;

    public Pose(int x, int y, int rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
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
