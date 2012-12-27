package survivalGame.weapon;

import engine.Model;
import survivalGame.Level;
import survivalGame.VisibleObjectHandler;

public class WeaponAK47 extends WeaponRange{

	public WeaponAK47( Level level) {
		super((Model) VisibleObjectHandler.getVisableObject("Weapon_AK47"),level);
	}                                                                                               

}
