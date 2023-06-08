package model.world;

import java.util.*;

public class Villain extends Champion {
	
	public Villain(String name,int maxHP,int mana,int maxActionPointsPerTurn,int attackRange,int attackDamage,int speed) {
		super(name,maxHP,mana,maxActionPointsPerTurn,attackRange,attackDamage,speed);
	}

	public void useLeaderAbility(ArrayList<Champion> targets) {
		for(int i=0;i<targets.size();i++)
			targets.get(i).setCondition(Condition.KNOCKEDOUT);
	}
}
