package br.com.jogo;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Inimigo {
    
    private Image imagem;
    private int x;
    private int y;
    private int altura;
    private int largura;
    private boolean isVisible;

    public Inimigo(int x, int y) {
        this.x = x;
        this.y = y;
        String sImagem = "resource\\";

        int numAleatorio = new Random().nextInt();
        numAleatorio /= 500;
        if (numAleatorio % 50 == 0) {
            sImagem = sImagem + "136.gif";
        } else if (numAleatorio % 7 == 0) {
            sImagem = sImagem + "95.gif";
        } else if (numAleatorio % 13 == 0) {
            sImagem = sImagem + "001.gif";
        } else {
            sImagem = sImagem + "97.gif";
        }
        setImagem(new ImageIcon(sImagem).getImage());
        setVisible(true);
        setLargura(this.imagem.getWidth(null));
        setAltura(this.imagem.getHeight(null));
    }

    public void Mexer() {
        if (getX() < 0) {
            this.x = 800;
        } else {
            this.x -= 1;
        }
    }

    public Image getImagem() {
        return this.imagem;
    }

    public void setImagem(Image imagem) {
        this.imagem = imagem;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public int getAltura() {
        return this.altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return this.largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getAltura(), getLargura());
    }
}
