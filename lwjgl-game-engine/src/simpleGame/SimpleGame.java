package simpleGame;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.imageio.ImageIO;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.bulletphysics.collision.broadphase.BroadphaseNativeType;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexShape;
import com.bulletphysics.linearmath.Transform;

import engine.BasicGame;
import engine.Color3f;
import engine.FTree;
import engine.GameManager;
import engine.Light;
import engine.MobControler;
import engine.MobControlerOLD;
import engine.Model;
import engine.OBJLoader;
import engine.PhysObj;
import engine.Point3d;
import engine.Shader;
import engine.Shape;
import engine.ShapeGenarator;
import engine.Terrain;
import engine.TerrainManager;
import engine.Vehicle;



public class SimpleGame  extends BasicGame{



	public SimpleGame(int width, int height, Canvas canvas) {
		super(width, height, canvas);
		// TODO Auto-generated constructor stub
	}

	//	Light l = new Light(1, 1, 1, new Color3f(0f,0.3f,0.3f), 0);
	Shader s = null;
	static GameManager g;
	double factor = (Math.PI/180);
	final double dSpeed  = 2;
	double speed = dSpeed;
	public static void main(String[] args) {
		GameManager gm = new GameManager();
		g = gm;
		gm.startGame(new SimpleGame(640,480,null), 60);
			}



	Image[] cubeIs = new Image[6];
	Model terrain = null;
	//	Light light = new Light(0, 0, 0, new Color3f(0,0.1f,0), 0);
	MobControler player;
	TerrainManager t = new TerrainManager();
	@Override
	public void preInit() {
		Vector3f v = new Vector3f(5,8,2); 
		Vector3f v2 = new Vector3f(2,9,3);
		Vector3f v3 = new Vector3f();
		v3.cross(v, v2);
		System.out.println(v3);
		Image image = null;
		//		world.addLight(l);
		OBJLoader l = new OBJLoader();
		Model level = null;
		ConvexShape cap = new CapsuleShape(0.01f,0.1f);
		player = new MobControler(cap,world,0.5f);	
		//		world.addLight(light);
		Model model = null;
		try {		
			terrain = l.loadOBJ(new File("res/Terrain.obj"),ImageIO.read(new File("res/cat.bmp")));
			//	s = new Shader(new File("Shaders/Texture.vert"), new File("Shaders/Texture.frag"));
			s = new Shader(new File("Shaders/TextureLight.vert"), new File("Shaders/TextureLight.frag"));
			//	image = ImageIO.read(new File("res/dirt.bmp"));
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("SurvivalGame/res/House.model")));
			try {
				model = (Model) ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			//	level = l.loadOBJ(new File("res/cube.obj"),ImageIO.read(new File("res/cat.png")));
			for(int i=0;i<cubeIs.length;i++){
				cubeIs[i] = ImageIO.read(new File("res/cat.bmp"));
			}
		} catch (IOException e1) {			
			e1.printStackTrace();
		}

		FTree tree = new FTree(40,3,0,0, 9, 9);
		world.addObject(tree);
		model.loadedFromFile();
		world.addObject(model);
		Light light = new Light(0,100,0,0);
		light.setAmbient(new Vector4f(1,1,1,1));
		light.setDiffuse(new Vector4f(0.1f,0.1f,0.1f,0.2f));
		world.addLight(light);
		//	world.addObject(level);
		//world.addShader(s);
		//	world.addObject(terrain);
		terrain.move(new Point3d(0,-10,0));
		//	terrain.addToPhysWorld(0);
		//terrain.setColor(new Colorq3f(1,0,0));
		//		light.move(new Vector3f(0,10,0));
		//	cube.addToPhysWorld(10f);
		//	level.addToPhysWorld(0);//,new BoxShape(new Vector3f(100,1,100)));//,new BoxShape(new Vector3f(100,1,100)));

