package survivalGame.entity;

import survivalGame.Level;
import survivalGame.VisibleObjectHandler;

public class EntityProjectileBullet extends EntityProjectile{

	public EntityProjectileBullet(Level level) {
		super(VisibleObjectHandler.getVisableObject("Entity_Projectile_Bullet"), level);
	//	super(VisibleObjectHandler.getVisableObject("Weapon_Pistol_01"), level);
		damage = 5;
		initVel = 5000;
	}

}
