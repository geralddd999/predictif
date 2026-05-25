package web.vue;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
import metier.modele.RDV;

public class ListerRDVClientSerialisation extends Serialisation {
    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        
        List<RDV> listeRDV = (List<RDV>) req.getAttribute("listeRDVClient");
        JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
        JsonArrayBuilder jsonListe = Json.createArrayBuilder();
        
        if (listeRDV != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (RDV rdv : listeRDV) {
                JsonObjectBuilder jsonRDV = Json.createObjectBuilder();
                
                if (rdv.getMedium() != null) {
                    jsonRDV.add("mediumNom", rdv.getMedium().getDenomination());
                    String type = "Médium";
                    if (rdv.getMedium() instanceof metier.modele.Astrologue) type = "Astrologue";
                    else if (rdv.getMedium() instanceof metier.modele.Spirite) type = "Spirite";
                    else if (rdv.getMedium() instanceof metier.modele.Cartomancien) type = "Cartomancien";
                    jsonRDV.add("mediumType", type);
                } else {
                    jsonRDV.add("mediumNom", "Inconnu");
                    jsonRDV.add("mediumType", "Médium");
                }
                
                if (rdv.getDateRDV() != null) {
                    jsonRDV.add("date", rdv.getDateRDV().format(formatter));
                } else if (rdv.getDateDemandeRDV() != null) {
                    jsonRDV.add("date", rdv.getDateDemandeRDV().format(formatter) + " (En attente)");
                } else {
                    jsonRDV.add("date", "--/--/----");
                }
                jsonListe.add(jsonRDV);
            }
        }
        jsonContainer.add("Consultations", jsonListe);
        try (PrintWriter out = res.getWriter()) {
            out.println(jsonContainer.build().toString());
        }
    }
}