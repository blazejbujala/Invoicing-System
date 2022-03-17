package com.fc.invoicing.services

import com.fc.invoicing.db.JpaCompanyRepository
import com.fc.invoicing.dto.CompanyDto
import com.fc.invoicing.dto.mappers.CompanyListMapper
import com.fc.invoicing.dto.mappers.CompanyMapper
import com.fc.invoicing.testHelpers.TestHelpers
import spock.lang.Specification

class CompanyServiceTest extends Specification{

        JpaCompanyRepository jpaCompanyRepository = Mock()
        CompanyListMapper companyListMapper = Mock()
        CompanyMapper companyMapper = Mock()

        def companyService = new CompanyService(jpaCompanyRepository, companyListMapper, companyMapper)
        def company = TestHelpers.company(1)
        def company2 = TestHelpers.company(2)
        def company3 = TestHelpers.company(3)
        def updatedCompany = TestHelpers.company(4)
        def updatedCompanyDto = companyMapper.toDto(updatedCompany)
        def companyDto = new CompanyDto (company.getCompanyId(), company.getTaxIdentificationNumber(), company.getName(), company.getAddress(),
                 company.getPensionInsurance(), company.getHealthInsurance())


        def "should add company to database"() {
            setup:
            companyMapper.toEntity(companyDto) >> company
            jpaCompanyRepository.save(company) >> company
            companyMapper.toDto(company) >> companyDto
            jpaCompanyRepository.findById(company.getCompanyId()) >> Optional.of(company)

            when:
            def result = companyService.add(companyDto)

            then:
            result.getName() == "1 Company"
        }

        def "should get company from database by Id"() {
            setup:
            jpaCompanyRepository.findById(company.getCompanyId()) >> Optional.of(company)

            when:
            def result = companyService.getById(company.getCompanyId())

            then:
            result != null
            result.getName() == "1 Company"
        }

        def "should get list of all companies from database"() {
            setup:
            jpaCompanyRepository.findAll() >> [company, company2, company3]

            when:
            def result = companyService.getAll()

            then:
            result.size() == 3
        }

        def "should update company in the database"() {
            setup:
            updatedCompany.setCompanyId(company.getCompanyId())
            companyMapper.toEntity(updatedCompanyDto) >> updatedCompany
            jpaCompanyRepository.findById(updatedCompany.getCompanyId()) >> Optional.of(updatedCompany)
            jpaCompanyRepository.save(updatedCompany) >> updatedCompany
            companyMapper.toDto(updatedCompany) >> updatedCompanyDto

            when:
            def result = companyService.update(company.getCompanyId(), updatedCompanyDto)

            then:
            companyService.getById(result.getCompanyId()) != null
            companyService.getById(result.getCompanyId()).getName() == "Company 4"
        }

        def "should delete company from database"() {
            setup:
            jpaCompanyRepository.findById(company.getCompanyId()) >> Optional.of(company)
            jpaCompanyRepository.findAll() >> []

            when:
            def result = companyService.delete(company.getCompanyId())

            then:
            result
            companyService.getAll().size() == 0
        }
    }
