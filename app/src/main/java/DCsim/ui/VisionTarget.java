package DCsim.ui;

import java.util.LinkedList;
import java.util.Queue;

public class VisionTarget 
{
    final static long MAX_TARGET_LIFE = 500;
    final static int AVG_NUM = 5;

    private Queue<Double> yValues;
    private Queue<Double> xValues;

    private double xSum, ySum;

    private final int blockID;
    long lastTime;

    public VisionTarget(int blockID)
    {
        this.blockID = blockID;
        lastTime = System.currentTimeMillis();

        xValues = new LinkedList<>();
        yValues = new LinkedList<>();
        
        xSum = 0;
        ySum = 0;
    }

    public void addVisionMeasurement(double x, double y)
    {
        xSum += x;
        ySum += y;

        xSum -= xValues.isEmpty() ? 0 : xValues.poll();
        ySum -= yValues.isEmpty() ? 0 : yValues.poll();

        xValues.add(x);
        yValues.add(y);

        lastTime = System.currentTimeMillis();
    }

    public double getAvgX()
    {
        return xSum/AVG_NUM;
    }
    public double getAvgY()
    {
        return ySum/AVG_NUM;
    }

    public boolean shouldDisapear()
    {
        return (System.currentTimeMillis() - lastTime) >= MAX_TARGET_LIFE;
    }
    public int getBlockID()
    {
        return blockID;
    }
}
