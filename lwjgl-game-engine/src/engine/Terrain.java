package engine;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDeleteLists;
import static org.lwjgl.opengl.GL11.GL_DECAL;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexEnvf;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.vecmath.Vector3f;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import com.bulletphysics.collision.broadphase.BroadphaseNativeType;
import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.collision.shapes.StaticPlaneShape;
import com.bulletphysics.collision.shapes.StridingMeshInterface;
import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;
import com.bulletphysics.collision.shapes.TriangleMeshShape;
import com.bulletphysics.collision.shapes.TriangleShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.bulletphysics.util.ObjectArrayList;





public class Terrain{

	//Mag is the size of the terrain, actuality it is a multiplier that determines the size
	//when cords would normaly be x,y,z now are multiplied (ex: 1,2,2,--> mag = 10 --> 10,20,20
	int mag = 5;
	float noise= 1f;
	float smoothingfactor= 1f;
	float Chunk [][];
	int defp;
	boolean multiplechunks=false;
	int startx;
	int starty;
	int[] seed = new int[4];
	int displayListId;
	public ArrayList<Triangle> triangles = new ArrayList<Triangle>();	
	Render render;
	IntBuffer imageBuffer;
	int textid = 0;
	BufferedImage image;
	boolean texutreLoaded = false;
	boolean visable = true;
	public float[] points;
	public int[] tris;	
	float[]top;
	float[]left;
	float[]right;
	float[]bottom;
	float[]ntop;
	float[]nleft;
	float[]nright;
	float[]nbottom;
	CollisionShape collisionShape;
	public FloatBuffer vertPart;
	public FloatBuffer texPart;
	public FloatBuffer	normalPart;
	public String id = "null";


	public boolean getVisable(){
		return visable;
	}
	//public Terrain(Render render){
	//	this.render = render;	
	//	render.terrainNEEDbuild.add(this);
	//}

	public void cleanUp(){
		glDeleteLists(displayListId, 1);
	}
	//if the array of prev cordinates is all zer0, it ignores it 



	public Terrain(int x,int y,float noise, int[] seed){
		for(int a =0;a<seed.length;a++)
			this.seed[a]=seed[a];
		this.noise=noise;
		startx=x;
		starty=y;

	}
	public Terrain(int x,int y,float noise){
		for(int a =0;a<seed.length;a++)
			seed[a]=10;
		this.noise=noise;
		startx=x;
		starty=y;
	}
	public Terrain(int x,int y){
		for(int a =0;a<seed.length;a++)
			seed[a]=10;
		startx=x;
		starty=y;
	}
	public void setSeed(float[]top,float[]left,float[]right,float[]bottom,boolean mult){	
		this.top=top;
		this.left=left;
		this.right=right;
		this.bottom=bottom;

	//	for(int a=0;a<defp;a++){
	//		this.top[a]=top[a];
	//		this.left[a]=left[a];
	//		this.right[a]=right[a];
	//		this.bottom[a]=bottom[a];
	//	}
		multiplechunks=mult;
	}
	
