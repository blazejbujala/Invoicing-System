package com.fc.invoicing.services;

import com.fc.invoicing.db.CompanyRepository;
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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyListMapper companyListMapper;
    private final CompanyMapper companyMapper;

    public CompanyDto add(CompanyDto company) {
        company.setCompanyId(UUID.randomUUID());
        return companyMapper.toDto(companyRepository.save(companyMapper.toEntity(company)));
    }

    public CompanyDto getById(UUID id) {
        return companyMapper.toDto(companyRepository.findById(id).orElseThrow(() -> new ItemNotFoundExemption("No company with id: " + id)));
    }

    public List<CompanyDto> getAll() {
        List<Company> companyList = new ArrayList<>();
        companyRepository.findAll().forEach(companyList::add);
        return companyList.stream().map(companyMapper::toDto).collect(Collectors.toList());
    }

    public CompanyDto update(UUID id, CompanyDto updatedCompany) {
        if (companyRepository.findById(id).isPresent()) {
            updatedCompany.setCompanyId(id);
            return companyMapper.toDto(companyRepository.save(companyMapper.toEntity(updatedCompany)));
        } else {
            throw new ItemNotFoundExemption("No company with id: " + id + " please add new company");
        }
    }

    public boolean delete(UUID id) {
        try {
            companyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundExemption("No company with id: " + id);
        }
        return true;
    }

    public void clear() {
        companyRepository.deleteAll();
    }
}
