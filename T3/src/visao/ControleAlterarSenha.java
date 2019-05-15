package visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ControleAlterarSenha {

	
	public void callAlterarSenha(JPanel cabecalho , JPanel corpo1) {
		InterfaceAlterarSenha ia = new InterfaceAlterarSenha();
		ia.addCabecalho(cabecalho);
		ia.addCorpo1(corpo1);
		addActVoltarMenu(ia);
		ia.setVisible();
		
	}
	
	public void addActVoltarMenu(InterfaceAlterarSenha ia) {
		ia.getVoltarMenu().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ia.getMenu().dispose();
				ControleMenu cm = new ControleMenu();
				cm.callMenu();
				
			}
		});
	}
	
	
	
}
