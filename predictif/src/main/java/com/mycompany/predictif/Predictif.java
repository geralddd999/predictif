/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.predictif;

import dao.JpaUtil;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.RDV;
import metier.service.Service;

/**
 *
 * @author adupire
 */
public class Predictif {

    public static void main(String[] args) {
        Client c = new Client("Rey", "Jaime","H",LocalDate.of(2005,06,29), "0000", "Valence, Espagne", "jaime.rey@insa-lyon.fr", "abcd");
        System.out.println(c);
        
        JpaUtil.creerFabriquePersistance();
        
        Service ic = new Service();
        
        // Ajout de médiums et employé pour remplir la abse de données
        Boolean initMed = ic.initialiserMediums();
        System.out.println("Reussite init medium : "+initMed);
        Boolean initEmp = ic.initialiserEmploye();
        System.out.println("Reussite init emp : "+initEmp);
        
        // Inscription du client
        Boolean val = ic.inscrireClient(c);
        System.out.println("Reussite inscription client : "+val);
        
        Employe empl = ic.authentifierEmploye("deborah-solomon.teferra@insa-lyon.fr", "abcd");
        System.out.println(empl);
        
        //Le client se connect, choisi un medium et il demande un rdv
        Client c3 = ic.authentifierClient("jaime.rey@insa-lyon.fr", "abcd");
        System.out.println(c3);
        
        //Recupération de la liste des médiumms, simulation : le choix de médium se fait sur le premier de la liste
        List<Medium> listeMediums = ic.listerMedium();
        Boolean createRDV = ic.creationRDV(listeMediums.getFirst(), c3);
        System.out.println("Reussite creation RDV : "+createRDV);
        
        //Appel a trouverEmployeParId afin de mettre à jour l'employé assigné au rdv
        Long id_emp = c3.getOpenRDV().getEmploye().getId();
        Employe empl2 = ic.trouverEmployeParId(id_emp);
        System.out.println(empl2);
        
        //Début du rdv
        Boolean startRDV = ic.commencerRDV(c3.getOpenRDV());
        System.out.println("Reussite début RDV : "+startRDV);
        
        //Test de calcul de prédictions
        List<String> Predictions = ic.calculPrediction(c3, 1, 2, 3);
        System.out.println("Reussite calcul predi :" + Predictions);

        //Si le rdv a bien été commencé
        if (startRDV) {
            //On récuppère le rendez-vous en cours du client
            RDV rdv_a_modif = c3.getOpenRDV();
            
            //On ferme le rendez-vous
            Boolean fermerRDV = ic.fermetureRDV(rdv_a_modif);
            System.out.println("Reussite fermeture RDV : "+fermerRDV);
            
            //On ajoute le commentaire
            Boolean ajoutComm = ic.saisirCommentaire(rdv_a_modif, "top le rdv");
            System.out.println("Reussite saisie commentaire : "+ajoutComm);
            System.out.println("RDV avec commentaire : " + rdv_a_modif);
            
            //Test affichage des rendez-vous passé du client concerné
            List<RDV> listrdv = ic.listerRDVClient(c3);
            System.out.println("Liste de RDV : " + listrdv);
                    
                    
        }
        
       /* Long id_emp2 = empl.getId();
        Employe empl21 = ic.trouverEmployeParId(id_emp2);
        System.out.println(empl21);*/
        List<Client> ListeClients = ic.listerClients();
        System.out.println(ListeClients);
         
        
        // test des appels des services pour la page de statistiques de l'agence
        
        double[] coords = ic.getCoords(c3);
        System.out.println("Coordonnées de " + c3.getPrenom() + " : " + Arrays.toString(coords));
        
        Map<Medium, Long> top5 = ic.obtenirTop5Mediums();
        if (top5 != null && !top5.isEmpty()) {
            for (Map.Entry<Medium, Long> entry : top5.entrySet()) {
                System.out.println("- " + entry.getKey().getDenomination() + " : " + entry.getValue() + " consultation(s)");
            }
        } else {
            System.out.println("Aucune donnée pour le Top 5 ou erreur de récupération.");
        }
        
        Map<LocalDate, Long> stats7Jours = ic.obtenirStatistiques7DerniersJours();
        if (stats7Jours != null && !stats7Jours.isEmpty()) {
            for (Map.Entry<LocalDate, Long> entry : stats7Jours.entrySet()) {
                System.out.println("- Date : " + entry.getKey() + " -> " + entry.getValue() + " consultation(s)");
            }
        } else {
            System.out.println("Aucune donnée pour les 7 derniers jours ou erreur de récupération.");
        }
        
        JpaUtil.fermerFabriquePersistance();
    }
}