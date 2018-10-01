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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Momcilo
 */
@Entity
@Table(name = "promet")
@NamedQueries({
    @NamedQuery(name = "Promet.findAll", query = "SELECT p FROM Promet p")
    , @NamedQuery(name = "Promet.findByProdavnica", query = "SELECT p FROM Promet p WHERE p.prometPK.prodavnica = :prodavnica")
    , @NamedQuery(name = "Promet.findByDatum", query = "SELECT p FROM Promet p WHERE p.prometPK.datum = :datum")
    , @NamedQuery(name = "Promet.findByKolicina", query = "SELECT p FROM Promet p WHERE p.kolicina = :kolicina")
    , @NamedQuery(name = "Promet.findByUkupno", query = "SELECT p FROM Promet p WHERE p.ukupno = :ukupno")})
public class Promet implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrometPK prometPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kolicina")
    private int kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ukupno")
    private float ukupno;
    @JoinColumn(name = "Prodavnica", referencedColumnName = "IdPrd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Prodavnice prodavnice;

    public Promet() {
    }

    public Promet(PrometPK prometPK) {
        this.prometPK = prometPK;
    }

    public Promet(PrometPK prometPK, int kolicina, float ukupno) {
        this.prometPK = prometPK;
        this.kolicina = kolicina;
        this.ukupno = ukupno;
    }

    public Promet(int prodavnica, Date datum) {
        this.prometPK = new PrometPK(prodavnica, datum);
    }

    public PrometPK getPrometPK() {
        return prometPK;
    }

    public void setPrometPK(PrometPK prometPK) {
        this.prometPK = prometPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public float getUkupno() {
        return ukupno;
    }

    public void setUkupno(float ukupno) {
        this.ukupno = ukupno;
    }

    public Prodavnice getProdavnice() {
        return prodavnice;
    }

    public void setProdavnice(Prodavnice prodavnice) {
        this.prodavnice = prodavnice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prometPK != null ? prometPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promet)) {
            return false;
        }
        Promet other = (Promet) object;
        if ((this.prometPK == null && other.prometPK != null) || (this.prometPK != null && !this.prometPK.equals(other.prometPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Promet[ prometPK=" + prometPK + " ]";
    }
    
}
