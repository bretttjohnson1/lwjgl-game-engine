package survivalGame;

import java.awt.image.BufferedImage;

import engine.Model;

public class Item {
	
	Item[] items = new Item[255];
	Model model;
	BufferedImage image;
	public String name = "";
	int weight;
	
	public Item(int id,BufferedImage image, Model model,int weight){
		items[id] = this;
		this.model = model;
		this.image= image;
		this.weight = weight;
	}
	
}
