package com.lucasdnd.loneblock;

import java.io.BufferedReader;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.lucasdnd.loneblock.gameplay.Monster;
import com.lucasdnd.loneblock.gameplay.Player;
import com.lucasdnd.loneblock.gameplay.Point;
import com.lucasdnd.loneblock.gameplay.items.Bandage;
import com.lucasdnd.loneblock.gameplay.items.BlueStone;
import com.lucasdnd.loneblock.gameplay.items.Campfire;
import com.lucasdnd.loneblock.gameplay.items.Cotton;
import com.lucasdnd.loneblock.gameplay.items.Fruit;
import com.lucasdnd.loneblock.gameplay.items.GreenStone;
import com.lucasdnd.loneblock.gameplay.items.Inventory;
import com.lucasdnd.loneblock.gameplay.items.InventoryBox;
import com.lucasdnd.loneblock.gameplay.items.Item;
import com.lucasdnd.loneblock.gameplay.items.MagentaStone;
import com.lucasdnd.loneblock.gameplay.items.RedStone;
import com.lucasdnd.loneblock.gameplay.items.Sapling;
import com.lucasdnd.loneblock.gameplay.items.Stone;
import com.lucasdnd.loneblock.gameplay.items.TealStone;
import com.lucasdnd.loneblock.gameplay.items.Wood;
import com.lucasdnd.loneblock.gameplay.items.YellowStone;
import com.lucasdnd.loneblock.gameplay.world.BlueRock;
import com.lucasdnd.loneblock.gameplay.world.CampfireBlock;
import com.lucasdnd.loneblock.gameplay.world.DeepWater;
import com.lucasdnd.loneblock.gameplay.world.GreenRock;
import com.lucasdnd.loneblock.gameplay.world.MagentaRock;
import com.lucasdnd.loneblock.gameplay.world.MapObject;
import com.lucasdnd.loneblock.gameplay.world.RedRock;
import com.lucasdnd.loneblock.gameplay.world.Rock;
import com.lucasdnd.loneblock.gameplay.world.SaplingBlock;
import com.lucasdnd.loneblock.gameplay.world.TealRock;
import com.lucasdnd.loneblock.gameplay.world.Tree;
import com.lucasdnd.loneblock.gameplay.world.Water;
import com.lucasdnd.loneblock.gameplay.world.WoodBlock;
import com.lucasdnd.loneblock.gameplay.world.World;
import com.lucasdnd.loneblock.gameplay.world.YellowRock;

public class SaveLoad {
	
	private static final String separator = ";";
	private static final String innerSeparator = ",";
	private static final String lineBreak = "\n";
	
