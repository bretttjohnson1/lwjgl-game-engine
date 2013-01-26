package engine;

import javax.vecmath.Vector3f;

import com.bulletphysics.dynamics.RigidBody;

public interface CollisionListener {

	public void collisionOccured(Object b,Object call,Vector3f loc);
}
