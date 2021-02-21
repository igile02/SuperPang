package pantallas;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

/**
 * Interfaz pantalla que se implementara en todas nuestras pantallas de juego
 */
public interface Pantalla {
    
    /**
     * Métodos que hay que llevar a cabo para inicialiar la pantalla.
     */
    public void inicializarPantalla();

    /**
     * Método que pinta la pantalla.
     */
    public void pintarPantalla(Graphics g);

    /**
     * Acciones que llevará a cabo la pantalla en cada Frame.
     */
    public void ejecutarFrame();


    /**
     * Método que se llevará a cabo cuando se pulse el ratón en la pantalla.
     */
    public void pulsarRaton(MouseEvent e);

    /**
     * Método que se llevará a cabo cuando se pulse el teclado
     */
    public void pulsarTeclado(KeyEvent e);

    /**
     * Método que se llevara a cabo cuando se suelte el teclado
     */
    public void soltarTeclado(KeyEvent e);
}
