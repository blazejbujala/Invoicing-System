package com.fc.invoicing.dto.mappers;

import com.fc.invoicing.dto.CompanyDto;
import com.fc.invoicing.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto company);

}
