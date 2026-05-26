package web.vue;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import metier.modele.Client;
import metier.modele.Medium;

public class StatistiquesSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=UTF-8");

        Boolean success = (Boolean) request.getAttribute("success");
        String message = (String) request.getAttribute("message");

        JsonArrayBuilder top5Array = Json.createArrayBuilder();
        JsonArrayBuilder consultationsArray = Json.createArrayBuilder();
        JsonArrayBuilder stats7JoursArray = Json.createArrayBuilder();
        JsonArrayBuilder clientsArray = Json.createArrayBuilder();

        List<Client> clients = (List<Client>) request.getAttribute("clients");

        Map<Long, double[]> coordonneesClients =
                (Map<Long, double[]>) request.getAttribute("coordonneesClients");

        Map<Medium, Long> top5Mediums =
                (Map<Medium, Long>) request.getAttribute("top5Mediums");

        Map<Medium, Long> consultationsParMedium =
                (Map<Medium, Long>) request.getAttribute("consultationsParMedium");

        Map<LocalDate, Long> stats7Jours =
                (Map<LocalDate, Long>) request.getAttribute("stats7Jours");

        // Top 5 médiums
        if (top5Mediums != null) {
            for (Map.Entry<Medium, Long> entry : top5Mediums.entrySet()) {
                Medium medium = entry.getKey();
                Long nombre = entry.getValue();

                top5Array.add(Json.createObjectBuilder()
                        .add("nom", medium.getDenomination())
                        .add("consultations", nombre)
                );
            }
        }

        // Consultations par médium
        if (consultationsParMedium != null) {
            for (Map.Entry<Medium, Long> entry : consultationsParMedium.entrySet()) {
                Medium medium = entry.getKey();
                Long nombre = entry.getValue();

                consultationsArray.add(Json.createObjectBuilder()
                        .add("nom", medium.getDenomination())
                        .add("consultations", nombre)
                );
            }
        }

        // Statistiques des 7 derniers jours
        if (stats7Jours != null) {
            for (Map.Entry<LocalDate, Long> entry : stats7Jours.entrySet()) {
                stats7JoursArray.add(Json.createObjectBuilder()
                        .add("date", entry.getKey().toString())
                        .add("consultations", entry.getValue())
                );
            }
        }

        // Clients + coordonnées géographiques
        if (clients != null) {
            for (Client client : clients) {

                double latitude = 0.0;
                double longitude = 0.0;
                boolean hasCoords = false;

                if (coordonneesClients != null && coordonneesClients.containsKey(client.getId())) {
                    double[] coords = coordonneesClients.get(client.getId());

                   if (coords != null && coords.length == 2) {
                       latitude = coords[1];
                       longitude = coords[0];

                       if (!(latitude == 0.0 && longitude == 0.0)) {
                             hasCoords = true;
                       }
                   }
                }

                clientsArray.add(Json.createObjectBuilder()
                        .add("id", client.getId())
                        .add("nom", client.getNom() != null ? client.getNom() : "")
                        .add("prenom", client.getPrenom() != null ? client.getPrenom() : "")
                        .add("adressePostale", client.getAdressePostale() != null ? client.getAdressePostale() : "")
                        .add("hasCoords", hasCoords)
                        .add("latitude", latitude)
                        .add("longitude", longitude)
                );
            }
        }

        JsonObject json = Json.createObjectBuilder()
                .add("success", success != null && success)
                .add("message", message != null ? message : "")
                .add("clients", clientsArray)
                .add("top5Mediums", top5Array)
                .add("consultationsParMedium", consultationsArray)
                .add("stats7Jours", stats7JoursArray)
                .build();

        System.out.println("JSON statistiques : " + json.toString());

        PrintWriter out = response.getWriter();
        out.println(json.toString());
        out.close();
    }
}