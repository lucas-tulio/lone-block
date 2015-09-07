package com.lucasdnd.onepixel.gameplay;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.gameplay.items.Inventory;
import com.lucasdnd.onepixel.gameplay.items.Item;
import com.lucasdnd.onepixel.gameplay.items.StatRecovery;
import com.lucasdnd.onepixel.gameplay.items.Usable;
import com.lucasdnd.onepixel.gameplay.items.Wood;
import com.lucasdnd.onepixel.gameplay.world.MapObject;
import com.lucasdnd.onepixel.gameplay.world.Water;
import com.lucasdnd.onepixel.gameplay.world.World;

public class Player {
	
	public static final int MAX_STAT_VALUE = 10000;
	private int x, y, z, direction;
	public final int UP = 0;
	public final int LEFT = 1;
	public final int DOWN = 2;
	public final int RIGHT = 3;
	private Color color;
	private Inventory inventory;
	
	private boolean freeMovementMode = true;
	private boolean dead;
	
	private int health, stamina, food, drink;
	private int statusDecrease;
	private int statusDecreaseLimit = 10;
	
	private class Point {
		public int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public Player(World world) {
		
		color = Color.BLACK;
		health = MAX_STAT_VALUE;
		stamina = MAX_STAT_VALUE;
		food = MAX_STAT_VALUE;
		drink = MAX_STAT_VALUE;
		
		faceUp();
		inventory = new Inventory(27);
		
		// Spawn point
		ArrayList<Point> spawnPoints = new ArrayList<Point>();
		for (int i = 0; i < world.getSize(); i++) {
			for (int j = 0; j < world.getSize(); j++) {
				if (world.getMapObjectAt(i, j, 0) == null) {
					spawnPoints.add(new Point(i, j));
				}
			}
		}
		
		int randomPoint = new Random().nextInt(spawnPoints.size());
		this.x = spawnPoints.get(randomPoint).x;
		this.y = spawnPoints.get(randomPoint).y;
	}

