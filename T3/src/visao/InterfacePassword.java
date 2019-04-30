package visao;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InterfacePassword {

	
	private JFrame screen;
	private JPanel panel;
	private ArrayList<JButton> buttons;
	
	
	public InterfacePassword(ArrayList<Par_Digitos> first) {
		screen = new JFrame("Autentication Password");
		panel = new JPanel();
		screen.setSize(400,400);
		buttons = new ArrayList<JButton>(5);
		addPanel();
		create(first);
		screen.setVisible(true);
	}
	
	public void create(ArrayList<Par_Digitos> digitos) {
		for (Par_Digitos digito : digitos ) {
			addButton(digito.n1, digito.n2);
		}
		addButtonsToPanel();
		
	}
	
	public void addButton(int number1 , int number2) {
		String number1String = Integer.toString(number1);
		String number2String = Integer.toString(number2);
		JButton button = new JButton(number1String + "|" + number2String);
		button.setPreferredSize(new Dimension(80,80));
		buttons.add(button);
		addActButton(button);
	}
	
	public void addButtonsToPanel() {
		for (int i = 0 ; i < buttons.size() ; i++) {
			
			panel.add(buttons.get(i));
		}
	}
	
	public void reinicialize() {
		panel = new JPanel();
		buttons = new ArrayList<JButton>(5);
	}
	
	public void addPanel() {
		screen.getContentPane().add(BorderLayout.CENTER , panel);
	}
	
	public void addActButton(JButton button) {

		button.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e)
        {	     
        	screen.remove(panel);
        	reinicialize();
    		ArrayList<Par_Digitos> digitos = Functions.Gerar_Set_Pares();
            create(digitos);
            addPanel();
            screen.revalidate();
        }
		});
	}
	
}
