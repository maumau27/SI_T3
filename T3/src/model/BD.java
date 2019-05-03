package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BD {
	
	static String url = "jdbc:mysql://localhost:3306/testbd?useTimezone=true&serverTimezone=UTC";
	static String user = "root";
	static String password  = "";
	
	static String query = "select * from teste";
	
	private static Connection connection;
	
	public static void Estabelecer_Conexao()
	{
		try {
			
			connection = DriverManager.getConnection(url, user, password);
		    /*Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		
		    if (rs.next()) {
		        
		        System.out.println(rs.getString(1));
		    }*/
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
		
		System.out.println("Connexão estabelecida");
	}

	public static int Get_Id_by_Email(String email)
	{
		String query = "SELECT id FROM usuarios WHERE Email ='" + email + "'";
		ResultSet rs = Run_Query(query);
		
		try {
		    if(rs.first())
		    {
		    	return rs.getInt(1);
		    }
		    else
		    	return -1;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
		
		return -1;
	}
	
	public static int Get_Senha_by_Id(int id)
	{
		String query = "SELECT senha FROM usuarios WHERE id =" + id;
		ResultSet rs = Run_Query(query);
		
		try {
			if(rs.first())
			{
				return rs.getInt(1);
			}
			else
				return -1;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
		
		return -1;
	}

	public static boolean Usuario_Existe(String login_name)
	{
		String query = "SELECT id FROM usuarios WHERE login_name ='" + login_name + "'";
		ResultSet rs = Run_Query(query);
		
		try {
			if(rs.first())
				return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
		
		return false;
	}
	
	public static void Cadastrar_Usuario(String email, int grupo, int senha)
	{
		String query = "INSERT INTO usuario (email, senha,id_grupo) VALUES (" + email + "," + senha + "," + grupo + ",)";
		ResultSet rs = Run_Query(query);
	}
	
	public static void Cadastrar_Usuario(String email, int grupo, String senha)
	{
		String query = "INSERT INTO usuario (email, senha, id_grupo) VALUES (" + email + "," + senha + "," + grupo + ",)";
		ResultSet rs = Run_Query(query);
	}
	
	public static String Get_Grupo_Usuario(int id)
	{
		String query = "SELECT nome FROM grupo INNER JOIN usuarios ON usuarios.id_grupo = grupo.GID where usuarios.id =" + id;
		ResultSet rs = Run_Query(query);
		try {
			if(rs.next())
				return rs.getString(1);
		
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
		
		return null;
	}
	
	public static void Adicionar_Acesso_Usuario(int id)
	{
		String query = "UPDATE usuarios SET total_acessos = total_acessos + 1  WHERE id =" + id;
		ResultSet rs = Run_Query(query);
	}
	
	public static void Bloquear_Usuario(int id)
	{
		String query = "UPDATE usuarios SET data_bloqueio = adddate(CURRENT_TIMESTAMP(), INTERVAL 2 MINUTE) WHERE id =" + id;
		ResultSet rs = Run_Query(query);
	}
	
	public static boolean Usuario_Bloqueado(int id)
	{
		String query = "SELECT id FROM usuarios WHERE data_bloqueio <= CURRENT_TIMESTAMP() and id =" + id;
		ResultSet rs = Run_Query(query);
		try {
			if(rs.next())
				return false;
		
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
		
		return true;
	}
	
	private static ResultSet Run_Query(String query)
	{
		try {
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery(query);
		    return rs;
		
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
		
		return null;
	}
	
	public static void Log(int codigo)
	{
		try {
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery("INSERT INTO registros (codigo) VALUES (" + codigo + ")");
		
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
	}
	
	public static void Log(int codigo, int id_usuario)
	{
		try {
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery("INSERT INTO registros (codigo, id_usuario) VALUES (" + codigo + "," + id_usuario + ")");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Connexão Falhou");
		}
	}
}
