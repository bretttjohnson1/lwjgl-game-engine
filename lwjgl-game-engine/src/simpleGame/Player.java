package simpleGame;

import javax.vecmath.Vector3f;

import engine.*;
import com.bulletphysics.*;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CylinderShape;

public class Player {
	
	AnimatedModel model;
	
	public Player(AnimatedModel model,World world){
		this.model = model;
		
		CollisionShape playerCollisionShape = new CylinderShape(new Vector3f(2,1,1));
		world.addObject(model);
		model.addToPhysWorld(playerCollisionShape, 10f);
		
	}

}
