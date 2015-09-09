package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.Player;
import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.items.Stone;
import com.lucasdnd.onepixel.gameplay.items.Wood;
import com.lucasdnd.onepixel.ui.SideBar;

public class World implements Disposer {

	private Random r;

	private int size;
	private MapObject[][] mapObjects;

	// World settings
	int numTrees;

	// Terrain levels
	int archipelago = 100;
	int islands = 120;
	int continents = 140;
	int greatLakes = 180;
	int plains = 200;

	int seaLevel = islands;

	// Mountain and water levels
	int mountainLevel = seaLevel + 20;	// This is basically how much land there will be between water and mountains.
										// Lowering this value will generate much larger mountains at the expanse of land.
	int waterLevel = seaLevel - 20;

	public World() {

		size = 128;
		mapObjects = new MapObject[size][size];

		r = new Random();
		PerlinNoise perlin = new PerlinNoise(r.nextInt());

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
				
				int k = roundedValue;
				
				if (k > maxK) {
					maxK = k;
				} else if (k < minK) {
					minK = k;
				}
				
				if (k > mountainLevel) {
					mapObjects[i][j] = new Rock(this, i, j, 0);
				} else if (k <= mountainLevel && k > seaLevel) {

				} else if (k <= seaLevel && k > waterLevel) {
					mapObjects[i][j] = new Water(this, i, j);
				} else {
					mapObjects[i][j] = new DeepWater(this, i, j);
				}
			}
		}
		
		// Add Trees
		numTrees = size * size / 32;
		for (int i = 0; i < numTrees; i++) {
			int x = r.nextInt(size);
			int y = r.nextInt(size);
			if (mapObjects[x][y] == null) {
				Tree tree = new Tree(this, x, y);
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

		// Calculate the visible world objects
		// so we clip the view and prevent rendering shit that wouldn't even be visible to the player
		int minRenderX = 0;
		int maxRenderX = 0;
		int minRenderY = 0;
		int maxRenderY = 0;
		
		// Get the visible area on the screen
		SideBar sideBar = ((OnePixel)Gdx.app.getApplicationListener()).getSideBar();
		float gameViewWidth = Gdx.graphics.getWidth() - sideBar.getWidth();
		float visibleBlocksX = gameViewWidth / OnePixel.PIXEL_SIZE;
		float visibleBlocksY = Gdx.graphics.getHeight() / OnePixel.PIXEL_SIZE;
		
		// Get the Player's position
		Player player = ((OnePixel)Gdx.app.getApplicationListener()).getPlayer();
		int centerX = player.getX();
		int centerY = player.getY();
		
		// Calculate the number of visible blocks in each direction
		minRenderX = (int)(centerX - visibleBlocksX / 2f);
		maxRenderX = (int)(centerX + visibleBlocksX / 2f);
		minRenderY = (int)(centerY - visibleBlocksY / 2f);
		maxRenderY = (int)(centerY + visibleBlocksY / 2f);
		
		// Bounds check
		if (minRenderX < 0) {
			minRenderX = 0;
		}
		if (maxRenderX >= size) {
			maxRenderX = size;
		}
		if (minRenderY < 0) {
			minRenderY = 0;
		}
		if (maxRenderY >= size) {
			maxRenderY = size;
		}
		
		// World objects
		for (int i = minRenderX; i < maxRenderX; i++) {
			for (int j = minRenderY; j < maxRenderY; j++) {

				MapObject mapObject = mapObjects[i][j];

				if (mapObject instanceof Tree) {
					((Tree) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
				} else if (mapObject instanceof WoodBlock) {
					((WoodBlock) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
				} else if (mapObject instanceof Rock) {
					((Rock) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
				} else if (mapObject instanceof Water) {
					((Water) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
				} else if (mapObject instanceof DeepWater) {
					((DeepWater) mapObject).render(sr, i * OnePixel.PIXEL_SIZE, j * OnePixel.PIXEL_SIZE);
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

	public MapObject getMapObjectAt(int targetX, int targetY) {
		if (targetX < 0 || targetY < 0) {
			return null;
		} else if (targetX >= size || targetY >= size) {
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
	public MapObject exchange(Item item, int x, int y) {
		if (item instanceof Wood) {
			return new WoodBlock(this, x, y);
		} else if (item instanceof Stone) {
			return new Rock(this, x, y, 1);
		}

		return null;
	}

	@Override
	public void dispose(MapObject mapObject) {
		mapObjects[mapObject.x][mapObject.y] = null;
	}
}
