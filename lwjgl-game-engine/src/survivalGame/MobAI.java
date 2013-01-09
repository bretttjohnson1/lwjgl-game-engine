package survivalGame;

import javax.vecmath.Vector3f;

import engine.MobControler;
import engine.Point3d;
import survivalGame.entity.Entity;

public class MobAI {

	Point3d currentTarget;
	MobControler controler;
	Vector3f rot = new Vector3f();
	
	public void setControler(MobControler mc){
		controler = mc;
		mc.setRot(rot);
	}
	
	public void tick(){
		if(currentTarget == null){ 
			findTarget();
			return;
		}
	}
	
	public void findTarget(){
		
	}
}
