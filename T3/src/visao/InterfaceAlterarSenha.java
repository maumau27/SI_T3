package visao;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfaceAlterarSenha {

	JPanel cabecalho;
	JFrame menu;
	JPanel corpo1;
	JTextField campoCertificado , password , repeatedPassword;
	JLabel label1 , label2 , label3;
	JPanel corpo2;
	JButton send;
	JButton voltarMenu;
	JPanel buttons;
	
	
	
	public InterfaceAlterarSenha() {
		menu = new JFrame("Alterar Senha");
		menu.setSize(600,600);
		corpo2 = new JPanel();
		menu.add(corpo2 , BorderLayout.SOUTH);
		corpo2.setLayout(new BoxLayout(corpo2, BoxLayout.Y_AXIS));
		AddTexts();
		addPanelButtomToCorpo2();
		setButtons();
	}
	
	
	public void addCabecalho(JPanel cabecalho) {
		this.cabecalho = cabecalho;
		cabecalho.setLayout(new BorderLayout());
		//cabecalho.setPreferredSize(new Dimension(100,100));
		menu.add(cabecalho , BorderLayout.NORTH);
	}
	
	public void addCorpo1(JPanel corpo1) {
		this.corpo1 = corpo1;
		corpo1.setLayout(new BorderLayout());
	    //corpo1.setPreferredSize(new Dimension(100,100));
		menu.add(corpo1 , BorderLayout.CENTER);
	}
	
	public void setLabels() {
		label1 = new JLabel("Certificate Path");
		label2 = new JLabel("Password");
		label3 = new JLabel("Repetead Password");
	}
	
	public void setTextCertificatePath() {
		campoCertificado = new JTextField();
		campoCertificado.setDocument(new TamanhoFixoJText(255));
	}
	
	public JFrame getMenu() {
		return menu;
	}


	public void setMenu(JFrame menu) {
		this.menu = menu;
	}


	public JButton getSend() {
		return send;
	}


	public void setSend(JButton send) {
		this.send = send;
	}


	public void setTextPassword() {
		password = new JTextField();
		password.setDocument(new TamanhoFixoJText(8));
	}
	public JButton getVoltarMenu() {
		return voltarMenu;
	}


	public void setVoltarMenu(JButton voltarMenu) {
		this.voltarMenu = voltarMenu;
	}


	public void setTextRepeteadPassword() {
		repeatedPassword = new JTextField();
		repeatedPassword.setDocument(new TamanhoFixoJText(8));
	}
	
	public void AddTexts() {
		setLabels();
		setTextCertificatePath();
		setTextPassword();
		setTextRepeteadPassword();
		corpo2.add(label1);
		corpo2.add(campoCertificado);
		corpo2.add(label2);
		corpo2.add(password);
		corpo2.add(label3);
		corpo2.add(repeatedPassword);	
	}
	
	public void addPanelButtomToCorpo2() {
		buttons = new JPanel();
		corpo2.add(buttons);
	}
	
	public void setVisible() {
		menu.setVisible(true);
	}
	
	public void setButtons() {
		send = new JButton("Send");
		voltarMenu = new JButton("Voltar parao Menu");
		buttons.add(send);
		buttons.add(voltarMenu);
		
	}
}
