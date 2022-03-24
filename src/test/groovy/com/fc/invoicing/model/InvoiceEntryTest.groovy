package com.fc.invoicing.model

import com.fc.invoicing.testHelpers.TestHelpers
import spock.lang.Specification

class InvoiceEntryTest extends Specification {

    def "should make InvoiceEntry Object" (){
        setup:
        def invoiceEntry = TestHelpers.product(1)

        when:
        def result = invoiceEntry

        then:
        result.getDescription() == "Product 1"
    }

    def "should calculate total price of invoiceEntry" (){
        setup:
        def invoiceEntry = TestHelpers.product(2)

        when:
        def result = invoiceEntry

        then:
        result.getTotalPrice() == 400
    }

    def "should calculate vat value"(){

        given:
        def invoiceEntry = new InvoiceEntry("Maslo", 6 as BigDecimal, Vat.VAT_8)

        when:
        def result = invoiceEntry

        then:
        result.getVatValue() == 0.48
    }
}
