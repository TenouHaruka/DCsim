package components;

public class Pose {
    private int x, y, rotation;
    
    public Pose(int x, int y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getRotation() {
        return rotation;
    }
    
    public void moveX(int x) {
        this.x = x;
    }
    
    public void moveY(int y) {
        this.y = y;
    }
    
    public void moveCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void moveRotation(int rotation) {
        this.rotation = rotation;
    }
}
