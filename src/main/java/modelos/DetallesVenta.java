/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edgar
 */
@Entity
@Table(name = "detalles_venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetallesVenta.findAll", query = "SELECT d FROM DetallesVenta d"),
    @NamedQuery(name = "DetallesVenta.findByIdDetalle", query = "SELECT d FROM DetallesVenta d WHERE d.idDetalle = :idDetalle"),
    @NamedQuery(name = "DetallesVenta.findByProducto", query = "SELECT d FROM DetallesVenta d WHERE d.producto = :producto"),
    @NamedQuery(name = "DetallesVenta.findByCantidad", query = "SELECT d FROM DetallesVenta d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetallesVenta.findByPrecioUnitario", query = "SELECT d FROM DetallesVenta d WHERE d.precioUnitario = :precioUnitario")})
public class DetallesVenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    @Column(name = "producto")
    private String producto;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_unitario")
    private BigDecimal precioUnitario;
    @JoinColumn(name = "id_venta", referencedColumnName = "id_venta")
    @ManyToOne
    private Ventas idVenta;

    public DetallesVenta() {
    }

    public DetallesVenta(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Ventas getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Ventas idVenta) {
        this.idVenta = idVenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalle != null ? idDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallesVenta)) {
            return false;
        }
        DetallesVenta other = (DetallesVenta) object;
        if ((this.idDetalle == null && other.idDetalle != null) || (this.idDetalle != null && !this.idDetalle.equals(other.idDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.DetallesVenta[ idDetalle=" + idDetalle + " ]";
    }
    
}
