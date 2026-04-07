package com.br.appui.ui.crud.conta;

import com.br.appui.ui.crud.despesa.DespesaEditForm;
import com.br.appui.ui.util.*;
import com.br.dto.response.ContaResponseDTO;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContaTable extends JPanel{

    private final JTable table;
    private final ContaTableModel model;
    private JPanel detailPanel;
    private ContaResponseDTO selecionado;
    private JLabel detailNumero;
    private JTextField detailTipo = new JTextField();
    private JTextField detailBarras = new JTextField();
    private JTextField detailEmissao = new JTextField();
    private JTextField detailVencimento = new JTextField();
    private JTextField detailParcelas = new JTextField();
    private JTextField detailValor = new JTextField();
    private JTextField detailFormaPgto = new JTextField();
    private JTextField detailDataPgto = new JTextField();
    private JTextField detailValorPgto = new JTextField();
    private JTextField detailObs = new JTextField();
    private JLabel detailStatus = new JLabel();
    public ContaTable() {
        setLayout(new BorderLayout());

        model = new ContaTableModel();
        table = new JTable(model);

        configurarTabela();
        init();
        JScrollPane scroll = new JScrollPane(table);

        add(scroll,  BorderLayout.CENTER);
        add(detailPanel,  BorderLayout.EAST);
    }

    void init(){
        JLabel lblPgto = new JLabel("Pagamento ");
        lblPgto.setFont(new Font(lblPgto.getFont().getFontName(), Font.BOLD | Font.ITALIC, 20));

        detailPanel = new JPanel(new MigLayout("insets 24, gapy 15"));
        detailPanel.add(detailStatus,"gaptop 20, span");
        detailPanel.add(new JLabel("Conta: "),"gaptop 20");
        detailPanel.add(detailTipo,"wrap");
        detailPanel.add(new JLabel("Cod. Barras: "));
        detailPanel.add(detailBarras,"wrap");
        detailPanel.add(new JLabel("Emissão: "));
        detailPanel.add(detailEmissao,"wrap");
        detailPanel.add(new JLabel("Vencimento: "));
        detailPanel.add(detailVencimento,"wrap");
        detailPanel.add(new JLabel("Parcelas: "));
        detailPanel.add(detailParcelas,"wrap");
        detailPanel.add(new JLabel("Valor: "));
        detailPanel.add(detailValor,"wrap");
        detailPanel.add(lblPgto, "gaptop 30, span");
        detailPanel.add(new JLabel("Forma Pgto: "));
        detailPanel.add(detailFormaPgto, "wrap");
        detailPanel.add(new JLabel("Data Pgto: "));
        detailPanel.add(detailDataPgto, "wrap");
        detailPanel.add(new JLabel("Valor Pgto: "));
        detailPanel.add(detailValorPgto, "wrap");
        detailPanel.add(new JLabel("Observação: "),"gaptop 30");
        detailPanel.add(detailObs, "wrap");

        for (Component c :  detailPanel.getComponents()){
            if(c instanceof JTextField){
                JTextField campo = (JTextField) c;
                campo.setEditable(false);
                campo.setColumns(33);
                campo.setFont(new Font(campo.getFont().getFontName(), Font.BOLD | Font.ITALIC, 18));
                campo.setBorder(new EmptyBorder(1, 1, 1, 1));
            }
        }
    }

    void setStatusContaDetail(int opc){
        detailStatus.setFont(new Font(detailStatus.getFont().getFontName(), Font.BOLD | Font.ITALIC, 25));
        switch (opc){
            case -1: detailStatus.setIcon(Icons.getIconError()); detailStatus.setForeground(AppColors.CRIMSON); break;
            case 0: detailStatus.setIcon(Icons.getIconClock()); detailStatus.setForeground(AppColors.ORANGE); break;
            case 1: detailStatus.setIcon(Icons.getIconFoldeOpen()); detailStatus.setForeground(AppColors.SEA_GREEN); break;
            case 2: detailStatus.setIcon(Icons.getIconCalendarCheck()); detailStatus.setForeground(AppColors.CORNFLOWER_BLUE); break;
        }

        detailStatus.setText(selecionado.getStatus());
    }

    private void configurarTabela() {
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        TableRowSorter<ContaTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setSortKeys(List.of(new RowSorter.SortKey(3, SortOrder.DESCENDING)));
        sorter.sort();

        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(0),0);
        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(4),120);
        TableRenderers.setColumnWidth(table.getColumnModel().getColumn(5), 120);

        TableRenderers.setColumnDefault(table, 1);
        TableRenderers.setColumnDate(table, 2);
        TableRenderers.setColumnDate(table, 3);
        TableRenderers.setColumnCenter(table, 4);
        TableRenderers.setColumnCenter(table, 5);
        TableRenderers.setColumnMoney(table, 6);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int modelRow = table.convertRowIndexToModel(row);
                    selecionado = model.getAt(modelRow);
                    for (Component c :  detailPanel.getComponents()){
                        if(c instanceof JTextField){
                            JTextField campo = (JTextField) c;
                            campo.setText("");
                        }
                    }
                    detailTipo.setText(selecionado.tipoConta().nome());
                    detailBarras.setText(selecionado.codigoBarras());
                    detailEmissao.setText(FormatUtil.formatData(selecionado.emissao()));
                    detailVencimento.setText(FormatUtil.formatData(selecionado.vencimento()));
                    detailParcelas.setText(selecionado.parcela()+ "/" + selecionado.totalParcela());
                    detailValor.setText(FormatUtil.formatMoeda(selecionado.valor()));
                    detailDataPgto.setText(FormatUtil.formatData(selecionado.dataPagamento()));
                    if(selecionado.formaPagamento()!=null) {
                        detailFormaPgto.setText(
                                selecionado.formaPagamento().getModalidade().getDescricao() +
                                        (selecionado.formaPagamento().getInstituicao() == null ? "" : " - " + selecionado.formaPagamento().getInstituicao())

                        );
                        detailValorPgto.setText(FormatUtil.formatMoeda(selecionado.valorPago()));
                        detailObs.setText(selecionado.observacao());
                    }
                    setStatusContaDetail(selecionado.getIntStatus());
                }
            }
        });

