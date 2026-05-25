/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import dao.ClientDao;
import dao.EmployeDao;
import dao.JpaUtil;
import dao.MediumDao;
import dao.RDVDao;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;
import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.ProfilAstral;
import metier.modele.RDV;
import metier.modele.Spirite;
import static util.Message.envoyerMail;
import static util.Message.envoyerNotification;
import util.RequeteAPI;
import static util.RequeteAPI.appelCoordonneesGeo;
import static util.RequeteAPI.calculProfilAstral;
import static util.RequeteAPI.calculConseil;

/**
 *
 * @author adupire
 */
public class Service {
    public Boolean inscrireClient(Client client){
        Boolean reussi = false;
        ClientDao clientDao = new ClientDao();
        
        try {
            
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            ProfilAstral pa = calculProfilAstral(client.getPrenom(), client.getDateNaissance()); 
            client.setProfilAstral(pa);
            // gérer cas profil astral qui ne marche pas
            clientDao.create(client);
            
            JpaUtil.validerTransaction();
            reussi = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            reussi = false;
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        if (reussi) {
            envoyerMail("contact@predict.if", client.getMail(), "Bienvenue chez PREDICT'IF", "Bonjour "+ client.getPrenom()+", nous vous confirmons votre inscription au service PREDICT'IF. Rendez-vous sur notre site pour consulter votre profil astrologique et pofiter des dons incroyables de nos mediums");
        }
        else {
            envoyerMail("contact@predict.if", client.getMail(), "Echec de l'inscription chez PREDICT'IF", "Bonjour " + client.getPrenom() + ", votre inscription au service PREDICT'IF a malencontreusement échoué ... Merci de recommencer ultérieurement.");
        }
        return reussi;
    } 
    
    public Boolean initialiserMediums(){
        Boolean reussi = false;
        MediumDao mediumDao = new MediumDao();
        
        Medium spirite2 = new Spirite("Professeur Tran", "H", "Votre avenir est devant vous : regardons-le ensemble !", "Marc de café, boule de cristal, oreilles de lapin");
        Medium carto1 = new Cartomancien("Madame Irma", "F", "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        Medium carto2 = new Cartomancien("Endora", "F", "Mes cartes répondront à toutes vos questions personnelles.");
        Medium astro1 = new Astrologue("Serena", "F", "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé.", "ENS-Astro", 2006);
        Medium astro2 = new Astrologue("Mr M", "H", "Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter !", "Institut des Nouveaux Savoirs Astrologiques", 2010);
        Medium spirite1 = new Spirite("Gwenaëlle", "F", "Sécialiste des grandes converesations au-delà de TOUTES les frontières", "Boule de cristal");
        
        try {
            
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            mediumDao.create(spirite1);
            mediumDao.create(spirite2);
            mediumDao.create(astro1);
            mediumDao.create(astro2);
            mediumDao.create(carto1);
            mediumDao.create(carto2);
            
            
            JpaUtil.validerTransaction();
            reussi = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            reussi = false;
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return reussi;
    } 
    
    public Boolean initialiserEmploye(){
        Boolean reussi = false;
        EmployeDao employeDao = new EmployeDao();
        
        Employe emp = new Employe("GUERIN", "Yoan", "yoan.guerin@insa-lyon.fr", "abcd","0613257865", "H");
        Employe emp2 = new Employe("TEFERRA", "Deborah Solomon", "deborah-solomon.teferra@insa-lyon.fr", "abcd","0625341575", "F");
        Employe emp3 = new Employe("FAVRO", "Samuel", "samuel.favro@free.fr", "abcd","0642049305", "H");
        Employe emp4 = new Employe("GUOGUEN", "Gabriela", "gguoguen2418@hotmail.com", "abcd","0719843316", "F");
        
        try {
            
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            employeDao.create(emp);
            employeDao.create(emp2);
            employeDao.create(emp3);
            employeDao.create(emp4);

            JpaUtil.validerTransaction();
            reussi = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            reussi = false;
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return reussi;
    }
    
    public List<Client> listerClients(){
        List<Client> listeClients = null;
        ClientDao clientDao = new ClientDao();
        
        try {
            
            JpaUtil.creerContextePersistance();
            listeClients = clientDao.findAll();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return listeClients;
    } 
    
    public Client trouverClientParId(Long id){
        Client client = null;
        ClientDao clientDao = new ClientDao();
        
        try {
            
            JpaUtil.creerContextePersistance();
            client = clientDao.findById(id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return client;
    } 
    
    public Client authentifierClient(String mail, String motDePasse){
        Boolean reussi = false;
        Client client = null;
        ClientDao clientDao = new ClientDao();
        
        try {
            
            JpaUtil.creerContextePersistance();
            client = clientDao.findByMail(mail);
            if (client != null && client.getMotDePasse().equals(motDePasse)) {
                reussi = true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return reussi ? client : null;
    } 
    
    public Employe authentifierEmploye(String mail, String motDePasse){
        Boolean reussi = false;
        Employe employe = null;
        EmployeDao employeDao = new EmployeDao();
        
        try {
            
            JpaUtil.creerContextePersistance();
            employe = employeDao.findByMail(mail);
            if (employe != null && employe.getMdp().equals(motDePasse)) {
                
                 reussi = true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return reussi ? employe : null;
    } 
    
    public Boolean creationRDV(Medium m, Client c){
        
        EmployeDao employeDao = new EmployeDao();
        MediumDao mediumDao = new MediumDao();
        ClientDao clientDao = new ClientDao();
        RDVDao rdvDao = new RDVDao();
        Employe emp = null;
        
        Boolean reussi = false;
        try {
            
            JpaUtil.creerContextePersistance();
            
            RDV rdv = new RDV (LocalDate.now(), LocalTime.now());
            emp = employeDao.findAvailableEmp(m.getGenre());
            System.out.println("Employé : " + emp);
            
            Boolean trouve = (emp != null);
            
            if (trouve) {
                rdv.setClient(c);
                rdv.setEmploye(emp);
                rdv.setMedium(m);
                emp.setDisponible(0);
                c.addRDV(rdv);
                    emp.addRDV(rdv);
                System.out.println("RDV : "+ rdv);
                
                JpaUtil.ouvrirTransaction();

                rdvDao.create(rdv);
                clientDao.mergeClient(c);
                employeDao.mergeEmp(emp);

                JpaUtil.validerTransaction();
                reussi = true;
            }
            else {
                reussi = false;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            reussi = false;
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        if (reussi) {
            String message = "Bonjour "+emp.getPrenom()+". Consultation requise pour ";
            
            if ("F".equals(c.getGenre())) {
                message += "Mme ";
            }
            else {
                message += "Mr ";
            }
            
            message += c.getPrenom() + " " + c.getNom() + ". Médium à incarner : " + m.getDenomination();
            
            envoyerNotification(emp.getNumTel(), message);
        }
        return reussi;
    }
    
    public Boolean commencerRDV (RDV rdv) {

        Boolean reussi = false;
        try {
            Client c = rdv.getClient();
            Employe emp = rdv.getEmploye();
            Medium m = rdv.getMedium();
            java.time.LocalTime t = rdv.getHeureDemandeRDV();
            String heureStr = (t != null) ? t.getHour() + "h" + t.getMinute() : "à l'heure convenue";
            String message = "Bonjour " + c.getPrenom() +". J'ai bien reçu votre demande de consultation du " + rdv.getDateDemandeRDV() + " " + heureStr + ".";
            message += "Vous pouvez dès à présent me contacter au " + emp.getNumTel() + ". A tout de suite !\nMédiumiquement vôtre, " + m.getDenomination();
            
            envoyerNotification(c.getNumTel(), message);
            
            reussi = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            reussi = false;
        }

        return reussi;
    }
    
    public Boolean fermetureRDV(RDV rdv){

        EmployeDao employeDao = new EmployeDao();
        MediumDao mediumDao = new MediumDao();
        RDVDao rdvDao = new RDVDao();
        
        Boolean reussi = false;
        try {
            
            JpaUtil.creerContextePersistance();
            Employe emp = rdv.getEmploye();
            rdv.setDateRDV(LocalDate.now());
            rdv.setHeureRDV(LocalTime.now());
            emp.setDisponible(1);

            JpaUtil.ouvrirTransaction();

            rdvDao.mergeRDV(rdv);
            employeDao.mergeEmp(emp);

            JpaUtil.validerTransaction();
            reussi = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            reussi = false;
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return reussi;
    }
    
    public Boolean saisirCommentaire (RDV rdv, String commentaire) {
        RDVDao rdvDao = new RDVDao();
        Boolean reussi = false;
        
        try {
            
            JpaUtil.creerContextePersistance();
            
            rdv.setCommentaire(commentaire);
            rdvDao.mergeRDV(rdv);
            
            reussi = true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return reussi;
    }
    
    public List<Medium> listerMedium(){
        MediumDao mediumDao = new MediumDao();
        List<Medium> res = null;
        
        try {
            
            JpaUtil.creerContextePersistance();
            res = mediumDao.findAll();
            System.out.println(res);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            res = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return res;
    }
    
    
    public List<String> calculPrediction(Client c, Integer predAmour, Integer predSante, Integer predTravail){
        Boolean reussi = false;
        List<String> predi = null;
        try {
            JpaUtil.creerContextePersistance();
            ProfilAstral pa = c.getProfilAstral();
            System.out.println(pa.getCouleurPorteBonheur());
            System.out.println(pa.getAnimalTotem());
            predi = calculConseil( pa.getCouleurPorteBonheur(), pa.getAnimalTotem(), predAmour, predSante, predTravail);
            reussi = true;
        }
        catch (Exception ex) {
            JpaUtil.annulerTransaction();
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        
        System.out.println(predi);
        return predi;
    }
    
    public List<RDV> listerRDVClient (Client c) {
        List<RDV> listeRDV = null;
        RDVDao rdvDao = new RDVDao();
        
        try {
            
            JpaUtil.creerContextePersistance();
            listeRDV = rdvDao.findByClient(c);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return listeRDV;
    }
    
     public Employe trouverEmployeParId(Long id){
        Employe employe = null;
        EmployeDao employeDao = new EmployeDao();
        
        try {
            
            JpaUtil.creerContextePersistance();
            employe = employeDao.findById(id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            JpaUtil.fermerContextePersistance();
            
        }
        return employe;
    } 
     
     public double[] getCoords(Client client) {
                 
         double coords[] = new double[2];
         
         try {
            JpaUtil.creerContextePersistance();
            coords = appelCoordonneesGeo(client.getAdressePostale());
            
        }
        catch (Exception ex) {
            JpaUtil.annulerTransaction();
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        
        System.out.println(Arrays.toString(coords));
        return coords;
     }
     
    public Map<Medium, Long> obtenirTop5Mediums() {
        Map<Medium, Long> top5 = null;
        MediumDao mediumDao = new MediumDao();
        
        try {
            JpaUtil.creerContextePersistance();
            
            top5 = mediumDao.trouverTop5Consultations();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return top5;
    }
    
    public Map<Medium, Long> obtenirNombreConsultationsParMedium() {
        Map<Medium, Long> stats = null;
        MediumDao mediumDao = new MediumDao();
        
        try {
            JpaUtil.creerContextePersistance();
            
            stats = mediumDao.trouverNombreConsultationsParMedium();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return stats;
    }
    
    public Map<LocalDate, Long> obtenirStatistiques7DerniersJours() {
        Map<LocalDate, Long> stats = null;
        RDVDao rdvDao = new RDVDao();
        
        try {
            JpaUtil.creerContextePersistance();
            
            stats = rdvDao.compterConsultations7DerniersJours();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        
        return stats;
    }
}
