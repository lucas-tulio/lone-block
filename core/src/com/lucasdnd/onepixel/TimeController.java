package com.lucasdnd.onepixel;

public class TimeController {
	
	long ticks;
	final long fps = 60;
	
	long time;	// 1 time == one real second
	long oneDay = 600;	// how many seconds in one day. 3600 = 10 minutes 
	
	protected void update() {
		ticks++;
		if (ticks % fps == 0) {
			time++;
		}
	}
	
	public boolean isNight() {
		return (int)(time % oneDay) > oneDay / 2;
	}
	
	public int getHourOfDay() {
		return (int)(time % oneDay);
	}
	
	public int getDay() {
		return (int)(time / oneDay);
	}

	public long getTicks() {
		return ticks;
	}

	public long getTime() {
		return time;
	}
	
}
