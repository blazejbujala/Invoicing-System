package com.fc.invoicing.model;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyTaxInformation {

    private BigDecimal incomingVat;
    private BigDecimal outgoingVat;
    private BigDecimal vatToPay;
    private BigDecimal income;
    private BigDecimal costs;
    private BigDecimal earnings;
    private BigDecimal pensionInsurance;
    private BigDecimal taxBase;
    private BigDecimal taxBaseRounded;
    private BigDecimal incomeTax;
    private BigDecimal healthInsuranceNinePercent;
    private BigDecimal healthInsuranceSevenAndSeventyFivePercent;
    private BigDecimal incomeTaxReducedByHealthInsurance;
    private BigDecimal finalIncomeTaxValue;
}
