package visao;

import model.BD;

public class MainInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//InterfaceEmail e = new InterfaceEmail();
		
		
		BD.Estabelecer_Conexao();
		ControllerEmail ce = new ControllerEmail();
		
		ce.callInterfaceEmail();

	}

}
