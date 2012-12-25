package survivalGame.weapon;


import javax.vecmath.Vector3f;

import org.lwjgl.Sys;

import survivalGame.Level;
import survivalGame.VisibleObjectHandler;
import survivalGame.entity.EntityProjectile;

import com.bulletphysics.collision.dispatch.CollisionObject;

import engine.AnimatedModel;
import engine.AnimationPlayer;
import engine.AnimationSet;
import engine.Frame;
import engine.Line;
import engine.Model;
import engine.VisibleObject;
import engine.World;

public class RangeWeapon extends Weapon{
	
	public RangeWeapon(Model model, Level level){
		super(model,level);
	}
	
	
	public int clipSize;	
	public EntityProjectile projectile;
	AnimationSet reCoil;
	AnimationPlayer ap;
	int animationID;
	//EntitySpawnManager esm;
	
	public void use(Vector3f dir, Vector3f location, Vector3f rot){
		ap.playAnimation(animationID, 100,false);	
		dir.scale(3000);
		dir.add(location);
		CollisionObject co = level.renderWorld.rayTest(location, dir);	
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
