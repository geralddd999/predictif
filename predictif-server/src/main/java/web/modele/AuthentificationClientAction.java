/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import metier.modele.Client;
import metier.service.Service;

/**
 *
 * @author gschambiram
 */
public class AuthentificationClientAction extends Action {
    private final Service service;
    public AuthentificationClientAction(){
        service = new Service();
    }
    @Override
    public void execute(HttpServletRequest req){
        
        String mail = req.getParameter("login_mail");
        String password = req.getParameter("password");
        boolean success = false;
        
        Client c;
        try{
            c = service.authentifierClient(mail, password);
        }catch(Exception e){
            c = null;
        }
        
        if(c != null){
            HttpSession session = req.getSession();
            session.setAttribute("client", c);
            session.setAttribute("user_type", "client");
            success = true;
        }else{
            req.setAttribute("error", "Authentification failed : Invalid Credentials");
        }
        req.setAttribute("logged_in_user", success);
        
        System.out.println("Log-in attempt for user:" + mail + ",got: " + ((c != null) ? "SUCCESSFULL" : "FAILED"));
        
    }
}
