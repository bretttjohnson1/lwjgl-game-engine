package survivalGame;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.HUD;
import engine.HUDElement;
import engine.HUDImage;
import engine.HUDString;

public class PlayerHUD {

	HUD hud;
	HUDString health = new HUDString("Heath: ", 0, 0);
	
	public PlayerHUD(Player player,float ratio){	
		hud = new HUD(ratio);
		BufferedImage crossHairImage = null;
		try {
			crossHairImage = ImageIO.read(new File("SurvivalGame/res/crosshair.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		HUDElement crossHair = new HUDImage(crossHairImage, hud.getWidth()/2-30, hud.getHeight()/2-30);
		hud.addElement(crossHair);
		health.move(hud.getWidth()-10, 0);
		hud.addElement(health);
		player.level.renderWorld.setHUD(hud);
	}
	
	public void tick(){
		
	}
	
}
