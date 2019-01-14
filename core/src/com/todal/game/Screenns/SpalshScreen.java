package com.todal.game.Screenns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.todal.game.MyGdxGame;


/**
 * Created by User on 2018-03-07.
 */

public class SpalshScreen extends AbstractScreen {

    private Texture spalashImg;


    public SpalshScreen(final MyGdxGame game) {
        super(game);
        initSplashScreen();

        Timer.schedule(new Task() {
            @Override
            public void run() {
                game.setScreen(new GameScreen(game));
            }
        },2);

        }

    private void initSplashScreen() {
        spalashImg = new Texture("data/badlogic.jpg");
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        spriteBatch.begin();
        spriteBatch.draw(spalashImg, 10, 10);
        spriteBatch.end();
    }
}
