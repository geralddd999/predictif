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
import web.test.DemandeTest;

/**
 *
 * @author gschambiram
 */
public class DemandesSerialisation extends Serialisation {
    
    @Override
    public void  appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("application/json;charset=UTF-8");
    
        var demandes = (List<DemandeTest>) req.getAttribute("demandes");
        
        //how tf do i build the json
        JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
        JsonArrayBuilder jsonListeDemandes = Json.createArrayBuilder();
        
        for (var d : demandes){
            JsonObjectBuilder jsonDemande = Json.createObjectBuilder();
            
            jsonDemande.add("id", d.getId());
            jsonDemande.add("dateCreation", d.getDateCreation().toString());
            jsonDemande.add("description", d.getDescription());
            
            jsonListeDemandes.add(jsonDemande);
        }
        
        jsonContainer.add("demands",jsonListeDemandes);
        
        res.setContentType ( "application/json" ); 
        res.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = res.getWriter()){
            out.println(jsonContainer.build().toString()); 
        }
        //PrintWriter out = res.getWriter();
        
    }
}
