package data.dao;

import data.dto.ProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author Thiep Ngo
 */
public class ProductDAO {

    private static final String SEARCH_PRODUCT_ADMIN = "SELECT [productID]\n"
            + "                                       ,[productName]\n"
            + "                                       ,[quantity]\n"
            + "                                       ,[price]\n"
            + "                                       ,[description]\n"
            + "                                       ,[imageUrl]\n"
            + "                                       ,[tblProduct].[categoryID]\n"
            + "                                       ,[importDate]\n"
            + "                                       ,[usingDate]\n"
            + "                                       ,[tblProduct].[status]\n"
            + "                                       ,[tblCategory].categoryName\n"
            + "                                 FROM [dbo].[tblProduct] INNER JOIN [tblCategory]\n"
            + "                                 ON [tblProduct].[categoryID] = [tblCategory].[categoryID] WHERE [productName] LIKE ? AND [tblProduct].[status] = 1\n"
            + "					ORDER BY [productID]\n"
            + "                                 OFFSET (? - 1) * ? ROW FETCH NEXT ? ROWS ONLY";
    private static final String SEARCH_PRODUCT_USER = "SELECT [productID]\n"
            + "                                      ,[productName]\n"
            + "                                      ,[quantity]\n"
            + "                                      ,[price]\n"
            + "                                      ,[description]\n"
            + "                                      ,[imageUrl]\n"
            + "                                      ,[categoryID]\n"
            + "                                      ,[importDate]\n"
            + "                                      ,[usingDate]\n"
            + "                                      ,[tblProduct].[status]\n"
            + "                                FROM [dbo].[tblProduct] "
            + "                                WHERE DATEDIFF (day, [usingDate], getDate()) <= 0 AND [quantity] > 0 AND [tblProduct].[status] = 1 AND [productName] LIKE ?\n"
            + "                                ORDER BY [productID]  OFFSET (? - 1) * ? ROW FETCH NEXT ? ROWS ONLY";
    private static final String SEARCH_CATEGORY_ADMIN = "SELECT [productID]\n"
            + "                                          ,[productName]\n"
            + "                                          ,[quantity]\n"
            + "                                          ,[price]\n"
            + "                                          ,[description]\n"
            + "                                          ,[imageUrl]\n"
            + "                                          ,[tblProduct].[categoryID]\n"
            + "                                          ,[importDate]\n"
            + "                                          ,[usingDate]\n"
            + "                                          ,[tblCategory].categoryName\n"
            + "                                          ,[tblProduct].[status]\n"
            + "                                    FROM [dbo].[tblProduct] INNER JOIN [tblCategory]\n"
            + "                                    ON [tblProduct].[categoryID] = [tblCategory].[categoryID] "
            + "                                    WHERE [tblProduct].[categoryID] LIKE ? AND [tblProduct].[status] = 1\n"
            + "					   ORDER BY [productID]\n"
            + "                                    OFFSET (? - 1) * ? ROW FETCH NEXT ? ROWS ONLY";
    private static final String SEARCH_CATEGORY_USER = "SELECT [productID]\n"
            + "                                          ,[productName]\n"
            + "                                          ,[quantity]\n"
            + "                                          ,[price]\n"
            + "                                          ,[description]\n"
            + "                                          ,[imageUrl]\n"
            + "                                          ,[tblProduct].[categoryID]\n"
            + "                                          ,[importDate]\n"
            + "                                          ,[usingDate]\n"
            + "                                          ,[tblCategory].categoryName\n"
            + "                                          ,[tblProduct].[status]\n"
            + "                                    FROM [dbo].[tblProduct] INNER JOIN [tblCategory]\n"
            + "                                    ON [tblProduct].[categoryID] = [tblCategory].[categoryID] "
            + "                                    WHERE [tblProduct].[categoryID] LIKE ? AND [tblProduct].[status] = 1 "
            + "                                    AND DATEDIFF (day, [usingDate], getDate()) <= 0 AND [quantity] > 0 \n"
            + "					   ORDER BY [productID]\n"
            + "                                    OFFSET (? - 1) * ? ROW FETCH NEXT ? ROWS ONLY";
    private static final String DELETE = "UPDATE [dbo].[tblProduct]\n"
            + "                           SET [status] = 0 "
            + "                           WHERE [productID] = ?";

