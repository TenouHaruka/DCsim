package DCsim.components;

import DCsim.handler.ConstraitHandler;

public class ComputingUnit extends Module {
    private final String component;
    private final double coolingRequirement;  
    private final double computingPower;
    

    public ComputingUnit(double cost, double deliveryTime, double electricityUsage, int id, 
                         String component, double coolingRequirement, double computingPower, 
                         int x, int y, int rotation, int width, int height, int moduleID) {
        super(cost, deliveryTime, electricityUsage, id, x, y, rotation, width, height, moduleID);
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

    @Override
    public void updateConstraints() {
        ConstraitHandler.getInstance().updateElectricityConsumption(getElectricityUsage());
        ConstraitHandler.getInstance().updateCoolingConsumption(coolingRequirement);
        ConstraitHandler.getInstance().updateComputingPowerProduction(computingPower);
    }
}
