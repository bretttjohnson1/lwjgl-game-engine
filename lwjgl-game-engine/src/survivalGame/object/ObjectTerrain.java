
package survivalGame.object;

import java.io.File;
import java.util.Random;


import survivalGame.Player;
import survivalGame.Biome.BiomeDesert;
import survivalGame.Biome.BiomeHills;
import survivalGame.Biome.BiomeSpawn;

import engine.Biome;
import engine.Point3d;
import engine.TerrainManager;

public class ObjectTerrain extends Object{

	TerrainManager manager;
	Player player;
	boolean inited = false;
	File imageA, imageB;
	int chunkCount = 0;
	Biome biome = new BiomeSpawn();
	Biome[] biomeTypes = new Biome[3];
	int[] chance = new int[biomeTypes.length];
	Biome[] biomeorder = new Biome[biomeTypes.length*20];
	Random random;
	
	public ObjectTerrain(Player player,File imageA,File imageB, Random random) {
		super(new TerrainManager(random));
		manager = (TerrainManager) vObject;
		this.player = player;
		this.imageA = imageA;
		this.imageB = imageB;
		this.random = random;
	}
	Point3d locA;
	public void tick(){
		if(!inited){
			manager.addToPhysWorld();
			manager.Initgen(6, imageA, imageB, .6f,biome);
			locA = player.getLocation();
			inited = true;
		}
		if(chunkCount >= 15) biome = new BiomeDesert();
		Point3d locB = player.getLocation();
		if(Math.sqrt(Math.pow(locA.x-locB.x,2) +  Math.pow(locA.y-locB.y,2) + Math.pow(locA.z-locB.z,2)) > 30);{
			chunkCount += manager.update((float)-locB.x,(float) -locB.z,biome);
			locA = player.getLocation();			
		}		
	}
	void genBiomeOrder(){
		int max = biomeTypes.length;
		for(int i=0;i<max;i++){
			int num = random.nextInt(20);
			for(int i2=0;i2<biomeTypes.length;i2++){
				
			}
		}
	}
	public void fillBiomeTypes(){
		biomeTypes[0] = new BiomeSpawn();
		biomeTypes[1] = new BiomeDesert();
		biomeTypes[2] = new BiomeHills();
		chance[0] = 5;
		chance[1] = 14;
		chance[2] = 1;
	}
	
	@Override
	public void addToPhys(){}
}
