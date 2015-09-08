package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.items.Stone;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class World implements Disposer {

	private Random r;

	private int size;
	private MapObject[][] mapObjects;

	// World settings
	private int numTrees = 10000;

	// Variation
	float increment = 0.01f;

	// Amount of Land
	int archipelago = 100;
	int islands = 120;
	int continents = 140;
	int greatLakes = 180;
	int plains = 200;

	int seaLevel = islands;

	// Mountain and water levels
	int mountainLevel = seaLevel + 20; // 50 to max (less is more)
	int waterLevel = seaLevel - 20;

	public World() {

		size = 512;
		mapObjects = new MapObject[size][size];

		r = new Random();
		PerlinNoise perlin = new PerlinNoise(r.nextInt());

		int[][] map = new int[size][size];
		int minK = 255;
		int maxK = 0;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				
				// Generate a noise value for the coordinate (x,y)
				float noiseValue = 0;
				noiseValue += perlin.scale256(perlin.interpolatedNoise(i * 0.01f, j * 0.01f));
				noiseValue += perlin.scale256(perlin.interpolatedNoise(i * 0.02f, j * 0.02f));
				noiseValue += perlin.scale256(perlin.interpolatedNoise(i * 0.04f, j * 0.04f));
				noiseValue += perlin.scale256(perlin.interpolatedNoise(i * 0.08f, j * 0.08f));
				int roundedValue = Math.round(noiseValue / 4f);
				
				map[i][j] = roundedValue;
				int k = map[i][j];
				
				if (k > maxK) {
					maxK = k;
				} else if (k < minK) {
					minK = k;
				}
				
				if (k > mountainLevel) {
					mapObjects[i][j] = new Rock(this, i, j, 0);
				} else if (k <= mountainLevel && k > seaLevel) {

				} else if (k <= seaLevel && k > waterLevel) {
					mapObjects[i][j] = new Water(this, i, j, 0, 15);
				} else {
					mapObjects[i][j] = new Water(this, i, j, 0, 5);
				}
			}
		}
		
		// Add Trees
		for (int i = 0; i < numTrees; i++) {
			int x = r.nextInt(size);
			int y = r.nextInt(size);
			int z = 0;
			if (mapObjects[x][y] == null) {
				Tree tree = new Tree(this, x, y, z);
				mapObjects[x][y] = tree;
			}
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

				MapObject mapObject = mapObjects[i][j];

				if (mapObject instanceof Tree) {
					((Tree) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
				} else if (mapObject instanceof WoodBlock) {
					((WoodBlock) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
				} else if (mapObject instanceof Rock) {
					((Rock) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
				} else if (mapObject instanceof Water) {
					((Water) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
				}
			}
		}

		sr.end();
	}

	public MapObject[][] getMapObjects() {
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

		return mapObjects[targetX][targetY];
	}

	/**
	 * Give it an inventory item, get a map block
	 * 
	 * @param item
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public MapObject exchange(Item item, int x, int y, int z) {
		if (item instanceof Wood) {
			return new WoodBlock(this, x, y, z);
		} else if (item instanceof Stone) {
			return new Rock(this, x, y, z, 1);
		}

		return null;
	}

	@Override
	public void dispose(MapObject mapObject) {
		mapObjects[mapObject.x][mapObject.y] = null;
	}
}
