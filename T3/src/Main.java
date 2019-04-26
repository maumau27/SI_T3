import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<Par_Digitos> par_digitos = new ArrayList<Par_Digitos>();
		Autentificador auth = new Autentificador();
		
		par_digitos.add(new Par_Digitos(1, 2));
		par_digitos.add(new Par_Digitos(3, 4));
		par_digitos.add(new Par_Digitos(5, 6));
		par_digitos.add(new Par_Digitos(7, 8));
		
		BD.Estabelecer_Conexao();
		
		auth.Iniciar_Validacao();
		auth.Validar_Email("mauricio@teste.com");
		auth.Validar_Senha(par_digitos);
	}
}
