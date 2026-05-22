package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import metier.modele.RDV;
import metier.service.Service;

public class CloseRDVAction extends Action{
  private Service service;
  public CloseRDVAction(){
    service = new Service();
  }
  @Override
    public void execute(HttpServletRequest req){

      HttpSession session = req.getSession(false);
      req.setAttribute("op_success", false);
      if(session == null){
        return;
      }
      String user_type = (String)session.getAttribute("user_type");
      if("employee".equals(user_type)){
        RDV r = (RDV) session.getAttribute("rdv");
        if(r == null){
          return;
        }
        try{
          boolean op_success = service.fermetureRDV(r);
          req.setAttribute("op_success", op_success);
        }catch(Exception e){
          System.err.println("Error closing RDV: " + e.getMessage());
          e.printStackTrace();
        }
      }
    }

}
