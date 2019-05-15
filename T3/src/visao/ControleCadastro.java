package visao;

import java.awt.color.CMMException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class ControleCadastro {

	
	public void callCadastro(JPanel cabecalho , JPanel corpo1) {
		InterfaceCadastro ic = new InterfaceCadastro();
		ic.addCabecalho(cabecalho);
		ic.addCorpo1(corpo1);
		addActVoltarMenu(ic);
		ic.setVisible();
	}
	
	
	public void addActVoltarMenu(InterfaceCadastro ic) {
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
