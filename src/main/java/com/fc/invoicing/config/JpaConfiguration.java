package com.fc.invoicing.config;

import com.fc.invoicing.dto.CompanyDto;
import com.fc.invoicing.model.Company;
import com.fc.invoicing.model.Invoice;
import com.fc.invoicing.services.JsonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "pl.fc.invoicing.db.mode", havingValue = "jpa")
public class JpaConfiguration {

    @Bean
    public JsonService<Invoice> jsonService() {
        return new JsonService<>();
    }

    @Bean
    public JsonService<Invoice[]> jsonServiceList() {
        return new JsonService<>();
    }

    @Bean
    public JsonService<Company> jsonServiceCompany() {
        return new JsonService<>();
    }

    @Bean
    public JsonService<CompanyDto> jsonServiceCompanyDto() {
        return new JsonService<>();
    }

    @Bean
    public JsonService<CompanyDto[]> jsonServiceCompanyListDto() {
        return new JsonService<>();
    }
}