	public static boolean save() {
		
		LoneBlock game = (LoneBlock)Gdx.app.getApplicationListener();
		StringBuilder sb = new StringBuilder();
		
		// Write stuff
		
		// Line 1: playerX, playerY, facing direction, health, cold, hunger, thirst
		Player p = game.getPlayer();
		sb.append("" +
				p.getX() + separator +
				p.getY() + separator +
				p.getDirection() + separator +
				p.getHealth() + separator +
				p.getCold() + separator +
				p.getHunger() + separator +
				p.getThirst()
				);
		
		sb.append(lineBreak);
		
		// Line 2: inventory items and their amounts (from left to right, top to bottom),
		// then crafting box items
		// Items' saveId and amounts are separated by innerSeparators
		// Different items are separated by separators
		Inventory inventory = p.getInventory();
		ArrayList<InventoryBox> inventoryBoxes = new ArrayList<InventoryBox>();
		inventoryBoxes.addAll(inventory.getInventoryBoxes());
		inventoryBoxes.addAll(inventory.getCraftingBoxes());
		
		boolean isFirst = true;
		for (InventoryBox ib : inventoryBoxes) {
			
			if (isFirst == false) {
				sb.append(separator);
			}
			isFirst = false;
			
			Item item = ib.getItem();
			if (item == null) {
				sb.append("0" + innerSeparator + "0");
			} else {
				sb.append(item.getSaveId() + innerSeparator + item.getAmount());
			}
		}
		
		sb.append(lineBreak);
		
		// Line 3: time
		sb.append(game.getTimeController().getTicks());
		
		sb.append(lineBreak);
		
		// Line 4: map size
		sb.append(game.getWorld().getSize());
		
		sb.append(lineBreak);
		
		// Map objects
		MapObject[][] mapObjects = game.getWorld().getMapObjects();
		for (int i = 0; i < game.getWorld().getSize(); i++) {
			
			isFirst = true;
			
			for (int j = 0; j < game.getWorld().getSize(); j++) {
				
				if (isFirst == false) {
					sb.append(separator);
				}
				isFirst = false;
				
				if (mapObjects[i][j] == null) {
					sb.append("0");
				} else if (mapObjects[i][j] instanceof Tree) {
					// Tree data:
					// saveId, isGrown, isCotton, hasFruits, saplings, saplingsToGrow, fruits, hitsToChopDown,
					// growthTicks, fruitTicks, saplingTicks, maxGrowthTicks, maxFruitTicks, maxSaplingTicks
					Tree t = (Tree)mapObjects[i][j];
					sb.append("" +
							t.getSaveId() + innerSeparator +
							t.isGrown() + innerSeparator +
							t.isCotton() + innerSeparator +
							t.hasFruits() + innerSeparator +
							t.getSaplings() + innerSeparator +
							t.getSaplingsToGrow() + innerSeparator +
							t.getFruits() + innerSeparator +
							t.getHitsToChopDown() + innerSeparator +
							t.getGrowthTicks() + innerSeparator +
							t.getFruitTicks() + innerSeparator +
							t.getSaplingTicks() + innerSeparator +
							t.getMaxGrowthTicks() + innerSeparator +
							t.getMaxFruitTicks() + innerSeparator +
							t.getMaxSaplingTicks()
							);
				} else {
					// Any other map object
					sb.append(mapObjects[i][j].getSaveId());
				}
			}
			sb.append(lineBreak);
		}
		
		// Monsters
		ArrayList<Monster> monsters = game.getWorld().getMonsters();
		isFirst = true;
		for (Monster m : monsters) {
				
			if (isFirst == false) {
				sb.append(separator);
			}
			isFirst = false;
			
			sb.append(m.getLocation().x + innerSeparator + m.getLocation().y + innerSeparator + m.getMaxMovementTicks() + innerSeparator + m.getMaxChaseTicks());
		}
		
		// Write to file
		try {
			Resources.get().saveFile.writeString(sb.toString(), false);
		} catch (GdxRuntimeException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean load() {
		
		LoneBlock game = (LoneBlock)Gdx.app.getApplicationListener();
		game.setLoadingGame(true);
		
		// Read from file
		try {
			
			BufferedReader br = Resources.get().saveFile.reader(1024);
			
			// Line 1: playerX, playerY, facing direction, health, cold, hunger, thirst
			String[] playerData = br.readLine().split(separator);
			int playerX = Integer.parseInt(playerData[0]);
			int playerY = Integer.parseInt(playerData[1]);
			int direction = Integer.parseInt(playerData[2]);
			int health = Integer.parseInt(playerData[3]);
			int cold = Integer.parseInt(playerData[4]);
			int hunger = Integer.parseInt(playerData[5]);
			int thirst = Integer.parseInt(playerData[6]);
			Player player = new Player(playerX, playerY, direction, health, cold, hunger, thirst);
			
			// Line 2: inventory items and their amounts (from left to right, top to bottom),
			// then crafting box items
			// Items' saveId and amounts are separated by innerSeparators
			// Different items are separated by separators
			
			Inventory inventory = new Inventory();
			
			String[] inventoryData = br.readLine().split(separator);
			for (int i = 0; i < inventoryData.length; i++) {
				
				String[] boxContent = inventoryData[i].split(innerSeparator);
				Item itemInBox = getItemFromSaveId(Integer.parseInt(boxContent[0]));
				if (itemInBox != null) {
					itemInBox.setAmount(Integer.parseInt(boxContent[1]));
				}
				
				if (i < inventory.getInventoryBoxes().size()) {
					inventory.getInventoryBoxes().get(i).setItem(itemInBox);
				} else {
					inventory.getCraftingBoxes().get(i - inventory.getInventoryBoxes().size()).setItem(itemInBox);
				}
				
			}
			player.setInventory(inventory);
			
			// Line 3: time
			TimeController timeController = new TimeController();
			timeController.setTicks(Long.parseLong(br.readLine()));
			game.setTimeController(timeController);
			
			// Line 4: map size
			int mapSize = Integer.parseInt(br.readLine());
			
			// World
			World world = new World(mapSize, true);
			MapObject[][] mapObjects = new MapObject[mapSize][mapSize];
			ArrayList<Tree> trees = new ArrayList<Tree>();
			
			for (int i = 0; i < mapSize; i++) {
				
				String[] objects = br.readLine().split(separator);
				if (objects.length != mapSize) {
					System.out.println("Save data inconsistency at " + i + ", " + "j");
					Gdx.app.exit();
				}
				
				for (int j = 0; j < mapSize; j++) {
					
					String objectData[] = objects[j].split(innerSeparator);
					MapObject mapObject = getMapObjectFromSaveId(Integer.parseInt(objectData[0]), world, i, j);
					
					if (mapObject instanceof Tree) {
						// Tree data:
						// saveId, isGrown, hasFruits, saplings, saplingsToGrow, fruits, hitsToChopDown,
						// growthTicks, fruitTicks, saplingTicks
						Tree tree = (Tree)mapObject;
						tree.setGrown(Boolean.parseBoolean(objectData[1]));
						tree.setCotton(Boolean.parseBoolean(objectData[2]));
						tree.setHasFruits(Boolean.parseBoolean(objectData[3]));
						tree.setSaplings(Integer.parseInt(objectData[4]));
						tree.setSaplingsToGrow(Integer.parseInt(objectData[5]));
						tree.setFruits(Integer.parseInt(objectData[6]));
						tree.setHitsToChopDown(Integer.parseInt(objectData[7]));
						tree.setGrowthTicks(Long.parseLong(objectData[8]));
						tree.setFruitTicks(Long.parseLong(objectData[9]));
						tree.setSaplingTicks(Long.parseLong(objectData[10]));
						tree.setMaxGrowthTicks(Long.parseLong(objectData[11]));
						tree.setMaxFruitTicks(Long.parseLong(objectData[12]));
						tree.setMaxSaplingTicks(Long.parseLong(objectData[13]));
						trees.add(tree);
					}
					
					mapObjects[i][j] = mapObject;
				}
			}
			
			// Set the world objects
			world.setMapObjects(mapObjects, trees);
			
			// After world data: read monsters
			ArrayList<Monster> monsters = new ArrayList<Monster>();
			
			String[] monsterData = br.readLine().split(separator);
			
			for (int i = 0; i < monsterData.length; i++) {
				
				// Monster: monsterX, monsterY, maxMovementTicks, maxChaseTicks
				String[] monsterContent = monsterData[i].split(innerSeparator);
				
				int monsterX = Integer.parseInt(monsterContent[0]);
				int monsterY = Integer.parseInt(monsterContent[1]);
				int maxMovementTicks = Integer.parseInt(monsterContent[2]);
				int maxChaseTicks = Integer.parseInt(monsterContent[3]);
				
				// Instantiate it
				Monster m = new Monster(maxMovementTicks, maxChaseTicks);
				m.setLocation(new Point(monsterX, monsterY));
				monsters.add(m);
			}
			
			// Add them to the world
			world.setMonsters(monsters);
			
			// Set the loaded objects in the game
			game.setWorld(world);
			game.setPlayer(player);
			
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			game.setLoadingGame(false);
			return false;
		}
		
		game.setLoadingGame(false);
		return true;
	}
	
	private static Item getItemFromSaveId(int saveId) {
		Item result = null;
		
		if (Campfire.saveId == saveId) {
			return new Campfire();
		} else if (Fruit.saveId == saveId) {
			return new Fruit();
		} else if (Sapling.saveId == saveId) {
			return new Sapling();
		} else if (Stone.saveId == saveId) {
			return new Stone();
		} else if (Wood.saveId == saveId) {
			return new Wood();
		} else if (Cotton.saveId == saveId) {
			return new Cotton();
		} else if (Bandage.saveId == saveId) {
			return new Bandage();
		} else if (RedStone.saveId == saveId) {
			return new RedStone();
		} else if (GreenStone.saveId == saveId) {
			return new GreenStone();
		} else if (BlueStone.saveId == saveId) {
			return new BlueStone();
		} else if (YellowStone.saveId == saveId) {
			return new YellowStone();
		} else if (TealStone.saveId == saveId) {
			return new TealStone();
		} else if (MagentaStone.saveId == saveId) {
			return new MagentaStone();
		}
		
		return result;
	}
	
	private static MapObject getMapObjectFromSaveId(int saveId, World disposer, int x, int y) {
		MapObject result = null;
		
		if (CampfireBlock.saveId == saveId) {
			return new CampfireBlock(disposer, x, y);
		} else if (DeepWater.saveId == saveId) {
			return new DeepWater(disposer, x, y);
		} else if (Rock.saveId == saveId) {
			return new Rock(disposer, x, y);
		} else if (SaplingBlock.saveId == saveId) {
			return new SaplingBlock(disposer, x, y);
		} else if (Tree.saveId == saveId) {
			return new Tree(disposer, x, y);
		} else if (Water.saveId == saveId) {
			return new Water(disposer, x, y);
		} else if (WoodBlock.saveId == saveId) {
			return new WoodBlock(disposer, x, y);
		} else if (RedRock.saveId == saveId) {
			return new RedRock(disposer, x, y);
		} else if (GreenRock.saveId == saveId) {
			return new GreenRock(disposer, x, y);
		} else if (BlueRock.saveId == saveId) {
			return new BlueRock(disposer, x, y);
		} else if (YellowRock.saveId == saveId) {
			return new YellowRock(disposer, x, y);
		} else if (TealRock.saveId == saveId) {
			return new TealRock(disposer, x, y);
		} else if (MagentaRock.saveId == saveId) {
			return new MagentaRock(disposer, x, y);
		}
		
		return result;
	}
}
