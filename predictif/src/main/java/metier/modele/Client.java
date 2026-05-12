/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


/**
 *
 * @author adupire
 */
@Entity
public class Client {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String genre;
    private LocalDate dateNaissance;
    private String numTel;
    private String adressePostale;
    
    @Embedded
    private ProfilAstral profilAstral;
    @Column(unique = true)
    private String mail;
    private String motDePasse;
    @OneToMany
    private List<RDV> listeRDV;

    public Client(String nom, String prenom, String genre, LocalDate dateNaissance, String numTel, String adressePostale, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.dateNaissance = dateNaissance;
        this.numTel = numTel;
        this.adressePostale = adressePostale;
        this.mail = mail;
        this.motDePasse = motDePasse;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMail() {
        return mail;
    }

    public ProfilAstral getProfilAstral() {
        return profilAstral;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getNumTel() {
        return numTel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getAdressePostale() {
        return adressePostale;
    }

    public List<RDV> getListeRDV() {
        return listeRDV;
    }
    
    public RDV getOpenRDV(){
        RDV res = null;
        for (RDV rdv : listeRDV) {

            if (rdv.getDateRDV() == null) {
                res = rdv;
            }
        }
                
        return res;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }


    public void setProfilAstral(ProfilAstral profilAstral) {
        this.profilAstral = profilAstral;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(LocalDate DateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setNumTel(String NumTel) {
        this.numTel = numTel;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void addRDV(RDV rdv){
        this.listeRDV.add(rdv);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", genre=" + genre + ", dateNaissance=" + dateNaissance + ", numTel=" + numTel + ", adressePostale=" + adressePostale + ", profilAstral=" + profilAstral + ", mail=" + mail + ", motDePasse=" + motDePasse + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nom);
        hash = 29 * hash + Objects.hashCode(this.prenom);
        hash = 29 * hash + Objects.hashCode(this.genre);
        hash = 29 * hash + Objects.hashCode(this.dateNaissance);
        hash = 29 * hash + Objects.hashCode(this.numTel);
        hash = 29 * hash + Objects.hashCode(this.adressePostale);
        hash = 29 * hash + Objects.hashCode(this.profilAstral);
        hash = 29 * hash + Objects.hashCode(this.mail);
        hash = 29 * hash + Objects.hashCode(this.motDePasse);
        hash = 29 * hash + Objects.hashCode(this.listeRDV);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Objects.equals(this.numTel, other.numTel)) {
            return false;
        }
        if (!Objects.equals(this.adressePostale, other.adressePostale)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.motDePasse, other.motDePasse)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateNaissance, other.dateNaissance)) {
            return false;
        }
        if (!Objects.equals(this.profilAstral, other.profilAstral)) {
            return false;
        }
        return Objects.equals(this.listeRDV, other.listeRDV);
    }
    
    
    
}

    