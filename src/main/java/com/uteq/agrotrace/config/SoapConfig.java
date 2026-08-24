package com.uteq.agrotrace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.xml.config.annotation.EnableWs;
import org.springframework.xml.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServletRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.xml.ws.support.SimpleEndpointDeclaration;
import org.springframework.xml.ws.server.EndpointFactoryBean;

@Configuration
@EnableWs
public class SoapConfig extends WsConfigurerAdapter {

    private static final String NAMESPACE_URI = "http://uteq.com/agrotrace/soap";

    @Bean
    public EndpointFactoryBean certificacionEndpoint(com.uteq.agrotrace.soap.CertificacionSoapEndpoint endpoint) {
        EndpointFactoryBean factory = new EndpointFactoryBean();
        factory.setEndpointClass(com.uteq.agrotrace.soap.CertificacionSoapEndpoint.class);
        factory.setUri("/ws/certificacion");
        factory.setEndpointObject(endpoint);
        return factory;
    }
}
