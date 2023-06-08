package model.world;

import java.util.*;
import model.effects.*;

public class Hero extends Champion {
	
	public Hero(String name, int maxHP, int mana, int maxActionPointsPerTurn,int speed, int attackRange, int attackDamage) {
		super(name,maxHP,mana,maxActionPointsPerTurn,speed,attackRange,attackDamage);
	}

	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(int i=0;i<targets.size();i++) {
			Embrace e = new Embrace(2);
			e.apply(targets.get(i));
			((Champion) targets.get(i)).getAppliedEffects().add(e);
			for (int j = 0; j < ((Champion) targets.get(i)).getAppliedEffects().size(); j++) {
				if (((Champion) targets.get(i)).getAppliedEffects().get(j).getType() == EffectType.DEBUFF) {
					((Champion) targets.get(i)).getAppliedEffects().get(j).remove(((Champion) targets.get(i)));
					((Champion) targets.get(i)).getAppliedEffects().remove(((Champion) targets.get(i)).getAppliedEffects().get(j));
					j--;
				}
			}
		}
	}
}
