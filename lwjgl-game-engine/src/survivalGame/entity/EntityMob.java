package survivalGame.entity;

import javax.vecmath.Vector3f;

import survivalGame.Level;
import survivalGame.MobAI;
import survivalGame.item.Item;
import engine.AnimatedModel;
import engine.AnimationPlayer;
import engine.MobControler;
import engine.Point3d;
import engine.Vector3d;
import engine.VisibleObject;

public class EntityMob extends Entity {

	public EntityMob(VisibleObject vo, Level level,MobAI mobAI) {
		super(vo, level);
		this.mobAI = mobAI;
		createWalkingAnimation();
	}
	
	MobControler controler;
	MobAI mobAI;
	float health;
	int maxHealth;
	int defence = 0;
	AnimationPlayer ap;
	int walkingAnimation = 0;
	
	public float getHealth(){
		return health;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	@Override
	public void tick() {
		if(controler == null) return;
		if(mobAI == null) return;
		rendarbleObject.move(controler.getLocation());
		rendarbleObject.rot(controler.getRot());
		mobAI.tick();
		walk();
	}
	
	public void createWalkingAnimation(){
		if(!(rendarbleObject instanceof AnimatedModel)) return;
		AnimatedModel am = (AnimatedModel)rendarbleObject;
		ap = new AnimationPlayer(am);
		walkingAnimation = ap.defineAnimation(0, 50);
	}
	
	public void walk(){
		if(ap == null) return;
		Vector3f temp = new Vector3f();
		controler.getVelocity(temp);
		if((Math.abs(temp.x))>0.2 || (Math.abs(temp.x)>0.2)){
			ap.playAnimation(walkingAnimation, 30, true);
		}else{
			ap.stopAnimation();
		}
	}
	public void equip(Item item){
		//TODO: Write general equip code
	}
	public void kill(){
		//TODO: death animation here and despawn
		dropItem();
	}
	public void dropItem(){
		
	}
	public void damage(float damage){
		if(damage > defence){
			health -= (damage - defence);			
		}
		if(health <= 0){
			kill();
		}
	}
	
}
