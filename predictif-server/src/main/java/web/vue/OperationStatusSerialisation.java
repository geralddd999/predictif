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
public class OperationStatusSerialisation extends Serialisation {
    @Override
    public void  appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException{
        res.setContentType("application/json;charset=UTF-8");

        JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
        var stat = (boolean) req.getAttribute("logged_out_from_valid_session");

        if(!stat){
            jsonContainer.add("redirect-error", true);
        }
        jsonContainer.add("redirect", "/");

        res.setContentType ( "application/json" );
        res.setCharacterEncoding("UTF-8");

        try (PrintWriter out = res.getWriter()){
            out.println(jsonContainer.build().toString());
        }
    }
}
