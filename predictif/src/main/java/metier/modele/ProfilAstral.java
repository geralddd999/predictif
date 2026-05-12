/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author adupire
 */
@Embeddable
public class ProfilAstral {
    private String signeZodiaque;
    private String signeAstroChinois;
    private String couleurPorteBonheur;
    private String animalTotem;

    public ProfilAstral(String signeZodiaque, String signeAstroChinois, String couleurPorteBonheur, String animalTotem) {
        this.signeZodiaque = signeZodiaque;
        this.signeAstroChinois = signeAstroChinois;
        this.couleurPorteBonheur = couleurPorteBonheur;
        this.animalTotem = animalTotem;
    }

    public ProfilAstral() {
    }

    public String getSigneZodiaque() {
        return signeZodiaque;
    }

    public String getSigneAstroChinois() {
        return signeAstroChinois;
    }

    public String getCouleurPorteBonheur() {
        return couleurPorteBonheur;
    }

    public String getAnimalTotem() {
        return animalTotem;
    }

    public void setSigneZodiaque(String signeZodiaque) {
        this.signeZodiaque = signeZodiaque;
    }

    public void setSigneAstroChinois(String signeAstroChinois) {
        this.signeAstroChinois = signeAstroChinois;
    }

    public void setCouleurPorteBonheur(String couleurPorteBonheur) {
        this.couleurPorteBonheur = couleurPorteBonheur;
    }

    public void setAnimalTotem(String animalTotem) {
        this.animalTotem = animalTotem;
    }

    @Override
    public String toString() {
        return "ProfilAstral{" + "signeZodiaque=" + signeZodiaque + ", signeAstroChinois=" + signeAstroChinois + ", couleurPorteBonheur=" + couleurPorteBonheur + ", animalTotem=" + animalTotem + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.signeZodiaque);
        hash = 71 * hash + Objects.hashCode(this.signeAstroChinois);
        hash = 71 * hash + Objects.hashCode(this.couleurPorteBonheur);
        hash = 71 * hash + Objects.hashCode(this.animalTotem);
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
        final ProfilAstral other = (ProfilAstral) obj;
        if (!Objects.equals(this.signeZodiaque, other.signeZodiaque)) {
            return false;
        }
        if (!Objects.equals(this.signeAstroChinois, other.signeAstroChinois)) {
            return false;
        }
        if (!Objects.equals(this.couleurPorteBonheur, other.couleurPorteBonheur)) {
            return false;
        }
        return Objects.equals(this.animalTotem, other.animalTotem);
    }
    
    
}


