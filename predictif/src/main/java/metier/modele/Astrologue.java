/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.util.Objects;
import javax.persistence.Entity;

/**
 *
 * @author adupire
 */
@Entity
public class Astrologue extends Medium{
    
    private String formation;
    private Integer promotion; 

    public Astrologue() {
    }

    public Astrologue(String denomination,  String genre, String presentation, String formation, Integer promotion) {
        super(denomination, genre, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }

    public String getFormation() {
        return formation;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.formation);
        hash = 23 * hash + Objects.hashCode(this.promotion);
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
        final Astrologue other = (Astrologue) obj;
        if (!Objects.equals(this.formation, other.formation)) {
            return false;
        }
        return Objects.equals(this.promotion, other.promotion);
    }

    @Override
    public String toString() {
        return super.toString() + "\nAstrologue{" + "formation=" + formation + ", promotion=" + promotion + '}';
    }
    
    
}
