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
import javax.validation.constraints.Size;

/**
 *
 * @author Momcilo
 */
@Entity
@Table(name = "prodavnice")
@NamedQueries({
    @NamedQuery(name = "Prodavnice.findAll", query = "SELECT p FROM Prodavnice p")
    , @NamedQuery(name = "Prodavnice.findByIdPrd", query = "SELECT p FROM Prodavnice p WHERE p.idPrd = :idPrd")
    , @NamedQuery(name = "Prodavnice.findByNaziv", query = "SELECT p FROM Prodavnice p WHERE p.naziv = :naziv")
    , @NamedQuery(name = "Prodavnice.findByAdresa", query = "SELECT p FROM Prodavnice p WHERE p.adresa = :adresa")})
public class Prodavnice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPrd")
    private Integer idPrd;
    @Size(max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Size(max = 45)
    @Column(name = "Adresa")
    private String adresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodavnice")
    private Collection<Promet> prometCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodavnica")
    private Collection<Rezervacije> rezervacijeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prodavnice")
    private Collection<NaLageru> naLageruCollection;

    public Prodavnice() {
    }

    public Prodavnice(Integer idPrd) {
        this.idPrd = idPrd;
    }

    public Integer getIdPrd() {
        return idPrd;
    }

    public void setIdPrd(Integer idPrd) {
        this.idPrd = idPrd;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Collection<Promet> getPrometCollection() {
        return prometCollection;
    }

    public void setPrometCollection(Collection<Promet> prometCollection) {
        this.prometCollection = prometCollection;
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
        hash += (idPrd != null ? idPrd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prodavnice)) {
            return false;
        }
        Prodavnice other = (Prodavnice) object;
        if ((this.idPrd == null && other.idPrd != null) || (this.idPrd != null && !this.idPrd.equals(other.idPrd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Prodavnice[ idPrd=" + idPrd + " ]";
    }
    
}
