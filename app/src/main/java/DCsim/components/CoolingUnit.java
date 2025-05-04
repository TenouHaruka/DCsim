package DCsim.components;

import DCsim.handler.ConstraitHandler;

public class CoolingUnit extends Module {
    private final double coolingProduction;
    private final double waterUsage;
    
    public CoolingUnit(double cost, double deliveryTime, double electricityUsage, int id, 
                       double coolingProduction, double waterUsage, int x, int y, int rotation) {
        super(cost, deliveryTime, electricityUsage, id, x, y, rotation);
        this.coolingProduction = coolingProduction;
        this.waterUsage = waterUsage;
    }
    
    public double getCoolingProduction() {
        return coolingProduction;
    }
    
    public double getWaterUsage() {
        return waterUsage;
    }

    @Override
    public void updateConstraints() {
        ConstraitHandler.getInstance().updateElectricityConsumption(getElectricityUsage());
        ConstraitHandler.getInstance().updateCoolingProduction(coolingProduction);
        ConstraitHandler.getInstance().updateWaterConsumption(waterUsage);
        
    }
}
