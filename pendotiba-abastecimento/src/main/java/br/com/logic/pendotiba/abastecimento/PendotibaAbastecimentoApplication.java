package br.com.logic.pendotiba.abastecimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"br.com.logic.pendotiba.core.model"})
@EnableJpaRepositories({"br.com.logic.pendotiba.core.repository"})
public class PendotibaAbastecimentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PendotibaAbastecimentoApplication.class, args);
	}

}
