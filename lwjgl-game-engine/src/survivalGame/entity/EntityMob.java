package survivalGame.entity;

import survivalGame.Level;
import survivalGame.MobAI;
import engine.MobControler;
import engine.Point3d;
import engine.VisibleObject;

public class EntityMob extends Entity {

	public EntityMob(int vID, Level level,MobAI mobAI) {
		super(vID, level);
		this.mobAI = mobAI;
	}
	
	MobControler controler;
	MobAI mobAI;
	int health;
	int maxHealth;
	
	public int getHealth(){
		return health;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	@Override
	public void tick() {
		if(controler == null) return;
		rendarbleObject.move(controler.getLocation());
	}
}
