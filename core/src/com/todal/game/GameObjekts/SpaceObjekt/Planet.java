package com.todal.game.GameObjekts.SpaceObjekt;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.todal.game.GameObjekts.SpaceShipParts.CargoType;
import com.todal.game.Helper.ModifiedXML;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.Hud.Hud;

;
import java.util.ArrayList;
import java.util.List;

import static com.todal.game.MyGdxGame.GAME_SCALE;

public class Planet extends SpaceObject {

    private MyGdxGame game;
    private float timeToChangePrice;
    private Ware ware;
    private CargoType cargoType;

    public List <Ware> wares = new ArrayList<>();

    private Hud hud;

    /** For create new space **/
    public Planet(final MyGdxGame game, Hud hud, float x, float y, String name) {
        super(game);
        this.path = "planet_"+MathUtils.random(1, 16);
        this.hud = hud;
        setScale(GAME_SCALE);
        setPosition(x,y);
        rotationSpeed = MathUtils.random(10f, 15f);
        setSpaceObjectName(name);
        setTexture(path);
        newRandom();
        addClickListener();
        }

        /** For read from XML **/
    public Planet(final MyGdxGame game, Hud hud, float x, float y, String path, String name, float speedRot) {
        super(game);
        this.path = path;
        this.hud = hud;
        setScale(GAME_SCALE);
        setPosition(x, y);
        rotationSpeed = speedRot;
        setSpaceObjectName(name);
        this.wares.clear();
        this.wares = wares;
        setTexture(path);
        //newRandom();
        addClickListener();
    }

    private void newRandom() {

        boolean rerandom;

        wares.add(new Ware(cargoType.values()[MathUtils.random(2, CargoType.values().length) - 1]));
        setPrice(0);

        int z = wares.size();

        for (int w = z; w < 3; w++) {
            rerandom = false;
            do {
                rerandom = false;
                cargoType = (cargoType.values()[MathUtils.random(2, CargoType.values().length) - 1]);
                for (int i = 0; i < w ; i++){
                    if (cargoType.equals(wares.get(i).getCargoType())){
                        rerandom = true;
                    }
                }
            }
            while (rerandom);
            wares.add(new Ware(cargoType));
            setPrice(w);
        }

        wares.add(new Ware(CargoType.FUEL));
        setPrice(3);
    }

    private void setPrice(int i2) {
        wares.get(i2).setPrice(wares.get(i2).randomPrice(wares.get(i2).getCargoType()));
    }

    private void addClickListener(){
        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                hud.createWindowPlanetInfo(spaceObjectName);
                System.out.println("KLIK_PLANET");
                return super.touchDown(event, x, y, pointer, button);
            }
        });
    }

    public void update(float dt) {

        setPositionC();
        setRotations(dt);
        setLabelPosition();
        setActualSize();

        timeToChangePrice = timeToChangePrice + dt;

        if (timeToChangePrice > rotationSpeed * 1000){
            changePrice();
            timeToChangePrice = 0;
        }

    }

    public void changePrice(){
        for (int i=0; i< wares.size();i++){
            wares.get(i).changePrice();
        }

        //ModifiedXML.writeNewPriceToXml(spaceObjectName, wares);

        hud.showDlg(spaceObjectName + "New price on ", "New price");
    }



}
