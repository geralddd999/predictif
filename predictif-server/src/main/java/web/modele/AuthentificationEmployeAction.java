/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import metier.modele.Employe;
import metier.service.Service;

/**
 *
 * @author gschambiram
 */
public class AuthentificationEmployeAction extends Action {
    private final Service service;
    
    public AuthentificationEmployeAction(){
        service = new Service();
    }
    @Override
    public void execute(HttpServletRequest req){
        boolean success = false;
        String mail = req.getParameter("login_mail");
        String password = req.getParameter("password");
        
        Employe e;
        try{
            e = service.authentifierEmploye(mail, password);
        }catch(Exception ex){
            e = null;
        }
        
        
        if(e != null){
            HttpSession session = req.getSession();
            session.setAttribute("user", e);
            session.setAttribute("user_type", "employe");
            success = true;
        }else{
            req.setAttribute("error", "Authentification failed : Invalid Credentials");
        }
        req.setAttribute("logged_in_user", success);
        
        System.out.println(null != "Log-in attempt for user:" + mail + ",got: " + e ? "SUCCESSFULL" : "FAILED");
        
    }
}
