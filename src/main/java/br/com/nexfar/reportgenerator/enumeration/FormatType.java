package br.com.nexfar.reportgenerator.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
@AllArgsConstructor
@Getter
public enum FormatType {
    XLS("XLS"),
    CSV("CSV");

    private final String label;

}
