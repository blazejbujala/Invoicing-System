package com.fc.invoicing.services

import com.fc.invoicing.db.InvoiceRepository
import com.fc.invoicing.dto.mappers.InvoiceListMapper
import com.fc.invoicing.model.Company
import com.fc.invoicing.model.Invoice
import com.fc.invoicing.model.InvoiceEntry
import com.fc.invoicing.model.Vat
import spock.lang.Specification

import java.time.LocalDate

class InvoiceServiceTest extends Specification {

    InvoiceRepository invoiceRepository = Mock()
    InvoiceListMapper invoiceListMapper = Mock()

    def invoiceService = new InvoiceService(invoiceRepository, invoiceListMapper)
    def issuer = new Company("333-44-55-321", "Forbs", "ul. Jukowska 15/5, 14-666 Wisla",BigDecimal.valueOf(10.00), BigDecimal.valueOf(70.00))
    def issuer2 = new Company("111-11-11-111", "Slup", "ul.Debowa 12, 10-333 Gostyn", BigDecimal.valueOf(10.00), BigDecimal.valueOf(20.15))
    def receiver = new Company("586-10-44-999", "Mare", "ul.Bukowska 14, 11-333 Gostyn", BigDecimal.valueOf(20.00), BigDecimal.valueOf(40.00))
    def receiver2 = new Company("232-23-23-232", "DDD", "ul.Warszawska 1, 34-454 Wroclaw", BigDecimal.valueOf(30.00), BigDecimal.valueOf(20.00))
    def dateOfIssue1 = LocalDate.of(2022, 01, 01)
    def dateOfIssue2 = LocalDate.of(2022, 01, 02)
    def entry1 = new InvoiceEntry("Buty", 100 as BigDecimal, Vat.VAT_23)
    def entry2 = new InvoiceEntry("Skarpetki", 10 as BigDecimal, Vat.VAT_8)
    def entry3 = new InvoiceEntry("Klapki", 50 as BigDecimal, Vat.VAT_23)
    def entries1 = Arrays.asList(entry1, entry2)
    def entries2 = Arrays.asList(entry1, entry3)
    def invoice = new Invoice(UUID.randomUUID(),"12/12/12", dateOfIssue1,issuer,receiver, entries1)
    def invoice2 = new Invoice(UUID.randomUUID(), dateOfIssue2, issuer2, receiver2, entries2)


    def "should add new invoice to the repository"() {
        given:
        invoiceRepository.save(invoice) >> invoice
        invoiceRepository.findById(invoice.getInvoiceId()) >> Optional.of(invoice)

        when:
        def result = invoiceService.add(invoice)

        then:
        result != null
        result.getReceiver().getName() == "Mare"
    }

    def "should get invoice from repository by id"() {
        given:
        invoiceRepository.save(invoice) >> invoice
        invoiceRepository.findById(invoice.getInvoiceId()) >> Optional.of(invoice)

        when:
        def result = invoiceService.getById(invoice.getInvoiceId())

        then:
        result.getIssuer().getName() == "Forbs"
    }

    def "should get list of invoices from repository"() {
        given:
        invoiceRepository.findAll() >> [invoice, invoice2]

        when:
        def result = invoiceService.getAll()

        then:
        result.size() == 2
    }

    def "should get short list of invoices from repository"() {
        given:
        invoiceRepository.findAll() >> [invoice, invoice2]

        when:
        def result = invoiceService.getList()

        then:
        result.size() == 2
    }

    def "should update invoice in the repository"() {
        given:
        invoiceRepository.save(invoice2) >> invoice2
        invoiceRepository.findById(invoice.getInvoiceId()) >> Optional.of(invoice2)
        invoice2.setInvoiceId(invoice.getInvoiceId())

        when:
        def result = invoiceService.update(invoice.getInvoiceId(), invoice2)

        then:
        result.getReceiver().getName() == "DDD"
    }

    def "should delete invoice form the repository"() {
        given:
        invoiceRepository.findById(invoice.getInvoiceId()) >> Optional.of(invoice)
        invoiceRepository.deleteById(invoice.getInvoiceId())
        invoiceRepository.findAll() >> []

        when:
        def result = invoiceService.delete(invoice.getInvoiceId())

        then:
        invoiceService.getAll().size() == 0
    }
}
