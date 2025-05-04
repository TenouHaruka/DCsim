package DCsim.handler;
import DCsim.Blueprint;
import DCsim.components.*;
import DCsim.components.Module;
import DCsim.Blueprint;

import java.util.ArrayList;
import java.util.List;

public class ConstraitHandler {


    private double electricityConsumption;
    private double electricityGeneration;
    private State stateElectricity;

    public void updateElectricityConsumption(double electricityConsumption) {
        this.electricityConsumption += electricityConsumption;
    }

    public void updateElectricityGeneration(double electricityGeneration) {
        this.electricityGeneration += electricityGeneration;
    }

    public State getStateElectricity() {
        if (electricityConsumption == electricityGeneration) {
            stateElectricity = State.ONTHELIMIT;
        } else if ((electricityConsumption - electricityGeneration) > 0) {
            stateElectricity = State.OVERTHELIMIT;
        } else {
            stateElectricity = State.UNDERTHELIMIT;
        }
        return stateElectricity;
    }

    private double coolingConsumption;
    private double coolingProduction;
    private State stateCooling;

    public void updateCoolingConsumption(double coolingConsumption) {
        this.coolingConsumption += coolingConsumption;
    }

    public void updateCoolingProduction(double coolingProduction) {
        this.coolingProduction += coolingProduction;
    }

    public State getStateCooling() {
        if (coolingConsumption == coolingProduction) {
            stateCooling = State.ONTHELIMIT;
        } else if ((coolingConsumption - coolingProduction) > 0) {
            stateCooling = State.OVERTHELIMIT;
        } else {
            stateCooling = State.UNDERTHELIMIT;
        }
        return stateCooling;
    }

    private double waterConsumption;
    private double waterProduction;
    private State stateWater;

    public void updateWaterConsumption(double waterConsumption) {
        this.waterConsumption += waterConsumption;
    }

    public void updateWaterProduction(double waterProduction) {
        this.waterProduction += waterProduction;
    }

    public State getStateWater() {
        if (waterConsumption == waterProduction) {
            stateWater = State.ONTHELIMIT;
        } else if ((waterConsumption - waterProduction) > 0) {
            stateWater = State.OVERTHELIMIT;
        } else {
            stateWater = State.UNDERTHELIMIT;
        }
        return stateWater;
    }

    private double computingPowerProduction;
    private double computingPowerNeeded;
    private State stateComputing;

    public void updateComputingPowerProduction(double computingPowerProduction) {
        this.computingPowerProduction += computingPowerProduction;
    }

    public void updateComputingPowerNeeded(double computingPowerNeeded) {
        this.computingPowerNeeded += computingPowerNeeded;
    }

    public State getStateComputing() {
        if (computingPowerNeeded == computingPowerProduction) {
            stateComputing = State.ONTHELIMIT;
        } else if ((computingPowerNeeded - computingPowerProduction) > 0) {
            stateComputing = State.OVERTHELIMIT;
        } else {
            stateComputing = State.UNDERTHELIMIT;
        }
        return stateComputing;
    }

    private double currDeliveryTime;
    private double maxDeliveryTime;
    private State stateDelivery; // On supposera que le delai sont des jours.

    public void updateCurrDeliveryTime(double currDeliveryTime) {
        this.currDeliveryTime += currDeliveryTime;
    }

    public void updateMaxDeliveryTime(double maxDeliveryTime) {
        this.maxDeliveryTime += maxDeliveryTime;
    }

    public State getStateDelivery() {
        if (currDeliveryTime == maxDeliveryTime) {
            stateDelivery = State.ONTHELIMIT;
        } else if ((currDeliveryTime - maxDeliveryTime) > 0) {
            stateDelivery = State.OVERTHELIMIT;
        } else {
            stateDelivery = State.UNDERTHELIMIT;
        }
        return stateDelivery;
    }

    private double currentPrice;
    private double maxPrice;
    private State statePrice;

    public void updateCurrentPrice(double currentPrice) {
        this.currentPrice += currentPrice;
    }

    public void updateMaxPrice(double maxPrice) {
        this.maxPrice += maxPrice;
    }

    public State getStatePrice() {
        if (currentPrice == maxPrice) {
            statePrice = State.ONTHELIMIT;
        } else if ((currentPrice - maxPrice) > 0) {
            statePrice = State.OVERTHELIMIT;
        } else {
            statePrice = State.UNDERTHELIMIT;
        }
        return statePrice;
    }


    /* Getters */

    enum State {
        UNDERTHELIMIT,
        ONTHELIMIT,
        OVERTHELIMIT;
    }

    private ConstraitHandler() {
        /* Nothing, every is automatically updated to 0.0 */
    }

    /* Instance statique */
    private static ConstraitHandler constraitHandlerInstance;

    /* Retourne une instance, ou initialise si n'existe pas */
    public static ConstraitHandler getInstance() {
        if (constraitHandlerInstance == null) {
            constraitHandlerInstance = new ConstraitHandler();
        }

        return constraitHandlerInstance;
    }

    /* Update the constraints iterating through all the current elements */
    public void updateConstraints(List<Module> listeModules) {
        for (int i=0; i < listeModules.size(); i++) {
            listeModules.get(i).updateConstraints();
        }
    }

    public List<Module> checkOutOfBounds(List<Module> list, Blueprint blueprint) {
        List<Module> listOut = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (blueprint.isInsideBlueprint(list.get(i).getPose())) {
                listOut.add(list.get(i));
            }
        }

        return listOut;
    }


}
