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
import java.util.List;
import metier.modele.Medium;
import metier.modele.ProfilAstral;
import metier.modele.RDV;

/**
 *
 * @author gschambiram
 */
public class ConsultEmployeeSerialisation extends Serialisation {
    @Override
    public void  appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("application/json;charset=UTF-8");
    
        boolean is_logged_in = (boolean) req.getAttribute("user_logged_in");
        
        JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
        
        if(is_logged_in){
            //write the rdv inside it
            var rdv = (RDV) req.getAttribute("current_meeting");
            if(rdv != null){
                var client_history = Json.createArrayBuilder();
                var chl = (List<RDV>) req.getAttribute("client_history");
                if(chl != null){
                    for(var h : chl){
                        // r represents a individual rdv inside the clients history
                        JsonObjectBuilder r = Json.createObjectBuilder();
                        r.add("medium_name", h.getMedium().getDenomination());
                        r.add("medium_type", h.getMedium().getClass().getSimpleName());
                        r.add("date", h.getDateRDV().toString());
                        client_history.add(r);
                    }
                }
                
                var client_astral_profile = Json.createObjectBuilder();
                var cap = (ProfilAstral) req.getAttribute("astral_profile");
                client_astral_profile.add("zodiac_sign", cap.getSigneZodiaque());
                client_astral_profile.add("color", cap.getCouleurPorteBonheur());
                client_astral_profile.add("ch_sign", cap.getSigneAstroChinois());
                client_astral_profile.add("animal", cap.getAnimalTotem());
                
                var medium = Json.createObjectBuilder();
                var m = (Medium) req.getAttribute("medium");
                medium.add("name", m.getDenomination());
                medium.add("gender", m.getGenre());
                medium.add("presentation", m.getPresentation());
                medium.add("type", m.getClass().getSimpleName());
                
                //append to json
                jsonContainer.add("client_history", client_history);
                jsonContainer.add("client_astral_profile", client_astral_profile);
                jsonContainer.add("medium", medium);
                
            }
        }
        else{
            jsonContainer.add("error", "Forbidden Access, you need to be logged-in");
            jsonContainer.add("redirect", "/predictif-server/index.html");
            return;
        }

        res.setContentType ( "application/json" ); 
        res.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = res.getWriter()){
            out.println(jsonContainer.build().toString()); 
        }
    }
}
