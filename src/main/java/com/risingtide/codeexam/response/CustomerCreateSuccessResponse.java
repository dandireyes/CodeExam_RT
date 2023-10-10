package com.risingtide.codeexam.response;

public class CustomerCreateSuccessResponse extends AbstractResponse {

    int customerNumber;

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }
}
