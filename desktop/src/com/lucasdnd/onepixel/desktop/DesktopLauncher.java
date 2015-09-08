package com.lucasdnd.onepixel.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lucasdnd.onepixel.OnePixel;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 704;
		config.resizable = false;
		new LwjglApplication(new OnePixel(), config);
	}
	
	/**
	 * Placement sound - DasDeer (freesound.org) http://freesound.org/people/DasDeer/sounds/161544/
	 * Wood cutting sound - f4ngy (freesound.org) http://freesound.org/people/f4ngy/sounds/240788/
	 * Stone mining sound (edited) - ryanconway (freesound.org) http://freesound.org/people/ryanconway/sounds/240801/
	 * Leaves sound (edited) - ramagochi (freesound.org) http://freesound.org/people/ramagochi/sounds/119642/
	 * Eating sound (edited) - niwki (freesound.org) http://freesound.org/people/niwki/sounds/169761/
	 * Drinking sound (edited) - mshahen (freesound.org) http://freesound.org/people/mshahen/sounds/185456/
	 * 
	 */
}
