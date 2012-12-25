package survivalGame;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import engine.AnimatedModel;
import engine.Model;
import engine.OBJLoader;
import engine.VisibleObject;
import engine.World;

public class VisibleObjectHandler {
	
	private static ArrayList<VisibleObject> vObjs = new ArrayList<VisibleObject>();
	
	public static Model getModel(int id){
		Model m = (Model) vObjs.get(id);
		return m.duplicate();
	}
	
	public static AnimatedModel getAnimatedModel(int id){
		AnimatedModel m = (AnimatedModel) vObjs.get(id);
		return m.duplicate();
	}
	
	public static VisibleObject getModel(String name){
		for(VisibleObject vo : vObjs){
			if( vo.getName().equals(name)){
				return vo;
			}
		}
		return null;
	}
	public static void load(String path, World w, boolean animatedModels) throws FileNotFoundException, IOException, ClassNotFoundException{
		File f = new File(path);
		File[] files = f.listFiles();
		if(animatedModels){
			for(int i=0;i<files.length;i++){				
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(files[i]));
				AnimatedModel am = (AnimatedModel)ois.readObject();
				am.name = files[i].getName();
				vObjs.add(am);	
				am.lodedFromFile();
				am.setVisible(false);
				ois.close();
			}
		}else{
			for(int i=0;i<files.length;i++){
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(files[i]));
				Model m = (Model)ois.readObject();
				m.name = files[i].getName();
				vObjs.add(m);	
				System.out.println(m.name);
				m.loadedFromFile();
				m.setVisable(false);
				ois.close();
			}
		}
	}
	public static void load(File dataSheet,World w) throws FileNotFoundException{
		//6
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dataSheet));
		Scanner in = new Scanner(bis);
		String line;
		int lineN = 0;
		OBJLoader l = new OBJLoader();
		while(in.hasNext()){		
			line = in.nextLine();
			lineN++;
			if(line == null) continue;
			if(!line.startsWith("--")) continue;
			String path;
			boolean anModel = false;
			int frames = 0;
			if(line.charAt(2) == 'A'){ 
				anModel = true;
				frames = Integer.valueOf(line.substring(4,6));
			}
			int index = line.indexOf("File: ");
			path = line.substring(index + 6);
			try{				
				File model = new File(path + ".obj");
				File image = new File(path + ".bmp");
				VisibleObject v = null;
				if(anModel){
				
					v = l.loadAnimatedModel(path,frames,ImageIO.read(image));
				}else{
				
					v =l.loadOBJ(model, ImageIO.read(image));					
				}
				w.addObject(v);
				vObjs.add(v);
				v.setVisible(false);
				
			}catch(Exception e){
				System.err.println("WARNING: line: " + lineN + " not read properly");
				e.printStackTrace();
			} 
		}
		in.close();
		try {
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
//----------Finals for the different VisibleObjects NOT USED ANYMORE!!!------------------
	
	//Entities
	public static final int Zombie = 0;
	public static final int RareZombie = 1;
	
	//Projectile
	public static final int Bullet = 5;
	
	//Weapons
	public static final int NambuPistol = 1;
	public static final int AK_47 = 2;
	public static final int Mannlicher = 7;
	
	//Objects
	public static final int Terrain = 0;
	public static final int Test = 4;
	public static final int House = 6;
	
}
