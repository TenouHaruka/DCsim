package components;

public class CoolingUnit extends Module
{
    private double coolingPower;
    private double waterUsage;

    public CoolingUnit(double cost, double deliveryTime, double electricityUsage, int id, double coolingPower, double waterUsage, int x, int y, int rotation) 
    {
        super(cost, deliveryTime, electricityUsage, id, x, y, rotation);
        this.coolingPower = coolingPower;
        this.waterUsage = waterUsage;
    }
    public double getCoolingPower() 
    {
        return coolingPower;
    }
    public double getWaterUsage() 
    {
        return waterUsage;
    }
}
