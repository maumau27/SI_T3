package model;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Par_Digitos> par_digitos = new ArrayList<Par_Digitos>();
		Autentificador auth = Autentificador.getInstance();
		
		par_digitos.add(new Par_Digitos(1, 2));
		par_digitos.add(new Par_Digitos(3, 4));
		par_digitos.add(new Par_Digitos(5, 6));
		par_digitos.add(new Par_Digitos(7, 8));
		
		String email = "'mauricio@teste.com";

		BD.Estabelecer_Conexao();
		
		auth.Iniciar_Validacao();
		auth.Validar_Email(email);
		auth.Validar_Senha(par_digitos);
	}
}
