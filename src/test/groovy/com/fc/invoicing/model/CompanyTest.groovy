package com.fc.invoicing.model

import spock.lang.Specification

class CompanyTest extends Specification {

    def "should be able to create instance of the class"() {

        given:
        def company = new Company("586-10-44-999", "Mare", "ul.Bukowska 14, 11-333 Gostyn",BigDecimal.valueOf(70.00),BigDecimal.valueOf(30.00))

        when:
        def expect = company

        then:
        expect != null

    }
}
