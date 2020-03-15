package br.com.logic.pendotiba.logicbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"br.com.logic.pendotiba.core.model"})
@EnableJpaRepositories({"br.com.logic.pendotiba.core.repository"})
@EnableFeignClients
public class LogicBusApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogicBusApplication.class, args);
	}
}
