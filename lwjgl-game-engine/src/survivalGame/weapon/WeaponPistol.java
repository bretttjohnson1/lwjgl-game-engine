package survivalGame.weapon;

import engine.Model;
import survivalGame.Level;
import survivalGame.VisibleObjectHandler;

public class WeaponPistol extends WeaponRange {

	public WeaponPistol(Level level) {
		super((Model)VisibleObjectHandler.getVisableObject("Weapon_Pistol_01"), level);
	//	super((Model)VisibleObjectHandler.getVisableObject("Entity_Projectile_Bullet"), level);
	}
	

}
