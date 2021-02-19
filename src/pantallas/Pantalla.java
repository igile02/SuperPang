package pantallas;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics;

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

    public void pulsarTeclado(KeyEvent e);

    public void soltarTeclado(KeyEvent e);
}
