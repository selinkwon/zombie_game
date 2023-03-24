package zombie_game;

import java.util.Random;

abstract class Unit {
	 private int travel;
	 private int hp;
	 private int max;
	 private String name;
	 public Random ran;
	 
	 public Unit(int travel, int hp, int max, String name) {
		 this.travel = travel;
		 this.hp = hp;
		 this.max = max;
		 this.name = name;
		 this.ran = new Random();
	 }
	 
	public int getTravel() {
		return this.travel;
	}
	public void setTravel(int travel) {
		this.travel = travel;
	}
	public int getHp() {
		return this.hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMax() {
		return this.max;
	}
	public String getName() {
		return this.name;
	}
	
	
	
	abstract void attack(Unit unit);
	 
}
