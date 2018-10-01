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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "proizvodi")
@NamedQueries({
    @NamedQuery(name = "Proizvodi.findAll", query = "SELECT p FROM Proizvodi p")
    , @NamedQuery(name = "Proizvodi.findByIdPrz", query = "SELECT p FROM Proizvodi p WHERE p.idPrz = :idPrz")
    , @NamedQuery(name = "Proizvodi.findByNaziv", query = "SELECT p FROM Proizvodi p WHERE p.naziv = :naziv")
    , @NamedQuery(name = "Proizvodi.findByCena", query = "SELECT p FROM Proizvodi p WHERE p.cena = :cena")})
public class Proizvodi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPrz")
    private Integer idPrz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private float cena;
    @JoinColumn(name = "Tip", referencedColumnName = "IdTip")
    @ManyToOne(optional = false)
    private Tip tip;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proizvod")
    private Collection<Rezervacije> rezervacijeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proizvodi")
    private Collection<NaLageru> naLageruCollection;

    public Proizvodi() {
    }

    public Proizvodi(Integer idPrz) {
        this.idPrz = idPrz;
    }

    public Proizvodi(Integer idPrz, String naziv, float cena) {
        this.idPrz = idPrz;
        this.naziv = naziv;
        this.cena = cena;
    }

    public Integer getIdPrz() {
        return idPrz;
    }

    public void setIdPrz(Integer idPrz) {
        this.idPrz = idPrz;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public Tip getTip() {
        return tip;
    }

    public void setTip(Tip tip) {
        this.tip = tip;
    }

    public Collection<Rezervacije> getRezervacijeCollection() {
        return rezervacijeCollection;
    }

    public void setRezervacijeCollection(Collection<Rezervacije> rezervacijeCollection) {
        this.rezervacijeCollection = rezervacijeCollection;
    }

    public Collection<NaLageru> getNaLageruCollection() {
        return naLageruCollection;
    }

    public void setNaLageruCollection(Collection<NaLageru> naLageruCollection) {
        this.naLageruCollection = naLageruCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrz != null ? idPrz.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proizvodi)) {
            return false;
        }
        Proizvodi other = (Proizvodi) object;
        if ((this.idPrz == null && other.idPrz != null) || (this.idPrz != null && !this.idPrz.equals(other.idPrz))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Proizvodi[ idPrz=" + idPrz + " ]";
    }
    
}
