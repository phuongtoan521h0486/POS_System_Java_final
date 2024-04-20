package com.thd.pos_system_java_final.shared.ultils;

import com.thd.pos_system_java_final.models.Account.Account;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
public class ExcelAdapter implements Exporter{
    private Workbook workbook;

    public ExcelAdapter() {
        this.workbook = new XSSFWorkbook();
    }

    @Override
    public String getExtensionPart() {
        return ".xlsx";
    }

    @Override
    public byte[] generate(List<Account> accounts) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Staffs");
            sheet.setColumnWidth(0, 6400);
            String[] headers = {"Avatar", "Full Name", "Email", "Status", "Role", "Phone"};

            createHeaderRow(sheet, headers);
            addDataToExcel(sheet, accounts);

            sheet.setColumnWidth(0, 30 * 256);
            for (int i = 1; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createHeaderRow(Sheet sheet, String[] headers) {
        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void addDataToExcel(Sheet sheet, List<Account> accounts) {
        for (int rowIndex = 0; rowIndex < accounts.size(); rowIndex++) {
            Account account = accounts.get(rowIndex);
            Row dataRow = sheet.createRow(rowIndex + 1);

            if (account.getPicture() != null) {
                addPicture(sheet, account, dataRow, rowIndex);
            }

            dataRow.createCell(1).setCellValue(account.getFullName());
            dataRow.createCell(2).setCellValue(account.getEmail());
            dataRow.createCell(3).setCellValue(account.isStatus() ? "Normal": "Blocked");
            dataRow.createCell(4).setCellValue(account.getRole().toString());
            dataRow.createCell(5).setCellValue(account.getPhone());

            CellStyle style = sheet.getWorkbook().createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            for (int i = 1; i <= 5; i++) {
                Cell cell = dataRow.getCell(i);
                if (cell == null) {
                    cell = dataRow.createCell(i);
                }
                cell.setCellStyle(style);
            }
        }
    }

    private void addPicture(Sheet sheet, Account account, Row dataRow, int rowIndex) {
        try {
            dataRow.setHeightInPoints(200);
            int pictureIndex = sheet.getWorkbook().addPicture(account.getPicture(), Workbook.PICTURE_TYPE_JPEG);
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = sheet.getWorkbook().getCreationHelper().createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(rowIndex + 1);
            anchor.setCol2(1);
            anchor.setRow2(rowIndex + 2);

            Picture pict = drawing.createPicture(anchor, pictureIndex);
            pict.resize(1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
