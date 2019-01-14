package com.todal.game.Helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.todal.game.GameObjekts.SpaceObjekt.Planet;
import com.todal.game.GameObjekts.SpaceObjekt.Ware;
import com.todal.game.GameObjekts.SpaceShipParts.CargoType;
import com.todal.game.GameObjekts.SpaceShipParts.Container;
import com.todal.game.GameObjekts.SpaceShipParts.ModuleType;
import com.todal.game.GameObjekts.SpaceShipParts.ShipCrow.Crow;
import com.todal.game.GameObjekts.SpaceShipParts.ShipCrow.CrowType;
import com.todal.game.GameObjekts.SpaceShipParts.ShipCrow.ExperienceLevel;
import com.todal.game.GameObjekts.SpaceShipParts.ShipCrow.ExperienceType;
import com.todal.game.GameObjekts.SpaceObjekt.SpaceShipPlayer;
import com.todal.game.GameObjekts.SpaceShipParts.ShipModule;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.GameScreen;
import com.todal.game.Screenns.Hud.Hud;

import java.util.ArrayList;
import java.util.List;


public class ReadXML {

    public static double readPlayerMoney(){

        Element root = new XmlReader().parse(Gdx.files.local(MyGdxGame.FILE_PLAYER));
        return root.getFloat("money");
    }

    public static int readPlayerPlanetCount(){

        Element root = new XmlReader().parse(Gdx.files.local(MyGdxGame.FILE_PLAYER));
        return root.getInt("planetCount");
    }


    public static  Boolean setShipFromXML(SpaceShipPlayer spaceShipPlayer) {

        Element root = new XmlReader().parse(Gdx.files.internal(MyGdxGame.FILE_SPACE_SHIP));

        spaceShipPlayer.setSpaceObjectName(root.get("Name"));

        Element position = root.getChildByName("pos");
        spaceShipPlayer.setPositionOrgin(position.getFloat("x"), position.getFloat("y"));

            for (int k=0; k<14; k++) {
                Element Slot = root.getChildByName("SLOT" + k);

                String TEXT = (Slot.get("ModuleType"));
                if (!TEXT.equals("Empty")) {
                    spaceShipPlayer.addModule(k, ModuleType.valueOf(Slot.get("ModuleType")),
                            Slot.get("Name"),
                            Slot.getInt("Capacity"),
                            //Slot.getInt("Fill"),
                            Slot.getFloat("Cost"),
                            Slot.getInt("BaseFailureRate"));

                    Element Fill = Slot.getChildByName("Fill");
                    int zzz = Fill.getChildCount();
                    for (int j = 0; j < zzz; j++) {

                        spaceShipPlayer.shipModules.get(k).addCargo(CargoType.valueOf(Fill.getChild(j).get("cargotype")), Fill.getChild(j).getFloat("fill"));
                    }
                }
                    else spaceShipPlayer.addModule(k, ModuleType.EMPTY,"Empty", 0,0,0);
                }

        return true;
    }

    public static boolean readCaptain(SpaceShipPlayer spaceShipPlayer){

        Element root = new XmlReader().parse(Gdx.files.internal("data/crows.xml"));

        int personCount = root.getChildCount();

        for (int i=0; personCount>i; i++) {

            Element person = root.getChild(i);

            ExperienceLevel ex1 = new ExperienceLevel(ExperienceType.valueOf(person.get("fet")), person.getInt("fel"));
            ExperienceLevel ex2 = new ExperienceLevel(ExperienceType.valueOf(person.get("set")), person.getInt("sel"));

            spaceShipPlayer.persons.add( new Crow(
                    CrowType.valueOf(root.getChild(i).getName()),
                    person.get("name"),
                    person.getInt("age"),
                    person.getFloat("height"),
                    person.get("gender"),
                    new ExperienceLevel(ExperienceType.valueOf(person.get("fet")), person.getInt("fel")),
                    new ExperienceLevel(ExperienceType.valueOf(person.get("set")), person.getInt("sel")),
                    person.getFloat("pay")
            ));

        }
        spaceShipPlayer.readExperience();
        return true;
    }

    public static boolean readPlanets(MyGdxGame game, GameScreen screen, Hud hud){

        List<Ware> wares = new ArrayList<>();
        Element root = new XmlReader().parse(Gdx.files.internal(MyGdxGame.FILE_PLANETS));

        int j = root.getChildCount();

        for (int i=0; i < j; i++) {
            Element planet = root.getChild(i);
            wares.clear();

            screen.planets.add(new Planet
                    (game, hud,
                            planet.getFloat("posx"),
                            planet.getFloat("posy"),
                            planet.get("texture"),
                            planet.get("name"),
                            planet.getFloat("rot")));
            for (int k=1; k<=4; k++) {

                Element cargo = planet.getChildByName("CargoType"+k);
                screen.planets.get(i).wares.add(new Ware(CargoType.valueOf(cargo.getAttribute("cargotype")), cargo.getFloatAttribute(("price"))));
            }
        }

        return true;
    }

    public static List<ShipModule> readListModuleFromXML(int index){

        List <ShipModule> shipModules = new ArrayList<>();
        Element root = new XmlReader().parse(Gdx.files.internal(MyGdxGame.FILE_SHIP_MODULES));

        int j = root.getChildCount();

        for (int i =0; i<j; i++) {
            Element module = root.getChild(i);
            switch (index) {
                case 1: case 6: {
                    if (!module.get("ModuleType").equals(ModuleType.HOUSING_MODULE.name())) {
                        addModuleToList(shipModules, module);
                    }
                    break;
            }
                case 5: case 10: {
                    if (!module.get("ModuleType").equals(ModuleType.FUEL.name())) {
                        addModuleToList(shipModules, module);
                    }
                    break;
                }
                case 2: case 3: case 4: case 7: case 8: case 9:{
                    if (!module.get("ModuleType").equals(ModuleType.FUEL.name()) &&
                    (!module.get("ModuleType").equals(ModuleType.HOUSING_MODULE.name())))
                    {
                        addModuleToList(shipModules, module);
                    }
                }
                break;

            }
        }
        return shipModules;
    }

    private static void addModuleToList(List<ShipModule> shipModules, Element module) {
        shipModules.add(new Container(
                ModuleType.valueOf(module.get("ModuleType")),
                module.get("Name"),
                module.getFloat("Capacity"),
                module.getFloat("Cost"),
                0,
                module.getInt("BaseFailureRate")));
    }
}
