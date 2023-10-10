package com.risingtide.codeexam.service;

import com.risingtide.codeexam.entity.Customer;
import com.risingtide.codeexam.repository.CustomerRepo;
import com.risingtide.codeexam.response.AbstractResponse;
import com.risingtide.codeexam.response.CustomerCreateFailResponse;
import com.risingtide.codeexam.response.CustomerCreateSuccessResponse;
import com.risingtide.codeexam.response.CustomerSingleResponseSuccess;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

public class CustomerService {


    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ModelMapper mapper;

    public CustomerSingleResponseSuccess getCustomerByNumber(int customerNumber) {

        Optional<Customer> customer = customerRepo.findById(customerNumber);
        CustomerSingleResponseSuccess customerSingleResponse = mapper.map(customer, CustomerSingleResponseSuccess.class);
        return customerSingleResponse;

    }

    public AbstractResponse createCustomer(Customer newCustomer) {

        try {
            Customer _customer = customerRepo
                    .save(new Customer(newCustomer.getCustomerName(), newCustomer.getCustomerMobile(), newCustomer.getCustomerEmail(), newCustomer.getAddress1(), newCustomer.getAddress2(), newCustomer.getAccountType()));

            CustomerCreateSuccessResponse customerCreateSuccessResponse = new CustomerCreateSuccessResponse();
            customerCreateSuccessResponse.setCustomerNumber(_customer.getCustomerNumber());

            return customerCreateSuccessResponse;
        } catch (ConstraintViolationException cve) {
            CustomerCreateFailResponse customerCreateFailResponse = new CustomerCreateFailResponse();
            Object[] arrViolations = cve.getConstraintViolations().toArray();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arrViolations.length; i++) {
                ConstraintViolation violation = (ConstraintViolation) arrViolations[i];
                if (i != 0) {
                    sb.append(", ").append(violation.getMessage());
                } else {
                    sb.append(violation.getMessage());
                }

            }
            customerCreateFailResponse.setTransactionStatusDescription(sb.toString());

            return customerCreateFailResponse;

        } catch (Exception e) {
            CustomerCreateFailResponse customerCreateFailResponse = new CustomerCreateFailResponse();
            customerCreateFailResponse.setTransactionStatusDescription(e.getMessage());
            return customerCreateFailResponse;
        }
    }


}
