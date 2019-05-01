package visao;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ControllerInterface {

	private int numberClicks = 0;
	
	public void callInterfacePassword() {
		InterfacePassword ip = new InterfacePassword(5);
		addValueAndActionToButton(ip);
		AddActButtonSend(ip);
		addActResetButton(ip);
		ip.setVisibleScreen();
		
	}
	
	
	public void addValuetoButton(int number1 , int number2 , JButton button) {
		String number1String = Integer.toString(number1);
		String number2String = Integer.toString(number2);
		button.setText(number1String + "|" + number2String);
		button.setPreferredSize(new Dimension(80,80));
	}
	
	public void addPassword(InterfacePassword ip , String text) {
		
		ip.getPassword().setText(ip.getPassword().getText() + "(" + text + ")");
	}
	
	
	public void addValueAndActionToButton(InterfacePassword ip) {
		int n1 , n2 = 0;
		ArrayList<Par_Digitos> digitos = Functions.Gerar_Set_Pares();
		for (int i = 0 ; i < ip.getButtons().size() ; i++) {
			n1 = digitos.get(i).n1;
			n2 = digitos.get(i).n2;
			JButton button = ip.getButtons().get(i);
			addValuetoButton(n1, n2, button);
			if ( button.getActionListeners().length == 0) {
				addActButtonNumbers(button, ip);
			}
		}		
	}
	
	public void addActResetButton(InterfacePassword i) {
		i.getReset().addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e)
	        {	     
				numberClicks = 0;
				i.getPassword().setText("");
	        }
			});
	}
	
	public void addActButtonNumbers(JButton button , InterfacePassword i) {

		button.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e)
        {	     
			numberClicks++;
			if ( numberClicks <= 5) {
            addPassword(i, button.getText());
            addValueAndActionToButton(i);
            i.getScreen().revalidate();
			} else {
				JOptionPane.showMessageDialog(button, "Password must have less than 5 numbers!");
			}
        }
		});
	}
	
	public boolean comparePassword(String text , String value) {
		text = text.replace("(", "").replace(")", "").replace("|", "");
		int contadorNumber1 = 0;
		int contadorNumber2 = 1;
		if (text.length() == 10) 
		{
			for (int i = 0 ; i < value.length() ; i++) {
			char n1 = text.charAt(contadorNumber1);
			char n2 = text.charAt(contadorNumber2);
			char valueChar = value.charAt(i);
			if ( valueChar != n1 && valueChar != n2) {
				return false;
			}
			contadorNumber1 += 2;
			contadorNumber2 += 2;
			
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	public void AddActButtonSend(InterfacePassword i) {
		i.getSend().addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean correct = comparePassword(i.getPassword().getText(), "52675");
				
				if ( correct) {
					JOptionPane.showMessageDialog(null, "Password Accepted");
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect Password");
				}
			}
		});
	}
}
