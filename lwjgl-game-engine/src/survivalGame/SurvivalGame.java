package survivalGame;

import java.awt.Canvas;
import java.io.File;
import java.io.FileNotFoundException;

import javax.vecmath.Vector3f;

import org.lwjgl.input.Keyboard;

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
	Weapon[] weapons = new Weapon[2];
	Object test = null;
	HUD headsUpDisplay = null;
//	EntitySpawnManager esm;
	HUDString fps = new HUDString("FPS: What you paid for", 0, 0);
	HUDString tps = new HUDString("spdjasdjsa;dja;djas;ldkas;ldksa;ldkas", 300, 0);
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
		player = new Player(level, speed, runFactor, jumpForce);
		setGravity(new Vector3f(0,-9.8f,0));
		gm.grabMouse = true;
		try {
			VisibleObjectHandler.load(new File("SurvivalGame/Game_Resorces_Link.dat"),world);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}		
	}
	
	@Override
	public void midInit() {
		
		
	}
	
	
	@Override
	public void postInit() {
		ObjectTerrain terrain = new ObjectTerrain(player, new File("res/dirt.bmp"),new File("res/dirt.bmp"));
		level.addObject(terrain);		
		terrain.addToPhys();
		HouseObject house = new HouseObject();
		house.setLocation(new Vector3f(0,-10,0));
		level.addObject(house);
	//	house.addToPhys(0)
		house.addToPhys();
		test = new ObjectTest();
		test.setLocation(new Vector3f(0,0,0));
		weapons[0] = new WeaponAK47(1,level);
		ItemWeapon itemAK47 = new ItemWeapon(weapons[0],0);
		itemAK47.name = "AK47";
		weapons[1] = new WeaponPistol(0,level);
		ItemWeapon itemPistol = new ItemWeapon(weapons[1],0);
		itemPistol.name = "Pistol";
		EntityItem enityItemAK47 = new EntityItem(level, itemAK47);
		headsUpDisplay = new HUD((float)Render.width/(float)Render.height);
		headsUpDisplay.addElement(fps);
		headsUpDisplay.addElement(tps);
	//	world.setHUD(headsUpDisplay);
//		level.addEntity(enityItemAK47);	
	//	enityItemAK47.addedToLevel();
	//	EntityItem enityItemPistol = new EntityItem(level, itemPistol);
	//	level.addEntity(enityItemPistol);
	//	enityItemPistol.rendarbleObject.move(new Point3d(10,0,10));
	//	enityItemPistol.addedToLevel();
		player.inventory.addItem(new InventoryItem(1, itemAK47));
		player.inventory.addItem(new InventoryItem(1, itemPistol));
		player.setLocation(new Point3d(20,100,20));
	//	player.setWeapon(new WeaponMannlicher(0, level));
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
		player.tick(camera);
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
}
