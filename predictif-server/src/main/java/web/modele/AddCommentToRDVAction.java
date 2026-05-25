package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import metier.modele.RDV;
import metier.service.Service;

public class AddCommentToRDVAction extends Action{
  private Service service;
  public AddCommentToRDVAction(){
    service = new Service();
  }

  @Override
    public void execute(HttpServletRequest req){

      HttpSession session = req.getSession(false);
      req.setAttribute("op_success", false);
      String comment = req.getParameter("employee_comment");
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
          boolean op_success = service.saisirCommentaire(r, comment);
          req.setAttribute("op_success", op_success);
        }catch(Exception e){
          System.err.println("Error adding comment to RDV: " + e.getMessage());
          e.printStackTrace();
        }
      }
    }
}
