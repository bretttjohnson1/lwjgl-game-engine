package survivalGame.entity;

import survivalGame.Level;
import survivalGame.VisibleObjectHandler;
import engine.VisibleObject;

public class EntityAnimated extends Entity{

	public EntityAnimated(int visibleObjectID, Level level) {
		super(-1, level);
		rendarbleObject = VisibleObjectHandler.getAnimatedModel(visibleObjectID);
	}
	

}
