package com.fc.invoicing.db

import com.fc.invoicing.model.Company
import com.fc.invoicing.model.Invoice
import com.fc.invoicing.model.InvoiceEntry
import spock.lang.Specification

import java.time.LocalDate

abstract class DatabaseTest extends Specification {

    abstract Database getDatabaseInstance();

    def issuer = new Company(UUID.randomUUID(),"333-44-55-321", "Forbs", "ul. Jukowska 15/5, 14-666 Wisla",BigDecimal.valueOf(10.00), BigDecimal.valueOf(70.00))
    def issuer2 = new Company(UUID.randomUUID(),"111-11-11-111", "Slup", "ul.Debowa 12, 10-333 Gostyn", BigDecimal.valueOf(10.00), BigDecimal.valueOf(20.15))
    def receiver = new Company(UUID.randomUUID(),"586-10-44-999", "Mare", "ul.Bukowska 14, 11-333 Gostyn", BigDecimal.valueOf(20.00), BigDecimal.valueOf(40.00))
    def receiver2 = new Company(UUID.randomUUID(),"232-23-23-232", "DDD", "ul.Warszawska 1, 34-454 Wroclaw", BigDecimal.valueOf(30.00), BigDecimal.valueOf(20.00))
    def dateOfIssue1 = LocalDate.of(2022, 01, 01)
    def dateOfIssue2 = LocalDate.of(2022, 01, 02)
    def entry1 = new InvoiceEntry("Buty", 100 as BigDecimal, Vat.VAT_23)
    def entry2 = new InvoiceEntry("Skarpetki", 10 as BigDecimal, Vat.VAT_8)
    def entry3 = new InvoiceEntry("Klapki", 50 as BigDecimal, Vat.VAT_23)
    def entries1 = Arrays.asList(entry1, entry2)
    def entries2 = Arrays.asList(entry1, entry3)
    def invoice = new Invoice(UUID.randomUUID(),"001/02/2022", dateOfIssue1, issuer, receiver, entries1)
    def invoice2 = new Invoice(UUID.randomUUID(), "002/02/2022", dateOfIssue2, issuer2, receiver2, entries2)
    Database database;

    def setup() {
        database = getDatabaseInstance()
        database.clear()
    }

    def "should add invoice to database"() {
        when:
        def result = database.add(invoice)

        then:
        database.getAll() != null
    }

    def "should get invoice from database by Id"() {
        setup:
        database.add(invoice)

        when:
        def result = database.getById(invoice.getInvoiceId())

        then:
        result != null
        result.getIssuer().getName() == "Forbs"
    }

    def "should get list of all invoices from database"() {
        setup:
        database.add(invoice)
        database.add(invoice2)

        when:
        def result = database.getAll()

        then:
        result.size() == 2
    }

    def "should update invoice in the database"() {
        setup:
        database.add(invoice)
        invoice2.setInvoiceId(invoice.getInvoiceId())

        when:
        def result = database.update(invoice.getInvoiceId(), invoice2)

        then:
        database.getById(result.getInvoiceId()) != null
        database.getById(result.getInvoiceId()).getIssuer().getName() == "Slup"
    }

    def "should delete invoice from database"() {
        setup:
        database.add(invoice)

        when:
        def result = database.delete(invoice.getInvoiceId())

        then:
        result
        database.getAll().size() == 0
    }

    def "should delete not existing invoice from database"() {
        when:
        def result = database.delete(UUID.randomUUID())

        then:
        !result
        genericRepository.getAll().size() == 0
    }

    def "should remove all invoices form database"() {
        setup:
        def invoice2 = InvoiceFixture.invoice(1)
        def invoice3 = InvoiceFixture.invoice(3)
        genericRepository.save(invoice)
        genericRepository.save(invoice2)
        genericRepository.save(invoice3)

        when:
        def result = genericRepository.clear()

        then:
        genericRepository.getAll().size() == 0
    }
}
