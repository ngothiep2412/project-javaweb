package sessionmodel;

import data.dto.ProductDTO;

/**
 *
 * @author Thiep Ngo
 */
public class Cart {
    private ProductDTO product;
    private int quantity;

    public Cart() {
    }

    public Cart(ProductDTO product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    
    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  
}
