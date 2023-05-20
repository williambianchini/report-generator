package br.com.nexfar.reportgenerator.util;

import br.com.nexfar.reportgenerator.entity.Order;
import br.com.nexfar.reportgenerator.enumeration.FormatType;
import br.com.nexfar.reportgenerator.enumeration.OrderRequestKeyType;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExportUtil {
    private final List<Order> orderList;

    public CsvExportUtil(List<Order> orderList) {
        this.orderList = orderList;
    }

    //TODO infelizmente não consegui concluir esta feature dentro do prazo
    public void exportOrderDetailedToCSV(HttpServletResponse response, OrderRequestKeyType requestKeyType) {
        return;
    }

    //TODO infelizmente não consegui concluir esta feature dentro do prazo;
    public void exportOrderSimpleToCSV(HttpServletResponse response, OrderRequestKeyType requestKeyType) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(OrderRequestKeyType.ORDER_SIMPLE.getArchiveName() + FormatType.CSV.getLabel().toLowerCase()));
        StatefulBeanToCsv<Order> beanToCsv = new StatefulBeanToCsvBuilder<Order>(csvWriter)
                .withSeparator(';')
                .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                .withOrderedResults(true)
                .build();
                beanToCsv.write(this.orderList);
        beanToCsv.getCapturedExceptions();
    }
}
