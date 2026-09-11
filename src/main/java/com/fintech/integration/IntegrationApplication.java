package com.fintech.integration;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CamelAutoConfiguration.class)
public class IntegrationApplication {

    public static void main(final String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }

    @Bean
    public CamelContext customizeCamelContext(final CamelContext camelContext) throws Exception {
        camelContext.setUseMDCLogging(true);
        camelContext.setTracing(false);
        camelContext.getTypeConverterRegistry().addTypeConverter(
            String.class, 
            byte[].class, 
            new org.apache.camel.impl.conversion.StringToByteArrayConverter()
        );
        return camelContext;
    }
}