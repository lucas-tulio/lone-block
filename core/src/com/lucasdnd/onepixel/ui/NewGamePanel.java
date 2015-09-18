package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.FontUtils;

public class NewGamePanel {
	
	private float x, y, width, height;
	private final float paddingX = 15f;
	private final float paddingY = 16f;
	private final float lineWeight = 4f;
	private boolean visible;
	
	private String title;
	private Button startButton, cancelButton;
	private Panel panel;
	private FontUtils font;
	private ShapeRenderer sr;
	
	public NewGamePanel() {
		
		title = "New Game";
		panel = new Panel();
		font = new FontUtils();
		sr = new ShapeRenderer();
		
		width = 300f;
		height = 200f;
		
		startButton = new Button("Start", x + paddingX, y - paddingY * 3f);
		cancelButton = new Button("Cancel", x + paddingX, 20f);
	}
	
	public void update() {
		startButton.update();
		cancelButton.update();
	}
	
	public void render() {
		if (visible) {
			
			// Panel
			sr.begin(ShapeType.Filled);
			sr.setColor(Color.BLACK);
			sr.rect(x + lineWeight, y, width - lineWeight, -height);
			panel.drawFrame(sr, x, y, width, height, lineWeight);
			sr.end();
			
			// Text and buttons
			width = 500f; // Multiples of 4
			height = 308f;
			x = (Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH) / 2f - width / 2f + 2f;
			y = Gdx.graphics.getHeight() / 2f + height / 2f + 2f;
			startButton.setX(x + paddingX);
			startButton.setY(y - height + startButton.height + paddingY);
			cancelButton.setX(x + width - paddingX - lineWeight - cancelButton.width);
			cancelButton.setY(y - height + startButton.height + paddingY);
			
			// Title
			font.drawWhiteFont(title, x, y - paddingY / 2f, true, Align.center, (int)width);
			startButton.render();
			cancelButton.render();
		}
	}
	
	public void show() {
		visible = true;
	}
	
	public void hide() {
		visible = false;
	}

	public boolean isVisible() {
		return visible;
	}

	public Button getStartButton() {
		return startButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}
}
