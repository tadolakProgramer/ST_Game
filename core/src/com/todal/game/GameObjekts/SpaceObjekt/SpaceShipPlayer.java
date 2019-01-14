package com.todal.game.GameObjekts.SpaceObjekt;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.todal.game.GameObjekts.SpaceShipParts.CargoType;
import com.todal.game.GameObjekts.SpaceShipParts.Container;
import com.todal.game.GameObjekts.SpaceShipParts.HousingModule;
import com.todal.game.GameObjekts.SpaceShipParts.ModuleType;
import com.todal.game.GameObjekts.SpaceShipParts.ShipCrow.ExperienceLevel;
import com.todal.game.GameObjekts.SpaceShipParts.ShipCrow.ExperienceType;
import com.todal.game.GameObjekts.SpaceShipParts.ShipCrow.Person;
import com.todal.game.GameObjekts.SpaceShipParts.ShipModule;
import com.todal.game.GameObjekts.SpaceShipParts.SpaceShipCocpit;
import com.todal.game.GameObjekts.SpaceShipParts.SpaceShipEngine;
import com.todal.game.Helper.MackeXML;
import com.todal.game.Helper.ModifiedXML;
import com.todal.game.Helper.ReadXML;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.GameScreen;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.badlogic.gdx.math.MathUtils;

import static com.todal.game.GameObjekts.SpaceShipParts.ModuleType.EMPTY;
import static com.todal.game.MyGdxGame.GAME_SCALE;



public class SpaceShipPlayer extends SpaceObject {


    public List<ShipModule> shipModules = new ArrayList<ShipModule>();
    public List<Person> persons = new ArrayList<Person>();
    public Map<ExperienceType, Integer> elMap = new HashMap<ExperienceType, Integer>();

    public SpaceShipEngine spaceShipEngine;
    private boolean isRun;

    private int planetNumber;

    private Vector2 moveVector;
    private float targetX;
    private float targetY;
    public String targetName;

    private double money;
    private boolean noMoney;
    private GameScreen gameScreen;
    private int planetTripCounter;  //Do zdobywania nagród


    //test
    private float navigationFactor;
    private boolean korekt;


