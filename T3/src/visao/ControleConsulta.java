package visao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import model.Arquivo;
import model.BD;
import model.Usuario;

public class ControleConsulta {

	
	public void callConsulta(JPanel cabecalho) {
		InterfaceConsulta ic = new InterfaceConsulta();
		ic.addCabecalho(cabecalho);
		ic.addCorpo1();
		addTotalConsulta(ic);
		ic.setVisible();
		addActLista(ic);
		addActSair(ic);
		
		
	}
	
	
	public void addActLista(InterfaceConsulta ic) {
		ic.getListar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BD.Log(8003, Usuario.getInstance().Get_Email());
				ArrayList<Arquivo> arquivos = Usuario.getInstance().Parse_Index(ic.getCampoPasta().getText());
				addArchivestoTable(arquivos, ic.getTable());
				ic.addTable();
				addActClick(ic.getTable(), arquivos);
			}
		});
	}
	
	public void addActSair(InterfaceConsulta ic) {
		ic.getSair().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BD.Log(8002, Usuario.getInstance().Get_Email());
				
				ic.getMenu().dispose();
				ControleMenu cm = new ControleMenu();
				cm.callMenu();
				
			}
		});
	}
	
	public void addArchivestoTable(ArrayList<Arquivo> arquivos , JTable table) {		
		DefaultTableModel model = new DefaultTableModel(); 
		table = new JTable(model); 
		String[] listRow = new String[4];
		// Create a couple of columns 
		model.addColumn("Nome_Codigo"); 
		model.addColumn("Nome_Secreto");
		model.addColumn("Dono"); 
		model.addColumn("Grupo"); 

		for (Arquivo arquivo : arquivos) {			
			listRow[0] = arquivo.Get_NomeCodigo();
			listRow[1] = arquivo.Get_NomeSecreto();
			listRow[2] = arquivo.Get_Dono();
			listRow[3] = arquivo.Get_Grupo();
			model.addRow(listRow);
			listRow = new String[4];
		}
	}
	
	public void addActClick(JTable table , ArrayList<Arquivo> arquivos) {	
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) 
			{
				int position = table.getSelectedRow();
				Arquivo a = arquivos.get(position);
				Usuario.getInstance().Decriptar_Arquivo(a);
			}
				
		});
	}
	
	public void addTotalConsulta(InterfaceConsulta ic) {
		// to do here
		ic.setTotalConsultas(ic.getTotalConsultas() + );
	}
	
}
