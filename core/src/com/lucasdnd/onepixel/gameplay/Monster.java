package com.lucasdnd.onepixel.gameplay;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.TimeController;

public class Monster {

	// Basic stuff
	private Point location;
	private static final Color color = Color.PURPLE;
	
	// Pathfinding
	private boolean isChasing;
	private ArrayList<Point> path;
	private int currentPathIndex;
	private int detectionRange, attackRange;
	
	// Timers
	private long movementTicks, maxMovementTicks;
	private long detectionTicks, maxDetectionTicks;
	private long attackTicks, maxAttackTicks;
	
	public Monster() {
		movementTicks = 0;
		maxMovementTicks = TimeController.FPS;
		
		maxMovementTicks = 60;
		maxDetectionTicks = 60;
		maxAttackTicks = 30;
	}
	
	public void spawn() {
		Player player = ((OnePixel)Gdx.app.getApplicationListener()).getPlayer();
		int spawnDistance = 8;
		Random r = new Random();
		int x = player.getX() + r.nextInt(spawnDistance) - (spawnDistance / 2);
		int y = player.getY() + r.nextInt(spawnDistance) - (spawnDistance / 2);
		this.location = new Point(x, y);
	}
	
	public void update() {
		
		// Movement
		movementTicks++;
		if (movementTicks % maxMovementTicks != 0) {
			randomMovement();
			return;
		}
		
		// Player detection
		// When chasing, we call this every time 
		detectionTicks++;
		if (detectionTicks % maxDetectionTicks == 0 || isChasing) {
			if (getDistanceToPlayer() <= detectionRange) {
				isChasing = true;
				// TODO: create the path
			}
		}
		
		// Chases the player
		if (isChasing) {
			currentPathIndex++;
			if (path != null && path.size() > 0) { // TODO: remove this null check
				location = path.get(currentPathIndex);
			}
			
			// Attacks
			if (getDistanceToPlayer() <= attackRange) {
				attack();
			}
		}
	}
	
	public void render(ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.rect(location.x, location.y, OnePixel.pixelSize, OnePixel.pixelSize);
		sr.end();
	}
	
	private void setupNewPath() {
		currentPathIndex = 0;
	}
	
	private void moveOnPath() {
		
	}
	
	/**
	 * Moves in a random direction, when not chasing the player
	 */
	private void randomMovement() {
		Point newLocation = new Point(location.x, location.y);
		int direction = new Random().nextInt(4);
		switch (direction) {
		case 0:
			newLocation.x++;
			break;
		case 1:
			newLocation.x--;
			break;
		case 2:
			newLocation.y++;
			break;
		default:
			newLocation.y--;
			
		}
		this.location = newLocation;
	}
	
	/**
	 * Returns the distance to the player, in tiles
	 * @return
	 */
	private int getDistanceToPlayer() {
		int distance = 0;
		return distance;
	}
	
	private void attack() {
		
	}
}
