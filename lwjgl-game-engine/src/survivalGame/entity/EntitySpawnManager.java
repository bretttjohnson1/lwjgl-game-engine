package survivalGame.entity;

import java.util.ArrayList;

import survivalGame.Level;

public class EntitySpawnManager {
	ArrayList<SpawnedEntity>  spawnedEntitys= new ArrayList<SpawnedEntity>();
	Level l;
	
	public EntitySpawnManager(Level l){
		this.l = l;
		l.esm = this;
	}
	
	public void tick(){
		for(int i=0;i<spawnedEntitys.size();i++){
		SpawnedEntity s = spawnedEntitys.get(i);
			if(s.oneSecondTick()){
				l.removeEntity(s.e);
				spawnedEntitys.remove(s);
			}
		}
	}
	
	public void spawnEntity(Entity e, int life){
		SpawnedEntity se = new SpawnedEntity(e, life);
		spawnedEntitys.add(se);
		l.addEntity(e);
	} 
	
	public void despawnEntity(Entity e){
		for(SpawnedEntity s : spawnedEntitys){
			if(s.e == e){
				l.removeEntity(e);
				spawnedEntitys.remove(s);
			}
		}
	}
}

class SpawnedEntity{
	Entity e;
	int life;
	int eTime = 0;
	
	public SpawnedEntity(Entity e,int life) {
		this.e = e;
		this.life = life;
	}
	
	public boolean oneSecondTick(){
		if(eTime >= life && life != 0){
			return true;
		}
		eTime++;
		
		return false;
	}
}
