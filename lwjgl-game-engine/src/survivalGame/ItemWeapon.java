package survivalGame;

import java.awt.image.BufferedImage;

import survivalGame.weapon.RangeWeapon;

import engine.Model;

public class ItemWeapon extends Item{

	public ItemWeapon(RangeWeapon w,int weight) {
		super(w.WeaponItemID,w.weaponModel.image,w.weaponModel, weight);
		weapon = w;
	}
	RangeWeapon weapon;
	
}
