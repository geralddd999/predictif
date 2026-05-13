/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web.modele;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author gschambiram
 */
public class LogoutAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        
        if(session != null) {
            System.out.println("Logged out user");
            session.invalidate();
            request.setAttribute("logged_out_from_valid_session", true);
        }else{
            request.setAttribute("logged_out_from_valid_session", false);
        }
        
        //return to home page 
    }
}
