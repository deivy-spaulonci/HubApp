package com.br.service;

import com.br.dto.response.TreeNodeResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class PagamentosArquivoService {

    @Value("${app.path.payments.files}")
    private String pathPayments;

    public List<TreeNodeResponseDTO> listarEstruturaDiretorio() {
        File raiz = new File(pathPayments);
        List<TreeNodeResponseDTO> nodes = new ArrayList<>();

        if (raiz.exists()) {
            nodes.add(criarNoRecursivo(raiz));
        }
        return nodes.get(0).children.reversed();
    }

    private TreeNodeResponseDTO criarNoRecursivo(File arquivo) {
        boolean isDirectory = arquivo.isDirectory();
        String icon = isDirectory ? "pi pi-folder" : "pi pi-file";

        TreeNodeResponseDTO node = new TreeNodeResponseDTO(arquivo.getName(), arquivo.getAbsolutePath(), icon);

        if (isDirectory) {

            File[] listaArquivos = arquivo.listFiles();
            Arrays.sort(listaArquivos, Comparator.comparing(File::getName, String.CASE_INSENSITIVE_ORDER));
            if (listaArquivos != null) {
                node.children = new ArrayList<>();
                for (File f : listaArquivos) {
                    node.children.add(criarNoRecursivo(f));
                }
            }
        }

        return node;
    }

    public Resource carregarArquivo(String pathCompleto) throws Exception {
        Path path = Paths.get(pathCompleto);

        // Validação básica de segurança: o arquivo solicitado deve estar dentro do caminhoBase
        if (!path.startsWith(Paths.get(this.pathPayments))) {
            throw new SecurityException("Acesso não permitido fora do diretório base.");
        }

        Resource resource = new UrlResource(path.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Arquivo não encontrado ou ilegível: " + pathCompleto);
        }
    }

    public String identificarTipoArquivo(String pathCompleto) {
        try {
            String type = Files.probeContentType(Paths.get(pathCompleto));
            return type != null ? type : "application/octet-stream";
        } catch (Exception e) {
            return "application/octet-stream";
        }
    }
}
