package visao;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ControleMenu {

	public void callMenu() {
		InterfaceMenu im = new InterfaceMenu();
		im.setVisible();
		addActBottom1(im);
		addActBottom2(im);
		addActBottom3(im);
		addActBottom4(im);
	}
	
	
	public void addActBottom1(InterfaceMenu i) {
		i.getButton1().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				i.getMenu().dispose();
				ControleCadastro cc = new ControleCadastro();
				cc.callCadastro(i.getCabecalho() , i.getCorpo1());
				
			}
		});
	}
	
	public void addActBottom2(InterfaceMenu i) {
		i.getButton2().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				i.getMenu().dispose();
				ControleAlterarSenha ca = new ControleAlterarSenha();
				ca.callAlterarSenha(i.getCabecalho(), i.getCorpo1());
				
			}
		});
	}
	
	public void addActBottom3(InterfaceMenu i) {
		i.getButton3().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				i.getMenu().dispose();
				ControleConsulta cc = new ControleConsulta();
				cc.callConsulta(i.getCabecalho(), i.getCorpo1());
				
			}
		});
	}
	
	public void addActBottom4(InterfaceMenu i) {
		i.getButton4().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				i.getMenu().dispose();
				ControleSaida cs = new ControleSaida();
				cs.callSaida(i.getCabecalho(), i.getCorpo1());
				
			}
		});
	}
}