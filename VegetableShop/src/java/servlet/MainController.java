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

/**
 *
 * @author Thiep Ngo
 */
public class MainController extends HttpServlet {
    
    private static final String ERROR = "HomeController";
    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    private static final String SEARCH = "Search";
    private static final String SEARCH_ADMIN_CONTROLLER = "SearchProductController";
    private static final String ADD = "Add";
    private static final String ADD_PRODUCT_CONTROLLER = "AddProductController";
    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    private static final String HOME = "Home";
    private static final String HOME_CONTROLLER = "HomeController";
    private static final String FILTER = "Filter";
    private static final String FILTER_CONTROLLER = "FilterCategoryController";
    private static final String HOME_ADMIN = "HomeAdmin";
    private static final String HOME_ADMIN_CONTROLLER = "HomeAdminController";
    private static final String DELETE = "Delete";
    private static final String DELETE_CONTROLLER = "DeleteProductController";
    private static final String UPDATE = "Update";
    private static final String UPDATE_CONTROLLER = "UpdateProductController";
    private static final String ADD_ITEM_TO_CARD = "AddToCart";
    private static final String ADD_ITEM_TO_CARD_CONTROLLER = "AddToCartController";
    private static final String DETAIL = "Detail";
    private static final String DETAIL_CONTROLLER = "DetailController";
    private static final String CART = "Cart";
    private static final String CART_CONTROLLER = "CartController";
    private static final String DELETE_CART = "DeleteCart";
    private static final String DELETE_CART_CONTROLLER = "DeleteCartController";
    private static final String CHECKOUT = "CheckOut";
    private static final String CHECKOUT_CONTROLLER = "CheckOutController";
    private static final String CREATE = "Register";
    private static final String CREATE_CONTROLLER = "CreateUserController";
    private static final String VERIFY = "Verify";
    private static final String VERIFY_CONTROLLER = "VerifyController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            String action = request.getParameter("action");
            if (LOGIN.equals(action)) {
                url = LOGIN_CONTROLLER;
            } else if (SEARCH.equals(action)) {
                url = SEARCH_ADMIN_CONTROLLER;
            } else if (ADD.equals(action)) {
                url = ADD_PRODUCT_CONTROLLER;
            } else if (LOGOUT.equals(action)) {
                url = LOGOUT_CONTROLLER;
            } else if (HOME.equals(action)) {
                url = HOME_CONTROLLER;
            } else if (FILTER.equals(action)) {
                url = FILTER_CONTROLLER;
            } else if (HOME_ADMIN.equals(action)) {
                url = HOME_ADMIN_CONTROLLER;
            } else if (UPDATE.equals(action)) {
                url = UPDATE_CONTROLLER;
            } else if (DELETE.equals(action)) {
                url = DELETE_CONTROLLER;
            } else if (ADD_ITEM_TO_CARD.equals(action)) {
                url = ADD_ITEM_TO_CARD_CONTROLLER;
            } else if (DETAIL.equals(action)) {
                url = DETAIL_CONTROLLER;
            } else if (CART.equals(action)) {
                url = CART_CONTROLLER;
            } else if (DELETE_CART.equals(action)) {
                url = DELETE_CART_CONTROLLER;
            } else if (CHECKOUT.equals(action)) {
                url = CHECKOUT_CONTROLLER;
            } else if (CREATE.equals(action)) {
                url = CREATE_CONTROLLER;
            } else if (VERIFY.equals(action)) {
                url = VERIFY_CONTROLLER;
            }
        } catch (Exception e) {
            log("Error at MainController" + e.toString());
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
