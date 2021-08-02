package com.builder.api.config.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		// @formatter:off
		 return new Docket(DocumentationType.SWAGGER_2)
		          .select()
		          .apis(RequestHandlerSelectors.basePackage("com.builder.api.controller"))
		          .paths(PathSelectors.any())
		          .build()
		          .globalResponseMessage(RequestMethod.GET, globalResponses())
	              .globalResponseMessage(RequestMethod.POST, globalResponses())
	              .globalResponseMessage(RequestMethod.DELETE, globalResponses())
	              .globalResponseMessage(RequestMethod.PUT, globalResponses())
		          .apiInfo(apiInfo());
		 // @formatter:on
	}

	private ApiInfo apiInfo() {
		// @formatter:off
	    return new ApiInfoBuilder()
	            .title("Aplicação de Avaliação")
	            .description("Aplicação REST API Spring Boot")
	            .version("1.0")
	            .contact(new Contact("Gustavo Augusto", "https://www.linkedin.com/in/gustavo-rocha-8830a226/", "gustavoarr@gmail.com"))
	            .build();
		 // @formatter:on

	}

	private List<ResponseMessage> globalResponses() {
		// @formatter:off
	    return new ArrayList<ResponseMessage>() {{
	        add( new ResponseMessageBuilder().code(500).message("Erro inesperado no servidor, não foi possível processar a solicitação").build());
	        add( new ResponseMessageBuilder().code(400).message("Dados inválidos fornecidos pelo cliente").build());
	        add( new ResponseMessageBuilder().code(404).message("Recurso não encontrado").build());
	        add( new ResponseMessageBuilder().code(200).message("Operação bem sucedida").build());
	    }};
		// @formatter:on

	}

}
