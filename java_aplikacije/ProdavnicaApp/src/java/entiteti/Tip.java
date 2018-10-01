/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Momcilo
 */
@Entity
@Table(name = "tip")
@NamedQueries({
    @NamedQuery(name = "Tip.findAll", query = "SELECT t FROM Tip t")
    , @NamedQuery(name = "Tip.findByIdTip", query = "SELECT t FROM Tip t WHERE t.idTip = :idTip")
    , @NamedQuery(name = "Tip.findByNaziv", query = "SELECT t FROM Tip t WHERE t.naziv = :naziv")})
public class Tip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdTip")
    private Integer idTip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tip")
    private Collection<Proizvodi> proizvodiCollection;

    public Tip() {
    }

    public Tip(Integer idTip) {
        this.idTip = idTip;
    }

    public Tip(Integer idTip, String naziv) {
        this.idTip = idTip;
        this.naziv = naziv;
    }

    public Integer getIdTip() {
        return idTip;
    }

    public void setIdTip(Integer idTip) {
        this.idTip = idTip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Collection<Proizvodi> getProizvodiCollection() {
        return proizvodiCollection;
    }

    public void setProizvodiCollection(Collection<Proizvodi> proizvodiCollection) {
        this.proizvodiCollection = proizvodiCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTip != null ? idTip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tip)) {
            return false;
        }
        Tip other = (Tip) object;
        if ((this.idTip == null && other.idTip != null) || (this.idTip != null && !this.idTip.equals(other.idTip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Tip[ idTip=" + idTip + " ]";
    }
    
}
