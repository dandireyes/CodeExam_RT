package com.risingtide.codeexam.configuration;

import com.risingtide.codeexam.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfiguration {

    @Bean
    public CustomerService customerBean(){
        return new CustomerService();
    }

    @Bean
    public ModelMapper modelMapperBean(){
        return new ModelMapper();
    }
}
