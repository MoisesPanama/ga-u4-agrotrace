package ec.edu.uteq.agrotrace.soap.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

// TODO-GA-10: Configuracion SOAP y publicacion del WSDL
@Configuration
@EnableWs
public class ConfiguracionSoap extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> mensajeria(
            ApplicationContext contexto) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(contexto);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "certificacion")
    public DefaultWsdl11Definition wsdl(XsdSchema esquema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("CertificacionPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("https://agrotrace.uteq.edu.ec/soap/certificacion");
        wsdl.setSchema(esquema);
        return wsdl;
    }

    @Bean
    public XsdSchema esquema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/certificacion.xsd"));
    }
}
