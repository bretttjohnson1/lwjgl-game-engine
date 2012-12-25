package survivalGame.weapon;

import engine.Model;
import survivalGame.Level;
import survivalGame.VisibleObjectHandler;

public class WeaponPistol extends RangeWeapon {

	public WeaponPistol(Level level) {
		super((Model)VisibleObjectHandler.getModel("Weapon_Pistol_01"), level);
	}
	

}
