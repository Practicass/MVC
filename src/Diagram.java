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
	public Class claseSeleccionada; 
	public Class claseAsociar; 
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
		associations.add(new Association(claseSeleccionada, claseAsociar));
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

	private boolean inClass(Class cl){
		return (x >= cl.getX() && x <= cl.getX() + cl.getWidth() &&
				y >= cl.getY() && y <= cl.getY() + cl.getHeight());
	}
	
	/********************************************/
	/** MÈtodos de MouseListener               **/
	/********************************************/

	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	
		for (int i = classes.size() - 1; i >= 0; i--) {
			Class cl=classes.get(i);			// Suponiendo que Class tiene métodos getX(), getY(), getWidth() y getHeight()
			if (inClass(cl)) {
				x -= cl.getX();
				y -= cl.getY();
				claseSeleccionada = cl;
				break;
			}
		}
   	 }
    
    	public void mouseReleased(MouseEvent e) {
 		//…		
			if (claseSeleccionada != null) {
				if(claseSeleccionada.isSelected()){
					x = e.getX();
					y = e.getY();
					for (Class cl : classes) {
						if (inClass(cl)) {
							x -= cl.getX();
							y -= cl.getY();
							claseAsociar = cl;
							break;
						}
					}
					if(claseAsociar != null){
						boolean existente = false;
						for(Association as : associations){
							if((as.isClass1(claseSeleccionada) && as.isClass2(claseAsociar)) || (as.isClass1(claseAsociar) && as.isClass2(claseSeleccionada))){
								existente = true;
							}
						}
						if(!existente){
							this.addAssociation();
							window.updateNAssociations(this);


						}
						claseAsociar.setColor(Color.white);
						claseSeleccionada.setColor(Color.white);
						repaint();

						claseSeleccionada = null;
						claseAsociar = null;

					}

				}
				else{
					repaint();
					claseSeleccionada = null;
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
				x = e.getX();
				y = e.getY();
			
				for (Class cl : classes) {
					if (inClass(cl)) {
						claseSeleccionada = cl;
					}
				}
				if(claseSeleccionada != null){
					classes.remove(claseSeleccionada);

					Iterator<Association> iterator = associations.iterator();
					while (iterator.hasNext()) {
						Association as = iterator.next();
						if (as.isClass1(claseSeleccionada) || as.isClass2(claseSeleccionada)) {
							iterator.remove();
						}
					}
					repaint();
					claseSeleccionada= null;
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
			if (inClass(cl)) {
				claseSeleccionada = cl;
				classes.remove(cl);
				classes.insertElementAt(cl, classes.size());
				repaint();
				break;
			}
			
		}
		claseSeleccionada=null;
	}
    
	public void mouseDragged(MouseEvent e) {
		//…
		if(claseSeleccionada != null){
			if(!claseSeleccionada.isSelected()){
				claseSeleccionada.setRectangle(e.getX()-x, e.getY()-y, claseSeleccionada.getWidth(), claseSeleccionada.getHeight());
				repaint();
			}else{
				x = e.getX();
				y = e.getY();
				for (Class cl : classes) {
					if(cl != claseSeleccionada){
						if (inClass(cl)) {
							claseAsociar = cl;
							claseAsociar.setColor(Color.green);
							for(Association as : associations){
								if((as.isClass1(claseSeleccionada) && as.isClass2(claseAsociar) )|| (as.isClass1(claseAsociar) && as.isClass2(claseSeleccionada))){
									claseAsociar.setColor(Color.red);
								}
							}
							repaint();
							break;
						}else if(claseAsociar!=null){
							claseAsociar.setColor(Color.white);
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
				if (inClass(cl)) {
					claseSeleccionada = cl;
					if(claseSeleccionada.isSelected()){
						claseSeleccionada.setColor(Color.white);
					}else{
						claseSeleccionada.setColor(Color.cyan);
					}
					break;
				}
			}
			if(claseSeleccionada != null){
				for (Class cl : classes) {
						if(cl != claseSeleccionada){
							cl.setColor(Color.white);
						}
					
				}
			}else{
					for (Class cl : classes) {
								cl.setColor(Color.white);
					}
			}
			repaint();

		}

    }
	public void keyPressed(KeyEvent e) {
		//…
	}
    
    	public void keyReleased(KeyEvent e) {
    	}

}
