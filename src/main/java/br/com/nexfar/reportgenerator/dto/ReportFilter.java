package br.com.nexfar.reportgenerator.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportFilter {

    @NotEmpty(message = "Necessário informar pelo menos um parâmetro de busca.")
    private String key;
    @NotEmpty(message = "Necessário informar pelo menos um tipo de operação para busca.")
    private String operation;
    @NotEmpty(message = "Necessário informar pelo menos um valor de busca para a operação de busca.")
    private String value1;
    private String value2;

}
