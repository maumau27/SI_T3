package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.undo.UndoableEdit;

public class InterfaceCertificado {

	JFrame screen;
	JPanel panel;
	JButton btAbrir;
	JLabel labelSenha;
	JTextField senhaSecreta;
	JPanel fieldSenha;
	JButton send;
	
	
	public InterfaceCertificado() {
		screen = new JFrame("Autentication Certificate");
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		screen.setSize(400,400);
		addPanel();
		setAbrirConfig();
		addLabel();
		addText();
		addSendButtom();
		
	}
	
	
	public JFrame getScreen() {
		return screen;
	}


	public void setScreen(JFrame screen) {
		this.screen = screen;
	}


	public JButton getSend() {
		return send;
	}


	public void setSend(JButton send) {
		this.send = send;
	}


	public void setAbrirConfig() {
		btAbrir = new JButton();
		btAbrir.setBounds(new Rectangle(60, 49, 166, 69));
		btAbrir.setFont(new Font("Dialog", Font.BOLD, 24));
		btAbrir.setText("Abrir arquivo");
		addButtom();
	}
	
	public void addLabel() {
		labelSenha = new JLabel("Senha Secreta");	
		labelSenha.setFont(new Font("Dialog", Font.BOLD, 15));
	}
	
	public void addText() {
		fieldSenha = new JPanel();
		fieldSenha.setLayout(new BoxLayout(fieldSenha, BoxLayout.Y_AXIS));
		senhaSecreta = new JTextField(255);
		senhaSecreta.setDocument(new TamanhoFixoJText(255));
		fieldSenha.add(labelSenha);
		fieldSenha.add(senhaSecreta);
		panel.add(fieldSenha , BorderLayout.CENTER);
	}
	
	public void addButtom() {
		panel.add(btAbrir , BorderLayout.NORTH);
	}
	public void addSendButtom() {
		send = new JButton("Send");
		send.setFont(new Font("Dialog", Font.BOLD, 15));
		send.setPreferredSize(new Dimension(100,100));
		panel.add(send , BorderLayout.SOUTH);
	}
	
	public void addPanel() {
		screen.getContentPane().add(panel);
	}
	
	public void setVisible() {
		screen.setVisible(true);
	}


	public JButton getBtAbrir() {
		return btAbrir;
	}


	public void setBtAbrir(JButton btAbrir) {
		this.btAbrir = btAbrir;
	}
	
	
}
