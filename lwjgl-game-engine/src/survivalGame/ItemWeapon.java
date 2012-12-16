package survivalGame;

import java.awt.image.BufferedImage;

import engine.Model;

public class ItemWeapon extends Item{

	public ItemWeapon(Weapon w,int weight) {
		super(w.WeaponItemID,w.weaponModel.image,w.weaponModel, weight);
		weapon = w;
	}
	Weapon weapon;
	
}
