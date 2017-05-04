package com.ford.gqas;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.ford.gqas.config.CustomObjectMapper;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run( Application.class, args);
	}
	
	@Bean
	@Primary
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToEnable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
        builder.configure(new CustomObjectMapper());
        return builder;
    }
	
	/*
	@Autowired
	public void configeJackson(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
	    jackson2ObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
	}
	*/
}
