package survivalGame;

import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import engine.Color3f;
import engine.World;
import engine.menu.Menu;

public class Level {

	ArrayList<Entity> entitys = new ArrayList<Entity>();
	ArrayList<Object> objects = new ArrayList<Object>();
	EntityLightSun sun = new EntityLightSun(this);
	World renderWorld;
	EntitySpawnManager esm;
	float time = 1;
	boolean night = false;
	BaseMenu menu = null;
	
	public Level(World world){
		renderWorld = world;
		world.addLight(sun.l);	
		sun.setLocation(new Vector3f(0,1000,0));
		sun.l.setAmbient(new Vector4f(1,1,1,1));
		esm = new EntitySpawnManager(this);
	}
	
	public void addEntity(Entity e){
		entitys.add(e);
		if(e instanceof EntityLight){
			renderWorld.addLight(((EntityLight)e).l);
			return;
		}
		renderWorld.addObject(e.getVisableObject());
		e.getVisableObject().setVisible(true);
	} 
	
	public void removeEntity(Entity e){
		entitys.remove(e);
		if(e instanceof EntityLight){
			renderWorld.removeLight(((EntityLight)e).l);
			return;
		}
		renderWorld.removeObject(e.getVisableObject());
	}
	
	public void addObject(Object o){
		objects.add(o);
		renderWorld.addObject(o.vObject);
		o.vObject.setVisible(true);
	}
	
	public void removeObject(Object o){
		objects.remove(o);
		renderWorld.removeObject(o.vObject);
	}
	public void setMenu(BaseMenu menu){
		this.menu = menu;
		if(menu != null){
			renderWorld.setMenu(menu.menu);
		}else{
			renderWorld.setMenu(null);
		}
		
	}
	
	public void tick(){
		if(esm != null){
			esm.tick();
		}
		
		if(time <= 0.1f && !night){ 
			night = true;
		}else if(night && time >= 1){
			night = false;
		}
		if(night) time += 0.001f;
		else      time -= 0.001f;
		sun.setTime(time);
		renderWorld.render.setClearColor(new Color3f(time/2, time/2, time));
		for(int i=0;i<objects.size();i++){
			objects.get(i).tick();
		}
		
	}
	
	public void menuTick(){
		if(menu != null){
			menu.tick();
		}
	}
	
}
