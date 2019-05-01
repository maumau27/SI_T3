package visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.Autentificador;

public class ControllerEmail {

	
	public void callInterfaceEmail() {
		InterfaceEmail ie = new InterfaceEmail();
		Autentificador.getInstance().Iniciar_Validacao();
		addActs(ie);
		
	}
	
	
	public void addActs(InterfaceEmail ie) {
		addActSend(ie);
		addActReset(ie);
		ie.setVisible();
		
	}
	
	
	public void addActSend(InterfaceEmail ie) {
		ie.getSend().addActionListener( new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	String text = ie.getTf().getText();
	            boolean validate = Autentificador.getInstance().Validar_Email(text);
	            
	            if (validate) {
	            	ie.getArea().dispose();
	            	ControllerPassword cp = new ControllerPassword();
	            	cp.callInterfacePassword();	            	
	            }
	        }
	});
	}
	
	public void addActReset(InterfaceEmail ie) {
		ie.getReset().addActionListener( new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	                ie.getTf().setText("");
	        }
	});
	}
}
