package visao;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InterfaceEmail implements ActionListener{

	private JFrame area;
	private JPanel panel;
	private JLabel textLabel;
	private JButton send;
	private JButton reset;
	
	
	public InterfaceEmail(){
		area = new JFrame("Autentication Email");
		panel = new JPanel();
		area.setSize(500,300);
		
	}
	public void createLabelArea() {
		
        textLabel = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        send = new JButton("Send");
        send.addActionListener(this);
        reset = new JButton("Reset");
        panel.add(textLabel); // Components Added using Flow Layout
        panel.add(textLabel); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(send);
        panel.add(reset);

	}
	public void addPanel() {
		area.getContentPane().add(BorderLayout.SOUTH , panel);
	}
	
	public void setVisible() {
		area.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Hi");
		area.dispose();
		
	}
}
