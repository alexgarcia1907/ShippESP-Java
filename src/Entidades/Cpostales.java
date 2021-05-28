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
@Table(name = "cpostales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cpostales.findAll", query = "SELECT c FROM Cpostales c")
    , @NamedQuery(name = "Cpostales.findById", query = "SELECT c FROM Cpostales c WHERE c.id = :id")
    , @NamedQuery(name = "Cpostales.findByCpob", query = "SELECT c FROM Cpostales c WHERE c.cpob = :cpob")
    , @NamedQuery(name = "Cpostales.findByCp", query = "SELECT c FROM Cpostales c WHERE c.cp = :cp")})
public class Cpostales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "CPOB")
    private long cpob;
    @Basic(optional = false)
    @Column(name = "CP")
    private long cp;

    public Cpostales() {
    }

    public Cpostales(Long id) {
        this.id = id;
    }

    public Cpostales(Long id, long cpob, long cp) {
        this.id = id;
        this.cpob = cpob;
        this.cp = cp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCpob() {
        return cpob;
    }

    public void setCpob(long cpob) {
        this.cpob = cpob;
    }

    public long getCp() {
        return cp;
    }

    public void setCp(long cp) {
        this.cp = cp;
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
        if (!(object instanceof Cpostales)) {
            return false;
        }
        Cpostales other = (Cpostales) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cpostales[ id=" + id + " ]";
    }
    
}
