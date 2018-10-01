/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
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
@Table(name = "na_lageru")
@NamedQueries({
    @NamedQuery(name = "NaLageru.findAll", query = "SELECT n FROM NaLageru n")
    , @NamedQuery(name = "NaLageru.findByProdavnica", query = "SELECT n FROM NaLageru n WHERE n.naLageruPK.prodavnica = :prodavnica")
    , @NamedQuery(name = "NaLageru.findByProizvod", query = "SELECT n FROM NaLageru n WHERE n.naLageruPK.proizvod = :proizvod")
    , @NamedQuery(name = "NaLageru.findByKolicina", query = "SELECT n FROM NaLageru n WHERE n.kolicina = :kolicina")})
public class NaLageru implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NaLageruPK naLageruPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kolicina")
    private int kolicina;
    @JoinColumn(name = "Prodavnica", referencedColumnName = "IdPrd", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Prodavnice prodavnice;
    @JoinColumn(name = "Proizvod", referencedColumnName = "IdPrz", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Proizvodi proizvodi;

    public NaLageru() {
    }

    public NaLageru(NaLageruPK naLageruPK) {
        this.naLageruPK = naLageruPK;
    }

    public NaLageru(NaLageruPK naLageruPK, int kolicina) {
        this.naLageruPK = naLageruPK;
        this.kolicina = kolicina;
    }

    public NaLageru(int prodavnica, int proizvod) {
        this.naLageruPK = new NaLageruPK(prodavnica, proizvod);
    }

    public NaLageruPK getNaLageruPK() {
        return naLageruPK;
    }

    public void setNaLageruPK(NaLageruPK naLageruPK) {
        this.naLageruPK = naLageruPK;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Prodavnice getProdavnice() {
        return prodavnice;
    }

    public void setProdavnice(Prodavnice prodavnice) {
        this.prodavnice = prodavnice;
    }

    public Proizvodi getProizvodi() {
        return proizvodi;
    }

    public void setProizvodi(Proizvodi proizvodi) {
        this.proizvodi = proizvodi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (naLageruPK != null ? naLageruPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NaLageru)) {
            return false;
        }
        NaLageru other = (NaLageru) object;
        if ((this.naLageruPK == null && other.naLageruPK != null) || (this.naLageruPK != null && !this.naLageruPK.equals(other.naLageruPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.NaLageru[ naLageruPK=" + naLageruPK + " ]";
    }
    
}
