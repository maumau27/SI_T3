package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Point;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InterfaceMenu {

	JFrame menu;
	JPanel cabecalho;
	
	public JPanel getCabecalho() {
		return cabecalho;
	}

	public void setCabecalho(JPanel cabecalho) {
		this.cabecalho = cabecalho;
	}

	public JPanel getCorpo1() {
		return corpo1;
	}

	public JFrame getMenu() {
		return menu;
	}

	public void setMenu(JFrame menu) {
		this.menu = menu;
	}

	public void setCorpo1(JPanel corpo1) {
		this.corpo1 = corpo1;
	}

	JPanel corpo1;
	JPanel corpo2;
	JLabel login;
	JLabel grupo;
	JLabel nome;
	JLabel totalAcessos;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	
	public InterfaceMenu() {
		menu = new JFrame("Menu");
		menu.setLayout(new BorderLayout());
		menu.setSize(600,600);
		setConfigurationsOfPanels();
		fillCabecalho();
		fillAcessos();
		fillButtons();
		menu.add(cabecalho , BorderLayout.NORTH);
		menu.add(corpo1 , BorderLayout.CENTER);
		menu.add(corpo2 , BorderLayout.SOUTH);
		//addCabecalho();
		
	}
	
	public void setConfigurationsOfPanels() {
		cabecalho = new JPanel();  
		cabecalho.setLayout(new BorderLayout());
		cabecalho.setPreferredSize(new Dimension(100,100));
	    corpo1 = new JPanel();
	    corpo1.setLayout(new BorderLayout());
	    corpo1.setPreferredSize(new Dimension(200,200));
	    corpo2 = new JPanel();
	    corpo2.setLayout(new BoxLayout(corpo2, BoxLayout.Y_AXIS));
	    corpo2.setPreferredSize(new Dimension(600,300));
	}
	
	
	
	public void fillCabecalho() {
		login = new JLabel("Login : ");
		grupo = new JLabel("Grupo : ");
		nome  = new JLabel("Nome : ");
		login.setFont(new Font("Dialog", Font.BOLD, 15));
		grupo.setFont(new Font("Dialog", Font.BOLD, 15));
		nome.setFont(new Font("Dialog", Font.BOLD, 15));
		cabecalho.add(login , BorderLayout.NORTH);
		cabecalho.add(grupo , BorderLayout.CENTER);
		cabecalho.add(nome , BorderLayout.SOUTH);
	}
	
	public void fillAcessos() {
		totalAcessos = new JLabel("Total de acessos do usu�rio: ");
		totalAcessos.setFont(new Font("Dialog", Font.BOLD, 15));
		corpo1.add(totalAcessos , BorderLayout.CENTER);
	}
	
	public void fillButtons() {
		button1 = new JButton("Cadastrar um novo usu�rio");
		button2 = new JButton("Alterar senha pessoal e certificado digital do usu�rio");
		button3 = new JButton("Consultar pasta de arquivos secretos do usu�rio");
		button4 = new JButton("Sair do Sistema");
		corpo2.add(button1);
		corpo2.add(button2);
		corpo2.add(button3);
		corpo2.add(button4);
		button1.setPreferredSize(new Dimension(600,100));
		button2.setPreferredSize(new Dimension(600,100));
		button3.setPreferredSize(new Dimension(600,100));
		button4.setPreferredSize(new Dimension(600,100));
	}
	
	public JButton getButton1() {
		return button1;
	}

	public void setButton1(JButton button1) {
		this.button1 = button1;
	}

	public JButton getButton2() {
		return button2;
	}

	public void setButton2(JButton button2) {
		this.button2 = button2;
	}

	public JButton getButton3() {
		return button3;
	}

	public void setButton3(JButton button3) {
		this.button3 = button3;
	}

	public JButton getButton4() {
		return button4;
	}

	public void setButton4(JButton button4) {
		this.button4 = button4;
	}

	public void addCabecalho() {
		menu.getContentPane().add(BorderLayout.NORTH , cabecalho);
	}
	
	public void setVisible() {
		menu.setVisible(true);
	}
	
}