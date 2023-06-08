package model.abilities;

import java.util.*;
import model.world.*;

abstract public class Ability implements Damageable {
	private String name;
	private int manaCost;
	private int baseCooldown;
	private int currentCooldown;
	private int castRange;
	private int requiredActionPoints;
	private AreaOfEffect castArea;
	
	public Ability(String name,int manaCost,int baseCooldown,int castRange,AreaOfEffect castArea,int requiredActionPoints) {
		this.name=name;
		this.manaCost=manaCost;
		this.baseCooldown=baseCooldown;
		this.castRange=castRange;
		this.requiredActionPoints=requiredActionPoints;
		this.castArea=castArea;
	}

	public String getName() {
		return name;
	}

	public int getManaCost() {
		return manaCost;
	}

	public int getBaseCooldown() {
		return baseCooldown;
	}

	public int getCurrentCooldown() {
		return currentCooldown;
	}

	public int getCastRange() {
		return castRange;
	}

	public int getRequiredActionPoints() {
		return requiredActionPoints;
	}

	public AreaOfEffect getCastArea() {
		return castArea;
	}
	
	public void setCurrentCooldown(int currentCooldown) {
		if (currentCooldown>baseCooldown)
			this.currentCooldown=baseCooldown;
		else if (currentCooldown<0)
			this.currentCooldown=0;
		else
			this.currentCooldown=currentCooldown;
	}
	
	abstract public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException;
}