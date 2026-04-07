package com.br.appui.ui.crud.conta;

import com.br.appui.ui.shared.InputDate;
import com.br.appui.ui.shared.InputMoney;
import com.br.appui.ui.shared.SelectUI;
import com.br.appui.ui.util.*;
import com.br.dto.request.ref.FormaPagamentoRefDTO;
import com.br.dto.request.ref.TipoContaRefDTO;
import com.br.dto.request.update.ContaRequestUpdateDTO;
import com.br.dto.response.ContaResponseDTO;
import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.service.ContaService;
import com.br.service.FormaPagamentoService;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class ContaPayForm extends JDialog {
    private ContaService contaService;
    private FormaPagamentoService  formaPagamentoService;
    private SelectUI<FormaPagamentoResponseDTO> selectFormaPagamento;
    private InputDate inputDataPgto = new InputDate();
    private InputMoney inputValorPgto = new InputMoney();
    private JTextArea inputObs = new  JTextArea(3,35);
    private JButton btSavar;
    private JButton btCancelar;
    private ContaResponseDTO contaResponseDTO;

    public ContaPayForm(Window parent, UUID uuidConta) {
        super(SwingUtilities.getWindowAncestor(parent),"Pagar Conta", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setBounds(100, 100, 500, 500);
        setLocationRelativeTo(parent);
        this.formaPagamentoService = SpringContext.getBean(FormaPagamentoService.class);
        this.contaService = SpringContext.getBean(ContaService.class);

        this.contaResponseDTO = contaService.findByUuid(uuidConta);
        init();
    }

    void init()
    {
        this.getContentPane().setLayout(new MigLayout("insets 50, gapy 15, gapx 15"));
        this.selectFormaPagamento = new SelectUI<FormaPagamentoResponseDTO>(formaPagamentoService.all(), false);

        btSavar = Buttons.getBtaSave();
        btSavar.addActionListener(e -> salvar());

        btCancelar = Buttons.getBtCancelar();
        btCancelar.addActionListener(e -> dispose());

        add(new JLabel("Forma :"));
        add(selectFormaPagamento, "wrap");
        add(new JLabel("Data :"));
        add(inputDataPgto, "wrap");
        add(new JLabel("Valor :"));
        add(inputValorPgto, "wrap");

        add(new JLabel("Observação:"));
        add(inputObs,"span");
        add(btSavar, "align center, split 2, span");
        add(btCancelar, "gapleft 30");
    }

    void salvar(){
        boolean valid = ValidUtil.validRequiredField(
                new Component[]{selectFormaPagamento,inputDataPgto,inputValorPgto},
                null);
        if (valid) {
            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            LoadingDialog dialog = new LoadingDialog(frame);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    ContaRequestUpdateDTO  contaRequestUpdateDTO = new ContaRequestUpdateDTO(
                            contaResponseDTO.uuid(),
                            contaResponseDTO.codigoBarras(),
                            new TipoContaRefDTO(null, contaResponseDTO.tipoConta().uuid()),
                            contaResponseDTO.emissao(),
                            contaResponseDTO.vencimento(),
                            contaResponseDTO.parcela(),
                            contaResponseDTO.totalParcela(),
                            contaResponseDTO.valor(),
                            new FormaPagamentoRefDTO(null,
                                    (selectFormaPagamento.getSelectedItemTyped() == null ? null : selectFormaPagamento.getSelectedItemTyped().uuid())
                            ),
                            inputDataPgto.getDateTime(),
                            inputValorPgto.getBigDecimal(),
                            inputObs.getText(),
                            null,
                            null
                    );
                    contaService.update(contaRequestUpdateDTO);
                    return null;
                }
                @Override
                protected void done() {
                    dialog.dispose();
                    Dialogs.alertSucesso(frame, "Salvo com sucesso");
                }
            };
            worker.execute();
            dialog.setVisible(true); // bloqueia até terminar
        }
    }
}
