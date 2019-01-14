package com.todal.game.Screenns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.todal.game.GameObjekts.SpaceObjekt.Planet;
import com.todal.game.GameObjekts.SpaceObjekt.SpaceShipPlayer;
import com.todal.game.Helper.CreateXmlFile;
import com.todal.game.Helper.ModifiedXML;
import com.todal.game.Helper.ReadXML;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.Hud.Hud;

import java.util.ArrayList;
import java.util.List;

import static com.todal.game.MyGdxGame.FILE_PLANETS;
import static com.todal.game.MyGdxGame.FILE_PLAYER;
import static com.todal.game.MyGdxGame.GAME_HEIGHT;
import static com.todal.game.MyGdxGame.GAME_WIDTH;
import static com.todal.game.MyGdxGame.TIME_TO_PAYMENT;


public class GameScreen extends AbstractScreen implements InputProcessor, GestureDetector.GestureListener {


    public List<Planet> planets = new ArrayList<Planet>();
    public List<Planet> viewPlanets = new ArrayList<Planet>();

    public SpaceShipPlayer spaceShipPlayer;

    private float timeToPayment;
    private float gameTime;
    private String dateOfGame;
    private int month;
    private int year = 2400;

    private Planet planet;

    private Hud hud;
    private Background background;
    private OrthographicCamera backgroundCam;
    public InputMultiplexer multiplexer;


    public GameScreen(final MyGdxGame game) {
        super(game);
        multiplexer = new InputMultiplexer(this, stage);
        GestureDetector gd = new GestureDetector(this);
        multiplexer.addProcessor(gd);
        multiplexer.addProcessor(this);
        init();
    }

    private void init() {
        backgroundInit();
        initSpaceShipPlayer();
        initHUD();
        setDateOfGame();
        spaceInit();
        setCameraToSpaceSchip();
    }

    private void backgroundInit() {
        backgroundCam = new OrthographicCamera();
        backgroundCam.setToOrtho(false, MyGdxGame.GAME_WIDTH, MyGdxGame.GAME_HEIGHT);
        background = new Background();
    }

    private void spaceInit(){
        //CreateXmlFile.crateSpace(game, this, hud);
        ReadXML.readPlanets(game, this, hud);
        for (int i = 0; i<=planets.size()-1; i++){
            stage.addActor(planets.get(i));
        }
        stage.addActor(spaceShipPlayer);
    }

    private void initHUD() {
        hud = new Hud(this, game, spriteBatch);
    }

    private void initSpaceShipPlayer() {
        spaceShipPlayer = new SpaceShipPlayer(game, this);

    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if ((camera.position.x - Gdx.input.getDeltaX() > GAME_WIDTH / 2) || (camera.position.x + Gdx.input.getDeltaX() < 34000)){
            camera.translate(-Gdx.input.getDeltaX(), 0);
            backgroundCam.translate(-Gdx.input.getDeltaX()/4, 0);
                    }
        if ((camera.position.y + Gdx.input.getDeltaY() < GAME_HEIGHT / 2) || (camera.position.y + Gdx.input.getDeltaY() < 34000)) {
            camera.translate(0, Gdx.input.getDeltaY());
            backgroundCam.translate(0, Gdx.input.getDeltaY()/4);
            }

                   return false;
        }


    @Override
    public void render(float delta){
        super.render(delta);
        update(delta);
        //System.out.println("KLIK: " + delta);

        background.render(backgroundCam);
       spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);

        spriteBatch.begin();
        stage.draw();
        spriteBatch.end();

        hud.stage.draw();
        //update(delta);
        }

        private void setViewPlanets(){

            float x1 = camera.position.x;
            float y1 = camera.position.y;
            float w = GAME_WIDTH * camera.zoom;
            float h = GAME_HEIGHT * camera.zoom;

            for (int i=0; i<planets.size(); i++){
                if ((planets.get(i).getPositionCX() - planets.get(i).getActualWidth()/2 >= x1 + w/2) ||
                        (planets.get(i).getPositionCX() + planets.get(i).getActualWidth()/2 <= x1 - w/2) ||
                        (planets.get(i).getPositionCY() - planets.get(i).getActualHight()/2 >= y1 + h/2) ||
                        (planets.get(i).getPositionCY() + planets.get(i).getActualHight()/2 <= y1 - h/2)
                        )
                {
                    if ( planets.get(i).isVisible()){
                    planets.get(i).setVisible(false);
                    viewPlanets.remove(planets.get(i));}
                }
                else{
                    if(!planets.get(i).isVisible()){
                    planets.get(i).setVisible(true);
                    viewPlanets.add(planets.get(i));}
                    }
                }
            }


    private void update(float dt) {
        backgroundCam.zoom = camera.zoom;
        backgroundCam.update();
        hud.update(dt);
        stage.act();
        setViewPlanets();
        updateTime(dt);
        }

    private void updateTime(float dt) {
        gameTime = gameTime + dt;
        timeToPayment = timeToPayment + dt;
        if (timeToPayment >= TIME_TO_PAYMENT){
            timeToPayment = 0;
            spaceShipPlayer.addMonth();
            hud.showDlgPayment();
            setDateOfGame();
        }
    }

    private void setDateOfGame() {
        month++;
        if (month > 12){
            month = 1;
            year++;
            spaceShipPlayer.addYear();
        }
        dateOfGame = (Integer.toString(year)+"."+Integer.toString(month));
    }

    public String getDateOfGame() {
        return dateOfGame;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiplexer);
    }
    @Override

    public  void dispose(){
        ModifiedXML.writePositionToXml(spaceShipPlayer.getOriginX(), spaceShipPlayer.getOriginY());

    }


    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }



    public void setCameraToSpaceSchip() {

        //double angle = Math.atan2(spaceShipPlayer.getPositionCY() - camera.position.y, camera.position.x-spaceShipPlayer.getPositionCX());
        //float distance = Vector2.dst2(camera.position.x, camera.position.y, spaceShipPlayer.getPositionCX(), camera.position.y)/100;
        //float moveX = (float)Math.cos(angle)*distance;
        //float moveY = (float)Math.sin(angle)*distance;
        //for (int i = 1 ; i<=100;i++) {
            //camera.translate(moveX, moveY);
            camera.position.y = spaceShipPlayer.getPositionCY();
            camera.position.x = spaceShipPlayer.getPositionCX();

            backgroundCam.position.x = camera.position.x;
            backgroundCam.position.y = camera.position.y;
        }


    public void createSpaceShipScreen() {

        game.setScreen(new SpaceShipScreen(game, spaceShipPlayer, this));
    }

    public void createMarketWindow() {
        hud.createWindowPlanetMarket(spaceShipPlayer.targetName);
    }

    public void createMsg(String msgText, String title){
        hud.showDlg(msgText, title);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        /** Called when the user performs a pinch zoom gesture. The original distance is the distance in pixels when the gesture
         * started.
         * @param initialDistance distance between fingers when the gesture started.
         * @param distance current distance between fingers. */

        camera.zoom = camera.zoom + (initialDistance - distance)/50000;
        if (camera.zoom > 5)
            camera.zoom = 5;
        if (camera.zoom < 0.5f)
            camera.zoom = 0.5f;
        camera.update();
        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
