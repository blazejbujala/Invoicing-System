package com.fc.invoicing.controllers

import com.fc.invoicing.model.Invoice
import com.fc.invoicing.services.InvoiceService
import com.fc.invoicing.services.JsonService
import com.fc.invoicing.testHelpers.TestHelpers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonService<Invoice> jsonService

    @Autowired
    private JsonService<Invoice[]> jsonListService

    @Autowired
    private InvoiceService invoiceRepository

    @Shared
    def invoice1 = TestHelpers.invoice(1)
    def invoice2 = TestHelpers.invoice(2)
    def invoice3 = TestHelpers.invoice(3)

    def setup() { invoiceRepository.clear() }
    def cleanup() { invoiceRepository.clear() }

    def "should get empty list"() {
        given:
        def expected = "[]"

        when:
        def result = mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        then:
        expected == result
    }

    def "should post invoice"() {
        given:
        def invoiceAsJson = jsonService.toJson(invoice1)

        when:
        def result = mockMvc.perform(post("/invoices").content(invoiceAsJson).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def receivedInvoice = jsonService.toObject(result, Invoice.class)
        def id = receivedInvoice.getInvoiceId()
        invoice1.setInvoiceId(id)

        then:
        receivedInvoice.getInvoiceId() == invoice1.getInvoiceId()
        receivedInvoice.getIssuer().getName() == invoice1.getIssuer().getName()
    }

    def "should get second invoice by Id"() {
        given:
        def invoiceAsJson1 = jsonService.toJson(invoice1)
        def invoiceAsJson2 = jsonService.toJson(invoice2)
        def invoiceAsJson3 = jsonService.toJson(invoice3)

        when:
        mockMvc.perform(post("/invoices").content(invoiceAsJson1).contentType(MediaType.APPLICATION_JSON_VALUE))
        def postedInvoice2 = mockMvc.perform(post("/invoices").content(invoiceAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        mockMvc.perform(post("/invoices").content(invoiceAsJson3).contentType(MediaType.APPLICATION_JSON_VALUE))
        def addedInvoice2 = jsonService.toObject(postedInvoice2, Invoice.class)
        def id = addedInvoice2.getInvoiceId()
        invoice2.setInvoiceId(id)
        def result = mockMvc.perform(get("/invoices/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def receivedInvoice = jsonService.toObject(result, Invoice.class)

        then:
        receivedInvoice.getIssuer().getName() == invoice2.getIssuer().getName()
        receivedInvoice.getInvoiceId() == invoice2.getInvoiceId()
    }

    def "should get all invoices as a list"() {
        given:
        def invoiceAsJson1 = jsonService.toJson(invoice1)
        def invoiceAsJson2 = jsonService.toJson(invoice2)
        def invoiceAsJson3 = jsonService.toJson(invoice3)

        when:
        mockMvc.perform(post("/invoices").content(invoiceAsJson1).contentType(MediaType.APPLICATION_JSON_VALUE))
        mockMvc.perform(post("/invoices").content(invoiceAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE))
        mockMvc.perform(post("/invoices").content(invoiceAsJson3).contentType(MediaType.APPLICATION_JSON_VALUE))
        def result = mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        def invoices = jsonListService.toObject(result, Invoice[].class)

        then:
        invoices.size() == 3
    }

    def "should return short list of invoices"() {
        when:
        def response = mockMvc.perform(get("/invoices/list"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        then:
        response != null
    }

    def "should delete second invoice from repository"() {
        given:
        def invoiceAsJson1 = jsonService.toJson(invoice1)
        def invoiceAsJson2 = jsonService.toJson(invoice2)
        def invoiceAsJson3 = jsonService.toJson(invoice3)

        when:
        mockMvc.perform(post("/invoices").content(invoiceAsJson1).contentType(MediaType.APPLICATION_JSON_VALUE))
        def secondInvoice = mockMvc.perform(post("/invoices").content(invoiceAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def invoiceToBeDelated = jsonService.toObject(secondInvoice, Invoice.class)
        def id = invoiceToBeDelated.getInvoiceId()
        invoice2.setInvoiceId(id)
        mockMvc.perform(post("/invoices").content(invoiceAsJson3).contentType(MediaType.APPLICATION_JSON_VALUE))
        mockMvc.perform(delete("/invoices/" + id))
        def response = mockMvc.perform(get("/invoices"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def invoices = jsonListService.toObject(response, Invoice[])

        then:
        invoices.size() == 2
        invoiceToBeDelated.getReceiver().getName() == invoice2.getReceiver().getName()
    }

    def "should delete not existing invoice"() {
        given:
        def invoiceAsJson1 = jsonService.toJson(invoice1)

        when:
        def postResponse = mockMvc.perform(
                post("/invoices").content(invoiceAsJson1).contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString()

        def id = jsonService.toObject(postResponse, Invoice.class).getInvoiceId()
        invoice1.setInvoiceId(id)
        UUID notInRepositoryId = UUID.randomUUID()
        def deleteResponse = mockMvc.perform(
                delete("/invoices/" + notInRepositoryId))
                .andExpect(status().isNotFound())
                .andReturn()
                .response
                .contentAsString

        def response = mockMvc.perform(
                get("/invoices"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString

        def invoices = jsonListService.toObject(response, Invoice[].class)

        then:
        deleteResponse == "No invoice with id: " + notInRepositoryId
        invoices[0].getReceiver().getName() == invoice1.getReceiver().getName()
    }

    def "should update invoice"() {
        given:
        def invoiceAsJson1 = jsonService.toJson(invoice1)
        def invoiceAsJson2 = jsonService.toJson(invoice2)

        when:
        def postedInvoice = mockMvc.perform(post("/invoices")
                .content(invoiceAsJson1).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def id = jsonService.toObject(postedInvoice, Invoice.class).getInvoiceId()
        def receivedBody = mockMvc.perform(patch("/invoices/" + id)
                .content(invoiceAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
        def updatedInvoice = jsonService.toObject(receivedBody, Invoice.class)
        invoice2.setInvoiceId(id)

        then:
        invoice2.getReceiver().getName() == updatedInvoice.getReceiver().getName()
    }

    def "should update not existing invoice"() {
        given:
        def invoiceAsJson1 = jsonService.toJson(invoice1)
        def invoiceAsJson2 = jsonService.toJson(invoice2)

        when:
        def postResponse = mockMvc.perform(
                post("/invoices").content(invoiceAsJson1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        UUID updatedId = UUID.randomUUID()
        def patchBody = mockMvc.perform(patch("/invoices/" + updatedId)
                .content(invoiceAsJson2).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString()

        then:
        patchBody == "No invoice with id: " + updatedId + " please add new invoice"
    }
}

