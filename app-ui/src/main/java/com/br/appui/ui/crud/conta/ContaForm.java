package com.br.appui.ui.crud.conta;

import com.br.appui.ui.shared.InputDate;
import com.br.appui.ui.shared.InputMoney;
import com.br.appui.ui.shared.InputNumber;
import com.br.appui.ui.shared.SelectUI;
import com.br.appui.ui.util.*;
import com.br.dto.request.create.ContaRequestCreateDTO;
import com.br.dto.request.ref.FormaPagamentoRefDTO;
import com.br.dto.request.ref.TipoContaRefDTO;
import com.br.dto.request.update.ContaRequestUpdateDTO;
import com.br.dto.response.ContaResponseDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.TipoContaResponseDTO;
import com.br.service.ContaService;
import com.br.service.FormaPagamentoService;
import com.br.service.TipoContaService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.UUID;

public class ContaForm extends JPanel {
    private ContaService contaService;
    private TipoContaService tipoContaService;
    private FormaPagamentoService formaPagamentoService;
    private SelectUI<TipoContaResponseDTO> selectTipoConta;
    private InputNumber inputCodigoBarras;
    private InputNumber inputParcela;
    private InputNumber inputTotalParcela;
    private SelectUI<FormaPagamentoResponseDTO> selectFormaPagamento;
    private InputDate inputVencimento = new InputDate();
    private InputDate inputEmissao = new InputDate();
    private InputMoney inputValor = new InputMoney();
    private InputDate inputDataPgto = new InputDate();
    private InputMoney inputValorPgto = new InputMoney();
    private JTextArea inputObs = new  JTextArea(3,35);
    private JButton btSavar;
    private JButton btCancelar;
    private ContaResponseDTO contaResponseDTOEdit;

    public ContaForm(UUID uuidEdit){
        this.tipoContaService = SpringContext.getBean(TipoContaService.class);
        this.formaPagamentoService = SpringContext.getBean(FormaPagamentoService.class);
        this.contaService = SpringContext.getBean(ContaService.class);
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

        this.selectTipoConta = new SelectUI<TipoContaResponseDTO>(tipoContaService.findAll(), false);
        this.selectFormaPagamento = new SelectUI<FormaPagamentoResponseDTO>(formaPagamentoService.all(), false);
        this.inputCodigoBarras = new InputNumber(48, 33);
        this.inputParcela = new InputNumber(2, 5);
        this.inputTotalParcela = new InputNumber(2, 5);

        btSavar = Buttons.getBtaSave();
        btSavar.addActionListener(e -> salvar());

        add(new JLabel("Código Barras:"));
        add(inputCodigoBarras,"wrap");
        add(new JLabel("Tipo Conta:"));
        add(selectTipoConta,"wrap");
        add(new JLabel("Emissão:"));
        add(inputEmissao,"wrap");
        add(new JLabel("Vencimento:"));
        add(inputVencimento,"wrap");
        add(new JLabel("Parcela:"));
        add(inputParcela,"split 2");
        add(inputTotalParcela, "wrap");
        add(new JLabel("Valor:"));
        add(inputValor, "wrap");

        JLabel labelPgto = new JLabel("Pagamento:");
        labelPgto.setFont(new Font(getFont().getName(), Font.BOLD | Font.ITALIC, 16));
        add(labelPgto, "span, gapx 20");
        add(new JLabel("Forma :"));
        add(selectFormaPagamento, "wrap");
        add(new JLabel("Data :"));
        add(inputDataPgto, "wrap");
        add(new JLabel("Valor :"));
        add(inputValorPgto, "wrap");

        add(new JLabel("Observação:"));
        add(inputObs,"span");
        add(btSavar, "align center, split 2, span");
    }

