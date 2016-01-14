package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.FontUtils;
import com.lucasdnd.onepixel.Resources;

public class DialogBox {
	
	private float x, y, width, height;
	private final float paddingX = 15f;
	private final float paddingY = 16f;
	private final float lineWeight = 4f;
	private boolean visible;
	
	private String text;
	private Button yesButton, noButton;
	private Panel panel;
	private FontUtils font;
	private ShapeRenderer sr;
	
	public DialogBox(String text) {
		panel = new Panel();
		font = new FontUtils();
		sr = new ShapeRenderer();
		
		this.text = text;
		
		// Measures
		width = 212f; // Multiples of 4
		height = 100f;
		x = (Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH) / 2f - width / 2f + 2f;
		y = Gdx.graphics.getHeight() / 2f + height / 2f + 2f;
		
		// Buttons
		yesButton = new Button("Yes");
		noButton = new Button("No");
		yesButton.setX(x + paddingX);
		yesButton.setY(y - height + yesButton.height + paddingY);
		noButton.setX(x + width - paddingX - lineWeight - noButton.width);
		noButton.setY(y - height + noButton.height + paddingY);
	}
	
	public void update() {
		yesButton.update();
		noButton.update();
	}
	
	public void render() {
		if (visible) {
			
			// Panel
			sr.begin(ShapeType.Filled);
			sr.setColor(Resources.Color.uiFill);
			sr.rect(x + lineWeight, y, width - lineWeight, -height);
			panel.drawFrame(sr, x, y, width, height, lineWeight);
			sr.end();
			
			// Text and buttons
			font.drawWhiteFont(text, x, y - paddingY / 2f - 2f, true, Align.center, (int)width);
			yesButton.render();
			noButton.render();
		}
	}
	
	public void show() {
		visible = true;
	}
	
	public void hide() {
		visible = false;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Button getYesButton() {
		return yesButton;
	}

	public void setYesButton(Button yesButton) {
		this.yesButton = yesButton;
	}

	public Button getNoButton() {
		return noButton;
	}

	public void setNoButton(Button noButton) {
		this.noButton = noButton;
	}

	public boolean isVisible() {
		return visible;
	}
}
