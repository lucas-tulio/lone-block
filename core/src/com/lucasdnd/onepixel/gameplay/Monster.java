package com.lucasdnd.onepixel.gameplay;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.lucasdnd.onepixel.OnePixel;
import com.lucasdnd.onepixel.gameplay.world.MapObject;
import com.lucasdnd.onepixel.gameplay.world.World;
import com.lucasdnd.onepixel.gameplay.world.pathfinder.Path;
import com.lucasdnd.onepixel.gameplay.world.pathfinder.PathFinder;

public class Monster {

	// Basic stuff
	private Point location;
	private static final Color restingColor = Color.ORANGE;
	private static final Color chasingColor = Color.RED;
	
	// Pathfinding
	private Path path;
	private boolean chasing;
	private int attackRange;
	
	// Timers
	private long movementTicks, maxMovementTicks;
	private long detectionTicks, maxDetectionTicks;
	private long chaseTicks, maxChaseTicks;
	private long attackTicks, maxAttackTicks;
	
	public Monster(World world) {
		location = new Point(0, 0);
		maxMovementTicks = 120;
		maxDetectionTicks = 60;
		maxChaseTicks = 15;
		maxAttackTicks = 30;
		attackRange = 1;
	}
	
	public void spawn(Point spawnPoint) {
		this.location.x = spawnPoint.x;
		this.location.y = spawnPoint.y;
	}
	
	public void update(PathFinder pathFinder, int detectionRange) {
		
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
				if (location.x < 0 || location.y < 0) {
					chasing = false;
					return;
				}
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
		
		// Check if it's a valid world coordinate
		World world = ((OnePixel)Gdx.app.getApplicationListener()).getWorld();
		if (newLocation.x < 0 || newLocation.x >= world.getSize() || newLocation.y < 0 || newLocation.y >= world.getSize()) {
			return;
		}
		
		// Check if that position is blocked
		if (world.getMapObjectAt(newLocation.x, newLocation.y) != null) {
			return;
		}
		
		this.location = newLocation;
	}
	
	private void attack() {
		
	}
}
