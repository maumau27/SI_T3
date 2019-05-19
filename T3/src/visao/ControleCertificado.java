package visao;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.X509Certificate;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

import model.Autentificador;
import model.BD;

public class ControleCertificado {

	public void callControllerCertificado() {
		InterfaceCertificado ic = new InterfaceCertificado();
		addActs(ic);
		ic.setVisible();
		
	}

	
	public void addActs(InterfaceCertificado ic) {
		addActbtAbrir(ic);
		addActSend(ic);
	}
	
	public void addActbtAbrir(InterfaceCertificado ic) {
		ic.getBtAbrir().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				abrir();
				
			}
		});
	}
	
	private void abrir() {
		try {			
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = jfc.showOpenDialog(null);
			// int returnValue = jfc.showSaveDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				System.out.println(selectedFile.getAbsolutePath());
				X509Certificate certificate = Autentificador.getInstance().Recuperar_Certificado_Digital(selectedFile.getAbsolutePath());
			}

		} catch (Exception e1) {			
			e1.printStackTrace();
		}
	}
	
	public void addActSend(InterfaceCertificado ic) {
		ic.getSend().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Validar dados
				
				// Verificar se eh adm ou usuario 
				
				BD.Log(4002, Autentificador.getInstance().Get_LoginName());
				ic.getScreen().dispose();
				ControleMenu cm = new ControleMenu();
				cm.callMenu();
				
			}
		});
		
		
	}
}
