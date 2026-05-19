package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import metier.modele.Client;
import metier.service.Service;

public class InscrirClientAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String genre = request.getParameter("genre");
        String dateNaissanceTexte = request.getParameter("dateNaissance");
        String numTel = request.getParameter("numTel");
        //String adressePostale = request.getParameter("adressePostale");
        String mail = request.getParameter("mail");
        String motDePasse = request.getParameter("motDePasse");
        
        String adresse = request.getParameter("adresse");
        String ville = request.getParameter("ville");
        String codePostal = request.getParameter("codePostal");
        String pays = request.getParameter("pays");

        String adressePostale = adresse + ", " + codePostal + " " + ville + ", " + pays;
        
        
        System.out.println("Inscription client : " + prenom + " " + nom);

        try {
            LocalDate dateNaissance = LocalDate.parse(dateNaissanceTexte);

            Client client = new Client(
                    nom,
                    prenom,
                    genre,
                    dateNaissance,
                    numTel,
                    adressePostale,
                    mail,
                    motDePasse
            );

            Service service = new Service();

            Boolean resultat = service.inscrireClient(client);

            request.setAttribute("success", resultat != null && resultat);
            request.setAttribute("client", client);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("success", false);
            request.setAttribute("message", "Erreur lors de l'inscription du client.");
        }
    }
}