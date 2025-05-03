package handler; 

import components.Module;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap; 


public class ModuleHandler {


    private static ModuleHandler modulehandler; 
    private List<Module> listModules;
    private HashMap<Integer, ModuleType> hashmapBlockID;

    enum ModuleType {
        COMPUTING,
        CONTROL,
        COOLING,
        STORAGE;
    }

    /* Builder */
    private ModuleHandler() {
        listModules = new ArrayList<>();
        hashmapBlockID = new HashMap<>();
    }

    /* Creates a ModuleHandler if it doesn't exist, returns the ModuleHandler */
    public static ModuleHandler getInstance() {
        if (modulehandler == null) {
            modulehandler = new ModuleHandler();
        } 
        
        return modulehandler;
    }

    /* Returns the type of module (enum) associated with the ID */
    public ModuleType getModuleType(int blockID) {
        if (hashmapBlockID.containsKey((blockID))) {
            return hashmapBlockID.get((blockID));
        } else {
            return null;
        }
    }

    /* Moves the physical position of the module if possible */
    /* TODO  */
    public void moveModule(Module block) {
        return;
    }

    /* Updates the block to reflect the new blockID */
    /* TODO */
    public void changeModule(Module block, int newBlockID) {
        return;
    }

    /* Deletes the module from the list */
    public void deleteModule(Module block) {
        return;
    }

    /* Creates a new module, add it to the list and inserting into the list  */
    public void createModule(Module block) {

    }





    /* Getter for listModules */
    public List<Module> getListModules(){
        return this.listModules;
    }




}
