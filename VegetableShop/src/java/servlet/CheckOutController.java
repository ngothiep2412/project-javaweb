/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import data.dao.OrderDAO;
import data.dao.OrderDetailDAO;
import data.dao.ProductDAO;
import data.dao.ShippingDAO;
import data.dto.OrderDTO;
import data.dto.ShippingDTO;
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
public class CheckOutController extends HttpServlet {
    
    private final Logger log = Logger.getLogger(this.getClass());
    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "checkout.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = ERROR;
        double totalMoney = 0;
        try {
            URL urlLof4j = this.getClass().getResource("/log4j/Log4j.properties");
            PropertyConfigurator.configure(urlLof4j);
            HttpSession session = request.getSession();

            if (session.getAttribute("LOGIN_USER") == null) {
                url = ERROR;
            } else {
                Map<Integer, Cart> carts = (Map<Integer, Cart>) session.getAttribute("CARTS");
                if (carts == null) {
                    carts = new LinkedHashMap<>();
                }
                for (Map.Entry<Integer, Cart> entry : carts.entrySet()) {
                    Integer productID = entry.getKey();
                    Cart cart = entry.getValue();
                    totalMoney += cart.getQuantity() * cart.getProduct().getPrice();
                }
                url = SUCCESS;
                request.setAttribute("TOTAL_MONEY", totalMoney);
            }

        } catch (Exception e) {
            log("Error at CheckOutController" + e.toString());
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            double totalPrice = 0;
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String note = request.getParameter("note");

            ShippingDTO shipping = new ShippingDTO(0, fullName, phone, address);
            int shippingID = new ShippingDAO().createReturnID(shipping);
            HttpSession session = request.getSession();
            if (session.getAttribute("CARTS") == null) {
                url = "error.jsp";
            } else {
                Map<Integer, Cart> carts = (Map<Integer, Cart>) session.getAttribute("CARTS");
                if (carts == null) {
                    carts = new LinkedHashMap<>();
                }

                for (Map.Entry<Integer, Cart> entry : carts.entrySet()) {
                    Cart cart = entry.getValue();
                    new ProductDAO().updateQuantityAfterBuy(cart.getProduct().getProductID(), cart.getQuantity());
                    totalPrice += cart.getQuantity() * cart.getProduct().getPrice();
                }
                OrderDTO order = new OrderDTO(0, email, totalPrice, note, "", shippingID);

                int orderID = new OrderDAO().createReturnID(order);

                new OrderDetailDAO().saveCart(orderID, carts);
                session.removeAttribute("CARTS");
                url = "thanks.jsp";
            }

        } catch (Exception e) {
            log("Error at CheckOutController" + e.toString());
        } finally {
            response.sendRedirect(url);
        }
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
