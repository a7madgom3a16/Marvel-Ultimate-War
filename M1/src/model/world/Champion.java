package model.world;

import model.effects.*;
import model.abilities.*;
import java.awt.*;
import java.util.*;

@SuppressWarnings("rawtypes")
abstract public class Champion implements Damageable,Comparable {
	private String name;
	private int maxHP;
	private int currentHP;
	private int mana;
	private int maxActionPointsPerTurn;
	private int currentActionPoints;
	private int attackRange;
	private int attackDamage;
	private int speed;
	private ArrayList<Ability> abilities;
	private ArrayList<Effect> appliedEffects;
	private Condition condition;
	private Point location;
	
	public Champion(String name,int maxHP,int mana,int maxActionPointsPerTurn,int speed,int attackRange,int attackDamage) {
		this.name=name;
		this.maxHP=maxHP;
		this.mana=mana;
		this.maxActionPointsPerTurn=maxActionPointsPerTurn;
		this.currentActionPoints=maxActionPointsPerTurn;
		this.attackRange=attackRange;
		this.attackDamage=attackDamage;
		this.speed=speed;
		this.currentHP=maxHP;
		this.abilities=new ArrayList<Ability>();
		this.appliedEffects=new ArrayList<Effect>();
		this.condition=Condition.ACTIVE;
	}
	
	public String toString() {
		String s = "";
		s+= "Name: " + this.name + "\n" + "MaxHp: " + this.maxHP + "\n" + "CurrentHP: " + this.currentHP + "\n" + "Mana: " +mana + "\n"
				 + "MaxActionPointsPerTurn: " + this.maxActionPointsPerTurn + "\n" + "CurrentActionPoints: " + this.currentActionPoints + "\n" +
				"Attack Range: " + this.attackRange + "\n" + "Attack Damage: " + this.attackDamage 
				+ "\n" + "Speed: " + this.speed + "\n" + "Abilities: " + "\n" + " 1) " + this.abilities.get(0).getName() 
				+ "\n" + " 2) "+this.abilities.get(1).getName() + "\n" + " 3) "+this.abilities.get(2).getName();
		return s;
	}

	public String getName() {
		return name;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public int getCurrentHP() {
		return currentHP;
	}

	public int getMana() {
		return mana;
	}

	public int getMaxActionPointsPerTurn() {
		return maxActionPointsPerTurn;
	}

	public int getCurrentActionPoints() {
		return currentActionPoints;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public int getSpeed() {
		return speed;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public ArrayList<Effect> getAppliedEffects() {
		return appliedEffects;
	}

	public Condition getCondition() {
		return condition;
	}

	public Point getLocation() {
		return location;
	}

	public void setCurrentHP(int currentHP) {
		if (currentHP>maxHP)
			this.currentHP=maxHP;
		else if (currentHP<0)
			this.currentHP=0;
		else
			this.currentHP=currentHP;
	}

	public void setMana(int mana) {
		this.mana=mana;
	}

	
	public void setMaxActionPointsPerTurn(int maxActionPointsPerTurn) {
		this.maxActionPointsPerTurn = maxActionPointsPerTurn;
	}
	
	public void setCurrentActionPoints(int currentActionPoints) {
		if (currentActionPoints>maxActionPointsPerTurn)
			this.currentActionPoints=maxActionPointsPerTurn;
		else if (currentActionPoints<0)
			this.currentActionPoints=0;
		else
			this.currentActionPoints=currentActionPoints;
	}

	public void setAttackDamage(int attackDamage) {
		this.attackDamage = attackDamage;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public int compareTo(Object o) {
		Champion c = (Champion) o;
		if (c.speed<this.speed)
			return -1;
		else if (c.speed>this.speed)
			return 1;
		else
			return (this.name.compareTo(c.name));
	}
	
	abstract public void useLeaderAbility(ArrayList<Champion> targets);
}