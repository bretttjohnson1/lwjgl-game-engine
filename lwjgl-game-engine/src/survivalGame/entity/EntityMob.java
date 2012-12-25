package survivalGame.entity;

import survivalGame.Level;
import engine.MobControler;
import engine.Point3d;
import engine.VisibleObject;

public class EntityMob extends Entity {

	public EntityMob(int vID, Level level) {
		super(vID, level);
	}
	
	public MobControler controler;
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
