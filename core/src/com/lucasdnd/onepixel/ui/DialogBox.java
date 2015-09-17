package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Align;
import com.lucasdnd.onepixel.FontUtils;

public class DialogBox {
	
	private float x, y, width, height;
	private final float paddingX = 16f;
	private final float paddingY = 16f;
	private final float lineWeight = 4f;
	private boolean visible;
	
	private String text;
	private Button yesButton, noButton;
	private FontUtils font;
	private ShapeRenderer sr;
	
	public DialogBox(String text) {
		font = new FontUtils();
		sr = new ShapeRenderer();
		
		this.text = text;
		
		width = font.getTextWidth(text) + paddingX * 8f;
		height = paddingY * 6;
		x = (Gdx.graphics.getWidth() - SideBar.SIDEBAR_WIDTH) / 2f - width / 2f;
		y = Gdx.graphics.getHeight() / 2f + height / 2f;
		
		yesButton = new Button("Yes", x + paddingX, y - paddingY * 3f);
		noButton = new Button("No", x + width / 2f + paddingX * 1.5f - lineWeight / 2f, y - paddingY * 3f);
	}
	
	public void update() {
		yesButton.update();
		noButton.update();
	}
	
	public void render() {
		if (visible) {
			
			// Panel
			sr.begin(ShapeType.Filled);
			sr.setColor(Color.BLACK);
			sr.rect(x + lineWeight, y, width - lineWeight, -height);
			drawFrame();
			sr.end();
			
			// Text and buttons
			font.drawWhiteFont(text, x + paddingX, y - paddingY / 2f-2f, true, Align.center, font.getTextWidth(text));
			yesButton.render();
			noButton.render();
		}
	}
	
	private void drawFrame() {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		
		sr.setColor(Color.WHITE);
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + width, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x + lineWeight, y, lineWidth - lineWeight * 2, lineWeight);
		
		// Bottom
		sr.rect(x + lineWeight, y - lineHeight, lineWidth - lineWeight * 2, lineWeight); 
		
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
