/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author adupire
 */
@Entity
public class RDV {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate dateRDV;
    private String commentaire;
    private LocalTime heureRDV;

    private LocalDate dateDemandeRDV;
    private LocalTime heureDemandeRDV;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Medium medium;
    private String prediction_amour;
    private String prediction_sante;
    private String prediction_travail;

    public String getPrediction_amour() {
        return prediction_amour;
    }

    public String getPrediction_sante() {
        return prediction_sante;
    }

    public String getPrediction_travail() {
        return prediction_travail;
    }

    public RDV() {
    }

    public RDV(LocalDate dateDemandeRDV, LocalTime heureDemandeRDV) {
        this.dateDemandeRDV = dateDemandeRDV;
        this.heureDemandeRDV = heureDemandeRDV;
    }

    public LocalDate getDateDemandeRDV() {
        return dateDemandeRDV;
    }

    public LocalTime getHeureDemandeRDV() {
        return heureDemandeRDV;
    }

    public void setDateDemandeRDV(LocalDate dateDemandeRDV) {
        this.dateDemandeRDV = dateDemandeRDV;
    }

    public void setHeureDemandeRDV(LocalTime heureDemandeRDV) {
        this.heureDemandeRDV = heureDemandeRDV;
    }

    public void setPrediction_amour(String prediction_amour) {
        this.prediction_amour = prediction_amour;
    }

    public void setPrediction_sante(String prediction_sante) {
        this.prediction_sante = prediction_sante;
    }

    public void setPrediction_travail(String prediction_travail) {
        this.prediction_travail = prediction_travail;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDateRDV() {
        return dateRDV;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalTime getHeureRDV() {
        return heureRDV;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setDateRDV(LocalDate dateRDV) {
        this.dateRDV = dateRDV;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setHeureRDV(LocalTime heureRDV) {
        this.heureRDV = heureRDV;
    }

    @Override
    public String toString() {
        return "RDV{" + "id=" + id + ", dateRDV=" + dateRDV + ", commentaire=" + commentaire + ", heureRDV=" + heureRDV + ", dateDemandeRDV=" + dateDemandeRDV + ", heureDemandeRDV=" + heureDemandeRDV + ", prediction_amour=" + prediction_amour + ", prediction_sante=" + prediction_sante + ", prediction_travail=" + prediction_travail + ", client=" + client + ", employe=" + employe + ", medium=" + medium + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.dateRDV);
        hash = 59 * hash + Objects.hashCode(this.commentaire);
        hash = 59 * hash + Objects.hashCode(this.heureRDV);
        hash = 59 * hash + Objects.hashCode(this.dateDemandeRDV);
        hash = 59 * hash + Objects.hashCode(this.heureDemandeRDV);
        hash = 59 * hash + Objects.hashCode(this.prediction_amour);
        hash = 59 * hash + Objects.hashCode(this.prediction_sante);
        hash = 59 * hash + Objects.hashCode(this.prediction_travail);
        hash = 59 * hash + Objects.hashCode(this.client);
        hash = 59 * hash + Objects.hashCode(this.employe);
        hash = 59 * hash + Objects.hashCode(this.medium);
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
        final RDV other = (RDV) obj;
        if (!Objects.equals(this.commentaire, other.commentaire)) {
            return false;
        }
        if (!Objects.equals(this.prediction_amour, other.prediction_amour)) {
            return false;
        }
        if (!Objects.equals(this.prediction_sante, other.prediction_sante)) {
            return false;
        }
        if (!Objects.equals(this.prediction_travail, other.prediction_travail)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dateRDV, other.dateRDV)) {
            return false;
        }
        if (!Objects.equals(this.heureRDV, other.heureRDV)) {
            return false;
        }
        if (!Objects.equals(this.dateDemandeRDV, other.dateDemandeRDV)) {
            return false;
        }
        if (!Objects.equals(this.heureDemandeRDV, other.heureDemandeRDV)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.employe, other.employe)) {
            return false;
        }
        return Objects.equals(this.medium, other.medium);
    }

}
