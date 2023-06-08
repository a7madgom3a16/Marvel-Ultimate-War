package model.abilities;

import java.awt.Point;
import java.util.*;
import model.world.*;

public class HealingAbility extends Ability {
	private int healAmount;
	
	public HealingAbility(String name,int manaCost,int baseCooldown,int castRange,AreaOfEffect castArea,int requiredActionPoints,int healAmount) {
		super(name,manaCost,baseCooldown,castRange,castArea,requiredActionPoints);
		this.healAmount=healAmount;
	}

	public int getHealAmount() {
		return healAmount;
	}

	public void setHealAmount(int healAmount) {
		this.healAmount = healAmount;
	}
	
	public void execute(ArrayList<Damageable> targets) {
		for(int i=0;i<targets.size();i++)
			if (targets.get(i) instanceof Champion)
				targets.get(i).setCurrentHP(targets.get(i).getCurrentHP()+healAmount);
	}
	
	public Point getLocation() {
		return null;
	}

	public int getCurrentHP() {
		return 0;
	}

	public void setCurrentHP(int hp) {
		
	}
}
