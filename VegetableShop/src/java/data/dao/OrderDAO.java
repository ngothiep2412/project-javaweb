/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.dto.OrderDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.DBUtils;

/**
 *
 * @author Thiep Ngo
 */
public class OrderDAO {

    public int createReturnID(OrderDTO order) throws SQLException {
        String sql = "INSERT INTO [dbo].[tblOrders]\n"
                + "           ([email]\n"
                + "           ,[totalPrice]\n"
                + "           ,[note]\n"
                + "           ,[shippingID]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "          (?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ptm.setString(1, order.getEmail());
                ptm.setDouble(2, order.getTotalPrice());
                ptm.setString(3, order.getNote());
                ptm.setInt(4, order.getShippingID());
                ptm.setInt(5, 1);
                ptm.executeUpdate();
                rs = ptm.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
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
            if (rs != null) {
                rs.close();
            }
        }
        return 0;
    }

}
