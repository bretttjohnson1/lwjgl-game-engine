package survivalGame.entity;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.SphereShape;

import engine.Point3d;

import survivalGame.Level;
import survivalGame.VisibleObjectHandler;

public class EntityProjectileBattleAxe extends EntityProjectile{

	public EntityProjectileBattleAxe(Level level) {
		super(VisibleObjectHandler.getVisableObject("Battle_Axe"), level);
		damage = 5;
		initVel = 50;
		life = 10000;
	}

	@Override
	public EntityProjectile duplicate() {
		return new EntityProjectileBattleAxe(level);
	}
	
	@Override
	public void spawn(Vector3f dir, Vector3f location, Vector3f rot) {
		level.esm.spawnEntity(this, life);
		rendarbleObject.setVisible(true);
		projectileModel.addToPhysWorld(new BoxShape(new Vector3f(.5f,.5f,10)),3f);
		projectileModel.move(new Point3d((-dir.x*1.5 + location.x),dir.y + location.y,-dir.z*1.5 + location.z));
		projectileModel.rot(0, -rot.y, 0);
		projectileModel.rb.applyForce(new Vector3f(-dir.x*initVel,dir.y*initVel,-dir.z*initVel), new Vector3f(0,0,0));
	}
}
