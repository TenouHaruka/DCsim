package components;

public abstract class Module {
    private final double cost;
    private final double deliveryTime;
    private final double electricityUsage;
    private int id;

    private Pose pose;
    
    public Module(double cost, double deliveryTime, double electricityUsage, int id, int x, int y, int rotation) {
        this.cost = cost;
        this.deliveryTime = deliveryTime;
        this.electricityUsage = electricityUsage;
        this.id = id;
        this.pose = new Pose(x, y, rotation);
    }
    
    public double getCost() {
        return cost;
    }
    
    public double getDeliveryTime() {
        return deliveryTime;
    }
    
    public double getElectricityUsage() {
        return electricityUsage;
    }
    
    public int getId() {
        return id;
    }

    
    public Pose getPose() {
        return pose;
    }
    
    // Setter to update the module's id (used in ModuleHandler.changeModule).
    public void setId(int newId) {
        this.id = newId;
    }
}
