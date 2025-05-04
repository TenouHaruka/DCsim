package DCsim.components;

import DCsim.handler.ConstraitHandler;

public class CoolingUnit extends Module {
    private final double coolingProduction;
    private final double waterUsage;
    
    public CoolingUnit(double price, double deliveryTime, double electricityUsage, int id, 
                       double coolingProduction, double waterUsage, int x, int y, int rotation, 
                       int width, int height, int moduleID) {
        super(price, deliveryTime, electricityUsage, id, x, y, rotation, width, height, moduleID);
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
