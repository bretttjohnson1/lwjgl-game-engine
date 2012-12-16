package survivalGame;

public class EntityProjectileBullet extends EntityProjectile{

	public EntityProjectileBullet(Level level) {
		super(VisibleObjectHandler.Bullet, level);
		damage = 5;
		initVel = 500;
	}

}
