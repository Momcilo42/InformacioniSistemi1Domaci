/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Momcilo
 */
@Entity
@Table(name = "Artikal")
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a")
    , @NamedQuery(name = "Artikal.findByIdAr", query = "SELECT a FROM Artikal a WHERE a.idAr = :idAr")
    , @NamedQuery(name = "Artikal.findByNaziv", query = "SELECT a FROM Artikal a WHERE a.naziv = :naziv")
    , @NamedQuery(name = "Artikal.findByTip", query = "SELECT a FROM Artikal a WHERE a.tip = :tip")
    , @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena")
    , @NamedQuery(name = "Artikal.findByVreme", query = "SELECT a FROM Artikal a WHERE a.vreme = :vreme")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdAr")
    private Integer idAr;
    @Size(max = 20)
    @Column(name = "Naziv")
    private String naziv;
    @Column(name = "Tip")
    private Integer tip;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cena")
    private Float cena;
    @Column(name = "Vreme")
    private Float vreme;

    public Artikal() {
    }

    public Artikal(Integer idAr) {
        this.idAr = idAr;
    }

    public Integer getIdAr() {
        return idAr;
    }

    public void setIdAr(Integer idAr) {
        this.idAr = idAr;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getTip() {
        return tip;
    }

    public void setTip(Integer tip) {
        this.tip = tip;
    }

    public Float getCena() {
        return cena;
    }

    public void setCena(Float cena) {
        this.cena = cena;
    }

    public Float getVreme() {
        return vreme;
    }

    public void setVreme(Float vreme) {
        this.vreme = vreme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAr != null ? idAr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.idAr == null && other.idAr != null) || (this.idAr != null && !this.idAr.equals(other.idAr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Artikal[ idAr=" + idAr + " ]";
    }
    
}
