package survivalGame;


import javax.vecmath.Vector3f;

import org.lwjgl.Sys;

import com.bulletphysics.collision.dispatch.CollisionObject;

import engine.AnimatedModel;
import engine.AnimationPlayer;
import engine.AnimationSet;
import engine.Frame;
import engine.Line;
import engine.Model;
import engine.VisibleObject;
import engine.World;

public class Weapon {
	
	public Weapon(int weaponModelID, int WeaponItemId, Level level){
		weaponModel = VisibleObjectHandler.getModel(weaponModelID);
		this.WeaponItemID = weaponModelID;
		this.level = level;
	}
	
	public int WeaponItemID;
	public int id;	
	public Model weaponModel;
	public int clipSize;	
	public EntityProjectile projectile;
	AnimationSet reCoil;
	AnimationPlayer ap;
	int animationID;
	Level level;
	//EntitySpawnManager esm;
	
	public void shoot(Vector3f dir, Vector3f location, Vector3f rot){
		ap.playAnimation(id, 100,false);
	//	EntityProjectileBullet epb = new EntityProjectileBullet(level);
	//	epb.spawn(dir, location, rot);
		dir.scale(3000);
		dir.add(location);
		CollisionObject co = level.renderWorld.rayTest(location, dir);
	//	System.out.println(co);
	//	Line l = new Line(location, dir);
	//	level.renderWorld.addObject(l);
	}
	
	public void creatRecoilAnimation(){
		reCoil = new AnimationSet(weaponModel, 12);
		for(int i=0;i<12;i++){
			Frame f = new Frame(weaponModel);
			f.rx += i;
			reCoil.setFrame(f, i);
		}
		/*for(int i=5;i<12;i++){
			Frame f = new Frame(weaponModel);
			f.rx -= i;
			reCoil.setFrame(f, i);
		}*/
		ap = new AnimationPlayer(reCoil);
		animationID = ap.defineAnimation(0, 12);
	}

}
