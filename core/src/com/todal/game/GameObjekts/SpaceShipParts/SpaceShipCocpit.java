package com.todal.game.GameObjekts.SpaceShipParts;

public class SpaceShipCocpit extends ShipModule implements Modules{

    public SpaceShipCocpit(ModuleType moduleType, String name, double capacity, double cost, int index, int baseFailureRate){
        super(moduleType, name, capacity, cost, index);

        this.fill = fill;
        this.baseFailureRate = baseFailureRate;


    }

    @Override
    public void distanceControl() {

    }

    @Override
    public void randomFailure() {

    }
}
