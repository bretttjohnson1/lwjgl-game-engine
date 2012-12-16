package survivalGame;

import javax.vecmath.Vector4f;


public class EntityLightSun extends EntityLight{

	
	public EntityLightSun(Level level) {
		super(level,0);
	}
	
	public void setTime(float time){
		l.setDiffuse(new Vector4f(time,time,time,time));
		l.setAmbient(new Vector4f(time,time,time,time));
	}

}
