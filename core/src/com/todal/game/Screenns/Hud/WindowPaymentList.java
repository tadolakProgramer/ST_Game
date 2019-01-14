package com.todal.game.Screenns.Hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.todal.game.MyGdxGame;
import com.todal.game.Screenns.GameScreen;

import static com.todal.game.MyGdxGame.GAME_HEIGHT;
import static com.todal.game.MyGdxGame.GAME_WIDTH;



public class WindowPaymentList extends AbstractHUD {

    protected Window dlgListPayment;

    public WindowPaymentList(final GameScreen gameScreen, MyGdxGame game, SpriteBatch sb) {
        super(gameScreen, game, sb);

        gameScreen.multiplexer.addProcessor(stage);

        float sumOfPayment =0;

        dlgListPayment = new Window("List of payment ", skin2);
        TextButton btnTryAgain = new TextButton("Close", skin2);
        Table table = new Table();

        table.align(1);
        table.top();

        for (int i = 0; i < gameScreen.spaceShipPlayer.persons.size(); i++)
        {
            String name = gameScreen.spaceShipPlayer.persons.get(i).getName();
            String crowType = gameScreen.spaceShipPlayer.persons.get(i).getCrowType().name();
            String pay = String.format("%.2f", gameScreen.spaceShipPlayer.persons.get(i).getPay());
            sumOfPayment = sumOfPayment + gameScreen.spaceShipPlayer.persons.get(i).getPay();
            table.row();
            table.add(new Label(name + " "+ crowType + " " + pay,skin,"grafen")).pad(5);
        }
        table.row();
        table.add(new Label("Sum of payment: "+sumOfPayment, skin, "grafen")).pad(5);
        table.row().pad(10);
        table.add(btnTryAgain);

        dlgListPayment.setSize(400,300);
        dlgListPayment.setPosition(GAME_WIDTH / 2.0f - 100, GAME_HEIGHT / 2.0f - 100);
        dlgListPayment.add(table).grow().pad(10);

        stage.addActor(dlgListPayment);

        btnTryAgain.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dlgListPayment.remove();
                gameScreen.multiplexer.removeProcessor(stage);
            }
        });
    }

    public void update(float dt) {

    }
}
