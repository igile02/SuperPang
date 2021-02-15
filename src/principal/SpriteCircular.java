package principal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Graphics;
/*
public class SpriteCircular extends Sprite {

    private int radio;
    private BufferedImage buffer;
    private Image asteroide;

    
    public SpriteCircular(int ancho, int alto, int posX, int posY) {
        super(ancho, alto, posX, posY);
        inicializarBuffer();
    }

    @Override
    protected void inicializarBuffer() {

        buffer = null;

        try {
            buffer = ImageIO.read(new File("Imagenes/asteroide.png"));
            asteroide = buffer.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            Graphics graphics = buffer.getGraphics();
            graphics.drawImage(asteroide, super.getPosX(), super.getPosY(), null);
            graphics.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void estanpar(Graphics g){
        g.drawImage(asteroide, super.getPosX(), super.getPosY(), null);
    }

    public boolean colisionCirular(SpriteCircular circulo) {
        int radioCirculo = circulo.getAncho() / 2;
        radio = super.getAncho() / 2;

        int c1_x = super.getPosX() + radio;
        int c1_y = super.getPosY() + radio;

        int c2_x = circulo.getPosX() + radioCirculo;
        int c2_y = circulo.getPosY() + radioCirculo;

        double distancia = Math.hypot(c1_x - c2_x, c1_y - c2_y);

        if (distancia > radio + radioCirculo) {
            return false;
        } else {
            return true;
        }
    }
}
*/