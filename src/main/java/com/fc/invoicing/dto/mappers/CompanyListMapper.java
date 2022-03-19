package com.fc.invoicing.dto.mappers;

import com.fc.invoicing.dto.CompanyListDto;
import com.fc.invoicing.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyListMapper {

    CompanyListDto companyListToDto(Company company);
}
