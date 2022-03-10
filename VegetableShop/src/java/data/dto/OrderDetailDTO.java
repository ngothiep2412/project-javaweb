
package data.dto;

/**
 *
 * @author Thiep Ngo
 */
public class OrderDetailDTO {
    private int detailID;
    private int orderID;
    private int productID;
    private double Price;
    private int quantity;

    public OrderDetailDTO() {
    }

    
    public OrderDetailDTO(int detailID, int orderID, int productID, double Price, int quantity) {
        this.detailID = detailID;
        this.orderID = orderID;
        this.productID = productID;
        this.Price = Price;
        this.quantity = quantity;
    }

    public int getDetailID() {
        return detailID;
    }

    public void setDetailID(int detailID) {
        this.detailID = detailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    
}
