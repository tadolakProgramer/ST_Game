package com.todal.game.GameObjekts.SpaceObjekt;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.todal.game.MyGdxGame;

public abstract class SpaceObject extends Actor {

    private MyGdxGame game;
    protected TextureRegion region;
    protected String path;
    protected  Vector2 positionC;

    protected float rotationSpeed;
    public Label labelName;
    public String spaceObjectName;

    private float actualWidth;
    private float actualHight;
    private float actualZoom;

    public SpaceObject(final MyGdxGame game) {
        super();
        this.game = game;
        region = new TextureRegion();
        positionC = new Vector2();
        addLabel();
        }

    private void addLabel() {
        BitmapFont Fonts = game.myFont;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = Fonts;
        labelStyle.fontColor = Color.YELLOW;
        labelName = new Label(spaceObjectName, labelStyle);
    }

    public void setTexture(String texture){

        setWidth(game.textureAtlas.findRegion(texture).packedWidth);
        setHeight(game.textureAtlas.findRegion(texture).packedHeight);
        region.setRegion(game.textureAtlas.findRegion(texture));
        setOrigin((game.textureAtlas.findRegion(texture).packedWidth)/2, (game.textureAtlas.findRegion(texture).packedHeight)/2);
    }

    public void act(float dt){
        super.act(dt);
        //labelName.act(dt);
        update(dt);
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
            labelName.draw(batch, parentAlpha);

       // System.out.println("P: "+ spaceObjectName);

    }

    public void update(float dt) {

        }
        protected void setLabelPosition(){
            labelName.setX(positionC.x - labelName.getPrefWidth() / 2);
            labelName.setY(positionC.y - getHeight() / 2 * getScaleY() - labelName.getPrefHeight() / 2);
        }

        public Vector getVector() {
            return positionC;
        }

        public void setPositionOrgin(float x, float y){
            setPosition(x - getOriginX(), y - getOriginY());
        }

    protected void setRotations(float dt) {
        setRotation(getRotation()+rotationSpeed * dt);
    }

    public void  setPositionC(){
        positionC.x = (getX() + getOriginX());
        positionC.y = (getY() + getOriginY());
    }

    public float getPositionCX(){
        return positionC.x;
    }

    public float getPositionCY(){
        return positionC.y;
    }

    public void setSpaceObjectName(String spaceObjectName) {
        this.spaceObjectName = spaceObjectName;
        labelName.setText(spaceObjectName);
    }

    public String getPath() {
        return path;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public String getSpaceObjectName() {
        return spaceObjectName;
    }

    public void setActualSize(){
        actualWidth = getScaleX() * getWidth();
        actualHight = getScaleY() * getHeight();
    }

    public float getActualWidth() {
        return actualWidth;
    }

    public float getActualHight() {
        return actualHight;
    }


}

