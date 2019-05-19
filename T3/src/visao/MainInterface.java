package visao;

import model.BD;

public class MainInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//InterfaceEmail e = new InterfaceEmail();
		
		
		BD.Estabelecer_Conexao();
		BD.Log(1001);
//		ControllerEmail ce = new ControllerEmail();
//		
//		ce.callInterfaceEmail();
		
		ControllerEmail cc = new ControllerEmail();
		
		cc.callInterfaceEmail();
		
//		ControleMenu cm = new ControleMenu();
//		cm.callMenu();
		

	}

}
