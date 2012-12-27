package survivalGame;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import survivalGame.entity.EntityLightFlashLight;
import survivalGame.weapon.WeaponRange;
import survivalGame.weapon.Weapon;

import com.bulletphysics.collision.shapes.CapsuleShape;

import engine.Camera;
import engine.MobControler;
import engine.Model;
import engine.Point3d;
import engine.SpotLight;
import engine.Utils;
import engine.World;

public class Player {
	Weapon weapon;
	MobControler mobControler;
	Model wModel = null;
	public Inventory inventory;
	World world;
	Level level;
	double speed;
	double oSpeed;
	double runFactor;
	float jumpForce;
	public int maxWeight = 1000;
	EntityLightFlashLight flashLight;
	Camera camera;
	
	
	public Player(Level level, double speed, double runFactor, float jumpForce,Camera camera){
		world = level.renderWorld;
		this.level = level;
		oSpeed = speed;
		this.runFactor = runFactor;
		this.jumpForce = jumpForce;
		mobControler = new MobControler(new CapsuleShape(1,3f),world,20);
		//Mouse.setGrabbed(true);
		mobControler.warp(new Vector3f(0,0,0));
		inventory = new Inventory(maxWeight);
		flashLight = new EntityLightFlashLight(level);		
		level.addEntity(flashLight);
		this.camera = camera;
		level.setPlayer(this);
	}
	
	float roty;
	float rotx;
	boolean gPressed = false;
	//float protx;
	//float proty;
	
	public void tick(){		
		//proty = (float) camera.roty;
		//protx = (float) camera.rotx;
		camera.rotx -= ((double) Mouse.getDY())/4;
		camera.roty += ((double) Mouse.getDX())/4;		
		boolean moving = false;
		if(!(camera.rotx<-89 || camera.rotx > 89)){
			rotx = (float) camera.rotx;
		}else{
			camera.rotx = rotx;
		}
			roty = (float) camera.roty;
		speed = oSpeed;
		Vector3f loc = camera.locAsVecotr3f();
//		loc.negate();
		flashLight.setLocation(loc);		
		Vector3f dir = new Vector3f((float) (Math.sin(roty*Utils.FACTOR_DEG_TO_RAD)),(float) (-Math.sin(rotx*Utils.FACTOR_DEG_TO_RAD)),(float) (-Math.cos(roty*Utils.FACTOR_DEG_TO_RAD)));
		flashLight.setDirection(dir);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) speed *=runFactor;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			mobControler.jump(jumpForce);
		}		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			mobControler.moveXZ(new Vector2f((float)  (-Math.sin(roty*Utils.FACTOR_DEG_TO_RAD)*speed),(float) (Math.cos(roty*Utils.FACTOR_DEG_TO_RAD)*speed)));
			moving = true;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			mobControler.moveXZ(new Vector2f((float) (Math.sin(roty*Utils.FACTOR_DEG_TO_RAD)*speed),(float) (-Math.cos(roty*Utils.FACTOR_DEG_TO_RAD)*speed)));
			moving = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			double x,z;
			x = -Math.sin((roty-90)*Utils.FACTOR_DEG_TO_RAD)*speed;
			z = +Math.cos((roty-90)*Utils.FACTOR_DEG_TO_RAD)*speed;
			mobControler.moveXZ(new Vector2f((float)x,(float)z));
			moving = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			double x,z;
			x = +Math.sin((roty-90)*Utils.FACTOR_DEG_TO_RAD)*speed;
			z = -Math.cos((roty-90)*Utils.FACTOR_DEG_TO_RAD)*speed;
			mobControler.moveXZ(new Vector2f((float)x,(float)z));
			moving = true;
		}	
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
		//	Vector3f newLoc = mobControler.getLocation().asVector3f();
		//	newLoc.y += 10;
		//	mobControler.warp(newLoc);
			mobControler.jumpNOCheck(100);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			//	Vector3f newLoc = mobControler.getLocation().asVector3f();
			//	newLoc.y += 10;
			//	mobControler.warp(newLoc);
				mobControler.jumpNOCheck(-100);
			}
		if(Keyboard.isKeyDown(Keyboard.KEY_G)){
			if(!gPressed){
				gPressed = true;
				if(Math.abs(mobControler.getGravity().y)>0){
					mobControler.setGravity(new Vector3f());
				}else{
					Vector3f out = new Vector3f();
					world.physWorld.getGravity(out);
					mobControler.setGravity(out);
				}
			}
		}else{
			gPressed = false;
		}
			
		if(!moving){
			mobControler.moveXZ(new Vector2f(0,0));
		}
		
		
		Point3d mLoc = mobControler.getLocation();
		camera.x = mLoc.x;
		camera.y = mLoc.y-1;
		camera.z = mLoc.z;
		//System.out.println(mobControler.checkCollisionWithOtherObject());
		if(weapon != null){
			weapon.tick();
		}
	}
	
	public void physTick(){
		if(Mouse.isButtonDown(0)) {
			if(weapon != null) {
				System.out.println(rotx);
				weapon.use(new Vector3f((float) (Math.sin(roty*Utils.FACTOR_DEG_TO_RAD)),(float) (-Math.sin(rotx*Utils.FACTOR_DEG_TO_RAD)),(float) (-Math.cos(roty*Utils.FACTOR_DEG_TO_RAD))), camera.locAsVecotr3f(), camera.rotAsVecotr3f());
			}
		}
	}
	
	public void setWeapon(Weapon weapon2){
		if(weapon != null) world.removeVisibleHUDObject(weapon.weaponModel);
		this.weapon = weapon2;
		wModel = weapon2.weaponModel;
		world.addVisisbleHUDObject(wModel);
		wModel.setVisable(true);
		wModel.move(new Point3d(0.2f,-0.5f,0.1f));
		wModel.rot(0, 0, 0);
		if(weapon instanceof WeaponRange)
		((WeaponRange) weapon).creatRecoilAnimation();
	}
	public Point3d getLocation(){
		return mobControler.getLocation().duplicate();
	}
	public void setLocation(Point3d newLoc){
		mobControler.warp(newLoc.asVector3f());
	}
	
}
