package com.lucasdnd.onepixel;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
}
