package visao;

import java.util.ArrayList;

import model.Autentificador;
import model.BD;
import model.Par_Digitos;

public class MainInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//InterfaceEmail e = new InterfaceEmail();
		
		ArrayList<Par_Digitos> par_digitos = new ArrayList<Par_Digitos>();
		BD.Estabelecer_Conexao();
		BD.Log(1001);
		par_digitos.add(new Par_Digitos(3, 1));
		par_digitos.add(new Par_Digitos(9, 4));
		par_digitos.add(new Par_Digitos(1, 6));
		par_digitos.add(new Par_Digitos(6, 8));
		par_digitos.add(new Par_Digitos(9, 8));
		par_digitos.add(new Par_Digitos(4, 8));

		String path = "C:\\Users\\Maur�cio\\Desktop\\Puc\\Seguran�a Da Informa��o\\SI_T3\\T3\\Pacote-T3\\Keys\\admin-x509.crt";
		
		//Autentificador.getInstance().Validar_Dados_Cadastro(path, 2, 391694, 391694);
		
		path = "C:\\Users\\Maur�cio\\Desktop\\Puc\\Seguran�a Da Informa��o\\SI_T3\\T3\\Pacote-T3\\Keys\\user01-pkcs8-des.pem";
		
		Autentificador.getInstance().Iniciar_Validacao();
		Autentificador.getInstance().Validar_Email("user01@inf1416.puc-rio.br");
		Autentificador.getInstance().Validar_Senha(par_digitos);
		Autentificador.getInstance().Validar_ChavePrivada(path, "user01");
		
//		ControllerEmail ce = new ControllerEmail();
//		
//		ce.callInterfaceEmail();
		
		ControllerEmail cc = new ControllerEmail();
		
		cc.callInterfaceEmail();
		
//		ControleMenu cm = new ControleMenu();
//		cm.callMenu();
		

	}

}
