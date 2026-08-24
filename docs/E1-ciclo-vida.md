# E1 - Ciclo de Vida de una Peticion MVC

## Tracing: GET /lotes en AgroTrace Quevedo

| Paso | Componente | Clase Real (Framework) | Clase Real (Proyecto) | Archivo |
|------|------------|----------------------|----------------------|---------|
| 1 | Apertura TCP | Tomcat 11 (contenedor embebido) | — | application.yml (server.port: 8080) |
| 2 | Construccion de request/response | Tomcat 11 (Servlet 6.1) | — | Tomcat embebido en Spring Boot |
| 3 | Cadena de filtros | `OncePerRequestFilter` (Spring) | — | Filtros configurados en la app |
| 4 | Front Controller | `DispatcherServlet` (Spring MVC) | — | org.springframework.web.servlet.DispatcherServlet |
| 5 | Resolucion de ruta | `HandlerMapping` (Spring MVC) | `LoteWebController` | ec.edu.uteq.agrotrace.lote.web.LoteWebController |
| 6 | Invocacion del controlador | `HandlerAdapter` (Spring MVC) | `LoteWebController.listar()` | ec.edu.uteq.agrotrace.lote.web.LoteWebController:30 |
| 7 | Logica de negocio y datos | `EntityManager` (Hibernate/JPA) | `LoteService.buscar()` → `LoteRepository.findByEstado()` | ec.edu.uteq.agrotrace.lote.service.LoteService:40 |
| 8 | Renderizado de vista | `ViewResolver` + Thymeleaf | `lotes/lista.html` | src/main/resources/templates/lotes/lista.html |
| 9 | Respuesta HTTP | Tomcat 11 | — | HTML renderizado enviado al navegador |

## Diagrama de Flujo

```
Navegador → TCP:8080
    ↓
Tomcat 11 → HttpServletRequest/Response
    ↓
Filtros (JwtAuthFilter, etc.)
    ↓
DispatcherServlet (Front Controller)
    ↓
HandlerMapping → LoteWebController.listar()
    ↓
HandlerAdapter → invoca metodo
    ↓
LoteWebController → LoteService.buscar(estado)
    ↓
LoteRepository → Hibernate/JPA → PostgreSQL
    ↓
Retorna List<Lote> al controller
    ↓
Controller agrega al Model y retorna nombre de vista
    ↓
ViewResolver → Thymeleaf renderiza lotes/lista.html
    ↓
Tomcat escribe respuesta HTTP
    ↓
Navegador muestra tabla de lotes
```

## Justificacion del Patron MVC en AgroTrace

AgroTrace es una aplicacion de gestion con:
- **Formularios** de registro de lotes
- **Flujos de trabajo** (registro → evaluación → certificación)
- **Usuarios autenticados** (tecnicos de acopio)
- **Contenido que cambia por navegacion** (no por interaccion continua)

Esto justifica MVC en servidor sobre una interfaz reactiva del lado del cliente.

## Referencias
- Reenskaug, T. (1979). Thing-Model-View-Editor
- Krasner & Pope (1986). A Cookbook for Using the Model-View-Controller User Interface Paradigm
- Fowler, M. (2000). Patterns of Enterprise Application Architecture - Model 2
- Spring Boot 4.1.1 Documentation
