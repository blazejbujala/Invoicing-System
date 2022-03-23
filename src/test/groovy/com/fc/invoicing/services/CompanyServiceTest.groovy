package com.fc.invoicing.services

import com.fc.invoicing.db.CompanyRepository
import com.fc.invoicing.dto.CompanyDto
import com.fc.invoicing.dto.mappers.CompanyListMapper
import com.fc.invoicing.dto.mappers.CompanyMapper
import com.fc.invoicing.testHelpers.TestHelpers
import spock.lang.Specification

class CompanyServiceTest extends Specification{

        CompanyRepository companyRepository = Mock()
        CompanyListMapper companyListMapper = Mock()
        CompanyMapper companyMapper = Mock()

        def companyService = new CompanyService(companyRepository, companyListMapper, companyMapper)
        def company = TestHelpers.company(1)
        def company2 = TestHelpers.company(2)
        def company3 = TestHelpers.company(3)
        def updatedCompany = TestHelpers.company(4)
        def updatedCompanyDto = new CompanyDto(updatedCompany.getCompanyId(),updatedCompany.getTaxIdentificationNumber(),
                updatedCompany.getName(), updatedCompany.getAddress(),
                updatedCompany.getPensionInsurance(), updatedCompany.getHealthInsurance())
        def companyDto = new CompanyDto (company.getCompanyId(), company.getTaxIdentificationNumber(), company.getName(),
                company.getAddress(), company.getPensionInsurance(), company.getHealthInsurance())


        def "should add company to database"() {
            setup:
            companyMapper.toEntity(companyDto) >> company
            companyRepository.save(company) >> company
            companyMapper.toDto(company) >> companyDto
            companyRepository.findById(company.getCompanyId()) >> Optional.of(company)

            when:
            def result = companyService.add(companyDto)

            then:
            result.getName() == "1 Company"
        }

        def "should get company from database by Id"() {
            setup:
            companyRepository.findById(company.getCompanyId()) >> Optional.of(company)
            companyMapper.toDto(company) >> companyDto

            when:
            def result = companyService.getById(company.getCompanyId())

            then:
            result != null
            result.getName() == "1 Company"
        }

        def "should get list of all companies from database"() {
            setup:
            companyRepository.findAll() >> [company, company2, company3]

            when:
            def result = companyService.getAll()

            then:
            result.size() == 3
        }

        def "should update company in the database"() {
            setup:
            updatedCompany.setCompanyId(company.getCompanyId())
            companyMapper.toEntity(updatedCompanyDto) >> updatedCompany
            companyRepository.findById(updatedCompany.getCompanyId()) >> Optional.of(updatedCompany)
            companyRepository.save(updatedCompany) >> updatedCompany
            companyMapper.toDto(updatedCompany) >> updatedCompanyDto

            when:
            def result = companyService.update(company.getCompanyId(), updatedCompanyDto)

            then:
            result.getCompanyId() != null
            result.getName() == "4 Company"
        }

        def "should delete company from database"() {
            setup:
            companyRepository.findById(company.getCompanyId()) >> Optional.of(company)
            companyRepository.findAll() >> []

            when:
            def result = companyService.delete(company.getCompanyId())

            then:
            result
            companyService.getAll().size() == 0
        }
    }
