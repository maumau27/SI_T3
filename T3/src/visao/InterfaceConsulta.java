package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfaceConsulta {

	private JFrame menu;
	private JPanel cabecalho , corpo1 , corpo2;
	private JTextField campoPasta;
	private JButton listar , sair;
	
	
	public InterfaceConsulta() {
		menu = new JFrame();
		menu.setLayout(new BorderLayout());
		menu.setSize(600,600);
		setConfigurationsOfPanels();
		menu.add(corpo2 , BorderLayout.SOUTH);
		corpo2.setLayout(new BoxLayout(corpo2, BoxLayout.Y_AXIS));
		addText();
		setButtons();
		
		
		
	}
	public void setConfigurationsOfPanels() {
		cabecalho = new JPanel();  
		cabecalho.setLayout(new BorderLayout());
		cabecalho.setPreferredSize(new Dimension(100,100));
	    corpo1 = new JPanel();
	    corpo1.setLayout(new BorderLayout());
	    corpo1.setPreferredSize(new Dimension(200,200));
	    corpo2 = new JPanel();
	    corpo2.setLayout(new BorderLayout());
	    corpo2.setPreferredSize(new Dimension(200,200));
	    
	}
	
	public void addCabecalho(JPanel cabecalho) {
		this.cabecalho = cabecalho;
		menu.add(cabecalho, BorderLayout.NORTH);
	}
	
	public void addCorpo1(JPanel corpo1) {
		this.corpo1 = corpo1;
		menu.add(corpo1 , BorderLayout.CENTER);
	}
	
	public void addText() {
		campoPasta = new JTextField();
		campoPasta.setDocument(new TamanhoFixoJText(255));
		corpo2.add(campoPasta , BorderLayout.NORTH);
	}
	

	public void setButtons() {
		listar = new JButton("Listar");
		sair = new JButton("Voltar parao Menu");
		listar.setPreferredSize(new Dimension(50,50));
		sair.setPreferredSize(new Dimension(50,50));
		corpo2.add(listar);
		corpo2.add(sair);
		
	}
	
	public JFrame getMenu() {
		return menu;
	}
	public void setMenu(JFrame menu) {
		this.menu = menu;
	}
	public JButton getListar() {
		return listar;
	}
	public void setListar(JButton listar) {
		this.listar = listar;
	}
	public JButton getSair() {
		return sair;
	}
	public void setSair(JButton sair) {
		this.sair = sair;
	}
	public void setVisible() {
		menu.setVisible(true);
	}
}
