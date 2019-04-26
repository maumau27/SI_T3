import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class BD {
	
	static String url = "jdbc:mysql://localhost:3306/testebd?useTimezone=true&serverTimezone=UTC";
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
		try {
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery("SELECT id FROM usuarios WHERE Email ='" + email + "'");
		
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
		try {
		    Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery("SELECT senha FROM usuarios WHERE id =" + id);
		
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
}
