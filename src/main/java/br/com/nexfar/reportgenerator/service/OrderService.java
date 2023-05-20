package br.com.nexfar.reportgenerator.service;

import br.com.nexfar.reportgenerator.dto.ReportFilter;
import br.com.nexfar.reportgenerator.dto.ReportGenerateRequest;
import br.com.nexfar.reportgenerator.entity.Order;
import br.com.nexfar.reportgenerator.enumeration.FormatType;
import br.com.nexfar.reportgenerator.enumeration.OrderRequestKeyType;
import br.com.nexfar.reportgenerator.util.CsvExportUtil;
import br.com.nexfar.reportgenerator.util.XlsExportUtil;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static br.com.nexfar.reportgenerator.enumeration.ReportFilterKey.*;
import static java.util.Objects.*;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    public static final int POSSIBLE_FILTERS = 4;
    @Autowired
    MongoTemplate mongoTemplate;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void generateXLSReport(ReportGenerateRequest reportGenerateRequest, HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        final var orderList = dynamicQuery(reportGenerateRequest.getFilters());
        switch  (getRequestKeyType(reportGenerateRequest)) {
            case ORDER_SIMPLE -> {
                if (reportGenerateRequest.getFormat().equalsIgnoreCase(String.valueOf(FormatType.XLS))) {
                    final var xlsExportUtil = new XlsExportUtil(orderList);
                    xlsExportUtil.exportOrderSimpleToXLS(response, getRequestKeyType(reportGenerateRequest));
                }else if (reportGenerateRequest.getFormat().equalsIgnoreCase(String.valueOf(FormatType.CSV))) {
                    final var csvExportUtil = new CsvExportUtil(orderList);
                    csvExportUtil.exportOrderSimpleToCSV(response, getRequestKeyType(reportGenerateRequest));
                }
            }
            case ORDER_DETAILED -> {
                if (reportGenerateRequest.getFormat().equalsIgnoreCase(String.valueOf(FormatType.XLS))) {
                    final var xlsExportUtil = new XlsExportUtil(orderList);
                    xlsExportUtil.exportOrderDetailedToXLS(response, getRequestKeyType(reportGenerateRequest));
                }else if (reportGenerateRequest.getFormat().equalsIgnoreCase(String.valueOf(FormatType.CSV))) {
                    final var csvExportUtil = new CsvExportUtil(orderList);
                    csvExportUtil.exportOrderDetailedToCSV(response, getRequestKeyType(reportGenerateRequest));
                }
            }
        }
    }

    private static OrderRequestKeyType getRequestKeyType(ReportGenerateRequest reportGenerateRequest) {
        return OrderRequestKeyType.valueOf(reportGenerateRequest.getKey());
    }

    public List<Order> dynamicQuery(List<ReportFilter> reportFilter) {
        final var query = new Query();
        final var criteria = new ArrayList<Criteria>();
        reportFilter.stream().limit(POSSIBLE_FILTERS).forEach(it -> {
            if (nonNull(it.getKey()) && nonNull(it.getOperation())) {
                if(isCnpjfilter(it)) {
                    criteria.add(Criteria.where("client.cnpj").is(it.getValue1()));
                } else if (isStatusfilter(it)) {
                    criteria.add(Criteria.where("status").is(it.getValue1()));
                } else if (isCreatedAtfilter(it)) {
                    LocalDateTime startDate = LocalDateTime.parse(it.getValue1(), formatter);
                    LocalDateTime endDate = LocalDateTime.parse(it.getValue2(), formatter);

                    criteria.add(Criteria.where("createdAt").gte(startDate).lt(endDate));
                } else if (isNetTotalFilter(it)) {
                    if (it.getOperation().equalsIgnoreCase(NETTOTAL.getOperation())) {
                        criteria.add(Criteria.where("netTotal").gte(it.getValue1()));
                    } else if (it.getOperation().equalsIgnoreCase(NETTOTAL.getOptionalOperation())) {
                        criteria.add(Criteria.where("netTotal").lte(it.getValue1()));
                    }
                }
            }
        });
        if(!criteria.isEmpty()) query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        return mongoTemplate.find(query, Order.class);

    }

    private static boolean isCnpjfilter(ReportFilter it) {
        return nonNull(it.getValue1())
                && CNPJ.getLabel().equalsIgnoreCase(it.getKey())
                && CNPJ.getOperation().equalsIgnoreCase(it.getOperation());
    }

    private static boolean isStatusfilter(ReportFilter it) {
        return nonNull(it.getValue1())
                && STATUS.getLabel().equalsIgnoreCase(it.getKey())
                && STATUS.getOperation().equalsIgnoreCase(it.getOperation());
    }

    private static boolean isCreatedAtfilter(ReportFilter it) {
        return nonNull(it.getValue1()) && nonNull(it.getValue2())
                && CREATEDAT.getLabel().equalsIgnoreCase(it.getKey())
                && CREATEDAT.getOperation().equalsIgnoreCase(it.getOperation());
    }

    private static boolean isNetTotalFilter(ReportFilter it) {
        return nonNull(it.getValue1())
                && NETTOTAL.getLabel().equalsIgnoreCase(it.getKey())
                && (NETTOTAL.getOperation().equalsIgnoreCase(it.getOperation()) || NETTOTAL.getOptionalOperation().equalsIgnoreCase(it.getOperation()));
    }

}
