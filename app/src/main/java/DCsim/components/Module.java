package app.src.main.java.DCsim.components;

public abstract class Module {


    private final double price;
    private final double deliveryTime;
    private final double electricityUsage;
    private int id;
    private Pose pose;
    
    public Module(double price, double deliveryTime, double electricityUsage, int id, int x, int y, int rotation, int width, int height) {
        this.price = price;
        this.deliveryTime = deliveryTime;
        this.electricityUsage = electricityUsage;
        this.id = id;
        this.pose = new Pose(x, y, rotation, width, height);
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
    
    public int getId() {
        return id;
    }

    public Pose getPose() {
        return pose;
    }

    public abstract void updateConstraints();
    
    // Setter to update the module's id (used in ModuleHandler.changeModule).
    public void setId(int newId) {
        this.id = newId;
    }
}
