package com.lucasdnd.onepix;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepix.gameplay.Player;
import com.lucasdnd.onepix.gameplay.World;
import com.lucasdnd.onepix.ui.SideBar;

public class OnePix extends ApplicationAdapter {
	
	final float scale = 8f;
	World world;
	Player player;
	SideBar sideBar;
	
	InputHandler input;
	OrthographicCamera camera;
	ShapeRenderer shapeRenderer;
	ShapeRenderer uiShapeRenderer;
	
	@Override
	public void create () {
		
		// Render, camera
		shapeRenderer = new ShapeRenderer();
		uiShapeRenderer = new ShapeRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 1f/scale;
		
		// Input
		input = new InputHandler();
		Gdx.input.setInputProcessor(input);

		// Game objects
		int worldSize = 1024;
		world = new World(worldSize);
		Random r = new Random();
		player = new Player(r.nextInt(worldSize), r.nextInt(worldSize));
		
		int sideBarWidth = 400;
		sideBar = new SideBar(Gdx.graphics.getWidth() - sideBarWidth, 0, sideBarWidth);
	}
	
	private void update() {
		
		camera.position.set(player.getX() + (sideBar.getWidth() * 0.5f * 1f/scale), player.getY(), 0f);
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
		
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public World getWorld() {
		return world;
	}
}
