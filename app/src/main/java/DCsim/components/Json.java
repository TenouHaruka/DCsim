package app.src.main.java.DCsim.components;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Json {
    public static String convertToJsonString(Module module) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{\n")
                   .append("  \"type\": \"").append(module.getClass().getSimpleName()).append("\",\n")
                   .append("  \"id\": ").append(module.getId()).append(",\n")
                   .append("  \"cost\": ").append(module.getCost()).append(",\n")
                   .append("  \"deliveryTime\": ").append(module.getDeliveryTime()).append(",\n")
                   .append("  \"electricityUsage\": ").append(module.getElectricityUsage()).append(",\n")
                   .append("  \"x_value\": ").append(module.getPose().getX()).append(",\n")
                   .append("  \"y_value\": ").append(module.getPose().getY());
        
        if (module instanceof ComputingUnit) {
            ComputingUnit cu = (ComputingUnit) module;
            jsonBuilder.append(",\n").append("  \"component\": \"").append(cu.getComponent()).append("\",\n")
            .append("  \"coolingRequirement\": ").append(cu.getCoolingRequirement()).append(",\n")
            .append("  \"computingPower\": ").append(cu.getComputingPower());
        } else if (module instanceof CoolingUnit) {
            CoolingUnit cu = (CoolingUnit) module;
            jsonBuilder.append(",\n").append("  \"coolingPower\": ").append(cu.getCoolingPower()).append(",\n")
            .append("  \"waterUsage\": ").append(cu.getWaterUsage());
        } else if (module instanceof StorageUnit) {
            StorageUnit su = (StorageUnit) module;
            jsonBuilder.append(",\n").append("  \"component\": \"").append(su.getComponent()).append("\",\n")
            .append("  \"coolingRequirement\": ").append(su.getCoolingRequirement()).append(",\n")
            .append("  \"storageCapacity\": ").append(su.getStorageCapacity());
        }

        jsonBuilder.append("\n}");
        
        return jsonBuilder.toString();
        
    }

    public static void saveModuleToFile(Module module, String filename) {
        String moduleJson = convertToJsonString(module);
        File file = new File(filename);
        String newContent;
        
        try {
            // If the file doesn't exist or is empty, we create a new JSON array.
            if (!file.exists() || file.length() == 0) {
                newContent = "[\n" + moduleJson + "\n]";
            } else {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8).trim();

                // If content does not end with the closing bracket, warn and reset.
                if (!content.endsWith("]")) {
                    System.err.println("File content does not end with a closing bracket. Resetting file content.");
                    newContent = "[\n" + moduleJson + "\n]";
                } else {
                    // Find the last closing bracket.
                    int lastBracketIndex = content.lastIndexOf("]");
                    if (lastBracketIndex < 0) {
                        // Should never happen, but reset if it does.
                        newContent = "[\n" + moduleJson + "\n]";
                    } else {
                        // Extract everything before the closing bracket.
                        String prefix = content.substring(0, lastBracketIndex).trim();
                        // Check if there are already elements in the array.
                        if (prefix.endsWith("[")) {
                            // Array is empty.
                            newContent = prefix + "\n" + moduleJson + "\n]";
                        } else {
                            // Array already has one or more elements.
                            newContent = prefix + ",\n" + moduleJson + "\n]";
                        }
                    }
                }
            }
            
            // Write the updated content back to the file.
            Files.write(file.toPath(), newContent.getBytes(StandardCharsets.UTF_8));
            System.out.println("Updated JSON file: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
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

        saveModuleToFile(tf1, "Transformer.json");
        saveModuleToFile(tf2, "Transformer.json");
        saveModuleToFile(tf3, "Transformer.json");

        saveModuleToFile(cu1, "ComputingUnit.json");
        saveModuleToFile(cu2, "ComputingUnit.json");
        saveModuleToFile(cu3, "ComputingUnit.json");
        
        saveModuleToFile(co1, "CoolingUnit.json");
        saveModuleToFile(co2, "CoolingUnit.json");
        saveModuleToFile(co3, "CoolingUnit.json");

        saveModuleToFile(su1, "StorageUnit.json");
        saveModuleToFile(su2, "StorageUnit.json");
        saveModuleToFile(su3, "StorageUnit.json");
    }
}
