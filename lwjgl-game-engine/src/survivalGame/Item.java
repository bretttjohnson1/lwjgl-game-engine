package survivalGame;

import java.awt.image.BufferedImage;

import engine.Model;

public class Item {
	

	public Model model;
	BufferedImage image;
	public String name = "";
	int weight;
	
	public Item(BufferedImage image, Model model,int weight){
		
		this.model = model;
		this.image= image;
		this.weight = weight;
	}
	
}
