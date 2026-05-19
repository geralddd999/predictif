/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package web.controleur;

import dao.JpaUtil;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import web.modele.AuthentificationClientAction;
import web.modele.AuthentificationEmployeAction;
import web.modele.LogoutAction;
import web.vue.AuthentificationSerialisation;
import web.vue.LogoutSerialisation;
/**
 *
 * @author gschambiram
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String todo = request.getParameter("todo");
        
        System.out.println(todo);
        switch(todo){
            
            case "authenticate-client" : {
                new AuthentificationClientAction().execute(request);
                new AuthentificationSerialisation().appliquer(request, response);
                break;
            }
            case "authenticate-employe" : {
                new AuthentificationEmployeAction().execute(request);
                new AuthentificationSerialisation().appliquer(request, response);
                break;
            }
            case "get-current-rdv" : {
                break;
            }
            case "logout" : {
                new LogoutAction().execute(request);
                new LogoutSerialisation().appliquer(request, response);
                break;

            }
            default: {
                System.out.println("Invalid request received");
                break;
            }
        }
    }
    
    @Override
    public void init(){
        // Initializes the EMFactory
        JpaUtil.creerFabriquePersistance();
    }
    
    @Override
    public void destroy(){
        // Destroys the EMFactory
        JpaUtil.fermerFabriquePersistance();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
