package pantallas;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;

import javax.annotation.processing.FilerException;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import principal.PanelJuego;

public class PantallaPerder implements Pantalla {

    private BufferedImage fondo;
    private Image img;
    private PanelJuego panelJuego;
    private Font pixel;

    public PantallaPerder(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        inicializarPantalla();
    }

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
        pixel = pixel.deriveFont(Font.BOLD|Font.ITALIC, 30);
    }

    @Override
    public void pintarPantalla(Graphics g) {
        rellenarFondo(g);

        g.setFont(pixel);
        g.setColor(Color.BLUE);
        g.drawString("PULSA CUALQUIER TECLA PARA VOLVER AL MENU", panelJuego.getWidth()/2-500, panelJuego.getHeight()/2);
    }

    /**
     * Método para rellenar el fondo del componente.
     * 
     * @param g
     */
    private void rellenarFondo(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    @Override
    public void ejecutarFrame() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pulsarRaton(MouseEvent e) {
        panelJuego.cambiarPantalla(new PantallaInicio(panelJuego));
    }

    @Override
    public void pulsarTeclado(KeyEvent  e) {
        panelJuego.cambiarPantalla(new PantallaInicio(panelJuego));
    }

    @Override
    public void soltarTeclado(KeyEvent e) {
    }
}
