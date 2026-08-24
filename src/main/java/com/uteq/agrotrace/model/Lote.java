package com.uteq.agrotrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lote")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO-GA-01: Completar campos de la entidad con validaciones
    @NotBlank(message = "El codigo es obligatorio")
    @Column(unique = true, nullable = false, length = 50)
    private String codigo;

    @NotBlank(message = "El nombre del productor es obligatorio")
    @Column(name = "productor_nombre", nullable = false, length = 150)
    private String productorNombre;

    @NotBlank(message = "La cedula del productor es obligatoria")
    @Column(name = "productor_cedula", nullable = false, length = 20)
    private String productorCedula;

    @Column(name = "finca_nombre", length = 100)
    private String fincaNombre;

    @Column(name = "finca_ubicacion", length = 200)
    private String fincaUbicacion;

    @Column(length = 50)
    private String variedad = "CRILLO";

    @DecimalMin(value = "0.5", message = "Minimo 0.5 hectareas")
    private BigDecimal hectareas;

    @NotNull(message = "La fecha de cosecha es obligatoria")
    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;

    @Column(name = "fecha_procesamiento")
    private LocalDate fechaProcesamiento;

    @NotNull(message = "El peso es obligatorio")
    @DecimalMin(value = "1.0", message = "Minimo 1 kg")
    @Column(nullable = false)
    private BigDecimal pesoKg;

    // TODO-GA-02: Agregar reglas de negocio para humedad y fermentacion
    @DecimalMin(value = "5.0", message = "Humedad minima 5%")
    @DecimalMax(value = "12.0", message = "Humedad maxima 12%")
    @Column(name = "humedad_porcentaje")
    private BigDecimal humedadPorcentaje;

    @Min(value = 100, message = "Minimo 100 horas de fermentacion")
    @Max(value = 336, message = "Maximo 336 horas de fermentacion")
    @Column(name = "fermentacion_horas")
    private Integer fermentacionHoras;

    @Column(name = "temperatura_maxima")
    private BigDecimal temperaturaMaxima;

    @Column(name = "temperatura_minima")
    private BigDecimal temperaturaMinima;

    @Column(nullable = false, length = 30)
    private String estado = "REGISTRADO";

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

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getProductorNombre() { return productorNombre; }
    public void setProductorNombre(String productorNombre) { this.productorNombre = productorNombre; }

    public String getProductorCedula() { return productorCedula; }
    public void setProductorCedula(String productorCedula) { this.productorCedula = productorCedula; }

    public String getFincaNombre() { return fincaNombre; }
    public void setFincaNombre(String fincaNombre) { this.fincaNombre = fincaNombre; }

    public String getFincaUbicacion() { return fincaUbicacion; }
    public void setFincaUbicacion(String fincaUbicacion) { this.fincaUbicacion = fincaUbicacion; }

    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }

    public BigDecimal getHectareas() { return hectareas; }
    public void setHectareas(BigDecimal hectareas) { this.hectareas = hectareas; }

    public LocalDate getFechaCosecha() { return fechaCosecha; }
    public void setFechaCosecha(LocalDate fechaCosecha) { this.fechaCosecha = fechaCosecha; }

    public LocalDate getFechaProcesamiento() { return fechaProcesamiento; }
    public void setFechaProcesamiento(LocalDate fechaProcesamiento) { this.fechaProcesamiento = fechaProcesamiento; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public BigDecimal getHumedadPorcentaje() { return humedadPorcentaje; }
    public void setHumedadPorcentaje(BigDecimal humedadPorcentaje) { this.humedadPorcentaje = humedadPorcentaje; }

    public Integer getFermentacionHoras() { return fermentacionHoras; }
    public void setFermentacionHoras(Integer fermentacionHoras) { this.fermentacionHoras = fermentacionHoras; }

    public BigDecimal getTemperaturaMaxima() { return temperaturaMaxima; }
    public void setTemperaturaMaxima(BigDecimal temperaturaMaxima) { this.temperaturaMaxima = temperaturaMaxima; }

    public BigDecimal getTemperaturaMinima() { return temperaturaMinima; }
    public void setTemperaturaMinima(BigDecimal temperaturaMinima) { this.temperaturaMinima = temperaturaMinima; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Boolean getCertificado() { return certificado; }
    public void setCertificado(Boolean certificado) { this.certificado = certificado; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
