package survivalGame;

import java.awt.Canvas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import javax.vecmath.Vector3f;

import org.lwjgl.input.Keyboard;

import survivalGame.entity.EntityItem;
import survivalGame.entity.EntityPlayer;
import survivalGame.item.ItemWeapon;
import survivalGame.object.HouseObject;
import survivalGame.object.Object;
import survivalGame.object.ObjectTerrain;
import survivalGame.object.ObjectTest;
import survivalGame.weapon.Weapon;
import survivalGame.weapon.WeaponBattleAxeGun;
import survivalGame.weapon.WeaponRange;
import survivalGame.weapon.WeaponAK47;
import survivalGame.weapon.WeaponPistol;
import com.bulletphysics.collision.dispatch.CollisionWorld;
import com.bulletphysics.collision.dispatch.CollisionWorld.RayResultCallback;

import engine.BasicGame;
import engine.Color3f;
import engine.GameManager;
import engine.HUD;
import engine.HUDString;
import engine.MobControler;
import engine.Point3d;
import engine.Render;
import engine.World;

public class SurvivalGame extends BasicGame {

	double speed = 3;
	double runFactor = 2;
	float jumpForce = 300;
	static GameManager gm;
	Player player;
	EntityPlayer entityPlayer;
	Level level;	
	WeaponRange[] weapons = new WeaponRange[2];
	Object test = null;
	HUD headsUpDisplay = null;
	HUDString fps = new HUDString("FPS: 0", 0, 0);
	HUDString tps = new HUDString("TPS: 0", 300, 0);
	HUDString ptps = new HUDString("PTPS: 0", 600, 0);
	Random random;
	public static void main(String[] args) {
		gm = new GameManager();
		gm.startGame(new SurvivalGame(640, 480, null), 100);
	}
	
	public SurvivalGame(int width, int height, Canvas canvas) {
		super(width, height, canvas);
		random = new Random();
	}
	
	@Override
	public void preInit() {
		world.setMenu(new MenuLoading(world).menu);		
		level = new Level(world);
		player = new Player(level, speed, runFactor, jumpForce,camera);
		entityPlayer = new EntityPlayer(null, level, player);
		setGravity(new Vector3f(0,-9.8f,0));
		gm.grabMouse = true;
		try {
			VisibleObjectHandler.load("SurvivalGame/Models",world,false);
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("There was an error reading the Model Files.");
			e.printStackTrace();
		}
		try {
			VisibleObjectHandler.load("SurvivalGame/AnimatedModels",world,true);
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("There was an error reading the Animated Model Files.");
			e.printStackTrace();
		}		
	}
	
	@Override
	public void midInit() {}
	
	
	@Override
	public void postInit() {
		ObjectTerrain terrain = new ObjectTerrain(player, new File("res/dirt.bmp"),new File("res/dirt.bmp"),random);
		level.addObject(terrain);		
		terrain.addToPhys();		
		headsUpDisplay = new HUD((float)Render.width/(float)Render.height);
		headsUpDisplay.addElement(fps);
		headsUpDisplay.addElement(tps);
		headsUpDisplay.addElement(ptps);
		world.setHUD(headsUpDisplay);
		WeaponAK47 ak47 = new WeaponAK47(level);
		WeaponPistol pistol = new WeaponPistol(level);
		Weapon gun = new WeaponBattleAxeGun(level);
		ItemWeapon itemAK47 = new ItemWeapon(ak47, 0);
		ItemWeapon itemPistol = new ItemWeapon(pistol, 0);
		ItemWeapon itemGun = new ItemWeapon(gun, 0);
		player.inventory.addItem(new InventoryItem(1, itemAK47));
		player.inventory.addItem(new InventoryItem(1, itemPistol));
		player.inventory.addItem(new InventoryItem(1, itemGun));
		
		player.setLocation(new Point3d(20,70,20));	
		world.setMenu(null);		
		
	}
	boolean pressed = false;
	boolean tPressed = false;
	boolean ePressed = false;
	@Override
	public void tick() {
		if(!Keyboard.isCreated()) return;
		fps.changeString("FPS: " + render.fps);
		tps.changeString("TPS: " + gm.tps);
		ptps.changeString("PTPS: " + GameManager.ptps);
		player.tick();
	    level.tick();	
		if(Keyboard.isKeyDown(Keyboard.KEY_F) && !tPressed){
			tPressed = true;
			render.wireFrame = !render.wireFrame;
		}else if(!Keyboard.isKeyDown(Keyboard.KEY_F)){
			tPressed = false;
		}		
	}
	boolean tabPressed = false;
	@Override
	public void nonPauseTick() {
		if(!Keyboard.isCreated()) return;
		level.menuTick();
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && level.menu != null && !ePressed){
			level.setMenu(null);
			ePressed = true;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE )&& !ePressed ){
			gm.cleanupAndEndGame();
			return;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_TAB)){			
			if(!tabPressed){
				tabPressed = true;
				if(level.menu == null){ 
					BaseMenu inventoryMenu = new MenuPlayerInventory(world, entityPlayer);
					level.setMenu(inventoryMenu);
				}else{
					level.setMenu(null);
				}
			}
		}else{
			tabPressed = false;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			ePressed = false;
		}
	}
	@Override
	public void physTick() {
		player.physTick();
	}
}
