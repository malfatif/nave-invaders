package br.com.jogo;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Missel {
    
    private Image imagem;
    private int x;
    private int y;
    private int altura;
    private int largura;
    private boolean isVisible;

    public Missel(int x, int y) {
        this.x = x;
        this.y = y;
        setImagem(new ImageIcon("resource\\missel.png").getImage());
        setLargura(getImagem().getWidth(null));
        setAltura(getImagem().getHeight(null));
        setVisible(true);
    }

    public void mover() {
        this.x += 2;
        if (this.x > 850) {
            setVisible(false);
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
