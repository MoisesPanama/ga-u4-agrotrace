# E2 - Justificacion del TTL de Cache para Clima

## Por que 15 minutos y no otro valor

El pronostico meteorologico de Open-Meteo se actualiza en el origen cada **60 minutos** (frecuencia horaria del modelo GFS/WRF que alimenta la API). Un TTL de 15 minutos garantiza que:

1. **El dato nunca tiene mas de 15 minutos de antiguedad**, lo cual esaceptable para un pronostico de 48 horas donde las condiciones cambian gradualmente.

2. **Se evita golpear el origen innecesariamente**: con 160 productores y tecnicos consultando el tablero, sin cache serian ~160 llamadas/hora al servicio externo. Con cache de 15 min, se reducen a ~4 llamadas/hora.

3. **Un TTL de 30 segundos** castigaria al proveedor sin ganar frescura: un pronostico horario no cambia significativamente en 30 segundos.

4. **Un TTL de 24 horas** serviria datos obsoletos: el pronostico del dia siguiente podria ser completamente diferente al real.

## Justificacion en el contexto de APROCAFA

El centro de acopio recibe entre 20 y 40 lotes diarios en temporada alta. El tecnico necesita saber si habra lluvia en las proximas 48 horas para programar el secado de cacao. Un dato con 15 minutos de retraso no afecta la decision, pero un dato de 24 horas si podria llevar a programar secado en un dia de lluvia.

## Degradacion elegante

Si Open-Meteo cae, el servicio devuelve el ultimo pronostico conocido en lugar de una pantalla de error. Esto es critico porque:
- El tablero del tecnico debe seguir funcionando
- El ultimo pronostico sigue siendo mas util que nada
- La disponibilidad del sistema no depende de un tercero

## Evidencia en Redis

```bash
# Ver clave almacenada
docker exec agrotrace-redis redis-cli KEYS "pronostico-secado*"
# TTL restante (entre 0 y 900 segundos = 15 min)
docker exec agrotrace-redis redis-cli TTL "pronostico-secado::acopio-principal"
```
