/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.vue;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import metier.modele.*;

/**
 *
 * @author gschambiram
 */
public class ProfilClientSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json;charset=UTF-8");

        var client = (Client) req.getAttribute("clientConcerne");

        JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
        JsonObjectBuilder jsonProfil = Json.createObjectBuilder();

        if (client != null && client.getProfilAstral() != null) {
            ProfilAstral pa = client.getProfilAstral();

            jsonContainer.add("success", true);

            jsonProfil.add("id", client.getId() != null ? client.getId() : -1L);
            jsonProfil.add("nom", client.getNom() != null ? client.getNom() : "");
            jsonProfil.add("prenom", client.getPrenom() != null ? client.getPrenom() : "");
            jsonProfil.add("genre", client.getGenre() != null ? client.getGenre() : "M/F");
            
            jsonProfil.add("zodiaque", pa.getSigneZodiaque() != null ? pa.getSigneZodiaque() : "");
            jsonProfil.add("chinois", pa.getSigneAstroChinois() != null ? pa.getSigneAstroChinois() : "");
            jsonProfil.add("couleur", pa.getCouleurPorteBonheur() != null ? pa.getCouleurPorteBonheur() : "");
            jsonProfil.add("animal", pa.getAnimalTotem() != null ? pa.getAnimalTotem() : "");
            
            jsonContainer.add("nom", client.getNom() != null ? client.getNom() : "");
            jsonContainer.add("prenom", client.getPrenom() != null ? client.getPrenom() : "");
            jsonContainer.add("zodiaque", pa.getSigneZodiaque() != null ? pa.getSigneZodiaque() : "");
            jsonContainer.add("chinois", pa.getSigneAstroChinois() != null ? pa.getSigneAstroChinois() : "");
            jsonContainer.add("couleur", pa.getCouleurPorteBonheur() != null ? pa.getCouleurPorteBonheur() : "");
            jsonContainer.add("animal", pa.getAnimalTotem() != null ? pa.getAnimalTotem() : "");
        } else {
            jsonContainer.add("success", false);
        }

        if (client != null) {
            jsonContainer.add("Profil", jsonProfil);
        }

        try (PrintWriter out = res.getWriter()) {
            out.println(jsonContainer.build().toString());
        }
    }
}