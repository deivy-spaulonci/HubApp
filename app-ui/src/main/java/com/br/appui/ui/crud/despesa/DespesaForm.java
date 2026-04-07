package com.br.appui.ui.crud.despesa;

import com.br.appui.ui.shared.AutoCompleteFornec;
import com.br.appui.ui.shared.InputDate;
import com.br.appui.ui.shared.InputMoney;
import com.br.appui.ui.shared.SelectUI;
import com.br.appui.ui.util.*;
import com.br.dto.request.create.DespesaRequestCreateDTO;
import com.br.dto.request.ref.FormaPagamentoRefDTO;
import com.br.dto.request.ref.PessoaRefDTO;
import com.br.dto.request.ref.TipoDespesaRefDTO;
import com.br.dto.request.update.DespesaRequestUpdateDTO;
import com.br.dto.response.DespesaResponseDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.TipoDespesaResponseDTO;
import com.br.service.DespesaService;
import com.br.service.FormaPagamentoService;
import com.br.service.PessoaService;
import com.br.service.TipoDespesaService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class DespesaForm extends JPanel {
    private DespesaService despesaService;
    private TipoDespesaService tipoDespesaService;
    private PessoaService pessoaService;
    private FormaPagamentoService formaPagamentoService;
    private SelectUI<TipoDespesaResponseDTO> selectTipoDespesa;
    private SelectUI<FormaPagamentoResponseDTO> selectFormaPagamento;
    private AutoCompleteFornec autoComplete;
    private InputDate inputDataPgto = new InputDate();
    private InputMoney inputValor = new InputMoney();
    private JTextArea inputObs = new  JTextArea(3,35);
    private JButton btSalvar;
    private JButton btCancelar;
    private DespesaResponseDTO despesaResponseDTOEdit;

    public DespesaForm(UUID uuidEdit) {
        this.tipoDespesaService = SpringContext.getBean(TipoDespesaService.class);
        this.pessoaService = SpringContext.getBean(PessoaService.class);
        this.formaPagamentoService = SpringContext.getBean(FormaPagamentoService.class);
        this.despesaService = SpringContext.getBean(DespesaService.class);
        init();
        if(Objects.nonNull(uuidEdit)) {
            initEdit(uuidEdit);
            btCancelar = Buttons.getBtCancelar();
            btCancelar.addActionListener(e -> {
                if(SwingUtilities.getWindowAncestor(this) instanceof JDialog)
                    ((JDialog)SwingUtilities.getWindowAncestor(this)).dispose();
            });
            add(btCancelar, "gapleft 30");
        }
    }

    void init(){
        setLayout(new MigLayout("insets 100, gapy 15, gapx 15","","[center]"));
        this.autoComplete = new AutoCompleteFornec(pessoaService);
        this.selectTipoDespesa = new SelectUI<TipoDespesaResponseDTO>(tipoDespesaService.all(), false);
        this.selectFormaPagamento =  new SelectUI<FormaPagamentoResponseDTO>(formaPagamentoService.all(), false);

        inputObs.setPreferredSize(new Dimension(150, 40));
        inputObs.setBorder(new EmptyBorder(3, 3, 3, 0));

        btSalvar = Buttons.getBtaSave();
        btSalvar.addActionListener(e -> salvar());

        add(new JLabel("Tipo Despesa:"));
        add(selectTipoDespesa,"wrap");
        add(new JLabel("Fornecedor:"));
        add(autoComplete,"wrap");
        add(new JLabel("Data Pgto:"));
        add(inputDataPgto,"wrap");
        add(new JLabel("Forma Pgto:"));
        add(selectFormaPagamento,"wrap");
        add(new JLabel("Valor:"));
        add(inputValor,"wrap");
        add(new JLabel("Observação:"));
        add(inputObs,"wrap");
        add(btSalvar, "align center, split 2, span");
    }

    void initEdit(UUID uuidEdit){
        this.despesaResponseDTOEdit = despesaService.getDespesaByUuid(uuidEdit);
        this.selectTipoDespesa.setSelectedItem(despesaResponseDTOEdit.tipoDespesa());
        this.autoComplete.setSelectedItem(despesaResponseDTOEdit.fornecedor());
        this.inputDataPgto.setText(FormatUtil.formatData(despesaResponseDTOEdit.dataPagamento()));
        this.selectFormaPagamento.setSelectedItem(despesaResponseDTOEdit.formaPagamento());
        this.inputValor.setText(FormatUtil.formatMoeda(despesaResponseDTOEdit.valor()));
        this.inputObs.setText(despesaResponseDTOEdit.observacao());
    }

    void salvar(){
        boolean valid = ValidUtil.validRequiredField(new Component[]{
                inputDataPgto,
                inputValor,
                selectTipoDespesa,
                selectFormaPagamento,
                autoComplete},
                this);

        if(valid){
            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            LoadingDialog dialog = new LoadingDialog(frame);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                DespesaRequestUpdateDTO despesaRequestUpdateDTO;
                DespesaRequestCreateDTO despesaRequestCreateDTO;
                @Override
                protected Void doInBackground() {
                    if(despesaResponseDTOEdit != null) {
                        despesaRequestUpdateDTO = new DespesaRequestUpdateDTO(
                                despesaResponseDTOEdit.uuid(),
                                new TipoDespesaRefDTO(
                                        despesaResponseDTOEdit.tipoDespesa().value(),
                                        despesaResponseDTOEdit.tipoDespesa().nome()
                                ),
                                new PessoaRefDTO(null,despesaResponseDTOEdit.fornecedor().uuid()),
                                despesaResponseDTOEdit.dataPagamento(),
                                despesaResponseDTOEdit.valor(),
                                new FormaPagamentoRefDTO(null, despesaResponseDTOEdit.formaPagamento().uuid()),
                                despesaResponseDTOEdit.observacao()
                        );
                        despesaService.update(despesaRequestUpdateDTO);

                    }else {
                        despesaRequestCreateDTO = new DespesaRequestCreateDTO(
                                new TipoDespesaRefDTO(
                                        selectTipoDespesa.getSelectedItemTyped().value(),
                                        selectTipoDespesa.getSelectedItemTyped().nome()
                                ),
                                new PessoaRefDTO(null, autoComplete.getSelectedItemTyped().uuid()),
                                inputDataPgto.getDateTime(),
                                inputValor.getBigDecimal(),
                                new FormaPagamentoRefDTO(null, selectFormaPagamento.getSelectedItemTyped().uuid()),
                                inputObs.getText()
                        );
                        despesaService.create(despesaRequestCreateDTO);
                    }
                    return null;
                }
                @Override
                protected void done() {
                    despesaRequestCreateDTO = null;
                    despesaResponseDTOEdit = null;
                    reset();
                    dialog.dispose();
                    Dialogs.alertSucesso(frame, "Salvo com sucesso");
                }
            };
            worker.execute();
            dialog.setVisible(true); // bloqueia até terminar
        }
    }

    void reset(){
        this.despesaResponseDTOEdit = null;
        this.inputValor.setText("0,00");
        this.inputObs.setText("");

    }
}
