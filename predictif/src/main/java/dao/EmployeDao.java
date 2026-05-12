/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import java.util.NoSuchElementException;
import javax.persistence.TypedQuery;
import metier.modele.Employe;

/**
 *
 * @author adupire
 */
public class EmployeDao {
    public void create (Employe employe){
        JpaUtil.obtenirContextePersistance().persist(employe);
        
    }
    
    public void mergeEmp (Employe employe) {
        JpaUtil.obtenirContextePersistance().merge(employe);
    }
    
    public Employe findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Employe.class, id);
    }
    
    public List<Employe> findAll() {
        String s = "select e from Employe e order by e.nom asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        return query.getResultList();
    }
    
    public Employe findAvailableEmp(String genre){
        Employe res = null;
        String s = "select e from Employe e where e.disponible = 1 and e.genre = :unGenre order by e.nombreRDVrealises asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("unGenre", genre);
        try{
            res = (Employe)query.getResultList().getFirst();

        } catch (NoSuchElementException nse){
            System.out.println("Il n'y a pas d'Employe disponible");
            res = null;
        }
        return res;
    }
    
    public Employe findByMail(String mail) {
        String s = "select c from Employe c where c.mail = :leMail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Employe.class);
        query.setParameter("leMail", mail);
        return (Employe) query.getSingleResult();
    }
}
