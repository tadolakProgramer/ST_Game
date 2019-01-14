package com.todal.game.GameObjekts.SpaceShipParts;

import java.util.HashMap;
import java.util.Map;

public abstract class ShipModule {

    public ModuleType moduleType;

    public String name;
    public double capacity;
    public double fill;
    public double cost;
    public int baseFailureRate;
    public int failureRate; //lower i beater
    public int experienceLevel;
    protected float timeToFailure;
    protected boolean moduleError;
    protected boolean errorIsRead;
    protected String textEror;
    protected int index;

    public Map<CargoType, Double> capacitys = new HashMap<CargoType, Double>();

    public ShipModule(ModuleType moduleType, String name, double capacity, double cost, int index){
        this.moduleType = moduleType;
        this.name = name;
        this.index = index;
    }

    public double addCargo(CargoType cargoType, double quantity){

        if (moduleType.equals(cargoType.getModuleType())) {

            if (fill + quantity <= capacity) {
                fill = fill + quantity;

                //ModifiedXML.writeNewFillToXml(index,quantity);

                if (capacitys.size() == 0) {
                    capacitys.put(cargoType, quantity);
                    quantity = 0;
                } else {
                    for (int i = 0; i < capacitys.size(); quantity++) {
                        if (capacitys.containsKey(cargoType)) {
                            capacitys.put(cargoType, quantity + capacitys.get(cargoType));
                            quantity =0;
                            break;
                        } else {
                            capacitys.put(cargoType, quantity);
                            quantity = 0;
                            break;
                        }
                    }
                }
            } else {
                quantity = quantity - (capacity - fill);
                if (capacitys.size() == 0) {
                    capacitys.put(cargoType, capacity);
                    fill = capacity;
                } else {
                    for (int z = 0; z < capacitys.size(); z++) {
                        if (capacitys.containsKey(cargoType)) {
                            capacitys.put(cargoType, capacity);
                            fill = capacity;
                            break;
                        }
                    }
                }
            }
        }
        return quantity;
    }

    public double subCargo(CargoType cargoType, double quantity){

        double q = quantity;

        if ((capacitys.containsKey(cargoType))) {
            if (capacitys.get(cargoType) < quantity){
                q = quantity - fill;
                capacitys.remove(cargoType);
                fill = 0;
            }
            else{
                fill = fill - quantity;
                capacitys.put(cargoType, capacitys.get(cargoType)-quantity);
                q = 0;
            }
        }
        return q;
    }

    public void setFailureRate(int experienceLevel) {

        this.failureRate = baseFailureRate - experienceLevel;
        if (failureRate < 1) {
            failureRate =1;
        }

    }

    public void update(float dt){

    }

    public void addDistance(double distance){

    }

    public void resetFailure(){
        moduleError = false;
        textEror = " ";
    }

    protected void setError() {
        errorIsRead = false;
        moduleError = true;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public void setExperienceLevel(int level){
        experienceLevel = level;
        setFailureRate(level);
    }

    public double getCapacity() {
        return capacity;
    }

    public double getFill() {
        return fill;
    }

    public double getFillCargoType(CargoType cargoType){
        return capacitys.get(cargoType);
    }

    public Map<CargoType, Double> getCapacitys() {
        return capacitys;
    }

    public boolean isModuleError() {
        return moduleError;
    }

    public boolean isErrorIsRead() {
        return errorIsRead;
    }

    public String getTextEror() {
        errorIsRead = true;
        return textEror;
    }

    protected void setModuleError(){
        moduleError = true;
    }
}
