package com.lucasdnd.loneblock.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lucasdnd.loneblock.LoneBlock;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 704;
		config.resizable = false;
		new LwjglApplication(new LoneBlock(), config);
	}
}
