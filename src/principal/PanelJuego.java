package principal;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import pantallas.Pantalla;
import pantallas.PantallaInicio;

/**
 * Clase PanelJuego
 * 
 * @author Iván Gil Esteban
 */
public class PanelJuego extends JPanel
        implements Runnable, MouseMotionListener, ComponentListener, MouseListener, KeyListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Pantalla pantalla;

    /**
     * Constructor
     */
    public PanelJuego() {
        setFocusable(true);
        pantalla = new PantallaInicio(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
        this.addKeyListener(this);
        new Thread(this).start();
    }

    // Método que se llama automáticamente para pintar el componente.
    @Override
    public void paintComponent(Graphics g) {
        pantalla.pintarPantalla(g);

    }

    @Override
    public void run() {
        while (true) {

            pantalla.ejecutarFrame();

            // Repintar el componente:
            repaint();
            Toolkit.getDefaultToolkit().sync();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Listener que implementa el disparar y poner la nave
     */
    @Override
    public void mousePressed(MouseEvent e) {
        pantalla.pulsarRaton(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Listener que rescala la imagen cuando la ventana se haga mas grande o mas
     * pequeña
     */
    @Override
    public void componentResized(ComponentEvent e) {
        pantalla.redimensionarPantalla(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentShown(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Método que hace que la nave siga el raton
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        pantalla.moverRaton(e);
    }

    public void cambiarPantalla(Pantalla pantallaNueva) {
        pantalla = pantallaNueva;
    }

    public Pantalla getPantalla() {
        return pantalla;
    }

    public void setPantalla(Pantalla pantalla) {
        this.pantalla = pantalla;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        pantalla.pulsarTeclado(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pantalla.soltarTeclado(e);
    }


}
