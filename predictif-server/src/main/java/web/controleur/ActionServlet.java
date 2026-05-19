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
import web.modele.Action;
import web.modele.InscrirClientAction;
import web.vue.InscreptionClientSerilisation;
import web.vue.Serialisation;
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
        
        Action action = null ;
        Serialisation serialisation = null ; 
        
        switch(todo){
            case"inscrire-client" :
                action = new InscrirClientAction ();
                serialisation = new InscreptionClientSerilisation();
                break;
                
            default:
                System.out.println("invalid request ");
                break;
        }
          
            
            if(action !=null && serialisation!=null){
                action.execute(request);
                serialisation.appliquer(request, response);
            }else{
                 response.setContentType("application/json;charset=UTF-8");
                 response.getWriter().println("{\"success\":false,\"message\":\"Action inconnue\"}");
            }
            
        }
        
   
        
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

   
    
    

}
