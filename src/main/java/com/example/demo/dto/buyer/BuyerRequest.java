package com.example.demo.dto.buyer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class BuyerRequest {

    @NotBlank(message = "Buyer name is required")
    @Size(max = 150, message = "Buyer name cannot exceed 150 characters")
    private String buyerName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10,15}$",
            message = "Mobile number must contain 10 to 15 digits")
    private String mobile;

    @Pattern(regexp = "^[0-9A-Z]{15}$",
            message = "Invalid GSTIN format",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String gstin;

    @Email(message = "Invalid email address")
    @Size(max = 255)
    private String address;

    @Size(max = 100)
    private String state;

    @Size(max = 10)
    private String stateCode;

    // Getters and Setters

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getEaddress() {
        return address;
    }

    public void setEaddress(String eaddress) {
        this.address = eaddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}