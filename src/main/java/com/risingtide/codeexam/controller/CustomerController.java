package com.risingtide.codeexam.controller;


import com.risingtide.codeexam.entity.Customer;
import com.risingtide.codeexam.response.*;
import com.risingtide.codeexam.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private Environment env;

    @PostMapping("/api/v1/account")
    public ResponseEntity<AbstractResponse> createCustomer(@RequestBody Customer customer) {
        AbstractResponse result = customerService.createCustomer(customer);

        if (result instanceof CustomerCreateSuccessResponse){
            result.setTransactionStatusCode(HttpStatus.CREATED.value());
            result.setTransactionStatusDescription(env.getProperty("message.add.success"));

        }else {
            result.setTransactionStatusCode(HttpStatus.BAD_REQUEST.value());
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/api/v1/account/{customerNumber}")
    private ResponseEntity<AbstractResponse> getCustomerDetails(@PathVariable("customerNumber") int customerNumber) {
        CustomerSingleResponseSuccess customerSingleResponse = customerService.getCustomerByNumber(customerNumber);
        if(customerSingleResponse!=null){
            customerSingleResponse.setTransactionStatusCode(HttpStatus.FOUND.value());
            customerSingleResponse.setTransactionStatusDescription(env.getProperty("message.find.success"));
            return ResponseEntity.status(HttpStatus.OK).body(customerSingleResponse);
        }else {
            CustomerSingleResponseFail failResponse = new CustomerSingleResponseFail();
            failResponse.setTransactionStatusCode(HttpStatus.UNAUTHORIZED.value());
            failResponse.setTransactionStatusDescription(env.getProperty("message.find.fail"));
            return ResponseEntity.status(HttpStatus.OK).body(failResponse);
        }


    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AbstractResponse> handleNoSuchElementFoundException(
            HttpMessageNotReadableException exception
    ) {
        CustomerCreateFailResponse response = new CustomerCreateFailResponse();
        response.setTransactionStatusCode(HttpStatus.BAD_REQUEST.value());
        response.setTransactionStatusDescription(env.getProperty("message.accounttype.invalid"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
