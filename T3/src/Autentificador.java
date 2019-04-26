import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.DataBindingException;

public class Autentificador {
	
	private enum State { EMAIL, SENHA, CHAVE_PRIVADA }
	private State current_state = State.EMAIL;
	
	private int id;
	
	private int erros_senha = 3;
	private int erros_chave_privada = 3;
	
	public void Iniciar_Validacao()
	{
		id = -1;
		erros_senha = 3;
		erros_chave_privada = 3;
		current_state = State.EMAIL;
	}
	
	public boolean Validar_Email(String email)
	{
		//se não está no estado de email, falha a validação
		if(current_state != State.EMAIL)
			return false;
		
		id = BD.Get_Id_by_Email(email);
		
		if(id <= 0)
		{
			System.out.println("Email Invalido");
			return false;
		}
		
		System.out.println("Email Validado");
		current_state = State.SENHA;
		return false;
	}

	public boolean Validar_Senha(ArrayList<Par_Digitos> par_digitos)
	{
		//se não está no estado de senha, falha a validação
		if(current_state != State.SENHA)
			return false;
		
		//valida a senha
		int senha = BD.Get_Senha_by_Id(id);
		
		char[] senha_array = Integer.toString(senha).toCharArray();
		
		if(senha_array.length != par_digitos.size())
		{
			System.out.println("Acesso Negado : Tamanho da Senha Invalido");
			erros_senha -= 1;
			if(erros_senha <= 0)
				System.out.println("Usuario Bloqueado : Numero de tentativas maximo execidida");//bloquear o usuario
			return false;
		}
		
		int i = 0;
		for (char digit : senha_array) {
			int valor = Character.getNumericValue(digit);

			if(!par_digitos.get(i).Match(valor))
			{
				System.out.println("Acesso Negado : Senha Invalida!");
				erros_senha -= 1;
				if(erros_senha <= 0)
					System.out.println("Usuario Bloqueado : Numero de tentativas maximo execidida");//bloquear o usuario
				return false;
			}
			
			i++;
		}
		
		System.out.println("Senha Validada");
		current_state = State.CHAVE_PRIVADA;
		return true;
	}
	
	public boolean Validar_ChavePrimaria(File chave_privada)
	{
		//se não está no estado de chave_privada, falha a validação
		if(current_state != State.CHAVE_PRIVADA)
			return false;
		return false;
	}
}
