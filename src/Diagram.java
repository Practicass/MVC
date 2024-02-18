import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Diagram 
		extends JPanel 
		implements MouseListener, 
			   MouseMotionListener, 
			   KeyListener {
	
	//atributos
	private Window window;//Ventana en la que está el diagrama
	public Class clase; 
	public Class clase2; 
	private Vector<Class> classes = new Vector<Class>(); //las clases que crea el usuario
	private Vector<Association> associations = new Vector<Association>(); // las asociaciones que crea el usuario
	
	// ... (otros posibles atributos)
	int x, y;

	//metodos
	public Diagram(Window theWindow) {
		window = theWindow;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public void addClass() {
		//Añade una clase al diagrama
		classes.add(new Class("clase", 10, 10, 100, 100, Color.white));
		paint(getGraphics());

	}

	public void addAssociation() {
		//Añade una asociación al diagrama
		//...
		associations.add(new Association(clase, clase2));
		paint(getGraphics());
	}
	
	public int getNClasses(){
		//Devuelve el número de clases
		return classes.capacity();

	}
	
	public int getNAssociations(){
		//Devuelve el numero de asociaciones
		return associations.capacity();
	}

	public void paint(Graphics g) {
		//Dibuja el diagrama de clases
				//Dibuja la clase
		Graphics2D g2 = (Graphics2D)g;
		g2.clearRect(0, 0, getWidth(), getHeight());
		for (Association as : associations) {
			as.draw(g2);
		} 
		for (Class cl : classes) {
			cl.draw(g2);
		} 

		
	}
	
	/********************************************/
	/** MÈtodos de MouseListener               **/
	/********************************************/

	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	
		for (Class cl : classes) {
			// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
			if (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
				y >= cl.getY() && y <= cl.getY() + cl.getHeight()) {
				// El mouse se ha pulsado dentro de esta clase
				x -= cl.getX();
				y -= cl.getY();
				clase = cl;
				break;
			}
		}
   	 }
    
    	public void mouseReleased(MouseEvent e) {
 		//…		
			if (clase != null) {
				if(clase.isSelected()){
					x = e.getX();
					y = e.getY();
					for (Class cl : classes) {
						// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
						if (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
							y >= cl.getY() && y <= cl.getY() + cl.getHeight()) {
							// El mouse se ha pulsado dentro de esta clase
							x -= cl.getX();
							y -= cl.getY();
							clase2 = cl;
							break;
						}
					}
					if(clase2 != null){

						this.addAssociation();
						clase = null;
						clase2 = null;
					}

				}
				else{
					paint(getGraphics());
					clase = null;
				}


			}
		
    	}
    
	    public void mouseEntered(MouseEvent e) {
    	}
    
	public void mouseExited(MouseEvent e) {
    	}
    
	public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {

			} else if (e.getButton() == MouseEvent.BUTTON3) {
				int x = e.getX();
				int y = e.getY();
			
				for (Class cl : classes) {
					// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
					if (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
						y >= cl.getY() && y <= cl.getY() + cl.getHeight()) {
						// El mouse se ha pulsado dentro de esta clase
						clase = cl;
						break;
					}
				}
				if(clase != null){
					classes.remove(clase);
					paint(getGraphics());
					clase= null;
				}
				
			}
    	}

	/********************************************/
	/** MÈtodos de MouseMotionListener         **/
	/********************************************/    
    	public void mouseMoved(MouseEvent e) {
		//…
		x=e.getX();
		y=e.getY();
	}
    
	public void mouseDragged(MouseEvent e) {
		//…
		if(clase != null){
			if(!clase.isSelected()){
				clase.setRectangle(e.getX()-x, e.getY()-y, clase.getWidth(), clase.getHeight());
				paint(getGraphics());
			}
		}
		
	}
    
	/********************************************/
	/** MÈtodos de KeyListener                 **/
	/********************************************/

	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_S){
			for (Class cl : classes) {
				// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
				if (cl.isSelected()) {
					// El mouse se ha pulsado dentro de esta clase
					cl.setColor(Color.white);
					paint(getGraphics());
					break;
				}
			}
			for (Class cl : classes) {
				// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
				if (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
					y >= cl.getY() && y <= cl.getY() + cl.getHeight()) {
					// El mouse se ha pulsado dentro de esta clase
					clase = cl;
					break;
				}
			}
			if(clase != null){
				clase.setColor(Color.cyan);
				paint(getGraphics());
				clase= null;
			}
		}

    }
    
	public void keyPressed(KeyEvent e) {
		//…
	}
    
    	public void keyReleased(KeyEvent e) {
    	}
}
