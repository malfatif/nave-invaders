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

    private Image fundo;
    private Nave nave;
    private Timer timer;
    private boolean emJogo;

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

    private int[][] coordenadas = { { 2000, 128 }, { 900, 10 }, { 1500, 70 }, { 2000, 20 }, { 4000, 20 }, { 1500, 200 }, { 3000, 30 }, { 900, 150 }, { 1000, 100 }, { 1000, 145 }, { 1000, 100 }, { 1000, 100 } };
    private List<Inimigo> inimigos;

    public void preencheCoordenadas() {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                this.coordenadas[x][y] = (x + 1000 + (y + 1000));
            }
        }
    }

    public void inicializaInimigos() {
        this.inimigos = new ArrayList<>();
        for (int i = 0; i < this.coordenadas.length; i++) {
            this.inimigos.add(new Inimigo(this.coordenadas[i][0], this.coordenadas[i][1]));
        }
    }

    public void paint(Graphics g) {
        Graphics2D graficos = (Graphics2D) g;
        graficos.drawImage(getFundo(), 0, 0, null);
        if (this.emJogo) {
            graficos.drawImage(getNave().getImagem(), getNave().getX(), getNave().getY(), null);
            List<Missel> misseis = this.nave.getMisseis();
            for (int i = 0; i < misseis.size(); i++) {
                Missel oMissel = (Missel) misseis.get(i);
                graficos.drawImage(oMissel.getImagem(), oMissel.getX(), oMissel.getY(), this);
            }
            for (int i = 0; i < this.inimigos.size(); i++) {
                Inimigo oInimigo = (Inimigo) this.inimigos.get(i);
                graficos.drawImage(oInimigo.getImagem(), oInimigo.getX(), oInimigo.getY(), this);
            }
            graficos.setColor(Color.WHITE);
            graficos.drawString("Inimigos:" + this.inimigos.size(), 5, 15);
        } else {
            ImageIcon fimJogo = new ImageIcon("resource\\newGameOver.jpg");
            graficos.drawImage(fimJogo.getImage(), 0, 0, null);
        }
        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        if (this.inimigos.size() == 0) {
            this.emJogo = false;
        }
        List<Missel> misseis = this.nave.getMisseis();
        for (int i = 0; i < misseis.size(); i++) {
            Missel oMissel = (Missel) misseis.get(i);
            if (oMissel.isVisible()) {
                oMissel.Mexer();
            } else {
                misseis.remove(i);
            }
        }
        for (int i = 0; i < this.inimigos.size(); i++) {
            Inimigo oInimigo = (Inimigo) this.inimigos.get(i);
            if (oInimigo.isVisible()) {
                oInimigo.Mexer();
            } else {
                this.inimigos.remove(i);
            }
        }
        getNave().mexer();
        checarColisao();
        repaint();
    }

    public void checarColisao() {
        Rectangle formaNave = this.nave.getBounds();
        for (int i = 0; i < this.inimigos.size(); i++) {
            Inimigo tempInimigo = (Inimigo) this.inimigos.get(i);
            Rectangle formaInimigo = tempInimigo.getBounds();
            if (formaNave.intersects(formaInimigo)) {
                this.nave.setVisivel(false);
                tempInimigo.setVisible(false);
                this.emJogo = false;
            }
        }
        List<Missel> misseis = this.nave.getMisseis();
        for (int i = 0; i < misseis.size(); i++) {
            for (int b = 0; b < this.inimigos.size(); b++) {
                Inimigo tempInimigo = (Inimigo) this.inimigos.get(b);
                Rectangle formaInimigo = tempInimigo.getBounds();
                if (((Missel) misseis.get(i)).getBounds().intersects(formaInimigo)) {
                    tempInimigo.setVisible(false);
                    ((Missel) misseis.get(i)).setVisible(false);
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
                Fase.this.emJogo = true;
                Fase.this.nave = new Nave();
                Fase.this.inicializaInimigos();
            }
            Fase.this.getNave().keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            Fase.this.getNave().keyReleased(e);
        }
    }
}
