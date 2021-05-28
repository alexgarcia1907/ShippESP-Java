/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Álex García
 */
@Entity
@Table(name = "municipios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipios.findAll", query = "SELECT m FROM Municipios m")
    , @NamedQuery(name = "Municipios.findByCmum", query = "SELECT m FROM Municipios m WHERE m.cmum = :cmum")
    , @NamedQuery(name = "Municipios.findByCpro", query = "SELECT m FROM Municipios m WHERE m.cpro = :cpro")
    , @NamedQuery(name = "Municipios.findByCun", query = "SELECT m FROM Municipios m WHERE m.cun = :cun")
    , @NamedQuery(name = "Municipios.findByDmun50", query = "SELECT m FROM Municipios m WHERE m.dmun50 = :dmun50")})
public class Municipios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CMUM")
    private Long cmum;
    @Basic(optional = false)
    @Column(name = "CPRO")
    private long cpro;
    @Basic(optional = false)
    @Column(name = "CUN")
    private String cun;
    @Basic(optional = false)
    @Column(name = "DMUN50")
    private String dmun50;

    public Municipios() {
    }

    public Municipios(Long cmum) {
        this.cmum = cmum;
    }

    public Municipios(Long cmum, long cpro, String cun, String dmun50) {
        this.cmum = cmum;
        this.cpro = cpro;
        this.cun = cun;
        this.dmun50 = dmun50;
    }

    public Long getCmum() {
        return cmum;
    }

    public void setCmum(Long cmum) {
        this.cmum = cmum;
    }

    public long getCpro() {
        return cpro;
    }

    public void setCpro(long cpro) {
        this.cpro = cpro;
    }

    public String getCun() {
        return cun;
    }

    public void setCun(String cun) {
        this.cun = cun;
    }

    public String getDmun50() {
        return dmun50;
    }

    public void setDmun50(String dmun50) {
        this.dmun50 = dmun50;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cmum != null ? cmum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipios)) {
            return false;
        }
        Municipios other = (Municipios) object;
        if ((this.cmum == null && other.cmum != null) || (this.cmum != null && !this.cmum.equals(other.cmum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Municipios[ cmum=" + cmum + " ]";
    }
    
}
