package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;


public class World implements Disposer {
	
	private Random r;
	
	private int size;
	private final int depth = 10;
	private MapObject [][][] mapObjects;
	
	public static final int TREE = 1;
	
	private int numTrees = 10000;
	
	public World(int size) {
		this.size = size;
		mapObjects = new MapObject[size][size][depth];
		r = new Random();
		
		// Generate trees
		for (int i = 0; i < numTrees; i++) {
			int x = r.nextInt(size);
			int y = r.nextInt(size);
			int z = 0;
			Tree tree = new Tree(this, x, y, z);
			mapObjects[x][y][z] = tree;
		}
	}

	public void update() {
		
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.FOREST);
		sr.rect(0f, 0f, size * OnePixel.PIXEL_SIZE, size * OnePixel.PIXEL_SIZE);
		
		// World objects
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (mapObjects[i][j][0] instanceof Tree) {
					sr.setColor(new Color(0f, 0.3f, 0f, 1f));
					sr.rect(i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
				}
			}
		}
		
		sr.end();
	}

	public MapObject[][][] getMapObjects() {
		return mapObjects;
	}
	
	public int getSize() {
		return size;
	}

	public MapObject getMapObjectAt(int targetX, int targetY, int targetZ) {
		if (targetX < 0 || targetY < 0 || targetZ < 0) {
			return null;
		} else if (targetX >= size || targetY >= size || targetZ >= size) {
			return null;
		}
		
		return mapObjects[targetX][targetY][targetZ];
	}

	@Override
	public void dispose(MapObject mapObject) {
		mapObjects[mapObject.x][mapObject.y][mapObject.z] = null;
	}
}