//        table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 1 && table.getSelectedRow() != -1) {
//                    int row = table.getSelectedRow();
//                    int modelRow = table.convertRowIndexToModel(row);
//                    selecionado = model.getAt(modelRow);
//
//                }
//            }
//        });

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemEdit = new JMenuItem("Editar", Icons.getIconEdit());
        JMenuItem menuItemSetPg = new JMenuItem("Pagar", Icons.getIconCheck());
        JMenuItem menuItemDel = new JMenuItem("Excluir", Icons.getIconDelete());

        menuItemEdit.addActionListener(e -> editar());
        menuItemDel.addActionListener(e -> excluir());
        menuItemSetPg.addActionListener(e -> pagar());

        popupMenu.add(menuItemEdit);
        popupMenu.add(menuItemSetPg);
        popupMenu.add(menuItemDel);
        table.setComponentPopupMenu(popupMenu);
    }

     void pagar()
     {
         Window parent = SwingUtilities.getWindowAncestor(this);
         ContaPayForm dialog = new ContaPayForm(parent, getSelecionado().uuid());
         dialog.setVisible(true);
     }
    void editar()
    {
        Window parent = SwingUtilities.getWindowAncestor(this);
        ContaEditForm dialog = new ContaEditForm(parent, getSelecionado().uuid());
        dialog.setVisible(true);
    }
    void excluir()
    {
        if(Dialogs.confirma(null, "Deseja excluir essa conta?")){
            Dialogs.alertSucesso(this, "vai excluir: "+ getSelecionado().tipoConta().nome());
        }
    }

    public void setDados(List<ContaResponseDTO> lista) {
        model.setDados(lista);
    }

    public ContaResponseDTO getSelecionado() {
        int row = table.getSelectedRow();
        if (row < 0) return null;

        int modelRow = table.convertRowIndexToModel(row);
        return model.getAt(modelRow);
    }

    class ContaTableModel extends AbstractTableModel {
        private final String[] colunas = {"UUID","Conta","Emissão","Vencimento","Parcela","Total parcela","Valor"};

        private List<ContaResponseDTO> dados = new ArrayList<>();

        public void setDados(List<ContaResponseDTO> dados) {
            this.dados = dados;
            fireTableDataChanged();
        }

        public ContaResponseDTO getAt(int row) {
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
            ContaResponseDTO c = dados.get(rowIndex);

            return switch (columnIndex) {
                case 0 -> c.uuid();
                case 1 -> c.tipoConta();
                case 2 -> c.emissao();
                case 3 -> c.vencimento();
                case 4 -> c.parcela();
                case 5 -> c.totalParcela();
                case 6 -> c.valor();
                default -> "";
            };
        }
    }
}
