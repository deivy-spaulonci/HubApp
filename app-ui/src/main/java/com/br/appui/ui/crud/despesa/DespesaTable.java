package com.br.appui.ui.crud.despesa;

import com.br.appui.ui.util.Dialogs;
import com.br.appui.ui.util.Icons;
import com.br.appui.ui.util.TableRenderers;
import com.br.dto.response.DespesaResponseDTO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

public class DespesaTable extends JPanel {

    private final JTable table;
    private final DespesaTableModel model;
    public DespesaTable()
    {
        setLayout(new BorderLayout());
        model = new DespesaTableModel();
        table = new JTable(model);
        configurarTabela();
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
    }

    private void configurarTabela()
    {
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        // Ordenação
        TableRowSorter<DespesaTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(3, SortOrder.DESCENDING)));
        sorter.sort();

        // Ajustes de coluna
        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(0),0);
        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(1),180);
        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(4),300);
        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(6),250);

        TableRenderers.setColumnDefault(table,1);
        TableRenderers.setColumnDefault(table,2);
        TableRenderers.setColumnDate(table, 3);
        TableRenderers.setColumnDefault(table,4);
        TableRenderers.setColumnMoney(table, 5);
        TableRenderers.setColumnDefault(table,6);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    int modelRow = table.convertRowIndexToModel(row);
                    DespesaResponseDTO selecionado = model.getAt(modelRow);
                    System.out.println("Double click: " + selecionado);
                }
            }
        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemEdit = new JMenuItem("Editar", Icons.getIconEdit());
        JMenuItem menuItemDel = new JMenuItem("Excluir", Icons.getIconDelete());

        menuItemDel.addActionListener(e -> excluir());
        menuItemEdit.addActionListener(e -> editar());

        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemDel);
        table.setComponentPopupMenu(popupMenu);
    }

    void editar()
    {
        Window parent = SwingUtilities.getWindowAncestor(this);
        DespesaEditForm dialog = new DespesaEditForm(parent, getSelecionado().uuid());
        dialog.setVisible(true);
    }
    void excluir()
    {
        if(Dialogs.confirma(null, "Deseja excluir essa despesa?")){
            Dialogs.alertSucesso(this, "vai excluir: "+ getSelecionado().tipoDespesa().nome());
        }
    }

    public void setDados(List<DespesaResponseDTO> lista)
    {
        model.setDados(lista);
    }

    public DespesaResponseDTO getSelecionado()
    {
        int row = table.getSelectedRow();
        if (row < 0) return null;

        int modelRow = table.convertRowIndexToModel(row);
        return model.getAt(modelRow);
    }

    // ================= MODEL =================
    class DespesaTableModel extends AbstractTableModel
    {
        private final String[] colunas = {"UUID","Tipo","Fornecedor","Data","Pagamento","Valor","Observação"};
        private List<DespesaResponseDTO> dados = new ArrayList<>();
        public void setDados(List<DespesaResponseDTO> dados) {
            this.dados = dados;
            fireTableDataChanged();
        }
        public DespesaResponseDTO getAt(int row) {
            return dados.get(row);
        }

        @Override
        public int getRowCount() {
            return dados.size();
        }

        @Override
        public int getColumnCount() {
            return colunas.length;
        }

        @Override
        public String getColumnName(int column) {
            return colunas[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            DespesaResponseDTO d = dados.get(rowIndex);

            return switch (columnIndex) {
                case 0 -> d.uuid();
                case 1 -> d.tipoDespesa() != null ? d.tipoDespesa().toString() : "";
                case 2 -> d.fornecedor() != null ? d.fornecedor().toString() : "";
                case 3 -> d.dataPagamento();
                case 4 -> d.formaPagamento() != null ? d.formaPagamento().toString() : "";
                case 5 -> d.valor();
                case 6 -> d.observacao();
                default -> "";
            };
        }
    }
}
