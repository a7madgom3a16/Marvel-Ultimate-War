package model.abilities;

import java.awt.Point;
import java.util.*;
import model.world.*;
import model.effects.*;

public class CrowdControlAbility extends Ability {
	private Effect effect;
	
	public CrowdControlAbility(String name,int manaCost,int baseCooldown,int castRange,AreaOfEffect castArea,int requiredActionPoints,Effect effect) {
		super(name,manaCost,baseCooldown,castRange,castArea,requiredActionPoints);
		this.effect=effect;
	}

	public Effect getEffect() {
		return effect;
	}
	
	public void execute(ArrayList<Damageable> targets) throws CloneNotSupportedException {
		for(int i=0;i<targets.size();i++)
			if (targets.get(i) instanceof Champion) {
				((Champion) targets.get(i)).getAppliedEffects().add((Effect) effect.clone());
				effect.apply((Champion)targets.get(i));
			}
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
