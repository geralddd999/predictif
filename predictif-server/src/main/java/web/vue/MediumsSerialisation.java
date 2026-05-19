/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.vue;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import metier.modele.*;

/**
 *
 * @author gschambiram
 */
public class MediumsSerialisation extends Serialisation {
    
    @Override
    public void  appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("application/json;charset=UTF-8");
    
        var listeMedium = (List<Medium>) req.getAttribute("Medium");
        
        JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
        JsonArrayBuilder jsonListeMedium = Json.createArrayBuilder();
        
        if (listeMedium != null) {
            for (Medium m : listeMedium) {
                JsonObjectBuilder jsonMedium = Json.createObjectBuilder();
                
                jsonMedium.add("id", m.getId() != null ? m.getId() : -1L);
                jsonMedium.add("nom", m.getDenomination() != null ? m.getDenomination() : "");
                jsonMedium.add("genre", m.getGenre() != null ? m.getGenre() : "M/F");
                jsonMedium.add("description", m.getPresentation() != null ? m.getPresentation() : "");
                
                if (m instanceof Astrologue) {
                    Astrologue astro = (Astrologue) m;
                    jsonMedium.add("type", "Astrologue");
                    jsonMedium.add("formation", astro.getFormation() != null ? astro.getFormation() : "");
                    jsonMedium.add("promotion", astro.getPromotion() != null ? String.valueOf(astro.getPromotion()) : "");
                } 
                else if (m instanceof Spirite) {
                    Spirite spirite = (Spirite) m;
                    jsonMedium.add("type", "Spirite");
                    jsonMedium.add("support", spirite.getSupport() != null ? spirite.getSupport() : "");
                } 
                else if (m instanceof Cartomancien) {
                    jsonMedium.add("type", "Cartomancien");
                }
                
                jsonListeMedium.add(jsonMedium);
            }
        }
        
        jsonContainer.add("Medium", jsonListeMedium);
        
        try (PrintWriter out = res.getWriter()) {
            out.println(jsonContainer.build().toString()); 
        }
    }
}
