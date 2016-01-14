package com.lucasdnd.loneblock;

import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Loads and keeps pointers to resources (fonts, sounds, etc)
 * @author lucasdnd
 *
 */
public class Resources {

	private static Resources instance;
	public static Resources get() {
		if (instance == null) {
			instance = new Resources();
		}
		return instance;
	}
	
	private Resources() {
		whiteFont = new BitmapFont(Gdx.files.internal("font/proggyWhite.fnt"));
		blackFont = new BitmapFont(Gdx.files.internal("font/proggyBlack.fnt"));
		lightGrayFont = new BitmapFont(Gdx.files.internal("font/proggyLightGray.fnt"));
		grayFont = new BitmapFont(Gdx.files.internal("font/proggyGray.fnt"));
		redFont = new BitmapFont(Gdx.files.internal("font/proggyRed.fnt"));
		greenFont = new BitmapFont(Gdx.files.internal("font/proggyGreen.fnt"));
		
		placementSound = Gdx.audio.newSound(Gdx.files.internal("sfx/placement.wav"));
		woodcuttingSound = Gdx.audio.newSound(Gdx.files.internal("sfx/woodcutting.wav"));
		miningSound = Gdx.audio.newSound(Gdx.files.internal("sfx/mining.wav"));
		leavesSound1 = Gdx.audio.newSound(Gdx.files.internal("sfx/leaves-1.wav"));
		leavesSound2 = Gdx.audio.newSound(Gdx.files.internal("sfx/leaves-2.wav"));
		leavesSound3 = Gdx.audio.newSound(Gdx.files.internal("sfx/leaves-3.wav"));
		leavesSound4 = Gdx.audio.newSound(Gdx.files.internal("sfx/leaves-4.wav"));
		eatingSound1 = Gdx.audio.newSound(Gdx.files.internal("sfx/eating-1.wav"));
		eatingSound2 = Gdx.audio.newSound(Gdx.files.internal("sfx/eating-2.wav"));
		eatingSound3 = Gdx.audio.newSound(Gdx.files.internal("sfx/eating-3.wav"));
		drinkingSound1 = Gdx.audio.newSound(Gdx.files.internal("sfx/drinking-1.wav"));
		drinkingSound2 = Gdx.audio.newSound(Gdx.files.internal("sfx/drinking-2.wav"));
		drinkingSound3 = Gdx.audio.newSound(Gdx.files.internal("sfx/drinking-3.wav"));
		
		paletteFile = Gdx.files.local("palette.conf");
		readColors();
		
		saveFile = Gdx.files.local("saves/save.op");
	}
	
	/**
	 * Reads the colors from the palette.conf file
	 */
	private void readColors() {
		
		BufferedReader br = null;
		try {
			br = paletteFile.reader(1024);
			
			String line = "";
			while ((line = br.readLine()) != null) {
				
				line = line.trim();
				if (line.length() == 0 || line.substring(0, 2).compareToIgnoreCase("//") == 0) {
					continue; // Comment or empty, ignore
				} else {
					
					// Read content
					String[] fields = line.split(":");
					if (fields.length != 2) {
						throw new Exception("Bad palette file format at " + line);
					}
					fields[0] = fields[0].trim();
					fields[1] = fields[1].trim().replace("#", "");
					
					// Match with Color attribute name
					Class<?> colorClass = Color.class;
					Field field = colorClass.getDeclaredField(fields[0]);
					field.setAccessible(true);
					
					// Create the libgdx Color object
					long colorValue = Long.parseLong(fields[1] + "ff", 16);
					com.badlogic.gdx.graphics.Color colorObject = new com.badlogic.gdx.graphics.Color((int)colorValue);
					
					// Set!
					field.set(null, colorObject);
				}
			}

		} catch (NoSuchFieldException e) {
			System.out.println("Error reading palette file. Unknown field:\n");
			System.out.println(e.getMessage());
			e.printStackTrace();
			Gdx.app.exit();
		} catch (Exception e) {
			System.out.println("Error reading palette file\n");
			System.out.println(e.getMessage());
			e.printStackTrace();
			Gdx.app.exit();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static class Color {
		public static com.badlogic.gdx.graphics.Color monsterResting, monsterChasing, player, bandage, blueRock,
		campfire1, campfire2, campfire3, campfire4, cotton, fruit, greenRock, magentaRock, redRock, rock, tealRock,
		wood, yellowRock, tree, ground, buttonDisabled, button, buttonHover, uiFill, uiStroke, uiBarFill, tooltipFill,
		worldBackground, sapling, water, deepWater;
	}
	
	public Sound randomLeavesSound() {
		switch(new Random().nextInt(4)) {
		case 0:
			return leavesSound1;
		case 1:
			return leavesSound2;
		case 2:
			return leavesSound3;
		default:
			return leavesSound4;
		}
	}
	
	public Sound randomEatingSound() {
		switch(new Random().nextInt(3)) {
		case 0:
			return eatingSound1;
		case 1:
			return eatingSound2;
		default:
			return eatingSound3;
		}
	}
	
	public Sound randomDrinkingSound() {
		switch(new Random().nextInt(3)) {
		case 0:
			return drinkingSound1;
		case 1:
			return drinkingSound2;
		default:
			return drinkingSound3;
		}
	}
	
	// Fonts
	public BitmapFont whiteFont;
	public BitmapFont blackFont;
	public BitmapFont lightGrayFont;
	public BitmapFont grayFont;
	public BitmapFont redFont;
	public BitmapFont greenFont;
	
	// Sound effects
	public Sound placementSound;
	public Sound woodcuttingSound;
	public Sound miningSound;
	private Sound leavesSound1;
	private Sound leavesSound2;
	private Sound leavesSound3;
	private Sound leavesSound4;
	private Sound eatingSound1;
	private Sound eatingSound2;
	private Sound eatingSound3;
	private Sound drinkingSound1;
	private Sound drinkingSound2;
	private Sound drinkingSound3;
	
	// Palette
	private FileHandle paletteFile;
	
	// Save File
	public FileHandle saveFile;
}
