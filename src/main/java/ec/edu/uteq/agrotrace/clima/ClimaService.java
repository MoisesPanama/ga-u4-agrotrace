package ec.edu.uteq.agrotrace.clima;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClimaService {

    private static final Logger log = LoggerFactory.getLogger(ClimaService.class);

    // Centro de acopio APROCAFA, km 7 via Quevedo-El Empalme
    private static final double LAT = -1.0286;
    private static final double LON = -79.4636;

    private final RestClient cliente;
    private PronosticoSecado ultimoConocido = null;

    public ClimaService(RestClient.Builder constructor) {
        this.cliente = constructor
                .baseUrl("https://api.open-meteo.com")
                .requestFactory(org.springframework.http.client.SimpleClientHttpRequestFactory::new)
                .build();
    }

    // TODO-GA-13: Consumo desde servidor con RestClient
    public PronosticoSecado consultarOrigen() {
        try {
            return cliente.get()
                    .uri(uri -> uri.path("/v1/forecast")
                            .queryParam("latitude", LAT)
                            .queryParam("longitude", LON)
                            .queryParam("hourly", "temperature_2m,relative_humidity_2m,precipitation")
                            .queryParam("forecast_days", 2)
                            .queryParam("timezone", "America/Guayaquil")
                            .build())
                    .retrieve()
                    .body(PronosticoSecado.class);
        } catch (RestClientResponseException ex) {
            throw new ClimaNoDisponibleException(
                    "Peticion invalida al servicio meteorologico: " + ex.getStatusCode(), ex);
        } catch (Exception ex) {
            throw new ClimaNoDisponibleException(
                    "El servicio meteorologico no responde", ex);
        }
    }

    // TODO-GA-14: Cache-aside con Redis
    @Cacheable(cacheNames = "pronostico-secado", key = "'acopio-principal'",
               unless = "#result == null")
    public PronosticoSecado consultar() {
        log.info("Fallo de cache: consultando origen open-meteo");
        PronosticoSecado resultado = consultarOrigen();
        this.ultimoConocido = resultado;
        return resultado;
    }

    // TODO-GA-14: Degradacion elegante si el origen cae
    public PronosticoSecado consultarTolerante() {
        try {
            return consultar();
        } catch (ClimaNoDisponibleException | RuntimeException ex) {
            log.warn("Origen meteorologico inaccesible: {}", ex.getMessage());
            return ultimoConocido != null ? ultimoConocido : PronosticoSecado.noDisponible();
        }
    }
}
