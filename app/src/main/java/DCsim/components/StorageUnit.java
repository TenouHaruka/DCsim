package DCsim.components;

public class StorageUnit extends Module {
    private final String component;
    private final double coolingRequirement; 
    private final double storageCapacity;   
    
    public StorageUnit(double cost, double deliveryTime, double electricityUsage, int id, 
                       String component, double coolingRequirement, double storageCapacity, 
                       int x, int y, int rotation, int width, int height, int moduleID) {
        super(cost, deliveryTime, electricityUsage, id, x, y, rotation, width, height, moduleID);
        this.component = component;
        this.coolingRequirement = coolingRequirement;
        this.storageCapacity = storageCapacity;
    }
    

    public String getComponent() {
        return component;
    }
    
    public double getCoolingRequirement() {
        return coolingRequirement;
    }
    
    public double getStorageCapacity() {
        return storageCapacity;
    }


    @Override
    public void updateConstraints() {
        
    }
}
