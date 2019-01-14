package com.todal.game.GameObjekts.SpaceObjekt;

import com.badlogic.gdx.math.MathUtils;
import com.todal.game.GameObjekts.SpaceShipParts.CargoType;

public class Ware {

    private CargoType cargoType;
    private double price;

    public Ware(CargoType cargoType) {
        this.cargoType = cargoType;
        this.price = randomPrice(cargoType);
    }

    public Ware(CargoType cargoType, double price){
        this.cargoType = cargoType;
        this.price = price;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public void setPrice (double price){
        this.price = price;
    }

    public double changePrice(){
        return this.price * MathUtils.random(-1.049f, 1.051f);
    }

    public double randomPrice(CargoType cargoType) {

        double rmin = 0;

        switch (cargoType) {
            case FUEL: {
                rmin = 5;
                break;
            }
            case TITAN:{
                rmin = 5;
                break;
            }
            case GRAFEN:{
                rmin = 15;
                break;
            }
            case WATER:{
                rmin = 50;
                break;
            }
            case HELIUM3:{
                rmin = 500;
                break;
            }
            case PERSON:{
                rmin = 5000;
                break;
            }
            case QUICKSILVER:{
                rmin = 1000;
                break;
            }
        }
        return this.price = MathUtils.random((float) rmin, (float) rmin * 1.2f);
    }

    public double getPrice() {
        return price;
    }
}
