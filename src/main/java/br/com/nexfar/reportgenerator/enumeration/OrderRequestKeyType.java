package br.com.nexfar.reportgenerator.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

@AllArgsConstructor
@Getter
public enum OrderRequestKeyType {
    ORDER_SIMPLE("ORDER_SIMPLE", "PedidoResumido"),
    ORDER_DETAILED("ORDER_DETAILED", "PedidoDetalhado");

    private final String label;

    private final String archiveName;

}
