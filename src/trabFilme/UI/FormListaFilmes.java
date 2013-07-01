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

public class FormListaFilmes extends JDialog {
	private JTable table;

	private final RegrasFilmes regrasFilmes;

	/**
	 * Create the dialog.
	 */
	public FormListaFilmes() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				// JOptionPane.showMessageDialog(null, "getFilmes!", "Msg",
				// JOptionPane.INFORMATION_MESSAGE);
				carregaDados();
			}
		});

		setTitle("Cadastro de Filmes");
		setBounds(100, 100, 572, 438);

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
		model.addColumn("idFilme");
		model.addColumn("Nome");
		model.addColumn("G�nero");
		model.addColumn("Ano");
		model.addColumn("Dura��o");

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(table, BorderLayout.CENTER);

		regrasFilmes = new RegrasFilmes();
	}

	void carregaDados() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		List<Filme> filmes = regrasFilmes.getFilmes();
		for (Filme f : filmes) {
			Object[] data = new Object[] { f.getIdFilme(), f.getNome(),
					f.getGenero(), f.getAnoLancamento(), f.getDuracao() };
			model.addRow(data);
		}

		table.setModel(model);
	}

	void Inclusao() {
		FormFilme frm = new FormFilme();
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

		Integer idFilme = (Integer) model.getValueAt(row, 0);
		FormFilme frm = new FormFilme();
		frm.SetIdFilme(idFilme);
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

		Integer idFilme = (Integer) model.getValueAt(row, 0);
		try {
			String msg = regrasFilmes.ExcluiFilme(idFilme);
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
