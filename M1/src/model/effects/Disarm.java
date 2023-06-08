package model.effects;

import model.abilities.*;
import model.world.*;

public class Disarm extends Effect {
	
	public Disarm(int duration) {
		super("Disarm",duration,EffectType.DEBUFF);
	}
	
	public void apply(Champion c) {
		DamagingAbility a = new DamagingAbility("Punch",0,1,1,AreaOfEffect.SINGLETARGET,1,50);
		c.getAbilities().add(a);
	}
	
	public void remove(Champion c) {
		for(int i=0;i<c.getAbilities().size();i++)
			if (c.getAbilities().get(i).getName().equals("Punch"))
					c.getAbilities().remove(c.getAbilities().get(i));
	}
}