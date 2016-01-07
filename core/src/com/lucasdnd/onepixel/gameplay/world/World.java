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
import com.lucasdnd.onepixel.gameplay.Point;
import com.lucasdnd.onepixel.gameplay.items.Campfire;
import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.items.Sapling;
import com.lucasdnd.onepixel.gameplay.items.Stone;
import com.lucasdnd.onepixel.gameplay.items.Wood;
import com.lucasdnd.onepixel.gameplay.world.pathfinder.AStarPathFinder;
import com.lucasdnd.onepixel.gameplay.world.pathfinder.PathFindingContext;
import com.lucasdnd.onepixel.gameplay.world.pathfinder.TileBasedMap;
import com.lucasdnd.onepixel.ui.SideBar;

public class World implements Disposer, TileBasedMap {
	
	private Random r;

	private int size;
	public class Size {
		public static final int small = 512;
		public static final int normal = 1024;
		public static final int large = 2048;
		public static final int numMonstersSmall = 5;
		public static final int numMonstersNormal = 20;
		public static final int numMonstersLarge = 80;
	}
	
	private MapObject[][] mapObjects;
	
	// Pathfinding
	private int monsterDetectionRange = 45;
	private AStarPathFinder pathFinder;
	
	// World objects that need to be updated()
	private ArrayList<Tree> trees;
	private ArrayList<Monster> monsters;

	// World settings
	private int numTrees;

	/**
	 * Terrain settings
	 */
	private final int land = 120;
	private final int mountainOffset = 20;
	private final int waterOffset = 20;
	private final int mountainLevel = land + mountainOffset;
	private final int waterLevel = land - waterOffset;

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
				} else if (k <= mountainLevel && k > land) {

				} else if (k <= land && k > waterLevel) {
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
		
		// Pathfinder
		pathFinder = new AStarPathFinder(this, monsterDetectionRange, false);
		
		// Create monsters
		monsters = new ArrayList<Monster>();
		int numMonsters = 0;
		if (size == Size.small) {
			numMonsters = Size.numMonstersSmall;
		} else if (size == Size.normal) {
			numMonsters = Size.numMonstersNormal;
		} else if (size == Size.large) {
			numMonsters = Size.numMonstersLarge;
		}
		ArrayList<Point> spawnPoints = getRandomAvailableSpawnPoints(numMonsters);
		for (int i = 0; i < numMonsters; i++) {
			int maxChaseTicks;
			int monsterType = new Random().nextInt(2);
			if (monsterType == 0) {
				maxChaseTicks = 6;
			} else if (monsterType == 1) {
				maxChaseTicks = 7;
			} else {
				maxChaseTicks = 9;
			}
			Monster m = new Monster(maxChaseTicks * 8, maxChaseTicks);
			monsters.add(m);
			m.spawn(spawnPoints.get(i));
		}
	}
	
	/**
	 * Load game constructor
	 */
	public World(int size, boolean isLoadGame) {
		this.size = size;
		monsters = new ArrayList<Monster>();
		pathFinder = new AStarPathFinder(this, monsterDetectionRange, false);
	}
	
	public void update() {
		// Update map objects
		for (Tree t : trees) {
			t.update();
		}
		for (Monster m : monsters) {
			m.update(pathFinder, monsterDetectionRange);
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
		minRenderX = (int)Math.floor(centerX - visibleBlocksX / 2f);
		maxRenderX = (int)Math.ceil(centerX + visibleBlocksX / 2f);
		minRenderY = (int)Math.floor(centerY - visibleBlocksY / 2f);
		maxRenderY = (int)Math.ceil(centerY + visibleBlocksY / 2f);
		
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
		
		// Mobs
		for (Monster m : monsters) {
			m.render(sr);
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
	
	/**
	 * Set the monsters after loading a game from a save file
	 * @param monsters
	 */
	public void setMonsters(ArrayList<Monster> monsters) {
		this.monsters = monsters;
	}
	
	/**
	 * Returns a random available spawn point
	 * @return
	 */
	public ArrayList<Point> getRandomAvailableSpawnPoints(int num) {
		
		final int skip = 100; // Check every x tiles
		
		ArrayList<Point> spawnPoints = new ArrayList<Point>();
		for (int i = 0; i < size; i += skip) {
			for (int j = 0; j < size; j += skip) {
				if (getMapObjectAt(i, j) == null) {
					spawnPoints.add(new Point(i, j));
				}
			}
		}

		if (spawnPoints.size() == 0) {
			System.out.println("no spawn points available");
			Gdx.app.exit();
		}
		
		// Check for wrong spawn points
		for (Point sp : spawnPoints) {
			if (sp.x == -1 || sp.y == -1) {
				System.out.println("wrong spawn point: x = " + sp.x + ", y = " + sp.y);
			}
		}
		
		ArrayList<Point> result = new ArrayList<Point>();
		for (int i = 0; i < num; i++) {
			result.add(spawnPoints.get(new Random().nextInt(spawnPoints.size())));
		}
		return result;
	}

	@Override
	public int getWidthInTiles() {
		return size;
	}

	@Override
	public int getHeightInTiles() {
		return size;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * If there's something on that tile, then it's blocked.
	 */
	@Override
	public boolean blocked(PathFindingContext context, int tx, int ty) {
		return mapObjects[tx][ty] != null;
	}

	/**
	 * Since there's no cost difference between the tiles, always return 1.
	 */
	@Override
	public float getCost(PathFindingContext context, int tx, int ty) {
		return 1f;
	}
	
	public ArrayList<Monster> getMonsters() {
		return monsters;
	}
}
