package components;

public abstract class Module 
{
    private double cost;
    private double deliveryTime;
    private double electricityUsage;
    private int id;
    
    public Module(double cost, double deliveryTime, double electricityUsage, int id) 
    {
        this.cost = cost;
        this.deliveryTime = deliveryTime;
        this.electricityUsage = electricityUsage;
        this.id = id;
    }

    public double getCost() 
    {
        return cost;
    }   
    public double getDeliveryTime() 
    {
        return deliveryTime;
    }
    public double getElectricityUsage() 
    {
        return electricityUsage;
    }   
    public int getId() 
    {
        return id;
    }
}
