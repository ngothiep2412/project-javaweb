/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.dto.ShippingDTO;
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
public class ShippingDAO {

    public int createReturnID(ShippingDTO shipping) throws SQLException {
        String sql = "INSERT INTO [dbo].[tblShipping]\n"
                + "           ([name]\n"
                + "           ,[phone]\n"
                + "           ,[address]\n"
                + "           ,[status])\n"
                + "     VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ptm.setString(1, shipping.getName());
                ptm.setString(2, shipping.getPhone());
                ptm.setString(3, shipping.getAddress());
                ptm.setInt(4, 1);

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
