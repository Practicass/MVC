import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
//otros import




public class Class {
	
	//Atributos
	private String name; //Nombre de la clase	
	private Rectangle rectangle1; //Rectangulo que representa el nombre
	private Rectangle rectangle2; //Rectangulo que representa los atributos
	private Rectangle rectangle3; //Rectangulo que representa los metodos clase
	private Color color; //Color de la clase fondo

	
	public Class(String name, int x, int y, int width, int height, Color colorFill) {
		this.name = name;
		this.rectangle1 = new Rectangle(x, y, width, height/3);
		this.rectangle2 = new Rectangle(x, y+height/3, width, height/3);
		this.rectangle3 = new Rectangle(x, y+2*height/3, width, height/3);
		color = colorFill;
	}
	
	public void draw(Graphics g){
		//Dibuja la clase
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(color);
		g2.fillRect(rectangle1.x, rectangle1.y, getWidth(), getHeight());
		g2.setColor(Color.black);
		g2.draw(rectangle1);
		g2.draw(rectangle2);
		g2.draw(rectangle3);

		g2.drawString(name, rectangle1.x+35, rectangle1.y+15);	
		g2.drawString("atributos", rectangle2.x+27, rectangle2.y+15);
		g2.drawString("metodos", rectangle3.x+27, rectangle3.y+15);
	}
	
	//Otros metodos	
	public int getX(){
		return rectangle1.x;
	}
	public int getY(){
		return rectangle1.y;
	}
	public int getWidth(){
		return rectangle1.width;
	}
	public int getHeight(){
		return rectangle1.height+rectangle2.height+rectangle3.height;
	}


	public void setName(String name){
		this.name = name;
	}

	public void setRectangle(int x, int y, int width, int height){
		this.rectangle1 = new Rectangle(x, y, width, height/3);
		this.rectangle2 = new Rectangle(x, y+height/3, width, height/3);
		this.rectangle3 = new Rectangle(x, y+2*height/3, width, height/3);	
	}
	public void setColor(Color color){
		this.color = color;
	}
	public boolean isSelected(){
		return color == Color.cyan;
	}


}
