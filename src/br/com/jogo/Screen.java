package br.com.jogo;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

public class Screen extends JFrame {

	private static final long serialVersionUID = -1026074368249935922L;

	public Screen() {
		JMenuBar barraMenu = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem sobre = new JMenuItem("Sobre");
		sobre.addActionListener((ActionEvent e) -> {
			JOptionPane.showMessageDialog(null, "Jogo Desenvolvido Por Felipe Malfatti!", "Informações", 1);
		});
		JMenuItem sair = new JMenuItem("Sair");
		sair.addActionListener((ActionEvent e) -> {
			System.exit(0);
		});
		menu.add(sair);
		menu.add(new JSeparator());
		menu.add(sobre);
		barraMenu.add(menu);
		setJMenuBar(barraMenu);

		add(new Fase());
		setTitle("Nave Invaders");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 800);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Screen();
	}
}
