package com.br.appui.ui.crud.despesa;

import com.br.appui.ui.shared.AutoCompleteFornec;
import com.br.appui.ui.shared.InputDate;
import com.br.appui.ui.shared.SelectUI;
import com.br.appui.ui.util.Buttons;
import com.br.appui.ui.util.FormatUtil;
import com.br.appui.ui.util.LoadingDialog;
import com.br.appui.ui.util.SpringContext;
import com.br.dto.response.DespesaResponseDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.entity.TipoDespesaEnum;
import com.br.filter.DespesaFilter;
import com.br.service.DespesaService;
import com.br.service.FormaPagamentoService;
import com.br.service.PessoaService;
import com.br.service.TipoDespesaService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.math.BigDecimal;

public class DespesaList extends JPanel {
    private TipoDespesaService tipoDespesaService;
    private PessoaService pessoaService;
    private FormaPagamentoService formaPagamentoService;
    private DespesaService despesaService;
    private JPanel panelTop;
    private DespesaTable despesaTable;
    private SelectUI<TipoDespesaResponseDTO> selectFilterTipo;
    private AutoCompleteFornec autoCompleteFornec;
    private SelectUI<FormaPagamentoResponseDTO> selectFilterFormaPgto;
    private InputDate inicio = new InputDate();
    private InputDate fim = new InputDate();
    private JButton btSearch;
    private JButton btClear;
    private JLabel lblTotal = new JLabel();
    private JLabel lblQtdRg = new JLabel();

    public DespesaList()
    {
        this.tipoDespesaService = SpringContext.getBean(TipoDespesaService.class);
        this.pessoaService = SpringContext.getBean(PessoaService.class);
        this.formaPagamentoService = SpringContext.getBean(FormaPagamentoService.class);
        this.despesaService = SpringContext.getBean(DespesaService.class);
        init();
    }
    void init()
    {
        initTop();
        despesaTable = new DespesaTable();

        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
        LoadingDialog dialog = new LoadingDialog(frame);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                loadDespesas();
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
        add(despesaTable,BorderLayout.CENTER);
    }

    void loadDespesas(){
        TipoDespesaEnum tipoDespesaEnum = (selectFilterTipo.getSelectedItemTyped()!=null ?
                TipoDespesaEnum.forValues(selectFilterTipo.getSelectedItemTyped().value()) : null);

        DespesaFilter filter = DespesaFilter.builder()
                .tipoDespesa(tipoDespesaEnum)
                .fornecedor(autoCompleteFornec.getSelectedItemTyped())
                .formaPagamento(selectFilterFormaPgto.getSelectedItemTyped())
                .inicio(inicio.getDateTime())
                .termino(fim.getDateTime())
                .build();
         var dados = despesaService.all(filter);
        BigDecimal total = dados.stream().map(DespesaResponseDTO::valor).reduce(BigDecimal.ZERO, BigDecimal::add);
        lblTotal.setText(FormatUtil.formatMoeda(total));
        lblQtdRg.setText(String.valueOf(dados.size()));
        despesaTable.setDados(dados);
    }

    void initTop(){
        panelTop = new JPanel(new BorderLayout(10,10));
        JPanel filterPanel = new JPanel(new MigLayout("gap 20, insets 20"));

        this.autoCompleteFornec = new AutoCompleteFornec(pessoaService);
        this.selectFilterTipo = new SelectUI<TipoDespesaResponseDTO>(tipoDespesaService.all(), true);
        this.selectFilterFormaPgto = new SelectUI<FormaPagamentoResponseDTO>(formaPagamentoService.all(), true);

        btSearch = Buttons.getBtSearch();
        btSearch.addActionListener(e -> loadDespesas());
        btClear = Buttons.getBtClear();
        btClear.addActionListener(e -> cleanFilter());

        filterPanel.setBorder(new TitledBorder("Filtros"));
        filterPanel.add(new JLabel("Tipo Despesa:"));
        filterPanel.add(selectFilterTipo,"wrap");
        filterPanel.add(new JLabel("Fornecedor:"));
        filterPanel.add(autoCompleteFornec,"span");
        filterPanel.add(new JLabel("Forma Pgto:"));
        filterPanel.add(selectFilterFormaPgto,"wrap");
        filterPanel.add(new JLabel("Periodo:"));
        filterPanel.add(inicio,"split 2");
        filterPanel.add(fim);
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
        autoCompleteFornec.setSelectedIndex(-1);
        selectFilterFormaPgto.setSelectedIndex(-1);
        inicio.setText("");
        fim.setText("");
    }
}
