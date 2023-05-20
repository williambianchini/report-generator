package br.com.nexfar.reportgenerator.resource;

import br.com.nexfar.reportgenerator.dto.ReportGenerateRequest;
import br.com.nexfar.reportgenerator.enumeration.OrderRequestKeyType;
import br.com.nexfar.reportgenerator.enumeration.ReportFilterKey;
import br.com.nexfar.reportgenerator.service.OrderService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/report")
public class OrderResource {

    private final OrderService orderService;

    @PostMapping("/generate")
    public void generate(HttpServletResponse response, @Valid @RequestBody ReportGenerateRequest reportGenerateRequest) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        response.setContentType("application/octet-stream");
        orderService.generateXLSReport(reportGenerateRequest, response);
    }

}
