package ec.edu.uteq.agrotrace.lote.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lote")
public class Lote {

    private static final BigDecimal UMBRAL_HUMEDAD = new BigDecimal("7.5");
    private static final BigDecimal UMBRAL_FERMENTACION = new BigDecimal("60.0");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El codigo es obligatorio")
    @Column(unique = true, nullable = false, length = 50)
    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "finca_id")
    private Finca finca;

    @NotNull(message = "La fecha de recepcion es obligatoria")
    @Column(name = "fecha_recepcion", nullable = false)
    private LocalDate fechaRecepcion;

    @NotNull(message = "El peso es obligatorio")
    @DecimalMin(value = "1.0", message = "Minimo 1 kg")
    @Column(nullable = false)
    private BigDecimal pesoKg;

    // TODO-GA-01: Campo de fermentacion en porcentaje (no horas)
    @DecimalMin(value = "0.0", message = "Minimo 0%")
    @DecimalMax(value = "100.0", message = "Maximo 100%")
    @Column(name = "fermentacion_porcentaje")
    private BigDecimal fermentacionPorcentaje;

    @DecimalMin(value = "0.0", message = "Minimo 0%")
    @DecimalMax(value = "100.0", message = "Maximo 100%")
    @Column(name = "humedad_porcentaje")
    private BigDecimal humedadPorcentaje;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoLote estado = EstadoLote.REGISTRADO;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @Column(nullable = false)
    private Boolean certificado = false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // TODO-GA-01: Politica de recepcion de APROCAFA
    public EstadoLote evaluarEstado() {
        if (this.humedadPorcentaje == null || this.fermentacionPorcentaje == null) {
            throw new IllegalStateException(
                "No se puede evaluar un lote sin humedad y fermentacion medidas");
        }
        if (this.humedadPorcentaje.compareTo(UMBRAL_HUMEDAD) > 0) {
            return EstadoLote.SECADO_ADICIONAL;
        }
        if (this.fermentacionPorcentaje.compareTo(UMBRAL_FERMENTACION) < 0) {
            return EstadoLote.RECHAZADO;
        }
        return EstadoLote.ACEPTADO;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public Finca getFinca() { return finca; }
    public void setFinca(Finca finca) { this.finca = finca; }

    public LocalDate getFechaRecepcion() { return fechaRecepcion; }
    public void setFechaRecepcion(LocalDate fechaRecepcion) { this.fechaRecepcion = fechaRecepcion; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public BigDecimal getFermentacionPorcentaje() { return fermentacionPorcentaje; }
    public void setFermentacionPorcentaje(BigDecimal fermentacionPorcentaje) { this.fermentacionPorcentaje = fermentacionPorcentaje; }

    public BigDecimal getHumedadPorcentaje() { return humedadPorcentaje; }
    public void setHumedadPorcentaje(BigDecimal humedadPorcentaje) { this.humedadPorcentaje = humedadPorcentaje; }

    public EstadoLote getEstado() { return estado; }
    public void setEstado(EstadoLote estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Boolean getCertificado() { return certificado; }
    public void setCertificado(Boolean certificado) { this.certificado = certificado; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
