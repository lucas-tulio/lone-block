package com.lucasdnd.onepixel.gameplay;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.TimeController;
import com.lucasdnd.onepixel.gameplay.world.World;
import com.lucasdnd.onepixel.gameplay.world.pathfinder.AStarPathFinder;
import com.lucasdnd.onepixel.gameplay.world.pathfinder.Path;

public class Monster {

	// Basic stuff
	private Point location;
	private static final Color restingColor = Color.ORANGE;
	private static final Color chasingColor = Color.RED;
	
	// Pathfinding
	private boolean chasing;
	private int detectionRange, attackRange;
	private Path path;
	private AStarPathFinder pathFinder;
	
	// Timers
	private long movementTicks, maxMovementTicks;
	private long detectionTicks, maxDetectionTicks;
	private long chaseTicks, maxChaseTicks;
	private long attackTicks, maxAttackTicks;
	
	public Monster(World world) {
		maxMovementTicks = 180;
		maxDetectionTicks = 60;
		maxChaseTicks = 60;
		maxAttackTicks = 30;
		
		detectionRange = 45;
		attackRange = 1;
		
		pathFinder = new AStarPathFinder(world, detectionRange, false);
	}
	
	public void spawn() {
		Player player = ((OnePixel)Gdx.app.getApplicationListener()).getPlayer();
		int spawnDistance = 80;
		Random r = new Random();
		int x = player.getX() + r.nextInt(spawnDistance) - (spawnDistance / 2);
		int y = player.getY() + r.nextInt(spawnDistance) - (spawnDistance / 2);
		this.location = new Point(x, y);
	}
	
	public void update() {
		
		if (chasing == false) {
			
			// Random movement
			movementTicks++;
			if (movementTicks % maxMovementTicks == 0) {
				randomMovement();
				return;
			}
			
			// Player detection
			detectionTicks++;
			if (detectionTicks % maxDetectionTicks == 0) {
				
				Player player = ((OnePixel)Gdx.app.getApplicationListener()).getPlayer();
				path = pathFinder.findPath(player, location.x, location.y, player.getX(), player.getY());
				chasing = (path != null && path.getLength() <= detectionRange);
			}
		
		
		} else {
			
		
			// Chases the player
			chaseTicks++;
			if (chaseTicks % maxChaseTicks == 0) {
				
				Player player = ((OnePixel)Gdx.app.getApplicationListener()).getPlayer();
				path = pathFinder.findPath(player, location.x, location.y, player.getX(), player.getY());

				if (path != null && path.getLength() > 0) {
					Path.Step step = path.getStep(1); // Next step only
					location.x = step.getX();
					location.y = step.getY();
				}
				
				// Attacks
				if (path != null && path.getLength() <= attackRange) {
					attack();
				}
				
				if (path == null) {
					chasing = false;
				}
			}
		}
	}
	
	public void render(ShapeRenderer sr) {
		if (chasing) {
			sr.setColor(chasingColor);
		} else {
			sr.setColor(restingColor);
		}
		
		sr.rect(location.x * OnePixel.blockSize, location.y * OnePixel.blockSize, OnePixel.blockSize, OnePixel.blockSize);
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
	
	private void attack() {
		
	}
}