	public void update() {
		
		inventory.update();
		
		// Status
		statusDecrease++;
		if (statusDecrease % statusDecreaseLimit == 0) {
			stamina--;
			food--;
			drink -= 2;
			statusDecrease = 0;
		}
		
		// Max stat check
		if (health >= MAX_STAT_VALUE) {
			health = MAX_STAT_VALUE;
		}
		if (stamina >= MAX_STAT_VALUE) {
			stamina = MAX_STAT_VALUE;
		}
		if (food >= MAX_STAT_VALUE) {
			food = MAX_STAT_VALUE;
		}
		if (drink >= MAX_STAT_VALUE) {
			drink = MAX_STAT_VALUE;
		}
		
		// Death check
		if (food <= 0 || drink <= 0 || health <= 0) {
			dead = true;
		}
	}

	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(color);
		sr.rect(x * OnePixel.PIXEL_SIZE, y * OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE, OnePixel.PIXEL_SIZE);
		float directionOffsetX = 0f;
		float directionOffsetY = 0f;
		switch (direction) {
		case UP:
			directionOffsetX = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE + OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case LEFT:
			directionOffsetX = -OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case DOWN:
			directionOffsetX = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = -OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
			break;
		case RIGHT:
			directionOffsetX = OnePixel.PIXEL_SIZE + OnePixel.PIXEL_SIZE / 4f;
			directionOffsetY = OnePixel.PIXEL_SIZE / 2f - OnePixel.PIXEL_SIZE / 4f;
			sr.rect(x * OnePixel.PIXEL_SIZE + directionOffsetX, y * OnePixel.PIXEL_SIZE + directionOffsetY, OnePixel.PIXEL_SIZE / 2f, OnePixel.PIXEL_SIZE / 2f);
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
		int targetZ = target[2];
		
		MapObject targetObject = world.getMapObjectAt(targetX, targetY, targetZ);
		if (targetObject == null) {
			return;
		}
		
		Object result = targetObject.performAction();
		if (result != null) {
			if (result instanceof Item) {
				inventory.addItem((Item)result);
				stamina -= 10;
				food -= 3;
				drink -= 5;
				
				// Play the appropriate sound effect
				if (result instanceof Wood) {
					Resources.get().woodcuttingSound.play(0.4f);
				}
				
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
		int targetZ = target[2];
		
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
		MapObject itemBlock = world.exchange(item, targetX, targetY, targetZ);
		if (itemBlock == null && item instanceof Usable) {

			// Item
			StatRecovery statRecovery = ((Usable)item).use();
			recoverStats(statRecovery);
		
		} else {
			
			// Block
			
			// Check what's in the target coordinates
			MapObject targetObject = world.getMapObjectAt(targetX, targetY, targetZ);
			if (targetObject != null && !(targetObject instanceof Water)) {
				return;
			}
			
			world.getMapObjects()[targetX][targetY][targetZ] = itemBlock;
			Resources.get().placementSound.play(0.2f);
			
			// Stats update
			stamina -= 10;
			food -= 3;
			drink -= 5;
		}
		
		// Consume it
		item.decreaseAmount();
		inventory.checkItems();
	}
	
	private void recoverStats(StatRecovery statRecovery) {
		health += statRecovery.getHealth();
		stamina += statRecovery.getStamina();
		food += statRecovery.getFood();
		drink += statRecovery.getDrink();
	}
	
	/**
	 * Get the coordinates of the block in front of the player
	 * @return
	 */
	private int[] getTargetBlock() {
		int[] result = new int[3];
		final int X = 0;
		final int Y = 1;
		final int Z = 2;
		result[X] = x;
		result[Y] = y;
		result[Z] = z;
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
		if (freeMovementMode) {
			faceUp();
			return moveUpCheck(world);
		} else {
			
			if (direction != UP) {
				faceUp();
				return false;
			} else {
				return moveUpCheck(world);
			}
		}
	}
	private boolean moveUpCheck(World world) {
		return y + 1 < world.getSize() && world.getMapObjects()[x][y+1][0] == null;
	}

	public boolean canMoveLeft(World world) {
		if (freeMovementMode) {
			faceLeft();
			return moveLeftCheck(world);
		} else {
			
			if (direction != LEFT) {
				faceLeft();
				return false;
			} else {
				return moveLeftCheck(world);
			}
		}
	}
	private boolean moveLeftCheck(World world) {
		return x - 1 >= 0 && world.getMapObjects()[x-1][y][0] == null;
	}

	public boolean canMoveDown(World world) {
		if (freeMovementMode) {
			faceDown();
			return moveDownCheck(world);
		} else {
			
			if (direction != DOWN) {
				faceDown();
				return false;
			} else {
				return moveDownCheck(world);
			}
		}
	}
	private boolean moveDownCheck(World world) {
		return y - 1 >= 0 && world.getMapObjects()[x][y-1][0] == null;
	}

	public boolean canMoveRight(World world) {
		if (freeMovementMode) {
			faceRight();
			return moveRightCheck(world);
		} else {
			
			if (direction != RIGHT) {
				faceRight();
				return false;
			} else {
				return moveRightCheck(world);
			}
		}
	}
	private boolean moveRightCheck(World world) {
		return x + 1 < world.getSize() && world.getMapObjects()[x+1][y][0] == null;
	}
	
	public void moveUp() {
		stamina--;
		y++;
	}
	
	public void moveDown() {
		stamina--;
		y--;
	}
	
	public void moveRight() {
		stamina--;
		x++;
	}
	
	public void moveLeft() {
		stamina--;
		x--;
	}

	/** Getters and setters */
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getDrink() {
		return drink;
	}

	public void setDrink(int drink) {
		this.drink = drink;
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
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}

	public boolean isFreeMovementMode() {
		return freeMovementMode;
	}

	public boolean isDead() {
		return dead;
	}
}
