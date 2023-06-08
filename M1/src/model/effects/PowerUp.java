package model.effects;

import model.world.*;
import model.abilities.*;
import java.util.*;

public class PowerUp extends Effect {

	public PowerUp(int duration) {
		super("PowerUp",duration,EffectType.BUFF);
	}
	
	public void apply(Champion c) {
		ArrayList<Ability> a = c.getAbilities();
		for(int i=0;i<a.size();i++) {
			if (a.get(i) instanceof DamagingAbility)
				((DamagingAbility) a.get(i)).setDamageAmount((int)(((DamagingAbility) a.get(i)).getDamageAmount()*1.2));
			else if (a.get(i) instanceof HealingAbility)
				((HealingAbility) a.get(i)).setHealAmount((int)(((HealingAbility) a.get(i)).getHealAmount()*1.2));
			else
				continue;
		}
	}
	
	public void remove(Champion c) {
		ArrayList<Ability> a = c.getAbilities();
		for(int i=0;i<a.size();i++) {
			if (a.get(i) instanceof DamagingAbility)
				((DamagingAbility) a.get(i)).setDamageAmount((int)(((DamagingAbility) a.get(i)).getDamageAmount()/1.2));
			else if (a.get(i) instanceof HealingAbility)
				((HealingAbility) a.get(i)).setHealAmount((int)(((HealingAbility) a.get(i)).getHealAmount()/1.2));
			else
				continue;
		}
	}
}
