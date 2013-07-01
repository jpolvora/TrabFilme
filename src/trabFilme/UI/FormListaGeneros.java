package trabFilme.UI;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import trabFilme.Negocios.*;
import trabFilme.Persistencia.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;

public class FormListaGeneros extends JDialog {

	private JTable table;

	private final RegrasGeneros regrasGeneros;

	/**
	 * Create the dialog.
	 */
	public FormListaGeneros() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				carregaDados();
			}
		});

		setTitle("Cadastro de G�neros");
		setBounds(100, 100, 574, 439);

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);

		JButton btnIncluir = new JButton("Incluir");
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Inclusao();
			}
		});
		toolBar.add(btnIncluir);

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alteracao();
			}
		});
		toolBar.add(btnAlterar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Exclusao();
			}
		});
		toolBar.add(btnExcluir);

		JButton btnConsultar = new JButton("Consultar");
		toolBar.add(btnConsultar);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("idGenero");
		model.addColumn("Descri��o");

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(table, BorderLayout.CENTER);

		regrasGeneros = new RegrasGeneros();
	}

	void carregaDados() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		List<Genero> generos = regrasGeneros.getGeneros();
		for (Genero g : generos) {
			Object[] data = new Object[] { g.getIdGenero(), g.getDescricao() };
			model.addRow(data);
		}

		table.setModel(model);
	}

	void Inclusao() {
		FormGenero frm = new FormGenero();
		frm.setModalityType(ModalityType.APPLICATION_MODAL);
		frm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frm.setVisible(true);
		carregaDados();
	}

	void Alteracao() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null,
					"Selecione uma linha para ALTERAR !", "Msg",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Integer id = (Integer) model.getValueAt(row, 0);
		FormGenero frm = new FormGenero();
		frm.SetIdGenero(id);
		frm.setModalityType(ModalityType.APPLICATION_MODAL);
		frm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frm.setVisible(true);
		carregaDados();
	}

	void Exclusao() {

		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null,
					"Selecione uma linha para EXCLUIR !", "Msg",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int result = JOptionPane.showConfirmDialog(this,
				"Confirma a exclus�o deste filme ?");
		if (result > 0) {
			return;
		}

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Integer id = (Integer) model.getValueAt(row, 0);
		try {
			String msg = regrasGeneros.ExcluiGenero(id);
			if (msg != null && !msg.isEmpty()) {
				JOptionPane.showMessageDialog(null, msg, "Exclus�o",
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		carregaDados();
	}
}
