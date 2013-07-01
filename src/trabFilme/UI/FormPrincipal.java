package trabFilme.UI;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JPasswordField;
import javax.swing.JButton;

import trabFilme.Persistencia.Conexao;

public class FormPrincipal {

	private JFrame frmTrabfilmes;
	private JTextField txtServidor;
	private JTextField txtUsuario;
	private JPasswordField pwdSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormPrincipal window = new FormPrincipal();
					window.frmTrabfilmes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FormPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTrabfilmes = new JFrame();
		frmTrabfilmes.setTitle("TrabFilmes");
		frmTrabfilmes.setBounds(100, 100, 450, 300);
		frmTrabfilmes.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frmTrabfilmes.setJMenuBar(menuBar);

		JMenu mnCadastros = new JMenu("Cadastros");
		menuBar.add(mnCadastros);

		JMenuItem mntmGeneros = new JMenuItem("G\u00EAneros");
		mntmGeneros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbreCadastroGeneros();
			}
		});
		mnCadastros.add(mntmGeneros);

		JMenuItem mntmFilmes = new JMenuItem("Filmes");

		mntmFilmes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbreCadastroFilmes();
			}
		});
		mnCadastros.add(mntmFilmes);

		JPanel panel = new JPanel();
		frmTrabfilmes.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 2));

		// servidor
		JLabel lblServidor = new JLabel("Servidor");
		panel.add(lblServidor);

		txtServidor = new JTextField();
		txtServidor.setText("jdbc:mysql://localhost:3306/TrabFilme");
		panel.add(txtServidor);
		txtServidor.setColumns(10);

		// usuario
		JLabel lblUsuario = new JLabel("Usuário");
		panel.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setText("root");
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);

		// senha
		JLabel lblSenha = new JLabel("Senha");
		panel.add(lblSenha);

		pwdSenha = new JPasswordField();
		pwdSenha.setText("3306");
		panel.add(pwdSenha);

		// teste
		JLabel lblTeste = new JLabel("Teste");
		panel.add(lblTeste);

		JButton btnTestar = new JButton("Testar");
		btnTestar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfiguraConexao();
				TestaConexao();
			}
		});
		panel.add(btnTestar);

		ConfiguraConexao();
	}
	
	void AbreCadastroGeneros() {
		FormListaGeneros frm = new FormListaGeneros();
		frm.setModalityType(ModalityType.APPLICATION_MODAL);
		frm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frm.setVisible(true);
	}

	void AbreCadastroFilmes() {
		FormListaFilmes frm = new FormListaFilmes();
		frm.setModalityType(ModalityType.APPLICATION_MODAL);
		frm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frm.setVisible(true);
	}

	void ConfiguraConexao() {
		Conexao.servidor = txtServidor.getText();
		Conexao.usuario = txtUsuario.getText();
		Conexao.senha = pwdSenha.getPassword();
	}
	
	void TestaConexao() {
		try {
			java.sql.Connection conn = Conexao.getConexao();
			if (conn != null) {
				JOptionPane.showMessageDialog(null,
						"Conexão ao banco realizada com sucesso!", "Msg",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane
						.showMessageDialog(
								null,
								"Falha ao conectar ao servidor. Verifique servidor, usuário e senha!",
								"Msg", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Msg",
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
