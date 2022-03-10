/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.dao;

import data.dto.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sendmail.UserEmail;
import utils.DBUtils;

public class UserDAO {

    private static final String LOGIN = "SELECT email, fullName, roleID, address, birthday, phone, status FROM tblUsers WHERE email = ? AND password = ? AND status = 1 ";

    public UserDTO checkLogin(String email, String password) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, email);
                ptm.setString(2, password);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    int roleID = rs.getInt("roleID");
                    String address = rs.getString("address");
                    String birthday = rs.getString("birthday");
                    String phone = rs.getString("phone");
                    int status = rs.getInt("status");
                    user = new UserDTO(email, fullName, "", roleID, address, birthday, phone, status);
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
        return user;
    }

    public boolean checkDuplicate(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        boolean check = false;
        String sql = "SELECT [email], [fullName], [roleID] "
                + "FROM [dbo].[tblUsers]"
                + " WHERE [email] = ?";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, email);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;

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

        return check;
    }

    public void createUserByGoogle(String email, String name) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        String sql = "INSERT INTO [dbo].[tblUsers]\n"
                + "           ([email]\n"
                + "           ,[fullName]\n"
                + "           ,[password]\n"
                + "           ,[roleID]\n"
                + "           ,[address]\n"
                + "           ,[birthday]\n"
                + "           ,[phone]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "          (?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ptm.setString(1, email);
                ptm.setString(2, name);
                ptm.setString(3, "");
                ptm.setInt(4, 2);
                ptm.setString(5, "");
                ptm.setString(6, "");
                ptm.setString(7, "");
                ptm.setInt(8, 1);
                ptm.executeUpdate();
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

    }

    public boolean createUser(UserEmail user) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        String sql = "INSERT INTO [dbo].[tblUsers]\n"
                + "           ([email]\n"
                + "           ,[fullName]\n"
                + "           ,[password]\n"
                + "           ,[roleID]\n"
                + "           ,[address]\n"
                + "           ,[birthday]\n"
                + "           ,[phone]\n"
                + "           ,[status])\n"
                + "     VALUES\n"
                + "          (?,?,?,?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, user.getEmail());
                ptm.setString(2, user.getFullName());
                ptm.setString(3, user.getPassword());
                ptm.setInt(4, user.getRoleID());
                ptm.setString(5, user.getAddress());
                ptm.setString(6, user.getBirthday());
                ptm.setString(7, user.getPhone());
                ptm.setInt(8, user.getStatus());
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

    public UserDTO getUserByEmail(String email) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT [email]\n"
                    + "      ,[fullName]\n"
                    + "      ,[password]\n"
                    + "      ,[roleID]\n"
                    + "      ,[address]\n"
                    + "      ,[birthday]\n"
                    + "      ,[phone]\n"
                    + "      ,[status]\n"
                    + "  FROM [dbo].[tblUsers] WHERE [email] = ? And [status] = 1";
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(sql);
                ptm.setString(1, email);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    int roleID = rs.getInt("roleID");
                    String address = rs.getString("address");
                    String birthday = rs.getString("birthday");
                    String phone = rs.getString("phone");
                    int status = rs.getInt("status");
                    user = new UserDTO(email, fullName, "", roleID, address, birthday, phone, status);
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
        return user;
    }

   

}
