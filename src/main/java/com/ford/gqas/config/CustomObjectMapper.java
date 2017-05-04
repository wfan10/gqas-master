package com.ford.gqas.config;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;

@Component
@Primary
public class CustomObjectMapper extends ObjectMapper {
    public CustomObjectMapper() {
        //CustomSerializerFactory sf = new CustomSerializerFactory();
        //sf.addSpecificMapping(LocalDate.class, new LocalDateSerializer());
        //this.setSerializerFactory(sf);
    	//this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    	// this.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true );
    	this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
    	//this.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    	//this.configure(MapperFeature., state)
    }
}