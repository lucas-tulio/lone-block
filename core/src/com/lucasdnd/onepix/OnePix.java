package com.lucasdnd.onepix;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepix.gameplay.Player;
import com.lucasdnd.onepix.gameplay.World;
import com.lucasdnd.onepix.ui.SideBar;

public class OnePix extends ApplicationAdapter {
	
	final float scale = 8f;
	World world;
	Player player;
	SideBar sideBar;
	
	OrthographicCamera camera;
	ShapeRenderer shapeRenderer;
	ShapeRenderer uiShapeRenderer;
	BitmapFont font;
	
	@Override
	public void create () {
		
		// Game control stuff
		shapeRenderer = new ShapeRenderer();
		uiShapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 1f/scale;

		// Game objects
		int worldSize = 1000;
		world = new World(worldSize);
		player = new Player(worldSize / 2, worldSize / 2);
		sideBar = new SideBar(400);
	}
	
	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.W)) {
			player.moveUp();
		} else if (Gdx.input.isKeyJustPressed(Keys.A)) {
			player.moveLeft();
		} else if (Gdx.input.isKeyJustPressed(Keys.S)) {
			player.moveDown();
		} else if (Gdx.input.isKeyJustPressed(Keys.D)) {
			player.moveRight();
		}
	}
	
	private void update() {
		
		handleInput();
		
		camera.position.set(player.getX() + (sideBar.getWidth() * 0.5f * 1f/scale), player.getY(), 0f);
		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		
		world.update();
		player.update();
	}

	@Override
	public void render () {
		this.update();
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.render(shapeRenderer);
		player.render(shapeRenderer);
		sideBar.render(uiShapeRenderer);
		
	}
}
