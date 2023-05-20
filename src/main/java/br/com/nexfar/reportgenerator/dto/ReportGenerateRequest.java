package br.com.nexfar.reportgenerator.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportGenerateRequest {

    @NotBlank(message = "Necessário informar a chave de busca.")
    private String key;

    @NotBlank(message = "Necessário informar o formato do arquivo a ser exportado.")
    private String format;

    @NotEmpty(message = "Necessário informar pelo menos um filtro.")
    private List<ReportFilter> filters;

}
