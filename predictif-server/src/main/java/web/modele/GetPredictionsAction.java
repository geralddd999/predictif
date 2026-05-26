package web.modele;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.RDV;
import metier.service.Service;

public class GetPredictionsAction extends Action {
  private Service service;

  public GetPredictionsAction(){
    service = new Service();
  }
  
  @Override
  public void execute(HttpServletRequest req) {
    HttpSession session = req.getSession(false);
    req.setAttribute("operation_allowed", false);
    req.setAttribute("active_rdv", false);
    if(session == null){
      return;
    }

    String user_type = (String) session.getAttribute("user_type");
    if("employee".equals(user_type)){
      req.setAttribute("operation_allowed", true);

      var e = (Employe) session.getAttribute("user");
      e = service.trouverEmployeParId(e.getId());
      session.setAttribute("user", e);
      RDV r = e.getOpenRDV();
      if(r == null){
        return;
      }
      req.setAttribute("active_rdv", true);

      try {
        Integer love_idx = Integer.parseInt(req.getParameter("love"));
        Integer health_idx = Integer.parseInt(req.getParameter("health"));
        Integer work_idx = Integer.parseInt(req.getParameter("work"));

        Client c = r.getClient();
        List<String> predictions = service.calculPrediction(c, love_idx, health_idx, work_idx);
        req.setAttribute("predictions", predictions);
      } catch (NumberFormatException ex) {
        req.setAttribute("active_rdv", false);
      }
    }
  }
}
