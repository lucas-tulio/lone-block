package com.lucasdnd.onepix;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepix.gameplay.Player;
import com.lucasdnd.onepix.gameplay.World;

public class OnePix extends ApplicationAdapter {
	
	World world;
	Player player;
	
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
		camera.zoom = 1f/8f;
		
		int worldSize = 100;
		world = new World(worldSize);
		player = new Player(worldSize / 2, worldSize / 2);
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
		
		camera.position.set(player.getX(), player.getY(), 0f);
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
		
		
	}
}
