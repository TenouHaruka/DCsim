package app.src.test.java;

import app.src.main.java.DCsim.components.*;
import app.src.main.java.DCsim.handler.*;

public class testInput {

    public static void main(String[] args){
        Transformer tf1 = new Transformer(1000, 3, 100, 1, 40, 45, 0);
        Transformer tf2 = new Transformer(50000, 0, 1000, 2, 100, 100, 0);
        Transformer tf3 = new Transformer(250000, 15, 5000, 3, 150, 200, 0);
        
        ComputingUnit cu1 = new ComputingUnit(2000, 0, 50, 4, "network rack" , 5.0, 50, 40, 40, 0);
        ComputingUnit cu2 = new ComputingUnit(12000, 5, 125, 5, "server rack", 25, 150, 40, 40, 0);
        ComputingUnit cu3 = new ComputingUnit(50000, 2, 240, 6, "cpu", 50, 1000, 40, 40, 0);        
        
        CoolingUnit co1 = new CoolingUnit(40000, 9, 500, 7, 100, 150, 100, 100, 0);
        CoolingUnit co2 = new CoolingUnit(200, 26, 950, 8, 70, 80, 50, 50, 0);       
        CoolingUnit co3 = new CoolingUnit(70000, 0, 150, 9, 500, 500, 400, 400, 0);        
        
        StorageUnit su1 = new StorageUnit(2000, 20, 15, 10, "data rack", 3, 100, 40, 40, 0);
        StorageUnit su2 = new StorageUnit(7500, 10, 25, 11, "data rack", 3, 250, 40, 40, 0);
        StorageUnit su3 = new StorageUnit(20500, 0, 40, 12, "data rack", 6, 500, 40, 40, 0);
    
        ModuleHandler.createModule(tf1, (ModuleType)"TRANSFORMER", (ModuleVariant)"one");

    }
}
