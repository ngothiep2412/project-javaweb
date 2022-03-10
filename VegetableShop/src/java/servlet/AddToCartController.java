package servlet;

import data.dao.ProductDAO;
import data.dto.ProductDTO;
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
public class AddToCartController extends HttpServlet {

    private final Logger log = Logger.getLogger(this.getClass());
    private static final String ERROR = "error.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            URL urlLof4j = this.getClass().getResource("/log4j/Log4j.properties");
            PropertyConfigurator.configure(urlLof4j);
            HttpSession session = request.getSession();
            int productID = Integer.parseInt(request.getParameter("productID"));
            String categoryIDStr = request.getParameter("categoryID");
            String keywordStr = request.getParameter("keyword");
            if (session.getAttribute("LOGIN_USER") == null) {
                url = "login.jsp";
            } else {
                if (request.getParameter("page") != null) {
                    int page = Integer.parseInt(request.getParameter("page"));
                    request.setAttribute("PAGE", page);
                }

                Map<Integer, Cart> carts = (Map<Integer, Cart>) session.getAttribute("CARTS");
                if (carts == null) {
                    carts = new LinkedHashMap<>();
                }
                if (!carts.containsKey(productID)) {
                    ProductDTO product = new ProductDAO().getProductByProductIDByUser(productID);
                    carts.put(productID, new Cart(product, 1));
                } else {
                    int maxQuantity = new ProductDAO().getProductQuantity(productID);
                    int currentQuantity = carts.get(productID).getQuantity();
                    if (currentQuantity >= maxQuantity) {
                        log.info("Eror, this product has not enough quantity");
                        request.setAttribute("ERROR_QUANTITY_MESSAGE", "This product has not enough quantity.");
                    } else {
                        carts.get(productID).setQuantity(currentQuantity + 1);
                    }
                }
                session.setAttribute("CARTS", carts);
                url = (String) session.getAttribute("URL");
                if (url == null) {
                    url = "MainController?action=Home";
                }
                if (categoryIDStr != null) {
                    url = "MainController?action=Filter";
                }
                if (keywordStr != null) {
                    url = "MainController?action=Search";
                }
            }
        } catch (Exception e) {
            log("Error at AddToCartController" + e.toString());
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
