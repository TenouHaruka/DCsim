import components.ComputingUnit;
import components.ControlUnit;
import components.CoolingUnit;
import components.Module;
import components.StorageUnit;
import handler.ModuleHandler;
import handler.ModuleHandler.ModuleType;
import javax.swing.SwingUtilities;

public class Designer {
    private double totalCost;
    private double totalElectricityCons;
    private double totalCoolingPower;
    private double totalComputingPower;
    
    // Getters
    public double getTotalCost() {
        return totalCost;
    }
    
    public double getTotalElectricityCons() {
        return totalElectricityCons;
    }
    
    public double getTotalCoolingPower() {
        return totalCoolingPower;
    }
    
    public double getTotalComputingPower() {
        return totalComputingPower;
    }
    
    // Setters
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public void setTotalElectricityCons(double totalElectricityCons) {
        this.totalElectricityCons = totalElectricityCons;
    }
    
    public void setTotalCoolingPower(double totalCoolingPower) {
        this.totalCoolingPower = totalCoolingPower;
    }
    
    public void setTotalComputingPower(double totalComputingPower) {
        this.totalComputingPower = totalComputingPower;
    }
    
    public static void main(String[] args) {
        // Initialize Designer resource totals.
        Designer designer = new Designer();
        designer.setTotalCost(1000);
        designer.setTotalElectricityCons(500);
        designer.setTotalCoolingPower(300);
        designer.setTotalComputingPower(200);
        
        // Get the ModuleHandler instance and add some modules.
        ModuleHandler handler = ModuleHandler.getInstance();
        
        Module storage = new StorageUnit(150, 5, 60, 1, "StorageCore", 20, 500, 50, 50, 0);
        Module cooling = new CoolingUnit(200, 6, 80, 2, 150, 50, 150, 100, 0);
        Module control = new ControlUnit(120, 4, 40, 3, 250, 120, 0);
        Module computing = new ComputingUnit(180, 7, 70, 4, "CPU-X", 30, 200, 350, 150, 0);
        
        handler.createModule(storage, ModuleType.STORAGE);
        handler.createModule(cooling, ModuleType.COOLING);
        handler.createModule(control, ModuleType.CONTROL);
        handler.createModule(computing, ModuleType.COMPUTING);
        
        // Create the GUI display and supply resource data.
        GuiDisplay gui = new GuiDisplay();
        gui.setGlobalData(() -> new GuiDisplay.ResourceData(
            (int)(storage.getCost() + cooling.getCost() + control.getCost() + computing.getCost()),
            (int)designer.getTotalCost(),
            (int)(storage.getElectricityUsage() + cooling.getElectricityUsage() + control.getElectricityUsage() + computing.getElectricityUsage()),
            (int)designer.getTotalElectricityCons(),
            0, // usedCooling (example)
            (int)designer.getTotalCoolingPower(),
            0, // usedComputing (example)
            (int)designer.getTotalComputingPower()
        ));
        
        SwingUtilities.invokeLater(() -> {
            gui.setup();
            gui.updateView();
        });
    }
}
