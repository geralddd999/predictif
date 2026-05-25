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
import web.modele.AddCommentToRDVAction;
import web.modele.AuthentificationClientAction;
import web.modele.AuthentificationEmployeAction;
import web.modele.CloseRDVAction;
import web.modele.ConsultEmployeeRDV;
import web.modele.GetPredictionsAction;
import web.modele.LogoutAction;
import web.modele.StartRDVAction;
import web.vue.AuthentificationSerialisation;
import web.vue.ConsultEmployeeSerialisation;
import web.vue.LogoutSerialisation;
import web.vue.OperationStatusSerialisation;
import web.vue.PredictionsSerialisation;
import metier.service.Service;
import web.modele.*;
import web.vue.*;
/**
 *
 * @author gschambiram
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet", "/faire-une-demande"})
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
        
        if (request.getServletPath().equals("/faire-une-demande")) {
            request.getRequestDispatcher("faire-une-demande.html").forward(request, response);
            return;
        }
        
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
            case "start-current-rdv" : {
                new StartRDVAction().execute(request);
                new OperationStatusSerialisation().appliquer(request, response);
                break;
            }
            case "consult-employee-rdv" : {
                new ConsultEmployeeRDV().execute(request);
                new ConsultEmployeeSerialisation().appliquer(request, response);
                break;
            }
            case "get-predictions" : {
                new GetPredictionsAction().execute(request);
                new PredictionsSerialisation().appliquer(request, response);
                break;
            }
            case "close-rdv" : {
                new CloseRDVAction().execute(request);
                new OperationStatusSerialisation().appliquer(request, response);
                break;
            }
            case "add-comment-to-rdv" : {
                new AddCommentToRDVAction().execute(request);
                new OperationStatusSerialisation().appliquer(request, response);
                break;
            }
            case "logout" : {
                new LogoutAction().execute(request);
                new LogoutSerialisation().appliquer(request, response);
                break;

            case "lister-mediums": {
                Action action = new AfficherListeMedium();
                action.execute(request); 

                Serialisation serialisation = new MediumsSerialisation();
                serialisation.appliquer(request, response);
                break;
            }
            case "obtenir-profil-client": {
                Action action = new ObtenirProfilClientAction();
                action.execute(request);

                Serialisation serialisation = new ProfilClientSerialisation();
                serialisation.appliquer(request, response);
                break;
            }
            
            case "creer-rdv": {
                Action action = new CreerRDVAction();
                action.execute(request);

                Serialisation serialisation = new CreerRDVSerialisation();
                serialisation.appliquer(request, response);
                break;
            }
            
            default: {
                System.out.println("Invalid request received: " + todo);
                break;
            }
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.creerFabriquePersistance();
    }

    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy(); 
    }
    
    
    
//    @Override
//    public void init() throws ServletException {
//        // 1. Initialisation de la persistence (EMFactory)
//        JpaUtil.creerFabriquePersistance();
//
////        // 2. Appel des services d'initialisation des données
////        Service service = new Service();
////
////        // Remplit la base avec les médiums (Serena, Mme Irma, etc.)
////        service.initialiserMediums(); 
////
////        // Remplit la base avec les employés de test
////        service.initialiserEmploye(); 
//    }
//
//@Override
//public void destroy() {
//    JpaUtil.fermerFabriquePersistance(); 
//}
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
