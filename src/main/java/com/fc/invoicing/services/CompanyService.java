package com.fc.invoicing.services;

import com.fc.invoicing.db.Database;
import com.fc.invoicing.db.JpaCompanyRepository;
import com.fc.invoicing.dto.CompanyDto;
import com.fc.invoicing.dto.mappers.CompanyListMapper;
import com.fc.invoicing.dto.mappers.CompanyMapper;
import com.fc.invoicing.exeptions.handlers.ItemNotFoundExemption;
import com.fc.invoicing.model.Company;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyService implements Database<CompanyDto> {

    private final JpaCompanyRepository jpaCompanyRepository;
    private final CompanyListMapper companyListMapper;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDto add(CompanyDto company) {
        company.setCompanyId(UUID.randomUUID());
        return companyMapper.toDto(jpaCompanyRepository.save(companyMapper.toEntity(company)));
    }

    @Override
    public CompanyDto getById(UUID id) {
        if (jpaCompanyRepository.findById(id).isPresent()) {
            return companyMapper.toDto(jpaCompanyRepository.findById(id).get());
        } else {
            throw new ItemNotFoundExemption("No company with id: " + id);
        }
    }

    @Override
    public List<CompanyDto> getAll() {
        List<Company> companyList = new ArrayList<>();
        jpaCompanyRepository.findAll().forEach(companyList::add);
        return companyList.stream().map(companyMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CompanyDto update(UUID id, CompanyDto updatedCompany) {
        if (jpaCompanyRepository.findById(id).isPresent()) {
            updatedCompany.setCompanyId(id);
            return companyMapper.toDto(jpaCompanyRepository.save(companyMapper.toEntity(updatedCompany)));
        } else {
            throw new ItemNotFoundExemption("No company with id: " + id + " please add new company");
        }
    }

    @Override
    public boolean delete(UUID id) {
        if (jpaCompanyRepository.findById(id).isPresent()) {
            jpaCompanyRepository.deleteById(id);
            return true;
        } else {
            throw new ItemNotFoundExemption("No company with id: " + id);
        }
    }

    @Override
    public void clear() {
        jpaCompanyRepository.deleteAll();
    }
}
