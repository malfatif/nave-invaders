package br.com.jogo;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Nave {
    
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int altura;
    private int largura;
    private Image imagem;
    private List<Missel> misseis;
    private boolean isVisivel;

    public Nave() {
        ImageIcon referencia = new ImageIcon("resource\\nave.png");
        setImagem(referencia.getImage());
        setAltura(getImagem().getHeight(null));
        setLargura(getImagem().getWidth(null));
        this.x = 100;
        this.x = 100;
        this.misseis = new ArrayList<>();
    }

    public void atira() {
        this.misseis.add(new Missel(this.largura + getX(), this.altura / 2 + getY()));
    }

    public void mexer() {
        if ((this.y >= -2) && (this.y <= 506)) {
            this.y += this.dy;
        } else if (this.y > 0) {
            this.y += -1;
        } else {
            this.y += 1;
        }
        if ((this.x <= 729) && (this.x >= -9)) {
            this.x += this.dx;
        } else if (this.x > 0) {
            this.x += -1;
        } else {
            this.x += 1;
        }
    }

    public void keyPressed(KeyEvent tecla) {
        if ((tecla.getKeyCode() == 38) || (tecla.getKeyCode() == 87)) {
            this.dy = -2;
        } else if ((tecla.getKeyCode() == 40) || (tecla.getKeyCode() == 83)) {
            this.dy = 2;
        } else if ((tecla.getKeyCode() == 37) || (tecla.getKeyCode() == 65)) {
            this.dx = -2;
        } else if ((tecla.getKeyCode() == 39) || (tecla.getKeyCode() == 68)) {
            this.dx = 2;
        } else if (tecla.getKeyCode() == 32) {
            atira();
        }
    }

    public void keyReleased(KeyEvent tecla) {
        if ((tecla.getKeyCode() == 38) || (tecla.getKeyCode() == 87)) {
            this.dy = 0;
        } else if ((tecla.getKeyCode() == 40) || (tecla.getKeyCode() == 83)) {
            this.dy = 0;
        } else if ((tecla.getKeyCode() == 37) || (tecla.getKeyCode() == 65)) {
            this.dx = 0;
        } else if ((tecla.getKeyCode() == 39) || (tecla.getKeyCode() == 68)) {
            this.dx = 0;
        }
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

    public Image getImagem() {
        return this.imagem;
    }

    public void setImagem(Image imagem) {
        this.imagem = imagem;
    }

    public List<Missel> getMisseis() {
        return this.misseis;
    }

    public void setMisseis(List<Missel> misseis) {
        this.misseis = misseis;
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

    public boolean isVisivel() {
        return this.isVisivel;
    }

    public void setVisivel(boolean isVisivel) {
        this.isVisivel = isVisivel;
    }
}
