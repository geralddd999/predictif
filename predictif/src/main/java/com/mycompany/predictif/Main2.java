/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.predictif;

import dao.JpaUtil;
import java.time.LocalDate;
import java.util.List;
import metier.modele.Client;
import metier.modele.Medium;
import metier.modele.RDV;
import metier.service.Service;



/**
 *
 * @author adupire
 */
public class Main2 {

    public static void main(String[] args) {
        //Globalement la même structure que pour Main1 mais avec 2 prises de rendez-vous en simultanées
        
        Client c = new Client("Barez", "Cyrielle","F",LocalDate.of(2005,9, 07), "0001", "Godewaersvelde, France", "cyrielle.barez@insa-lyon.fr", "abcc");
        Client c2 = new Client("Tyl", "Angélique", "F", LocalDate.of(1992,9,28), "0251188792", "Villeurbanne, France", "atyl6553@free.fr", "atyl");
        System.out.println(c);
        
        JpaUtil.creerFabriquePersistance();
        
        Service ic = new Service();
        Boolean initMed = ic.initialiserMediums();
        System.out.println("Reussite init medium : "+initMed);
        Boolean val = ic.inscrireClient(c);
        System.out.println("Reussite inscription client : "+val);
        Boolean val2 = ic.inscrireClient(c2);
        System.out.println("Reussite inscription client : "+val2);

      // Boolean initEmp = ic.initialiserEmploye();
      //  System.out.println("Reussite init emp : "+initEmp);
        
        //Le client logs in
        Client c3 = ic.authentifierClient("cyrielle.barez@insa-lyon.fr", "abcc");
        System.out.println(c3);
        
        Client c4 = ic.authentifierClient("atyl6553@free.fr", "atyl");
        System.out.println(c4);
       
        
        List<Medium> listeMediums = ic.listerMedium();
        
        Boolean createRDV = ic.creationRDV(listeMediums.getFirst(), c3);
        System.out.println("Reussite creation RDV : "+createRDV);
        Boolean startRDV = ic.commencerRDV(c3.getOpenRDV());
        Boolean createRDV2 = ic.creationRDV(listeMediums.getFirst(), c4);
        System.out.println("Reussite creation RDV : "+createRDV2);
        

        
       
        List<String> Predictions = ic.calculPrediction(c3, 1, 3, 2);
        System.out.println("Reussite calcul predi :" + Predictions);

        /*
        if (createRDV) {
            RDV rdv_a_modif = c3.getOpenRDV();
            
            Boolean fermerRDV = ic.fermetureRDV(rdv_a_modif);
            System.out.println("Reussite fermeture RDV : "+fermerRDV);
            
            Boolean ajoutComm = ic.saisirCommentaire(rdv_a_modif, "top le rdv");
            System.out.println("Reussite saisie commentaire : "+ajoutComm);
            System.out.println("RDV avec commentaire : " + rdv_a_modif);

        }
        */
        List<Client> ListeClients = ic.listerClients();
        System.out.println(ListeClients);
        
        /*
        Client c2 = ic.trouverClientParId(12L);
        System.out.println(c2);
         */      
        
        JpaUtil.fermerFabriquePersistance();
    }
}