    public SpaceShipPlayer(final MyGdxGame game, final GameScreen gameScreen) {
        super(game);
        this.gameScreen = gameScreen;
        setScale(GAME_SCALE);
        this.path = "aliensprite2";
        setTexture(this.path);
        moveVector = new Vector2();
        initialize();


        this.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if (isRun) {
                    gameScreen.createSpaceShipScreen();
                } else {
                    //gameScreen.createSpaceShipScreen();
                    if (targetName == null){
                        gameScreen.createSpaceShipScreen();
                    }
                    else {
                        gameScreen.createMarketWindow();
                    }
                }

                System.out.println("KLIK");
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    private void initialize() {

        for (int i = 0; i < 14; i++) {
            shipModules.add(i, new Container(EMPTY, "Empty", 0, 0, i,0));
        }
        spaceShipEngine = new SpaceShipEngine(ModuleType.SPACE_SHIP_ENGINE, "Golem", 1, 1, 14, 10);

        ReadXML.setShipFromXML(this);

        ReadXML.readCaptain(this);

        setSpaceObjectName("Tado-044");

        setMoney();
        setPlanetTripCounter();
    }

    public void addModule(int index, ModuleType moduleType, String name, double capacity, /*double fill,*/ double cost, int baseFailureRate) {

        if (shipModules.get(index).moduleType == EMPTY) {

            switch (moduleType) {
                case COKPIT: {
                    shipModules.set(index, new SpaceShipCocpit(ModuleType.COKPIT, name, capacity, cost, index, baseFailureRate));
                    break;
                }
                case SPACE_SHIP_ENGINE: {
                    shipModules.set(index, new SpaceShipEngine(ModuleType.SPACE_SHIP_ENGINE, name, capacity, cost, index, baseFailureRate));
                    break;
                }
                case HOUSING_MODULE: {
                    shipModules.set(index, new HousingModule(ModuleType.HOUSING_MODULE, name, capacity, cost, index, baseFailureRate));
                    break;
                }
                default:
                {
                    shipModules.set(index, new Container(moduleType, name, capacity,  cost, index, baseFailureRate));
                    break;
                }
            }
        }
    }

    public void bayModule(int index, ModuleType moduleType, String name, double capacity, double cost, int baseFailureRate){

        if (checkMoney(1, cost)){
            addModule(index, moduleType, name, capacity, cost, baseFailureRate);
            subMoney(cost);
            setBaseFailureRate();
            modifyFailureRate();
        }
    }

    public void addPerson() {

    }

    public void setStart(int planetNumber) {

        if (persons.size() > 0 ) {

            this.planetNumber = planetNumber;

            korekt = false;

            targetX = gameScreen.planets.get(planetNumber).getPositionCX();
            targetY = gameScreen.planets.get(planetNumber).getPositionCY();

            navigationFactor = 100 - elMap.get(ExperienceType.NAVIGATE) * 10;

            float targetX_temp = MathUtils.random(-navigationFactor, navigationFactor) + targetX;
            float targetY_temp = MathUtils.random(-navigationFactor, navigationFactor) + targetY;

            targetX = targetX_temp;
            targetY = targetY_temp;

            MackeXML.newTarget(targetX, targetY);

           // ModifiedXML.writeTargetPositionToXml(targetX, targetY);

            targetName = gameScreen.planets.get(planetNumber).getSpaceObjectName();

            double angle = Math.atan2(targetY - positionC.y, targetX - positionC.x);
            double rotation = Math.toDegrees(angle) + 90;

            spaceShipEngine.setSpeedActual(spaceShipEngine.getSpeedEngine());
            moveVector.set((float) Math.cos(angle), (float) Math.sin(angle));

            isRun = true;

            Action startAction = Actions.parallel(
                    Actions.rotateTo((float) rotation, 1.2f),
                    Actions.scaleTo(0.5f, 0.5f, 1.2f)
                    //Actions.(moveVector.x ,moveVector.y ,20f)
            );

            this.addAction(startAction);
        }

    }

    private void setNewTarget(){

        if (!korekt) {

            targetX = gameScreen.planets.get(planetNumber).getPositionCX();
            targetY = gameScreen.planets.get(planetNumber).getPositionCY();
            double angle = Math.atan2(targetY - positionC.y, targetX - positionC.x);
            double rotation = Math.toDegrees(angle) + 90;
            moveVector.set((float) Math.cos(angle), (float) Math.sin(angle));
            korekt = true;
            Action startAction = Actions.parallel(
                    Actions.rotateTo((float) rotation, 1.2f),
                    //Actions.scaleTo(0.5f, 0.5f, 1.2f)
                    Actions.moveBy(moveVector.x ,moveVector.y ,20f)
            );

            this.addAction(startAction);
        }
    }

    public void update(float dt) {
        setPositionC();
        setLabelPosition();
        setActualSize();
        setNewPosition(dt);
        updateShipModule(dt);
        shipModules.set(13, spaceShipEngine);
    }


    private void updateShipModule(float dt) {

        if (isRun){
        for (int i = 0; i < shipModules.size(); i++) {
            shipModules.get(i).update(dt);
        }
        spaceShipEngine.update(dt);
        }

    }

    private void setNewPosition(float dt){

        float distance = Vector2.dst(targetX, targetY, positionC.x, positionC.y);
        float stepDistance = Vector2.dst(positionC.x + moveVector.x, positionC.y + moveVector.y, positionC.x, positionC.y);

        //System.out.println("Distance: "+ distance);

        if (isRun) {
            //System.out.println("Distance: " + distance + " SD: "+ stepDistance  + " L: "+  (4 * stepDistance * spaceShipEngine.getSpeedActual() * dt));
            if (distance <= stepDistance ) {

                setStop();
            } else {
                if (distance < navigationFactor * 5){setNewTarget();}
                if (getFill(CargoType.FUEL) <= 0) {
                    spaceShipEngine.setSpeedActual(spaceShipEngine.getSpeedEngineSlow());
                    //spaceShipEngine.addDistance(Vector2.dst( positionC.x, moveVector.x * dt * spaceShipEngine.getSpeedActual() + positionC.x, positionC.y, moveVector.y * dt * spaceShipEngine.getSpeedActual() + positionC.y ));
                    setPositionOrgin(moveVector.x * dt * spaceShipEngine.getSpeedActual() + positionC.x, moveVector.y * dt * spaceShipEngine.getSpeedActual() + positionC.y);
                    //fuelFill = 0;
                } else {
                    //System.out.println("Distance: "+ stepDistance*dt*spaceShipEngine.getSpeedActual());
                    for (int i = 0; i < shipModules.size(); i++){
                        shipModules.get(i).addDistance(stepDistance);
                    }
                    setPositionOrgin(moveVector.x * dt * spaceShipEngine.getSpeedActual() + positionC.x, moveVector.y * dt * spaceShipEngine.getSpeedActual() + positionC.y);
                    subCargo(CargoType.FUEL, stepDistance * spaceShipEngine.getConsumptionFuel() / 100);
                    //fuelFill = fuelFill - stepDistance * spaceShipEngine.getConsumptionFuel() / 100;
                }
            }
        }

    }


    private void setStop() {
        setPositionOrgin(targetX, targetY);
        spaceShipEngine.setSpeedActual(0);
        isRun = false;
        planetTripCounter++;

        MackeXML.newPosition(this.getPositionCX(), this.getPositionCY());
        MackeXML.savePlounetCount(planetTripCounter);
        //ModifiedXML.writePlanetCountTripToXml(planetTripCounter);

        Action stopAction = Actions.parallel(
                Actions.rotateTo((float) 180, 1.2f),
                Actions.scaleTo(0.25f, 0.25f, 1.2f)
        );
        this.addAction(stopAction);

        for (int i =0; i<shipModules.size(); i++)
        if (shipModules.get(i).isModuleError()){
            shipModules.get(i).resetFailure();
        }
    }

    public void buy(CargoType cargoType, int quantity, double cost) {

        if (checkMoney(quantity, cost) && checkFill(cargoType, quantity)) {
            subMoney(quantity * cost);
            addCargo(cargoType, quantity);
        }
    }

    public void sell(CargoType cargoType, int quantity, double cost) {

        if (getFill(cargoType) >= quantity) {
            addMoney(quantity * cost);
            subCargo(cargoType, quantity);
        }
    }

    private void addCargo(CargoType cargoType, double quantity) {

        for (int i = 0; i < shipModules.size(); i++) {
            if (shipModules.get(i).getModuleType().equals(cargoType.getModuleType())) {
                quantity = shipModules.get(i).addCargo(cargoType, quantity);
            }
        }
    }

    private void subCargo(CargoType cargoType, double quantity) {

        for (int i = 0; i < shipModules.size(); i++) {
            if (shipModules.get(i).getModuleType().equals(cargoType.getModuleType())) {
                quantity = shipModules.get(i).subCargo(cargoType, quantity);
            }
        }
    }
    /**
     *
     */

    public void subMoney(double v) {
        money = money - v;
        MackeXML.saveMoney(money);
        //ModifiedXML.writeMoneyToXml(money);
        if (getMoney() <= 0){
            noMoney = true;
        }
    }

    private void addMoney(double v) {
        money = money + v;
        MackeXML.saveMoney(money);
        //ModifiedXML.writeMoneyToXml(money);
        if (getMoney() > 0 ){
            noMoney = false;
        }
    }

    private boolean checkMoney(int quantity, double cost) {

        if (quantity * cost > money){
            String msgText = "You do not have enough money to buy so much";
            String tile = "Caution!";
            gameScreen.createMsg(msgText, tile);
            return false;
        }else
        return true;
    }


    private boolean checkFill(CargoType cargoType, int quantity) {

        if (getCapacity(cargoType) < (quantity + getFill(cargoType))) {
            String msgText = "You do not have enough space to buy so much";
            String tile = "Caution!";
            gameScreen.createMsg(msgText, tile);
            return false;
        } else{
            return true;
            }
    }

    public double getFill(CargoType cargoType) {

        double fill = 0;
        for (int i = 0; i < shipModules.size(); i++) {
            if (shipModules.get(i).getModuleType().equals(cargoType.getModuleType())) {
                if (shipModules.get(i).getFill() > 0) {
                    fill = shipModules.get(i).getFillCargoType(cargoType) + fill;
                }
            }
        }
        return fill;
    }

    public double getFillModule(CargoType cargoType) {

        double fill = 0;
        for (int i = 0; i < shipModules.size(); i++) {
            if (shipModules.get(i).getModuleType().equals(cargoType.getModuleType())) {
                fill = shipModules.get(i).getFill() + fill;
            }
        }
        return fill;
    }


    public double getCapacity(CargoType cargoType) {

        double capacity = 0;
        for (int i = 0; i < shipModules.size(); i++) {
            if (shipModules.get(i).getModuleType().equals(cargoType.getModuleType())) {
                capacity = shipModules.get(i).getCapacity() + capacity;
            }
        }
        return capacity;
    }



    public void readExperience() {

        elMap.clear();

        for (int p = 0; p < persons.size(); p++) {
            ExperienceLevel ex1 = persons.get(p).getFirstExperienceLevel();
            addExperience(ex1);
            ExperienceLevel ex2 = persons.get(p).getSecondExperienceLevel();
            addExperience(ex2);
        }
        modifyFailureRate();
    }

    public void addExperience(ExperienceLevel ex){

        if (elMap.size() == 0) {
            elMap.put(ex.getExperienceType(),ex.getLevel());

        } else {
            for (int i = 0; i < elMap.size(); i++) {

                if (elMap.containsKey(ex.getExperienceType())){

                    if (elMap.get(ex.getExperienceType()) <= ex.getLevel()) {
                        elMap.put(ex.getExperienceType(),ex.getLevel());
                        break;
                    }
                }
                else {
                    elMap.put(ex.getExperienceType(),ex.getLevel());
                    break;
                }
            }
        }
    }


    public void modifyFailureRate() {

    for (int i = 1; i < shipModules.size(); i++) {
                ModuleType md = shipModules.get(i).moduleType;
                switch (md) {
                    case FUEL: case LOSE:  case GAS:{
                        if (elMap.containsKey(ExperienceType.MECHANIKS)) {
                            shipModules.get(i).setExperienceLevel(elMap.get(ExperienceType.MECHANIKS));
                        }
                        break;
                }
                    case LIQUID:
                    {
                        if (elMap.containsKey(ExperienceType.PLUMBER)) {
                            shipModules.get(i).setExperienceLevel(elMap.get(ExperienceType.PLUMBER));
                        }
                        break;
                    }
                    case SPACE_SHIP_ENGINE:
                        if (elMap.containsKey(ExperienceType.MECHANIKS)){
                            shipModules.get(i).setExperienceLevel(elMap.get(ExperienceType.MECHANIKS));
                        }
                        break;
            }
        }
    }

    private void setBaseFailureRate() {
        for (int i = 1; i < shipModules.size(); i++) {
            shipModules.get(i).setExperienceLevel(0);
        }
    }

    public void addMonth() {
        for (int i = 0; i < persons.size(); i++){
            persons.get(i).dethOrLive();
            if (persons.get(i).isDeth()) {
                persons.remove(i);
                readExperience();
                modifyFailureRate();
            }
            subMoney(persons.get(i).getPay());
        }
    }

    public void addYear() {
        for (int p = 0; p < persons.size(); p++){
            persons.get(p).addAge();
        }
    }

//GET SET **********************************************************************


    public double getMoney() {
        return money;
    }

    public void setMoney() {
        money = ReadXML.readPlayerMoney();
    }

    private void setPlanetTripCounter() {
        planetTripCounter = ReadXML.readPlayerPlanetCount();
    }

    public boolean isRun() {
        return isRun;
    }
}
