package components;

public class ComputingUnit extends Module
{
    private String component;
    private double coolingRequirement;  
    private double powerRequirement;
    
    public ComputingUnit(double cost, double deliveryTime, double electricityUsage, int id, String component, double coolingRequirement, double powerRequirement) 
    {
        super(cost, deliveryTime, electricityUsage, id);
        this.component = component;
        this.coolingRequirement = coolingRequirement;
        this.powerRequirement = powerRequirement;
    }

    public String getComponent() 
    {
        return component;
    }
    public double getCoolingRequirement() 
    {
        return coolingRequirement;
    }
    public double getPowerRequirement() 
    {
        return powerRequirement;
    }
}
