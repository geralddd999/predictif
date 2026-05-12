/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.RDV;

/**
 *
 * @author adupire
 */
public class RDVDao {
    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author adupire
 */
    public void create (RDV rdv){
        JpaUtil.obtenirContextePersistance().persist(rdv);
        
    }
    
    public void mergeRDV (RDV rdv) {
        JpaUtil.obtenirContextePersistance().merge(rdv);
    }
    
    public RDV findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(RDV.class, id);
    }
    
    public List<RDV> findAll() {
        String s = "select r from RDV r order by r.dateRDV asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, RDV.class);
        return query.getResultList();
    }
    
    public List<RDV> findByClient(Client client) {
        String s = "select r from RDV r where r.client = :unClient and r.heureRDV is not null order by r.dateRDV asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, RDV.class);
        query.setParameter("unClient", client);
        return query.getResultList();
    }
    
    public Map<LocalDate, Long> compterConsultations7DerniersJours() {
        Map<LocalDate, Long> statistiques = new LinkedHashMap<>();
        LocalDate aujourdHui = LocalDate.now();
        LocalDate dateDebut = aujourdHui.minusDays(6);

        for (int i = 6; i >= 0; i--) {
            statistiques.put(aujourdHui.minusDays(i), 0L);
        }
        
        EntityManager em = JpaUtil.obtenirContextePersistance(); 
        
        String jpql = "SELECT r.dateRDV, COUNT(r) FROM RDV r "
                    + "WHERE r.dateRDV BETWEEN :debut AND :fin "
                    + "GROUP BY r.dateRDV "
                    + "ORDER BY r.dateRDV ASC";
        
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        query.setParameter("debut", dateDebut);
        query.setParameter("fin", aujourdHui);
        
        List<Object[]> listeResultats = query.getResultList();
        
        for (Object[] ligne : listeResultats) {
            LocalDate dateBDD = (LocalDate) ligne[0];
            Long nbConsultations = (Long) ligne[1];
            
            statistiques.put(dateBDD, nbConsultations); 
        }

        return statistiques;
    }
}

