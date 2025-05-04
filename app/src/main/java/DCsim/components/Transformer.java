package DCsim.components;

import DCsim.handler.ConstraitHandler;

public class Transformer extends Module {

    public Transformer(double price, double deliveryTime, double electricityUsage, int id, int x, int y, int rotation) {
        super(price, deliveryTime, electricityUsage, id, x, y, rotation);
        
    }

    @Override
    public void updateConstraints() {
        ConstraitHandler.getInstance().updateElectricityGeneration(getElectricityUsage());
    }
    
}
