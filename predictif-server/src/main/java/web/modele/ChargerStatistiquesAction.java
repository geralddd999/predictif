package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import metier.modele.Client;
import metier.modele.Medium;
import metier.service.Service;

public class ChargerStatistiquesAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {

        try {
            Service service = new Service();

            List<Client> clients = service.listerClients();

            Map<Long, double[]> coordonneesClients = new HashMap<>();

            if (clients != null) {
                for (Client client : clients) {
                    if (client.getAdressePostale() != null && !client.getAdressePostale().trim().isEmpty()) {
                        double[] coords = service.getCoords(client);

                        if (coords != null && coords.length == 2) {
                            coordonneesClients.put(client.getId(), coords);
                        }
                    }
                }
            }

            Map<Medium, Long> top5Mediums = service.obtenirTop5Mediums();
            Map<Medium, Long> consultationsParMedium = service.obtenirNombreConsultationsParMedium();
            Map<LocalDate, Long> stats7Jours = service.obtenirStatistiques7DerniersJours();

            request.setAttribute("success", true);
            request.setAttribute("clients", clients);
            request.setAttribute("coordonneesClients", coordonneesClients);
            request.setAttribute("top5Mediums", top5Mediums);
            request.setAttribute("consultationsParMedium", consultationsParMedium);
            request.setAttribute("stats7Jours", stats7Jours);

        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("success", false);
            request.setAttribute("message", "Erreur lors du chargement des statistiques.");
        }
    }
}