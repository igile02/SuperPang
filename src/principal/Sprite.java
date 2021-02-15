package principal;

import java.awt.image.BufferedImage;
import java.awt.Color;
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

    private BufferedImage buffer;
    private Color color;
    // Dimensión:
    private int ancho;
    private int alto;
    // Colocación:
    private int posX;
    private int posY;
    // Velocidades:
    private int velX;
    private int velY;

    private int contX = 1;
    private int contY = 1;

    public Sprite(Color color, int ancho, int alto, int posX, int posY, int velY) {
        this.color = color;
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        this.velY = velY;
        inicializarBuffer();
    }

    public Sprite(String rutaImagen, int ancho, int alto, int posX, int posY) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        do {
            velX = aleatorio();
            velY = aleatorio();
        } while (velX == 0 || velY == 0);
        inicializarBuffer(rutaImagen);
    }

    public Sprite(String rutaImagen, int ancho, int alto, int posX, int posY, int velX) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        this.velX = velX;
        inicializarBuffer(rutaImagen);
    }

    public Sprite(String rutaImagen, int ancho, int alto, int posX, int posY, int velY, int velX) {
        this.ancho = ancho;
        this.alto = alto;
        this.posX = posX;
        this.posY = posY;
        this.velY = velY;
        this.velX = velX;
        inicializarBuffer(rutaImagen);
    }

    /**
     * Crea un buffer vacio del color del Sprite.
     */
    private void inicializarBuffer() {
        buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, ancho, alto);
    }

    /**
     * Crea un buffer vacio del color del Sprite por parámetros.
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
     * Método para estampar los cuadrados en el gráfico
     * 
     * @param g graphics
     */
    public void estanpar(Graphics g) {
        g.drawImage(buffer, posX, posY, null);
    }

    public void disparar() {
        alto += velY;
        posY -= velY;

        BufferedImage imagen = null;
        buffer = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        try {
            if (alto > 100) {
                imagen = ImageIO.read(new File("Imagenes/disparo02.png"));
            } else {
                imagen = ImageIO.read(new File("Imagenes/disparo.png"));
            }

            if (alto > 200) {
                imagen = ImageIO.read(new File("Imagenes/disparo03.png"));
            }

            if (alto > 300) {
                imagen = ImageIO.read(new File("Imagenes/disparo04.png"));
            }

            if (alto > 400) {
                imagen = ImageIO.read(new File("Imagenes/disparo05.png"));
            }

            if (alto > 500) {
                imagen = ImageIO.read(new File("Imagenes/disparo06.png"));
            }

            if (alto > 600) {
                imagen = ImageIO.read(new File("Imagenes/disparo07.png"));
            }

            Graphics g = buffer.getGraphics();
            g.drawImage(imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void animacionDisparar() {
        BufferedImage imagen = null;
        buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
        try {
            imagen = ImageIO.read(new File("Imagenes/playerDisparando.png"));
            Graphics g = buffer.getGraphics();
            g.drawImage(imagen.getScaledInstance(this.ancho, this.alto, Image.SCALE_SMOOTH), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para mover el sprite
     */
    public void mover(int ancho, int alto) {
        // Mover el cuadrado
        posX = posX + velX;
        posY = posY + velY;

        // Comprobar si esta en el borde
        if ((posX + this.ancho) >= ancho - 30) {
            velX = -Math.abs(velX); // Forzar velocidad negativa
        }
        // Por la izquierda
        if (posX < 30) {
            velX = Math.abs(velX); // Forzar velocidad positiva
        }

        // Comprobar si esta en el borde
        if ((posY + this.alto) >= alto - 125) {
            velY = -Math.abs(velY); // Forzar velocidad negativa
        }
        // Por arriba
        if (posY < 20) {
            velY = Math.abs(velY); // Forzar velocidad positiva
        }
    }

    public void moverSpriteDerecha(int ancho) {

        BufferedImage imagen = null;
        buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
        try {
            if (contX % 2 == 0) {
                imagen = ImageIO.read(new File("Imagenes/playerDerecha.png"));
            } else {
                imagen = ImageIO.read(new File("Imagenes/playerDerecha01.png"));
            }
            Graphics g = buffer.getGraphics();
            g.drawImage(imagen.getScaledInstance(this.ancho, this.alto, Image.SCALE_SMOOTH), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mover el cuadrado
        posX = posX + velX;

        // Comprobar si esta en el borde
        if ((posX + this.ancho) >= ancho) {
            posX = ancho - this.ancho; // Forzar velocidad negativa
        }

        contX++;
    }

    public void moverSpriteIzquierda(int ancho) {

        BufferedImage imagen = null;
        buffer = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
        try {
            if (contY % 2 == 0) {
                imagen = ImageIO.read(new File("Imagenes/playerIzquierda.png"));
            } else {
                imagen = ImageIO.read(new File("Imagenes/playerIzquierda01.png"));
            }
            Graphics g = buffer.getGraphics();
            g.drawImage(imagen.getScaledInstance(this.ancho, this.alto, Image.SCALE_SMOOTH), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Mover el cuadrado
        posX = posX - velX;

        // Por la izquierda
        if (posX < 30) {
            posX = 30; // Forzar velocidad positiva
        }

        contY++;
    }

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

    public void colisionPorArribaAbajo(Sprite cuadrado) {
        int yCuadrado = cuadrado.getPosY();

        int cuadradoAlto = yCuadrado + cuadrado.getAlto();
        int xCuadrado = cuadrado.getPosX();
        int cuadradoAncho = xCuadrado + cuadrado.getAncho();

        if ((((posY + alto) > yCuadrado) && (posY < cuadradoAlto))
                && ((posX > xCuadrado) && (((posX + ancho) < cuadradoAncho) || (posX < cuadradoAncho)))) {
            velY = -Math.abs(velY);
        }

        if ((((posY) < cuadradoAlto) && (posY > yCuadrado))
                && ((posX > xCuadrado) && (((posX + ancho) < cuadradoAncho) || ((posX) < cuadradoAncho)))) {
            velY = Math.abs(velY);
            velX = velX*-1;
        }
    }

    public void colisionPorLados(Sprite cuadrado) {
        int yCuadrado = cuadrado.getPosY();

        int cuadradoAlto = yCuadrado + cuadrado.getAlto();
        int xCuadrado = cuadrado.getPosX();
        int cuadradoAncho = xCuadrado + cuadrado.getAncho();

        if (((posX < cuadradoAncho) && (posX > xCuadrado))
                && ((posY < cuadradoAlto) && (((posY + alto) > yCuadrado) || (posY) > yCuadrado))) {
            velX = Math.abs(velX);
        }

        if ((((posX + ancho) > xCuadrado) && (posX < xCuadrado))
                && ((posY < cuadradoAlto) && (((posY + alto) > yCuadrado) || (posY) > yCuadrado))) {
            velX = -Math.abs(velX);
        }
    }

    public int aleatorio() {
        return (int) (Math.random() * (5 - -5 + 1) + -5);
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
