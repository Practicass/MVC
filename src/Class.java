import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
//otros import




public class Class {
	
	//Atributos
	private String name; //Nombre de la clase	
	private Rectangle rectangle; //Rectangulo que representa la clase

	
	public Class(String name, int x, int y, int width, int height) {
		this.name = name;
		this.rectangle = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics g){
		//Dibuja la clase
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.draw(rectangle);
		g2.drawString(name, rectangle.x+15, rectangle.y+15);	
	}
	
	//Otros metodos	


	public void setName(String name){
		this.name = name;
	}

	public void setRectangle(Rectangle rectangle){
		this.rectangle = rectangle;
	}


}
