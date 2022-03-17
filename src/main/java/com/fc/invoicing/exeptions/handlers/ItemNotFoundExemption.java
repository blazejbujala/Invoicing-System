package com.fc.invoicing.exeptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemNotFoundExemption extends RuntimeException {

    private String message;
}
