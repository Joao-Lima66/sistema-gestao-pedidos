package com.weverson.gestao_pedidos;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Sistema de Gestão de Pedidos")
                        .version("1.0.0")
                        .description("Interface interativa para gerenciamento de pedidos, validação de dados e tratamento de erros.")
                        .contact(new Contact()
                                .name("Suporte do Sistema")
                                .email("suporte@gestaopedidos.com")));
    }
}