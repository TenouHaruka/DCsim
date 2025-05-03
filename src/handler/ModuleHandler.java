package handler;

import components.Module;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModuleHandler {

    private final List<Module> listModules;
    private final HashMap<Integer, ModuleType> hashmapBlockID;

    public enum ModuleType {
        COMPUTING,
        CONTROL,
        COOLING,
        STORAGE;
    }

    private static ModuleHandler moduleHandlerInstance;
    
    private ModuleHandler() {
        listModules = new ArrayList<>();
        hashmapBlockID = new HashMap<>();
    }

    public static ModuleHandler getInstance() {
        if (moduleHandlerInstance == null) {
            moduleHandlerInstance = new ModuleHandler();
        } 
        return moduleHandlerInstance;
    }

    // Returns the type of module for the given id.
    public ModuleType getModuleType(int blockID) {
        return hashmapBlockID.get(blockID);
    }
    
    // Moves a module to a new position and rotation.
    public void moveModule(Module module, int newX, int newY, int newRotation) {
        module.getPose().moveCoordinates(newX, newY);
        module.getPose().moveRotation(newRotation);
    }
    
    // Changes the module's id mapping.
    public void changeModule(Module module, int newBlockID) {
        if (hashmapBlockID.containsKey(module.getId())) {
            ModuleType type = hashmapBlockID.remove(module.getId());
            module.setId(newBlockID);
            hashmapBlockID.put(newBlockID, type);
        }
    }
    
    // Deletes the module.
    public void deleteModule(Module module) {
        listModules.remove(module);
        hashmapBlockID.remove(module.getId());
    }
    
    // Registers a new module with its type.
    public void createModule(Module module, ModuleType type) {
        listModules.add(module);
        hashmapBlockID.put(module.getId(), type);
    }
    
    // Returns the list of modules.
    public List<Module> getListModules(){
        return listModules;
    }
    
    public String getModuleTypeString(Module module) {
        ModuleType type = hashmapBlockID.get(module.getId());
        return (type != null) ? type.name() : "x";
    }
}
