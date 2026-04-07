package com.br.appuifx.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import com.br.appuifx.dto.CidadeDTO;
import com.br.appuifx.dto.MunicipioDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IbgeService {

    private static final String URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/municipios";

    private static final String URL_CIDADE = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios/%s?view=nivelado";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<CidadeDTO> buscarCidadesPorUF(String uf) {
        try {
            String url = String.format(URL, uf.toUpperCase());

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro ao consultar IBGE: " + response.statusCode());
            }

            // Classe auxiliar para mapear resposta
            MunicipioResponse[] municipios = mapper.readValue(response.body(), MunicipioResponse[].class);

            return Arrays.stream(municipios).map(m -> new CidadeDTO(m.getId(), m.getNome())).toList();

        } catch (Exception e) {
            throw new RuntimeException("Falha ao buscar cidades do IBGE", e);
        }
    }

    // Classe interna para mapear JSON do IBGE
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class MunicipioResponse {
        private String id;
        private String nome;

        public String getId() { return id; }
        public String getNome() { return nome; }

        public void setId(String id) { this.id = id; }
        public void setNome(String nome) { this.nome = nome; }
    }

    public MunicipioDTO buscarCidadePorIbge(String codigoIbge) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(URL_CIDADE, codigoIbge)))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Erro ao consultar IBGE: HTTP " + response.statusCode());
            }

            JsonNode json = mapper.readTree(response.body());

            String nomeCidade = json.get("municipio-nome").asText();
            String uf = json.get("UF-sigla").asText();

            return new MunicipioDTO(nomeCidade, uf);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar cidade IBGE", e);
        }
    }

}

