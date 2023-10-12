package com.risingtide.codeexam.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerNumber")
    private int customerNumber;

    @Column(name = "customerName", length = 50, nullable=false)
    @NotBlank(message = "Customer Name is a required Field")
    private String customerName;

    @Column(name = "customerMobile", length = 20, nullable=false )
    @NotBlank(message = "Mobile Number is a required Field")
    private String customerMobile;

    @Column(name = "customerEmail", length = 50, nullable=false)
    @NotBlank(message = "Email Address is a required Field")
    private String customerEmail;

    @Column(name = "address1", length = 100, nullable=false)
    @NotBlank(message = "Address is a required Field")
    private String address1;

    @Column(name = "address2", length = 100)
    private String address2;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Account Type is a required Field")
    @Column(name="accountType", nullable=false)
    private AccountType accountType;

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Customer(){

    }

    public Customer(String customerName, String customerMobile, String customerEmail, String address1, String address2, AccountType accountType) {
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.customerEmail = customerEmail;
        this.address1 = address1;
        this.address2 = address2;
        this.accountType = accountType;
    }
}


