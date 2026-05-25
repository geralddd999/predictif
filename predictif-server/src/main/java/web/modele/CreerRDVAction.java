package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import metier.modele.Client;
import metier.modele.Medium;
import metier.service.Service;

public class CreerRDVAction extends Action {
    @Override
    public void execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        
        Client client = (Client) session.getAttribute("client");
        
        String idMediumStr = req.getParameter("idMedium");
        
        boolean succes = false;
        
        if (client != null && idMediumStr != null) {
            Long idMedium = Long.parseLong(idMediumStr);
            Service service = new Service();
            
            List<Medium> listeMediums = service.listerMedium();
            Medium mediumChoisi = null;
            
            if (listeMediums != null) {
                for (Medium m : listeMediums) {
                    if (m.getId() != null && m.getId().equals(idMedium)) {
                        mediumChoisi = m;
                        break;
                    }
                }
            }
            
            if (mediumChoisi != null) {
                succes = service.creationRDV(mediumChoisi, client);
            }
        }
        
        req.setAttribute("rdvSuccess", succes);
    }
}