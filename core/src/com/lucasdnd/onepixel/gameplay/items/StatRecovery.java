package com.lucasdnd.onepixel.gameplay.items;

public class StatRecovery {
	
	int health, stamina, food, drink;
	
	public StatRecovery(int health, int stamina, int food, int drink) {
		this.health = health;
		this.stamina = stamina;
		this.food = food;
		this.drink = drink;
	}

	public int getHealth() {
		return health;
	}

	public int getStamina() {
		return stamina;
	}

	public int getFood() {
		return food;
	}

	public int getDrink() {
		return drink;
	}
}
