package DCsim.handler;

import DCsim.components.Module;
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
    public ModuleVariant getModuleVariant(int moduleID) {
        return hashmapVariant.get(moduleID);
    }
    
    // Moves a module to a new position and rotation.
    public void moveModule(Module module, int newX, int newY, int newRotation) {
        module.getPose().moveCoordinates(newX, newY);
        module.getPose().moveRotation(newRotation);
    }

    public void moveModule(int blockID, int newX, int newY, int newRotation)
    {
        Module mod = getModule(blockID);
        if(mod == null) return;
        moveModule(mod, newX, newY, newRotation);
    }
    
    // Changes the module's id mapping.
    public void replaceModule(int blockID, Module newModule) 
    {
        Module mod = getModule(blockID);
        if(mod != null)
        {
            listModules.remove(mod);
            newModule.setBlockID(blockID);
            listModules.add(mod);
        }
    }
    
    // Deletes the module.
    public void deleteModule(Module module) {
        listModules.remove(module);
    }
    
    // Overloaded: Registers a new module with its type and variant.
    public void createModule(Module module, ModuleType type, ModuleVariant variant) {
        listModules.add(module);
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
        ModuleType type = hashmapBlockID.get(module.getBlockID());
        return (type != null) ? type.name() : "x";
    }

    public String getModuleVariantString(Module module) {
        ModuleVariant variant = hashmapVariant.get(module.getBlockID());
        return (variant != null) ? variant.name() : "x";
    }

    public void addModule(Module block) {
        getListModules().add(block);
    }

    public Module getModule(int id)
    {
        for(var mod : listModules)
        {
            if(mod.getBlockID() == id)
                return mod;
        }
        return null;
    }

    /* Overload : Only demands */
    public void deleteModule(int id) 
    {
        Module mod = getModule(id);
        if(mod != null)
            listModules.remove(mod);
    }


    public void optimizeBlueprint() {

    }
}
