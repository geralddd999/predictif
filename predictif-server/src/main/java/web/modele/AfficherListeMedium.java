/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import metier.service.Service;
/**
 *
 * @author gschambiram
 */
public class AfficherListeMedium extends Action{
    
    @Override
    public void execute(HttpServletRequest req){
        Service service = new Service();
        
        //req.setAttribute("studentsList", req);
        //print the students list
        var ListeMedium = service.listerMedium();
        req.setAttribute("Medium", ListeMedium);
        
        
        System.out.println(ListeMedium);
    }
    
}
