/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author adupire
 */
@Entity
public class Spirite extends Medium{
    private String support;

    public Spirite() {
    }

    public Spirite(String denomination, String genre, String presentation, String support) {
        super(denomination, genre, presentation);
        this.support = support;
    }

    

    public void setSupport(String support) {
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSpirite{" + "support=" + support + '}';
    }
    
    
}

