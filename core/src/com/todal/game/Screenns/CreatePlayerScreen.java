package com.todal.game.Screenns;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.todal.game.MyGdxGame;

public class CreatePlayerScreen extends AbstractScreen {

    protected Skin skin;
    private Table tablePlayer;
    private TextField tfName;

    private String playerName;
    private GameScreen gameScreen;
    private MyGdxGame game;
    private double money;

    public CreatePlayerScreen(final MyGdxGame game) {
        super(game);
        this.game = game;
        Gdx.input.setInputProcessor(stage);
        init();
    }

    private void init(){
        //skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));
        //skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        createTablePlayer();
    }

    private void createTablePlayer() {

        tablePlayer = new Table();

        tablePlayer.top().center().pad(10f);
        //tableCrows.debug();
        tablePlayer.setFillParent(true);

        tablePlayer.row();
        tablePlayer.add(new Label("Your Name Captein:", skin));
        TextField tfName = new TextField("Capitan Solo", skin);
        tablePlayer.add(tfName);

        TextButton textButtonClose = new TextButton("Close", skin);

        /** Group Button**/
        tablePlayer.row();
        ButtonGroup buttonGroup = new ButtonGroup();
        ImageTextButton imageTextButton = new ImageTextButton("Male", skin, "radio");
        buttonGroup.add(imageTextButton);
        tablePlayer.add(imageTextButton);

        imageTextButton = new ImageTextButton("Female", skin, "radio");
        buttonGroup.add(imageTextButton);
        tablePlayer.add(imageTextButton);

        tablePlayer.row();
        tablePlayer.row().colspan(2);
        tablePlayer.add(textButtonClose.center().pad(10));


        textButtonClose.addListener(new ClickListener() {
            @Override
            public void  touchUp(InputEvent event, float x, float y, int pointer, int button) {
                tablePlayer.remove();
                game.setScreen(new SpalshScreen(game));

            }
        });

        stage.addActor(tablePlayer);


    }

    @Override
    public void render(float delta) {

            super.render(delta);
            update();
            spriteBatch.begin();
            stage.draw();
            spriteBatch.end();
    }

    private void update() {
        stage.act();
    }


}
