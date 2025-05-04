package app.src.main.java.DCsim.handler;

import app.src.main.java.DCsim.components.Module;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModuleHandler {

    private final List<Module> listModules;
    private final HashMap<Integer, ModuleType> hashmapBlockID;
    private final HashMap<Integer, ModuleVariant> hashmapVariant; // To store the variant of each module

    public enum ModuleType {
        COMPUTING,
        TRANSFORMER,
        COOLING,
        STORAGE;
    }

    public enum ModuleVariant {
        one,
        two,
        three,
    }

    private static ModuleHandler moduleHandlerInstance;
    
    private ModuleHandler() {
        listModules = new ArrayList<>();
        hashmapBlockID = new HashMap<>();
        hashmapVariant = new HashMap<>();
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
    
    // Returns the variant of the module for the given id.
    public ModuleVariant getModuleVariant(int blockID) {
        return hashmapVariant.get(blockID);
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
            ModuleVariant variant = hashmapVariant.remove(module.getId());
            module.setId(newBlockID);
            hashmapBlockID.put(newBlockID, type);
            hashmapVariant.put(newBlockID, variant);
        }
    }
    
    // Deletes the module.
    public void deleteModule(Module module) {
        listModules.remove(module);
        hashmapBlockID.remove(module.getId());
        hashmapVariant.remove(module.getId());
    }
    
    // Overloaded: Registers a new module with its type and variant.
    public void createModule(Module module, ModuleType type, ModuleVariant variant) {
        listModules.add(module);
        hashmapBlockID.put(module.getId(), type);
        hashmapVariant.put(module.getId(), variant);
    }
    
    // Overloaded: If no variant is provided, default to ModuleVariant.one.
    public void createModule(Module module, ModuleType type) {
        createModule(module, type, ModuleVariant.one);
    }
    
    // Returns the list of modules.
    public List<Module> getListModules(){
        return listModules;
    }
    
    public String getModuleTypeString(Module module) {
        ModuleType type = hashmapBlockID.get(module.getId());
        return (type != null) ? type.name() : "x";
    }

    public String getModuleVariantString(Module module) {
        ModuleVariant variant = hashmapVariant.get(module.getId());
        return (variant != null) ? variant.name() : "x";
    }


    public void optimizeBlueprint() {

    }
}
