package com.br.appuifx;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.br")
@EnableJpaRepositories(basePackages = "com.br.repository")
@EntityScan(basePackages = "com.br")
public class SpringBootApp {
}
