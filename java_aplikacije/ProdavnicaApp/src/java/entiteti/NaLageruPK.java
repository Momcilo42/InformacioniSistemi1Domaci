/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Momcilo
 */
@Embeddable
public class NaLageruPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Prodavnica")
    private int prodavnica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Proizvod")
    private int proizvod;

    public NaLageruPK() {
    }

    public NaLageruPK(int prodavnica, int proizvod) {
        this.prodavnica = prodavnica;
        this.proizvod = proizvod;
    }

    public int getProdavnica() {
        return prodavnica;
    }

    public void setProdavnica(int prodavnica) {
        this.prodavnica = prodavnica;
    }

    public int getProizvod() {
        return proizvod;
    }

    public void setProizvod(int proizvod) {
        this.proizvod = proizvod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) prodavnica;
        hash += (int) proizvod;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NaLageruPK)) {
            return false;
        }
        NaLageruPK other = (NaLageruPK) object;
        if (this.prodavnica != other.prodavnica) {
            return false;
        }
        if (this.proizvod != other.proizvod) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.NaLageruPK[ prodavnica=" + prodavnica + ", proizvod=" + proizvod + " ]";
    }
    
}
