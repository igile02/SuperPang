package principal;

import java.awt.Graphics;
import java.awt.Toolkit;
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
public class PanelJuego extends JPanel implements Runnable, MouseMotionListener, MouseListener, KeyListener {

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
        this.addKeyListener(this);
        new Thread(this).start();
    }

    // Método que se llama automáticamente para pintar el componente.
    @Override
    public void paintComponent(Graphics g) {
        pantalla.pintarPantalla(g);

    }

    /**
     * Método que se ejcutara todo el tiempo y llama al metodo ejecutarFrame de
     * nuestra pantalla para que realize sus distintas acciones
     */
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
     * Listener que implementa las acciones al pulsar el ratón de nuestra pantalla
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

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * Método para cambiar la pantalla actual, le pasamos la pantalla nueva por
     * parametros
     * 
     * @param pantallaNueva pantallaNueva que recibe
     */
    public void cambiarPantalla(Pantalla pantallaNueva) {
        pantalla = pantallaNueva;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * Método que realizara la acciones de nuestra pantalla al pulsar el teclado
     */
    @Override
    public void keyPressed(KeyEvent e) {
        pantalla.pulsarTeclado(e);
    }

    /**
     * Método que realiza las accionesque implementa nuestra pantalla al soltar el
     * teclado
     */
    @Override
    public void keyReleased(KeyEvent e) {
        pantalla.soltarTeclado(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}
