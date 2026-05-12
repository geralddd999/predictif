/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.LinkedHashMap;
import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Medium;
import java.util.Map;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author adupire
 */
public class MediumDao {
    public void create (Medium medium){
        JpaUtil.obtenirContextePersistance().persist(medium);
        
    }
    
    public void mergeMedium (Medium medium) {
        JpaUtil.obtenirContextePersistance().merge(medium);
    }
    
    public Medium findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }
    
      
    public List<Medium> findAll() {
        String s = "select m from Medium m order by m.denomination asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        return query.getResultList();
    }
    
    public Map<Medium, Long> trouverTop5Consultations() {
        Map<Medium, Long> resultat = new LinkedHashMap<>();
        
        EntityManager em = JpaUtil.obtenirContextePersistance(); 

        String jpql = "SELECT r.medium, COUNT(r) FROM RDV r GROUP BY r.medium ORDER BY COUNT(r) DESC";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        query.setMaxResults(5); 

        List<Object[]> listeResultats = query.getResultList();
        
        for (Object[] ligne : listeResultats) {
            Medium medium = (Medium) ligne[0];
            Long nbConsultations = (Long) ligne[1];
            resultat.put(medium, nbConsultations);
        }

        return resultat;
    }
            
    public Map<Medium, Long> trouverNombreConsultationsParMedium() {
        Map<Medium, Long> resultat = new LinkedHashMap<>();
        
        EntityManager em = JpaUtil.obtenirContextePersistance(); 

        String jpql = "SELECT r.medium, COUNT(r) FROM RDV r GROUP BY r.medium ORDER BY COUNT(r) DESC";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);

        List<Object[]> listeResultats = query.getResultList();
        
        for (Object[] ligne : listeResultats) {
            Medium medium = (Medium) ligne[0];
            Long nbConsultations = (Long) ligne[1];
            resultat.put(medium, nbConsultations);
        }

        return resultat;
    }
}
