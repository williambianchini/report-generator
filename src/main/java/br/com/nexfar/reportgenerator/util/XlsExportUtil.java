package br.com.nexfar.reportgenerator.util;

import br.com.nexfar.reportgenerator.entity.Order;
import br.com.nexfar.reportgenerator.enumeration.FormatType;
import br.com.nexfar.reportgenerator.enumeration.OrderRequestKeyType;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class XlsExportUtil {

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private List<Order> orderList;

    public XlsExportUtil(List<Order> orderList) {
        this.orderList = orderList;
        workbook = new HSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer integer){
            cell.setCellValue(integer);
        }else if (value instanceof Float aFloat){
            cell.setCellValue(aFloat);
        }else if (value instanceof Boolean aBoolean){
            cell.setCellValue(aBoolean);
        }else if (value instanceof Long aLong){
            cell.setCellValue(aLong);
        }else if (value instanceof LocalDateTime localDateTime){
            cell.setCellValue(localDateTime);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void createHeaderRow(OrderRequestKeyType keyType){
        sheet   = workbook.createSheet(keyType.getLabel());
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight((short) 500);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        createCell(row, 0, keyType.getArchiveName(), style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        font.setFontHeightInPoints((short) 400);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight((short) 250);
        style.setFont(font);
        createCell(row, 0, "_id", style);
        createCell(row, 1, "client.cnpj", style);
        createCell(row, 2, "client.name", style);
        createCell(row, 3, "createdAt", style);
        createCell(row, 4, "status", style);
        createCell(row, 5, "netTotal", style);
        createCell(row, 6, "totalWithTaxes", style);
    }

    private void writeOrderSimpleData(){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int rowCount = 2;
        CellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontHeight((short) 200);
        style.setFont(font);

        for (Order order : orderList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, order.getId(), style);
            createCell(row, columnCount++, order.getClient().getCnpj(), style);
            createCell(row, columnCount++, order.getClient().getName(), style);
            createCell(row, columnCount++, order.getCreatedAt().format(formatter), style);
            createCell(row, columnCount++, order.getStatus(), style);
            createCell(row, columnCount++, order.getNetTotal().toPlainString(), style);
            createCell(row, columnCount++, order.getTotalWithTaxes().toPlainString(), style);
        }

    }

    public void exportOrderSimpleToXLS(HttpServletResponse response, OrderRequestKeyType orderRequestKeyType) throws IOException {
        createHeaderRow(orderRequestKeyType);
        writeOrderSimpleData();
        ServletOutputStream outputStream = response.getOutputStream();
        String fileName = OrderRequestKeyType.ORDER_SIMPLE.getArchiveName() + "." + FormatType.XLS.getLabel().toLowerCase();
        String headerKey = "Content-Disposition";
        response.addHeader(headerKey, fileName);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
//TODO infelizmente não consegui concluir esta feature dentro do prazo.
    public void exportOrderDetailedToXLS(HttpServletResponse response, OrderRequestKeyType requestKeyType) {
        return;
    }
}
