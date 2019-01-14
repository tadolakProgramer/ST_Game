package com.todal.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.todal.game.Helper.MackeXML;
import com.todal.game.Screenns.CreatePlayerScreen;
import com.todal.game.Screenns.GameScreen;
import com.todal.game.Screenns.SpalshScreen;



public class MyGdxGame extends Game {



	public final static  String GAME_NAME = "Star Trader";
	public final static  int GAME_WIDTH = 1920;
	public final static  int GAME_HEIGHT = 1080;
	public static float SPACE_OBJECT_SCALIBG = 1;
	public final static float GAME_SCALE = 0.25f;
	public final static float SCROLL_SPEED =10f;

	//Files
	public final static String FILE_SPRITE_ATLAS = "data/planets.pack";
	public final static String FILE_CARGO_ATLAS = "data/cargo.atlas";
	public final static String FILE_SPACESHIP = "data/SpaceShip_Empty.png";
	public final static String FILE_SPACE_SHIP = "data/spaceship.xml";
	public final static String FILE_PLAYER = "data/player.xml";
	public final static String FILE_PLANETS = "data/cars.xml";
	public final static String FILE_SHIP_MODULES = "data/spacemodule.xml";
	//Skins
	public final static String FILE_EARTH_SKIN = "data/skin/flat-earth-ui.json";
	public final static String FILE_UI_SKIN = "data/skin/uiskin.json";

	//
	public final static int TIME_TO_PAYMENT = 300; /* Time to payment in s real time*/

	//
	public  Skin skin;

	public static String DIR;
	public Deque<Screen> kolejka = new ArrayDeque<Screen>();
	public BitmapFont myFont;
	public AssetManager assetManager;
	public TextureAtlas textureAtlas;
	public String filePath;

	private boolean paused;
	private boolean Jest;
	public Screen gameScreen;
	private float gameScale;

	public static String getLoclaPath(){

		String path;

		switch(Gdx.app.getType()) {

			case Android: {
				path = Gdx.files.getLocalStoragePath();
				break;
			}
			case Desktop: {
				path = "";
				break;
			}
			default: return "";
		}
		return path;
	}

	public float getGameScale() {
		return gameScale;
	}

	public void setGameScale(float gameScale) {
		this.gameScale = gameScale;
	}

	@Override
	public void create() {


		assetManager = new AssetManager();
		textureAtlas = new TextureAtlas(FILE_SPRITE_ATLAS);
		skin = new Skin(Gdx.files.internal(FILE_EARTH_SKIN));

		assetManager.load(FILE_SPRITE_ATLAS, TextureAtlas.class);

		myFont = new BitmapFont(Gdx.files.internal("fonts/xspace32.fnt"));
		myFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear,
				Texture.TextureFilter.Linear);

		switch(Gdx.app.getType()) {
			case Android:{
				String filePath = getLoclaPath();
				boolean isDirectory = Gdx.files.local("data/").isDirectory();
				FileHandle file = Gdx.files.local("data/");
				file.mkdirs();
				boolean isDirectory1 = Gdx.files.local("data/").isDirectory();
				if (!Gdx.files.local(filePath+FILE_PLAYER).exists()){
					System.out.print("Nie ma");
					try {
						MackeXML.createDocument(filePath);
						MackeXML.createSpaceship(filePath);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			// android specific code
			case Desktop:
				// desktop specific code
		}
		//this.gameScreen = new GameScreen(this);

		Jest = true;

		if (Jest) {
			System.out.println("Jest!");
			this.setScreen(new SpalshScreen(this));
		} else {
			System.out.println("Nie ma!");
			this.setScreen(new CreatePlayerScreen(this));
		}
	}

	@Override
	public void dispose () {
		System.out.println("Koniec");
		if (screen != null) screen.hide();
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

}