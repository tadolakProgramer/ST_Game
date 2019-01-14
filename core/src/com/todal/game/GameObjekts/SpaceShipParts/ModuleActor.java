package com.todal.game.GameObjekts.SpaceShipParts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.GameScreen;
import com.todal.game.Screenns.SpaceShipScreen;

import static com.badlogic.gdx.Gdx.graphics;
import static com.todal.game.MyGdxGame.FILE_CARGO_ATLAS;
import static com.todal.game.MyGdxGame.GAME_WIDTH;
import static com.todal.game.MyGdxGame.getLoclaPath;

public class ModuleActor extends Actor {

    private String textureName;
    public TextureRegion region;
    public TextureAtlas textureAtlas;
    //private SpaceShipScreen spaceShipScreen;
    protected ShipModule shipModule;
    private float gameScale;

    public ModuleActor(final SpaceShipScreen spaceShipScreen, final ShipModule shipModule, final int index, float scale) {
        super();
        this.shipModule = shipModule;
        this.gameScale = scale;
        textureAtlas = new TextureAtlas(FILE_CARGO_ATLAS);
        region = new TextureRegion();

        switch (this.shipModule.moduleType) {
                case FUEL: {
                    textureName = "CargoFuel";
                    setTexture(textureName);
                    setScale(scale);
                    setPositionModuleOnShip(index);
                    break;
                }
                case GAS: {
                    textureName = "CargoGrafen";
                    setTexture(textureName);
                    setScale(scale);
                    setPositionModuleOnShip(index);
                    break;
                }
                case HOUSING_MODULE: {
                    textureName = "CargoHM";
                    setTexture(textureName);
                    setScale(scale);
                    setPositionModuleOnShip(index);
                    break;
                }
                case LOSE: {
                    textureName = "CargoTitan";
                    setTexture(textureName);
                    setScale(scale);
                    setPositionModuleOnShip(index);
                    break;
                }
            case LIQUID: {
                textureName = "CargoWoter";
                setTexture(textureName);
                setScale(scale);
                setPositionModuleOnShip(index);
                break;
            }
                case EMPTY:{
                    textureName = "Empty";
                    setTexture(textureName);
                    setScale(scale);
                    setPositionModuleOnShip(index);
                    break;
                }
                default:{
                    textureName = "CargoTitan";
                    setTexture(textureName);
                }
            }
        this.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!shipModule.getModuleType().equals(ModuleType.EMPTY)) {
                    spaceShipScreen.createWindowShipModuleInfo(shipModule);
                }
                else {
                    spaceShipScreen.createWindowBayNewModule(index);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }

    private void setPositionModuleOnShip(int index) {

        switch (Gdx.graphics.getWidth()) {
            case 1920: {
                if (index <= 5) {
                    setPosition((478 + (index * this.getWidth()*1.32f * gameScale)), 325);
                } else {
                    setPosition((788 + ((index - 6) * this.getWidth()*1.32f * gameScale)), 800);
                    setRotation(180);
                }
                break;
            }
            default: {

                if (index <= 5) {
                    setPosition((index + 349 + (index - 1) * 93.3f), 325);
                } else {
                    setPosition((420 + (index - 6) * 93.3f), 723);
                    setRotation(180);
                }
            }
        }
    }

    private void setTexture(String textureName){
        setWidth(textureAtlas.findRegion(textureName).packedWidth);
        setHeight(textureAtlas.findRegion(textureName).packedHeight);
        region.setRegion(textureAtlas.findRegion(textureName));
    }

    public void act(float dt){
        super.act(dt);
        //labelName.act(dt);
        update(dt);
    }

    public void update(float dt) {

    }

    public void draw(Batch batch, float parentAlpha)
    {
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        if ( isVisible() )
            batch.draw(region, getX(), getY(), getOriginX(),
                    getOriginY(),
                    getWidth(), getHeight(), getScaleX(),
                    getScaleY(), getRotation());

    }
}
