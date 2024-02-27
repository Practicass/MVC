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
	private int nClasses = 0; //Número de clases
	public Class clase; 
	public Class clase2; 
	private Vector<Class> classes = new Vector<Class>(); //las clases que crea el usuario
	private Vector<Association> associations = new Vector<Association>(); // las asociaciones que crea el usuario
	
	// ... (otros posibles atributos)
	private int x, y;

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
		classes.add(new Class("clase "+nClasses, 10, 10, 100, 100, Color.white));
		nClasses++;
		repaint();
	}

	public void addAssociation() {
		//Añade una asociación al diagrama
		//...
		associations.add(new Association(clase, clase2));
		repaint();
	}
	
	public int getNClasses(){
		//Devuelve el número de clases
		return classes.size();

	}
	
	public int getNAssociations(){
		//Devuelve el numero de asociaciones
		return associations.size();
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
	
		for (int i = classes.size() - 1; i >= 0; i--) {
			Class cl=classes.get(i);			// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
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
						boolean existente = false;
						for(Association as : associations){
							if((as.isClass1(clase) && as.isClass2(clase2)) || (as.isClass1(clase2) && as.isClass2(clase))){
								existente = true;
							}
						}
						if(!existente){
							this.addAssociation();
							window.updateNAssociations(this);


						}
						clase2.setColor(Color.white);
						clase.setColor(Color.white);
						// paint(getGraphics());
						repaint();

						clase = null;
						clase2 = null;

					}

				}
				else{
					repaint();
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

			} else if (e.getButton() == MouseEvent.BUTTON3) { //BUTTON3 es el botón derecho
				int x = e.getX();
				int y = e.getY();
			
				for (Class cl : classes) {
					// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
					if (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
						y >= cl.getY() && y <= cl.getY() + cl.getHeight()) {
						// El mouse se ha pulsado dentro de esta clase
						clase = cl;
						
					}
				}
				if(clase != null){
					classes.remove(clase);

					Iterator<Association> iterator = associations.iterator();
					while (iterator.hasNext()) {
						Association as = iterator.next();
						if (as.isClass1(clase) || as.isClass2(clase)) {
							iterator.remove();
						}
					}
					repaint();
					clase= null;
					window.updateNClasses(this);
					window.updateNAssociations(this);
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
		for (int i = classes.size() - 1; i >= 0; i--) {
			Class cl=classes.get(i);
			// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
			if (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
			y >= cl.getY() && y <= cl.getY() + cl.getHeight()) {
				// El mouse se ha pulsado dentro de esta clase
				clase = cl;
				classes.remove(cl);
				classes.insertElementAt(cl, classes.size());
				repaint();
				break;
			}
			
		}
		clase=null;
	}
    
	public void mouseDragged(MouseEvent e) {
		//…
		if(clase != null){
			if(!clase.isSelected()){
				clase.setRectangle(e.getX()-x, e.getY()-y, clase.getWidth(), clase.getHeight());
				repaint();
			}else{
				x = e.getX();
				y = e.getY();
				for (Class cl : classes) {
					if(cl != clase){
						// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
						if (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
						y >= cl.getY() && y <= cl.getY() + cl.getHeight()) {
							// El mouse se ha pulsado dentro de esta clase
							clase2 = cl;
							clase2.setColor(Color.green);
							for(Association as : associations){
								if((as.isClass1(clase) && as.isClass2(clase2) )|| (as.isClass1(clase2) && as.isClass2(clase))){
									clase2.setColor(Color.red);
								}
							}
							// paint(getGraphics());
							repaint();
							break;
						}else if(clase2!=null){
							clase2.setColor(Color.white);
							// paint(getGraphics());
							repaint();
						}
					}
					
				}

			}
		}
		
	}
    
	/********************************************/
	/** MÈtodos de KeyListener                 **/
	/********************************************/

	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == KeyEvent.VK_S){

			for (int i = classes.size() - 1; i >= 0; i--) {
				Class cl=classes.get(i);
				if (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
					y >= cl.getY() && y <= cl.getY() + cl.getHeight()) {
					clase = cl;
					if(clase.isSelected()){
						clase.setColor(Color.white);
					}else{
						clase.setColor(Color.cyan);
					}
					break;
				}
			}
			if(clase != null){
				for (Class cl : classes) {
						if(cl != clase){
							cl.setColor(Color.white);
						}
					
				}
			}else{
					for (Class cl : classes) {
								cl.setColor(Color.white);
					}
			}
			repaint();
			clase= null;

		}

    }
    
	public void keyPressed(KeyEvent e) {
		//…
	}
    
    	public void keyReleased(KeyEvent e) {
    	}
}
