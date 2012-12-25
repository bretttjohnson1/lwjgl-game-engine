package mapEditor;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.shapes.CollisionShape;

import static org.lwjgl.opengl.GL11.*;

import engine.Color3f;
import engine.Point3d;
import engine.Shape;
import engine.VisibleObject;
import engine.World;

public class LineShape implements VisibleObject{
	public boolean visable = true;
//	public Color3f color;
	Shape shape;
	
	public LineShape(Shape s){
		shape = s;
	}
	
	@Override
	public void render() {
		glDisable(GL_TEXTURE_2D);
		glLineWidth(1);			
		glColor3f(1/shape.color.r, 1/shape.color.g, 1/shape.color.b);
		for(int i = 0;i<shape.planes.size();i++){					
			glTranslated(shape.location.x, shape.location.y, shape.location.z);	
			glBegin(GL_LINES);	
			for(int j=0;j<shape.planes.get(i).verts.length;j++){						
				Point3d vert = shape.planes.get(i).verts[j];
				glVertex3d(vert.x,vert.y,vert.z);						
			}
		}
		glEnd();		
	}
	@Override
	public void setVisible(boolean visible) {
		this.visable = visible;
	}

	@Override
	public boolean getVisible() {
		return visable;
	}

	@Override
	public void addedToWorld(World w) {
	}

	@Override
	public void tick() {
	}

	@Override
	public void move(Point3d newLocation) {
	}

	@Override
	public void cleanUp() {
	}

	@Override
	public void removedFromWorld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToPhysWorld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToPhysWorld(float mass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToPhysWorld(CollisionShape cs, float mass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFromPhysWorld() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
