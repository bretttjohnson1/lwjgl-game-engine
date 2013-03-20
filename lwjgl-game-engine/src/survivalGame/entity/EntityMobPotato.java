package survivalGame.entity;

import com.bulletphysics.collision.shapes.CapsuleShape;

import survivalGame.Level;
import survivalGame.MobAI;
import survivalGame.VisibleObjectHandler;
import engine.MobControler;
import engine.VisibleObject;

public class EntityMobPotato extends EntityMob{

	public EntityMobPotato(Level level) {
		super(VisibleObjectHandler.getVisableObject("fail"), level, new MobAI());
		
	}
}
