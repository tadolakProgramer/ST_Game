package com.todal.game.GameObjekts.SpaceShipParts;


public enum CargoType {

    FUEL(ModuleType.FUEL, "fuel"),
    TITAN(ModuleType.LOSE, "titan"),
    WATER(ModuleType.LIQUID, "water"),
    QUICKSILVER(ModuleType.LIQUID, "silver"),
    GRAFEN(ModuleType.LOSE, "grafen"),
    HELIUM3(ModuleType.GAS, "helium3"),
    PERSON(ModuleType.COKPIT, "person");

    private final ModuleType moduleType;
    private final String styleName;

    CargoType(ModuleType moduleType, String styleName) {
        this.moduleType = moduleType;
        this.styleName = styleName;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public String getStyleName(){
        return styleName;
    }

}
