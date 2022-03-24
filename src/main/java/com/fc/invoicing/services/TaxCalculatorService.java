package com.fc.invoicing.services;

import com.fc.invoicing.model.Company;
import com.fc.invoicing.model.CompanyTaxInformation;
import com.fc.invoicing.model.Invoice;
import com.fc.invoicing.model.InvoiceEntry;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaxCalculatorService {

    private final InvoiceService invoiceService;

    private BigDecimal visitStream(Predicate<Invoice> invoice, Function<InvoiceEntry, BigDecimal> invoiceEntryToAmount) {
        return invoiceService.getAll()
            .stream()
            .filter(invoice)
            .flatMap(inv -> inv.getEntries().stream())
            .map(invoiceEntryToAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal incomingVat(Company company) {
        return visitStream(invoice -> invoice.getIssuer().getTaxIdentificationNumber().equals(company.getTaxIdentificationNumber()),
            InvoiceEntry::getVatValue).setScale(2);
    }

    public BigDecimal outgoingVat(Company company) {
        return visitStream(invoice -> invoice.getReceiver().getTaxIdentificationNumber().equals(company.getTaxIdentificationNumber()),
            InvoiceEntry::getVatValue).setScale(2);
    }

    public BigDecimal income(Company company) {
        return visitStream(invoice -> invoice.getIssuer().getTaxIdentificationNumber().equals(company.getTaxIdentificationNumber()),
            InvoiceEntry::getPrice).setScale(2);
    }

    public BigDecimal costs(Company company) {
        return visitStream(invoice -> invoice.getReceiver().getTaxIdentificationNumber().equals(company.getTaxIdentificationNumber()),
            InvoiceEntry::getPrice).setScale(2);
    }

    public BigDecimal personalUseCarVat(Company company) {
        return invoiceService.getAll()
            .stream()
            .filter(inv -> inv.getIssuer().equals(company))
            .flatMap(inv -> inv.getEntries().stream())
            .filter(InvoiceEntry::isPersonalUseCar)
            .map(InvoiceEntry::getVatValue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal personalUseCarVatIncrease(Company company) {
        return personalUseCarVat(company).divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal earnings(Company company) {
        return income(company).subtract(costs(company));
    }

    public BigDecimal vatToPay(Company company) {
        return incomingVat(company).subtract(outgoingVat(company)).add(personalUseCarVatIncrease(company)).setScale(2);
    }

    public BigDecimal calculateTaxBase(Company company) {
        return (earnings(company).subtract(company.getPensionInsurance())).setScale(2);
    }

    public BigDecimal roundTaxBase(Company company) {
        return calculateTaxBase(company).setScale(0, RoundingMode.HALF_UP).setScale(2);
    }

    public BigDecimal incomeTax(Company company) {
        return roundTaxBase(company).multiply(BigDecimal.valueOf(0.19)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal healthInsuranceNinePercent(Company company) {
        return company.getHealthInsurance().multiply(BigDecimal.valueOf(0.09)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal healthInsuranceSevenPointSeventyFivePercent(Company company) {
        return company.getHealthInsurance().multiply(BigDecimal.valueOf(0.0775)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal incomeTaxReducedByHealthInsurance(Company company) {
        return incomeTax(company).subtract(healthInsuranceSevenPointSeventyFivePercent(company)).setScale(2);
    }

    public BigDecimal finalIncomeTaxValue(Company company) {
        return incomeTaxReducedByHealthInsurance(company).setScale(0, RoundingMode.HALF_UP).setScale(2);
    }

    public CompanyTaxInformation companyTaxInformation(Company company) {
        return CompanyTaxInformation.builder()
            .incomingVat(incomingVat(company))
            .outgoingVat(outgoingVat(company))
            .vatToPay(vatToPay(company))
            .income(income(company))
            .costs(costs(company))
            .earnings(earnings(company))
            .pensionInsurance(company.getPensionInsurance().setScale(2))
            .taxBase(calculateTaxBase(company))
            .taxBaseRounded(roundTaxBase(company))
            .incomeTax(incomeTax(company))
            .healthInsuranceNinePercent(healthInsuranceNinePercent(company))
            .healthInsuranceSevenAndSeventyFivePercent(healthInsuranceSevenPointSeventyFivePercent(company))
            .incomeTaxReducedByHealthInsurance(incomeTaxReducedByHealthInsurance(company))
            .finalIncomeTaxValue(finalIncomeTaxValue(company))
            .build();
    }
}
