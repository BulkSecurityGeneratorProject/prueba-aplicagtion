package zb.aplication.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_proveedor")
    private String idProveedor;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @OneToMany(mappedBy = "proveedor")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Articulo> idProveedors = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public Proveedor idProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
        return this;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public Proveedor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Proveedor direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<Articulo> getIdProveedors() {
        return idProveedors;
    }

    public Proveedor idProveedors(Set<Articulo> articulos) {
        this.idProveedors = articulos;
        return this;
    }

    public Proveedor addIdProveedor(Articulo articulo) {
        this.idProveedors.add(articulo);
        articulo.setProveedor(this);
        return this;
    }

    public Proveedor removeIdProveedor(Articulo articulo) {
        this.idProveedors.remove(articulo);
        articulo.setProveedor(null);
        return this;
    }

    public void setIdProveedors(Set<Articulo> articulos) {
        this.idProveedors = articulos;
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
        Proveedor proveedor = (Proveedor) o;
        if (proveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proveedor{" +
            "id=" + getId() +
            ", idProveedor='" + getIdProveedor() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
