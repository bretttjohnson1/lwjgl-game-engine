package survivalGame.weapon;

import engine.Model;
import survivalGame.Level;
import survivalGame.VisibleObjectHandler;

public class WeaponAK47 extends RangeWeapon{

	public WeaponAK47( Level level) {
		super((Model) VisibleObjectHandler.getModel("Weapon_AK47"),level);
	}                                                                                               

}
