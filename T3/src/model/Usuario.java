package model;

import java.io.PrintWriter;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

public class Usuario {

	private static Usuario instance;

	public static Usuario getInstance()
	{
		return instance;
	}
	public static void SetInstance(Usuario user)
	{
		instance = user;
	}

	private String nome;
	private String email;
	private X509Certificate certificado_digital;
	private String grupo;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public Usuario(String nome, String email, X509Certificate certificado_digital, String grupo, PrivateKey privateKey)
	{
		this.certificado_digital = certificado_digital;
		this.nome = nome;
		this.email = email;
		this.privateKey = privateKey;
		this.publicKey = certificado_digital.getPublicKey();
		this.grupo = grupo;
	}

	public String Decriptar_File(String path, String name)
	{
		byte[] index_crypt = Autentificador.getInstance().Ler_File_Bin(path + "\\" + name + ".enc");
		byte[] envelope_digital = Autentificador.getInstance().Ler_File_Bin(path + "\\" + name + ".env");
		byte[] assinatura_digital = Autentificador.getInstance().Ler_File_Bin(path + "\\" + name + ".asd");

		//decripta o envelope digital para recuperar a semente
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] seed = cipher.doFinal(envelope_digital);

			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			SecureRandom secureRandom = new SecureRandom(seed);
			int keyBitSize = 56;

			keyGenerator.init(keyBitSize, secureRandom);

			SecretKey secretKey = keyGenerator.generateKey();

			cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			//Decriptando o index_data
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] index_data_bytes = cipher.doFinal(index_crypt);
			String index_data = Autentificador.getInstance().Byte_to_String(index_data_bytes);
		
			Signature sig = Autentificador.getInstance().Gerar_AssinaturaDigital("MD5withRSA");
			byte[] signature = null;

			sig.initSign(privateKey);
			sig.update(index_data_bytes);
			signature = sig.sign();
			
			String signature_hex = Functions.Byte_to_Hex(signature);
			String assinatura_digital_hex = Functions.Byte_to_Hex(assinatura_digital);

			//TODO - ajeitar a valida��o
			if(signature_hex.equals(assinatura_digital_hex))
				return index_data;
			else 
			{
				JOptionPane.showMessageDialog(null, "Integridade e Autenticidade Corrompida!", "Erro", JOptionPane.ERROR_MESSAGE);
				System.out.println("Integridade e Autenticidade Corrompida!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public void Decriptar_Arquivo(Arquivo arquivo)
	{
		if(Tem_Acesso(arquivo))
		{
			String file_data = Decriptar_File(arquivo.Get_Path(), arquivo.Get_NomeCodigo());
			if(file_data != null)
			{
				try {
					PrintWriter out = new PrintWriter(arquivo.Get_Path() + "\\" + arquivo.Get_NomeSecreto());
					out.println(file_data);
					out.close();
					System.out.print("Arquivo Decriptado e Salvo com Sucesso!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<Arquivo> Parse_Index(String path)
	{
		String index_data = Decriptar_File(path, "index");
		if(index_data == null)
		{
			JOptionPane.showMessageDialog(null, "Index Invalido", "Erro", JOptionPane.ERROR_MESSAGE);
			System.out.println("Index Invalido");
		}
		
		ArrayList<Arquivo> sistema_files = new ArrayList<Arquivo>();

		String[] arquivos = index_data.split("\n");

		for(String arquivo : arquivos)
		{
			String[] partes = arquivo.split(" ");
			String nome_codigo = partes[0];
			String nome_secreto = partes[1];
			String dono = partes[2];
			String grupo = partes[3];
			sistema_files.add(new Arquivo(nome_codigo, nome_secreto, dono, grupo, path));
		}

		return sistema_files;
	}

	public boolean Tem_Acesso(Arquivo arquivo)
	{
		if(arquivo.Get_Dono().equals(email) || arquivo.Get_Grupo().equals(grupo))
	      return true;
		return false;
	}
}
