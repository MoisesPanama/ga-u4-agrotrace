package ec.edu.uteq.agrotrace.common.exception;

public class LoteNoEncontradoException extends RuntimeException {

    private final String codigo;

    public LoteNoEncontradoException(String codigo) {
        super("Lote no encontrado con codigo: " + codigo);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
}
