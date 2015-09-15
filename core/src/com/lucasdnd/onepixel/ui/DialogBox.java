package com.lucasdnd.onepixel.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.FontUtils;
import com.lucasdnd.onepixel.OnePixel;

public class DialogBox {
	
	float x, y, width, height;
	final float paddingX = 32f;
	final float paddingY = 32f;
	final float lineWeight = 4f;
	
	String question;
	Button yesButton, noButton;
	FontUtils font;
	ShapeRenderer sr;
	
	public DialogBox(String question) {
		this.question = question;
		updatePosition();
	}
	
	private void updatePosition() {
		x = Gdx.graphics.getWidth() / 2f - SideBar.SIDEBAR_WIDTH / 2f;
		y = Gdx.graphics.getHeight() / 2f;
		
		
	}
	
	public void update() {
			
	}
	
	public void render() {
		drawFrame();
//		font.drawWhiteFont(text, paddingX, paddingY, withShadow, align);
	}
	
	private void drawFrame() {
		final float lineHeight = height;
		final float lineWidth = width + lineWeight;
		sr.begin(ShapeType.Filled);
		sr.setColor(Color.WHITE);
		
		// Left
		sr.rect(x, y, lineWeight, lineWeight - lineHeight);
		
		// Right
		sr.rect(x + width, y, lineWeight, lineWeight - lineHeight);
		
		// Top
		sr.rect(x + lineWeight, y, lineWidth - lineWeight * 2, lineWeight);
		
		// Bottom
		sr.rect(x + lineWeight, y - lineHeight, lineWidth - lineWeight * 2, lineWeight); 
		
		sr.end();
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
}
