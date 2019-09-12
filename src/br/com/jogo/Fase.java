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
	private static final int QUANTIDADE_INIMIGOS = 21;

	private Image fundo;
	private Nave nave;
	private Timer timer;
	private boolean emJogo;
	private List<Inimigo> inimigos;
	private boolean bossDerrotado;

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
		inicializarInimigos();
	}

	public void inicializarInimigos() {
		this.inimigos = new ArrayList<>();

		for (int i = 0; i < QUANTIDADE_INIMIGOS; i++) {
			this.inimigos.add(new Inimigo());
		}
	}

	public void paint(Graphics g) {
		Graphics2D graficos = (Graphics2D) g;
		graficos.drawImage(getFundo(), 0, 0, null);
		if (gameOver()) {
			ImageIcon fimJogo = new ImageIcon("resource//gameOver01.jpg");
			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
			return;
		}

		Nave nave = getNave();
		graficos.drawImage(nave.getImagem(), nave.getX(), nave.getY(), null);

		List<Missel> misseis = this.nave.getMisseis();
		for (int i = 0; i < misseis.size(); i++) {
			Missel missel = (Missel) misseis.get(i);
			graficos.drawImage(missel.getImagem(), missel.getX(), missel.getY(), this);
		}
		for (int i = 0; i < this.inimigos.size(); i++) {
			Inimigo inimigo = (Inimigo) this.inimigos.get(i);
			graficos.drawImage(inimigo.getImagem(), inimigo.getX(), inimigo.getY(), this);
		}
		graficos.setColor(Color.ORANGE);
		graficos.drawString("Inimigos:" + this.inimigos.size(), 5, 15);
		graficos.drawString("Chef�o: 1", 5, 30);
		graficos.setColor(Color.black);
		g.dispose();
	}

	private boolean gameOver() {
		return !this.emJogo;
	}

	public void criarChefao() {
		inimigos.add(new Inimigo(true));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.inimigos.size() == 0) {
			if (!bossDerrotado) {
				criarChefao();
			} else {
				this.emJogo = false;
			}
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
				if (inimigo.isBoss()) {
					bossDerrotado = true;
				}
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

			Inimigo inimigo = this.inimigos.get(i);
			Rectangle formaInimigo = inimigo.getBounds();

			if (formaNave.intersects(formaInimigo)) {
				this.nave.setVisivel(false);
				inimigo.setVisible(false);
				this.emJogo = false;
			}
		}

		List<Missel> misseisDisparados = this.nave.getMisseis();
		for (int i = 0; i < misseisDisparados.size(); i++) {
			final Missel missel = misseisDisparados.get(i);

			inimigos.stream().filter(inimigo -> {
				return missel.getBounds().intersects(inimigo.getBounds());
			}).forEach(inimigo -> {
				inimigo.diminuirVidas();
				missel.setVisible(false);

				if (inimigo.morto()) {
					inimigo.setVisible(false);
				}
			});
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
				inicializarInimigos();
			}
			getNave().keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			Fase.this.getNave().keyReleased(e);
		}
	}
}
