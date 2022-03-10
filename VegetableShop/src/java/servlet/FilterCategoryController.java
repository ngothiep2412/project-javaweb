/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.ProductDAO;
import data.dto.ProductDTO;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Thiep Ngo
 */
public class FilterCategoryController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS_V1 = "admin.jsp";
    private static final String SUCCESS_V2 = "home.jsp";
    private static final int PAGE_SIZE = 9;
    private final Logger log = Logger.getLogger(this.getClass());
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        int page = 1;
        int roleID, categoryID, totalPage;
        try {
            URL urlLof4j = this.getClass().getResource("/log4j/Log4j.properties");
            PropertyConfigurator.configure(urlLof4j);
            String pageStr = request.getParameter("page");
            if (pageStr != null) {
                page = Integer.parseInt(pageStr);
            }
            if (request.getParameter("categoryID") == null) {
                categoryID = 0;
            } else {
                categoryID = Integer.parseInt(request.getParameter("categoryID"));
            }

            if (request.getParameter("roleID") == null) {
                roleID = 0;
            } else {
                roleID = Integer.parseInt(request.getParameter("roleID"));
            }

            if (roleID == 1) {
                int totalProducts = new ProductDAO().getTotalProductsByCategoryIDByAdmin(categoryID);
                totalPage = totalProducts / PAGE_SIZE;
                if (totalProducts % PAGE_SIZE != 0) {
                    totalPage += 1;
                }
                List<ProductDTO> listProducts = new ProductDAO().getProductsByCategoryIDWithPagingByAdmin(categoryID, page, PAGE_SIZE);
                if (listProducts.size() >= 0) {
                    request.setAttribute("LIST_PRODUCT", listProducts);
                    url = SUCCESS_V1;
                }
            } else {
                int totalProducts = new ProductDAO().getTotalProductsByCategoryIDByUser(categoryID);
                totalPage = totalProducts / PAGE_SIZE;
                if (totalProducts % PAGE_SIZE != 0) {
                    totalPage += 1;
                }
                List<ProductDTO> listProducts = new ProductDAO().getProductsByCategoryIDWithPagingByUser(categoryID, page, PAGE_SIZE);
                if (listProducts.size() >= 0) {
                    request.setAttribute("LIST_PRODUCT", listProducts);
                    url = SUCCESS_V2;
                }
            }
            request.setAttribute("PAGE", page);
            request.setAttribute("CATEGORYID", categoryID);
            request.setAttribute("TOTAL_PAGE", totalPage);

        } catch (Exception e) {
            log("Error at FilterCategoryController" + e.toString());
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
