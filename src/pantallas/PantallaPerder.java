package pantallas;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import principal.PanelJuego;

/**
 * Clase Pantalla Perder
 * 
 * @author Iván Gil Esteban
 */
public class PantallaPerder implements Pantalla {

    // Atributos de la clase
    private BufferedImage fondo;
    private Image img;
    private PanelJuego panelJuego;
    private Font pixel;
    private int puntos;
    private Color color;

    // Constructor
    public PantallaPerder(PanelJuego panelJuego, int puntos) {
        this.panelJuego = panelJuego;
        this.puntos = puntos;
        inicializarPantalla();
    }

    /**
     * Método para incializar los elementos de la pantalla
     */
    @Override
    public void inicializarPantalla() {
        cargarFuente();
        try {
            fondo = ImageIO.read(new File("Imagenes/gameOver.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
    }

    /**
     * Método para cargar una fuente externa de java
     */
    private void cargarFuente() {
        InputStream is = null;
        try {
            is = new FileInputStream("Fonts/pixel.ttf");
            pixel = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        pixel = pixel.deriveFont(Font.BOLD | Font.ITALIC, 30);
    }

    /**
     * Método para pintar en pantalla los distintos elementos que indiquemos
     */
    @Override
    public void pintarPantalla(Graphics g) {
        // Pintamos el fondo
        rellenarFondo(g);

        // Pintamos un texto indicando volver al menu
        g.setFont(pixel);
        g.setColor(color);
        g.drawString("PULSA CUALQUIER TECLA PARA VOLVER AL MENU", panelJuego.getWidth() / 2 - 500,
                panelJuego.getHeight() / 2);

        // Pintamos los puntos conseguidos
        g.setColor(Color.BLUE);
        g.drawString("HAS CONSEGUIDO " + puntos + " PUNTOS", panelJuego.getWidth() / 2 - 300,
                panelJuego.getHeight() / 2 - 300);
    }

    /**
     * Método para rellenar el fondo del componente.
     * 
     * @param g
     */
    private void rellenarFondo(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    /**
     * Método que ejecuta las acciones cada frame
     */
    @Override
    public void ejecutarFrame() {
        try {
            Thread.sleep(500);
            //Cambiamos el color cada 500ms
            color = color == Color.BLUE ? Color.YELLOW : Color.BLUE;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que realiza las acciones correspondientes al pulsar el raton
     */
    @Override
    public void pulsarRaton(MouseEvent e) {
        // Cambiamos de pantalla a la pantalla inicio
        panelJuego.cambiarPantalla(new PantallaInicio(panelJuego));
    }

    /**
     * Método que realiza las acciones correspondientes al pulsar el teclado
     */
    @Override
    public void pulsarTeclado(KeyEvent e) {
        // Cambiamos de pantalla a la pantalla inicio
        panelJuego.cambiarPantalla(new PantallaInicio(panelJuego));
    }

    @Override
    public void soltarTeclado(KeyEvent e) {
    }
}