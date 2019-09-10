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
	private int vidas;
	private boolean isVisible;
	private boolean isBoss;

	public Inimigo() {
		Random random = new Random();
		String imagemInimigo = "";
		int numAleatorio = random.nextInt(7);

		if (numAleatorio % 3 == 0) {
			imagemInimigo = "136.gif";
		} else if (numAleatorio % 5 == 0) {
			imagemInimigo = "95.gif";
		} else if (numAleatorio % 7 == 0) {
			imagemInimigo = "001.gif";
		} else {
			imagemInimigo = "97.gif";
		}
		setVidas(1);
		setX(random.nextInt(3000) + 1000);
		setY(random.nextInt(500));
		setImagem(new ImageIcon("resource\\" + imagemInimigo).getImage());
		setVisible(true);
		setLargura(this.imagem.getWidth(null));
		setAltura(this.imagem.getHeight(null));
	}

	public Inimigo(boolean isBoss) {
		this();
		setVidas(5);
		this.isBoss = isBoss;
		if (isBoss) {
			setImagem(new ImageIcon("resource\\alien.gif").getImage());
		}
	}

	public void mover() {
		if (getX() < 0) {
			this.x = 800;
		} else {
			this.x -= 1;
		}

//		if(isBoss) {
//			Random random = new Random();
//			int number = random.nextInt(3); // 0, 1, 2, 3
//			
//			if(number % 2 == 0) {
//				this.y -= 2;
//			}else {
//				this.y += 2;
//			}
//		}
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

	public boolean isBoss() {
		return isBoss;
	}

	public void setBoss(boolean isBoss) {
		this.isBoss = isBoss;
	}

	public int getVidas() {
		return vidas;
	}
	
	public boolean morto() {
		return vidas == 0;
	}
	
	public void setVidas(int vidas) {
		this.vidas = vidas;
	}
	
	public void diminuirVidas() {
		vidas--;
	}

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getAltura(), getLargura());
	}
}
