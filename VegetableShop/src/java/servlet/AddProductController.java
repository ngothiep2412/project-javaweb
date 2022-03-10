package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.dao.ProductDAO;
import data.dto.ProductDTO;
import java.net.URL;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebServlet(name = "AddProductController", urlPatterns = {"/AddProductController"})
public class AddProductController extends HttpServlet {

    private static final String ERROR = "admin_add.jsp";
    private static final String SUCCESS = "HomeAdminController";
    private final Logger log = Logger.getLogger(this.getClass());
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = ERROR;
        try {
            URL urlLof4j = this.getClass().getResource("/log4j/Log4j.properties");
            PropertyConfigurator.configure(urlLof4j);
            String productName = request.getParameter("productName");
            String imageUrl = request.getParameter("imageUrl");
            String description = request.getParameter("description");
            Double price = Double.parseDouble(request.getParameter("price"));
            String importDate = request.getParameter("importDate");
            String usingDate = request.getParameter("usingDate");
            int status = 1;
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            
            ProductDTO product = new ProductDTO(0, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, "", status);
            boolean checkCreate = new ProductDAO().createANewProduct(product);
            if (checkCreate) {
                url = SUCCESS;
            } else {
                log.info("Error, can not add product.");
            }
        } catch (Exception e) {
            log("Error at AddProductController" + e.toString());
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
