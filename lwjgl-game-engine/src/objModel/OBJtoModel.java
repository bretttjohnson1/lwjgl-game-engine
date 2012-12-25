package objModel;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.vecmath.Vector4f;

import org.lwjgl.input.Keyboard;

import engine.AnimatedModel;
import engine.BasicGame;
import engine.Color3f;
import engine.GameManager;
import engine.Light;
import engine.Model;
import engine.OBJLoader;
import engine.Point3d;

public class OBJtoModel extends BasicGame implements ActionListener{
	
	
	static Canvas c = new Canvas();
	public static void main(String[] args) {
		new OBJtoModel();
	}
	GameManager gm = new GameManager();
	JFrame window = new JFrame("OBJ to Model");
	File image,obj,out;
	JButton openImage,openOBJ,save,convert;
	JFileChooser jfc = new JFileChooser();
	JTextField numFrames = new JTextField();
	JTextField name = new JTextField("Enter Name Here");
	Light light = new Light(0, 10, 0, 1);
	JCheckBox checkBoxAnimatedModel = new JCheckBox("Animated Model");
	
	public OBJtoModel(){
		super(640,480,c);		
		window.setSize((int)(640*1.5f),480);
		window.setLayout(null);
		window.setVisible(true);
	//	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(c);
		c.setVisible(true);
		c.setSize(640, 480);
		gm.startGame(this, 20);
		
		openImage = new JButton("Open Image");
		openImage.setLocation(670, 100);
		openImage.setSize(200, 25);
		openImage.setVisible(true);
		openImage.addActionListener(this);
		window.add(openImage);
		
		openOBJ = new JButton("Open OBJ");
		openOBJ.setLocation(670, 150);
		openOBJ.setSize(200, 25);
		openOBJ.setVisible(true);
		openOBJ.addActionListener(this);
		window.add(openOBJ);
		
		save = new JButton("Save Model");
		save.setLocation(670, 200);
		save.setSize(200, 25);
		save.setVisible(true);
		save.addActionListener(this);
		window.add(save);
		
		convert = new JButton("Convert");
		convert.setLocation(670, 250);
		convert.setSize(200, 25);
		convert.setVisible(true);
		convert.addActionListener(this);
		convert.setEnabled(false);
		window.add(convert);
		
		numFrames.setLocation(850, 20);
		numFrames.setSize(30, 25);
		window.add(numFrames);
		
		name.setLocation(800, 48);
		name.setSize(120, 25);
		window.add(name);
		
		checkBoxAnimatedModel.setLocation(670, 27);
		checkBoxAnimatedModel.setSize(150, 20);
		window.add(checkBoxAnimatedModel);
		
		window.repaint();
	}
	@Override
	public void postInit() {
		render.setClearColor(new Color3f(0,0,1));
		light.setAmbient(new Vector4f(0.3f,0.3f,0.3f,0.01f));
		light.setDiffuse(new Vector4f(0.6f,0.6f,0.6f,1));
		world.addLight(light);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();		
		if(b == openImage){
			jfc.setVisible(true);
			jfc.showOpenDialog(window);
			image = jfc.getSelectedFile();					
		}
		
		if(b == openOBJ){
			jfc.setVisible(true);
			jfc.showOpenDialog(window);
			obj = jfc.getSelectedFile();					
		}
		
		if(b == save){
			jfc.setVisible(true);
			jfc.showSaveDialog(window);
			out = jfc.getSelectedFile();					
		}
		
		if(b == convert){
			OBJLoader ol = new OBJLoader();
			Model l = null;
			AnimatedModel am = null;
			try {			
				
				if(checkBoxAnimatedModel.getAccessibleContext().getAccessibleValue().getCurrentAccessibleValue().intValue() == 1){
					String fileName = obj.getAbsolutePath().substring(0,obj.getAbsolutePath().length()-11);
					am = ol.loadAnimatedModel(fileName, Integer.parseInt(numFrames.getText()));
				}else{
					l =	ol.loadOBJ(obj,ImageIO.read(image));
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
				System.err.println("ERROR: Model could not be loaded!");
				System.err.println("Exiting...");
				gm.cleanupAndEndGame();
			}
			
			world.addObject(l);	
			ObjectOutputStream oos = null;
			while(!l.readyToWrite()){
				System.out.println("Wating...");
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			try {
				oos = new ObjectOutputStream(new FileOutputStream(out));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				if(am != null){
					am.name = name.getText();
					oos.writeObject(am);
				}else{
					l.name = name.getText();
					oos.writeObject(l);
				}				
			} catch (IOException e1) {
				e1.printStackTrace();
				System.err.println("ERROR: Model could not be saved!");
				System.err.println("Exiting...");
			}				
			l.setVisible(true);
			l.move(new Point3d(0,0,-5));
		}
		
		
		if(image != null && obj != null && out != null){
			convert.setEnabled(true);
		}else{
			convert.setEnabled(false);
		}
	}
	@Override
	public void tick() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			camera.z += 0.5;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			camera.z -= 0.5;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			camera.x += 0.5;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			camera.x -= 0.5;
		}
		if(!window.isVisible()) gm.cleanupAndEndGame();
	}
}
