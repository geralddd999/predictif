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
import web.modele.Action;
import web.modele.ChargerStatistiquesAction;
import web.modele.InscrirClientAction;
import web.vue.InscreptionClientSerilisation;
import web.vue.Serialisation;
import web.vue.StatistiquesSerialisation;
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
        
        if (request.getServletPath().equals("/client-home-page")) {
            request.getRequestDispatcher("client-home-page.html").forward(request, response);
            return;
        }
        
        String todo = request.getParameter("todo");
        
        Action action = null ;
        Serialisation serialisation = null ; 
        
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
            }
            case "lister-mediums": {
                action = new AfficherListeMedium();
                action.execute(request); 

                serialisation = new MediumsSerialisation();
                serialisation.appliquer(request, response);
                break;
            }
            case "obtenir-profil-client": {
                action = new ObtenirProfilClientAction();
                action.execute(request);

                serialisation = new ProfilClientSerialisation();
                serialisation.appliquer(request, response);
                break;
            }
            
            case "creer-rdv": {
                action = new CreerRDVAction();
                action.execute(request);

                serialisation = new CreerRDVSerialisation();
                serialisation.appliquer(request, response);
                break;
            }
            
            case "lister-rdv-client": {
                action = new ListerRDVClientAction();
                action.execute(request);
                
                serialisation = new ListerRDVClientSerialisation();
                serialisation.appliquer(request, response);
                break;
            }
            
            case"inscrire-client" :
                action = new InscrirClientAction ();
                serialisation = new InscreptionClientSerilisation();
                
                action.execute(request);
                serialisation.appliquer(request, response);
                break;
                
            case "charger-statistiques" : {
                action = new ChargerStatistiquesAction();
                serialisation = new StatistiquesSerialisation();

                action.execute(request);
                serialisation.appliquer(request, response);
                break;
            }

            default: {
                System.out.println("Invalid request received: " + todo);
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


   
    
    

}
