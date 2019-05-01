package visao;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Thread.State;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import model.Autentificador;
import model.Functions;
import model.Par_Digitos;

public class ControllerPassword {

	private int numberClicks = 0;
	private ArrayList<Par_Digitos> atualValues;
	
	public void callInterfacePassword() {
		InterfacePassword ip = new InterfacePassword(5);
		atualValues = new ArrayList<Par_Digitos>(5);
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
	
	
	public void addValueAndActionToButton(InterfacePassword ip) {
		int n1 , n2 = 0;
		ArrayList<Par_Digitos> digitos = Functions.Gerar_Set_Pares();
		for (int i = 0 ; i < ip.getButtons().size() ; i++) {
            atualValues.add(digitos.get(i));
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
				atualValues = new ArrayList<Par_Digitos>(5);
	        }
			});
	}
	
	public void addActButtonNumbers(JButton button , InterfacePassword i) {

		button.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent e)
        {	     
			numberClicks++;
			if ( numberClicks <= 5) {
            addValueAndActionToButton(i);
            i.getScreen().revalidate();
			} else {
				JOptionPane.showMessageDialog(button, "Password must have less than 5 numbers!");
			}
        }
		});
	}	
	
	public void AddActButtonSend(InterfacePassword i) {
		i.getSend().addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean validarSenha = Autentificador.getInstance().Validar_Senha(atualValues);
				
				if ( Autentificador.getInstance().Current_State_Blocked()) {
					i.getScreen().dispose();
					ControllerEmail ce =  new ControllerEmail();
					ce.callInterfaceEmail();				
				}
				
				if (validarSenha) {
					
				}
			}
		});
	}
}