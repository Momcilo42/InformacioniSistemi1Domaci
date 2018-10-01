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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Momcilo
 */
@Entity
@Table(name = "menadzeri")
@NamedQueries({
    @NamedQuery(name = "Menadzeri.findAll", query = "SELECT m FROM Menadzeri m")
    , @NamedQuery(name = "Menadzeri.findByIdMen", query = "SELECT m FROM Menadzeri m WHERE m.idMen = :idMen")
    , @NamedQuery(name = "Menadzeri.findByIdPro", query = "SELECT m FROM Menadzeri m WHERE m.idPro = :idPro")
    , @NamedQuery(name = "Menadzeri.findByIdKor", query = "SELECT m FROM Menadzeri m WHERE m.idKor = :idKor")})
public class Menadzeri implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdMen")
    private Integer idMen;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdPro")
    private int idPro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdKor")
    private int idKor;

    public Menadzeri() {
    }

    public Menadzeri(Integer idMen) {
        this.idMen = idMen;
    }

    public Menadzeri(Integer idMen, int idPro, int idKor) {
        this.idMen = idMen;
        this.idPro = idPro;
        this.idKor = idKor;
    }

    public Integer getIdMen() {
        return idMen;
    }

    public void setIdMen(Integer idMen) {
        this.idMen = idMen;
    }

    public int getIdPro() {
        return idPro;
    }

    public void setIdPro(int idPro) {
        this.idPro = idPro;
    }

    public int getIdKor() {
        return idKor;
    }

    public void setIdKor(int idKor) {
        this.idKor = idKor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMen != null ? idMen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menadzeri)) {
            return false;
        }
        Menadzeri other = (Menadzeri) object;
        if ((this.idMen == null && other.idMen != null) || (this.idMen != null && !this.idMen.equals(other.idMen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Menadzeri[ idMen=" + idMen + " ]";
    }
    
}
