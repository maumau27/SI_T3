package model;
import java.io.File;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.xml.bind.DataBindingException;

public class Autentificador {
	
	private static Autentificador instance;
	
	public static Autentificador getInstance()
	{
		if(instance == null)
			instance = new Autentificador();
		return instance;
	}
	
	private enum State { EMAIL, SENHA, CHAVE_PRIVADA, BLOQUEADO }
	private State current_state = State.EMAIL;
	
	private int id;
	
	private int erros_senha = 3;
	private int erros_chave_privada = 3;
	
	private String email_format = ".*@.*\\..*";
	private Pattern email_pattern = Pattern.compile(email_format);
	
	public void Iniciar_Validacao()
	{
		id = -1;
		erros_senha = 3;
		erros_chave_privada = 3;
		current_state = State.EMAIL;
	}
	
	public int Validar_Email(String email)
	{
		//se n�o est� no estado de email, falha a valida��o
		if(current_state != State.EMAIL)
		{
			System.out.println("Estado do Autentificador Invalido");
			return -1;
		}
		
		//checar se n�o existem ' no email e se ele est� num formato aceitavel
		Matcher m = email_pattern.matcher(email);
		if(!m.find() || email.contains("'"))
		{
			System.out.println("Formato invalido de email");
			return -1;
		}
		
		id = BD.Get_Id_by_Email(email);
		
		if(id <= 0)
		{
			System.out.println("Email Invalido");
			return -1;
		}
		
		System.out.println("Email Validado");
		current_state = State.SENHA;
		return 1;
	}

	public int Validar_Senha(ArrayList<Par_Digitos> par_digitos)
	{
		//se n�o est� no estado de senha, falha a valida��o
		if(current_state != State.SENHA)
		{
			System.out.println("Estado do Autentificador Invalido");
			return -1;
		}
		
		//valida a senha
		int senha = BD.Get_Senha_by_Id(id);
		
		char[] senha_array = Integer.toString(senha).toCharArray();
		
		if(senha_array.length != par_digitos.size())
		{
			return Senha_Invalida();
		}
		
		int i = 0;
		for (char digit : senha_array) {
			int valor = Character.getNumericValue(digit);

			if(!par_digitos.get(i).Match(valor))
			{
				return Senha_Invalida();
			}
			
			i++;
		}
		
		System.out.println("Senha Validada");
		current_state = State.CHAVE_PRIVADA;
		return -1;
	}
	
	private int Senha_Invalida()
	{
		System.out.println("Acesso Negado : Senha Invalida!");
		erros_senha -= 1;
		if(erros_senha <= 0)
		{
			System.out.println("Usuario Bloqueado : Numero de tentativas maximo execidida");//bloquear o usuario
			current_state = State.BLOQUEADO;
			return -2;
		}
		return -1;
	}
	
	public String Cryptografar_Senha(int senha, PrivateKey private_key)
	{
		byte[] senha_crypto = null;
		//cryptografar senha
		
		return Functions.Byte_to_Hex(senha_crypto);
	}
	
	public int Decryptografar_Senha(StringBuffer senha_crypto_hex, PublicKey public_key)
	{
		//decriptografar a senha
		String senha_crypto_hex_string = senha_crypto_hex.toString();
		byte[] senha_crypto_byte = new byte[senha_crypto_hex_string.length() / 2];
		
		int senha = 0;
		
		return senha;
	}
	
	public int Validar_ChavePrimaria(File chave_privada)
	{
		//se n�o est� no estado de chave_privada, falha a valida��o
		if(current_state != State.CHAVE_PRIVADA)
		{
			System.out.println("Estado do Autentificador Invalido");
			return -1;
		}
		return 1;
	}
	
	public void Efetuar_Login()
	{
		
	}
}
