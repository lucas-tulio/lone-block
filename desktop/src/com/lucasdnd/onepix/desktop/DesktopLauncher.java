package com.lucasdnd.onepix.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lucasdnd.onepix.OnePix;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 700;
		config.resizable = false;
		new LwjglApplication(new OnePix(), config);
	}
}
