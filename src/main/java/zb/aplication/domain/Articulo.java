package zb.aplication.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Articulo.
 */
@Entity
@Table(name = "articulo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "valor_unidad")
    private Integer valorUnidad;

    @Column(name = "id_proveedor")
    private String idProveedor;

    @ManyToOne
    @JsonIgnoreProperties("idProveedors")
    private Proveedor proveedor;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Articulo codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Articulo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Articulo ubicacion(String ubicacion) {
        this.ubicacion = ubicacion;
        return this;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUnidad() {
        return unidad;
    }

    public Articulo unidad(String unidad) {
        this.unidad = unidad;
        return this;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public Integer getValorUnidad() {
        return valorUnidad;
    }

    public Articulo valorUnidad(Integer valorUnidad) {
        this.valorUnidad = valorUnidad;
        return this;
    }

    public void setValorUnidad(Integer valorUnidad) {
        this.valorUnidad = valorUnidad;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public Articulo idProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
        return this;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Articulo proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Articulo articulo = (Articulo) o;
        if (articulo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), articulo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Articulo{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", ubicacion='" + getUbicacion() + "'" +
            ", unidad='" + getUnidad() + "'" +
            ", valorUnidad=" + getValorUnidad() +
            ", idProveedor='" + getIdProveedor() + "'" +
            "}";
    }
}
