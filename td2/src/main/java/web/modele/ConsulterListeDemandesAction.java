/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import web.test.ServiceTest;
/**
 *
 * @author gschambiram
 */
public class ConsulterListeDemandesAction extends Action{
    
    @Override
    public void execute(HttpServletRequest req){
        ServiceTest service = new ServiceTest();
        
        //req.setAttribute("studentsList", req);
        //print the students list
        var demandes = service.listerDemandes();
        req.setAttribute("demandes", demandes);
        
        
        System.out.println(demandes);
    }
    
}
