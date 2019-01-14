package com.todal.game.GameObjekts.SpaceShipParts;


import com.badlogic.gdx.math.MathUtils;

public class SpaceShipEngine extends ShipModule implements Modules{

    private static final int MAX_DISTANCE_NO_ERROR = 10000;

    private float speedEngine;
    private float consumptionFuel;
    private float speedEngineSlow;
    private float speedActual;
    private double eventDistance;
    private double totalDistance;
    private double currentDistance;

    private int eventType;


    public SpaceShipEngine(ModuleType moduleType, String name, double capacity, double cost, int index, int baseFailureRate) {
        super(moduleType, name, capacity, cost, index);
        this. baseFailureRate = baseFailureRate;
        this.failureRate = baseFailureRate;
        speedEngine = 40;
        speedEngineSlow = 2;
        consumptionFuel = 20.50f;
    }

    public void update(float dt) {
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
        int error = MathUtils.random(0 ,100) + experienceLevel;
        System.out.println("LosowanieEngine: "+ error);
        switch (error){
            case 0: {
                setSpeedActual(getSpeedActual()*0.1f);
                textEror = "Engine speed has decreased by 90%";
                setError();
                break;
            }
            case 1: {
                setSpeedActual(getSpeedActual()*0.2f);
                textEror = "Engine speed has decreased by 80%";
                setError();
                break;
            }
            case 2: {
                setSpeedActual(getSpeedActual()*0.3f);
                textEror = "Engine speed has decreased by 70%";
                setError();
                break;
            }
            case 3: {
                setSpeedActual(getSpeedActual()*0.4f);
                textEror = "Engine speed has decreased by 60%";
                setError();
                break;
            }
            case 4: {
                setSpeedActual(getSpeedActual()*0.5f);
                textEror = "Engine speed has decreased by 50%";
                setError();
                break;
            }
            case 5: {
                setSpeedActual(getSpeedActual()*0.6f);
                textEror = "Engine speed has decreased by 40%";
                setError();
                break;
            }
            case 6: {
                setSpeedActual(getSpeedActual()*0.7f);
                textEror = "Engine speed has decreased by 30%";
                setError();
                break;
            }
            case 7: {
                setSpeedActual(getSpeedActual()*0.8f);
                textEror = "Engine speed has decreased by 20%";
                setError();
                break;
            }
        }
    }


    @Override
    public void resetFailure(){
        setEngineError(false);
        textEror = " ";
        setSpeedActual(getSpeedEngine());
    }

    public void addDistance(double distance){
        this.totalDistance = this.totalDistance + distance;
        currentDistance = currentDistance + distance;
    }

    public float getSpeedEngine() {
        return speedEngine;
    }

    public void setSpeedEngine(float speedEngine) {
        this.speedEngine = speedEngine;
    }

    public float getConsumptionFuel() {
        return consumptionFuel;
    }

    public void setConsumptionFuel(float consumptionFuel) {
        this.consumptionFuel = consumptionFuel;
    }

    public float getSpeedEngineSlow() {
        return speedEngineSlow;
    }

    public void setSpeedEngineSlow(float speedEngineSlow) {
        this.speedEngineSlow = speedEngineSlow;
    }

    public float getSpeedActual() {
        return speedActual;
    }

    public void setSpeedActual(float speedActual) {
        this.speedActual = speedActual;
    }

    public void setEngineError(boolean engineError) {
        this.moduleError = engineError;
    }
}
