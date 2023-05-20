package br.com.nexfar.reportgenerator.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum ReportFilterKey {
    CNPJ("CNPJ", "EQ", "") {},
    CREATEDAT("CREATEDAT", "INTERVAL", ""),
    STATUS("STATUS", "EQ", ""),
    NETTOTAL("NETTOTAL", "GTE", "LTE");

    private final String label;
    private final String operation;
    private final String optionalOperation;

}
