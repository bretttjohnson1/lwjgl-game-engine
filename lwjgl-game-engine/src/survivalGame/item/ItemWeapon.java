package survivalGame.item;

import survivalGame.weapon.Weapon;


public class ItemWeapon extends Item{

	public ItemWeapon(Weapon w,int weight) {
		super(w.image,w.weaponModel, weight);
		weapon = w;
	}
	public Weapon weapon;
	
}
