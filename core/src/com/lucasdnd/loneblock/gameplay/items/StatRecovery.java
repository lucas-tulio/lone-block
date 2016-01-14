package com.lucasdnd.loneblock.gameplay.items;

public class StatRecovery {
	
	private int health, cold, food, drink;
	
	public StatRecovery(int health, int cold, int food, int drink) {
		this.health = health;
		this.cold = cold;
		this.food = food;
		this.drink = drink;
	}

	public int getHealth() {
		return health;
	}

	public int getCold() {
		return cold;
	}

	public int getFood() {
		return food;
	}

	public int getDrink() {
		return drink;
	}
}
