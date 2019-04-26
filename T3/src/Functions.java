import java.util.ArrayList;
import java.util.Random;

public class Functions {

	
	public static ArrayList<Par_Digitos> Gerar_Set_Pares()
	{
		ArrayList<Par_Digitos> par_digitos = new ArrayList<Par_Digitos>();
		ArrayList<Integer> digitos = new ArrayList<Integer>();
		digitos.add(0);
		digitos.add(1);
		digitos.add(2);
		digitos.add(3);
		digitos.add(4);
		digitos.add(5);
		digitos.add(6);
		digitos.add(7);
		digitos.add(8);
		digitos.add(9);
		
		for(int i=0; i < 5; i++)
		{
			Random rnd = new Random();
			
			int index = rnd.nextInt(digitos.size());
			int n1 = digitos.get(index);
			digitos.remove(index);
			index = rnd.nextInt(digitos.size());
			int n2 = digitos.get(index);
			digitos.remove(index);
			
			par_digitos.add(new Par_Digitos(n1, n2));
		}
		
		return par_digitos;
	}
}
