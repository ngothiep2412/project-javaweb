/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import sessionmodel.Cart;

/**
 *
 * @author Thiep Ngo
 */
public class DeleteCartController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "MainController?action=Cart";
    private final Logger log = Logger.getLogger(this.getClass());
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            URL urlLof4j = this.getClass().getResource("/log4j/Log4j.properties");
            PropertyConfigurator.configure(urlLof4j);
            int productID = Integer.parseInt(request.getParameter("productID"));
            HttpSession session = request.getSession();
            if (session.getAttribute("CARTS") == null) {
                url = ERROR;
            } else {
                Map<Integer, Cart> carts = (Map<Integer, Cart>) session.getAttribute("CARTS");

                if (carts == null) {
                    carts = new LinkedHashMap<>();
                }

                if (!carts.containsKey(productID)) {
                    url = ERROR;
                } else if (carts.containsKey(productID)) {
                    carts.remove(productID);
                    session.setAttribute("CARTS", carts);
                    url = SUCCESS;
                }
            }

        } catch (Exception e) {
            log("Error at DeleteCartController" + e.toString());
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
