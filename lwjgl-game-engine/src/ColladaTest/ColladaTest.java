package ColladaTest;

import java.awt.Canvas;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import engine.BasicGame;
import engine.ColladaLoader;

public class ColladaTest extends BasicGame {

	public ColladaTest(int width, int height) {
		super(width, height, null);
	
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ColladaLoader.loadModel(new File("C:/Users/Carl/Desktop/cube.dae"), null);
	}

}
