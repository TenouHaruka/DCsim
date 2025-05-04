package DCsim.components;

public class ComputingUnit extends Module {
    private final String component;
    private final double coolingRequirement;  
    private final double computingPower;
    

    public ComputingUnit(double cost, double deliveryTime, double electricityUsage, int id, 
                         String component, double coolingRequirement, double computingPower, 
                         int x, int y, int rotation) {
        super(cost, deliveryTime, electricityUsage, id, x, y, rotation);
        this.component = component;
        this.coolingRequirement = coolingRequirement;
        this.computingPower = computingPower;
    }
    
    public String getComponent() {
        return component;
    }
    
    public double getCoolingRequirement() {
        return coolingRequirement;
    }
    
    public double getComputingPower() {
        return computingPower;
    }
}
