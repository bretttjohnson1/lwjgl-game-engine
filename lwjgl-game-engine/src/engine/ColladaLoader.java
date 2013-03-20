package engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.smartcardio.ATR;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.oracle.nio.BufferSecrets;

public class ColladaLoader {

	
	static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	public static Model loadModel(File model,File texture) throws ParserConfigurationException, SAXException, IOException {
		
	
		
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document domTree = db.parse(model);
		Node rootNode  = domTree.getDocumentElement();
		
		 
        NodeList childNodes = rootNode.getChildNodes();
 
        
        printNodes(childNodes);
 
		//TODO: this will convert the node data to a model
		return null;
	}
	
	public static void printNodes(NodeList list){
		for(int i=0;i<list.getLength();i++){
			System.out.println("Node Name: " + list.item(i).getNodeName());
			System.out.println("\tValue: " + list.item(i).getNodeValue());
			printNodes(list.item(i).getChildNodes());
		}
	}
}	
	
	/*
	private static class Node{		
		
		public Attribute[] atts;
		public String name;
		public String val;
		
		public Node(Attribute[] atts, String name, String val){
			this.name = name;
			this.val = val;
			this.atts = atts;
		}
		
		@Override
		public String toString() {
			String out = "";
			out += name;
			Character c = new Character((char) 10);
			out += c;
			out += "Attributes: ";
			out += c;
			for(int i=0;i<atts.length;i++){
				out += atts[i].name;
				out += ": ";
				out += atts[i].val + ", ";
			}
			out += c;
			out += "Chars: " + val;
			out += c;
			return out;
		}
	}
	
	private static class Attribute{
		public String name;
		public String val;
		public Attribute(String name, String val){
			this.name = name;
			this.val = val;
		}
	}
	
	private static class ParserCallBack extends DefaultHandler {
		
		
		String name;
		Attribute[] atts;
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			name = qName;
			atts = new Attribute[attributes.getLength()];
			for(int i=0;i<attributes.getLength();i++){
				atts[i] = new Attribute(attributes.getQName(i), attributes.getValue(attributes.getQName(i)));
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			String val = new String(ch,start,length);
			Node n = new Node(atts, name, val);
			nodes.add(n);
		}
	}
	
}
	*/
