package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.FontFormatException;
import javax.imageio.ImageIO;
import principal.PanelJuego;
import java.awt.event.KeyEvent;
import java.awt.Image;

/**
 * Clase Pantalla Inicio Pantalla inicial de nuestro juego que muestra el logo
 * de este y un mensaje para empezar a jugar.
 * 
 * @author Iván Gil Esteban
 */
public class PantallaInicio implements Pantalla {

    private Font pixel;
    private Image fondo;
    private Color color;
    // Referencia panelJuego
    private PanelJuego panelJuego;

    // Constructor
    public PantallaInicio(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        inicializarPantalla();
    }

    /**
     * Método para inicializar los elementos de la pantalla
     */
    @Override
    public void inicializarPantalla() {
        cargarFuente();
        try {
            fondo = ImageIO.read(new File("Imagenes/fondoInicio.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        fondo = fondo.getScaledInstance(1480, 920, Image.SCALE_SMOOTH);
        color = Color.YELLOW;
    }

    /**
     * Método para pintar los elementos de la pantalla
     */
    @Override
    public void pintarPantalla(Graphics g) {
        // Rellenamos fondo
        rellenarFondo(g);

        // Pintamos un texto
        g.setFont(pixel);
        g.setColor(color);
        g.drawString("PULSE CUALQUIER TECLA PARA EMPEZAR", panelJuego.getWidth() / 2 - 450, panelJuego.getHeight() / 2);
    }

    /**
     * Método para cargar una fuente externa a ajava
     */
    private void cargarFuente() {
        InputStream is = null;
        try {
            is = new FileInputStream("Fonts/pixel.ttf");
            pixel = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        pixel = pixel.deriveFont(Font.BOLD | Font.ITALIC, 30);
    }

    /**
     * Método para rellenar el fondo del componente.
     * 
     * @param g
     */
    private void rellenarFondo(Graphics g) {
        g.drawImage(fondo, 0, 0, null);
    }

    /**
     * Método que ejecuta las acciones de nuestra pantalla cada frame
     */
    @Override
    public void ejecutarFrame() {
        try {
            Thread.sleep(500);
            // Cambiamos el color cada 500ms
            color = color == Color.RED ? Color.YELLOW : Color.RED;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que cambia de pantalla al pulsar el raton
     */
    @Override
    public void pulsarRaton(MouseEvent e) {
        panelJuego.cambiarPantalla(new PantallaJuego(panelJuego));
    }

    /**
     * Método que cambia de pantalla al pulsar el teclado
     */
    @Override
    public void pulsarTeclado(KeyEvent e) {
        panelJuego.cambiarPantalla(new PantallaJuego(panelJuego));
    }

    @Override
    public void soltarTeclado(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
