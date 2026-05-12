/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Client;

/**
 *
 * @author adupire
 */
public class ClientDao {
    public void create (Client client){
        JpaUtil.obtenirContextePersistance().persist(client);
        
    }
    
    public void mergeClient(Client client) {
        JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    public Client findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
    
    public List<Client> findAll() {
        String s = "select c from Client c order by c.nom asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        return query.getResultList();
    }
    
    public Client findByMail(String mail) {
        String s = "select c from Client c where c.mail = :leMail";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        query.setParameter("leMail", mail);
        return (Client) query.getSingleResult();
    }
}
