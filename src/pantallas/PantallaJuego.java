package pantallas;

import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import principal.PanelJuego;
import principal.Sprite;
import java.awt.Toolkit;

public class PantallaJuego implements Pantalla {

    // Distintos Fondos
    private final static String[] FONDOS = { "Imagenes/mexico.png", "Imagenes/china.png", "Imagenes/arabia.png",
            "Imagenes/playa.png", "Imagenes/japon.png" };

    // Constantes de las bolas
    private final static int LADO_BOLA = 200;
    private final static int VELOCIDAD_BOLAS = 4;
    private final static int POSY_BOLAS = 20;
    private final static int PUNTUACION_BOLAS_GRANDES = 200;
    private final static int PUNTUACION_BOLAS_MEDIANAS = 400;
    private final static int PUNTUACION_BOLAS_PEQUENIAS = 800;
    private final static int PUNTUACION_BOLAS_MINIS = 1600;
    private final static int MAX_BOLAS = 4;
    private final static int MIN_BOLAS = 1;
    private final static String[] COLOR_BOLA = { "Imagenes/bolaRoja.png", "Imagenes/bolaAzul.png",
            "Imagenes/bolaVerde.png" };
    private final static String[] COLOR_EXP = { "Imagenes/explosionRoja.png", "Imagenes/explosionAzul.png",
            "Imagenes/explosionVerde.png" };

    // Colores de los bloques
    private final static String[] COLOR_BLOQUE = { "Imagenes/bloqueAmarillo.png", "Imagenes/bloqueAzul.png",
            "Imagenes/bloqueRojo.png" };
    private final static int POSX_UNO = 200;
    private final static int POSX_DOS = 500;
    private final static int POSX_TRES = 800;
    private final static int POSX_CUATRO = 1100;
    private final static int POSY_UNO_BLOQUES = 300;
    private final static int POSY_DOS_BLOQUES = 400;
    private final static int MIN_BLOQUES = 2;
    private final static int MAX_BLOQUES = 4;
    private final static int ALTO_BLOQUE = 30;
    private final static int ANCHO_BLOQUE = 150;

    // Constantes de nuestro player
    private final static int ANCHO_PLAYER = 100;
    private final static int ALTO_PLAYER = 100;
    private final static int VELOCIDAD_PLAYER = 20;
    private final static int MAX_VIDAS = 3;

    // Constantes arpon
    private final static int ANCHO_ARPON = 30;
    private final static int ALTO_ARPON = 60;
    private static final int VELOCIDADY_ARPON = 25;

    private ArrayList<Integer> posicionesBloquesX;
    private ArrayList<Integer> posicionesBloquesY;

    // Referencia panelJuego
    private PanelJuego panelJuego;

    private ArrayList<Sprite> bloques;
    private Image vidas;
    private Image img;
    private BufferedImage fondo;
    private BufferedImage vida;
    private Sprite arpon;
    private ArrayList<Sprite> bolas;
    private Sprite player;
    private String tiempo;
    private int puntos;
    private double tiempoTranscurrido;
    private double tiempoOriginal;
    private boolean esFinal;
    private boolean esDescanso;
    private DecimalFormat df;
    private boolean moverSpriteIzquierda;
    private boolean moverSpriteDerecha;
    private int nivel;
    private Font pixel;
    private int vidasActuales;
    private String colorBloque;
    private String colorBola;
    private int aleBola;
    private int numBolas;
    private int numBloques;
    private int aleFondo;
    private int aleBloque;
    private Image carga;

