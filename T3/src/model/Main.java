package model;
import java.io.FileInputStream;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Par_Digitos> par_digitos = new ArrayList<Par_Digitos>();
		BD.Estabelecer_Conexao();		
		
		par_digitos.add(new Par_Digitos(3, 1));
		par_digitos.add(new Par_Digitos(9, 4));
		par_digitos.add(new Par_Digitos(1, 6));
		par_digitos.add(new Par_Digitos(6, 8));
		par_digitos.add(new Par_Digitos(9, 8));
		par_digitos.add(new Par_Digitos(4, 8));
		Object[] options = {"Recusar",
        "Confirmar"};
		int n = JOptionPane.showOptionDialog(null,
			"Would you like green eggs and ham?",
			"A Silly Question",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,     //do not use a custom Icon
			options,  //the titles of buttons
			options[0]); //default button title
		System.out.println(n);
		String path = "C:\\Users\\Maurício\\Desktop\\Puc\\Segurança Da Informação\\SI_T3\\T3\\Pacote-T3\\Keys\\admin-x509.crt";
		
		//Autentificador.getInstance().Validar_Dados_Cadastro(path, 2, 391694, 391694);
		
		path = "C:\\Users\\Maurício\\Desktop\\Puc\\Segurança Da Informação\\SI_T3\\T3\\Pacote-T3\\Keys\\user01-pkcs8-des.pem";
		
		Autentificador.getInstance().Iniciar_Validacao();
		Autentificador.getInstance().Validar_Email("user01@inf1416.puc-rio.br");
		Autentificador.getInstance().Validar_Senha(par_digitos);
		Autentificador.getInstance().Validar_ChavePrivada(path, "user01");
		
		Usuario user = Usuario.getInstance();
		if(user != null)
		{
			path = "C:\\Users\\Maurício\\Desktop\\Puc\\Segurança Da Informação\\SI_T3\\T3\\Pacote-T3\\Files";
			ArrayList<Arquivo> arquivos = user.Parse_Index(path);
			user.Decriptar_Arquivo(arquivos.get(0));
			user.Decriptar_Arquivo(arquivos.get(1));
		}
		
		/*String path = "C:\\Users\\Maurício\\Desktop\\Puc\\Segurança Da Informação\\SI_T3\\T3\\Pacote-T3\\Keys\\admin-x509.crt";
	 	Autentificador.getInstance().Iniciar_Validacao();
		X509Certificate certificate = Autentificador.getInstance().Recuperar_Certificado_Digital(1);
		System.out.println(certificate.getSubjectDN());*/
		 
		
		/*par_digitos.add(new Par_Digitos(1, 2));
		par_digitos.add(new Par_Digitos(3, 4));
		par_digitos.add(new Par_Digitos(5, 6));
		par_digitos.add(new Par_Digitos(7, 8));

		String email = "'mauricio@teste.com";

		BD.Estabelecer_Conexao();
		
		auth.Iniciar_Validacao();
		auth.Validar_Email(email);
		auth.Validar_Senha(par_digitos);*/
	}
}
