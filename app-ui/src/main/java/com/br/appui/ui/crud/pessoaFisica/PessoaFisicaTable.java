package com.br.appui.ui.crud.pessoaFisica;

import com.br.appui.ui.crud.despesa.DespesaTable;
import com.br.appui.ui.util.TableRenderers;
import com.br.dto.response.DespesaResponseDTO;
import com.br.dto.response.PessoaFisicaResponseDTO;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaTable extends JPanel {
    private final JTable table;
    private final PessoaFisicaTableModel model;

    public PessoaFisicaTable()
    {
        setLayout(new BorderLayout());
        model = new PessoaFisicaTableModel();
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
        TableRowSorter<PessoaFisicaTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));
        sorter.sort();

        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(0),0);
        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(1),500);
        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(2),200);

        TableRenderers.setColumnDefault(table,1);
        TableRenderers.setColumnDefault(table,2);
        /*
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
        * */
    }

    public void setDados(List<PessoaFisicaResponseDTO> lista)
    {
        model.setDados(lista);
    }

    public PessoaFisicaResponseDTO getSelecionado()
    {
        int row = table.getSelectedRow();
        if (row < 0) return null;

        int modelRow = table.convertRowIndexToModel(row);
        return model.getAt(modelRow);
    }

    class PessoaFisicaTableModel extends AbstractTableModel
    {
        private final String[] colunas = {"UUID","Nome","CPF"};
        private List<PessoaFisicaResponseDTO> dados = new ArrayList<>();
        public void setDados(List<PessoaFisicaResponseDTO> dados) {
            this.dados = dados;
            fireTableDataChanged();
        }

        public PessoaFisicaResponseDTO getAt(int row) {
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
            PessoaFisicaResponseDTO f = dados.get(rowIndex);

            return switch (columnIndex) {
                case 0 -> f.uuid();
                case 1 -> f.nome();
                case 2 -> f.cpf() != null ? f.cpf() : "";
                default -> "";
            };
        }
    }
}
