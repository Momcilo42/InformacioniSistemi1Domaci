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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Momcilo
 */
@Entity
@Table(name = "rezervacije")
@NamedQueries({
    @NamedQuery(name = "Rezervacije.findAll", query = "SELECT r FROM Rezervacije r")
    , @NamedQuery(name = "Rezervacije.findByIdRez", query = "SELECT r FROM Rezervacije r WHERE r.idRez = :idRez")
    , @NamedQuery(name = "Rezervacije.findByKolicina", query = "SELECT r FROM Rezervacije r WHERE r.kolicina = :kolicina")
    , @NamedQuery(name = "Rezervacije.findByVreme", query = "SELECT r FROM Rezervacije r WHERE r.vreme = :vreme")})
public class Rezervacije implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdRez")
    private Integer idRez;
    @Column(name = "Kolicina")
    private Integer kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @JoinColumn(name = "Korisnik", referencedColumnName = "IdKor")
    @ManyToOne
    private Korisnik korisnik;
    @JoinColumn(name = "Proizvod", referencedColumnName = "IdPrz")
    @ManyToOne(optional = false)
    private Proizvodi proizvod;
    @JoinColumn(name = "Prodavnica", referencedColumnName = "IdPrd")
    @ManyToOne(optional = false)
    private Prodavnice prodavnica;

    public Rezervacije() {
    }

    public Rezervacije(Integer idRez) {
        this.idRez = idRez;
    }

    public Rezervacije(Integer idRez, Date vreme) {
        this.idRez = idRez;
        this.vreme = vreme;
    }

    public Integer getIdRez() {
        return idRez;
    }

    public void setIdRez(Integer idRez) {
        this.idRez = idRez;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Proizvodi getProizvod() {
        return proizvod;
    }

    public void setProizvod(Proizvodi proizvod) {
        this.proizvod = proizvod;
    }

    public Prodavnice getProdavnica() {
        return prodavnica;
    }

    public void setProdavnica(Prodavnice prodavnica) {
        this.prodavnica = prodavnica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRez != null ? idRez.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rezervacije)) {
            return false;
        }
        Rezervacije other = (Rezervacije) object;
        if ((this.idRez == null && other.idRez != null) || (this.idRez != null && !this.idRez.equals(other.idRez))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Rezervacije[ idRez=" + idRez + " ]";
    }
    
}
