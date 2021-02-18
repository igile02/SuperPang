package pantallas;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import principal.PanelJuego;

public class PantallaPerder implements Pantalla {

    private BufferedImage fondo;
    private Image img;
    private PanelJuego panelJuego;
    private Font fuente;

    public PantallaPerder(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        inicializarPantalla();
    }

    @Override
    public void inicializarPantalla() {
        fuente = new Font("Comic Sans MS", Font.BOLD, 40);
        try {
            fondo = ImageIO.read(new File("Imagenes/perder.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        img = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
    }

    @Override
    public void pintarPantalla(Graphics g) {
        rellenarFondo(g);

        g.setColor(Color.CYAN);
        g.setFont(fuente);
        g.drawString("Haz click pra volver a jugar", 20, panelJuego.getHeight() - (panelJuego.getHeight() - 100));

        g.setColor(Color.RED);
        g.setFont(fuente);
        g.drawString("YOU LOSE!!!", panelJuego.getWidth() - 300, panelJuego.getHeight() - 50);
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
    public void moverRaton(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pulsarRaton(MouseEvent e) {
        panelJuego.cambiarPantalla(new PantallaJuego(panelJuego));
    }

    @Override
    public void redimensionarPantalla(ComponentEvent e) {
        redimensionarFondo();
    }

    /**
     * 
     */
    public void redimensionarFondo() {
        img = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), Image.SCALE_SMOOTH);
    }

    @Override
    public void pulsarTeclado(KeyEvent  e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void soltarTeclado(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
