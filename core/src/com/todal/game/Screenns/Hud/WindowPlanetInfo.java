package com.todal.game.Screenns.Hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.GameScreen;

import static com.todal.game.MyGdxGame.GAME_HEIGHT;
import static com.todal.game.MyGdxGame.GAME_WIDTH;

public class WindowPlanetInfo extends AbstractHUD {

    private Label labelWarePrice1;
    private Label labelWarePrice2;
    private Label labelWarePrice3;
    private Label labelWarePrice4;

    private int windowInfoPlanetCount;


    public WindowPlanetInfo(final GameScreen gameScreen, MyGdxGame game, SpriteBatch sb, String planetName) {
        super(gameScreen, game, sb);

        windowInfoPlanetCount=1;

        if (windowInfoPlanetCount <= 1) {

            float  spaceshipPosX = gameScreen.spaceShipPlayer.getPositionCX();
            float  spaceshipPosY = gameScreen.spaceShipPlayer.getPositionCY();

            gameScreen.multiplexer.addProcessor(stage);

            window = new Window(planetName, skin2);
            region = new TextureRegion();

            window.setColor(Color.BLACK);
            window.getTitleLabel().setColor(Color.CHARTREUSE);
            window.setSize(600.0f, 600.0f);
            window.setPosition(GAME_WIDTH / 2.0f - 300, GAME_HEIGHT / 2.0f - 300);//, Align.center);

            Button button = new Button(skin, "close");
            window.getTitleTable().add(button).padRight(1.0f);
            window.setBounds(window.getX(), window.getY(), window.getWidth(), window.getHeight());

            Table winTable = new Table();
            window.add(winTable).grow().pad(10);
            winTable.align(1);
            winTable.top();
            stage.addActor(window);


            for (int i = 0; i <= gameScreen.planets.size() - 1; i++) {
                if (gameScreen.planets.get(i).getSpaceObjectName().equals(planetName)) {
                    planet = gameScreen.planets.get(i);
                    planetNumber = i;
                    region = game.textureAtlas.findRegion(planet.getPath());
                    planetImage = new Image(game.textureAtlas.findRegion(planet.getPath()));

                    planetImage.setSize(10, 10);
                    labelWarePrice1 = new Label(String.format("%.2f", planet.wares.get(0).getPrice()) + " T$", skin2, planet.wares.get(0).getCargoType().getStyleName());
                    labelWarePrice1.setFontScale(1.5f);
                    labelWarePrice2 = new Label(String.format("%.2f", planet.wares.get(1).getPrice()) + " T$", skin2, planet.wares.get(1).getCargoType().getStyleName());
                    labelWarePrice2.setFontScale(1.5f);
                    labelWarePrice3 = new Label(String.format("%.2f", planet.wares.get(2).getPrice()) + " T$", skin2, planet.wares.get(2).getCargoType().getStyleName());
                    labelWarePrice3.setFontScale(1.5f);
                    labelWarePrice4 = new Label(String.format("%.2f", planet.wares.get(3).getPrice()) + " T$", skin2, "fuel");
                    labelWarePrice4.setFontScale(1.5f);
                    break;
                }
            }

            float distance = Vector2.dst(spaceshipPosX, spaceshipPosY, planet.getPositionCX(), planet.getPositionCY());

            Label titanLabel = new Label(planet.wares.get(0).getCargoType().name(), skin2, planet.wares.get(0).getCargoType().getStyleName());
            titanLabel.setFontScale(1.5f);

            Label grafenLabel = new Label(planet.wares.get(1).getCargoType().name(), skin2, planet.wares.get(1).getCargoType().getStyleName());
            grafenLabel.setFontScale(1.5f);

            Label woterLabel = new Label(planet.wares.get(2).getCargoType().name(), skin2, planet.wares.get(2).getCargoType().getStyleName());
            woterLabel.setFontScale(1.5f);

            Label fuellLabel = new Label(planet.wares.get(3).getCargoType().name(), skin2, "fuel");
            fuellLabel.setFontScale(1.5f);


            TextButton textButton1 = new TextButton("Start", skin2);
            TextButton textButton2 = new TextButton("Close", skin2);

            window.row().pad(5);
            winTable.row().colspan(2);
            winTable.add(planetImage).size(100, 100);
            winTable.row().colspan(2);
            winTable.add(new Label("Location X: "+ planet.getPositionCX() + " Y: "+planet.getPositionCY(), skin2, "small")).expand().pad(5);
            winTable.row().colspan(2);
            winTable.add(new Label("Distance: "+ String.format("%.2f", distance) + " ly", skin2)).expand().pad(5);

            winTable.row().pad(5);
            winTable.add(titanLabel).expandX().left();
            winTable.add(labelWarePrice1).expandX().right();

            winTable.row().pad(5);
            winTable.add(grafenLabel).expandX().left();
            winTable.add(labelWarePrice2).expandX().right();

            winTable.row().pad(5);
            winTable.add(woterLabel).expandX().left();
            winTable.add(labelWarePrice3).expandX().right();

            winTable.row().pad(5);
            winTable.add(fuellLabel).expandX().left();
            winTable.add(labelWarePrice4).expand().right();

            winTable.row();
            winTable.add(textButton1).expand().center().pad(10);
            winTable.add(textButton2).expand().center().pad(10);

            textButton2.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    window.remove();
                    windowInfoPlanetCount=0;
                    gameScreen.multiplexer.removeProcessor(stage);
                }
            });
            textButton1.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    gameScreen.spaceShipPlayer.setStart(planetNumber);
                    window.remove();
                    windowInfoPlanetCount=0;
                    gameScreen.multiplexer.removeProcessor(stage);
                }
            });
        } else {
            windowInfoPlanetCount=0;
        }
    }

    public void update(float dt) {
        windowInfoPlanetUpdate(dt);
    }

    private void windowInfoPlanetUpdate(float dt) {
        if (windowInfoPlanetCount >= 1) {
            planetImage.setOrigin(planetImage.getImageWidth() / 2, planetImage.getImageHeight() / 2);
            planetImage.rotateBy(planet.getRotationSpeed() * dt);
        }
    }
}
