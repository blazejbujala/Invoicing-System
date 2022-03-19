package com.fc.invoicing.services

import com.fc.invoicing.model.CompanyTaxInformation
import com.fc.invoicing.testHelpers.TestHelpers
import spock.lang.Shared
import spock.lang.Specification

class TaxCalculatorServiceTest extends Specification {

    private InvoiceService invoiceService = Mock()
    private TaxCalculatorService taxCalculatorService = new TaxCalculatorService(invoiceService)

        @Shared
        def invoice = TestHelpers.invoice(1)
        def invoice2 = TestHelpers.invoice(4)
        def invoiceWithGasoline = TestHelpers.invoiceWithGasoline(3)

        def setup() {
            invoice2.getReceiver().setTaxIdentificationNumber(invoice.getIssuer().getTaxIdentificationNumber())
            invoiceWithGasoline.getIssuer().setTaxIdentificationNumber(invoice.getIssuer().getTaxIdentificationNumber())
            invoiceService.getAll() >> [invoice, invoice2, invoiceWithGasoline]
        }

        def "should calculate company incomingVat from two invoices"() {
            when:
            def result = taxCalculatorService.incomingVat(invoice.getIssuer())

            then:
            result == 1472.00
        }

        def "should calculate company outgoingVat"() {
            when:
            def result = taxCalculatorService.outgoingVat(invoice.getReceiver())

            then:
            result == 322.00
        }

        def "should calculate company income from three invoices"() {
            when:
            def result = taxCalculatorService.income(invoice.getIssuer())

            then:
            result == 1800.00
        }

        def "should calculate company costs"() {
            when:
            def result = taxCalculatorService.costs(invoice.getIssuer())

            then:
            result == 1500
        }

        def "should calculate company's earnings from three invoices"() {
            when:
            def result = taxCalculatorService.earnings(invoice.getIssuer())

            then:
            result == 300
        }

        def "should calculate company's vat increase due to personal car use"() {
            when:
            def result = taxCalculatorService.personalUseCarVatIncrease(invoiceWithGasoline.getIssuer())

            then:
            result == 184.00
        }

        def "should calculate company's vat to pay"() {
            when:
            def result = taxCalculatorService.vatToPay(invoice2.getIssuer())

            then:
            result == 621.00
        }

        def "should calculate base of tax to pay by company"() {
            when:
            def result = taxCalculatorService.calculateTaxBase(invoice.getIssuer())

            then:
            result == 289.00
        }

        def "should calculate rounded base of tax to pay by company"() {
            when:
            def result = taxCalculatorService.roundTaxBase(invoice.getIssuer())

            then:
            result == 289.00
        }

        def "should calculate income tax to be paid by company"() {
            when:
            def result = taxCalculatorService.incomeTax(invoice.getIssuer())

            then:
            result == 54.91
        }

        def "should calculate company's nine percent of health insurance"() {
            when:
            def result = taxCalculatorService.healthInsuranceNinePercent(invoice.getIssuer())

            then:
            result == 6.39
        }

        def "should calculate company's seven point seventy five percent of health insurance"() {
            when:
            def result = taxCalculatorService.healthInsuranceSevenPointSeventyFivePercent(invoice.getIssuer())

            then:
            result == 5.50
        }

        def "should calculate company's income tax reduced by health insurance "() {
            when:
            def result = taxCalculatorService.incomeTaxReducedByHealthInsurance(invoice.getIssuer())

            then:
            result == 49.41
        }

        def "should calculate company's final income tax to be paid"() {
            when:
            def result = taxCalculatorService.finalIncomeTaxValue(invoice.getIssuer())

            then:
            result == 49.00
        }

        def "should generate full Company Tax Information"() {
            def expected = CompanyTaxInformation.builder()
                    .incomingVat(1472.00)
                    .outgoingVat(1771.00)
                    .vatToPay(-299.00)
                    .income(1800.00)
                    .costs(1500.00)
                    .earnings(300.00)
                    .pensionInsurance(11.00)
                    .taxBase(289.00)
                    .taxBaseRounded(289.00)
                    .incomeTax(54.91)
                    .healthInsuranceNinePercent(6.39)
                    .healthInsuranceSevenAndSeventyFivePercent(5.50)
                    .incomeTaxReducedByHealthInsurance(49.41)
                    .finalIncomeTaxValue(49.00)
                    .build()

            when:
            def result = taxCalculatorService.companyTaxInformation(invoice.getIssuer())

            then:
            expected == result
        }
    }