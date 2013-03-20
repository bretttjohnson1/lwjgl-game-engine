package survivalGame;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import engine.MobControler;
import engine.Point3d;
import survivalGame.entity.Entity;

public class MobAI {

	Point3d currentTarget;
	MobControler controler;
	Vector3f rot = new Vector3f();
	Level level;
	
	public void setControler(MobControler mc, Level level){
		controler = mc;
	//	controler.setGravity(new Vector3f(0,-98f,0));
		mc.setRot(rot);
		System.out.println("l:" + mc);
		this.level = level;
	}
	
	public void tick(){
		if(currentTarget == null){
			findTarget();			
		}
		controler.moveXZ(new Vector2f((float)(level.player.getLocation().x-controler.getLocation().x),(float)(level.player.getLocation().z-controler.getLocation().z)));
	}
	
	public void findTarget(){
		currentTarget = level.player.getLocation();
	}
}
