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

/**
 *
 * @author gschambiram
 */
public class CreerRDVSerialisation extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        
        Boolean succes = (Boolean) req.getAttribute("rdvSuccess");
        
        JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
        
        jsonContainer.add("success", succes != null ? succes : false);
        
        try (PrintWriter out = res.getWriter()) {
            out.println(jsonContainer.build().toString());
        }
    }
}