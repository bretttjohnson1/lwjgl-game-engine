package survivalGame.entity;

import javax.vecmath.Vector3f;

import survivalGame.Level;
import survivalGame.VisibleObjectHandler;

import engine.Point3d;
import engine.VisibleObject;

public class Entity {
	
	Vector3f location;
	VisibleObject rendarbleObject;
	Level level;
	
	public Entity(int visibleObjectID, Level level){
		if(visibleObjectID > 0)
			this.rendarbleObject = VisibleObjectHandler.getModel(visibleObjectID);
		this.level = level;
		location = new Vector3f();
	}
	
	public VisibleObject getVisableObject(){
		return rendarbleObject;
	}
	
	public void tick(){}
	
	public Vector3f getLocation(){
		return location;
	}
	
	public void setLocation(Vector3f loc){
		this.location = loc;
	}
	
}
