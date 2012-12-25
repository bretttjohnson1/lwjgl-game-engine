
package survivalGame.object;

import java.awt.Point;
import java.io.File;

import org.lwjgl.input.Keyboard;

import survivalGame.Player;

import engine.Point3d;
import engine.TerrainManager;

public class ObjectTerrain extends Object{

	TerrainManager manager;
	Player player;
	boolean inited = false;
	File imageA, imageB;
	
	public ObjectTerrain(Player player,File imageA,File imageB) {
		super(new TerrainManager());
		manager = (TerrainManager) vObject;
		this.player = player;
		this.imageA = imageA;
		this.imageB = imageB;
	}
	Point3d locA;
	public void tick(){
		if(!inited){
			manager.addToPhysWorld();
			manager.Initgen(6, imageA, imageB, .6f);
			locA = player.getLocation();
			inited = true;
		}
		Point3d locB = player.getLocation();
		if(Math.sqrt(Math.pow(locA.x-locB.x,2) +  Math.pow(locA.y-locB.y,2) + Math.pow(locA.z-locB.z,2)) > 30);{
			manager.update((float)-locB.x,(float) -locB.z);
			locA = player.getLocation();			
		}
		
	}
	
	@Override
	public void addToPhys() {}

}
