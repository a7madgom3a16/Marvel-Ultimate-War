package model.abilities;

import java.awt.Point;
import java.util.*;
import model.world.*;

public class DamagingAbility extends Ability {
	private int damageAmount;
	
	public DamagingAbility(String name,int manaCost,int baseCooldown,int castRange,AreaOfEffect castArea,int requiredActionPoints,int damageAmount) {
		super(name,manaCost,baseCooldown,castRange,castArea,requiredActionPoints);
		this.damageAmount=damageAmount;
	}

	public int getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(int damageAmount) {
		this.damageAmount = damageAmount;
	}
	
	public void execute(ArrayList<Damageable> targets) {
		for(int i=0;i<targets.size();i++)
			targets.get(i).setCurrentHP(targets.get(i).getCurrentHP()-damageAmount);
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