		t.Initgen(2,new File("res/dirt.bmp"),new File("res/dirt.bmp"),2);		
		world.addObject(t);
		//	camera.x = 100;
		//	camera.z = 100;

		//	TerrainManager t = new TerrainManager();	

		//	world.addObject(t);
		Mouse.setGrabbed(true);
		player.warp(new Vector3f(0,10,0));
		//player.setGravity(new Vector3f(0,10,0));
		//	Shape p2 = ShapeGenarator.genBoxShape(new Vector3f(10,10,10));
		//	world.addObject(p2);
		//	p2.location.y = 20;
		//	p2.color = new Color3f(1,0,0);
		//	p2.setImage(cubeIs);
	}


	boolean down = false;
	boolean down2 = false;
	boolean pressed = false;
	boolean uPressed = false;

	@Override
	public void tick() {		

		//	light.x = (float) camera.x;
		//	light.y = (float) camera.y;
		//	light.z = (float) camera.z;
		//		light.move(new Vector3f((float)camera.x, (float)camera.y,(float) camera.z));

		camera.rotx -= Mouse.getDY()/8;
		camera.roty += Mouse.getDX()/8;
		/*
		Point3d p;
		p = player.getLocation();
		camera.x = -p.x;
		camera.y = -p.y - 1.3f;
		camera.z = -p.z;
		 */
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			g.cleanupAndEndGame();
			return;
		}


		/*	if(Mouse.isButtonDown(0)){
			if(!down){
				System.out.println("hay");
				Model m = cube.duplicate();
				m.move(new Point3d(-camera.x,-camera.y,-camera.z));
				world.addObject(m);
				down = true;
			}
		}else{
			down = false;
		}
		 */
		if(Keyboard.isKeyDown(Keyboard.KEY_F)){
			if(!down2){
				down2 = true;
				render.wireFrame = !render.wireFrame;
			}
		}else{
			down2 = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE));
		player.jump(2);
		/*
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
		//	player.moveForTime(new Vector3f( (float) (-Math.sin(camera.roty*factor)*speed),(float) (Math.sin(camera.rotx*factor)*speed),(float) (Math.cos(camera.roty*factor)*speed)), 10f);
			player.move(new Vector3f((float)(Math.sin(camera.roty*factor)*speed),0,(float)(-Math.cos(camera.roty*factor)*speed)));// y =(float)(Math.sin(camera.rotx*factor)*speed)
			pressed = true;
		}else if(pressed){
			player.stop();
			pressed = false;
		}
		 */

		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			camera.x -= Math.sin(camera.roty*factor)*speed;
			camera.y += Math.sin(camera.rotx*factor)*speed;
			camera.z += Math.cos(camera.roty*factor)*speed;

		}
		
	//	if(Keyboard.isKeyDown(Keyboard.KEY_U)){
		//	if(!uPressed){
		//		System.out.println(-camera.x + " :X player loc Y: " + -camera.y);
				t.update((float) -camera.x, (float) -camera.z);
			//	uPressed = true;
		//	}
	//	}else{		
	//		uPressed = false;
	//	}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			speed = dSpeed/10;
		}else{
			speed = dSpeed;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			camera.x -= Math.sin(camera.roty*factor)*speed;
			camera.y += Math.sin(camera.rotx*factor)*speed;
			camera.z += Math.cos(camera.roty*factor)*speed;

		}

		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			camera.x += Math.sin(camera.roty*factor)*speed;
			camera.y -= Math.sin(camera.rotx*factor)*speed;
			camera.z -= Math.cos(camera.roty*factor)*speed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			camera.y -= 1;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			camera.y += 1;
		}


		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			camera.x -= Math.sin((camera.roty-90)*factor)*speed;

			camera.z += Math.cos((camera.roty-90)*factor)*speed;
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			camera.x -= Math.sin((camera.roty+90)*factor)*speed;

			camera.z += Math.cos((camera.roty+90)*factor)*speed;
		}




	}

}
