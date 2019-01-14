package com.todal.game.GameObjekts.SpaceShipParts;

import com.badlogic.gdx.math.MathUtils;

public class HousingModule extends ShipModule implements Modules{

    private double currentDistance;
    private static final int MAX_DISTANCE_NO_ERROR = 1000;

    public HousingModule(ModuleType moduleType, String name, double capacity, double cost, int index, int baseFailureRate) {
        super(moduleType, name, capacity, cost, index);
        this.capacity = capacity;
        this.cost = cost;
        this.baseFailureRate = baseFailureRate;
        this.failureRate = baseFailureRate;

    }

    @Override
    public void update(float dt){
        timeToFailure = + dt;
        setFailure();
    }

    private void setFailure() {
        distanceControl();
    }

    @Override
    public void distanceControl() {
        if ((failureRate > 0) && (!isModuleError())){
            if (currentDistance > MAX_DISTANCE_NO_ERROR / failureRate) {
                currentDistance = 0;
                if (MathUtils.random(1, 20) < failureRate) {
                    randomFailure();
                } else {
                    moduleError = false;
                }
            }
        }

    }

    @Override
    public void randomFailure() {
        int error = MathUtils.random(0, 100) + experienceLevel;
        System.out.println("Losowanie_HM_Module: " + error + " "+this.moduleType+ " "+ index);

        if (error < 8) {
            for (int i = 0; i < CargoType.values().length; i++) {
                if (capacitys.containsKey(CargoType.values()[i])) {
                    subCargo((CargoType.values()[i]), (double) Math.round(capacitys.get(CargoType.values()[i]) * error * 0.01f));
                    textEror = "You lost " + (error) + "%" + CargoType.values()[i];
                    setError();
                    break;
                }
            }
        }

    }

    @Override
    public void addDistance(double distance){
        currentDistance = currentDistance + distance;
    }
}
