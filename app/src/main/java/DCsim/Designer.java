package app.src.main.java.DCsim;

import app.src.main.java.DCsim.components.ComputingUnit;
import app.src.main.java.DCsim.components.Transformer;
import app.src.main.java.DCsim.components.CoolingUnit;
import app.src.main.java.DCsim.components.Module;
import app.src.main.java.DCsim.components.StorageUnit;
import app.src.main.java.DCsim.handler.ModuleHandler;
import app.src.main.java.DCsim.handler.ModuleHandler.ModuleType;
import app.src.main.java.DCsim.handler.ModuleHandler.ModuleVariant;
import app.src.main.java.DCsim.ui.GuiDisplay;

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
        Module transform = new Transformer(120, 4, 40, 3, 250, 120, 0);
        Module computing = new ComputingUnit(180, 7, 70, 4, "CPU-X", 30, 200, 350, 150, 0);
        
        // Use overloaded createModule: storage explicitly provides a variant,
        // while others use the default (ModuleVariant.one).
        handler.createModule(storage, ModuleType.STORAGE, ModuleVariant.one);
        handler.createModule(cooling, ModuleType.COOLING, ModuleVariant.two);
        handler.createModule(transform, ModuleType.TRANSFORMER, ModuleVariant.one);
        handler.createModule(computing, ModuleType.COMPUTING, ModuleVariant.three);
        
        // Create the GUI display and supply resource data.
        GuiDisplay gui = new GuiDisplay();
        gui.setGlobalData(() -> new GuiDisplay.ResourceData(
            (int)(storage.getCost() + cooling.getCost() + transform.getCost() + computing.getCost()),
            (int)designer.getTotalCost(),
            (int)(storage.getElectricityUsage() + cooling.getElectricityUsage() + transform.getElectricityUsage() + computing.getElectricityUsage()),
            (int)designer.getTotalElectricityCons(),
            0,
            (int)designer.getTotalCoolingPower(),
            0,
            (int)designer.getTotalComputingPower()
        ));
        
        SwingUtilities.invokeLater(() -> {
            gui.setup();
            gui.updateView();
        });
    }
}
