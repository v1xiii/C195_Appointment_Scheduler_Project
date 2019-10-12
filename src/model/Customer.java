package model;

public class Customer {
    private Integer customerId;
    private String customerName;
    private String address;
    private String address2;
    private String city;
    private String postalCode;
    private String country;
    private String phone;
    private Boolean active;
    private Integer addressId;

    public Customer() {
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress1(String address) {
        this.address = address;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress1() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getActive() {
        return active;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public static String hasValidFields(String customerName, String address1, String city, String country, String postalCode, String phone){
        String error = "";

        if (customerName.length() == 0) {
            error = error + "Name is required. \n";
        }
        if (address1.length() == 0) {
            error = error + "Address 1 is required. \n";
        }
        if (city.length() == 0) {
            error = error + "City is required. \n";
        }
        if (country.length() == 0) {
            error = error + "Country is required. \n";
        }
        if (postalCode.length() == 0) {
            error = error + "Postal code is required. \n";
        }
        if (phone.length() == 0) {
            error = error + "Phone number is required. \n";
        }
        
        return error;
    }
}