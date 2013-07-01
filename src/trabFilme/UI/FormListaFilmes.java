package trabFilme.UI;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import trabFilme.Negocios.*;
import trabFilme.Persistencia.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.table.TableModel;

public class FormListaFilmes extends JDialog {

	private final RegrasFilmes regrasFilmes;
	private JTable table;

	/**
	 * Create the dialog.
	 */
	public FormListaFilmes() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				carregaDados(null);
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
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultar();
			}
		});
		toolBar.add(btnConsultar);

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("idFilme");
		model.addColumn("Nome");
		model.addColumn("Gênero");
		model.addColumn("Ano");
		model.addColumn("Duração");

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);

		regrasFilmes = new RegrasFilmes();
	}

	void carregaDados(String filtro) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}

		List<Filme> filmes = regrasFilmes.getFilmes(filtro);
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
		carregaDados(null);
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
		carregaDados(null);
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
				"Confirma a exclusão deste filme ?");
		if (result > 0) {
			return;
		}

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Integer idFilme = (Integer) model.getValueAt(row, 0);
		try {
			String msg = regrasFilmes.ExcluiFilme(idFilme);
			if (msg != null && !msg.isEmpty()) {
				JOptionPane.showMessageDialog(null, msg, "Exclusão",
						JOptionPane.WARNING_MESSAGE);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		carregaDados(null);
	}

	void consultar() {
		String filtro = JOptionPane
				.showInputDialog("Digite o valor a ser pesquisado:");
		if (filtro != null)
			carregaDados(filtro);
		else
			carregaDados(null);

	}
}
