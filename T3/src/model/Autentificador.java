package model;
import java.io.File;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
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
	
	private Encoder encoder_64;
	private Decoder decoder_64;
	
	public Autentificador()
	{
		encoder_64 = Base64.getEncoder();
		decoder_64 = Base64.getDecoder();
	}
	
	public void Iniciar_Validacao()
	{
		id = -1;
		erros_senha = 3;
		erros_chave_privada = 3;
		current_state = State.EMAIL;
	}
	
	public int Validar_Email(String email)
	{
		//se não está no estado de email, falha a validação
		if(current_state != State.EMAIL)
		{
			System.out.println("Estado do Autentificador Invalido");
			return -1;
		}
		
		if(!Validar_Formato_Email(email))
		{
			System.out.println("Formato invalido de email");
			return -1;
		}

		id = BD.Get_Id_by_Email(email);
		
		if(BD.Usuario_Bloqueado(id))
		{
			System.out.println("Usuario Bloqueado");
			return -1;
		}
		
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
		//se nï¿½o estï¿½ no estado de senha, falha a validaï¿½ï¿½o
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
			System.out.println("Usuario Bloqueado : Numero de tentativas maximo execidida");
			BD.Bloquear_Usuario(id);
			current_state = State.BLOQUEADO;
			return -2;
		}
		return -1;
	}
	
	public String Cryptografar_Senha(int senha)
	{
		byte[] senha_crypto = null;
		String senhaString = Integer.toString(senha);
		
		senhaString += Functions.Get_Random_SALT();
		
		String senhaHex = Functions.Byte_to_Hex(Gerar_Digest("SHA1", senhaString));
		
		return senhaHex;
	}
	
	public int Validar_ChavePrimaria(File chave_privada)
	{
		//se nï¿½o estï¿½ no estado de chave_privada, falha a validaï¿½ï¿½o
		if(current_state != State.CHAVE_PRIVADA)
		{
			System.out.println("Estado do Autentificador Invalido");
			return -1;
		}
		return 1;
	}
	
	public boolean Validar_Formato_Email(String email)
	{
		//checar se não existem ' no email e se ele está num formato aceitavel
		Matcher m = email_pattern.matcher(email);
		if(!m.find() || email.contains("'"))
		{
			System.out.println("Formato invalido de email");
			return false;
		}
		return true;
	}
	
	public int Validar_Dados_Cadastro(String email, int grupo, int senha, int senhaConfima)
	{
		char[] senhaChar = Integer.toString(senha).toCharArray();
		char[] senhaConfimaChar = Integer.toString(senhaConfima).toCharArray();
		
		//valida que a senha é igual a senhaConfirma
		if(senha != senhaConfima)
		{
			System.out.println("Senha não bate com a confirmação");
			return -1;
		}
		
		//valida que a senha está no padrão valido de senha
		if(!Functions.Validar_Padrao_Senha(senha))
		{
			System.out.println("Padrão invalido de senha");
			return -1;
		}
		
		//valida que o email do usuario é unico no sistema
		if(BD.Usuario_Existe(email))
		{
			System.out.println("Usuario ja existe");
			return -1;
		}
		
		//valida que o email está num formato valido
		if(Validar_Formato_Email(email))
		{
			System.out.println("Formato invalido de email");
			return -1;
		}
		
		//valida que o id_grupo existe
		if(grupo != 1 && grupo != 2)
		{
			System.out.println("Grupo não existe");
			return -1;
		}
		
		return 1;
	}
	
	private byte[] Gerar_Digest(String algoritmo, String dado)
	{
		try {
			byte[] plainText = dado.getBytes("UTF8");
			
			MessageDigest messageDigest = MessageDigest.getInstance(algoritmo);
			messageDigest.update( plainText);
			byte [] digest = messageDigest.digest();
			
			return digest;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Signature Gerar_AssinaturaDigital(String algoritimo, PrivateKey privateKey, String data)
	{
		Signature sig = null;
		byte[] plain_text = null;
		try {
			plain_text = data.getBytes("UTF8");
			sig = Signature.getInstance(algoritimo);
			sig.initSign(privateKey);
			sig.update(plain_text);
			byte[] signature = sig.sign();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sig;
	}
	
	public void Efetuar_Login()
	{
		
	}
}
