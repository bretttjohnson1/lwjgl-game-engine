package survivalGame.weapon;


import javax.vecmath.Vector3f;

import org.lwjgl.Sys;

import survivalGame.Level;
import survivalGame.VisibleObjectHandler;
import survivalGame.entity.EntityProjectile;
import survivalGame.entity.EntityProjectileBullet;

import com.bulletphysics.collision.dispatch.CollisionObject;

import engine.AnimatedModel;
import engine.AnimationPlayer;
import engine.AnimationSet;
import engine.Frame;
import engine.Line;
import engine.Model;
import engine.VisibleObject;
import engine.World;

public class WeaponRange extends Weapon{
	
	public WeaponRange(Model model, Level level){
		super(model,level);
		
	}
	
	
	public int clipSize;	
	public EntityProjectile projectile;
	AnimationSet reCoil;
	AnimationPlayer ap;
	int animationID;
	int ticksPerFire = 10;
	int deltaTicks = 0;
	//EntitySpawnManager esm;
	
	public void use(Vector3f dir, Vector3f location, Vector3f rot){
		if(!(deltaTicks>=ticksPerFire)) return;
		projectile = new EntityProjectileBullet(level);
		deltaTicks = 0;
		ap.playAnimation(animationID, 100,false);	
		dir.scale(3000);
		dir.add(location);
		projectile.spawn(dir, location, rot);
	//	CollisionObject co = level.renderWorld.rayTest(location, dir);	
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
	
	public void tick(){
		deltaTicks++;
		if(deltaTicks > 100000){
			deltaTicks = ticksPerFire + 1;
		}
	}

}
