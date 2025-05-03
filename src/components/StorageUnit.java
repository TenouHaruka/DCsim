package components;

public class StorageUnit extends Module 
{
    private String component;
    private double coolingRequirement; 
    private double storageCapacity;   

    public StorageUnit(double cost, double deliveryTime, double electricityUsage, int id, String component, double coolingRequirement, double storageCapacity) 
    {
        super(cost, deliveryTime, electricityUsage, id);
        this.component = component;
        this.coolingRequirement = coolingRequirement;
        this.storageCapacity = storageCapacity;
    }
    public String getComponent() 
    {
        return component;
    }
    public double getCoolingRequirement() 
    {
        return coolingRequirement;
    }  
    public double getStorageCapacity() 
    {
        return storageCapacity;
    }
}
