package com.br.appui.ui.shared;

import com.br.dto.response.PessoaResponseDTO;
import com.br.service.PessoaService;
import org.springframework.data.domain.Sort;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

public class AutoCompleteFornec extends JComboBox<PessoaResponseDTO> {

    private final PessoaService pessoaService;
    private Consumer<PessoaResponseDTO> onSelect;
    private boolean isAdjusting = false;

    public AutoCompleteFornec(PessoaService pessoaService) {
        this.pessoaService = pessoaService;

        setEditable(true);
        setMaximumRowCount(30);
        setPreferredSize(new Dimension(500, 30));
        setBorder(new EmptyBorder(0, 3, 0, 0));
        setupListener();
        setupSelection();
    }

    private void setupListener() {
        JTextField editor = (JTextField) getEditor().getEditorComponent();

        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscar(editor.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscar(editor.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscar(editor.getText());
            }
        });
    }

    private void buscar(String texto) {
        if (isAdjusting || texto.length() < 2) return;

        isAdjusting = true;

        SwingUtilities.invokeLater(() -> {
            List<PessoaResponseDTO> resultados = pessoaService.find(texto, Sort.by("nome"));

            setModel(new DefaultComboBoxModel<>(new Vector<>(resultados)));

            JTextField editor = (JTextField) getEditor().getEditorComponent();
            editor.setText(texto);

            setPopupVisible(true);

            isAdjusting = false;
        });
    }

    private void setupSelection() {
        addActionListener(e -> {
            if (isAdjusting) return;

            PessoaResponseDTO selected = getSelectedItemTyped();

            if (onSelect != null && selected != null) {
                onSelect.accept(selected);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public PessoaResponseDTO getSelectedItemTyped() {
        return (PessoaResponseDTO) getSelectedItem();
    }

    public void setOnSelect(Consumer<PessoaResponseDTO> onSelect) {
        this.onSelect = onSelect;
    }

    @Override
    public Dimension getMinimumSize() {
        Dimension size = super.getMinimumSize();
        size.width = Math.max(size.width, 200); // largura mínima
        return size;
    }
}