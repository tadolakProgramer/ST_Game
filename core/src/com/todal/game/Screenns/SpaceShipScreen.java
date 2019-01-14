package com.todal.game.Screenns;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.todal.game.GameObjekts.SpaceObjekt.SpaceShipDraft;
import com.todal.game.GameObjekts.SpaceObjekt.SpaceShipPlayer;
import com.todal.game.GameObjekts.SpaceShipParts.CargoType;
import com.todal.game.GameObjekts.SpaceShipParts.ModuleActor;
import com.todal.game.GameObjekts.SpaceShipParts.ShipModule;
import com.todal.game.Helper.ReadXML;
import com.todal.game.MyGdxGame;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.todal.game.MyGdxGame.FILE_EARTH_SKIN;
import static com.todal.game.MyGdxGame.FILE_UI_SKIN;
import static com.todal.game.MyGdxGame.GAME_HEIGHT;
import static com.todal.game.MyGdxGame.GAME_WIDTH;

public class SpaceShipScreen extends AbstractScreen {

    private SpaceShipDraft spaceShipDraft;
    private ModuleActor moduleActor;
    private SpaceShipPlayer spaceShipPlayer;

    private Label moneyLabel;
    private static Label fuellLabel;
    private Label moneyLabelText;
    private Label titanLabelText;
    private Label grafenLabelText;
    private Label titanLabel;
    private Label grafelLabel;
    private Label worldLabel;
    private Label fuelLabelText;
    private ProgressBar fuelFillBar;
    protected Skin skin;
    private Skin skin2;
    private Table table;
    private Table tableCrows;
    private Label waterLabelText;
    private Label waterlLabel;

    private Window windowShipModuleInfo;
    private Table box;
    private GameScreen gameScreen;


    public SpaceShipScreen(final MyGdxGame game, final SpaceShipPlayer spaceShipPlayer, final GameScreen gameScreen) {
        super(game);
        this.spaceShipPlayer = spaceShipPlayer;
        this.gameScreen = gameScreen;
        game.kolejka.addFirst(this);

        DecimalFormat df = new DecimalFormat("#.##" + " kT");

        isVisible = true;
        skin = new Skin(Gdx.files.internal(FILE_EARTH_SKIN));
        skin2 = new Skin(Gdx.files.internal(FILE_UI_SKIN));
        initSpaceShipDraft();
        intSpaceShipModuleActors(spaceShipPlayer);
        createTableWithCargo();
        createTableCrows();
    }

    private void initSpaceShipDraft() {
        spaceShipDraft = new SpaceShipDraft(game, gameScreen);
        stage.addActor(spaceShipDraft);
    }


    private void intSpaceShipModuleActors(final SpaceShipPlayer spaceShipPlayer) {
        for(int i = 1; i <= 10 ; i++){
                int index = i;
                ShipModule shipModule = spaceShipPlayer.shipModules.get(i);
                moduleActor = new ModuleActor(this, shipModule , index, spaceShipDraft.getScale());
                stage.addActor(moduleActor);
        }

    }

    @Override
    public void render(float delta){
        if (isVisible) {
            super.render(delta);
            update();
            spriteBatch.begin();
            stage.draw();
            spriteBatch.end();
        }
    }
    private void update() {
            stage.act();
    }

    @Override
    public void hide(){
        isVisible = false;
    }

    @Override
    public void show(){
        isVisible = true;
    }

    @Override
    public void dispose(){

    }

    private void createTableCrows() {

        List<Table> crowTables = new ArrayList<>();
        box = new Table();
        tableCrows = new Table();

        tableCrows.bottom().center().pad(10f);
        tableCrows.setFillParent(false);

        tableCrows.setColor(255,1,1,255);

        int personsNumber = spaceShipPlayer.persons.size();

        for (int tn=0; tn<personsNumber; tn++) {

            crowTables.add(new Table());

            crowTables.get(tn).row().pad(5);
            crowTables.get(tn).add(new Label("Specjalist:", skin));
            crowTables.get(tn).add(new Label(spaceShipPlayer.persons.get(tn).getCrowType().name(), skin));
            crowTables.get(tn).row().pad(5);
            crowTables.get(tn).add(new Label("Name:", skin));
            crowTables.get(tn).add(new Label(spaceShipPlayer.persons.get(tn).getName(), skin));
            crowTables.get(tn).row().pad(5);
            crowTables.get(tn).add(new Label("Age:", skin));
            crowTables.get(tn).add(new Label(""+spaceShipPlayer.persons.get(tn).getAge()+" y", skin));
            crowTables.get(tn).row().pad(5);
            crowTables.get(tn).add(new Label(spaceShipPlayer.persons.get(tn).getFirstExperienceLevel().getExperienceType().name(), skin));
            crowTables.get(tn).add(new Label(Float.toString(spaceShipPlayer.persons.get(tn).getFirstExperienceLevel().getLevel()), skin));
            crowTables.get(tn).row().pad(5);
            crowTables.get(tn).add(new Label(spaceShipPlayer.persons.get(tn).getSecondExperienceLevel().getExperienceType().name(), skin));
            crowTables.get(tn).add(new Label(Float.toString(spaceShipPlayer.persons.get(tn).getSecondExperienceLevel().getLevel()), skin));

            box.add(crowTables.get(tn));
        }
        box.setPosition(200, 100);
        stage.addActor(box);
    }

