package com.lucasdnd.onepix;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepix.gameplay.Player;
import com.lucasdnd.onepix.gameplay.World;
import com.lucasdnd.onepix.ui.SideBar;

public class OnePix extends ApplicationAdapter {
	
	public final static String GAME_NAME = "One Pixel";
	public final static String VERSION = "v0.1.0";
	boolean debug = true;
	
	public static float PIXEL_SIZE = 8f;
	private final float MIN_PIXEL_SIZE = 2f;
	private final float MAX_PIXEL_SIZE = 32f;
	
	World world;
	Player player;
	SideBar sideBar;
	
	InputHandler input;
	OrthographicCamera camera;
	ShapeRenderer shapeRenderer;
	ShapeRenderer uiShapeRenderer;
	
	SpriteBatch fontBatch;
	
	@Override
	public void create () {
		
		// Render, camera
		shapeRenderer = new ShapeRenderer();
		uiShapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		fontBatch = new SpriteBatch();
		
		// Input
		input = new InputHandler();
		Gdx.input.setInputProcessor(input);

		// Game objects
		int worldSize = 100;
		world = new World(worldSize);
		Random r = new Random();
		player = new Player(r.nextInt(worldSize), r.nextInt(worldSize));
		
		int sideBarWidth = 400;
		sideBar = new SideBar(Gdx.graphics.getWidth() - sideBarWidth, 0, sideBarWidth);
	}
	
	private void handleInput() {
		
		input.delay++;
		if (input.delay < input.maxDelay) {
			return;
		} else {
			input.delay = input.maxDelay;
		}
		
		if (input.wPressed) {
			if (player.canMoveUp(world)) {
				player.moveUp();
				input.delay = 0;
			}
		}
		if (input.sPressed) {
			if (player.canMoveDown(world)) {
				player.moveDown();
				input.delay = 0;
			}
		}
		if (input.aPressed) {
			if (player.canMoveLeft(world)) {
				player.moveLeft();
				input.delay = 0;
			}
		}
		if (input.dPressed) {
			if (player.canMoveRight(world)) {
				player.moveRight();
				input.delay = 0;
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.EQUALS)) {
			PIXEL_SIZE *= 2f;
			if (PIXEL_SIZE >= MAX_PIXEL_SIZE) {
				PIXEL_SIZE = MAX_PIXEL_SIZE;
			}
		} else if (Gdx.input.isKeyJustPressed(Keys.MINUS)) {
			PIXEL_SIZE /= 2f;
			if (PIXEL_SIZE <= MIN_PIXEL_SIZE) {
				PIXEL_SIZE = MIN_PIXEL_SIZE;
			}
		}
	}
	
	private void update() {
		
		handleInput();
		
		camera.position.set(player.getX() * OnePix.PIXEL_SIZE + (sideBar.getWidth() * 0.5f), player.getY() * OnePix.PIXEL_SIZE, 0f);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		world.update();
		player.update();
	}

	@Override
	public void render () {
		this.update();
		
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.render(shapeRenderer);
		player.render(shapeRenderer);
		sideBar.render(uiShapeRenderer);
		
		// Debug
		if (debug) {
			fontBatch.begin();
			Resources.get().whiteFont.draw(fontBatch, "w: " + input.wPressed, 0f, Gdx.graphics.getHeight());
			Resources.get().whiteFont.draw(fontBatch, "a: " + input.aPressed, 0f, Gdx.graphics.getHeight() - 20f);
			Resources.get().whiteFont.draw(fontBatch, "s: " + input.sPressed, 0f, Gdx.graphics.getHeight() - 40f);
			Resources.get().whiteFont.draw(fontBatch, "d: " + input.dPressed, 0f, Gdx.graphics.getHeight() - 60f);
			Resources.get().whiteFont.draw(fontBatch, "delay: " + input.delay, 0f, Gdx.graphics.getHeight() - 100f);
			fontBatch.end();
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public World getWorld() {
		return world;
	}
}
