package model.world;

import java.util.*;
import model.effects.*;

public class AntiHero extends Champion {
	
	public AntiHero(String name,int maxHP,int mana,int maxActionPointsPerTurn,int speed,int attackRange,int attackDamage) {
		super(name,maxHP,mana,maxActionPointsPerTurn,speed,attackRange,attackDamage);
	}

	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(int i=0;i<targets.size();i++) {
			Stun s = new Stun(2);
			s.apply(targets.get(i));
			((Champion) targets.get(i)).getAppliedEffects().add(s);
		}
	}	
}
