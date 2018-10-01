/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Momcilo
 */
@Embeddable
public class PrometPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Prodavnica")
    private int prodavnica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Datum")
    @Temporal(TemporalType.DATE)
    private Date datum;

    public PrometPK() {
    }

    public PrometPK(int prodavnica, Date datum) {
        this.prodavnica = prodavnica;
        this.datum = datum;
    }

    public int getProdavnica() {
        return prodavnica;
    }

    public void setProdavnica(int prodavnica) {
        this.prodavnica = prodavnica;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) prodavnica;
        hash += (datum != null ? datum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrometPK)) {
            return false;
        }
        PrometPK other = (PrometPK) object;
        if (this.prodavnica != other.prodavnica) {
            return false;
        }
        if ((this.datum == null && other.datum != null) || (this.datum != null && !this.datum.equals(other.datum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.PrometPK[ prodavnica=" + prodavnica + ", datum=" + datum + " ]";
    }
    
}
