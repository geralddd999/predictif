package web.vue;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PredictionsSerialisation extends Serialisation{
  @Override
  public void  appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException{
    res.setContentType("application/json;charset=UTF-8");

    boolean allowed = Boolean.TRUE.equals(req.getAttribute("operation_allowed"));
    boolean has_active_rdv = Boolean.TRUE.equals(req.getAttribute("active_rdv"));

    JsonObjectBuilder jsonContainer = Json.createObjectBuilder();
    jsonContainer.add("operation_allowed", allowed);

    if(allowed && has_active_rdv){
      var predictions_list = (List<String>) req.getAttribute("predictions");
      if(predictions_list != null){
        jsonContainer.add("predictions", Json.createArrayBuilder(predictions_list));
      }
    }
    res.setContentType ( "application/json" ); 
    res.setCharacterEncoding("UTF-8");
    
    try (PrintWriter out = res.getWriter()){
        out.println(jsonContainer.build().toString()); 
    }
  }
}
