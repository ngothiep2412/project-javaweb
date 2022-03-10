
package data.dto;

/**
 *
 * @author Thiep Ngo
 */
public class OrderDTO {

    private int orderID;
    private String email;
    private double totalPrice;
    private String note;
    private String orderDate;
    private int shippingID;

    public OrderDTO() {
    }
    
    

    public OrderDTO(int orderID, String email, double totalPrice, String note, String orderDate, int shippingID) {
        this.orderID = orderID;
        this.email = email;
        this.totalPrice = totalPrice;
        this.note = note;
        this.orderDate = orderDate;
        this.shippingID = shippingID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getShippingID() {
        return shippingID;
    }

    public void setShippingID(int shippingID) {
        this.shippingID = shippingID;
    }

    
    
}
