package com.todal.game.GameObjekts.SpaceShipParts.ShipCrow;

import com.badlogic.gdx.math.MathUtils;

public abstract class Person {

    protected CrowType crowType;
    private String name;
    private int age;
    private float height;
    private String gender;
    private ExperienceLevel firstExperienceLevel;
    protected ExperienceLevel secondExperienceLevel;
    private float pay;
    private boolean isDeth;

    public Person(CrowType crowType, String name, int age, float height, String gender, ExperienceLevel firstExperienceLevel, ExperienceLevel secondExperienceLevel, float pay) {
        this.crowType = crowType;
        this.name = name;
        this.age = age;
        this.height = height;
        this.gender = gender;
        this.firstExperienceLevel = firstExperienceLevel;
        this.secondExperienceLevel = secondExperienceLevel;
        this.pay = pay;
        this.isDeth = false;
    }

    public ExperienceLevel getFirstExperienceLevel() {
        return firstExperienceLevel;
    }

    public ExperienceLevel getSecondExperienceLevel() {
        return secondExperienceLevel;
    }

    public CrowType getCrowType() {
        return crowType;
    }

    public String getName() {
        return name;
    }

    public boolean isDeth() {
        return isDeth;
    }

    public int getAge() {
        return age;
    }

    public float getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public float getPay() {
        return pay;
    }

    public void addAge(){
        age++;
    }

    public void dethOrLive(){
        if (age > 75 ){
            if (MathUtils.random(age, 120)==120)
                isDeth = true;
        }
    }
}