    private void createTableWithCargo() {

        table = new Table();

        table.top().pad(10f);
        //table.debug();
        //make the table fill the entire stage
        table.setFillParent(false);
        table.setPosition(5, MyGdxGame.GAME_HEIGHT);
        table.setWidth(200f);
        table.setColor(255,1,1,255);


        moneyLabelText = new Label("Money", skin2);
        fuelLabelText = new Label("Fuel: ", skin2, "fuel");


        worldLabel = new Label("WORLD: ", skin2);


        //define our labels using the String, and a Label style consisting of a font and color
        moneyLabel = new Label(String.format("%.2f", spaceShipPlayer.getMoney()), skin2);
        fuellLabel =new Label(String.format("%.2f", spaceShipPlayer.getFill(CargoType.FUEL)), skin2, "fuel");


        //fuelFillBar = new ProgressBar(0.0f, (float) spaceShipPlayer.fuelCapacity, 1, false, skin, "fancy");

        //table.row();
        table.add(moneyLabelText).expandX().left();
        table.add(moneyLabel).expandX();

        table.row();
        table.add(fuelLabelText).expandX().left();
        table.add(fuellLabel).expandX();

        table.row();
        table.add(new Label("Speed: ",skin2)).expandX().left();
        table.add(new Label(String.format("%.2f", spaceShipPlayer.spaceShipEngine.getSpeedActual()), skin2)).expandX();


        if (spaceShipPlayer.getFill(CargoType.TITAN) > 0) {

            titanLabelText = new Label("Titan:", skin2, "titan");
            titanLabel = new Label(String.format("%.2f", spaceShipPlayer.getFill(CargoType.TITAN)), skin2, "titan");

            table.row();
            table.add(titanLabelText).expandX().left();
            table.add(titanLabel).expandX();
        }
        if (spaceShipPlayer.getFill(CargoType.GRAFEN) > 0) {

            grafenLabelText = new Label("Grafen", skin2, "grafen");
            grafelLabel = new Label(String.format("%.2f",spaceShipPlayer.getFill(CargoType.GRAFEN)) ,skin2, "grafen");

            table.row();
            table.add(grafenLabelText).expandX().left();
            table.add(grafelLabel).expandX();
        }

        if (spaceShipPlayer.getFill(CargoType.WATER) > 0) {

            waterLabelText = new Label("Water", skin2, "water");
            waterlLabel = new Label(String.format("%.2f",spaceShipPlayer.getFill(CargoType.WATER)) ,skin2, "water");

            table.row();
            table.add(waterLabelText).expandX().left();
            table.add(waterlLabel).expandX();
        }

        if (spaceShipPlayer.getFill(CargoType.HELIUM3) > 0) {

            waterLabelText = new Label("Helium 3", skin2, "water");
            waterlLabel = new Label(String.format("%.2f",spaceShipPlayer.getFill(CargoType.HELIUM3)) ,skin2, "helium3");

            table.row();
            table.add(waterLabelText).expandX().left();
            table.add(waterlLabel).expandX();
        }

        if (spaceShipPlayer.getFill(CargoType.QUICKSILVER) > 0) {

            waterLabelText = new Label("Quicksilver", skin2, "water");
            waterlLabel = new Label(String.format("%.2f",spaceShipPlayer.getFill(CargoType.QUICKSILVER)) ,skin2, "silver");

            table.row();
            table.add(waterLabelText).expandX().left();
            table.add(waterlLabel).expandX();
        }

        stage.addActor(table);

        //stage.addActor(fuelFillBar);

    }


