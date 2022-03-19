package com.fc.invoicing.model

import com.fc.invoicing.testHelpers.TestHelpers
import spock.lang.Specification

class InvoiceTest extends Specification {

    def "should make Invoice object"() {
        setup:
        def invoice = TestHelpers.invoice(1)

        when:
        def result = invoice

        then:
        result != null
    }

    def "should make Invoice with gasoline object"() {
        setup:
        def invoice = TestHelpers.invoiceWithGasoline(1)

        when:
        def result = invoice

        then:
        result.getEntries().get(1).getCarRegNo() == "GO 12322"
    }
}
