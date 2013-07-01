package trabFilme.UI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import trabFilme.Negocios.*;
import trabFilme.Persistencia.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormFilme extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int idFilme;
	private RegrasFilmes regrasFilmes;
	private RegrasGeneros regrasGeneros;
	private JTextField txtNome;
	private JTextField txtIdfilme;
	private JTextField txtAno;
	private JTextField txtDuracao;
	private JComboBox cmbGeneros;

	/**
	 * Create the dialog.
	 */
	public FormFilme() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					getDados();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e.getMessage(), "Msg",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		setTitle("Filme");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblIdfilme = new JLabel("idFilme");
		lblIdfilme.setBounds(10, 30, 46, 14);
		contentPanel.add(lblIdfilme);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 71, 46, 14);
		contentPanel.add(lblNome);

		txtNome = new JTextField();
		txtNome.setText("Nome");
		txtNome.setBounds(121, 71, 284, 20);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);

		txtIdfilme = new JTextField();
		txtIdfilme.setEditable(false);
		txtIdfilme.setText("idFilme");
		txtIdfilme.setBounds(121, 30, 86, 20);
		contentPanel.add(txtIdfilme);
		txtIdfilme.setColumns(10);

		JLabel lblGnero = new JLabel("G\u00EAnero");
		lblGnero.setBounds(10, 108, 46, 14);
		contentPanel.add(lblGnero);

		cmbGeneros = new JComboBox();
		cmbGeneros.setBounds(121, 108, 284, 20);
		contentPanel.add(cmbGeneros);

		JLabel lblAnoLanamento = new JLabel("Ano Lan\u00E7amento");
		lblAnoLanamento.setBounds(10, 147, 98, 14);
		contentPanel.add(lblAnoLanamento);

		txtAno = new JTextField();
		txtAno.setText("Ano");
		txtAno.setBounds(121, 144, 86, 20);
		contentPanel.add(txtAno);
		txtAno.setColumns(10);

		JLabel lblDurao = new JLabel("Dura\u00E7\u00E3o");
		lblDurao.setBounds(10, 188, 46, 14);
		contentPanel.add(lblDurao);

		txtDuracao = new JTextField();
		txtDuracao.setText("Duracao");
		txtDuracao.setBounds(121, 185, 86, 20);
		contentPanel.add(txtDuracao);
		txtDuracao.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (SalvaRegistro())
							setVisible(false);
						else {
							JOptionPane.showMessageDialog(null,
									"Erro ao gravar dados", "Msg",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		regrasFilmes = new RegrasFilmes();
		regrasGeneros = new RegrasGeneros();
	}

	public int getIdFilme() {
		return this.idFilme;
	}

	public void SetIdFilme(int idFilme) {
		this.idFilme = idFilme;
	}

	private void getDados() throws Exception {
		Filme filme = null;
		if (this.idFilme > 0) {
			filme = regrasFilmes.getFilme(this.idFilme);
		} else {
			filme = new Filme();
			filme.setIdFilme(regrasFilmes.getNextId());
		}

		// popúlar campos
		txtIdfilme.setText(String.valueOf(filme.getIdFilme()));
		txtNome.setText(filme.getNome());
		txtAno.setText(String.valueOf(filme.getAnoLancamento()));
		txtDuracao.setText(String.valueOf(filme.getDuracao()));

		// popular o combobox e selecionar o genero

		Integer cmbIndex = 0, laco = 0;

		List<Genero> listaGeneros = regrasGeneros.getGeneros(null);
		if (listaGeneros.isEmpty()) {
			throw new Exception(
					"Tabela de Gêneros não contém dados. É necessário cadastrar gêneros antes de incluir filmes!");
		}
		for (Genero g : listaGeneros) {
			if (g.getIdGenero() == filme.getIdGenero())
				cmbIndex = laco;
			cmbGeneros.addItem(g);
			laco++;
		}

		cmbGeneros.setSelectedIndex(cmbIndex);
	}

	Boolean SalvaRegistro() {
		Boolean update = false;

		try {
			Filme filme = new Filme();
			if (this.idFilme > 0) {
				update = true;
				filme.setIdFilme(this.idFilme);
			}

			filme.setAnoLancamento(Integer.valueOf(txtAno.getText()));
			filme.setDuracao(Integer.valueOf(txtDuracao.getText()));

			Genero g = (Genero) cmbGeneros.getSelectedItem();
			filme.setGenero(g);

			filme.setNome(txtNome.getText());

			String msg = "";
			if (update) {
				msg = regrasFilmes.AlteraFilme(filme);

			} else {
				msg = regrasFilmes.IncluiFilme(filme);
			}

			if (msg != null && !msg.isEmpty()) {
				JOptionPane.showMessageDialog(null, msg, "Msg",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
