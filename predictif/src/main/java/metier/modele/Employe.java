/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author adupire
 */
@Entity
public class Employe {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String mdp;
    private String numTel;
    private String genre;
    private Integer disponible;
    
    @OneToMany
    private List<RDV> listeRDV; 
    
    private Long nombreRDVrealises;

    public Employe() {
    }

    public Employe(String nom, String prenom, String mail, String mdp, String numTel, String genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mdp = mdp;
        this.numTel = numTel;
        this.genre = genre;
        this.disponible = 1;
        this.nombreRDVrealises = 0L;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMdp() {
        return mdp;
    }

    public String getNumTel() {
        return numTel;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getDisponible() {
        return disponible;
    }

    public List<RDV> getListeRDV() {
        return listeRDV;
    }

    public Long getNombreRDVrealises() {
        return nombreRDVrealises;
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
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDisponible(Integer disponible) {
        this.disponible = disponible;
    }

    public void setListeRDV(List<RDV> listeRDV) {
        this.listeRDV = listeRDV;
    }

    public void setNombreRDVrealises(Long nombreRDVrealises) {
        this.nombreRDVrealises = nombreRDVrealises;
    }
    
    public void addRDV(RDV rdv){
        this.listeRDV.add(rdv);
        this.nombreRDVrealises++;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.nom);
        hash = 73 * hash + Objects.hashCode(this.prenom);
        hash = 73 * hash + Objects.hashCode(this.mail);
        hash = 73 * hash + Objects.hashCode(this.mdp);
        hash = 73 * hash + Objects.hashCode(this.numTel);
        hash = 73 * hash + Objects.hashCode(this.genre);
        hash = 73 * hash + Objects.hashCode(this.disponible);
        hash = 73 * hash + Objects.hashCode(this.listeRDV);
        hash = 73 * hash + Objects.hashCode(this.nombreRDVrealises);
        return hash;
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", mdp=" + mdp + ", numTel=" + numTel + ", genre=" + genre + ", disponible=" + disponible + ", nombreRDVrealises=" + nombreRDVrealises + '}';
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
        final Employe other = (Employe) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.mdp, other.mdp)) {
            return false;
        }
        if (!Objects.equals(this.numTel, other.numTel)) {
            return false;
        }
        if (!Objects.equals(this.genre, other.genre)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.disponible, other.disponible)) {
            return false;
        }
        if (!Objects.equals(this.listeRDV, other.listeRDV)) {
            return false;
        }
        return Objects.equals(this.nombreRDVrealises, other.nombreRDVrealises);
    }

  
    
    
}


