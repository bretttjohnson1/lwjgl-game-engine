package survivalGame;

import java.awt.Canvas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.vecmath.Vector3f;

import org.lwjgl.input.Keyboard;

import survivalGame.entity.EntityItem;
import survivalGame.item.ItemWeapon;
import survivalGame.object.HouseObject;
import survivalGame.object.Object;
import survivalGame.object.ObjectTerrain;
import survivalGame.object.ObjectTest;
import survivalGame.weapon.RangeWeapon;
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
	Level level;	
	RangeWeapon[] weapons = new RangeWeapon[2];
	Object test = null;
	HUD headsUpDisplay = null;
//	EntitySpawnManager esm;
	HUDString fps = new HUDString("FPS: 0", 0, 0);
	HUDString tps = new HUDString("TPS: 0", 300, 0);
	HUDString ptps = new HUDString("PTPS: 0", 600, 0);
	public static void main(String[] args) {
		gm = new GameManager();
		gm.startGame(new SurvivalGame(640, 480, null), 100);
	}
	
	public SurvivalGame(int width, int height, Canvas canvas) {
		super(width, height, canvas);		
	}
	
	
	@Override
	public void preInit() {
		world.setMenu(new MenuLoading(world).menu);		
		level = new Level(world);
		player = new Player(level, speed, runFactor, jumpForce,camera);
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
		ObjectTerrain terrain = new ObjectTerrain(player, new File("res/dirt.bmp"),new File("res/dirt.bmp"));
		level.addObject(terrain);		
		terrain.addToPhys();		
		headsUpDisplay = new HUD((float)Render.width/(float)Render.height);
		headsUpDisplay.addElement(fps);
		headsUpDisplay.addElement(tps);
		headsUpDisplay.addElement(ptps);
		WeaponAK47 ak47 = new WeaponAK47(level);
		WeaponPistol pistol = new WeaponPistol(level);
		ItemWeapon itemAK47 = new ItemWeapon(ak47, 0);
		ItemWeapon itemPistol = new ItemWeapon(pistol, 0);
		
		player.inventory.addItem(new InventoryItem(1, itemAK47));
		player.inventory.addItem(new InventoryItem(1, itemPistol));
		
		player.setLocation(new Point3d(20,100,20));	
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
	//	light.setLocation(new Vector3f((float)-camera.x,(float)camera.y+10,(float)-camera.z));
	//	test.setLocation(new Vector3f((float)-camera.x, (float)(-camera.y) - 2, (float)-camera.z));
	//	if(Keyboard.isKeyDown(Keyboard.KEY_1) && !pressed){
//			player.setWeapon(weapons[0]);
//			pressed = true;
	//	}else if(Keyboard.isKeyDown(Keyboard.KEY_2) && !pressed){
////			player.setWeapon(weapons[1]);
//		}else{
//			pressed = false;
////		}
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
	//	System.out.println("FPS: " + render.fps);
	//	System.out.println("TPS: " + gm.tps);
		
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
					BaseMenu inventoryMenu = new MenuInventory(world, player.inventory, player);
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
