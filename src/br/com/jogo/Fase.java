package br.com.jogo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {

	private static final long serialVersionUID = 198639265218206826L;
	private static final int QUANTIDADE_INIMIGOS = 50;
	
	private Image fundo;
	private Nave nave;
	private Timer timer;
	private boolean emJogo;
	private List<Inimigo> inimigos;

	public Fase() {
		setFocusable(true);
		setDoubleBuffered(true);
		addKeyListener(new TecladoAdapter());
		ImageIcon referencia = new ImageIcon("resource//fundo.png");
		setFundo(referencia.getImage());
		setNave(new Nave());
		setTimer(new Timer(5, this));
		getTimer().start();
		this.emJogo = true;
		inicializaInimigos();
	}

	public void inicializaInimigos() {
		this.inimigos = new ArrayList<>();

		for (int i = 0; i < QUANTIDADE_INIMIGOS; i++) {
			this.inimigos.add(new Inimigo());
		}
	}

	public void paint(Graphics g) {
		Graphics2D graficos = (Graphics2D) g;
		graficos.drawImage(getFundo(), 0, 0, null);
		if (gameOver()) {
			ImageIcon fimJogo = new ImageIcon("resource\\gameOver01.jpg");
			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
			return;
		}

		Nave nave = getNave();
		graficos.drawImage(nave.getImagem(), nave.getX(), nave.getY(), null);

		List<Missel> misseis = this.nave.getMisseis();
		for (int i = 0; i < misseis.size(); i++) {
			Missel oMissel = (Missel) misseis.get(i);
			graficos.drawImage(oMissel.getImagem(), oMissel.getX(), oMissel.getY(), this);
		}
		for (int i = 0; i < this.inimigos.size(); i++) {
			Inimigo oInimigo = (Inimigo) this.inimigos.get(i);
			graficos.drawImage(oInimigo.getImagem(), oInimigo.getX(), oInimigo.getY(), this);
		}
		graficos.setColor(Color.ORANGE);
		graficos.drawString("Inimigos:" + this.inimigos.size(), 5, 15);
		graficos.setColor(Color.black);
		g.dispose();
	}

	private boolean gameOver() {
		return !this.emJogo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.inimigos.size() == 0) {
			this.emJogo = false;
		}
		List<Missel> misseis = this.nave.getMisseis();

		for (int i = 0; i < misseis.size(); i++) {
			Missel missel = (Missel) misseis.get(i);
			if (missel.isVisible()) {
				missel.mover();
			} else {
				misseis.remove(i);
			}
		}

		for (int i = 0; i < this.inimigos.size(); i++) {
			Inimigo inimigo = (Inimigo) this.inimigos.get(i);
			if (inimigo.isVisible()) {
				inimigo.mover();
			} else {
				this.inimigos.remove(i);
			}
		}
		getNave().mover();
		checarColisao();
		repaint();
	}

	public void checarColisao() {
		Rectangle formaNave = this.nave.getBounds();
		for (int i = 0; i < this.inimigos.size(); i++) {

			Inimigo tempInimigo = this.inimigos.get(i);
			Rectangle formaInimigo = tempInimigo.getBounds();

			if (formaNave.intersects(formaInimigo)) {
				this.nave.setVisivel(false);
				tempInimigo.setVisible(false);
				this.emJogo = false;
			}
		}

		List<Missel> misseisDisparados = this.nave.getMisseis();
		for (int i = 0; i < misseisDisparados.size(); i++) {
			for (int b = 0; b < this.inimigos.size(); b++) {
				Inimigo inimigo = this.inimigos.get(b);
				Rectangle formaInimigo = inimigo.getBounds();
				Missel missel = misseisDisparados.get(i);

				if (missel.getBounds().intersects(formaInimigo)) {
					inimigo.setVisible(false);
					misseisDisparados.get(i).setVisible(false);
				}
			}
		}
	}

	public Image getFundo() {
		return this.fundo;
	}

	public void setFundo(Image fundo) {
		this.fundo = fundo;
	}

	public Nave getNave() {
		return this.nave;
	}

	public void setNave(Nave nave) {
		this.nave = nave;
	}

	public Timer getTimer() {
		return this.timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	private class TecladoAdapter extends KeyAdapter {
		private TecladoAdapter() {
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 10) {
				emJogo = true;
				nave = new Nave();
				inicializaInimigos();
			}
			getNave().keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			Fase.this.getNave().keyReleased(e);
		}
	}
}
