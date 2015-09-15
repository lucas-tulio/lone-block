package com.lucasdnd.onepixel.gameplay;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class Monster {

	// Basic stuff
	private Point location;
	private Color color;
	
	private boolean isChasing;
	private ArrayList<Point> path;
	private int currentPathIndex;
	
	public Monster() {
		
	}
	
	public void update() {
		if (isChasing) {
			
		}
	}
	
	private void setupNewPath() {
		currentPathIndex = 0;
	}
	
	private void moveOnPath() {
		
	}
	
}
