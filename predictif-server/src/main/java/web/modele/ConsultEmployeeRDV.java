/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.RDV;
import metier.service.Service;

/**
 *
 * @author gschambiram
 */
public class ConsultEmployeeRDV extends Action {
    private Service service;
    public ConsultEmployeeRDV(){
        service = new Service();
    }
    
    @Override
    public void execute(HttpServletRequest req){
        HttpSession session = req.getSession(false);
        
        if(session == null){
            //add error in the serialization
            req.setAttribute("user_logged_in", false);
            return;
        }
        
        if(!"employee".equals(session.getAttribute("user_type"))){
            req.setAttribute("user_logged_in", false);
            return;
        }
        req.setAttribute("user_logged_in", true);
        Long id = ((Employe) session.getAttribute("user")).getId();

        // fetch fresh data so RDVs added while the employee is logged in appear on refresh
        Employe emp;
        try{
            emp = service.trouverEmployeParId(id);
        }catch(Exception e){
            System.err.println("Error fetching employee: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        
        if(emp != null){
            
            RDV rdv = emp.getOpenRDV();
            
            // check if null_check is needed for later
            req.setAttribute("current_meeting", rdv);
            if(rdv != null){
                session.setAttribute("rdv", rdv); 
                Client c = rdv.getClient();
                
                var medium = rdv.getMedium();
                req.setAttribute("medium",medium); 
                req.setAttribute("client_history", null);
                try{
                    var client_history = service.listerRDVClient(c);
                    req.setAttribute("client_history", client_history);
                }catch(Exception e){
                    System.err.println("Error fetching client history: " + e.getMessage());
                    e.printStackTrace();
                }
                var astral_profile = c.getProfilAstral();
                req.setAttribute("astral_profile", astral_profile);
            }
        }
    }
}
