package principal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Clase Sprite
 * 
 * @author Iván Gil Esteban
 */
public class Sprite {

    // Imagenes que vamos a cargar
    private BufferedImage buffer;
    private BufferedImage disparando;
    private BufferedImage morir;
    private BufferedImage ganar;
    private BufferedImage derecha;
    private BufferedImage derechaDoS;
    private BufferedImage izquierda;
    private BufferedImage izquierdaDos;
    private BufferedImage explotar;
    private BufferedImage disparo;
    private BufferedImage disparo2;
    private BufferedImage disparo3;
    private BufferedImage disparo4;
    private BufferedImage disparo5;
    private BufferedImage disparo6;
    private BufferedImage disparo7;

    // Dimensión:
    private int ancho;
    private int alto;
    // Colocación:
    private int posX;
    private int posY;
    // Velocidades:
    private int velX;
    private int velY;
    // Contadores para hacer una especie de animación
    private int contX = 1;
    private int contY = 1;

    // Constructor de los bloques
    public Sprite(String rutaImagen, int ancho, int alto, int posX, int posY) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        inicializarBuffer(rutaImagen);
    }

    // Constructor del jugador
    public Sprite(String rutaImagen, int ancho, int alto, int posX, int posY, int velX) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        inicializarBuffer(rutaImagen);
        cargarSpritesPlayer();
    }

    // Constructor de las bolas
    public Sprite(String rutaImagen, int ancho, int alto, int posX, int posY, int velY, int velX, String rutaExplo) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        this.velY = velY;
        this.velX = velX;
        inicializarBuffer(rutaImagen);
        cargarSpriteExplosion(rutaExplo);
    }

    // Constructor del arpon
    public Sprite(int velY, String rutaImagen, int ancho, int alto, int posX, int posY) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        this.velY = velY;
        inicializarBuffer(rutaImagen);
        cargarSpritesArpon();
    }

    /**
     * Crea un buffer con una imagen que recibe por parametros
     * 
     * @param ruta ruta de la imagen que vamos a cargar
     */
    private void inicializarBuffer(String ruta) {
        BufferedImage imagen = null;
        buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        try {
            imagen = ImageIO.read(new File(ruta));
            Graphics g = buffer.getGraphics();
            g.drawImage(imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para estampar los sprites en el gráfico
     * 
     * @param g graphics
     */
    public void estanpar(Graphics g) {
        g.drawImage(buffer, posX, posY, null);
    }

    /**
     * Método para cargar las imagenes del arpon una sola vez y no tener que volver
     * a cargarlas si las queremos volver a usar
     */
    private void cargarSpritesArpon() {
        try {
            disparo = ImageIO.read(new File("Imagenes/disparo.png"));

            disparo2 = ImageIO.read(new File("Imagenes/disparo02.png"));

            disparo3 = ImageIO.read(new File("Imagenes/disparo03.png"));

            disparo4 = ImageIO.read(new File("Imagenes/disparo04.png"));

            disparo5 = ImageIO.read(new File("Imagenes/disparo05.png"));

            disparo6 = ImageIO.read(new File("Imagenes/disparo06.png"));

            disparo7 = ImageIO.read(new File("Imagenes/disparo07.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para cargar las imagenes de la explosion una sola vez y no tener que
     * volver a cargarlas si las queremos volver a usar
     */
    private void cargarSpriteExplosion(String ruta) {
        try {
            explotar = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para cargar las imagenes del player y no tener que volver a cargarlas
     * si las usamos de nuevo
     */
    private void cargarSpritesPlayer() {
        try {
            disparando = ImageIO.read(new File("Imagenes/playerDisparando.png"));
            derecha = ImageIO.read(new File("Imagenes/playerDerecha.png"));
            derechaDoS = ImageIO.read(new File("Imagenes/playerDerecha01.png"));
            izquierda = ImageIO.read(new File("Imagenes/playerIzquierda.png"));
            izquierdaDos = ImageIO.read(new File("Imagenes/playerIzquierda01.png"));
            morir = ImageIO.read(new File("Imagenes/playerMorir.png"));
            ganar = ImageIO.read(new File("Imagenes/playerGanar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que aumenta el tamño del arpon hasta llegar al borde de la pantalla y
     * segun sea su tamaño cambiamos el sprite para crear una especie de animación
     */
    public void disparar() {
        alto += velY;
        posY -= velY;

        if (alto > 100) {
            pintarAnimacion(disparo2);
        } else {
            pintarAnimacion(disparo);
        }

        if (alto > 200) {
            pintarAnimacion(disparo3);
        }

        if (alto > 300) {
            pintarAnimacion(disparo4);
        }

        if (alto > 400) {
            pintarAnimacion(disparo5);
        }

        if (alto > 500) {
            pintarAnimacion(disparo6);
        }

        if (alto > 600) {
            pintarAnimacion(disparo7);
        }
    }

    /**
     * Método para cambiar el sprite de nuestro player al disparar y simular una
     * animación, llamamos al metodo pintar animacion y le pasamos la imagen que
     * queremos poner
     */
    public void animacionDisparar() {
        pintarAnimacion(disparando);
    }

    /**
     * Método para cambiar el sprite de nuestro player al morir y simular una
     * animación, llamamos al metodo pintar animacion y le pasamos la imagen que
     * queremos poner
     */
    public void animacionMorir() {
        pintarAnimacion(morir);
    }

    /**
     * Método para cambiar el sprite de nuestro player cuando elimna todas las bolas
     * y simular una animación, llamamos al metodo pintar animacion y le pasamos la
     * imagen que queremos poner
     */
    public void animacionGanar() {
        pintarAnimacion(ganar);
    }

    /**
     * Método que cambiar el sprite de la bola por una explosión y así simulamos la
     * animación de la explosión de la bola
     */
    public void explotar() {
        pintarAnimacion(explotar);
    }

    /**
     * Método para poner la animacion del personaje por defecto, yo he elegido la
     * dercha por defecto
     */
    public void playerDerecha() {
        pintarAnimacion(derecha);
    }

    /**
     * Método que cambiara la imagen del buffer por una imagen que recibe por
     * parametros, y la ajusta al tamaño del sprite
     * 
     * @param image Imagen que queremos pintar y le pasamos por parametros
     */
    public void pintarAnimacion(Image image) {
        buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        g.drawImage(image.getScaledInstance(this.ancho, this.alto, Image.SCALE_SMOOTH), 0, 0, null);
    }

    /**
     * Método para mover las bolas de colores de nuestro juego
     * 
     * @param ancho ancho de nuestra pantalla
     * @param alto  alto de nuestra pantalla
     */
    public void mover(int ancho, int alto) {
        // Mover las bolas
        posX = posX + velX;
        posY = posY + velY;

        // Comprobar si esta en el borde con el ancho jugable de nuestra pantalla
        // Por la derecha
        if ((posX + this.ancho) >= ancho - 30) {
            velX = -Math.abs(velX); // Forzar velocidad negativa
        }
        // Por la izquierda
        if (posX < 30) {
            velX = Math.abs(velX); // Forzar velocidad positiva
        }

        // Comprobar si esta en el borde con el alto jugable de nuestra pantalla
        // Por abajo
        if ((posY + this.alto) >= alto - 125) {
            velY = -Math.abs(velY); // Forzar velocidad negativa
        }
        // Por arriba
        if (posY < 20) {
            velY = Math.abs(velY); // Forzar velocidad positiva
        }
    }

    /**
     * Método que cambia la imagen del sprite y lo mueve aumentando su posX para
     * simular una animación de moverse, la animación la hacemos con un contador,
     * dependiendo de si el contador es par o impar se cambia la imagen y simulamos
     * una animación
     * 
     * @param ancho ancho limite del panel jugable
     */
    public void moverSpriteDerecha(int ancho) {

        if (contX % 2 == 0) {
            pintarAnimacion(derecha);
        } else {
            pintarAnimacion(derechaDoS);
        }

        // Mover el cuadrado
        posX = posX + velX;

        // Comprobar si esta en el borde
        if ((posX + this.ancho) >= ancho) {
            posX = ancho - this.ancho; // Forzar velocidad negativa
        }

        contX++;
        contY++;
    }

    /**
     * Método que cambia la imagen del sprite y lo mueve disminuyendo su posX para
     * simular una animación de moverse, la animación la hacemos con un contador,
     * dependiendo de si el contador es par o impar se cambia la imagen y simulamos
     * una animación
     * 
     * @param ancho ancho limite del panel jugable
     */
    public void moverSpriteIzquierda(int ancho) {

        if (contY % 2 == 0) {
            pintarAnimacion(izquierda);
        } else {
            pintarAnimacion(izquierdaDos);
        }

        // Mover el cuadrado
        posX = posX - velX;

        // Por la izquierda
        if (posX < ancho) {
            posX = ancho; // Forzar velocidad positiva
        }

        contY++;
        contX++;
    }

    /**
     * Método para comprobar la colision entre un cuadrado y un circulo
     * 
     * @param cuadrado sprite circular que recibimos por parametros
     * @return true si hay colision y false en caso contrario
     */
    public boolean colisionCuadradoCirculo(Sprite cuadrado) {
        int cx;
        int cy;
        int r = getAncho() / 2;

        int w = cuadrado.getAncho();
        int h = cuadrado.getAlto();
        int x = cuadrado.getPosX();
        int y = cuadrado.getPosY();
        cx = getPosX() + r;
        cy = getPosY() + r;

        int px = cx; // En principio son iguales
        if (px < x)
            px = x;
        if (px > x + w)
            px = x + w;
        int py = cy;
        if (py < y)
            py = y;
        if (py > y + h)
            py = y + h;
        double distancia = Math.sqrt((cx - px) * (cx - px) + (cy - py) * (cy - py));
        if (distancia < r) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método para comprobar la colision entre dos sprites cuadrados
     * 
     * @param cuadrado sprite que le pasamos por parametro
     * @return true si hay colision false en caso contrario
     */
    public boolean colision(Sprite cuadrado) {
        int borde_der = 0;
        int borde_abaj = 0;
        boolean colision = false;
        if (posX < cuadrado.getPosX() && posY < cuadrado.getPosY()) {
            borde_der = posX + ancho;
            borde_abaj = posY + alto;
            if (borde_der >= cuadrado.posX && borde_abaj >= cuadrado.posY) {
                colision = true;
            }
        } else if (posX > cuadrado.getPosX() && posY > cuadrado.getPosY()) {
            borde_der = cuadrado.getPosX() + cuadrado.getAncho();
            borde_abaj = cuadrado.getPosY() + cuadrado.getAlto();
            if (borde_der >= posX && borde_abaj >= posY) {
                colision = true;
            }
        }

        return colision;
    }

    /**
     * Método para comprobar si las bolas colsionan por arriba y por abajo o por los
     * lados, para en caso de que haya colsion cambiar su velocidad y simular el
     * rebote de las pelotas en los bloques
     * 
     * @param cuadrado sprite del bloque con el que simulamos los rebotes
     */
    public void colisionLadosUpDown(Sprite cuadrado) {
        int yCuadrado = cuadrado.getPosY();
        int cuadradoAlto = yCuadrado + cuadrado.getAlto();
        int xCuadrado = cuadrado.getPosX();
        int cuadradoAncho = xCuadrado + cuadrado.getAncho();

        // Por arriba
        if ((((posY + alto) > yCuadrado) && (posY < cuadradoAlto))
                && (((posX + ancho) > xCuadrado) && ((posX < cuadradoAncho)))) {
            velY = -Math.abs(velY);
        }

        // Por abajo
        if ((((posY) < cuadradoAlto) && (posY > yCuadrado))
                && (((posX + ancho) > xCuadrado) && (posX < cuadradoAncho))) {
            velY = Math.abs(velY);
        }

        // Por la derecha
        if (((posX < cuadradoAncho) && (posX > cuadradoAncho - 10))
                && ((posY < cuadradoAlto) && (((posY + alto) > yCuadrado) || (posY) > yCuadrado))) {
            velX = Math.abs(velX);
        }

        // Por la izquierda
        if ((((posX + ancho) > xCuadrado) && ((posX + ancho) < xCuadrado + 10))
                && ((posY < cuadradoAlto) && (((posY + alto) > yCuadrado) || (posY) > yCuadrado))) {
            velX = -Math.abs(velX);
        }
    }

    public BufferedImage getBuffer() {
        return buffer;
    }

    public void setBuffer(BufferedImage buffer) {
        this.buffer = buffer;
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }
}
