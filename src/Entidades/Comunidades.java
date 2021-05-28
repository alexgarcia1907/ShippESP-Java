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
@Table(name = "comunidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comunidades.findAll", query = "SELECT c FROM Comunidades c")
    , @NamedQuery(name = "Comunidades.findByCcom", query = "SELECT c FROM Comunidades c WHERE c.ccom = :ccom")
    , @NamedQuery(name = "Comunidades.findByCom", query = "SELECT c FROM Comunidades c WHERE c.com = :com")})
public class Comunidades implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CCOM")
    private Long ccom;
    @Basic(optional = false)
    @Column(name = "COM")
    private String com;

    public Comunidades() {
    }

    public Comunidades(Long ccom) {
        this.ccom = ccom;
    }

    public Comunidades(Long ccom, String com) {
        this.ccom = ccom;
        this.com = com;
    }

    public Long getCcom() {
        return ccom;
    }

    public void setCcom(Long ccom) {
        this.ccom = ccom;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ccom != null ? ccom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comunidades)) {
            return false;
        }
        Comunidades other = (Comunidades) object;
        if ((this.ccom == null && other.ccom != null) || (this.ccom != null && !this.ccom.equals(other.ccom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Comunidades[ ccom=" + ccom + " ]";
    }
    
}
