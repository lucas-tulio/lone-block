package com.lucasdnd.onepixel;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.gameplay.Player;
import com.lucasdnd.onepixel.gameplay.world.World;
import com.lucasdnd.onepixel.ui.ButtonClickListener;
import com.lucasdnd.onepixel.ui.DialogBox;
import com.lucasdnd.onepixel.ui.SideBar;
import com.lucasdnd.onepixel.ui.Tooltip;

public class OnePixel extends ApplicationAdapter {
	
	// General stuff
	public final static String GAME_NAME = "One Pixel";
	public final static String VERSION = "v0.5.0";
	private boolean debug = false;
	
	// Rendering, font
	private SpriteBatch fontBatch;
	private Tooltip tooltip;
	private FontUtils font;
	private static int zoomLevelDisplay = 1;	// The zoom level the player will see in the debug prints. I did this just to avoid doing log(x)/log(2) in every render call
	public static float blockSize = 8f;
	public static final float MIN_BLOCK_SIZE = 2f;
	public static final float MAX_BLOCK_SIZE = 32f;
	private int playableAreaWidth;
	private int playableAreaHeight;
	
	// Game objects
	private World world;
	private Player player;
	private TimeController timeController;
	
	// UI
	private SideBar sideBar;
	private DialogBox quitGameDialogBox;
	
	// Input, camera
	private InputHandler input;
	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private ShapeRenderer uiShapeRenderer;
	
	// Game states
	private boolean paused = false;
	
	private boolean justStarted = true;
	private boolean waiting = true;
	
	private boolean startingNewGame = false;
	private boolean savingGame = false;
	private boolean loadingGame = false;
	
