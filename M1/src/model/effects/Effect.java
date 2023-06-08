package model.effects;

import model.world.*;

abstract public class Effect implements Cloneable {
	private String name;
	private int duration;
	private EffectType type;
	
	public Effect(String name,int duration,EffectType type) {
		this.name=name;
		this.duration=duration;
		this.type=type;
	}

	public String getName() {
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public EffectType getType() {
		return type;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	abstract public void apply(Champion c);
	abstract public void remove(Champion c);
}
