import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
//otros imports …



public class Association {

	// Atributos
	// ...
	private Class class1;
	private Class class2;
	private Line2D line;
	
	// Constructores
	// ...
	public Association(Class class1, Class class2) {
		this.class1 = class1;
		this.class2 = class2;
		double x1 = class1.getX() + class1.getWidth() / 2.0;
        double y1 = class1.getY() + class1.getHeight() / 2.0;
        double x2 = class2.getX() + class2.getWidth() / 2.0;
        double y2 = class2.getY() + class2.getHeight() / 2.0;

		this.line = new Line2D.Double(x1, y1, x2, y2);
		
	}
	
	public void draw(Graphics graphics) {

		Graphics2D graphics2d = (Graphics2D)graphics;

		double x1 = class1.getX() + class1.getWidth() / 2.0;
        double y1 = class1.getY() + class1.getHeight() / 2.0;
        double x2 = class2.getX() + class2.getWidth() / 2.0;
        double y2 = class2.getY() + class2.getHeight() / 2.0;
		if(class1 == class2){
			double side = 100; 
        	graphics2d.setColor(Color.black);
        	graphics2d.drawRect((int)x1, (int)y1, (int)side, (int)side);
		}else{
		line.setLine(x1, y1, x2, y2);
		graphics2d.setColor(Color.black);
		graphics2d.draw(line);
		}

	}

	public boolean isClass1(Class clas){
		return (this.class1 == clas );
	}
	public boolean isClass2(Class clas){
		return (this.class2 == clas);
	}
	


}
