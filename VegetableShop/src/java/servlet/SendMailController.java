/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import sendmail.SendEmail;
import sendmail.UserEmail;

/**
 *
 * @author Thiep Ngo
 */
public class SendMailController extends HttpServlet {

    private static final String ERROR = "create.jsp";
    private static final String SUCCESS = "verify.jsp";
    private final Logger log = Logger.getLogger(this.getClass());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            URL urlLof4j = this.getClass().getResource("/log4j/Log4j.properties");
            PropertyConfigurator.configure(urlLof4j);
            String fullName = request.getParameter("fullName");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String birthday = request.getParameter("birthday");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            int roleID = 2;
            int status = 1;
            SendEmail sm = new SendEmail();
            String code = sm.getRandom();

            UserEmail user = new UserEmail(email, fullName, password, roleID, address, birthday, phone, status, code);

            boolean test = SendEmail.sendMail(user);
            if (test) {
                HttpSession session = request.getSession();
                session.setAttribute("USER_AUTHCODE", user);
                url = SUCCESS;
            }

        } catch (Exception e) {
            log("Error at SendMailController" + e.toString());
             log.info(e.getMessage(), e);
        } finally {
            response.sendRedirect(url);
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
