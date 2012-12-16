package survivalGame;

import engine.VisibleObject;

public class AnimatedEntity extends Entity{

	public AnimatedEntity(int visibleObjectID, Level level) {
		super(-1, level);
		rendarbleObject = VisibleObjectHandler.getAnimatedModel(visibleObjectID);
	}
	

}
