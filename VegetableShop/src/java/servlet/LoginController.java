/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import data.dao.UserDAO;
import data.dto.UserDTO;
import java.net.URL;
import recaptcha.VerifyRecaptcha;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Thiep Ngo
 */
public class LoginController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final int AD = 1;
    private static final String AD_PAGE = "HomeAdminController";
    private static final int US = 2;
    private static final String US_PAGE = "HomeController";
    private final Logger log = Logger.getLogger(this.getClass());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            URL urlLof4j = this.getClass().getResource("/log4j/Log4j.properties");
            PropertyConfigurator.configure(urlLof4j);
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UserDAO dao = new UserDAO();
            UserDTO loginUser = dao.checkLogin(email, password);
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            boolean verify = VerifyRecaptcha.isCaptchaValid(gRecaptchaResponse);
            if (!verify) {
                url = ERROR;
                log.info("You miss recaptcha");
                request.setAttribute("ERROR_RECAPTCHA", "You miss recaptcha");
            } else {
                if (loginUser != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("LOGIN_USER", loginUser);
                    int roleID = loginUser.getRoleID();
                    switch (roleID) {
                        case AD:
                            url = AD_PAGE;
                            break;
                        case US:
                            url = US_PAGE;
                            break;
                        default:
                            request.setAttribute("ERROR", "Your roleId is not support");
                            log.info("Your roleId is not support");
                            break;
                    }
                } else {
                    request.setAttribute("ERROR", "Incorrect email or password");
                    log.info("Incorrect email or password");
                }
            }
        } catch (Exception e) {
            log("Error at LoginController" + e.toString());
            log.info(e.getMessage(), e);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
