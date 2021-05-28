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
@Table(name = "poblaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Poblaciones.findAll", query = "SELECT p FROM Poblaciones p")
    , @NamedQuery(name = "Poblaciones.findByCmum", query = "SELECT p FROM Poblaciones p WHERE p.cmum = :cmum")
    , @NamedQuery(name = "Poblaciones.findByCpob", query = "SELECT p FROM Poblaciones p WHERE p.cpob = :cpob")
    , @NamedQuery(name = "Poblaciones.findByNentsi50", query = "SELECT p FROM Poblaciones p WHERE p.nentsi50 = :nentsi50")})
public class Poblaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "CMUM")
    private long cmum;
    @Id
    @Basic(optional = false)
    @Column(name = "CPOB")
    private Long cpob;
    @Basic(optional = false)
    @Column(name = "NENTSI50")
    private String nentsi50;

    public Poblaciones() {
    }

    public Poblaciones(Long cpob) {
        this.cpob = cpob;
    }

    public Poblaciones(Long cpob, long cmum, String nentsi50) {
        this.cpob = cpob;
        this.cmum = cmum;
        this.nentsi50 = nentsi50;
    }

    public long getCmum() {
        return cmum;
    }

    public void setCmum(long cmum) {
        this.cmum = cmum;
    }

    public Long getCpob() {
        return cpob;
    }

    public void setCpob(Long cpob) {
        this.cpob = cpob;
    }

    public String getNentsi50() {
        return nentsi50;
    }

    public void setNentsi50(String nentsi50) {
        this.nentsi50 = nentsi50;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpob != null ? cpob.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Poblaciones)) {
            return false;
        }
        Poblaciones other = (Poblaciones) object;
        if ((this.cpob == null && other.cpob != null) || (this.cpob != null && !this.cpob.equals(other.cpob))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Poblaciones[ cpob=" + cpob + " ]";
    }
    
}