    public List<ProductDTO> searchByAdminWithPaging(String search, int pageStr, int PAGE_SIZE) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PRODUCT_ADMIN);
                ptm.setString(1, "%" + search + "%");
                ptm.setInt(2, pageStr);
                ptm.setInt(3, PAGE_SIZE);
                ptm.setInt(4, PAGE_SIZE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    int categoryID = rs.getInt("categoryID");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    list.add(new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, rs.getString(11), status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public boolean deleteProduct(int productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE);
                ptm.setInt(1, productID);
                int value = ptm.executeUpdate();
                check = value > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
        }
        return check;
    }

    public List<ProductDTO> getProductsByCategoryID(int categoryID) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [productID]\n"
                    + "         ,[productName]\n"
                    + "         ,[quantity]\n"
                    + "         ,[price]\n"
                    + "         ,[description]\n"
                    + "         ,[imageUrl]\n"
                    + "         ,[tblProduct].[categoryID]\n"
                    + "         ,[importDate]\n"
                    + "         ,[usingDate]\n"
                    + "         ,[tblCategory].categoryName\n"
                    + "         ,[tblProduct].[status]\n"
                    + "   FROM [dbo].[tblProduct] INNER JOIN [tblCategory]\n"
                    + "   ON [tblProduct].[categoryID] = [tblCategory].[categoryID]"
                    + "   WHERE [tblProduct].[categoryID] = ? AND [tblProduct].[status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, categoryID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    list.add(new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, rs.getString(11), status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    // Admin Show Products
    public List<ProductDTO> getListProductWithPaging(int page, int PAGE_SIZE) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [productID]\n"
                    + "         ,[productName]\n"
                    + "         ,[quantity]\n"
                    + "         ,[price]\n"
                    + "         ,[description]\n"
                    + "         ,[imageUrl]\n"
                    + "         ,[tblProduct].[categoryID]\n"
                    + "         ,[importDate]\n"
                    + "         ,[usingDate]\n"
                    + "         ,[tblCategory].categoryName\n"
                    + "         ,[tblProduct].[status]\n"
                    + "   FROM [dbo].[tblProduct] INNER JOIN [tblCategory]\n"
                    + "   ON [tblProduct].[categoryID] = [tblCategory].[categoryID] WHERE [tblProduct].[status] = 1"
                    + "   ORDER BY [productID]\n"
                    + "   OFFSET (? - 1) * ? ROW FETCH NEXT ? ROWS ONLY";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, page);
                ptm.setInt(2, PAGE_SIZE);
                ptm.setInt(3, PAGE_SIZE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    int categoryID = rs.getInt("categoryID");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    list.add(new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, rs.getString(10), status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public int getTotalProductsByAdmin() throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT([productID]) FROM [dbo].[tblProduct] WHERE [status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return -1;
    }

    public int getTotalProductsByUser() throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT([productID]) FROM [dbo].[tblProduct] "
                    + "   WHERE DATEDIFF (day, [usingDate], getDate()) <= 0 AND [quantity] > 0 AND [status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return -1;
    }

    public ProductDTO getProductByProductIDByUser(int productID) throws SQLException {
        ProductDTO product = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [productID]\n"
                    + "         ,[productName]\n"
                    + "         ,[quantity]\n"
                    + "         ,[price]\n"
                    + "         ,[description]\n"
                    + "         ,[imageUrl]\n"
                    + "         ,[categoryID]\n"
                    + "         ,[importDate]\n"
                    + "         ,[usingDate]\n"
                    + "         ,[status]\n"
                    + "   FROM [dbo].[tblProduct] "
                    + "   WHERE [productID] = ? "
                    + "   AND DATEDIFF (day, [usingDate], getDate()) <= 0 AND [quantity] > 0 AND [status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    int categoryID = rs.getInt("categoryID");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    product = new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, "", status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return product;
    }

    
     public ProductDTO getProductByProductIDByAdmin(int productID) throws SQLException {
        ProductDTO product = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [productID]\n"
                    + "         ,[productName]\n"
                    + "         ,[quantity]\n"
                    + "         ,[price]\n"
                    + "         ,[description]\n"
                    + "         ,[imageUrl]\n"
                    + "         ,[categoryID]\n"
                    + "         ,[importDate]\n"
                    + "         ,[usingDate]\n"
                    + "         ,[status]\n"
                    + "   FROM [dbo].[tblProduct] "
                    + "   WHERE [productID] = ? "
                    + "   AND [status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    int categoryID = rs.getInt("categoryID");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    product = new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, "", status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return product;
    }
    
    
    
    public boolean updateProduct(ProductDTO products, int productID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            String sql = "UPDATE [dbo].[tblProduct]\n"
                    + "   SET [productName] = ?\n"
                    + "      ,[quantity] = ?\n"
                    + "      ,[price] = ?\n"
                    + "      ,[description] =?\n"
                    + "      ,[imageUrl] = ?\n"
                    + "      ,[categoryID] = ?\n"
                    + "      ,[importDate] =?\n"
                    + "      ,[usingDate] = ?  \n"
                    + "	  WHERE [productID] = ? AND [status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, products.getProductName());
                ptm.setInt(2, products.getQuantity());
                ptm.setDouble(3, products.getPrice());
                ptm.setString(4, products.getDescription());
                ptm.setString(5, products.getImageUrl());
                ptm.setInt(6, products.getCategoryID());
                ptm.setString(7, products.getImportDate());
                ptm.setString(8, products.getUsingDate());
                ptm.setInt(9, productID);
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }

        }
        return check;
    }

    public int getTotalProductsWithKeyWordByAdmin(String search) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(productID) FROM [dbo].[tblProduct] "
                    + "   WHERE [productName] LIKE ? AND [status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return -1;
    }

    public int getTotalProductsByCategoryIDByAdmin(int categoryID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(productID) FROM [dbo].[tblProduct] WHERE [categoryID] LIKE ? AND [status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, categoryID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return -1;
    }

    public List<ProductDTO> getProductsByCategoryIDWithPagingByAdmin(int categoryID, int page, int PAGE_SIZE) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_CATEGORY_ADMIN);
                ptm.setInt(1, categoryID);
                ptm.setInt(2, page);
                ptm.setInt(3, PAGE_SIZE);
                ptm.setInt(4, PAGE_SIZE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    list.add(new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, rs.getString(10), status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public boolean createANewProduct(ProductDTO product) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            String sql = "INSERT INTO [dbo].[tblProduct]\n"
                    + "           ([productName]\n"
                    + "           ,[quantity]\n"
                    + "           ,[price]\n"
                    + "           ,[description]\n"
                    + "           ,[imageUrl]\n"
                    + "           ,[categoryID]\n"
                    + "           ,[importDate]\n"
                    + "           ,[usingDate]\n"
                    + "           ,[status])\n"
                    + "     VALUES\n"
                    + "          (?,?,?,?,?,?,?,?,?)";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ptm.setString(1, product.getProductName());
                ptm.setInt(2, product.getQuantity());
                ptm.setDouble(3, product.getPrice());
                ptm.setString(4, product.getDescription());
                ptm.setString(5, product.getImageUrl());
                ptm.setInt(6, product.getCategoryID());
                ptm.setString(7, product.getImportDate());
                ptm.setString(8, product.getUsingDate());
                ptm.setInt(9, product.getStatus());
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }

        }
        return check;
    }

    public List<ProductDTO> getListProduct() throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [productID]\n"
                    + "         ,[productName]\n"
                    + "         ,[quantity]\n"
                    + "         ,[price]\n"
                    + "         ,[description]\n"
                    + "         ,[imageUrl]\n"
                    + "         ,[tblProduct].[categoryID]\n"
                    + "         ,[importDate]\n"
                    + "         ,[usingDate]\n"
                    + "         ,[tblCategory].categoryName\n"
                    + "         ,[tblProduct].[status]\n"
                    + "  FROM [dbo].[tblProduct] INNER JOIN [tblCategory]\n"
                    + "  ON [tblProduct].[categoryID] = [tblCategory].[categoryID] WHERE [tblProduct].[status] = 1\n"
                    + "	 ORDER BY [productID]\n";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    int categoryID = rs.getInt("categoryID");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    list.add(new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, rs.getString(10), status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<ProductDTO> searchByUserWithPaging(String search, int pageStr, int PAGE_SIZE) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PRODUCT_USER);
                ptm.setString(1, "%" + search + "%");
                ptm.setInt(2, pageStr);
                ptm.setInt(3, PAGE_SIZE);
                ptm.setInt(4, PAGE_SIZE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    int categoryID = rs.getInt("categoryID");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    list.add(new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, "", status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public int getTotalProductsWithKeyWordByUser(String search) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(productID) FROM [dbo].[tblProduct] "
                    + "   WHERE DATEDIFF (day, [usingDate], getDate()) <= 0 AND [quantity] > 0 AND [status] = 1 "
                    + "   AND [productName] LIKE ?";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return -1;
    }

    public List<ProductDTO> getListProductWithPagingByUser(int page, int PAGE_SIZE) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [productID]\n"
                    + "      ,[productName]\n"
                    + "      ,[quantity]\n"
                    + "      ,[price]\n"
                    + "      ,[description]\n"
                    + "      ,[imageUrl]\n"
                    + "      ,[categoryID]\n"
                    + "      ,[importDate]\n"
                    + "      ,[usingDate]\n"
                    + "      ,[status]\n"
                    + "   FROM [dbo].[tblProduct]  WHERE  DATEDIFF (day, [usingDate], getDate()) <= 0 AND [quantity] > 0 AND [status] = 1\n"
                    + "   ORDER BY [productID] OFFSET  (? - 1) * ? ROW FETCH NEXT ? ROWS ONLY";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, page);
                ptm.setInt(2, PAGE_SIZE);
                ptm.setInt(3, PAGE_SIZE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    int categoryID = rs.getInt("categoryID");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    list.add(new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, "", status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public int getProductQuantity(int productID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [quantity] FROM [dbo].[tblProduct]"
                    + "   WHERE DATEDIFF (day, [usingDate], getDate()) <= 0 "
                    + "   AND [status] = 1 AND productID = ?";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, productID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return -1;
    }

    public List<ProductDTO> getProductsByCategoryIDWithPagingByUser(int categoryID, int page, int PAGE_SIZE) throws SQLException {
        List<ProductDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_CATEGORY_USER);
                ptm.setInt(1, categoryID);
                ptm.setInt(2, page);
                ptm.setInt(3, PAGE_SIZE);
                ptm.setInt(4, PAGE_SIZE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String description = rs.getString("description");
                    String imageUrl = rs.getString("imageUrl");
                    String importDate = rs.getString("importDate");
                    String usingDate = rs.getString("usingDate");
                    int status = rs.getInt("status");
                    list.add(new ProductDTO(productID, productName, quantity, price, description, imageUrl, categoryID, importDate, usingDate, rs.getString(10), status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public int getTotalProductsByCategoryIDByUser(int categoryID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(productID) FROM [dbo].[tblProduct] "
                    + "WHERE [categoryID] LIKE ? AND DATEDIFF (day, [usingDate], getDate()) <= 0 AND [quantity] > 0 AND [status] = 1 ";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, categoryID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return -1;
    }

    public void updateQuantityAfterBuy(int productID, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            String sql = "UPDATE [dbo].[tblProduct]\n"
                    + "   SET [quantity] = [quantity] - ? WHERE productID = ? ";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setInt(1, quantity);
                ptm.setInt(2, productID);
                ptm.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}
