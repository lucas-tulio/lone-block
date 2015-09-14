package com.lucasdnd.onepixel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class TimeController {
	
	long ticks;
	final long fps = 60;
	
	long time;	// 1 time == one real second
	public static final long ONE_DAY = 600;	// how many seconds in one day. 3600 = 10 minutes
	
	String text;
	FontUtils font;
	float x = 0f;
	float textWidth = 160f;
	final float boxHeight = 26f;
	final float textPaddingX = 4f;
	final float textPaddingY = 4f;
	
	public TimeController() {
		font = new FontUtils();
		text = "";
	}
	
	protected void update() {
		ticks++;
		if (ticks % fps == 0) {
			time++;
		}
		text = (int)(((float)getHourOfDay() / (float)ONE_DAY) * 100f) + "% of day " + getDay();
		textWidth = font.getTextWidth(text) + textPaddingX * 2f;
		x = Gdx.graphics.getWidth() - OnePixel.SIDEBAR_WIDTH - textWidth;
	}
	
	public void render(ShapeRenderer uiShapeRenderer) {
		uiShapeRenderer.begin(ShapeType.Filled);
		uiShapeRenderer.setColor(Color.BLACK);
		uiShapeRenderer.rect(x, Gdx.graphics.getHeight() - boxHeight, textWidth, boxHeight);
		uiShapeRenderer.end();
		font.drawWhiteFont(text, x + textPaddingX, Gdx.graphics.getHeight() - textPaddingY, true);
	}
	
	public boolean isNight() {
		return (int)(time % ONE_DAY) > ONE_DAY / 2;
	}
	
	public int getHourOfDay() {
		return (int)(time % ONE_DAY);
	}
	
	public int getDay() {
		return (int)(time / ONE_DAY);
	}

	public long getTicks() {
		return ticks;
	}

	public long getTime() {
		return time;
	}
	
}
