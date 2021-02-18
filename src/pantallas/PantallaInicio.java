package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import principal.PanelJuego;
import java.awt.event.KeyEvent;

public class PantallaInicio implements Pantalla {

    private Font fuente;
    // Referencia panelJuego
    private PanelJuego panelJuego;

    public PantallaInicio(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        inicializarPantalla();
    }

    @Override
    public void inicializarPantalla() {
        fuente = new Font("Arial", Font.BOLD, 20);
    }

    @Override
    public void pintarPantalla(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());

        g.setFont(fuente);
        g.setColor(Color.WHITE);
        g.drawString("\"Bienvenidos a DAM-e 2 Asteroides\"", panelJuego.getWidth() / 4 - 40,
                panelJuego.getHeight() / 3);

        g.setColor(Color.RED);
        g.drawString("Pulsa para jugar", panelJuego.getWidth() / 3, panelJuego.getHeight() / 2);
        g.setColor(colorAleatorio());
        g.drawRect(panelJuego.getWidth() / 3 - 20, panelJuego.getHeight() / 2 - 20, 200, 30);
    }

    /**
     * Metodo para sacar un color aleatorio.
     * 
     * @return color RGB.
     */
    public Color colorAleatorio() {
        return new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
    }

    @Override
    public void ejecutarFrame() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        // TODO Auto-generated method stub

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
