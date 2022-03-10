package error;

/**
 *
 * @author Thiep Ngo
 */
public class CartError {

    private String quantityError;
    private int productIDError;

    public CartError(int productIDError) {
        this.productIDError = productIDError;
    }

    

    public CartError(String quantityError, int productIDError) {
        this.quantityError = quantityError;
        this.productIDError = productIDError;
    }

    public String getQuantityError() {
        return quantityError;
    }

    public void setQuantityError(String quantityError) {
        this.quantityError = quantityError;
    }

    public int getProductIDError() {
        return productIDError;
    }

    public void setProductIDError(int productIDError) {
        this.productIDError = productIDError;
    }

    

}
