package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;

import com.bulletphysics.dynamics.RigidBody;



public class GameManager implements Runnable {
	
	World world;
	public BasicGame game;
	public Timer timer;
	float gameSpeed = 0;
	public int tps = 0;
	Thread logic = new Thread();
	Thread phys = new Thread();
	public boolean grabMouse = false;
	public Phys physClass;
	
	public void startGame(BasicGame game,int gameSpeed){
		
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.err.println("ERROR: Could not creat AL audio device");
			cleanupAndEndGame();			
		}
		System.out.println("test");
		this.game = game;
		physClass = new Phys(game);
		this.game.initGameObjects(physClass);	
		this.game.preInit();		
		world = game.world;
		game.rendert.start();
		this.gameSpeed = (float) gameSpeed;
		game.midInit();
		while(!Keyboard.isCreated() || game.world.renderTickNumber < 3){
			//System.out.println("Loading...");
		};		
		game.postInit();
		logic = new Thread(this,"Logic");
		logic.start();		 
		phys = new Thread(physClass);
		phys.start();
		
	}
	
	
	
	long Stime = 0;
	long Etime = 0;
	boolean first = true;
	
	long Start = 0;
	long Finish = 0;
	
	
	@Override
	public void run() {	
		while(BasicGame.isRunning){
			if(Display.isCloseRequested()) cleanupAndEndGame();
			game.nonPauseTick();
			
			Etime = System.currentTimeMillis();
			int timeE = (int) ((Etime - Stime)/1000f);			
			float dps = 1f/gameSpeed;		
			float diff = dps-timeE;
			//System.out.println(diff*1000);
			if(diff > 0 && !first){
				try{		
					Thread.sleep((long) (diff*1000));
			//		System.out.println("Delay");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				System.out.println("NOTDelay");
			}
			Stime = System.currentTimeMillis();
			first = false;
			
			Finish = System.currentTimeMillis();
			if(Finish - Start == 0){
				tps = -1;
			}else{				
				tps = (int) (1000f/(Finish - Start));
			}
			
			if(game.world.currentMenu != null){
				Mouse.setGrabbed(false);
				continue;
			}else{
				if(grabMouse && !Mouse.isGrabbed()){
					Mouse.setGrabbed(true);
				}
			}	
			
			if(Keyboard.isCreated()){
				game.tick();
			}
			
			if(world != null) world.tick();
			
			
			if(!(phys.isAlive() && logic.isAlive() && game.rendert.isAlive())){
				cleanupAndEndGame();
			}
			
			Start = System.currentTimeMillis();
		}
	}
	
	public void cleanupAndEndGame(){		
		BasicGame.isRunning = false;
		world.cleanUp();	
		AL.destroy();
		System.out.println("Ending game...");
	}
}

class Phys implements Runnable {
	BasicGame game = null;
	long start = 0, finish;
	ArrayList<RigidBody> needToBeAdded = new ArrayList<RigidBody>();
	ArrayList<RigidBody> needToBeRemoved = new ArrayList<RigidBody>();
	public Phys(BasicGame game){
		this.game = game;
	}
	
	public void run(){
		//Timer tick = new Timer(1000/60,this);
		//tick.start();
		int ptps = 0;
		while(BasicGame.isRunning){
			try{
				for(int i=0;i<needToBeAdded.size();i++){
					game.world.physWorld.addRigidBody(needToBeAdded.get(i));
					needToBeAdded.remove(i);				
				}
				for(int i=0;i<needToBeRemoved.size();i++){
					game.world.physWorld.removeRigidBody(needToBeRemoved.get(i));
					needToBeRemoved.remove(i);					
				}
				
				Thread.sleep(10);
				finish = System.currentTimeMillis();			
				game.world.physWorld.stepSimulation((finish - start),10,0.005f);
		//		ptps = (int) ((finish - start)*1000);
				start = System.currentTimeMillis();
			}catch(Exception e){
				e.printStackTrace();
			}			
		}
	}
}

