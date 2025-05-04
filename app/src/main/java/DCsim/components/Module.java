package DCsim.components;

public abstract class Module {


    private final double price;
    private final double deliveryTime;
    private final double electricityUsage;
    private int blockID;
    private Pose pose;
    private int moduleID;
    
    public Module(double price, double deliveryTime, double electricityUsage, int blockID, 
    int x, int y, int rotation, int width, int height, int moduleID) {
        this.price = price;
        this.deliveryTime = deliveryTime;
        this.electricityUsage = electricityUsage;
        this.blockID = blockID;
        this.pose = new Pose(x, y, rotation, width, height);
        this.moduleID = moduleID;
    }


    /* Builders */
    public double getPrice() {
        return price;
    }
    
    public double getDeliveryTime() {
        return deliveryTime;
    }
    
    public double getElectricityUsage() {
        return electricityUsage;
    }
    
    public int getBlockID() {
        return blockID;
    }
    public int getModuleID()
    {
        return this.moduleID;
    }

    public Pose getPose() {
        return pose;
    }

    public abstract void updateConstraints();
    
    // Setter to update the module's id (used in ModuleHandler.changeModule).
    public void setBlockID(int newId) {
        this.blockID = newId;
    }
}
