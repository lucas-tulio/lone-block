package com.lucasdnd.onepixel.gameplay.world;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.Resources;
import com.lucasdnd.onepixel.TimeController;
import com.lucasdnd.onepixel.gameplay.items.Fruit;
import com.lucasdnd.onepixel.gameplay.items.Sapling;
import com.lucasdnd.onepixel.gameplay.items.Wood;

public class Tree extends MapObject {
	
	public static final int saveId = 7;

	final static Color color = new Color(0f, 0.3f, 0f, 1f);
	final static Color saplingColor = new Color(0.3f, 0.8f, 0.3f, 1f);
	
	private int hitsToChopDown;	// How many times the user will press E to chop this Tree down
	
	private boolean isGrown;	// If it's a sapling or a tree
	private boolean hasFruits = false;
	
	private int fruits, saplings;
	private int saplingsToGrow;
	
	// Growth clocks
	private long fruitTicks, maxFruitTicks;
	private long growthTicks, maxGrowthTicks;
	private long saplingTicks, maxSaplingTicks;
	
	public Tree(Disposer disposer, int x, int y, boolean isGrown) {
		
		super(disposer, x, y);
		
		Random r = new Random();
		this.isGrown = isGrown;
		
		if (isGrown == false) {
			// A sapling
			hitsToChopDown = 0;
			fruits = 0;
			calculateMaxGrowthTicks();
		} else {
			// A tree 
			refreshSaplings();
			refreshHits();
			hasFruits = r.nextInt(20) == 0;
			calculateMaxGatherableTicks();
			if (hasFruits) {
				refreshFruits();
			} else {
				fruits = 0;
			}
		}
	}
	
	private void refreshSaplings() {
		saplings = 1;
		saplingsToGrow = 1;
	}
	private void refreshFruits() {
		fruits = 2;
	}
	private void refreshHits() {
		hitsToChopDown = 3;
	}
	
	private void calculateMaxGatherableTicks() {
		Random r = new Random();
		long maxTicks = TimeController.ONE_DAY + (r.nextInt((int)TimeController.ONE_DAY));
		maxTicks *= TimeController.FPS;
		maxFruitTicks = maxTicks;
		maxSaplingTicks = maxTicks;
	}
	
	private void calculateMaxGrowthTicks() {
		Random r = new Random();
		maxGrowthTicks = TimeController.ONE_DAY * (r.nextInt(2) + 1) + (r.nextInt((int)TimeController.ONE_DAY));
		maxGrowthTicks *= TimeController.FPS;
	}
	
	public void update() {
		
		if (isGrown == false) {
			// Sapling growth
			growthTicks++;
			if (growthTicks >= maxGrowthTicks) {
				isGrown = true;
				hasFruits = new Random().nextInt(2) == 0;
				if (hasFruits) {
					refreshFruits();
				}
				refreshSaplings();
				refreshHits();
				calculateMaxGatherableTicks();
			}
		} else {
			// Fruit renewal
			if (hasFruits) {
				fruitTicks++;
				if (fruitTicks % maxFruitTicks == 0) {
					refreshFruits();
				}
			}
			// Sapling renewal
			if (saplingsToGrow > 0 && saplings <= 0) {
				saplingTicks++;
				if (saplingTicks % maxSaplingTicks == 0) {
					saplings++;
					saplingsToGrow--;
				}
			}
		}
	}
	
	public void render(ShapeRenderer sr, float x, float y) {
		
		if (isGrown) {
			
			// Tree
			sr.setColor(color);
			sr.rect(x, y, OnePixel.pixelSize, OnePixel.pixelSize);
			
			if (saplings > 0 && fruits > 0) {

				// Fruits and Sapling
					
				sr.setColor(saplingColor);
				sr.rect(x + OnePixel.pixelSize / 8f,
						y + OnePixel.pixelSize - OnePixel.pixelSize / 2.5f,
						OnePixel.pixelSize / 4f,
						OnePixel.pixelSize / 4f);
				
				sr.setColor(Color.RED);
				sr.rect(x + (OnePixel.pixelSize / 8f * 5f),
						y + OnePixel.pixelSize / 8f,
						OnePixel.pixelSize / 4f,
						OnePixel.pixelSize / 4f);
			
			} else if (saplings > 0 && fruits <= 0) {
				
				// Sapling only
				
				sr.setColor(saplingColor);
				sr.rect(x + OnePixel.pixelSize - OnePixel.pixelSize / 2f - OnePixel.pixelSize / 8f,
						y + OnePixel.pixelSize - OnePixel.pixelSize / 2f - OnePixel.pixelSize / 8f,
						OnePixel.pixelSize / 4f,
						OnePixel.pixelSize / 4f);
			
			} else if (saplings <= 0 && fruits > 0) {
				
				// Fruits only
				
				sr.setColor(Color.RED);
				sr.rect(x + OnePixel.pixelSize - OnePixel.pixelSize / 2f - OnePixel.pixelSize / 8f,
						y + OnePixel.pixelSize - OnePixel.pixelSize / 2f - OnePixel.pixelSize / 8f,
						OnePixel.pixelSize / 4f,
						OnePixel.pixelSize / 4f);
			}
			
		} else {
			
			// Growing sapling
			
			sr.setColor(saplingColor);
			sr.rect(x + OnePixel.pixelSize / 2f - OnePixel.pixelSize / 4f,
					y + OnePixel.pixelSize / 4f,
					OnePixel.pixelSize / 2f,
					OnePixel.pixelSize / 2f);
			
		}
	}
	
	@Override
	public Object performAction() {
		
		// Sapling
		
		if (isGrown == false) {
			Resources.get().randomLeavesSound().play(0.4f);
			disposer.dispose(this);
			return new Sapling();
		}
		
		// Tree
		
		if (saplings > 0) {
			
			saplings--;
			Resources.get().randomLeavesSound().play(0.4f);
			
			if (isGrown == false) {
				disposer.dispose(this);
			}
			
			return new Sapling();
		}
		
		if (fruits > 0) {
			fruits--;
			Resources.get().randomLeavesSound().play(0.4f);
			return new Fruit();
		}
		
		if (hitsToChopDown > 0) {
			hitsToChopDown--;
			Resources.get().woodcuttingSound.play(0.3f);
		
			if (hitsToChopDown == 0) {
				disposer.dispose(this);
				return new Wood();
			}
		}
		
		return null;
	}

	@Override
	public int getSaveId() {
		return saveId;
	}

	public int getHitsToChopDown() {
		return hitsToChopDown;
	}

	public boolean isGrown() {
		return isGrown;
	}

	public boolean hasFruits() {
		return hasFruits;
	}

	public int getFruits() {
		return fruits;
	}

	public int getSaplings() {
		return saplings;
	}

	public int getSaplingsToGrow() {
		return saplingsToGrow;
	}

	public long getFruitTicks() {
		return fruitTicks;
	}

	public long getGrowthTicks() {
		return growthTicks;
	}

	public long getSaplingTicks() {
		return saplingTicks;
	}
}
