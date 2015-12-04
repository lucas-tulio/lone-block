package com.lucasdnd.onepixel.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.Fruit;
import com.lucasdnd.onepixel.gameplay.items.Inventory;
import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.items.StatRecovery;
import com.lucasdnd.onepixel.gameplay.items.Usable;
import com.lucasdnd.onepixel.gameplay.world.CampfireBlock;
import com.lucasdnd.onepixel.gameplay.world.MapObject;
import com.lucasdnd.onepixel.gameplay.world.Tree;
import com.lucasdnd.onepixel.gameplay.world.Water;
import com.lucasdnd.onepixel.gameplay.world.World;
import com.lucasdnd.onepixel.gameplay.world.pathfinder.Mover;

public class Player implements Mover {
	
	// Basic stuff
	public static final int MAX_STAT_VALUE = 10000;
	private int x, y, direction;
	public final int UP = 0;
	public final int LEFT = 1;
	public final int DOWN = 2;
	public final int RIGHT = 3;
	private Color color;
	
	// Status
	private int health, cold, hunger, thirst;
	private int statusDecrease;
	private int statusDecreaseLimit = 10;
	private boolean heatingUp, dead;
	
	// Inventory, crafting
	private Inventory inventory;
	
	private Player() {
		color = Color.BLACK;
	}
	
	/**
	 * New Game constructor
	 * @param spawnPoint
	 */
	public Player(Point spawnPoint) {
		
		this();
		
		health = MAX_STAT_VALUE;
		cold = MAX_STAT_VALUE;
		hunger = MAX_STAT_VALUE;
		thirst = MAX_STAT_VALUE;
		
		faceUp();
		inventory = new Inventory();
		
		this.x = spawnPoint.x;
		this.y = spawnPoint.y;
	}

	/**
	 * Load Game constructor
	 * @param x
	 * @param y
	 * @param direction
	 * @param health
	 * @param cold
	 * @param hunger
	 * @param thirst
	 */
	public Player(int x, int y, int direction, int health, int cold, int hunger, int thirst) {
		this();
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.health = health;
		this.cold = cold;
		this.hunger = hunger;
		this.thirst = thirst;
	}

	public void update() {
		
		// Inventory, crafting, etc
		inventory.update();
		
		// Check if the Player is close to a campfire to heat it up
		World world = ((OnePixel)Gdx.app.getApplicationListener()).getWorld();
		int minCampfireRangeCheckX = x - 5;
		int maxCampfireRangeCheckX = x + 5;
		int minCampfireRangeCheckY = y - 5;
		int maxCampfireRangeCheckY = y + 5;
		// Bounds safe check
		if (minCampfireRangeCheckX <= 0) {
			minCampfireRangeCheckX = 0;
		}
		if (maxCampfireRangeCheckX >= world.getSize()) {
			maxCampfireRangeCheckX = world.getSize();
		}
		if (minCampfireRangeCheckY <= 0) {
			minCampfireRangeCheckY = 0;
		}
		if (maxCampfireRangeCheckY >= world.getSize()) {
			maxCampfireRangeCheckY = world.getSize();
		}
		
		heatingUp = false;
		for (int i = minCampfireRangeCheckX; i < maxCampfireRangeCheckX; i++) {
			for (int j = minCampfireRangeCheckY; j < maxCampfireRangeCheckY; j++) {
				if (world.getMapObjectAt(i, j) instanceof CampfireBlock) {
					heatingUp = true;
					break;
				}
			}
		}
		if (heatingUp) { 
			cold += 3;
		}
		
		// Status
		statusDecrease++;
		if (statusDecrease % statusDecreaseLimit == 0) {
			if (((OnePixel)Gdx.app.getApplicationListener()).getTimeController().isNight()) {
				cold -= 5;
			} else {
				cold--;
			}
			hunger--;
			thirst -= 2;
			statusDecrease = 0;
		}
		
		// Max stat check
		if (health >= MAX_STAT_VALUE) {
			health = MAX_STAT_VALUE;
		}
		if (cold >= MAX_STAT_VALUE) {
			cold = MAX_STAT_VALUE;
		}
		if (hunger >= MAX_STAT_VALUE) {
			hunger = MAX_STAT_VALUE;
		}
		if (thirst >= MAX_STAT_VALUE) {
			thirst = MAX_STAT_VALUE;
		}
		
		// Death check
		if (hunger <= 0 || thirst <= 0 || health <= 0 || cold <= 0) {
			dead = true;
		}
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(x * OnePixel.blockSize, y * OnePixel.blockSize, OnePixel.blockSize, OnePixel.blockSize);
		float directionOffsetX = 0f;
		float directionOffsetY = 0f;
		switch (direction) {
		case UP:
			directionOffsetX = OnePixel.blockSize / 2f - OnePixel.blockSize / 4f;
			directionOffsetY = OnePixel.blockSize + OnePixel.blockSize / 4f;
			sr.rect(x * OnePixel.blockSize + directionOffsetX, y * OnePixel.blockSize + directionOffsetY, OnePixel.blockSize / 2f, OnePixel.blockSize / 2f);
			break;
		case LEFT:
			directionOffsetX = -OnePixel.blockSize / 2f - OnePixel.blockSize / 4f;
			directionOffsetY = OnePixel.blockSize / 2f - OnePixel.blockSize / 4f;
			sr.rect(x * OnePixel.blockSize + directionOffsetX, y * OnePixel.blockSize + directionOffsetY, OnePixel.blockSize / 2f, OnePixel.blockSize / 2f);
			break;
		case DOWN:
			directionOffsetX = OnePixel.blockSize / 2f - OnePixel.blockSize / 4f;
			directionOffsetY = -OnePixel.blockSize / 2f - OnePixel.blockSize / 4f;
			sr.rect(x * OnePixel.blockSize + directionOffsetX, y * OnePixel.blockSize + directionOffsetY, OnePixel.blockSize / 2f, OnePixel.blockSize / 2f);
			break;
		case RIGHT:
			directionOffsetX = OnePixel.blockSize + OnePixel.blockSize / 4f;
			directionOffsetY = OnePixel.blockSize / 2f - OnePixel.blockSize / 4f;
			sr.rect(x * OnePixel.blockSize + directionOffsetX, y * OnePixel.blockSize + directionOffsetY, OnePixel.blockSize / 2f, OnePixel.blockSize / 2f);
			break;
		default:
			break;
		}
		sr.end();
	}
	