    public PantallaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        inicializarPantalla();
    }

    @Override
    public void inicializarPantalla() {
        cargarFuente();
        vidasActuales = MAX_VIDAS;
        tiempoOriginal = System.nanoTime();
        bolas = new ArrayList<Sprite>();
        bloques = new ArrayList<Sprite>();
        posicionesBloquesX = new ArrayList<Integer>();
        posicionesBloquesY = new ArrayList<Integer>();
        nivel = 1;

        tiempo = "100";

        puntos = 0;

        arpon = null;

        player = new Sprite("Imagenes/playerDerecha.png", ANCHO_PLAYER, ALTO_PLAYER, 25,
                panelJuego.getHeight() - 125 - ALTO_PLAYER, VELOCIDAD_PLAYER);

        try {
            vida = ImageIO.read(new File("Imagenes/live.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        vidas = vida.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        try {
            carga = ImageIO.read(new File("Imagenes/carga.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        carga = carga.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight() - 100, Image.SCALE_SMOOTH);

        cargarNivel();
        redimensionarFondo();
    }

    private void cargarFuente() {
        InputStream is = null;
        try {
            is = new FileInputStream("Fonts/pixel.ttf");
            pixel = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        pixel = pixel.deriveFont(Font.BOLD, 18);
    }

    @Override
    public void pintarPantalla(Graphics g) {
        rellenarFondo(g);

        g.setColor(Color.YELLOW);
        g.setFont(pixel.deriveFont(Font.BOLD, 50));
        g.drawString("TIME " + tiempo, 600, 830);

        g.setFont(pixel);
        g.drawString("NIVEL " + nivel, 730, 860);

        g.drawString("PUNTUACION: " + String.format("%06d", puntos), 1100, 810);

        g.setColor(Color.YELLOW);
        g.drawString("VIDAS", 30, 810);
        if (vidasActuales == 3) {
            g.drawImage(vidas, 10, 830, null);
            g.drawImage(vidas, 50, 830, null);
            g.drawImage(vidas, 90, 830, null);
        } else if (vidasActuales == 2) {
            g.drawImage(vidas, 10, 830, null);
            g.drawImage(vidas, 50, 830, null);
        } else if (vidasActuales == 1) {
            g.drawImage(vidas, 10, 830, null);
        }

        // TENER EN CUENTA SPRITES!!!
        for (int i = 0; i < bolas.size(); i++) {
            bolas.get(i).estanpar(g);
        }

        // Si el proyectis es distinto de null y no se sale de la pantalla lo pintamos
        if (arpon != null) {
            if (arpon.getAlto() > 740) {
                arpon = null;
            } else {
                arpon.estanpar(g);
            }
        }

        // Si la nave es distnto de null la pintamos
        if (player != null) {
            player.estanpar(g);
        }

        for (int i = 0; i < bloques.size(); i++) {
            bloques.get(i).estanpar(g);
        }

        if (esDescanso) {
            g.drawImage(carga, 0, 0, null);
            g.setColor(Color.YELLOW);
            g.setFont(pixel.deriveFont(Font.BOLD, 50));
            g.drawString("STAGE COMPLETE", 400, 430);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!tiempo.equals("000")) {
            if (esDescanso) {
                try {
                    Thread.sleep(3 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                cargarNivel();
                esDescanso = false;
            } else {
                if (bolas.size() == 0) {
                    player.animacionGanar();
                    esDescanso = true;
                    nivel++;
                } else {
                    moverSprites();
                    comprobarColisiones();
                }
            }
            contarTiempo();
        } else if (tiempo.equals("000") && vidasActuales > 0) {
            perderVida();
            respawnear();
        } else {
            // game over
        }
    }

    /**
     * Método que se encarga de actualizar el valor de la variable tiempo
     */
    public void actualizarTiempo() {
        double seconds = (tiempoTranscurrido / 1e+9);
        df = new DecimalFormat("000");
        tiempo = df.format(100 - seconds);
    }

    /**
     * Método que se encarga de hacer la operacion para contar el tiempo
     */
    private void contarTiempo() {
        tiempoTranscurrido = ((System.nanoTime() - tiempoOriginal));
        actualizarTiempo();
    }
    
    @Override
    public void pulsarRaton(MouseEvent e) {
    }

    /**
     * 
     */
    public void redimensionarFondo() {
        img = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight() - 100, Image.SCALE_SMOOTH);
    }

    public void moverSprites() {
        // Movemos los asteorides
        for (int i = 0; i < bolas.size(); i++) {
            bolas.get(i).mover(panelJuego.getWidth(), panelJuego.getHeight());
        }

        // El disparo se va moviendo
        if (arpon != null) {
            arpon.disparar();
        }

        if (moverSpriteDerecha) {
            player.moverSpriteDerecha(panelJuego.getWidth() - 30);
        }

        if (moverSpriteIzquierda) {
            player.moverSpriteIzquierda(panelJuego.getWidth() - 30);
        }
    }

    public void comprobarColisiones() {
        // Comprobamos colisiones
        for (int i = 0; i < bolas.size(); i++) {

            if (bloques.size() > 0) {
                for (int z = 0; z < bloques.size(); z++) {
                    bolas.get(i).colisionLadosUpDown(bloques.get(z));
                }
            }

            if (player != null) {
                if (vidasActuales > 0) {
                    if (bolas.get(i).colisionCuadradoCirculo(player)) {
                        // perderVida();
                        // respawnear();
                    }
                } else {
                    // Cambiar a gameOver
                }
            }

            // Si los asteriodes llegan a 0 paramos el hilo
            if (arpon != null) {
                if (bolas.get(i).colisionCuadradoCirculo(arpon)) {

                    bolas.get(i).explotar(COLOR_EXP[aleBola]);

                    panelJuego.repaint();
                    Toolkit.getDefaultToolkit().sync();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    arpon = null;
                    if (bolas.get(i).getAncho() >= 40) {
                        bolas.add(new Sprite(colorBola, bolas.get(i).getAncho() / 2, bolas.get(i).getAlto() / 2,
                                bolas.get(i).getPosX() - 60, bolas.get(i).getPosY(), VELOCIDAD_BOLAS,
                                -VELOCIDAD_BOLAS));
                        bolas.add(new Sprite(colorBola, bolas.get(i).getAncho() / 2, bolas.get(i).getAlto() / 2,
                                bolas.get(i).getPosX() + 60, bolas.get(i).getPosY(), VELOCIDAD_BOLAS, VELOCIDAD_BOLAS));
                    }
                    actualizarPuntos(bolas.get(i).getAncho());
                    bolas.remove(i);
                }
            }
        }

        if (bloques.size() > 0 && arpon != null) {
            for (int j = 0; j < bloques.size(); j++) {
                if (arpon != null) {
                    if (bloques.get(j).colision(arpon)) {
                        bloques.remove(j);
                        arpon = null;
                    }
                }
            }
        }
    }

    private void respawnear() {
        // Poner al player en el centro
        player.playerDerecha();
        player.setPosX((panelJuego.getWidth() / 2) - (player.getAncho() / 2));
        recargarNivel();
    }

    private void recargarNivel() {
        colorBloque = COLOR_BLOQUE[aleBloque];
        colorBola = COLOR_BOLA[aleBola];
        cargarFondo(FONDOS[aleFondo]);
        arpon = null;
        bolas.clear();
        bloques.clear();
        player.playerDerecha();
        player.setPosX((panelJuego.getWidth() / 2) - (player.getAncho() / 2));

        renovarTiempo();

        cargarNivelNuevo();
    }

    private void renovarTiempo() {
        tiempo = "100";
        tiempoOriginal = System.nanoTime();
    }

    private void cargarNivel() {
        aleBola = aleatorio(0, 3);
        aleBloque = aleatorio(0, 3);
        aleFondo = aleatorio(0, 5);
        numBolas = aleatorio(MIN_BOLAS, MAX_BOLAS + 1);
        numBloques = aleatorio(MIN_BLOQUES, MAX_BLOQUES + 1);
        colorBloque = COLOR_BLOQUE[aleBloque];
        colorBola = COLOR_BOLA[aleBola];
        cargarFondo(FONDOS[aleFondo]);
        arpon = null;
        bolas.clear();
        bloques.clear();
        player.playerDerecha();
        player.setPosX((panelJuego.getWidth() / 2) - (player.getAncho() / 2));

        renovarTiempo();

        if (nivel == 1) {
            cargarNivelUno();
        } else {
            cargarNivelNuevo();
        }
    }

    private void cargarNivelUno() {
        bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS, VELOCIDAD_BOLAS));
        bloques.add(new Sprite(colorBloque, ANCHO_BLOQUE, ALTO_BLOQUE, POSX_DOS, POSY_UNO_BLOQUES));
        bloques.add(new Sprite(colorBloque, ANCHO_BLOQUE, ALTO_BLOQUE, POSX_TRES, POSY_UNO_BLOQUES));
    }

    private void cargarFondo(String ruta) {
        try {
            fondo = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void actualizarPuntos(int ancho) {
        switch (ancho) {
            case 200:
                puntos += PUNTUACION_BOLAS_GRANDES;
                break;
            case 100:
                puntos += PUNTUACION_BOLAS_MEDIANAS;
                break;
            case 50:
                puntos += PUNTUACION_BOLAS_PEQUENIAS;
                break;
            case 25:
                puntos += PUNTUACION_BOLAS_MINIS;
                break;
        }
    }

    @Override
    public void pulsarTeclado(KeyEvent e) {
        if (player != null && !esDescanso) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                moverSpriteIzquierda = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_D) {
                moverSpriteDerecha = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (arpon == null) {
                    player.animacionDisparar();
                    arpon = new Sprite("Imagenes/disparo.png", ANCHO_ARPON, ALTO_ARPON,
                            player.getPosX() + (player.getAncho() / 2 - ANCHO_ARPON / 2), player.getPosY() + 40,
                            VELOCIDADY_ARPON, 0);
                }
            }
        }
    }

    public void cargarNivelNuevo() {
        cargarBolas();
        cargarBloques();
    }

    @Override
    public void soltarTeclado(KeyEvent e) {
        if (player != null) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                moverSpriteIzquierda = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_D) {
                moverSpriteDerecha = false;
            }
        }
    }

    public void perderVida() {
        vidasActuales--;
        player.animacionMorir();

        panelJuego.repaint();
        Toolkit.getDefaultToolkit().sync();

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int aleatorio(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public void cargarBloques() {
        posicionesBloquesX.clear();
        cargarArrayPosicioneX();
        posicionesBloquesY.clear();
        cargarArrayPosicionesY();

        for (int i = 0; i < numBloques; i++) {
            int aleX = aleatorio(0, posicionesBloquesX.size());
            int aleY = aleatorio(0, posicionesBloquesY.size());

            bloques.add(new Sprite(colorBloque, ANCHO_BLOQUE, ALTO_BLOQUE, posicionesBloquesX.get(aleX),
                    posicionesBloquesY.get(aleY)));
            posicionesBloquesX.remove(aleX);
            posicionesBloquesY.remove(aleY);
        }
    }

    public void cargarBolas() {
        switch (numBolas) {
            case 1:
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                break;
            case 2:
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_DOS, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                break;
            case 3:
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_UNO, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_DOS, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                break;
            case 4:
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_UNO, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_DOS, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_CUATRO, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS));
                break;
        }

    }

    public void cargarArrayPosicioneX() {
        posicionesBloquesX.add(POSX_UNO);
        posicionesBloquesX.add(POSX_DOS);
        posicionesBloquesX.add(POSX_TRES);
        posicionesBloquesX.add(POSX_CUATRO);
    }

    public void cargarArrayPosicionesY() {
        for (int i = 0; i < 3; i++) {
            posicionesBloquesY.add(POSY_UNO_BLOQUES);
        }
        posicionesBloquesY.add(POSY_DOS_BLOQUES);
    }
}
