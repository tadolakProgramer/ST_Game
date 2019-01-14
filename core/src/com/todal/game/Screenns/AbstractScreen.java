package com.todal.game.Screenns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.todal.game.MyGdxGame;

import static com.todal.game.MyGdxGame.GAME_WIDTH;

/**
 * Created by User on 2018-03-07.
 */

public abstract class AbstractScreen implements Screen {

    protected MyGdxGame game;
    protected Stage stage;
    public OrthographicCamera camera;
    public Viewport viewport;
    protected SpriteBatch spriteBatch;
    protected boolean isVisible;
    public float scaleGame = 1.0f;


    public AbstractScreen(MyGdxGame game) {
        this.game = game;
        createCamera();
        viewport = new FitViewport(GAME_WIDTH, MyGdxGame.GAME_HEIGHT,camera);
        viewport.apply();
        stage = new Stage(viewport);
        spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(stage);
    }



    private void createCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GAME_WIDTH, MyGdxGame.GAME_HEIGHT);
        camera.update();
    }


    @Override
    public void render (float delta) {
        clearScreen();
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void show() {
    }

    @Override
    public void resume(){
        game.setPaused(false);
    }

    @Override
    public void pause() {
        game.setPaused(true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        System.out.println("Koniec");
        game.dispose();
    }

    @Override
    public void resize(int width, int height) {
            viewport.update(width,height);
            camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
            float w = viewport.getWorldWidth();
            this.scaleGame = (float)width/640;
            game.setGameScale(scaleGame);
        }

    public float getScaleGame() {
        return scaleGame;
    }
}
