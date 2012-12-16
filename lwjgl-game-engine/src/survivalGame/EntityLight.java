package survivalGame;

import java.awt.Color;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import engine.Color3f;
import engine.Light;

public class EntityLight extends Entity{

	
	Light l;
	public EntityLight( Level level, int id) {
		super(0, level);
		l = new Light(0, 0, 0, id);
		l.setDiffuse(new Vector4f(1,1,1,1));
		l.setAmbient(new Vector4f(0.1f,0.1f,0.1f,0.3f));
	}
	
	@Override
	public void setLocation(Vector3f loc) {
		l.move(loc);
		super.setLocation(loc);
	}
	
}
