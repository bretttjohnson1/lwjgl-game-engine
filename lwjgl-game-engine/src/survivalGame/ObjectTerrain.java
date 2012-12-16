package survivalGame;

import java.awt.Point;
import java.io.File;

import org.lwjgl.input.Keyboard;

import engine.Point3d;
import engine.TerrainManager;

public class ObjectTerrain extends Object{

	TerrainManager manager;
	Player player;
	
	public ObjectTerrain(Player player,File imageA,File imageB) {
		super(new TerrainManager());
		manager = (TerrainManager) vObject;
		manager.Initgen(6, imageA, imageB, 1);
		this.player = player;
		locA = player.getLocation();
	}
	Point3d locA;
	public void tick(){
		//if(!Keyboard.isKeyDown(Keyboard.KEY_U)) return;
		Point3d locB = player.getLocation();
		if(Math.sqrt(Math.pow(locA.x-locB.x,2) +  Math.pow(locA.y-locB.y,2) + Math.pow(locA.z-locB.z,2)) > 30);{
			manager.update((float)-locB.x,(float) -locB.z);
			locA = player.getLocation();			
		}
		
	}

}
