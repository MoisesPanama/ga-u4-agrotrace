package com.uteq.agrotrace.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClimaService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.clima.cache-ttl-minutes:15}")
    private int cacheTtlMinutes;

    @Value("${app.clima.api-url:https://api.openweathermap.org/data/2.5/weather}")
    private String apiUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // TODO-GA-12: Consumo de servicios externos desde servidor
    // TODO-GA-13: Implementar cache con Redis (patron cache-aside)
    public Map<String, Object> obtenerClima(String ciudad) {
        String cacheKey = "clima:" + ciudad;

        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return (Map<String, Object>) cached;
        }

        try {
            String url = apiUrl + "?q=" + ciudad + "&appid=demo&units=metric&lang=es";
            String response = restTemplate.getForObject(url, String.class);

            JsonNode json = objectMapper.readTree(response);
            Map<String, Object> clima = new HashMap<>();
            clima.put("ciudad", json.path("name").asText());
            clima.put("temperatura", json.path("main").path("temp").asDouble());
            clima.put("humedad", json.path("main").path("humidity").asInt());
            clima.put("descripcion", json.path("weather").get(0).path("description").asText());
            clima.put("viento", json.path("wind").path("speed").asDouble());

            redisTemplate.opsForValue().set(cacheKey, clima, Duration.ofMinutes(cacheTtlMinutes));
            return clima;
        } catch (Exception e) {
            Map<String, Object> climaDefault = new HashMap<>();
            climaDefault.put("ciudad", ciudad);
            climaDefault.put("temperatura", 28.0);
            climaDefault.put("humedad", 75);
            climaDefault.put("descripcion", "Parcialmente nublado");
            climaDefault.put("viento", 5.0);
            climaDefault.put("fuente", "datos por defecto (API no disponible)");
            return climaDefault;
        }
    }

    public void limpiarCache(String ciudad) {
        redisTemplate.delete("clima:" + ciudad);
    }
}
