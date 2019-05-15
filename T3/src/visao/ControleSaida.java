package visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ControleSaida {

	public void callSaida(JPanel cabecalho , JPanel corpo1) {
		InterfaceSaida is = new InterfaceSaida();
		is.addCabecalho(cabecalho);
		is.addCorpo1(corpo1);
		addActSair(is);
		addActSairMenu(is);
		is.setVisible();
		
		
	}
	
	
	public void addActSair(InterfaceSaida is) {
		is.getSair().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				is.getMenu().dispose();				
			}
		});
	}
	
	public void addActSairMenu(InterfaceSaida is) {
		is.getSairMenu().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				is.getMenu().dispose();
				ControleMenu cm = new ControleMenu();
				cm.callMenu();
				
			}
		});
		
	}
}
