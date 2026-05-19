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
        
        req.setAttribute("user_logged_in", true);
        Long id = ((Employe)session.getAttribute("user")).getId();
        
        // done this to get the most up to date emp data so if a rdv gets added when the 
        // employee is logged it it gets registered on refresh.
        Employe emp = service.trouverEmployeParId(id);
        
        if(emp != null){
            
            RDV rdv = emp.getOpenRDV();
            
            // check if null_check is needed for later
            req.setAttribute("current_meeting", rdv);
            if(rdv != null){
                session.setAttribute("rdv", rdv); 
                Client c = rdv.getClient();
                
                var medium = rdv.getMedium();
                req.setAttribute("medium",medium); 
                var client_history = service.listerRDVClient(c);
                req.setAttribute("client_history", client_history);
                var astral_profile = c.getProfilAstral();
                req.setAttribute("astral_profile", astral_profile);
            }
        }
    }
}
