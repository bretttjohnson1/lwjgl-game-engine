package engine;


import java.awt.Canvas;

import javax.vecmath.Vector3f;

import org.lwjgl.opengl.GL11;

public class BasicGame {

	public static boolean isRunning = false;
	
	public Render render;
	
	public Camera camera;
	public World world;
	public static BasicGame game;
	public Thread rendert;
	int w;
	int h;
	Vector3f worldMax, worldMin;
	Canvas canvas;
	
	public BasicGame(int width, int height,Canvas canvas){
		game = this;
		w = width;
		h = height;
		this.canvas = canvas;
	}
	
	public void initGameObjects(Phys phys){
		camera = new Camera();
		world = new World(camera,phys);
		world.setupPhysics();
		render = new Render(world,w,h,canvas);
		rendert = new Thread(render,"Render");
		isRunning = true;			
	}
	
	
//	public static int genDispList(int range){
//		return GL11.glGenLists(range);
//	}
	
	public void preInit(){}

	public void midInit(){}
	
	public void postInit(){}

	public void tick(){}
	
	public void nonPauseTick(){}
	
	public void physTick(){};
	
	public void setGravity(Vector3f gravity){
		world.setGravity(gravity);
	}
}
