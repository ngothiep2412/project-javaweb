package error;

/**
 *
 * @author Thiep Ngo
 */
public class UserError {

    private String emailError;
    private String fullNameError;
    private int roleIDError;
    private String passwordError;
    private String confirmError;
    private String phoneError;
    private String addressError;
    private String birthdayError;

    public UserError() {
    }

    public UserError(String emailError, String fullNameError, int roleIDError, String passwordError, String confirmError, String phoneError, String addressError, String birthdayError) {
        this.emailError = emailError;
        this.fullNameError = fullNameError;
        this.roleIDError = roleIDError;
        this.passwordError = passwordError;
        this.confirmError = confirmError;
        this.phoneError = phoneError;
        this.addressError = addressError;
        this.birthdayError = birthdayError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getFullNameError() {
        return fullNameError;
    }

    public void setFullNameError(String fullNameError) {
        this.fullNameError = fullNameError;
    }

    public int getRoleIDError() {
        return roleIDError;
    }

    public void setRoleIDError(int roleIDError) {
        this.roleIDError = roleIDError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getConfirmError() {
        return confirmError;
    }

    public void setConfirmError(String confirmError) {
        this.confirmError = confirmError;
    }

    public String getPhoneError() {
        return phoneError;
    }

    public void setPhoneError(String phoneError) {
        this.phoneError = phoneError;
    }

    public String getAddressError() {
        return addressError;
    }

    public void setAddressError(String addressError) {
        this.addressError = addressError;
    }

    public String getBirthdayError() {
        return birthdayError;
    }

    public void setBirthdayError(String birthdayError) {
        this.birthdayError = birthdayError;
    }

    
    

}
