package com.lucasdnd.onepixel.gameplay.world;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.Monster;
import com.lucasdnd.onepixel.gameplay.Player;
import com.lucasdnd.onepixel.gameplay.items.Campfire;
import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.items.Sapling;
import com.lucasdnd.onepixel.gameplay.items.Stone;
import com.lucasdnd.onepixel.gameplay.items.Wood;
import com.lucasdnd.onepixel.ui.SideBar;

public class World implements Disposer {
	
	private Random r;

	private int size;
	public static final int SMALL = 512;
	public static final int NORMAL = 1024;
	public static final int LARGE = 4096;
	private MapObject[][] mapObjects;
	
	// World objects that need to be updated()
	private ArrayList<Tree> trees;
	private ArrayList<Monster> monsters;

	// World settings
	private int numTrees;

	// Terrain levels
	private int archipelago = 100;
	private int islands = 120;
	private int continents = 140;
	private int greatLakes = 180;
	private int plains = 200;

	private int seaLevel = islands;

	// Mountain and water levels
	private int mountainLevel = seaLevel + 20;	// This is basically how much land there will be between water and mountains.
										// Lowering this value will generate much larger mountains at the expanse of land.
	private int waterLevel = seaLevel - 20;

	/**
	 * New Game constructor
	 * @param size
	 */
	public World(int size) {

		this.size = size;
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
					mapObjects[i][j] = new Rock(this, i, j);
				} else if (k <= mountainLevel && k > seaLevel) {

				} else if (k <= seaLevel && k > waterLevel) {
					mapObjects[i][j] = new Water(this, i, j);
				} else {
					mapObjects[i][j] = new DeepWater(this, i, j);
				}
			}
		}
		
		// TODO: problem
		// I can't have an ArrayList of Trees and at the same time keep them in mapObjects[][]. That's bad
		
		// Add Trees
		trees = new ArrayList<Tree>();
		numTrees = size * size / 32;
		for (int i = 0; i < numTrees; i++) {
			int x = r.nextInt(size);
			int y = r.nextInt(size);
			if (mapObjects[x][y] == null) {
				Tree tree = new Tree(this, x, y, true);
				trees.add(tree);
				mapObjects[x][y] = tree;
			}
		}
		
		// Create monsters
		monsters = new ArrayList<Monster>();
		int numMonsters = 3;
		for (int i = 0; i < numMonsters; i++) {
			monsters.add(new Monster());
		}
	}
	
	/**
	 * Load Game constructor
	 * @param mapObjects
	 */
	public World() {
		monsters = new ArrayList<Monster>();
	}
	
	public void update() {
		// Update map objects
		for (Tree t : trees) {
			t.update();
		}
		for (Monster m : monsters) {
			m.update();
		}
	}

	public void render(ShapeRenderer sr) {
		
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.FOREST);
		sr.rect(0f, 0f, size * OnePixel.blockSize, size * OnePixel.blockSize);

		// Calculate the visible world objects
		// so we clip the view and prevent rendering shit that wouldn't even be visible to the player
		int minRenderX = 0;
		int maxRenderX = 0;
		int minRenderY = 0;
		int maxRenderY = 0;
		
		// Get the visible area on the screen
		float gameViewWidth = Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH;
		float visibleBlocksX = gameViewWidth / OnePixel.blockSize;
		float visibleBlocksY = Gdx.graphics.getHeight() / OnePixel.blockSize;
		
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
				if (mapObject != null) {
					mapObject.render(sr, i * OnePixel.blockSize, j * OnePixel.blockSize);
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
			return new Rock(this, x, y);
		} else if (item instanceof Campfire) {
			return new CampfireBlock(this, x, y);
		} else if (item instanceof Sapling) {
			return new Tree(this, x, y, false);
		}

		return null;
	}
	
	@Override
	public void dispose(MapObject mapObject) {
		mapObjects[mapObject.x][mapObject.y] = null;
	}
	
	public ArrayList<Tree> getTrees() {
		return trees;
	}

	public void spawnMonsters() {
		for (Monster m : monsters) {
			m.spawn();
		}
	}
	
	/**
	 * Set the Map Objects after loading a game data from a save file
	 * @param mapObjects
	 * @param trees
	 */
	public void setMapObjects(MapObject[][] mapObjects, ArrayList<Tree> trees) {
		
		this.size = mapObjects.length;
		this.mapObjects = mapObjects;
		
		// Trees
		this.trees = trees;
		this.numTrees = trees.size();
		for (Tree t : trees) {
			mapObjects[t.x][t.y] = t;
		}
	}
}
