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
import metier.modele.Client;

/**
 *
 * @author gschambiram
 */
public class AuthentificationSerialisation extends Serialisation {
    @Override
    public void  appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("application/json;charset=UTF-8");
    
        var success = (boolean) req.getAttribute("logged_in_user");
        
        JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
        
        jsonContainer.add("auth_success", success);
        if(success){
            jsonContainer.add("redirect", ""); //redirect to after-login window
        }

        res.setContentType ( "application/json" ); 
        res.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = res.getWriter()){
            out.println(jsonContainer.build().toString()); 
        }
    }
}



