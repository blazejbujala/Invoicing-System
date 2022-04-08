package com.fc.invoicing.controllers


import com.fc.invoicing.dto.CompanyDto
import com.fc.invoicing.services.CompanyService
import com.fc.invoicing.services.JsonService
import com.fc.invoicing.testHelpers.TestHelpers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonService<CompanyDto> jsonService

    @Autowired
    private JsonService<CompanyDto[]> jsonListService

    @Autowired
    private CompanyService companyRepository

    def setup() { companyRepository.clear() }
    def cleanup() { companyRepository.clear() }

    @Shared
    def company = TestHelpers.company(1)
    def company2 = TestHelpers.company(2)
    def company3 = TestHelpers.company(3)
    def companyDto = new CompanyDto (company.getCompanyId(), company.getTaxIdentificationNumber(), company.getName(),
            company.getAddress(), company.getPensionInsurance(), company.getHealthInsurance())
    def company2Dto = new CompanyDto (company2.getCompanyId(), company2.getTaxIdentificationNumber(), company2.getName(),
            company2.getAddress(), company2.getPensionInsurance(), company2.getHealthInsurance())
    def company3Dto = new CompanyDto (company3.getCompanyId(), company3.getTaxIdentificationNumber(), company3.getName(),
            company3.getAddress(), company3.getPensionInsurance(), company3.getHealthInsurance())

    def "should get empty list"() {
        given:
        def expected = "[]"

        when:
        def result = mockMvc.perform(get("/companies").with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        then:
        expected == result
    }

    def "should post company"() {
        given:
        def companyDtoAsJson = jsonService.toJson(companyDto)

        when:
        def result = mockMvc.perform(post("/companies")
                .content(companyDtoAsJson).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def receivedCompanyDto = jsonService.toObject(result, CompanyDto.class)
        def id = receivedCompanyDto.getCompanyId()
        company.setCompanyId(id)

        then:
        receivedCompanyDto.getCompanyId() == company.getCompanyId()
        receivedCompanyDto.getName()== company.getName()
    }

    def "should get second company by Id"() {
        given:
        def companyDtoAsJson1 = jsonService.toJson(companyDto)
        def companyDtoAsJson2 = jsonService.toJson(company2Dto)
        def companyDtoAsJson3 = jsonService.toJson(company3Dto)

        when:
        mockMvc.perform(post("/companies").content(companyDtoAsJson1).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
        def postedCompanyDto = mockMvc.perform(post("/companies").content(companyDtoAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        mockMvc.perform(post("/companies").content(companyDtoAsJson3).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
        def addedCompany2 = jsonService.toObject(postedCompanyDto, CompanyDto.class)
        def id = addedCompany2.getCompanyId()
        company2Dto.setCompanyId(id)
        def result = mockMvc.perform(get("/companies/" + id).with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def receivedCompanyDto = jsonService.toObject(result, CompanyDto.class)

        then:
        receivedCompanyDto.getName() == company2Dto.getName()
        receivedCompanyDto.getCompanyId() == company2Dto.getCompanyId()
    }

    def "should get all companies as a list"() {
        given:
        def companyDtoAsJson1 = jsonService.toJson(companyDto)
        def companyDtoAsJson2 = jsonService.toJson(company2Dto)
        def companyDtoAsJson3 = jsonService.toJson(company3Dto)

        when:
        mockMvc.perform(post("/companies").content(companyDtoAsJson1).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
        mockMvc.perform(post("/companies").content(companyDtoAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
        mockMvc.perform(post("/companies").content(companyDtoAsJson3).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
        def result = mockMvc.perform(get("/companies").with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        def companyListDto = jsonListService.toObject(result, CompanyDto[].class)

        then:
        companyListDto.size() == 3
    }

    def "should delete second company from database"() {
        given:
        def companyDtoAsJson1 = jsonService.toJson(companyDto)
        def companyDtoAsJson2 = jsonService.toJson(company2Dto)
        def companyDtoAsJson3 = jsonService.toJson(company3Dto)

        when:
        mockMvc.perform(post("/companies").content(companyDtoAsJson1).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
        def secondCompany = mockMvc.perform(post("/companies").content(companyDtoAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def companyToBeDeleted = jsonService.toObject(secondCompany, CompanyDto.class)
        def id = companyToBeDeleted.getCompanyId()
        company2Dto.setCompanyId(id)
        mockMvc.perform(post("/companies").content(companyDtoAsJson3).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
        mockMvc.perform(delete("/companies/" + id).with(csrf()))
        def response = mockMvc.perform(get("/companies").with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def companyListDto = jsonListService.toObject(response, CompanyDto[])

        then:
        companyListDto.size() == 2
        companyToBeDeleted.getName() == company2Dto.getName()
    }

    def "should delete not existing company"() {
        given:
        def companyDtoAsJson1 = jsonService.toJson(companyDto)

        when:
        def postResponse = mockMvc.perform(
                post("/companies").content(companyDtoAsJson1).contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andReturn()
                .getResponse()
                .getContentAsString()

        def id = jsonService.toObject(postResponse, CompanyDto.class).getCompanyId()
        companyDto.setCompanyId(id)
        UUID notInRepositoryId = UUID.randomUUID()
        def deleteResponse = mockMvc.perform(
                delete("/companies/" + notInRepositoryId).with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn()
                .response
                .contentAsString

        def response = mockMvc.perform(
                get("/companies").with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        def companyListDto = jsonListService.toObject(response, CompanyDto[].class)

        then:
        deleteResponse == "No company with id: " + notInRepositoryId
        companyListDto[0].getName() == companyDto.getName()
    }

    def "should update company"() {
        given:
        def companyDtoAsJson1 = jsonService.toJson(companyDto)
        def companyDtoAsJson2 = jsonService.toJson(company2Dto)

        when:
        def postedCompanyDto = mockMvc.perform(post("/companies")
                .content(companyDtoAsJson1).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def id = jsonService.toObject(postedCompanyDto, CompanyDto.class).getCompanyId()
        def receivedBody = mockMvc.perform(patch("/companies/" + id)
                .content(companyDtoAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def updatedCompanyDto = jsonService.toObject(receivedBody, CompanyDto.class)
        company2Dto.setCompanyId(id)

        then:
        company2Dto.getTaxIdentificationNumber() == updatedCompanyDto.getTaxIdentificationNumber()
    }

    def "should update not existing invoice"() {
        given:
        def companyDtoAsJson1 = jsonService.toJson(companyDto)
        def companyDtoAsJson2 = jsonService.toJson(company2Dto)

        when:
        def postResponse = mockMvc.perform(
                post("/companies").content(companyDtoAsJson1).contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
        UUID updatedId = UUID.randomUUID()
        def respond = mockMvc.perform(patch("/companies/" + updatedId)
                .content(companyDtoAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        respond == "No company with id: " + updatedId + " please add new company"
    }
}