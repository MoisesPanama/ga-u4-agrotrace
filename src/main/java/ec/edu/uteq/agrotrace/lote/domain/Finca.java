package ec.edu.uteq.agrotrace.lote.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "finca")
public class Finca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 200)
    private String ubicacion;

    @Column(name = "hectareas")
    private Double hectareas;

    @Column(name = "productor_nombre", nullable = false, length = 150)
    private String productorNombre;

    @Column(name = "productor_cedula", nullable = false, length = 20)
    private String productorCedula;

    @OneToMany(mappedBy = "finca", cascade = CascadeType.ALL)
    private List<Lote> lotes = new ArrayList<>();

    public Finca() {}

    public Finca(String nombre, String productorNombre, String productorCedula) {
        this.nombre = nombre;
        this.productorNombre = productorNombre;
        this.productorCedula = productorCedula;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Double getHectareas() { return hectareas; }
    public void setHectareas(Double hectareas) { this.hectareas = hectareas; }

    public String getProductorNombre() { return productorNombre; }
    public void setProductorNombre(String productorNombre) { this.productorNombre = productorNombre; }

    public String getProductorCedula() { return productorCedula; }
    public void setProductorCedula(String productorCedula) { this.productorCedula = productorCedula; }

    public List<Lote> getLotes() { return lotes; }
    public void setLotes(List<Lote> lotes) { this.lotes = lotes; }
}
