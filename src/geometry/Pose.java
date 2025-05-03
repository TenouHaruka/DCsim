package geometry;

public class Pose {
    private double x;
    private double y;
    private double rotation;
    private double width;
    private double length;

    public Pose(double x, double y, double rotation, double width, double length) 
    {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.width = width;
        this.length = length;
    }

    public double getX() 
    {
        return x;
    }

    public double getY() 
    {
        return y;
    }

    public double getRotation() 
    {
        return rotation;
    }

    public double getWidth() 
    {
        return width;
    }

    public double getLength() 
    {
        return length;
    }
}