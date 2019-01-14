package com.todal.game.Screenns.Hud;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;

import com.todal.game.GameObjekts.SpaceShipParts.CargoType;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.GameScreen;


import static com.todal.game.MyGdxGame.GAME_HEIGHT;
import static com.todal.game.MyGdxGame.GAME_WIDTH;

public class Hud extends  AbstractHUD {



    //Scene2D widgets
    private final Label dateLabel;
    private Label moneyLabel;
    private static Label fuellLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label marioLabel;
    private ProgressBar fuelFillBar;
    private float scale;



    private float time = 0;
    private int fps = 0;


    public Hud(final GameScreen gameScreen, MyGdxGame game, SpriteBatch sb){

        super(gameScreen, game, sb);
        scale = game.getGameScale();

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top().pad(10f);
        //table.debug();
        //make the table fill the entire stage
        table.setFillParent(true);
        table.setColor(255,1,1,255);


        Table tableButton = new Table();
        tableButton.bottom().right().pad(10f);
        tableButton.setFillParent(true);

        ProgressBar.ProgressBarStyle progressBarStyle = skin.get("fancy", ProgressBar.ProgressBarStyle.class);
        TiledDrawable tiledDrawable = skin.getTiledDrawable("slider-fancy-knob").tint(skin.getColor("fuell"));
        tiledDrawable.setMinWidth(0);
        progressBarStyle.knobBefore = tiledDrawable;


        //define our labels using the String, and a Label style consisting of a font and color
        moneyLabel = new Label(String.format("%.2f", gameScreen.spaceShipPlayer.getMoney()), skin2, "grafen");
        //moneyLabel.setScale(scale);
        fuellLabel =new Label(String.format("%.2f", gameScreen.spaceShipPlayer.getFill(CargoType.FUEL)), skin2, "grafen");
        //fuellLabel.setScale(scale);
        fuelFillBar = new ProgressBar(0.0f, (float) gameScreen.spaceShipPlayer.getCapacity(CargoType.FUEL), 1, false, skin, "fancy");
        dateLabel = new Label(gameScreen.getDateOfGame(),skin2, "grafen");
        //dateLabel.setScale(scale);


        timeLabel = new Label("Money", skin2, "grafen");
        //timeLabel.setFontScale(scale);
        levelLabel = new Label("1-1: ", skin2, "grafen");
        //levelLabel.setScale(scale);
        worldLabel = new Label("WORLD: ", skin2, "grafen");
        //worldLabel.setScale(scale);
        marioLabel = new Label("Fuel: ",skin2, "grafen");
        //marioLabel.setScale(scale);


        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(marioLabel).expandX().padTop(1);
        table.add(worldLabel).expandX().padTop(1);
        table.add(timeLabel).expandX().padTop(1);
        table.add(new Label("Date: ", skin2, "grafen")).expandX();
        //add a second row to our table
        table.row();
        table.add(fuellLabel).expandX();
        stage.addActor(fuelFillBar);
        table.add(levelLabel).expandX();
        table.add(moneyLabel).expandX();
        table.add(dateLabel).expandX();

        //add

        TextButton textButton1 = new TextButton("Go to ", skin2);
        textButton1.bottom().right().pad(10);
        textButton1.setScale(scale);
        textButton1.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.setCameraToSpaceSchip();
                return true;
            }
        });


                TextButton textButtonZoomP = new TextButton("Zoom + ", skin2);
        textButtonZoomP.setPosition(100,0);
        textButtonZoomP.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.camera.zoom = gameScreen.camera.zoom - 0.01f;
                return true;
            }
        });

        TextButton textButtonZoomM = new TextButton("Zoom - ", skin2);
        textButtonZoomM.setPosition(200,0);
        textButtonZoomM.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.camera.zoom = gameScreen.camera.zoom + 0.01f;
                return true;
            }
        });

        TextButton textButtonZoom1 = new TextButton("Zoom 1:1 ", skin2);
        textButtonZoom1.setPosition(300,0);
        textButtonZoom1.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.camera.zoom = 1.0f;
                return true;
            }
        });

        stage.addActor(table);

        switch(Gdx.app.getType()) {
            case Desktop: {
                tableButton.add(textButton1);
                tableButton.add(textButtonZoomP);
                tableButton.add(textButtonZoomM);
                tableButton.add(textButtonZoom1);
                break;
            }
            case Android: {
                tableButton.add(textButton1);
                tableButton.add(textButtonZoom1);
                break;
            }
        }
        stage.addActor(tableButton);
        }


    public void createWindowPlanetInfo(String planetName) {

        WindowPlanetInfo windowInfoPlanet = new WindowPlanetInfo(gameScreen, game, sb, planetName);
        stage.addActor(windowInfoPlanet.window);
    }

    public void createWindowPlanetMarket(String planetName) {

        WindowPlanetMarket windowPlanetMarket = new WindowPlanetMarket(gameScreen, game, sb, planetName);
        stage.addActor(windowPlanetMarket.window);

    }

    public void showDlgPayment() {
        WindowPaymentList windowPaymentList = new WindowPaymentList(gameScreen, game, sb);
        stage.addActor(windowPaymentList.dlgListPayment);
    }

    public void update(float dt) {
        progressBarUpdate();
        readShipError();
        moneyLabel.setText(String.format("%.2f", gameScreen.spaceShipPlayer.getMoney()));
        dateLabel.setText(gameScreen.getDateOfGame());
        time = time +dt;
        fps ++;
        if (time >= 1){
            float fpsA = fps;
           levelLabel.setText("FPS"+fpsA);
           time = 0;
           fps = 0;
        }
        }

    private void readShipError() {

        for (int i=0; i<gameScreen.spaceShipPlayer.shipModules.size(); i++){
            if (gameScreen.spaceShipPlayer.shipModules.get(i).isModuleError() && (!gameScreen.spaceShipPlayer.shipModules.get(i).isErrorIsRead())){

                String messageText = gameScreen.spaceShipPlayer.shipModules.get(i).getTextEror();
                String titleText = "Error" + gameScreen.spaceShipPlayer.shipModules.get(i).getModuleType().name();

                showDlg(messageText, titleText);
            }
        }
    }

    private void progressBarUpdate() {
        fuellLabel.setText(String.format("%.2f", gameScreen.spaceShipPlayer.getFill(CargoType.FUEL))+"  "+String.format("%.2f", (gameScreen.spaceShipPlayer.getFill(CargoType.FUEL)/(gameScreen.spaceShipPlayer.getCapacity(CargoType.FUEL))*100))+"$");
        fuelFillBar.setRange(0,(float)gameScreen.spaceShipPlayer.getCapacity(CargoType.FUEL));
        fuelFillBar.setValue((float)gameScreen.spaceShipPlayer.getFill(CargoType.FUEL));
        fuelFillBar.setPosition(fuellLabel.getX() + fuellLabel.getWidth()/2 - fuelFillBar.getWidth()/2, fuellLabel.getY()+5 );
    }

    public static void addScore(int value){

    }

    public void showDlg(String messageText, String titleText) {

        final Dialog dialog = new Dialog(titleText, skin2, "dialog");
        TextButton btnMain = new TextButton("Close", skin2);
        dialog.setModal(true);
        dialog.text(messageText);
        dialog.button(btnMain).pad(10);
        dialog.setSize(600,400);
        dialog.setPosition(GAME_WIDTH / 2.0f - 300, GAME_HEIGHT / 2.0f - 200);
        stage.addActor(dialog);

        btnMain.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dialog.remove();
                return true;
            }
        });
    }

    public void showDlgLowMony() {

        Dialog dlgNewPrice = new Dialog("Pozor! ", skin2);
        TextButton btnMain = new TextButton("Main", skin2);
        TextButton btnTryAgain = new TextButton("Try Again", skin2);
        dlgNewPrice.text("To low money or capacity ");
        dlgNewPrice.button(btnTryAgain);
        dlgNewPrice.button(btnMain);
        dlgNewPrice.setSize(200,200);
        dlgNewPrice.setPosition(GAME_WIDTH / 2.0f - 100, GAME_HEIGHT / 2.0f - 100);
        stage.addActor(dlgNewPrice);
    }

}