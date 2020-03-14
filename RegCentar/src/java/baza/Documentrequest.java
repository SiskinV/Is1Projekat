/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baza;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vladimir Siskin
 */
@Entity
@Table(name = "documentrequest")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Documentrequest.findAll", query = "SELECT d FROM Documentrequest d"),
    @NamedQuery(name = "Documentrequest.findById", query = "SELECT d FROM Documentrequest d WHERE d.id = :id"),
    @NamedQuery(name = "Documentrequest.findByJmbg", query = "SELECT d FROM Documentrequest d WHERE d.jmbg = :jmbg"),
    @NamedQuery(name = "Documentrequest.findByIme", query = "SELECT d FROM Documentrequest d WHERE d.ime = :ime"),
    @NamedQuery(name = "Documentrequest.findByPrezime", query = "SELECT d FROM Documentrequest d WHERE d.prezime = :prezime"),
    @NamedQuery(name = "Documentrequest.findByImeMajke", query = "SELECT d FROM Documentrequest d WHERE d.imeMajke = :imeMajke"),
    @NamedQuery(name = "Documentrequest.findByPrezimeMajke", query = "SELECT d FROM Documentrequest d WHERE d.prezimeMajke = :prezimeMajke"),
    @NamedQuery(name = "Documentrequest.findByPrezimeOca", query = "SELECT d FROM Documentrequest d WHERE d.prezimeOca = :prezimeOca"),
    @NamedQuery(name = "Documentrequest.findByPol", query = "SELECT d FROM Documentrequest d WHERE d.pol = :pol"),
    @NamedQuery(name = "Documentrequest.findByDatumRodjenja", query = "SELECT d FROM Documentrequest d WHERE d.datumRodjenja = :datumRodjenja"),
    @NamedQuery(name = "Documentrequest.findByNacionalnost", query = "SELECT d FROM Documentrequest d WHERE d.nacionalnost = :nacionalnost"),
    @NamedQuery(name = "Documentrequest.findByProfesija", query = "SELECT d FROM Documentrequest d WHERE d.profesija = :profesija"),
    @NamedQuery(name = "Documentrequest.findByBracnoStanje", query = "SELECT d FROM Documentrequest d WHERE d.bracnoStanje = :bracnoStanje"),
    @NamedQuery(name = "Documentrequest.findByOpstinaPrebivalista", query = "SELECT d FROM Documentrequest d WHERE d.opstinaPrebivalista = :opstinaPrebivalista"),
    @NamedQuery(name = "Documentrequest.findByUlicaPrebivalista", query = "SELECT d FROM Documentrequest d WHERE d.ulicaPrebivalista = :ulicaPrebivalista"),
    @NamedQuery(name = "Documentrequest.findByBrojPrebivalista", query = "SELECT d FROM Documentrequest d WHERE d.brojPrebivalista = :brojPrebivalista"),
    @NamedQuery(name = "Documentrequest.findByImeOca", query = "SELECT d FROM Documentrequest d WHERE d.imeOca = :imeOca"),
    @NamedQuery(name = "Documentrequest.findByStatus", query = "SELECT d FROM Documentrequest d WHERE d.status = :status")})
public class Documentrequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "JMBG")
    private String jmbg;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ime")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "prezime")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imeMajke")
    private String imeMajke;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "prezimeMajke")
    private String prezimeMajke;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "prezimeOca")
    private String prezimeOca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "pol")
    private String pol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "datumRodjenja")
    private String datumRodjenja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nacionalnost")
    private String nacionalnost;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "profesija")
    private String profesija;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "bracnoStanje")
    private String bracnoStanje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "opstinaPrebivalista")
    private String opstinaPrebivalista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "ulicaPrebivalista")
    private String ulicaPrebivalista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "brojPrebivalista")
    private String brojPrebivalista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imeOca")
    private String imeOca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;

    public Documentrequest() {
    }

    public Documentrequest(Long id) {
        this.id = id;
    }

    public Documentrequest(Long id, String jmbg, String ime, String prezime, String imeMajke, String prezimeMajke, String prezimeOca, String pol, String datumRodjenja, String nacionalnost, String profesija, String bracnoStanje, String opstinaPrebivalista, String ulicaPrebivalista, String brojPrebivalista, String imeOca, String status) {
        this.id = id;
        this.jmbg = jmbg;
        this.ime = ime;
        this.prezime = prezime;
        this.imeMajke = imeMajke;
        this.prezimeMajke = prezimeMajke;
        this.prezimeOca = prezimeOca;
        this.pol = pol;
        this.datumRodjenja = datumRodjenja;
        this.nacionalnost = nacionalnost;
        this.profesija = profesija;
        this.bracnoStanje = bracnoStanje;
        this.opstinaPrebivalista = opstinaPrebivalista;
        this.ulicaPrebivalista = ulicaPrebivalista;
        this.brojPrebivalista = brojPrebivalista;
        this.imeOca = imeOca;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getImeMajke() {
        return imeMajke;
    }

    public void setImeMajke(String imeMajke) {
        this.imeMajke = imeMajke;
    }

    public String getPrezimeMajke() {
        return prezimeMajke;
    }

    public void setPrezimeMajke(String prezimeMajke) {
        this.prezimeMajke = prezimeMajke;
    }

    public String getPrezimeOca() {
        return prezimeOca;
    }

    public void setPrezimeOca(String prezimeOca) {
        this.prezimeOca = prezimeOca;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(String datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getNacionalnost() {
        return nacionalnost;
    }

    public void setNacionalnost(String nacionalnost) {
        this.nacionalnost = nacionalnost;
    }

    public String getProfesija() {
        return profesija;
    }

    public void setProfesija(String profesija) {
        this.profesija = profesija;
    }

    public String getBracnoStanje() {
        return bracnoStanje;
    }

    public void setBracnoStanje(String bracnoStanje) {
        this.bracnoStanje = bracnoStanje;
    }

    public String getOpstinaPrebivalista() {
        return opstinaPrebivalista;
    }

    public void setOpstinaPrebivalista(String opstinaPrebivalista) {
        this.opstinaPrebivalista = opstinaPrebivalista;
    }

    public String getUlicaPrebivalista() {
        return ulicaPrebivalista;
    }

    public void setUlicaPrebivalista(String ulicaPrebivalista) {
        this.ulicaPrebivalista = ulicaPrebivalista;
    }

    public String getBrojPrebivalista() {
        return brojPrebivalista;
    }

    public void setBrojPrebivalista(String brojPrebivalista) {
        this.brojPrebivalista = brojPrebivalista;
    }

    public String getImeOca() {
        return imeOca;
    }

    public void setImeOca(String imeOca) {
        this.imeOca = imeOca;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Documentrequest)) {
            return false;
        }
        Documentrequest other = (Documentrequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "baza.Documentrequest[ id=" + id + " ]";
    }
    
}
