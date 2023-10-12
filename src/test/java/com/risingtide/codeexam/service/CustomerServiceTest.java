package com.risingtide.codeexam.service;

import com.risingtide.codeexam.entity.AccountType;
import com.risingtide.codeexam.entity.Customer;
import com.risingtide.codeexam.repository.CustomerRepo;
import com.risingtide.codeexam.response.AbstractResponse;
import com.risingtide.codeexam.response.CustomerCreateFailResponse;
import com.risingtide.codeexam.response.CustomerCreateSuccessResponse;
import com.risingtide.codeexam.response.CustomerSingleResponseSuccess;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    public CustomerService customerService;


    @BeforeEach
    void setUp()
    {
        customerRepo = Mockito.mock(CustomerRepo.class);
        this.customerService = new CustomerService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCustomerSuccess()
    {
        Customer customer = new Customer();
        customer.setCustomerNumber(1);

        Mockito.when(customerRepo.findById(1)).thenReturn(Optional.of(customer));

        CustomerSingleResponseSuccess response =customerService.getCustomerByNumber(1);

        assertEquals(customer.getCustomerNumber(),response.getCustomerNumber());
    }

    @Test
    public void testGetCustomerFail()
    {
        Mockito.when(customerRepo.findById(1)).thenReturn(Optional.empty());
        CustomerSingleResponseSuccess response =customerService.getCustomerByNumber(1);
        assertNull(response);
    }

    @Test
    public void testCreateCustomerSuccess() {

        Customer paramCustomer = new Customer();
        paramCustomer.setCustomerNumber(1);
        paramCustomer.setCustomerName("Name");
        paramCustomer.setCustomerEmail("name@domain.com");
        paramCustomer.setCustomerMobile("0917");
        paramCustomer.setAddress1("Line");
        paramCustomer.setAddress2("Line2");
        paramCustomer.setAccountType(AccountType.S);

        Customer resultCustomer = new Customer();
        resultCustomer.setCustomerNumber(1);
        resultCustomer.setCustomerName("Name");
        resultCustomer.setCustomerEmail("name@domain.com");
        resultCustomer.setCustomerMobile("0917");
        resultCustomer.setAddress1("Line");
        resultCustomer.setAddress2("Line2");
        resultCustomer.setAccountType(AccountType.S);


        Mockito.when(customerRepo.save(any())).thenReturn(resultCustomer);


        AbstractResponse response = customerService.createCustomer(paramCustomer);

        assertEquals(response.getClass(), CustomerCreateSuccessResponse.class);

    }

    @Test
    public void testCreateCustomerValidationFail() {

        Customer paramCustomer = new Customer();
        paramCustomer.setCustomerNumber(1);
        paramCustomer.setCustomerEmail("name@domain.com");
        paramCustomer.setCustomerMobile("0917");
        paramCustomer.setAddress1("Line");
        paramCustomer.setAddress2("Line2");
        paramCustomer.setAccountType(AccountType.S);

        ConstraintViolationException cve = getConstraintViolationException();

        Mockito.when(customerRepo.save(any())).thenThrow(cve);

        AbstractResponse response = customerService.createCustomer(paramCustomer);

        assertEquals(response.getClass(), CustomerCreateFailResponse.class);

        CustomerCreateFailResponse failResponse = (CustomerCreateFailResponse)response;

        assertEquals(failResponse.getTransactionStatusDescription(), "Customer Name is Required");

    }

    @NotNull
    private static ConstraintViolationException getConstraintViolationException() {
        ConstraintViolation constraintViolation = new ConstraintViolation() {
            @Override
            public String getMessage() {
                return "Customer Name is Required";
            }

            @Override
            public String getMessageTemplate() {
                return null;
            }

            @Override
            public Object getRootBean() {
                return null;
            }

            @Override
            public Class getRootBeanClass() {
                return null;
            }

            @Override
            public Object getLeafBean() {
                return null;
            }

            @Override
            public Object[] getExecutableParameters() {
                return new Object[0];
            }

            @Override
            public Object getExecutableReturnValue() {
                return null;
            }

            @Override
            public Path getPropertyPath() {
                return null;
            }

            @Override
            public Object getInvalidValue() {
                return null;
            }

            @Override
            public ConstraintDescriptor<?> getConstraintDescriptor() {
                return null;
            }

            @Override
            public Object unwrap(Class aClass) {
                return null;
            }
        };

        Set violations = new HashSet<ConstraintViolation>();


        violations.add(constraintViolation);


        return new ConstraintViolationException("CVE", violations );
    }

    @Test
    public void testCreateCustomerExceptionFail() {

        Customer paramCustomer = new Customer();


        Mockito.when(customerRepo.save(any())).thenThrow(new RuntimeException("Any Message"));

        AbstractResponse response = customerService.createCustomer(paramCustomer);

        assertEquals(response.getClass(), CustomerCreateFailResponse.class);

        CustomerCreateFailResponse failResponse = (CustomerCreateFailResponse)response;

        assertEquals(failResponse.getTransactionStatusDescription(), "Any Message");

    }


}
