package visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ControleConsulta {

	
	public void callConsulta(JPanel cabecalho , JPanel corpo1) {
		InterfaceConsulta ic = new InterfaceConsulta();
		ic.addCabecalho(cabecalho);
		ic.addCorpo1(corpo1);
		ic.setVisible();
		addActLista(ic);
		addActSair(ic);
		
	}
	
	
	public void addActLista(InterfaceConsulta ic) {
		ic.getListar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
	}
	
	public void addActSair(InterfaceConsulta ic) {
		ic.getSair().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ic.getMenu().dispose();
				ControleMenu cm = new ControleMenu();
				cm.callMenu();
				
			}
		});
	}
}
