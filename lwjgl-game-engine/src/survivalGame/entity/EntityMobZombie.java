package survivalGame.entity;

import survivalGame.Level;
import survivalGame.MobAIZombie;
import survivalGame.VisibleObjectHandler;

import com.bulletphysics.collision.shapes.CapsuleShape;

import engine.MobControler;
import engine.VisibleObject;

public class EntityMobZombie extends EntityMob{
	
	public EntityMobZombie(Level level) {
		super(VisibleObjectHandler.getVisableObject("Entity_Mob_Zombie"), level,new MobAIZombie());
		controler = new MobControler(new CapsuleShape(1, 1.5f), level.renderWorld, 1f);
		mobAI.setControler(controler);
	}
	
}
