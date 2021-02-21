package pantallas;

import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
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
    private final static int MAX_BOLAS = 3;
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

    // Constante tiempo
    private final static String MAX_TIEMPO = "100";

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

    // Constructor de la clase
    public PantallaJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
        inicializarPantalla();
    }

    /**
     * Método para inicializar los componentes de nuestra pantalla
     */
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

        tiempo = MAX_TIEMPO;

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

    /**
     * Método para cargar una fuente propia
     */
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

    /**
     * Método que pinta en el graphics todo lo que nosotros añadamos
     */
    @Override
    public void pintarPantalla(Graphics g) {
        // Pintamos el fondo
        rellenarFondo(g);

        // Le ponemos la fuente al texto y pintamos el tiempo
        g.setColor(Color.YELLOW);
        g.setFont(pixel.deriveFont(Font.BOLD, 50));
        g.drawString("TIME " + tiempo, 600, 830);

        // Pintamos el nivel actual
        g.setFont(pixel);
        g.drawString("NIVEL " + nivel, 730, 860);

        // Pintamos la puntuación
        g.drawString("PUNTUACION: " + String.format("%06d", puntos), 1100, 810);

        // Pintamos las vidas
        g.drawString("VIDAS", 30, 810);

        // Pintamos los sprites de las vidas, dependiendo del número de vidas
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

        // Pintamos las bolas
        for (int i = 0; i < bolas.size(); i++) {
            bolas.get(i).estanpar(g);
        }

        // Si el arpon es distinto de null y no se sale de la pantalla lo pintamos
        if (arpon != null) {
            if (arpon.getAlto() > 740) {
                arpon = null;
            } else {
                arpon.estanpar(g);
            }
        }

        // Si el player es distnto de null la pintamos
        if (player != null) {
            player.estanpar(g);
        }

        // Pintamos los bloques de nuestro juego
        for (int i = 0; i < bloques.size(); i++) {
            bloques.get(i).estanpar(g);
        }

        // Si es descanso cambiamos el fondo y escribimos nivel completado
        if (esDescanso) {
            g.drawImage(carga, 0, 0, null);
            g.setColor(Color.YELLOW);
            g.setFont(pixel.deriveFont(Font.BOLD, 50));
            g.drawString("STAGE COMPLETE", 400, 430);
        }

    }

    /**
     * Método para rellenar el fondo del componente con una imagen que no ocupa todo
     * y de color negro lo sobrante.
     * 
     * @param g graphics
     */
    private void rellenarFondo(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panelJuego.getWidth(), panelJuego.getHeight());
        g.drawImage(img, 0, 0, null);
    }

    /**
     * Método que se va ejecutar constantemente realizando las distintas acciones de
     * nuestro juego
     */
    @Override
    public void ejecutarFrame() {
        // Dormimos 25ms para que funcione mejor
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Si el tiempo no es 0 realizamos las distintas acciones
        if (!tiempo.equals("000")) {
            // Si es descanso dormimos el hilo durante 2 segundos y cargamos un nivel nuevo,
            // despues ponemos el descanso a false
            if (esDescanso) {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                cargarNivel();
                esDescanso = false;
            } else {
                // Si eliminamos todas la bolas, ponemos al jugador la animación de ganar y
                // repintamos
                if (bolas.size() == 0) {
                    player.animacionGanar();
                    repintar();

                    // Dormimos el hilo 1 segundo
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Hacemos que el descanso sea true para poner la pantalla de carga
                    esDescanso = true;
                    // Aumentamos el nivel
                    nivel++;
                } else {
                    // Si la bolas no son 0 entonces movemos los sprites y comprobamos las
                    // colisiones
                    moverSprites();
                    comprobarColisiones();
                }
            }
            // Contamos el tiempo y se actualiza
            contarTiempo();
        } else if (tiempo.equals("000") && vidasActuales > 0) {
            // Si el tiempo llega a 0 y seguimos teniendo vidas, perdemos una vida y
            // recargamos el nivel
            perderVida();
            respawnear();
        } else {
            // Si no tenemos más vidas cambiamos a la pantalla game over
            panelJuego.cambiarPantalla(new PantallaPerder(panelJuego, puntos));
        }

    }

    /**
     * Método que fuerza el refresco de nuestro panel de juego
     */
    private void repintar() {
        panelJuego.repaint();
        Toolkit.getDefaultToolkit().sync();
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
     * Método que se encarga de hacer la operacion para contar el tiempo y depues lo
     * actualiza
     */
    private void contarTiempo() {
        tiempoTranscurrido = ((System.nanoTime() - tiempoOriginal));
        actualizarTiempo();
    }

    @Override
    public void pulsarRaton(MouseEvent e) {
    }

    /**
     * Método que redimensiona el fondo al tamaño del panelJuego, pero solo la parte
     * jugable.
     */
    public void redimensionarFondo() {
        img = fondo.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight() - 100, Image.SCALE_SMOOTH);
    }

    /**
     * Método para mover los distintos sprites de nuestro juego
     */
    public void moverSprites() {
        // Movemos las bolas
        for (int i = 0; i < bolas.size(); i++) {
            bolas.get(i).mover(panelJuego.getWidth(), panelJuego.getHeight());
        }

        // Movemos el arpon si es distinto de null
        if (arpon != null) {
            arpon.disparar();
        }

        // Si moverSpriteDerecha es true movemos el personaje a la derecha
        if (moverSpriteDerecha) {
            player.moverSpriteDerecha(panelJuego.getWidth() - 30);
        }

        // Si moverSpriteIzquierda es true movemos el personaje a la izquierda
        if (moverSpriteIzquierda) {
            player.moverSpriteIzquierda(30);
        }
    }

    /**
     * Método para comprobar las distintas colisiones entre los sprites de nuestro
     * juego
     */
    public void comprobarColisiones() {
        // Comprobamos colisiones
        for (int i = 0; i < bolas.size(); i++) {

            // Si hay 1 bloque o más comprobamos la colision de las bolas con los bloques
            if (bloques.size() > 0) {
                for (int z = 0; z < bloques.size(); z++) {
                    bolas.get(i).colisionLadosUpDown(bloques.get(z));
                }
            }

            // Si el player es distinto de null, comprobamos la colision entre las bolas y
            // el personaje
            if (player != null) {
                if (bolas.get(i).colisionCuadradoCirculo(player)) {
                    if (vidasActuales > 0) {
                        // Si colisiona y tenemos vidas, perdemos una vida y revivimos
                        perderVida();
                        respawnear();
                    } else {
                        // Si colsiona pero no tenemos vidas, el personaje muestra la animación de morir
                        // y se repinta la pantalla, dormimos el hilo 3 segundos y se pone la pantalla
                        // de
                        // gamer over
                        player.animacionMorir();
                        repintar();
                        try {
                            Thread.sleep(3 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        panelJuego.cambiarPantalla(new PantallaPerder(panelJuego, puntos));
                    }
                }
            }

            // Si el arpon es distinto de null comprobamos colision
            if (arpon != null) {
                if (bolas.get(i).colisionCuadradoCirculo(arpon)) {
                    // Si las bolas colsionan con el arpon mostramos una animacion de explotar y
                    // eliminamos el arpon
                    bolas.get(i).explotar();
                    repintar();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    arpon = null;
                    // Si las bolas tienen un acho de más de 40, se dividen en dos nuevas bolas
                    if (bolas.get(i).getAncho() >= 40) {
                        bolas.add(new Sprite(colorBola, bolas.get(i).getAncho() / 2, bolas.get(i).getAlto() / 2,
                                bolas.get(i).getPosX() - 60, bolas.get(i).getPosY(), VELOCIDAD_BOLAS, -VELOCIDAD_BOLAS,
                                COLOR_EXP[aleBola]));
                        bolas.add(new Sprite(colorBola, bolas.get(i).getAncho() / 2, bolas.get(i).getAlto() / 2,
                                bolas.get(i).getPosX() + 60, bolas.get(i).getPosY(), VELOCIDAD_BOLAS, VELOCIDAD_BOLAS,
                                COLOR_EXP[aleBola]));
                    }
                    // Actualizamos los puntos segun el ancho de la bola y la eliminamos
                    actualizarPuntos(bolas.get(i).getAncho());
                    bolas.remove(i);
                }
            }
        }

        // Si hay mas de un bloque y el arpon es distinto de null recorremos el bucle de
        // bloques para comprobar colsiones
        if (bloques.size() > 0 && arpon != null) {
            for (int j = 0; j < bloques.size(); j++) {
                // Si el arpon es distinto de null y hay colsion borramos el bloque que golpea
                // el arpon y eliminamos el arpon
                if (arpon != null) {
                    if (bloques.get(j).colision(arpon)) {
                        bloques.remove(j);
                        arpon = null;
                    }
                }
            }
        }
    }

    /**
     * Método que recoloca al personaje en la posicion incial y carga de nuevo el
     * nivel
     */
    private void respawnear() {
        // Poner al player en el centro
        player.playerDerecha();
        player.setPosX((panelJuego.getWidth() / 2) - (player.getAncho() / 2));
        recargarNivel();
    }

    /**
     * Método que recarga el mismo nivel cuando el personaje respawnea.
     */
    private void recargarNivel() {
        // Cogemos los colores que ya teniamos
        colorBloque = COLOR_BLOQUE[aleBloque];
        colorBola = COLOR_BOLA[aleBola];
        // Cargamos el fondo que teniamos
        cargarFondo(FONDOS[aleFondo]);
        // Eliminamos el arpon y las bolas y bloques que habia
        arpon = null;
        bolas.clear();
        bloques.clear();

        // Renovamos el tiempo
        renovarTiempo();

        // Cargamos el nivel de nuevo
        cargarNivelNuevo();
    }

    /**
     * Método para renovar el tiempo a 100 de nuevo y empezar una nueva cuenta atras
     */
    private void renovarTiempo() {
        tiempo = MAX_TIEMPO;
        tiempoOriginal = System.nanoTime();
    }

    /**
     * Método para cargar un nivel aleatorio
     */
    private void cargarNivel() {
        // Sacamos el color de bola y bloque aleatoriamente
        aleBola = aleatorio(0, COLOR_BOLA.length);
        aleBloque = aleatorio(0, COLOR_BLOQUE.length);
        // Scamos un fondo aleatorio
        aleFondo = aleatorio(0, FONDOS.length);
        // Scamos el número de bolas y bloques aleatoriamente
        numBolas = aleatorio(MIN_BOLAS, MAX_BOLAS + 1);
        numBloques = aleatorio(MIN_BLOQUES, MAX_BLOQUES + 1);
        // Ponemos los colores a la variables
        colorBloque = COLOR_BLOQUE[aleBloque];
        colorBola = COLOR_BOLA[aleBola];
        // Cargamos el fondo
        cargarFondo(FONDOS[aleFondo]);
        // Reseteamos el arpon y los arrays de bloques y bolas
        arpon = null;
        bolas.clear();
        bloques.clear();
        // Colocamos al personaje
        player.playerDerecha();
        player.setPosX((panelJuego.getWidth() / 2) - (player.getAncho() / 2));

        // Renovamos el tiempo
        renovarTiempo();

        // Si el nivel es 1, cargamos el primer nivel, sino vamos creando niveleres
        // aleatorios
        if (nivel == 1) {
            cargarNivelUno();
        } else {
            cargarNivelNuevo();
        }
    }

    /**
     * Método predefinido que carga 1 bola y dos bloques como nivel inicial, los
     * colores si son aletorios
     */
    private void cargarNivelUno() {
        numBolas = 1;
        numBloques = 2;
        bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS, VELOCIDAD_BOLAS,
                COLOR_EXP[aleBola]));
        bloques.add(new Sprite(colorBloque, ANCHO_BLOQUE, ALTO_BLOQUE, POSX_DOS, POSY_UNO_BLOQUES));
        bloques.add(new Sprite(colorBloque, ANCHO_BLOQUE, ALTO_BLOQUE, POSX_TRES, POSY_UNO_BLOQUES));
    }

    /**
     * Método para cargar el fondo de nuestro juego
     * 
     * @param ruta ruta del fondo
     */
    private void cargarFondo(String ruta) {
        try {
            fondo = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que suma puntos dependiendo del ancho de la bola
     * 
     * @param ancho ancho de la bola con la que colisionamos
     */
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

    /**
     * Método que realiza acciones cuando pulsamos el teclado
     */
    @Override
    public void pulsarTeclado(KeyEvent e) {
        // Si el personaje es disntinto de null y no es descanso, dependiendo de la
        // tecla que pulsemos hacemos una accion o otra
        if (player != null && !esDescanso) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                // Si el usuario pincha en la tecla a ponemos la variable moverIzquierda a true
                moverSpriteIzquierda = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_D) {
                // Si el usuario pincha en la tecla d ponemos la variable moverDerecha a true
                moverSpriteDerecha = true;
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                if (arpon == null) {
                    // Si el arpon es null cuando el usario pulsa espacio nuestro personaje dispara
                    // un nuevo arpon
                    player.animacionDisparar();
                    arpon = new Sprite(VELOCIDADY_ARPON, "Imagenes/disparo.png", ANCHO_ARPON, ALTO_ARPON,
                            player.getPosX() + (player.getAncho() / 2 - ANCHO_ARPON / 2), player.getPosY() + 40);
                }
            }
        }
    }

    /**
     * Método para cargar las bolas y bloques de un nuevo nivel
     */
    public void cargarNivelNuevo() {
        cargarBolas();
        cargarBloques();
    }

    /**
     * Método que realiza distintas acciones al soltar el teclado
     */
    @Override
    public void soltarTeclado(KeyEvent e) {
        if (player != null) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                // Si el player es distinto de null y sulta la tecla a, ponemos la varaible
                // moverIzquierda a false
                moverSpriteIzquierda = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_D) {
                // Si el player es distinto de null y sulta la tecla d, ponemos la varaible
                // moverDerehca a false
                moverSpriteDerecha = false;
            }
        }
    }

    /**
     * Método que comprueba si las vidas son mayor que 0 y si son mayor resta una
     * vida y pone la animación de morir al personaje, durante 3 seugndos.
     */
    public void perderVida() {
        if (vidasActuales >= 0) {
            vidasActuales--;
            player.animacionMorir();

            repintar();
        }

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que devuelde un número aleatorio entre dos valores que recibe por
     * parametro
     * 
     * @param min valor minimo
     * @param max valor maximo
     * @return número aletorio entre el min y el máx
     */
    public int aleatorio(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * Método para cargar los bloques, vaciamos el array de posicionesX anterior y
     * lo llenamos por defecto, lo mismo con el array de posciones Y, despues vamos
     * sacando posciones aleatorias de estos arrays para ponersalas a los bloques y
     * borramos esa posicion del array para que nose repita
     */
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

    /**
     * Método que dependiendo del número de bolas, crea las bolas con disntinto
     * color y disinta posicion, que hemos predefinido nosotros
     */
    public void cargarBolas() {
        switch (numBolas) {
            case 1:
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS, COLOR_EXP[aleBola]));
                break;
            case 2:
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_DOS, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS, COLOR_EXP[aleBola]));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS, COLOR_EXP[aleBola]));
                break;
            case 3:
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_UNO, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS, COLOR_EXP[aleBola]));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_DOS, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS, COLOR_EXP[aleBola]));
                bolas.add(new Sprite(colorBola, LADO_BOLA, LADO_BOLA, POSX_TRES, POSY_BOLAS, VELOCIDAD_BOLAS,
                        VELOCIDAD_BOLAS, COLOR_EXP[aleBola]));
                break;
        }

    }

    /**
     * Método para llenar el array de posicionesX por defecto
     */
    public void cargarArrayPosicioneX() {
        posicionesBloquesX.add(POSX_UNO);
        posicionesBloquesX.add(POSX_DOS);
        posicionesBloquesX.add(POSX_TRES);
        posicionesBloquesX.add(POSX_CUATRO);
    }

    /**
     * Método para llenar el array de posicionesY por defecto
     */
    public void cargarArrayPosicionesY() {
        for (int i = 0; i < 3; i++) {
            posicionesBloquesY.add(POSY_UNO_BLOQUES);
        }
        posicionesBloquesY.add(POSY_DOS_BLOQUES);
    }
}
