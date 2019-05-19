package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.Arquivo;
import model.BD;
import model.Usuario;

public class InterfaceConsulta {

	private JFrame menu;
	private JPanel cabecalho , corpo1 , corpo2;
	private JTextField campoPasta;
	private JButton listar , sair;
	private JTable table;
	private JLabel totalConsultas;
	
	
	public InterfaceConsulta() {
		BD.Log(8001, Usuario.getInstance().Get_Email());
		
		menu = new JFrame();
		menu.setLayout(new BorderLayout());
		menu.setSize(600,600);
		menu.setLocationRelativeTo(null);
		setConfigurationsOfPanels();
		menu.add(corpo2 , BorderLayout.SOUTH);
		corpo2.setLayout(new BoxLayout(corpo2, BoxLayout.Y_AXIS));
		addText();
		setButtons();
		menu.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	BD.Log(1002);
                System.out.println("Sistema sendo fechado pelo usuario");
                System.exit(0);
            }
        });
	}
	
	public void setConfigurationsOfPanels() {
		cabecalho = new JPanel();  
		cabecalho.setLayout(new BorderLayout());
		cabecalho.setPreferredSize(new Dimension(100,100));
	    corpo1 = new JPanel();
	    corpo1.setLayout(new BorderLayout());
	    corpo1.setPreferredSize(new Dimension(200,200));
	    corpo2 = new JPanel();
	    corpo2.setLayout(new BorderLayout());
	    corpo2.setPreferredSize(new Dimension(200,200));
	    
	}
	
	public void addTable() {
		corpo2.add(table);
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTextField getCampoPasta() {
		return campoPasta;
	}

	public void setCampoPasta(JTextField campoPasta) {
		this.campoPasta = campoPasta;
	}

	public void addCabecalho(JPanel cabecalho) {
		this.cabecalho = cabecalho;
		menu.add(cabecalho, BorderLayout.NORTH);
	}
	
	public void addCorpo1() {
		corpo1 = new JPanel();
		totalConsultas = new JLabel("Total de consultas do usu�rio:");
		corpo1.add(totalConsultas);
		menu.add(corpo1 , BorderLayout.CENTER);
	}
	
	public JLabel getTotalConsultas() {
		return totalConsultas;
	}

	public void setTotalConsultas(JLabel totalConsultas) {
		this.totalConsultas = totalConsultas;
	}

	public void addText() {
		campoPasta = new JTextField();
		campoPasta.setDocument(new TamanhoFixoJText(255));
		corpo2.add(campoPasta , BorderLayout.NORTH);
	}
	

	public void setButtons() {
		listar = new JButton("Listar");
		sair = new JButton("Voltar parao Menu");
		listar.setPreferredSize(new Dimension(50,50));
		sair.setPreferredSize(new Dimension(50,50));
		corpo2.add(listar);
		corpo2.add(sair);
		
	}
	
	public JFrame getMenu() {
		return menu;
	}
	public void setMenu(JFrame menu) {
		this.menu = menu;
	}
	public JButton getListar() {
		return listar;
	}
	public void setListar(JButton listar) {
		this.listar = listar;
	}
	public JButton getSair() {
		return sair;
	}
	public void setSair(JButton sair) {
		this.sair = sair;
	}
	public void setVisible() {
		menu.setVisible(true);
	}
}