	@Override
	public void create () {
		
		// Render, camera
		shapeRenderer = new ShapeRenderer();
 		uiShapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		fontBatch = new SpriteBatch();
		font = new FontUtils();
		
		// Input
		input = new InputHandler();
		Gdx.input.setInputProcessor(input);

		// UI
		sideBar = new SideBar(Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH, 0);
		playableAreaWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH;
		playableAreaHeight = Gdx.graphics.getHeight();
		tooltip = new Tooltip();
		
		// Quit game dialog box
		quitGameDialogBox = new DialogBox("Quit?");
		
		quitGameDialogBox.getYesButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				Gdx.app.exit();
			}
			
		});

		quitGameDialogBox.getNoButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				quitGameDialogBox.hide();
			}
			
		});
		
		// Side Bar Buttons
		sideBar.getNewGameButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				hideAllPanels();
				startNewGame();
			}
			
		});
		
		sideBar.getSaveGameButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				hideAllPanels();
				savingGame = true;
			}
			
		});
		
		sideBar.getLoadGameButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				hideAllPanels();
				loadingGame = true;
			}
			
		});
		
		sideBar.getQuitButton().setClickListener(new ButtonClickListener() {

			@Override
			public void onClick() {
				hideAllPanels();
				quitGame();
			}
			
		});
	}
	
	private static void updateZoomLevelDisplay() {
		OnePixel.zoomLevelDisplay = (int)(Math.log(blockSize) / Math.log(2));
	}
	
	private void hideAllPanels() {
		quitGameDialogBox.hide();
	}
	
	public void startNewGame() {
		startingNewGame = true;
	}
	
	public void saveGame() {
		savingGame = true;
	}
	
	public void loadGame() {
		loadingGame = true;
	}
	
	public void quitGame() {
		quitGameDialogBox.show();
	}
	
	private void handleInput() {
		
		if (paused == false) {
		
			// Movement delay
			input.movementDelay--;
			if (input.movementDelay <= 0) {
				input.movementDelay = 0;
			
				// Movement
				if (input.upPressed) {
					
					if (input.shiftPressed) {
						player.faceUp();
					} else {
						if (player.canMoveUp(world)) {
							player.moveUp();
							input.applyMovementDelay();
						}
						if (input.ctrlPressed == false) {
							player.faceUp();
						}
					}
				}
				if (input.downPressed) {
					
					if (input.shiftPressed) {
						player.faceDown();
					} else {
						if (player.canMoveDown(world)) {
							player.moveDown();
							input.applyMovementDelay();
						}
						if (input.ctrlPressed == false) {
							player.faceDown();
						}
					}
				}
				if (input.leftPressed) {
					
					if (input.shiftPressed) {
						player.faceLeft();
					} else {
						if (player.canMoveLeft(world)) {
							player.moveLeft();
							input.applyMovementDelay();
						}
						if (input.ctrlPressed == false) {
							player.faceLeft();
						}
					}
				}
				if (input.rightPressed) {
					
					if (input.shiftPressed) {
						player.faceRight();
					} else {
						if (player.canMoveRight(world)) {
							player.moveRight();
							input.applyMovementDelay();
						}
						if (input.ctrlPressed == false) {
							player.faceRight();
						}
					}
				}
			}
			
			// Action delay
			input.actionDelay--;
			if (input.actionDelay <= 0) {
				input.actionDelay = 0;

				// Action
				if (input.ePressed) {
					player.performAction(world);
					input.applyActionDelay();
				}
			}
			
			// Use delay
			input.useDelay --;
			if (input.useDelay <= 0) {
				input.useDelay = 0;
				
				// Use
				if (input.wPressed) {
					player.useItem(world);
					input.applyUseDelay();
				}
			}
			
			// Zoom control
			if (Gdx.input.isKeyJustPressed(Keys.EQUALS)) {
				blockSize *= 2f;
				if (blockSize >= MAX_BLOCK_SIZE) {
					blockSize = MAX_BLOCK_SIZE;
				}
				OnePixel.updateZoomLevelDisplay();
			} else if (Gdx.input.isKeyJustPressed(Keys.MINUS)) {
				blockSize /= 2f;
				if (blockSize <= MIN_BLOCK_SIZE) {
					blockSize = MIN_BLOCK_SIZE;
				}
				OnePixel.updateZoomLevelDisplay();
			}
		}
		
		// Pause
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE) || Gdx.input.isKeyJustPressed(Keys.P)) {
			paused = !paused;
		}
		
		// Debug
		if (Gdx.input.isKeyJustPressed(Keys.F3)) {
			debug = !debug;
		}
	}
	
	private void update() {
		
		// Just opened the game
		if (justStarted) {
			if (Resources.get().saveFile.exists()) {
				// If the player has a saved game, load it
				loadingGame = true;
			} else {
				// Otherwise, start a new one
				startingNewGame = true;
			}
			
			justStarted = false;
		}
		
		// Starting new game
		if (startingNewGame) {
			world = new World(World.NORMAL);
			player = new Player(world);
			world.spawnMonsters();
			timeController = new TimeController();
			paused = false;
			startingNewGame = false;
			waiting = false;
			OnePixel.updateZoomLevelDisplay();
		}
		
		// Will Save
		if (savingGame) {
			SaveLoad.save();
			paused = false;
			waiting = false;
			savingGame = false;
		}
		// Will Load
		if (loadingGame) {
			SaveLoad.load();
			paused = false;
			waiting = false;
			loadingGame = false;
			OnePixel.updateZoomLevelDisplay();
		}
		
		// Dialog box update
		if (quitGameDialogBox.isVisible()) {
			quitGameDialogBox.update();
		}
		
		// Normal game loop
		if (player.isDead() == false) {
			
			if (waiting == false) {
				handleInput();
			}
			
			if (paused == false) {
				camera.position.set(player.getX() * OnePixel.blockSize + (SideBar.SIDEBAR_WIDTH * 0.5f), player.getY() * OnePixel.blockSize, 0f);
				camera.update();
				shapeRenderer.setProjectionMatrix(camera.combined);
				
				if (waiting == false) {
					timeController.update();
					world.update();
					player.update();
				}
			}
			
			sideBar.getSaveGameButton().setEnabled(!waiting);
			sideBar.update();
			
			handleInputEnd();
		} else {
			
			// Dead
			sideBar.update();
		}
	}

	@Override
	public void render () {
		this.update();
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Starting new Game
		if (startingNewGame) {
			sideBar.render(uiShapeRenderer);
			String text = "Creating new world...";
			float space = Gdx.graphics.getWidth() / 2f - SideBar.SIDEBAR_WIDTH / 2f;
			font.drawWhiteFont(text, space, Gdx.graphics.getHeight() / 2f - 12f, true, Align.center, 0);
			return;
		}
		
		// Loading game
		if (loadingGame) {
			sideBar.render(uiShapeRenderer);
			String text = "Loading...";
			float space = Gdx.graphics.getWidth() / 2f - SideBar.SIDEBAR_WIDTH / 2f;
			font.drawWhiteFont(text, space, Gdx.graphics.getHeight() / 2f - 12f, true, Align.center, 0);
			return;
		}
		
		// Saving game
		if (savingGame) {
			sideBar.render(uiShapeRenderer);
			String text = "Saving...";
			float space = Gdx.graphics.getWidth() / 2f - SideBar.SIDEBAR_WIDTH / 2f;
			font.drawWhiteFont(text, space, Gdx.graphics.getHeight() / 2f - 12f, true, Align.center, 0);
			return;
		}
		
		// World, Player, Time
		world.render(shapeRenderer);
		if (waiting == false) {
			player.render(shapeRenderer);
			timeController.render(uiShapeRenderer);
		}
		
		// Day and night
		if (timeController.isNight()) {
			drawOverlay();
		}
		
		// Pause
		if (paused) {
			drawOverlay();
			font.drawWhiteFont("Paused", 0f, Gdx.graphics.getHeight() / 2f, true, Align.center, (int)((Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH)));
		}
		
		// UI
		sideBar.render(uiShapeRenderer);
		tooltip.render();
		quitGameDialogBox.render();
		
		if (player.isDead()) {
			font.drawRedFont("You died", 0f, Gdx.graphics.getHeight() / 2f, false, Align.center, Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH);
		}
		
		// Debug
		if (debug && waiting == false) {
			fontBatch.begin();
			Resources.get().whiteFont.draw(fontBatch, "x: " + player.getX(), 0f, Gdx.graphics.getHeight());
			Resources.get().whiteFont.draw(fontBatch, "y: " + player.getY(), 0f, Gdx.graphics.getHeight() - 20f);
			Resources.get().whiteFont.draw(fontBatch, "world size: " + world.getSize() + "x" + world.getSize(), 0f, Gdx.graphics.getHeight() - 40f);
			Resources.get().whiteFont.draw(fontBatch, "heating up: " + player.isHeatingUp(), 0f, Gdx.graphics.getHeight() - 60f);
			
			Resources.get().whiteFont.draw(fontBatch, "time: " + timeController.getTime(), 0f, Gdx.graphics.getHeight() - 100f);
			Resources.get().whiteFont.draw(fontBatch, "hour of day: " + timeController.getHourOfDay() + " (" + (int)(((float)timeController.getHourOfDay() / (float)TimeController.ONE_DAY)*100f) + "%)", 0f, Gdx.graphics.getHeight() - 120f);
			Resources.get().whiteFont.draw(fontBatch, "night: " + timeController.isNight(), 0f, Gdx.graphics.getHeight() - 140f);
			Resources.get().whiteFont.draw(fontBatch, "day: " + timeController.getDay(), 0f, Gdx.graphics.getHeight() - 160f);
			
			Resources.get().whiteFont.draw(fontBatch, "zoom: " + OnePixel.zoomLevelDisplay, 0f, Gdx.graphics.getHeight() - 200f);
			
			fontBatch.end();
		}
	}
	
	private void drawOverlay() {
		shapeRenderer.begin(ShapeType.Filled);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapeRenderer.setColor(0f, 0f, 0f, 0.3f);
		shapeRenderer.rect(
				player.getX() * blockSize - playableAreaWidth / 2,
				player.getY() * blockSize + playableAreaHeight / 2,
				playableAreaWidth,
				-playableAreaHeight);
		shapeRenderer.end();
	}
	
	public void handleInputEnd() {
		input.leftMouseJustClicked = false;
		input.rightMouseJustClicked = false;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public World getWorld() {
		return world;
	}

	public SideBar getSideBar() {
		return sideBar;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}
	
	public InputHandler getInputHandler() {
		return input;
	}
	
	public TimeController getTimeController() {
		return timeController;
	}

	public boolean isLoadingGame() {
		return loadingGame;
	}

	public void setLoadingGame(boolean loadingGame) {
		this.loadingGame = loadingGame;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setTimeController(TimeController timeController) {
		this.timeController = timeController;
	}
	
}
