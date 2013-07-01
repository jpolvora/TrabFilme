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

public class FormGenero extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private int idGenero;
	private RegrasGeneros regrasGeneros;
	private JTextField txtDescricao;
	private JTextField txtIdGenero;

	/**
	 * Create the dialog.
	 */
	public FormGenero() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
					getDados();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		setTitle("Filme");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblIdGenero = new JLabel("idGenero");
		lblIdGenero.setBounds(10, 30, 57, 14);
		contentPanel.add(lblIdGenero);

		JLabel lblDescricao = new JLabel("Descri\u00E7\u00E3o");
		lblDescricao.setBounds(10, 71, 46, 14);
		contentPanel.add(lblDescricao);

		txtDescricao = new JTextField();
		txtDescricao.setText("Descri\u00E7\u00E3o");
		txtDescricao.setBounds(121, 71, 284, 20);
		contentPanel.add(txtDescricao);
		txtDescricao.setColumns(10);

		txtIdGenero = new JTextField();
		txtIdGenero.setEditable(false);
		txtIdGenero.setText("idGenero");
		txtIdGenero.setBounds(121, 30, 86, 20);
		contentPanel.add(txtIdGenero);
		txtIdGenero.setColumns(10);
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
		regrasGeneros = new RegrasGeneros();
	}

	public void SetIdGenero(int id) {
		this.idGenero = id;
	}

	private void getDados() throws SQLException {
		Genero genero = null;
		if (this.idGenero > 0) {
			genero = regrasGeneros.getGenero(this.idGenero);
		} else {
			genero = new Genero();
			genero.setIdGenero(regrasGeneros.getNextId());
		}

		// popúlar campos
		txtIdGenero.setText(String.valueOf(genero.getIdGenero()));
		txtDescricao.setText(genero.getDescricao());
	}

	Boolean SalvaRegistro() {
		Boolean update = false;

		try {
			Genero genero = new Genero();
			if (this.idGenero > 0) {
				update = true;
				genero.setIdGenero(this.idGenero);
			}

			genero.setDescricao(txtDescricao.getText());

			String msg = "";
			if (update) {
				msg = regrasGeneros.alterarGenero(genero);

			} else {
				msg = regrasGeneros.incluiGenero(genero);
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