    public void createWindowShipModuleInfo(ShipModule shipModule){


        windowShipModuleInfo = new Window(shipModule.getModuleType().name(), skin2);
        TextButton btnClose = new TextButton("Close", skin2);
        TextButton btnSale = new TextButton("Sale", skin2);
        Table table = new Table();

        table.align(1);
        table.top();

        table.row();
        table.add(new Label("Name: ", skin2)).left().pad(5);
        table.add(new Label(shipModule.name, skin2)).pad(5);

        table.row();
        table.add(new Label("Capacity: ", skin2)).left().pad(5);
        table.add(new Label(""+shipModule.getCapacity(), skin2)).pad(5);

        for (int i =0; i < CargoType.values().length; i++){
            if (shipModule.capacitys.containsKey(CargoType.values()[i])){
                table.row();
                table.add(new Label((CargoType.values()[i]).toString(), skin2, CargoType.values()[i].getStyleName())).left().pad(5);
                table.add(new Label(String.format("%.2f",shipModule.capacitys.get(CargoType.values()[i])), skin2, CargoType.values()[i].getStyleName())).pad(5);
                //
            }
        }

        table.row();
        table.add(new Label("Fill: ", skin2)).left().pad(5);
        table.add(new Label(String.format("%.2f",shipModule.getFill()), skin2)).pad(5);

        table.row();
        table.add(new Label("Base failure rate: ", skin2)).left().pad(5);
        table.add(new Label(""+shipModule.baseFailureRate, skin2)).pad(5);

        table.row();
        table.add(new Label("Factor failure rate: ", skin2)).left().pad(5);
        table.add(new Label(""+shipModule.experienceLevel, skin2)).pad(5);

        table.row();
        table.add(new Label("Price for sel: ", skin2)).left().pad(5);
        table.add(new Label(""+shipModule.cost * 0.8, skin2)).pad(5);

        if (spaceShipPlayer.isRun()) {
            table.row().colspan(2).pad(10);
            table.add(btnClose);
        }else
        {
            table.row().pad(10);
            table.add(btnClose);
            table.add(btnSale);
        }

        windowShipModuleInfo.setSize(300,400);
        windowShipModuleInfo.setPosition(GAME_WIDTH / 2.0f - 100, GAME_HEIGHT / 2.0f - 100);
        windowShipModuleInfo.add(table).grow().pad(5);


        stage.addActor(windowShipModuleInfo);

        btnClose.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                windowShipModuleInfo.remove();
                //gameScreen.multiplexer.removeProcessor(stage);
            }
        });
    }



    public void createWindowBayNewModule(final int index){

        List <Table> tables = new ArrayList<>();
        final List <ShipModule> moduleList = ReadXML.readListModuleFromXML(index);

        windowShipModuleInfo = new Window("Choice the module", skin2);
        TextButton btnClose = new TextButton("Close", skin2);

        Table box = new Table();
        box.debug();

        ScrollPane scrollPane = new ScrollPane(box, skin2);

        for (int itable=0; itable <moduleList.size(); itable++) {

            tables.add(table = new Table());

            box.row();

            box.add(tables.get(itable));

            table.align(1);
            table.top();

            table.row();
            table.add(new Label("Module Type: ", skin2)).left().pad(5);
            table.add(new Label(moduleList.get(itable).getModuleType().name(), skin2)).pad(5);

            table.row();
            table.add(new Label("Name: ", skin2)).left().pad(5);
            table.add(new Label(moduleList.get(itable).name, skin2)).pad(5);

            table.row();
            table.add(new Label("Capacity: ", skin2)).left().pad(5);
            table.add(new Label("" + moduleList.get(itable).getCapacity(), skin2)).pad(5);

            table.row();
            table.add(new Label("Base failure rate: ", skin2)).left().pad(5);
            table.add(new Label("" + moduleList.get(itable).baseFailureRate, skin2)).pad(5);

            table.row();
            table.add(new Label("Cost: ", skin2)).left().pad(5);
            table.add(new Label("" + moduleList.get(itable).cost, skin2)).pad(5);

            table.row().colspan(2).pad(5);
            final TextButton tbBuy = new TextButton("Buy", skin2);
            tbBuy.setName(Integer.toString(itable));
            table.add(tbBuy).expand().pad(5);


            tbBuy.addListener(new ClickListener() {
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    int listIndex = Integer.valueOf(tbBuy.getName());
                    spaceShipPlayer.bayModule(index,
                            moduleList.get(listIndex).getModuleType(),
                            moduleList.get(listIndex).name,
                            moduleList.get(listIndex).getCapacity(),
                            moduleList.get(listIndex).cost,
                            moduleList.get(listIndex).baseFailureRate);
                    changeModuleActor();
                    windowShipModuleInfo.remove();
                    //gameScreen.multiplexer.removeProcessor(stage);
                }
            });

        }
            table.row().colspan(2).pad(10);
            table.add(btnClose);


            windowShipModuleInfo.setSize(400, 400);
            windowShipModuleInfo.setPosition(GAME_WIDTH / 2.0f - 100, GAME_HEIGHT / 2.0f - 100);
            windowShipModuleInfo.add(scrollPane).grow().pad(5);
            //windowShipModuleInfo.add(tbButton).grow().pad(5);

            stage.addActor(windowShipModuleInfo);
/**

**/
        btnClose.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                windowShipModuleInfo.remove();
                //gameScreen.multiplexer.removeProcessor(stage);
            }
        });
        }

        private void changeModuleActor(){
        stage.clear();
        box.clear();
            initSpaceShipDraft();
            intSpaceShipModuleActors(spaceShipPlayer);
            createTableWithCargo();
            createTableCrows();
        }
    }