	public float[][] genTerrain(int def,BufferedImage image,float mag){
		this.image = image;
		Chunk = new float[def][def];
		defp= def;

		int histp = 0;
		//int rem = a;
		double height = 2;
		//determines flatness of terrain
		double spacing =0;
		for(int a=0;a<Chunk.length;a++){
			for (int b=0;b<Chunk.length;b++){
				Chunk[a][b]=0f;	
			}
		}

		Chunk[0][(Chunk.length-1)]= (float) (Math.random()*seed[1]);
		Chunk[0][0]= (float) (Math.random()*seed[0]);
		Chunk[(Chunk.length-1)][0]= (float) (Math.random()*seed[2]);
		Chunk[Chunk.length-1][(Chunk.length-1)]= (float) (Math.random()*seed[3]);
		if (multiplechunks){
			for(int a=0;a<Chunk.length;a++){
				if(top[a]!=0)Chunk[0][a]=top[a];
			//	System.out.println("Top: " + top[a]);
			
				if(bottom[a]!=0)Chunk[defp-1][a]=bottom[a];
			//	System.out.println("Bot: " + bottom[a]);

				if(left[a]!=0)Chunk[a][0]=left[a];
			//	System.out.println("Left: " + left[a]);

				if(right[a]!=0)Chunk[a][defp-1]=right[a];
				//System.out.println("Right: " + right[a]);
			}
		}

		for(int a=(def-1);a>=2;a=a/2){
			//	System.out.println(a);
			int size=Chunk.length-1;
			int half=a/2;
			int div = (int) Math.pow(2, a);
			//int div2 = (int) Math.pow(2, b);
			for(int x=0;x<Chunk.length-a;x+=a){
				for(int y=0;y<Chunk.length-a;y+=a){
					//if (x==0) avg=Chunk[x][y+half]+Chunk[x+half][y+half]+Chunk[x+half][y-half]+Chunk[x][y-half];
					//else if (y==0) avg=Chunk[x-half][y+half]+Chunk[x+half][y+half]+Chunk[x+half][y]+Chunk[x-half][y];
					//else if (y==256)avg=Chunk[x-half][y]+Chunk[x+half][y]+Chunk[x+half][y-half]+Chunk[x-half][y-half];
					//else if (x==256)avg=Chunk[x-half][y+half]+Chunk[x+half][y+half]+Chunk[x+half][y-half]+Chunk[x-half][y-half];
					//if (Chunk[x][y]==0){
					float avg=0;
					//avg=Chunk[(x+half)][y]+Chunk[(x-half)][y]+Chunk[x][(y-half)]+Chunk[x][(y+half)];
					//if ((x-half)>=0) avg+= Chunk[(x-half)][y];
					//if ((x+half)<def)avg+=Chunk[(x+half)][y];
					//if ((y-half)>=0) avg+=Chunk[x][(y-half)];
					//if ((y+half)<def)avg+=Chunk[x][(y+half)];
					avg=(Chunk[x][y]+Chunk[x+a][y]+Chunk[x+a][y+a]+Chunk[x][y+a])/4;
					float pd=(noise/2);
					float ab = (float) (Math.sqrt(a/2));
					float av2=(float)(avg+(((Math.random()*noise)-pd)*(half/2)));
					if(Chunk[x+half][y+half]==0)Chunk[x+half][y+half]=av2;
					avg=(Chunk[x][y]+Chunk[x+a][y]+Chunk[x+a][y+a]+Chunk[x][y+a]+Chunk[x+half][y+half])/5;

					avg=(Chunk[x+a][y]+Chunk[x][y]+Chunk[x+half][y+half])/3;
					av2=(float)(avg+(((Math.random()*noise)-pd)*(half/2)));
					if(Chunk[x+half][y]==0)Chunk[x+half][y]=av2;//(float) ( (((Math.random()*(avg*ab)/noise)-pd))/smoothingfactor);
					avg=(Chunk[x][y+a]+Chunk[x][y]+Chunk[x+half][y+half])/3;
					av2=(float)(avg+(((Math.random()*noise)-pd)*(half/2)));					
					if(Chunk[x][y+half]==0)Chunk[x][y+half]=av2;//(float) ( (((Math.random()*(avg*ab)/noise)-pd))/smoothingfactor);
					avg=(Chunk[x][y+a]+Chunk[x+a][y+a]+Chunk[x+half][y+half])/3;
					av2=(float)(avg+(((Math.random()*noise)-pd)*(half/2)));
					if(Chunk[x+half][y+a]==0)Chunk[x+half][y+a]=av2;//(float) ((((Math.random()*(avg*ab)/noise)-pd))/smoothingfactor);
					avg=(Chunk[x+a][y]+Chunk[x+a][y+a]+Chunk[x+half][y+half])/3;
					av2=(float)(avg+(((Math.random()*noise)-pd)*(half/2)));
					if(Chunk[x+a][y+half]==0)Chunk[x+a][y+half]=av2;///avg(float) ((((Math.random()*(avg*ab)/noise)-pd))/smoothingfactor);
				}
			}
		}
		ntop=new float[defp];
		nbottom=new float[defp];
		nleft=new float[defp];
		nright=new float[defp];
		for(int a=defp-1;a>=0;a--){

			ntop[a]=Chunk[0][a];

			nbottom[a]=Chunk[def-1][a];

			nleft[a]=Chunk[a][0];

			nright[a]=Chunk[a][def-1];
			
		//	ntop[a]=Chunk[0][(def-1) - a];

//			nbottom[a]=Chunk[def-1][(def-1) - a];

	//		nleft[a]=Chunk[(def-1) - a][0];

		//	nright[a]=Chunk[(def-1) - a][def-1];
		}

		points=new float[def*def*2*3*3];                          
		ByteBuffer verts = ByteBuffer.allocate((def-1)*(def-1)*8*4);
		ByteBuffer indes = ByteBuffer.allocate((def-1)*(def-1)*18*4);
		int i2 = 0;
		int i3 = 0;
		for(int a=0;a<Chunk.length-1;a++){
			for (int b=0;b<Chunk.length-1;b++){
				
				//int i2 = a*b;	
				verts.putFloat((a + startx));     //0
				verts.putFloat(Chunk[a][b]);    //1
				verts.putFloat((b + starty));     //2 
				verts.putFloat((a + 1 + startx)); //3
				verts.putFloat(Chunk[a+1][b]);  //4
				verts.putFloat((b + 1 + starty)); //5
				verts.putFloat(Chunk[a][b+1]);  //6
				verts.putFloat(Chunk[a+1][b+1]);//7
				
				indes.putInt(i3+0);
				indes.putInt(i3+1);
				indes.putInt(i3+2);
				
				indes.putInt(i3+3);
				indes.putInt(i3+4);
				indes.putInt(i3+2);
				
				indes.putInt(i3+0);
				indes.putInt(i3+6);
				indes.putInt(i3+5);
				//---------------------------------					
				indes.putInt(i3+3);
				indes.putInt(i3+7);
				indes.putInt(i3+2);
				
				indes.putInt(i3+3);
				indes.putInt(i3+4);
				indes.putInt(i3+2);
				
				indes.putInt(i3+0);
				indes.putInt(i3+6);
				indes.putInt(i3+5);
				
				points[i2] = a + startx;   
				points[i2+1]=Chunk[a][b];				
				points[i2+2] = b + starty;				

				points[i2+3] = a + 1 + startx;
				points[i2+4]=Chunk[a+1][b];				
				points[i2+5] = b + starty;

				points[i2+6] = a + startx;
				points[i2+7]=Chunk[a][b+1];				
				points[i2+8] = b + 1 + starty;
				//----------------------------------	
				points[i2+9] = a + 1 + startx;
				points[i2+10]=Chunk[a + 1][b + 1];				
				points[i2+11] = b + starty + 1;

				points[i2+12] = a + 1 + startx;
				points[i2+13]=Chunk[a + 1][b];				
				points[i2+14] = b + starty;

				points[i2+15] = a + startx;
				points[i2+16]=Chunk[a][b + 1];				
				points[i2+17] = b +  1 + starty;
				
				i2+=18;
				i3+=8;
			}
		}
	//System.out.println(i3);
	//	ObjectArrayList<Vector3f> tris = new ObjectArrayList<Vector3f>(points.length/3);
		
		for(int a=0;a<points.length;a+=1){
	//		System.out.println(points[a]);	
			points[a] *= mag;	
			
		} 
		
	///	for(int i=0;i<verts.capacity();i+=4){
	///	//	System.out.println(verts.getFloat(i));
		//	verts.putFloat(i, verts.getFloat(i)*mag);
		//	
	//	}
		
		int startz = starty;
		vertPart = BufferUtils.createFloatBuffer(points.length);
		vertPart.put(points);
		vertPart.rewind();

		float defp2 =Chunk.length/4;//size of the terrain
		texPart = BufferUtils.createFloatBuffer(Chunk.length*Chunk.length*12);
		float test = (float) (defp2*2/defp2);
		for (int f=0;f<Chunk.length-1;f++){
			for (int g=0;g<Chunk.length-1;g++){
				texPart.put((startx*mag+(f+1)*mag/defp2)*test);
				texPart.put((startz*mag+g*mag/defp2)*test);
				
				texPart.put((startx*mag+(f*mag)/defp2)*test);
				texPart.put((startz*mag+(g*mag)/defp2)*test);	

				texPart.put((startx*mag+f*mag/defp2)*test);
				texPart.put((startz*mag+(g+1)*mag/defp2)*test);

				
				texPart.put((startx*mag+(f+1)*mag/defp2)*test);
				texPart.put((startz*mag+g*mag/defp2)*test);
				
				texPart.put((startx*mag+(f+1)*mag/defp2)*test);
				texPart.put((startz*mag+((g+1)*mag)/defp2)*test);

				texPart.put((startx*mag+f*mag/defp2)*test);
				texPart.put((startz*mag+(g+1)*mag/defp2)*test);

			}
		}
		texPart.rewind();
		normalPart = BufferUtils.createFloatBuffer(Chunk.length*Chunk.length*18);
		for (int f=0;f<Chunk.length-1;f++){
			for (int g=0;g<Chunk.length-1;g++){
				//Possible bug with crossing vectors
				float temp=Chunk[f][g]-Chunk[f+1][g];
				Vector3f v1 = new Vector3f(1,temp,0);
				temp=Chunk[f][g]-Chunk[f][g+1];
				Vector3f v2 = new Vector3f(0,temp,1);
				Vector3f v3 = new Vector3f();
				v3.cross(v1,v2);
				normalPart.put(Utils.asFloatBuffer(v3));

				temp=Chunk[f+1][g]-Chunk[f][g];
				v1 = new Vector3f(1,temp,0);
				temp=Chunk[f+1][g]-Chunk[f][g+1];
				v2 = new Vector3f(1,temp,1);
				v3 = new Vector3f();
				v3.cross(v1,v2);
				normalPart.put(Utils.asFloatBuffer(v3));

				temp=Chunk[f][g+1]-Chunk[f+1][g];
				v1 = new Vector3f(1,temp,1);
				temp=Chunk[f][g+1]-Chunk[f][g];
				v2 = new Vector3f(0,temp,1);
				v3 = new Vector3f();
				v3.cross(v1,v2);
				normalPart.put(Utils.asFloatBuffer(v3));

				//----------------------------------------------

				temp=Chunk[f+1][g+1]-Chunk[f+1][g];
				Vector3f v12 = new Vector3f(0,temp,1);
				temp=Chunk[f+1][g+1]-Chunk[f][g+1];
				Vector3f v22 = new Vector3f(1,temp,0);
				v3 = new Vector3f();
				v3.cross(v12,v22);
				normalPart.put(Utils.asFloatBuffer(v3));

				temp=Chunk[f][g+1]-Chunk[f+1][g];
				v12 = new Vector3f(1,temp,1);
				temp=Chunk[f][g+1]-Chunk[f+1][g+1];
				v22 = new Vector3f(1,temp,0);
				v3 = new Vector3f();
				v3.cross(v12,v22);
				normalPart.put(Utils.asFloatBuffer(v3));

				temp=Chunk[f][g+1]-Chunk[f+1][g+1];
				v12 = new Vector3f(1,temp,0);
				temp=Chunk[f][g+1]-Chunk[f+1][g];
				v22 = new Vector3f(1,temp,1);
				v3 = new Vector3f();
				v3.cross(v12,v22);
				normalPart.put(Utils.asFloatBuffer(v3));
			}
		}
		normalPart.rewind();
	
		TriangleIndexVertexArray tiva = new TriangleIndexVertexArray((def-1)*(def-1)*2, indes, 3*3*4, (def-1)*(def-1)*8, verts, 4);
		tiva.setScaling(new Vector3f(mag,mag,mag));
		TriangleMeshShape tms = new BvhTriangleMeshShape(tiva, true);
		
		
		
		collisionShape = tms;
		return Chunk;
	}

	public CollisionShape getCollisionShape(){
		return collisionShape;
	}

}