# AgroTrace Quevedo

Aplicacion web MVC para registro y trazabilidad de lotes de cacao fino de aroma en Los Rios.

## Contexto
- **Cliente:** APROCAFA (160 productores, centro de acopio en Quevedo)
- **Stack:** Spring Boot 3.4.1 + Java 21 LTS + PostgreSQL + Redis + Thymeleaf

## Caracteristicas

### MVC
- Gestion de lotes: registro, consulta, modificacion y anulacion
- Validacion de reglas de negocio (humedad 5-12%, fermentacion 100-336h)
- Plantillas Thymeleaf con Bootstrap 5

### API REST
- API versionada (`/api/v1/lotes`) para exportadoras
- Endpoints: CRUD, inventario, resumen, busqueda
- Manejo de errores con formato RFC 9457

### SOAP
- Servicio de certificacion oficial (`/ws/certificacion`)
- Contrato WSDL con tipos fuertes
- Consulta y certificacion de lotes

### Cache Redis
- Clima cacheado con patron cache-aside
- TTL de 15 minutos

## Requisitos

- JDK 21 LTS o 25 LTS
- Maven 3.6.3+
- Docker Desktop 4.x con Compose v2
- Git 2.40+

## Instalacion

```powershell
# 1. Clonar repositorio
git clone https://github.com/MoisesPanama/ga-u4-agrotrace.git
cd ga-u4-agrotrace

# 2. Levantar DB y cache
docker compose up -d

# 3. Compilar
.\mvnw.cmd clean package -DskipTests

# 4. Arrancar
.\mvnw.cmd spring-boot:run
```

## Endpoints

### MVC
| Ruta | Metodo | Descripcion |
|------|--------|-------------|
| `/lotes` | GET | Lista de lotes |
| `/lotes/nuevo` | GET | Formulario nuevo lote |
| `/lotes/{id}` | GET | Detalle de lote |
| `/lotes/{id}/editar` | GET | Editar lote |
| `/lotes/{id}/anular` | POST | Anular lote |
| `/lotes/{id}/certificar` | POST | Certificar lote |

### REST API v1
| Endpoint | Metodo | Descripcion |
|----------|--------|-------------|
| `/api/v1/lotes` | GET | Listar lotes |
| `/api/v1/lotes/{id}` | GET | Obtener lote |
| `/api/v1/lotes/codigo/{codigo}` | GET | Buscar por codigo |
| `/api/v1/lotes/inventario` | GET | Inventario disponible |
| `/api/v1/lotes/inventario/variedad?variedad=X` | GET | Inventario por variedad |
| `/api/v1/lotes` | POST | Crear lote |
| `/api/v1/lotes/{id}` | PUT | Actualizar lote |
| `/api/v1/lotes/{id}/anular` | PATCH | Anular lote |
| `/api/v1/lotes/{id}` | DELETE | Eliminar lote |
| `/api/v1/lotes/resumen` | GET | Resumen general |
| `/api/v1/clima/{ciudad}` | GET | Clima de ciudad |

### SOAP
| Servicio | Endpoint |
|----------|----------|
| Certificar lote | `POST /ws/certificacion` |
| Consultar lote | `POST /ws/certificacion` |

## Roles del Equipo
- **Piloto:** Teclea y hace commits
- **Copiloto:** Lee la guia, verifica codigo
- **Documentador:** Captura evidencias y controla tiempos