    void initEdit(UUID uuidEdi){
        this.contaResponseDTOEdit = contaService.findByUuid(uuidEdi);
        this.selectTipoConta.setSelectedItem(contaResponseDTOEdit.tipoConta());
        this.inputCodigoBarras.setText(contaResponseDTOEdit.codigoBarras());
        this.inputVencimento.setText(FormatUtil.formatData(contaResponseDTOEdit.vencimento()));
        this.inputEmissao.setText(FormatUtil.formatData(contaResponseDTOEdit.emissao()));
        this.inputParcela.setText(contaResponseDTOEdit.parcela().toString());
        this.inputTotalParcela.setText(contaResponseDTOEdit.totalParcela().toString());
        this.inputValor.setText(FormatUtil.formatMoeda(contaResponseDTOEdit.valor()));

        if(contaResponseDTOEdit.formaPagamento()!=null)
            this.selectFormaPagamento.setSelectedItem(contaResponseDTOEdit.formaPagamento());
        if(contaResponseDTOEdit.dataPagamento()!=null)
            this.inputDataPgto.setText(FormatUtil.formatData(contaResponseDTOEdit.dataPagamento()));
        if(contaResponseDTOEdit.valorPago()!=null)
            this.inputValorPgto.setText(FormatUtil.formatMoeda(contaResponseDTOEdit.valorPago()));

        this.inputObs.setText(contaResponseDTOEdit.observacao());
    }

    void salvar(){
        boolean valid = ValidUtil.validRequiredField(new Component[]{
                        selectTipoConta,
                        inputCodigoBarras,
                        inputVencimento,
                        inputEmissao,
                        inputValor},
                this);
        if(valid){
            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            LoadingDialog dialog = new LoadingDialog(frame);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                ContaRequestCreateDTO contaRequestCreateDTO;
                ContaRequestUpdateDTO contaRequestUpdateDTO;
                @Override
                protected Void doInBackground() {
                    if(contaResponseDTOEdit != null) {
                        contaRequestUpdateDTO = new ContaRequestUpdateDTO(
                                contaResponseDTOEdit.uuid(),
                                contaResponseDTOEdit.codigoBarras(),
                                new TipoContaRefDTO(null, contaResponseDTOEdit.tipoConta().uuid()),
                                contaResponseDTOEdit.emissao(),
                                contaResponseDTOEdit.vencimento(),
                                contaResponseDTOEdit.parcela(),
                                contaResponseDTOEdit.totalParcela(),
                                contaResponseDTOEdit.valor(),
                                new FormaPagamentoRefDTO(null,
                                        (contaResponseDTOEdit.formaPagamento() == null ? null : contaResponseDTOEdit.formaPagamento().getUuid())
                                ),
                                inputDataPgto.getDateTime(),
                                inputValorPgto.getBigDecimal(),
                                contaResponseDTOEdit.observacao(),
                                null,
                                null
                        );
                        contaService.update(contaRequestUpdateDTO);
                    }else {
                        contaRequestCreateDTO = new ContaRequestCreateDTO(
                                inputCodigoBarras.getText(),
                                new TipoContaRefDTO(null,selectTipoConta.getSelectedItemTyped().uuid()),
                                inputEmissao.getDateTime(),
                                inputVencimento.getDateTime(),
                                Integer.valueOf(inputParcela.getText()),
                                Integer.valueOf(inputTotalParcela.getText()),
                                inputValor.getBigDecimal(),
                                new FormaPagamentoRefDTO(null,
                                        (selectFormaPagamento.getSelectedItemTyped() == null ?  null : selectFormaPagamento.getSelectedItemTyped().uuid())
                                ),
                                inputDataPgto.getDateTime(),
                                inputValorPgto.getBigDecimal(),
                                contaResponseDTOEdit.observacao(),
                                null,
                                null
                        );
                        contaService.create(contaRequestCreateDTO);
                    }
                    return null;
                }
                @Override
                protected void done() {
                    contaRequestCreateDTO = null;
                    contaRequestUpdateDTO = null;
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
        this.inputCodigoBarras.setText("");
        this.inputVencimento.setText("");
        this.inputEmissao.setText("");
        this.inputParcela.setText("0");
        this.inputTotalParcela.setText("0");
        this.inputValorPgto.setText("0,00");
        this.inputDataPgto.setText("");
        this.inputValorPgto.setText("0,00");
        this.selectFormaPagamento.setSelectedIndex(-1);
        this.inputObs.setText("");
    }
}
