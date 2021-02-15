package pantallas;

import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import principal.PanelJuego;
import principal.Sprite;

public class PantallaJuego implements Pantalla {

    // Constantes Asteroides
    private final static int LADO_ASTEROIDE = 150;
    private final static int INICIO_ASTEROIDE = 20;
    private final static int VELOCIDAD_BOLAS = 4;

    // Constantes de la Nave
    private final static int ANCHO_NAVE = 100;
    private final static int ALTO_NAVE = 100;

    // Constantes disparo
    private final static int ANCHO_DISPARO = 30;
    private final static int ALTO_DISPARO = 60;
    private static final int VELOCIDADY_PROYECTIL = 20;

    // Referencia panelJuego
    private PanelJuego panelJuego;

    private ArrayList<Sprite> bloques;
    private Image vidas;
    private Image img;
    private BufferedImage fondo;
    private BufferedImage vida;
    private Sprite proyectil;
    private ArrayList<Sprite> asteroides;
    private Sprite nave;
    private String tiempo;
    private double tiempoTranscurrido;
    private double tiempoOriginal;
    private Font fuente;

    public PantallaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        inicializarPantalla();
    }

    @Override
    public void inicializarPantalla() {
        fuente = new Font("Comic Sans MS", Font.BOLD, 50);
        tiempoOriginal = System.nanoTime();
        asteroides = new ArrayList<Sprite>();
        bloques = new ArrayList<Sprite>();

        asteroides.add(new Sprite("Imagenes/bolaRoja.png", LADO_ASTEROIDE, LADO_ASTEROIDE, INICIO_ASTEROIDE + 150,
                INICIO_ASTEROIDE, VELOCIDAD_BOLAS, VELOCIDAD_BOLAS));
        asteroides.add(new Sprite("Imagenes/bolaRoja.png", LADO_ASTEROIDE, LADO_ASTEROIDE, INICIO_ASTEROIDE,
                INICIO_ASTEROIDE, VELOCIDAD_BOLAS, VELOCIDAD_BOLAS));

        tiempo = "";

        proyectil = null;

        try {
            fondo = ImageIO.read(new File("Imagenes/mexico.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            vida = ImageIO.read(new File("Imagenes/live.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        vidas = vida.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        redimensionarFondo();
        cargarNivelUno();
    }

    @Override
    public void pintarPantalla(Graphics g) {
        rellenarFondo(g);

        g.setColor(Color.YELLOW);
        g.setFont(fuente);
        g.drawString("TIME " + tiempo, 610, 820);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("VIDAS", 40, 810);
        g.drawImage(vidas, 10, 830, null);
        g.drawImage(vidas, 50, 830, null);
        g.drawImage(vidas, 90, 830, null);

        // TENER EN CUENTA SPRITES!!!
        for (int i = 0; i < asteroides.size(); i++) {
            asteroides.get(i).estanpar(g);
        }

        // Si la nave es distnto de null la pintamos
        if (nave != null) {
            nave.estanpar(g);
        }

        // Si el proyectis es distinto de null y no se sale de la pantalla lo pintamos
        if (proyectil != null) {
            if (proyectil.getAlto() > 740) {
                proyectil = null;
            } else {
                proyectil.estanpar(g);
            }
        }

        for (int i = 0; i < bloques.size(); i++) {
            bloques.get(i).estanpar(g);
        }
    }

    /**
     * Método para rellenar el fondo del componente.
     * 
     * @param g
     */
    private void rellenarFondo(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());
        g.drawImage(img, 0, 0, null);
    }

    @Override
    public void ejecutarFrame() {
        try {
            Thread.sleep(25);
            contarTiempo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (asteroides.size() == 0) {
            panelJuego.cambiarPantalla(new PantallaGanar(panelJuego, tiempo));
        } else {
            moverSprites();
            comprobarColisiones();
        }
    }

    /**
     * Método que se encarga de actualizar el valor de la variable tiempo
     */
    public void actualizarTiempo() {
        double seconds = (tiempoTranscurrido / 1e+9);
        DecimalFormat df = new DecimalFormat("000");
        tiempo = df.format(seconds);
    }

    /**
     * Método que se encarga de hacer la operacion para contar el tiempo
     */
    private void contarTiempo() {
        tiempoTranscurrido = ((System.nanoTime() - tiempoOriginal));
        actualizarTiempo();
    }

    @Override
    public void moverRaton(MouseEvent e) {
    }

    @Override
    public void pulsarRaton(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            nave = new Sprite("Imagenes/playerDerecha.png", ANCHO_NAVE, ALTO_NAVE, 25,
                    panelJuego.getHeight() - 125 - ALTO_NAVE, 50);
        }

        if (SwingUtilities.isLeftMouseButton(e)) {
            asteroides.add(new Sprite("Imagenes/bolaRoja.png", LADO_ASTEROIDE, LADO_ASTEROIDE, e.getX(), e.getY()));
        }
    }

    @Override
    public void redimensionarPantalla(ComponentEvent e) {
        redimensionarFondo();
    }

    /**
     * 
     */
    public void redimensionarFondo() {
        img = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight() - 100, Image.SCALE_SMOOTH);
    }

    public void moverSprites() {
        // Movemos los asteorides
        for (int i = 0; i < asteroides.size(); i++) {
            asteroides.get(i).mover(panelJuego.getWidth(), panelJuego.getHeight());
        }

        // El disparo se va moviendo
        if (proyectil != null) {
            proyectil.disparar();
        }
    }

    public void comprobarColisiones() {
        // Comprobamos colisiones
        for (int i = 0; i < asteroides.size(); i++) {

            if (bloques.size() > 0) {
                for (int z = 0; z < bloques.size(); z++) {
                    asteroides.get(i).colisionPorArribaAbajo(bloques.get(z));
                    asteroides.get(i).colisionPorLados(bloques.get(z));
                }
            }

            if (nave != null) {
                if (asteroides.get(i).colisionCuadradoCirculo(nave)) {
                    // nave = null;
                    // panelJuego.cambiarPantalla(new PantallaPerder(panelJuego));
                }
            }

            // Si los asteriodes llegan a 0 paramos el hilo
            if (proyectil != null) {
                if (asteroides.get(i).colisionCuadradoCirculo(proyectil)) {
                    proyectil = null;
                    if (asteroides.get(i).getAncho() >= 40) {
                        asteroides.add(new Sprite("Imagenes/bolaRoja.png", asteroides.get(i).getAncho() / 2,
                                asteroides.get(i).getAlto() / 2, asteroides.get(i).getPosX() - 60,
                                asteroides.get(i).getPosY(), VELOCIDAD_BOLAS, -VELOCIDAD_BOLAS));
                        asteroides.add(new Sprite("Imagenes/bolaRoja.png", asteroides.get(i).getAncho() / 2,
                                asteroides.get(i).getAlto() / 2, asteroides.get(i).getPosX() + 60,
                                asteroides.get(i).getPosY(), VELOCIDAD_BOLAS, VELOCIDAD_BOLAS));
                    }
                    asteroides.remove(i);
                }
            }

        }

        if (bloques.size() > 0 && proyectil != null) {
            for (int j = 0; j < bloques.size(); j++) {
                if (bloques.get(j).colision(proyectil)) {
                    bloques.remove(j);
                    proyectil = null;
                }
            }
        }

    }

    @Override
    public void pulsarTeclado(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            nave.moverSpriteIzquierda(panelJuego.getWidth() - 30);
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            nave.moverSpriteDerecha(panelJuego.getWidth() - 30);
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (nave != null) {
                if (proyectil == null) {
                    nave.animacionDisparar();
                    proyectil = new Sprite("Imagenes/disparo.png", ANCHO_DISPARO, ALTO_DISPARO,
                            nave.getPosX() + (nave.getAncho() / 2 - ANCHO_DISPARO / 2), nave.getPosY() + 40,
                            VELOCIDADY_PROYECTIL, 0);
                }
            }
        }

    }

    public void cargarNivelUno() {
        bloques.add(new Sprite("Imagenes/bloqueAzul.png", 150, 30, 400, 200));
        bloques.add(new Sprite("Imagenes/bloqueAzul.png", 150, 30, 900, 200));

    }
}
