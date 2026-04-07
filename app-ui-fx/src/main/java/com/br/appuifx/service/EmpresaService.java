package com.br.appuifx.service;

import com.br.appuifx.dto.EmpresaDTO;
import com.br.appuifx.dto.MunicipioDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmpresaService {
    public static EmpresaDTO buscarCnpj(String cnpj)
    {
        try {
            String clean = cnpj.replaceAll("\\D", "");

            // 1. ReceitaWS
            String urlReceita = "https://publica.cnpj.ws/cnpj/" + clean;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(urlReceita))
                    .GET()
                    .build();

            HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(resp.body());

            String razao = json.get("razao_social").asText();
            String fantasia = json.get("estabelecimento").get("nome_fantasia").asText();
            String ibge = json.get("estabelecimento").get("cidade").get("ibge_id").asText();
            String cidade = json.get("estabelecimento").get("cidade").get("nome").asText();
            String uf = json.get("estabelecimento").get("estado").get("sigla").asText();
            return new EmpresaDTO(razao, fantasia, ibge, new MunicipioDTO(cidade, uf));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
