package survivalGame;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import engine.SpotLight;

public class EntityLightFlashLight extends EntityLight{

	public EntityLightFlashLight(Level level) {
		super(level, 1);
		l = new SpotLight(0, 0, 0, 1, 15);
		l.setAmbient(new Vector4f(0.3f, 0.3f, 0.3f, 0.3f));
		l.setDiffuse(new Vector4f(1, 1, 1, 1));
	}
	
	public void setCutoff(float cutoff){
		((SpotLight) l).setCutoff(cutoff);
	}
	
	
	public void setDirection(Vector3f dir){
		((SpotLight) l).serDirection(dir);
	}

}
