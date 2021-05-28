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
@Table(name = "provincias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provincias.findAll", query = "SELECT p FROM Provincias p")
    , @NamedQuery(name = "Provincias.findByCcom", query = "SELECT p FROM Provincias p WHERE p.ccom = :ccom")
    , @NamedQuery(name = "Provincias.findByCpro", query = "SELECT p FROM Provincias p WHERE p.cpro = :cpro")
    , @NamedQuery(name = "Provincias.findByPro", query = "SELECT p FROM Provincias p WHERE p.pro = :pro")})
public class Provincias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "CCOM")
    private long ccom;
    @Id
    @Basic(optional = false)
    @Column(name = "CPRO")
    private Long cpro;
    @Basic(optional = false)
    @Column(name = "PRO")
    private String pro;

    public Provincias() {
    }

    public Provincias(Long cpro) {
        this.cpro = cpro;
    }

    public Provincias(Long cpro, long ccom, String pro) {
        this.cpro = cpro;
        this.ccom = ccom;
        this.pro = pro;
    }

    public long getCcom() {
        return ccom;
    }

    public void setCcom(long ccom) {
        this.ccom = ccom;
    }

    public Long getCpro() {
        return cpro;
    }

    public void setCpro(Long cpro) {
        this.cpro = cpro;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpro != null ? cpro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provincias)) {
            return false;
        }
        Provincias other = (Provincias) object;
        if ((this.cpro == null && other.cpro != null) || (this.cpro != null && !this.cpro.equals(other.cpro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Provincias[ cpro=" + cpro + " ]";
    }
    
}
