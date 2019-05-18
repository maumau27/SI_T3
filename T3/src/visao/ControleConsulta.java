package visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import model.BD;
import model.Usuario;

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
				BD.Log(8003, Usuario.getInstance().Get_Email());
			}
		});
	}
	
	public void addActSair(InterfaceConsulta ic) {
		ic.getSair().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BD.Log(8002, Usuario.getInstance().Get_Email());
				
				ic.getMenu().dispose();
				ControleMenu cm = new ControleMenu();
				cm.callMenu();
				
			}
		});
	}
}
