/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import metier.modele.RDV;
import metier.service.Service;

/**
 *
 * @author gschambiram
 */
public class StartRDVAction extends Action {
    
    private Service service;
    public StartRDVAction(){
        service = new Service();
    }
    
    @Override
    public void execute(HttpServletRequest req){
        req.setAttribute("op_success", false);

        HttpSession session = req.getSession(false);
        if(session == null){
            req.setAttribute("user_logged_in", false);
            return;
        }

        var rdv = (RDV) session.getAttribute("rdv");
        if(rdv == null){
            return;
        }

        try{
            service.commencerRDV(rdv);
        }catch(Exception e){
            System.err.println("Error in commencerRDV notification: " + e.getMessage());
            e.printStackTrace();
        }
        req.setAttribute("op_success", true);
    }
}
