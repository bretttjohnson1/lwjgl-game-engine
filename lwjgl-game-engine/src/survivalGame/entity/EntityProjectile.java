package survivalGame.entity;

import javax.vecmath.Vector3f;

import survivalGame.Level;
import survivalGame.VisibleObjectHandler;

import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.Transform;

import engine.Model;
import engine.Point3d;
import engine.Utils;
import engine.VisibleObject;

public class EntityProjectile extends Entity{


	
	public EntityProjectile(VisibleObject vo,Level level){
		super(vo, level);
		projectileModel = ((Model) vo);
		rendarbleObject = projectileModel;
		
	}
	
	Model projectileModel;
	Vector3f location;
	int damage = 0;
	int initVel = 0;	
	int life = 400;
	
	
	public int getDamege(){
		return damage;
	}
	
	public int getInitVel(){
		return initVel;
	}
	
	public void entityCollision(Entity e, Point3d location){
		if(e instanceof EntityMob){
			((EntityMob) e).health -= damage;
			level.removeEntity(e);
		}
		else{
			level.removeEntity(e);
		}
	}
	
	public void spawn(Vector3f dir, Vector3f location, Vector3f rot){
		level.esm.spawnEntity(this, life);
		rendarbleObject.setVisible(true);
		projectileModel.addToPhysWorld(new SphereShape(0.3f),0.2f);
		projectileModel.move(new Point3d((-dir.x*1.5 + location.x),dir.y + location.y,-dir.z*1.5 + location.z));
		projectileModel.rot(0, -rot.y, 0);
		projectileModel.rb.applyForce(new Vector3f(-dir.x*initVel,dir.y*initVel,-dir.z*initVel), new Vector3f(0,0,0));
	}
	public EntityProjectile duplicate(){
		return new EntityProjectile(rendarbleObject, level);
	}

	
}
