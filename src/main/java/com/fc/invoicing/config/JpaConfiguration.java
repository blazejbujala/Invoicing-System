package com.fc.invoicing.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
@ConditionalOnProperty(name = "pl.fc.invoicing.db.mode", havingValue = "jpa")
public class JpaConfiguration {

}