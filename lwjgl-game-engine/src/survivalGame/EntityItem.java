package survivalGame;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;

import engine.Model;

public class EntityItem extends Entity{
	
	public EntityItem(Level level, Item item) {
		super(0, level);
		this.rendarbleObject = item.model.duplicate();
	}

	Item item;
	public void addedToLevel(){
		Model m = (Model) rendarbleObject;
		m.addToPhysWorld(0.5f);//, new BoxShape(new Vector3f(1f,0.3f,1f)));
		m.rb.setAngularFactor(0);
	}
}
