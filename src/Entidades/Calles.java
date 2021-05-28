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
@Table(name = "calles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calles.findAll", query = "SELECT c FROM Calles c")
    , @NamedQuery(name = "Calles.findById", query = "SELECT c FROM Calles c WHERE c.id = :id")
    , @NamedQuery(name = "Calles.findByIDPostal", query = "SELECT c FROM Calles c WHERE c.iDPostal = :iDPostal")
    , @NamedQuery(name = "Calles.findByCvia", query = "SELECT c FROM Calles c WHERE c.cvia = :cvia")
    , @NamedQuery(name = "Calles.findByNvia", query = "SELECT c FROM Calles c WHERE c.nvia = :nvia")
    , @NamedQuery(name = "Calles.findByTvia", query = "SELECT c FROM Calles c WHERE c.tvia = :tvia")})
public class Calles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "IDPostal")
    private long iDPostal;
    @Basic(optional = false)
    @Column(name = "CVIA")
    private long cvia;
    @Basic(optional = false)
    @Column(name = "NVIA")
    private String nvia;
    @Basic(optional = false)
    @Column(name = "TVIA")
    private String tvia;

    public Calles() {
    }

    public Calles(Long id) {
        this.id = id;
    }

    public Calles(Long id, long iDPostal, long cvia, String nvia, String tvia) {
        this.id = id;
        this.iDPostal = iDPostal;
        this.cvia = cvia;
        this.nvia = nvia;
        this.tvia = tvia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIDPostal() {
        return iDPostal;
    }

    public void setIDPostal(long iDPostal) {
        this.iDPostal = iDPostal;
    }

    public long getCvia() {
        return cvia;
    }

    public void setCvia(long cvia) {
        this.cvia = cvia;
    }

    public String getNvia() {
        return nvia;
    }

    public void setNvia(String nvia) {
        this.nvia = nvia;
    }

    public String getTvia() {
        return tvia;
    }

    public void setTvia(String tvia) {
        this.tvia = tvia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calles)) {
            return false;
        }
        Calles other = (Calles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Calles[ id=" + id + " ]";
    }
    
}
