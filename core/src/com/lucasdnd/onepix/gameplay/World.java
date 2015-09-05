package com.lucasdnd.onepix.gameplay;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class World {
	
	private int size;
	private int [][] map;
	
	public World(int size) {
		this.size = size;
		map = new int[size][size];
	}

	public void update() {
		
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.FOREST);
		sr.rect(0f, 0f, size, size);
		sr.end();
	}
	
}
