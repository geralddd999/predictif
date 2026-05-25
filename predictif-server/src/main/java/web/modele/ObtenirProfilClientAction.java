/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import metier.modele.Client;
import metier.service.Service;
import metier.modele.ProfilAstral;
/**
 *
 * @author gschambiram
 */
public class ObtenirProfilClientAction extends Action {
    @Override
    public void execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        
        // 1. Attempt to get the real logged-in client
        Client clientLogge = (Client) session.getAttribute("client"); 
        
        // 2. DEV HACK: If no one is logged in, create a dummy client so the page works!
        if (clientLogge == null) {
            System.out.println("[Dev Mode] No client found in session. Creating a dummy tester account.");
            
            // Instantiating a mock student client
            clientLogge = new Client("Lamkharbech", "Ilias", "H", 
                                     LocalDate.of(2005, 9, 7), 
                                     "0612345678", "Villeurbanne, France", 
                                     "ilias.tester@insa-lyon.fr", "password123");
            
            // Giving them a complete mock astral profile match
            ProfilAstral mockProfil = new ProfilAstral("Vierge", "Coq", "Vert Hypertrophie", "Lion");
            clientLogge.setProfilAstral(mockProfil);
            clientLogge.setId(999L); // Give them a fake database ID
            
            // Save it into the session so it persists for future clicks
            session.setAttribute("client", clientLogge);
        }
        
        // 3. Hand the client (real or fake) over to the serialization layer
        req.setAttribute("clientConcerne", clientLogge);
    }
}