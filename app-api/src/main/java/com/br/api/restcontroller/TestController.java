package com.br.api.restcontroller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/perfil")
    public String getUsuario(@AuthenticationPrincipal Jwt jwt) {
        // Retorna o "subject" (ID do usuário) e o e-mail que estão dentro do token
        return "Usuário autenticado no hubapp: " + jwt.getClaim("preferred_username");
    }
}
