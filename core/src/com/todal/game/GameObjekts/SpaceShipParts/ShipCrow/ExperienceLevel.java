package com.todal.game.GameObjekts.SpaceShipParts.ShipCrow;

public class ExperienceLevel {

    private ExperienceType experienceType;
    private int level;

    public ExperienceLevel(ExperienceType experienceType, int level) {
        this.experienceType = experienceType;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ExperienceType getExperienceType() {
        return experienceType;
    }

}