	/**
	 * Perform an action on a block (press E)
	 * @param world
	 */
	public void performAction(World world) {
		int[] target = getTargetBlock();
		int targetX = target[0];
		int targetY = target[1];
		
		MapObject targetObject = world.getMapObjectAt(targetX, targetY);
		if (targetObject == null) {
			return;
		}
		
		Object result = targetObject.performAction();
		if (result != null) {
			if (result instanceof Item) {
				
				inventory.addItem((Item)result);
				hunger -= 3;
				thirst -= 5;
				
			} else if (result instanceof StatRecovery) {
				recoverStats((StatRecovery)result);
			}
		}
	}
	
	/**
	 * Uses the currently selected item (press W)
	 * @param world
	 */
	public void useItem(World world) {
		int[] target = getTargetBlock();
		int targetX = target[0];
		int targetY = target[1];
		
		// Get item from the inventory
		Item item = null;
		try {
			item = inventory.getSelectedInventoryBox().getItem();
		} catch (Exception e) {
			return;
		}
		if (item == null) {
			return;
		}
		
		// A block or an usable item?
		MapObject itemBlock = world.exchange(item, targetX, targetY);
		if (itemBlock == null && item instanceof Usable) {

			// Item
			StatRecovery statRecovery = ((Usable)item).use();
			recoverStats(statRecovery);
			
			// Play sound
			if (item instanceof Fruit) {
				Resources.get().randomEatingSound().play();
			}
		
		} else {
			
			// Place block
			
			// Check if the target coordinates are outside the map
			if (targetX < 0 || targetY < 0 || targetX >= world.getSize() || targetY >= world.getSize()) {
				return;
			}
			
			// Check what's in the target coordinates
			MapObject targetObject = world.getMapObjectAt(targetX, targetY);
			if (targetObject != null && !(targetObject instanceof Water)) {
				return;
			}
			
			world.getMapObjects()[targetX][targetY] = itemBlock;
			if (itemBlock instanceof Tree) {
				world.getTrees().add((Tree)itemBlock);
			}
			Resources.get().placementSound.play(0.2f);
			
			// Stats update
			hunger -= 3;
			thirst -= 5;
		}
		
		// Consume it
		item.decreaseAmount();
		inventory.clearEmptyBoxes();
	}
	
	private void recoverStats(StatRecovery statRecovery) {
		health += statRecovery.getHealth();
		cold += statRecovery.getCold();
		hunger += statRecovery.getFood();
		thirst += statRecovery.getDrink();
	}
	
	/**
	 * Get the coordinates of the block in front of the player
	 * @return
	 */
	private int[] getTargetBlock() {
		int[] result = new int[2];
		final int X = 0;
		final int Y = 1;
		result[X] = x;
		result[Y] = y;
		if (direction == UP) {
			result[Y]++;
		} else if (direction == DOWN) {
			result[Y]--;
		} else if (direction == RIGHT) {
			result[X]++;
		} else if (direction == LEFT) {
			result[X]--;
		}
		return result;
	}
	
	/** Facing methods */
	
	public void faceUp() {
		direction = UP;
	}
	
	public void faceLeft() {
		direction = LEFT;
	}
	
	public void faceDown() {
		direction = DOWN;
	}
	
	public void faceRight() {
		direction = RIGHT;
	}

	/** Movement Methods */
	
	public boolean canMoveUp(World world) {
		return moveUpCheck(world);
	}
	private boolean moveUpCheck(World world) {
		return y + 1 < world.getSize() && world.getMapObjects()[x][y+1] == null;
	}

	public boolean canMoveLeft(World world) {
		return moveLeftCheck(world);
	}
	private boolean moveLeftCheck(World world) {
		return x - 1 >= 0 && world.getMapObjects()[x-1][y] == null;
	}

	public boolean canMoveDown(World world) {
		return moveDownCheck(world);
	}
	private boolean moveDownCheck(World world) {
		return y - 1 >= 0 && world.getMapObjects()[x][y-1] == null;
	}

	public boolean canMoveRight(World world) {
		return moveRightCheck(world);
	}
	private boolean moveRightCheck(World world) {
		return x + 1 < world.getSize() && world.getMapObjects()[x+1][y] == null;
	}
	
	public void moveUp() {
		y++;
	}
	
	public void moveDown() {
		y--;
	}
	
	public void moveRight() {
		x++;
	}
	
	public void moveLeft() {
		x--;
	}

	/** Getters and setters */
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getCold() {
		return cold;
	}

	public void setCold(int cold) {
		this.cold = cold;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public int getThirst() {
		return thirst;
	}

	public void setThirst(int thirst) {
		this.thirst = thirst;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isDead() {
		return dead;
	}
	
	public void setHeatingUp(boolean heatingUp) {
		this.heatingUp = heatingUp;
	}

	public boolean isHeatingUp() {
		return heatingUp;
	}

}
