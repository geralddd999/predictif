/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.vue;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

/**
 *
 * @author bbelkho
 */
public class InscreptionClientSerilisation  extends Serialisation {

    @Override
    public void appliquer(HttpServletRequest request, HttpServletResponse response) throws IOException {
          response.setContentType("application/json;charset=UTF-8");
          
          Boolean success = (Boolean) request.getAttribute("success");
          String message = (String) request.getAttribute("message");
          
          JsonObject  json = Json.createObjectBuilder()
                  .add("success", success!=null&& success)
                  .add("message",message!=null ? message:"")
                  .build();
          System.out.println("inscription-built"+json.toString());
          PrintWriter out = response.getWriter();
          out.println(json.toString()); 
          out.close();
           
            
    }
    
}
