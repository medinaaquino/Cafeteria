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
import javax.persistence.Lob;
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
@Table(name = "productos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productos.findAll", query = "SELECT p FROM Productos p"),
    @NamedQuery(name = "Productos.findByIDProducto", query = "SELECT p FROM Productos p WHERE p.iDProducto = :iDProducto"),
    @NamedQuery(name = "Productos.findByNombreProducto", query = "SELECT p FROM Productos p WHERE p.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "Productos.findByPrecio", query = "SELECT p FROM Productos p WHERE p.precio = :precio"),
    @NamedQuery(name = "Productos.findByTama\u00f1o", query = "SELECT p FROM Productos p WHERE p.tama\u00f1o = :tama\u00f1o"),
    @NamedQuery(name = "Productos.findByStock", query = "SELECT p FROM Productos p WHERE p.stock = :stock"),
    @NamedQuery(name = "Productos.findByPromoci\u00f3n", query = "SELECT p FROM Productos p WHERE p.promoci\u00f3n = :promoci\u00f3n")})
public class Productos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Producto")
    private Integer iDProducto;
    @Basic(optional = false)
    @Column(name = "Nombre_Producto")
    private String nombreProducto;
    @Lob
    @Column(name = "Descripci\u00f3n")
    private String descripción;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Precio")
    private BigDecimal precio;
    @Lob
    @Column(name = "Imagen_URL")
    private String imagenURL;
    @Column(name = "Tama\u00f1o")
    private String tamaño;
    @Column(name = "Stock")
    private Integer stock;
    @Column(name = "Promoci\u00f3n")
    private BigDecimal promoción;
    @JoinColumn(name = "ID_Categoria", referencedColumnName = "id_categoria")
    @ManyToOne(optional = false)
    private Categorias iDCategoria;
    @JoinColumn(name = "ID_Usuario", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario iDUsuario;

    public Productos() {
    }

    public Productos(Integer iDProducto) {
        this.iDProducto = iDProducto;
    }

    public Productos(Integer iDProducto, String nombreProducto, BigDecimal precio) {
        this.iDProducto = iDProducto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
    }

    public Integer getIDProducto() {
        return iDProducto;
    }

    public void setIDProducto(Integer iDProducto) {
        this.iDProducto = iDProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPromoción() {
        return promoción;
    }

    public void setPromoción(BigDecimal promoción) {
        this.promoción = promoción;
    }

    public Categorias getIDCategoria() {
        return iDCategoria;
    }

    public void setIDCategoria(Categorias iDCategoria) {
        this.iDCategoria = iDCategoria;
    }

    public Usuario getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuario iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDProducto != null ? iDProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productos)) {
            return false;
        }
        Productos other = (Productos) object;
        if ((this.iDProducto == null && other.iDProducto != null) || (this.iDProducto != null && !this.iDProducto.equals(other.iDProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelos.Productos[ iDProducto=" + iDProducto + " ]";
    }
    
}
