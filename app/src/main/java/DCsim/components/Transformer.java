package DCsim.components;

import DCsim.handler.ConstraitHandler;

public class Transformer extends Module {

    public Transformer(double price, double deliveryTime, double electricityUsage, int id, 
    int x, int y, int rotation, int width, int height, int moduleID) {
        super(price, deliveryTime, electricityUsage, id, x, y, rotation, width, height, moduleID);
    }

    @Override
    public void updateConstraints() {
        ConstraitHandler.getInstance().updateElectricityGeneration(getElectricityUsage());
    }
    
}
