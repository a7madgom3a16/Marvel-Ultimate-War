package model.effects;

import model.world.*;

public class Root extends Effect {

	public Root(int duration) {
		super("Root",duration,EffectType.DEBUFF);
	}
	
	public void apply(Champion c) {
		if(c.getCondition().equals(Condition.INACTIVE))
			return;
		c.setCondition(Condition.ROOTED);
	}
	
	public void remove(Champion c) {
		if(c.getCondition().equals(Condition.INACTIVE))
			return;
		if(c.getCondition().equals(Condition.ROOTED))
			for(int i=0;i<c.getAppliedEffects().size();i++)
				if(c.getAppliedEffects().get(i) instanceof Root)
					return;
		c.setCondition(Condition.ACTIVE);
	}
}
