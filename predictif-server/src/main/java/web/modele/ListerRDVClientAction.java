package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import metier.modele.Client;
import metier.modele.RDV;
import metier.service.Service;

public class ListerRDVClientAction extends Action {
    @Override
    public void execute(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Client client = (Client) session.getAttribute("client");
        
        List<RDV> listeRDV = null;
        if (client != null) {
            Service service = new Service();
            listeRDV = service.listerRDVClient(client);
        }
        req.setAttribute("listeRDVClient", listeRDV);
    }
}