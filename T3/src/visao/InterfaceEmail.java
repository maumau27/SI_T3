package visao;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Autentificador;

public class InterfaceEmail{

	private JFrame area;
	private JPanel panel;
	private JTextField tf;
	private JLabel textLabel;
	private JButton send;
	private JButton reset;
	
	
	public InterfaceEmail(){
		area = new JFrame("Autentication Email");
		panel = new JPanel();
		area.setSize(500,300);
		createLabelArea();
		
	}
	
	public void addTextField() {
		textLabel = new JLabel("Email");
		tf = new JTextField(10);
		panel.add(textLabel);
		panel.add(textLabel);
		panel.add(tf);
	}
	
	public void addButtonSend() {
		send = new JButton("Send");
		panel.add(send);
	}
	public void addButtonReset() {
		reset = new JButton("Reset");
		panel.add(reset);
	}
	
	public void createLabelArea()
	{
      addTextField();
      addButtonSend();
      addButtonReset();
      addPanel();

	}
	public void addPanel() {
		area.getContentPane().add(BorderLayout.CENTER , panel);
	}
	
	public void setVisible() {
		area.setVisible(true);
	}
	

	public JFrame getArea() {
		return area;
	}

	public void setArea(JFrame area) {
		this.area = area;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JTextField getTf() {
		return tf;
	}

	public void setTf(JTextField tf) {
		this.tf = tf;
	}

	public JLabel getTextLabel() {
		return textLabel;
	}

	public void setTextLabel(JLabel textLabel) {
		this.textLabel = textLabel;
	}

	public JButton getSend() {
		return send;
	}

	public void setSend(JButton send) {
		this.send = send;
	}

	public JButton getReset() {
		return reset;
	}

	public void setReset(JButton reset) {
		this.reset = reset;
	}
}

