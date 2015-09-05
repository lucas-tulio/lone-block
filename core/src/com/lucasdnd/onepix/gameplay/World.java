package com.lucasdnd.onepix.gameplay;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class World {
	
	private Random r;
	
	private int size;
	private int [][] map;
	
	private final int TREE = 1;
	
	private int numTrees = 10000;
	
	public World(int size) {
		this.size = size;
		map = new int[size][size];
		r = new Random();
		
		// Generate trees
		for (int i = 0; i < numTrees; i++) {
			map[r.nextInt(size)][r.nextInt(size)] = TREE;
		}
	}

	public void update() {
		
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.FOREST);
		sr.rect(0f, 0f, size, size);
		
		// World objects
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][j] == TREE) {
					sr.setColor(new Color(0f, 0.3f, 0f, 1f));
					sr.rect(i, j, 1f, 1f);
				}
			}
		}
		
		sr.end();
	}

	public int[][] getMap() {
		return map;
	}
	
}
