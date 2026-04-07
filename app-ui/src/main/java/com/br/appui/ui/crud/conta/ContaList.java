package com.br.appui.ui.crud.conta;

import com.br.appui.ui.shared.InputDate;
import com.br.appui.ui.shared.SelectUI;
import com.br.appui.ui.util.Buttons;
import com.br.appui.ui.util.FormatUtil;
import com.br.appui.ui.util.LoadingDialog;
import com.br.appui.ui.util.SpringContext;
import com.br.dto.response.ContaResponseDTO;
import com.br.dto.response.TipoContaResponseDTO;
import com.br.filter.ContaFilter;
import com.br.service.ContaService;
import com.br.service.TipoContaService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.math.BigDecimal;

public class ContaList extends JPanel {
    private ContaService contaService;
    private TipoContaService tipoContaService;
    private ContaTable contaTable;
    private JPanel panelTop;
    private InputDate vencInicial = new InputDate();
    private InputDate vencFinal = new InputDate();
    private JButton btSearch;
    private JButton btClear;
    private JLabel lblTotal = new JLabel();
    private JLabel lblQtdRg = new JLabel();
    private SelectUI<TipoContaResponseDTO> selectFilterTipo;
    public ContaList() {
        this.contaService = SpringContext.getBean(ContaService.class);
        this.tipoContaService = SpringContext.getBean(TipoContaService.class);
        init();
    }

    void init(){
        initTop();
        contaTable = new ContaTable();

        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
        LoadingDialog dialog = new LoadingDialog(frame);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                loadContas();
                return null;
            }
            @Override
            protected void done() {
                dialog.dispose();
            }
        };

        worker.execute();
        dialog.setVisible(true); // bloqueia até terminar

        setLayout(new BorderLayout(5, 5));
        add(panelTop,BorderLayout.NORTH);
        add(contaTable,BorderLayout.CENTER);
    }

    void loadContas(){
        ContaFilter filter = ContaFilter.builder()
                .tipoConta(selectFilterTipo.getSelectedItemTyped())
                .vencimentoInicial(vencInicial.getDateTime())
                .vencimentoFinal(vencFinal.getDateTime())
                .build();
        var dados = contaService.findConta(filter);
        BigDecimal total = dados.stream().map(ContaResponseDTO::valor).reduce(BigDecimal.ZERO, BigDecimal::add);
        lblTotal.setText(FormatUtil.formatMoeda(total));
        lblQtdRg.setText(String.valueOf(dados.size()));
        contaTable.setDados(dados);
    }

    void initTop() {
        panelTop = new JPanel(new BorderLayout(10, 10));
        JPanel filterPanel = new JPanel(new MigLayout("gap 20, insets 20"));

        this.selectFilterTipo = new SelectUI<TipoContaResponseDTO>(tipoContaService.findAll(), true);

        btSearch = Buttons.getBtSearch();
        btSearch.addActionListener(e -> loadContas());
        btClear = Buttons.getBtClear();
        btClear.addActionListener(e -> cleanFilter());

        filterPanel.setBorder(new TitledBorder("Filtros"));
        filterPanel.add(new JLabel("Tipo Conas:"));
        filterPanel.add(selectFilterTipo,"wrap");
        filterPanel.add(new JLabel("Periodo:"));
        filterPanel.add(vencInicial,"split 2");
        filterPanel.add(vencFinal);
        filterPanel.add(btSearch,"split 2");
        filterPanel.add(btClear);

        Font font = new Font(getFont().getFontName(), Font.BOLD | Font.ITALIC, 26);
        lblTotal.setFont(font);
        lblTotal.setForeground(Color.DARK_GRAY);
        lblQtdRg.setFont(font);
        lblQtdRg.setForeground(Color.DARK_GRAY);

        Label total = new Label("Total: ");
        Label qtdRg = new Label("Registros: ");
        total.setFont(font);
        qtdRg.setFont(font);

        JPanel statusPanel = new JPanel(new MigLayout("gap 15, insets 20"));
        statusPanel.add(total);
        statusPanel.add(lblTotal, "wrap");
        statusPanel.add(qtdRg);
        statusPanel.add(lblQtdRg);

        panelTop.add(filterPanel,BorderLayout.WEST);
        panelTop.add(statusPanel,BorderLayout.CENTER);
    }

    void cleanFilter(){
        selectFilterTipo.setSelectedIndex(-1);
        vencInicial.setText("");
        vencFinal.setText("");
    }
}
