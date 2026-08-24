package ec.edu.uteq.agrotrace.common.api;

import ec.edu.uteq.agrotrace.common.exception.LoteNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

// TODO-GA-07: Manejador global de errores con RFC 9457 ProblemDetail
@RestControllerAdvice(basePackages = "ec.edu.uteq.agrotrace")
public class ManejadorGlobalErrores {

    @ExceptionHandler(LoteNoEncontradoException.class)
    public ProblemDetail loteNoEncontrado(LoteNoEncontradoException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        pd.setType(URI.create("https://agrotrace.uteq.edu.ec/errores/lote-no-encontrado"));
        pd.setTitle("Lote no encontrado");
        pd.setProperty("codigoBuscado", ex.getCodigo());
        pd.setProperty("marcaTiempo", Instant.now());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validacion(MethodArgumentNotValidException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "La peticion tiene campos invalidos");
        pd.setType(URI.create("https://agrotrace.uteq.edu.ec/errores/validacion"));
        pd.setTitle("Error de validacion");
        pd.setProperty("campos", ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        f -> f.getField(),
                        f -> f.getDefaultMessage() == null ? "invalido" : f.getDefaultMessage(),
                        (a, b) -> a)));
        return pd;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail errorGeneral(RuntimeException ex) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        pd.setType(URI.create("https://agrotrace.uteq.edu.ec/errores/error-interno"));
        pd.setTitle("Error interno del servidor");
        pd.setProperty("marcaTiempo", Instant.now());
        return pd;
    }
}
