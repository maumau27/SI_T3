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
		
		ControleMenu cc = new ControleMenu();
		
		cc.callMenu();
		
//		ControleMenu cm = new ControleMenu();
//		cm.callMenu();
		

	}

}
