package visao;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
		addActSend();
	}
	public void addButtonReset() {
		reset = new JButton("Reset");
		panel.add(reset);
		addActReset();
	}
	
	public void createLabelArea()
	{
      addTextField();
      addButtonSend();
      addButtonReset();
      addPanel();
      setVisible();

	}
	public void addPanel() {
		area.getContentPane().add(BorderLayout.SOUTH , panel);
	}
	
	public void setVisible() {
		area.setVisible(true);
	}
	
	public void addActSend() {
		send.addActionListener( new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	String text = tf.getText();
	                if (text.equals("") || !text.contains("@") || !text.contains(".com")) {
	                	JOptionPane.showMessageDialog(null, "Invalid email");
	                }
	        }
	});
	}
	public void addActReset() {
		reset.addActionListener( new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	                tf.setText("");
	        }
	});
	}
}

