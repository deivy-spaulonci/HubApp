package com.br.service;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ArquivoService {

    public void renomearArquivo(String pathCompletoAntigo, String apenasNovoNome) throws Exception {
        Path origem = Paths.get(pathCompletoAntigo);

        // Extrai a extensão original (ex: .pdf, .jpg)
        String nomeArquivoAntigo = origem.getFileName().toString();
        String extensao = "";
        int dotIndex = nomeArquivoAntigo.lastIndexOf('.');
        if (dotIndex >= 0) {
            extensao = nomeArquivoAntigo.substring(dotIndex);
        }

        // Monta o novo nome garantindo a extensão original
        String nomeFinal = apenasNovoNome + extensao;
        Path destino = origem.resolveSibling(nomeFinal);

        if (Files.exists(destino)) {
            throw new RuntimeException("Já existe um arquivo com o nome: " + nomeFinal);
        }

        Files.move(origem, destino, StandardCopyOption.ATOMIC_MOVE);
    }

}
