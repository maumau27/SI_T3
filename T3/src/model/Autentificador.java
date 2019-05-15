package model;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLContext;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument.LeafElement;

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
	private X509Certificate ceritificadoDigital;
	private PrivateKey privateKey;
	private String login_name;
	
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
		Iniciar_Validacao();
	}
	
	//TODO - tornar private
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
			JOptionPane.showMessageDialog(null, "Estado do Autentificador Invalido", "Erro", JOptionPane.ERROR_MESSAGE);
			System.out.println("Estado do Autentificador Invalido");
			return -1;
		}
		
		if(!Validar_Formato_Email(email))
		{
			JOptionPane.showMessageDialog(null, "Formato invalido de email", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Formato invalido de email");
			return -1;
		}

		id = BD.Get_Id_by_Email(email);
		
		if(id <= 0)
		{
			BD.Log(2005, email);
			JOptionPane.showMessageDialog(null, "Email Invalido", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Email Invalido");
			return -1;
		}
		
		if(BD.Usuario_Bloqueado(id))
		{
			BD.Log(2004, email);
			JOptionPane.showMessageDialog(null, "Usuario Bloqueado", "Erro", JOptionPane.WARNING_MESSAGE);
			System.out.println("Usuario Bloqueado");
			return -1;
		}

		BD.Log(2003, email);
		login_name = email;
		System.out.println("Email Validado");
		current_state = State.SENHA;
		return 1;
	}

	public int Validar_Senha(ArrayList<Par_Digitos> par_digitos)
	{
		//se não está no estado de senha, falha a validação
		if(current_state != State.SENHA)
		{
			JOptionPane.showMessageDialog(null, "Estado do Autentificador Invalido", "Erro", JOptionPane.ERROR_MESSAGE);
			System.out.println("Estado do Autentificador Invalido");
			return -1;
		}
		
		//valida a senha
		String senha = BD.Get_Senha_by_Id(id);
		String SALT = BD.Get_Salt_by_Id(id);
		
		if(Testa_Senhas(par_digitos, SALT, senha))
		{
			BD.Log(3003, login_name);
			System.out.println("Senha Validada");
			current_state = State.CHAVE_PRIVADA;
			return 1;
		}

		return Senha_Invalida();
	}
	
	private int Senha_Invalida()
	{
		JOptionPane.showMessageDialog(null, "Senha Invalida!", "Erro", JOptionPane.INFORMATION_MESSAGE);
		System.out.println("Acesso Negado : Senha Invalida!");
		erros_senha -= 1;
		if(erros_senha == 2)
			BD.Log(3004, login_name);
		if(erros_senha == 1)
			BD.Log(3005, login_name);
		if(erros_senha <= 0)
		{
			BD.Log(3006, login_name);
			BD.Log(3007, login_name);
			JOptionPane.showMessageDialog(null, "Usuario Bloqueado : Numero de tentativas maximo execidida", "Erro", JOptionPane.ERROR_MESSAGE);
			System.out.println("Usuario Bloqueado : Numero de tentativas maximo execidida");
			BD.Bloquear_Usuario(id);
			current_state = State.BLOQUEADO;
			return -2;
		}
		return -1;
	}
	
	public String Cryptografar_Senha(int senha, String SALT)
	{
		String senhaString = Integer.toString(senha);
		
		senhaString += SALT;
		
		String senhaHex = Functions.Byte_to_Hex(Gerar_Digest("SHA1", senhaString));
		
		return senhaHex;
	}
	
	public int Validar_ChavePrivada(String chave_privada_path, String frase_secreta)
	{
		//se não está no estado de chave_privada, falha a validação
		if(current_state != State.CHAVE_PRIVADA)
		{
			JOptionPane.showMessageDialog(null, "Estado do Autentificador Invalido", "Erro", JOptionPane.ERROR_MESSAGE);
			System.out.println("Estado do Autentificador Invalido");
			return -1;
		}
		
		byte[] data = Functions.Random_Byte_Array(2048);
		byte[] signature = null;
		ceritificadoDigital = Recuperar_Certificado_Digital(id);
		
		privateKey = Recuperar_Private_Key(chave_privada_path, frase_secreta);
		
		if(privateKey == null)
		{
			return ChavePrivada_Invalida();
		}
		
		Signature assinatura = Gerar_AssinaturaDigital("MD5withRSA");
		
		try {
			assinatura.initSign(privateKey);
			assinatura.update(data);
			signature = assinatura.sign();
		} catch (Exception e) {
			e.printStackTrace();
			return ChavePrivada_Invalida();
		}
				 
		if(!Validar_AssinaturaDigital(assinatura, ceritificadoDigital.getPublicKey(), data, signature))
		{
			BD.Log(4006, login_name);
			return ChavePrivada_Invalida();
		}
		
		BD.Log(4003, login_name);
		System.out.println("Chave Privada Validada");
		Efetuar_Login();
		return 1;
	}
	
	private int ChavePrivada_Invalida()
	{
		JOptionPane.showMessageDialog(null, "Chave privada invalida", "Erro", JOptionPane.INFORMATION_MESSAGE);
		System.out.println("Acesso Negado : Chave privada invalida");
		erros_chave_privada -= 1;
		if(erros_chave_privada <= 0)
		{
			BD.Log(4007, login_name);
			JOptionPane.showMessageDialog(null, "Usuario Bloqueado : Numero de tentativas maximo execidida", "Erro", JOptionPane.ERROR_MESSAGE);
			System.out.println("Usuario Bloqueado : Numero de tentativas maximo execidida");
			BD.Bloquear_Usuario(id);
			current_state = State.BLOQUEADO;
			return -2;
		}
		return -1;
	}
	
	private boolean Validar_Formato_Email(String email)
	{
		//checar se não existem ' no email e se ele está num formato aceitavel
		Matcher m = email_pattern.matcher(email);
		if(!m.find() || email.contains("'"))
		{
			return false;
		}
		return true;
	}
	
	public int Validar_Dados_Cadastro(String certificadoPath, int grupo, int senha, int senhaConfirma)
	{
		X509Certificate certificado = Recuperar_Certificado_Digital(certificadoPath);
		
		String SALT = Functions.Get_Random_SALT();
		
		if(certificado == null)
		{
			JOptionPane.showMessageDialog(null, "Certificado Invalido", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Certificado Invalido");
			return -1;
		}
		
		String email = Recuperar_Email_Certificado(certificado);
		String nome = Recuperar_Nome_Certificado(certificado);
		
		//valida que a senha é igual a senhaConfirma
		if(senha != senhaConfirma)
		{
			JOptionPane.showMessageDialog(null, "Senha não bate com a confirmação", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Senha não bate com a confirmação");
			return -1;
		}
		
		//valida que a senha está no padrão valido de senha
		if(!Functions.Validar_Padrao_Senha(senha))
		{
			JOptionPane.showMessageDialog(null, "Padrão invalido de senha", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Padrão invalido de senha");
			return -1;
		}
		
		//valida que o email do usuario é unico no sistema
		if(BD.Usuario_Existe(email))
		{
			JOptionPane.showMessageDialog(null, "Usuario ja existe", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Usuario ja existe");
			return -1;
		}
		
		//valida que o email está num formato valido
		if(!Validar_Formato_Email(email))
		{
			JOptionPane.showMessageDialog(null, "Formato invalido de email", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Formato invalido de email");
			return -1;
		}
		
		//valida que o id_grupo existe
		if(grupo != 1 && grupo != 2)
		{
			JOptionPane.showMessageDialog(null, "Grupo não existe", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Grupo não existe");
			return -1;
		}
		
		BD.Cadastrar_Usuario(email, nome, Recuperar_PEM_Format_Ceritficado(Ler_File(certificadoPath)), SALT, grupo, Cryptografar_Senha(senha, SALT));;
		
		return 1;
	}
	
	public int Validar_Troca_Senha(String certificadoPath, int senha, int senhaConfirma)
	{
		String SALT = Functions.Get_Random_SALT();
		boolean valido = true;
		boolean alterarSenha = false;
		boolean alterarCertificado = false;
		
		
		X509Certificate certificado = Recuperar_Certificado_Digital(certificadoPath);
		if(certificado == null)
		{
			JOptionPane.showMessageDialog(null, "Certificado Invalido", "Erro", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("Certificado Invalido");
			valido = false;
		}
		else
		{
			alterarCertificado = true;
		}
		
		//senha foi prenchida
		if(senha != 0)
		{
			//valida que a senha é igual a senhaConfirma
			if(senha != senhaConfirma)
			{
				JOptionPane.showMessageDialog(null, "Senha não bate com a confirmação", "Erro", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Senha não bate com a confirmação");
				valido = false;
			}
			
			//valida que a senha está no padrão valido de senha
			if(!Functions.Validar_Padrao_Senha(senha))
			{
				JOptionPane.showMessageDialog(null, "Padrão invalido de senha", "Erro", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Padrão invalido de senha");
				valido = false;
			}
			alterarSenha = true;
		}
		
		if(valido)
		{
			if(alterarSenha)
				BD.Atualizar_Senha_Usuario(id, Cryptografar_Senha(senha, SALT), SALT);
			if(alterarCertificado)
				BD.Atualizar_Certificado_Usuario(id, Recuperar_PEM_Format_Ceritficado(Ler_File(certificadoPath)));
		}
		
		return 1;
	}
	
	public byte[] Gerar_Digest(String algoritmo, String dado)
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
	
	public Signature Gerar_AssinaturaDigital(String algoritimo)
	{
		Signature sig = null;
		try {
			sig = Signature.getInstance(algoritimo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sig;
	}
	
	public boolean Validar_AssinaturaDigital(Signature assinatura, PublicKey publicKey, byte[] data, byte[] signature)
	{
		try {
			assinatura.initVerify(publicKey);
			assinatura.update(data);
			
			if (assinatura.verify(signature)) 
				return true;
			else 
				return false;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
		return false;
	}
	
	public X509Certificate Recuperar_Certificado_Digital(String path)
	{
		Certificate certificadoDigital = null;
		
		try {
			String certificadoPlainText = Ler_File(path);
			byte[] certificado_pem_format = String_to_Byte(Recuperar_PEM_Format_Ceritficado(certificadoPlainText));

			InputStream certificado_stream = new ByteArrayInputStream(certificado_pem_format);
		
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			certificadoDigital = certificateFactory.generateCertificate(certificado_stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (X509Certificate)certificadoDigital;
	}
	
	public X509Certificate Recuperar_Certificado_Digital(int id)
	{
		Certificate certificadoDigital = null;
		
		String certificado_Pem = BD.Get_Certificado_Digital_Usuario(id);
		byte[] certificado_pem_format = String_to_Byte(Recuperar_PEM_Format_Ceritficado(certificado_Pem));
		
		try {
			InputStream certificado_stream = new ByteArrayInputStream(certificado_pem_format);
			
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			certificadoDigital = certificateFactory.generateCertificate(certificado_stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return (X509Certificate)certificadoDigital;
	}
	
	private PrivateKey Recuperar_Private_Key(String path, String frase_secreta)
	{
		PrivateKey privateKey = null;
		
		//TODO - ajustar a leitura do file bin
		byte[] privateKey_crypto = Ler_File_Bin(path);
		//privateKey_crypto = String_to_Byte(Byte_to_String(privateKey_crypto).replace("\n", ""));
		if(privateKey_crypto == null)
		{
			BD.Log(4004, login_name);
			System.out.println("Caminho Invalido");
		}
		
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			SecureRandom secureRandom = new SecureRandom(String_to_Byte(frase_secreta));
			int keyBitSize = 56;

			keyGenerator.init(keyBitSize, secureRandom);
			
			SecretKey secretKey = keyGenerator.generateKey();
			
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
		    byte[] privateKey_base64 = cipher.doFinal(privateKey_crypto);
		    //remove todos os newLines
		    privateKey_base64 = Recuperar_Base64_PrivateKey(Byte_to_String(privateKey_base64).replace("\n", ""));

		    byte[] privateKey_text = Decodificar_BASE64_Byte(privateKey_base64);
		    
		    PKCS8EncodedKeySpec private_key_spec = new PKCS8EncodedKeySpec(privateKey_text);
		    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		    privateKey = keyFactory.generatePrivate(private_key_spec);
		    
		} catch (BadPaddingException e) {
			BD.Log(4005, login_name);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return privateKey;
	}

	public byte[] Ler_File_Bin(String path)
	{
		try {
			FileInputStream stream = new FileInputStream(path);
			byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			stream.close();
			
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return null;
	}
	
	public String Ler_File(String path)
	{
		try {
			FileInputStream stream = new FileInputStream(path);
			byte[] bytes = new byte[stream.available()];
			stream.read(bytes);
			stream.close();
			
			return Byte_to_String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return null;
	}
	
	/*public void Escrever_File_Bin(String data, String path)
	{
		try {
			 FileOutputStream fileOut = new FileOutputStream(path);
			 ObjectOutputStream os = new ObjectOutputStream(fileOut);
			 
			 os.writeChars(data);
			 
			 os.close();
			 fileOut.close();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void Escrever_File_Bin(byte[] data, String path)
	{
		try {
			 FileOutputStream fileOut = new FileOutputStream(path);
			 ObjectOutputStream os = new ObjectOutputStream(fileOut);
			 
			 os.write(data);
			 
			 os.close();
			 fileOut.close();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}*/
	
	private byte[] Recuperar_Base64_Certificado(String certificadoPlainText)
	{
		String certificado_base64 = null;
		
		try {
			String[] partes = certificadoPlainText.split("-----BEGIN CERTIFICATE-----");
			partes = partes[1].split("-----END CERTIFICATE-----");
			certificado_base64 = partes[0];
		} catch (Exception e) {
			e.printStackTrace();
		}

		return String_to_Byte(certificado_base64);
	}
	
	private byte[] Recuperar_Base64_PrivateKey(String privateKeyPlainText)
	{
		String privateKey_base64 = null;
		
		try {
			String[] partes = privateKeyPlainText.split("-----BEGIN PRIVATE KEY-----");
			partes = partes[1].split("-----END PRIVATE KEY-----");
			privateKey_base64 = partes[0];
		} catch (Exception e) {
			e.printStackTrace();
		}

		return String_to_Byte(privateKey_base64);
	}

	
	private String Recuperar_PEM_Format_Ceritficado(String certificadoPlainText)
	{
		String PemFormat = null;
		
		int index = certificadoPlainText.indexOf("-----BEGIN CERTIFICATE-----");
		
		return certificadoPlainText.substring(index);
	}
	
	private String Recuperar_Email_Certificado(X509Certificate certificado)
	{
		String subject = certificado.getSubjectDN().getName();
		
		String[] partes = subject.split(",");
		
		return partes[0].substring(partes[0].indexOf('=') + 1);
	}
	
	private String Recuperar_Nome_Certificado(X509Certificate certificado)
	{
		String subject = certificado.getSubjectDN().getName();
		
		String[] partes = subject.split(",");
		
		return partes[1].substring(partes[1].indexOf('=') + 1);
	}
	
	public byte[] Codificar_BASE64(String texto)
	{
		try {
			byte[] dado = texto.getBytes("UTF8");
			byte[] codigo = encoder_64.encode(dado);

			return codigo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public byte[] Decodificar_BASE64_Byte(byte[] codigo)
	{
		return decoder_64.decode(codigo);
	}
	
	public byte[] String_to_Byte(String data)
	{
		byte[] bytes = null;
		try {
			bytes = data.getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bytes;
	}
	
	public String Byte_to_String(byte[] bytes)
	{
		return new String(bytes);
	}
	 
    /*private BufferedReader openFile(String fileName)
    {
        try {
        	 return new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return null;
    }*/
    
    private boolean Testa_Senhas(ArrayList<Par_Digitos> par_digitos, String SALT, String senha_hash)
    {
    	String bitMask = null;
    	
    	for(int i = 0; i < Math.pow(2, par_digitos.size()); i++)
    	{
    		bitMask = Integer.toBinaryString(i);
    		
    		//coloca os 0 a esquerda
    		while(bitMask.length() < par_digitos.size())
    		{
    			bitMask = "0" + bitMask;
    		}
    		
    		StringBuffer sb = new StringBuffer();
    		for(int j = 0; j < bitMask.length(); j++)
    		{
    			if(bitMask.charAt(j) == '0')
    				sb.append(String.valueOf(par_digitos.get(j).n1));
    			else
    				sb.append(String.valueOf(par_digitos.get(j).n2));
    		}
    		
    		int senha = Integer.parseInt(sb.toString());
    		if(senha_hash.equals(Cryptografar_Senha(senha, SALT)))
    			return true;
    	}
    	
    	return false;
    }
    
	public void Efetuar_Login()
	{
		Usuario user = new Usuario(Recuperar_Nome_Certificado(ceritificadoDigital), 
								   Recuperar_Email_Certificado(ceritificadoDigital), 
								   ceritificadoDigital, 
								   BD.Get_Grupo_Usuario(id), 
								   privateKey);
		Usuario.SetInstance(user);
	}
}
