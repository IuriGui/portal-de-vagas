package br.csi.oportunidades;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(
		info = @Info(
				title = "API PortalDeVagas",
				version = "1.0.1",
				description = "API",
				contact = @Contact(name = "Suporte", email = "iuriguilhermesantos@gmail.com")
		)
)

@SpringBootApplication
public class OportunidadesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OportunidadesApplication.class, args);
	}



}
