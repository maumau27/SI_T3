package model;

public class Usuario {
	
	public enum Grupos { ADIMINTRADOR , USUARIO }
	
	private String nome;
	private String email;
	private String certificado_digital;
	private Grupos grupo;
	
	
	public Usuario(String nome, String email, String certificado_digital, int grupo_id)
	{
		this.nome = nome;
		this.email = email;
		this.certificado_digital = certificado_digital;
		if(grupo_id == 1)
			this.grupo = Grupos.ADIMINTRADOR;
		else if(grupo_id == 2)
			this.grupo = Grupos.USUARIO;
		else
			System.out.println("Grupo Invalido");
	}
}
