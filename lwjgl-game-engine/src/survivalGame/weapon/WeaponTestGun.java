package survivalGame.weapon;

import survivalGame.Level;
import survivalGame.VisibleObjectHandler;
import engine.Model;

public class WeaponTestGun extends WeaponRange{

	public WeaponTestGun( Level level) {
		super((Model) VisibleObjectHandler.getVisableObject("Test_Gun_01"), level);
	}

}
