package com.project.flightManagement.Service.Impl;

import com.project.flightManagement.DTO.HoaDonDTO.HoaDonDTO;
import com.project.flightManagement.DTO.ThongKeDTO.TKTongQuatDTO;
import com.project.flightManagement.Service.HoaDonService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    private HoaDonService hoaDonService;

    public ByteArrayOutputStream exportThongKeToExcel(LocalDate startDate, LocalDate endDate) throws IOException {
        // Lấy dữ liệu thống kê từ service
        TKTongQuatDTO thongKe = hoaDonService.getTKTongQuat(startDate, endDate);
        List<HoaDonDTO> hoaDonDTOList = hoaDonService.getHoaDonByStartAndEndDate(startDate, endDate);

        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thống kê tổng quát");

        // Tiêu đề headerRow0 (Báo cáo doanh thu tổng quát)
        Row headerRow0 = sheet.createRow(0);
        Cell headerCell0 = headerRow0.createCell(0);
        headerCell0.setCellValue("Báo Cáo Doanh Thu Tổng Quát BAMBOO AIRLINE");

        // Tạo kiểu dáng cho headerRow0
        CellStyle headerStyle0 = workbook.createCellStyle();
        headerStyle0.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        headerStyle0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle0.setAlignment(HorizontalAlignment.CENTER);
        headerStyle0.setVerticalAlignment(VerticalAlignment.CENTER);

        // Font chữ cho headerRow0
        Font font0 = workbook.createFont();
        font0.setBold(true);
        font0.setFontHeightInPoints((short) 18);
        font0.setColor(IndexedColors.BLACK.getIndex());
        headerStyle0.setFont(font0);

        headerCell0.setCellStyle(headerStyle0);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));  // Merge các ô để hiển thị tiêu đề

        // Tiêu đề headerRow1 (Ngày bắt đầu, ngày kết thúc)
        Row headerRow1 = sheet.createRow(1);
        headerRow1.createCell(0).setCellValue("Ngày Bắt Đầu: " + startDate.toString());
        headerRow1.createCell(2).setCellValue("Ngày Kết Thúc: " + endDate.toString());

        // Tạo kiểu dáng cho headerRow1
        CellStyle headerStyle1 = workbook.createCellStyle();
        headerStyle1.setAlignment(HorizontalAlignment.CENTER);
        headerStyle1.setVerticalAlignment(VerticalAlignment.CENTER);

        // Font chữ cho headerRow1
        Font font1 = workbook.createFont();
        font1.setFontHeightInPoints((short) 12); // Font size nhỏ hơn
        headerStyle1.setFont(font1);

        headerRow1.getCell(0).setCellStyle(headerStyle1);
        headerRow1.getCell(2).setCellStyle(headerStyle1);
//        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));  // Merge ô cho ngày bắt đầu và kết thúc
        // Merge ô cho ngày bắt đầu và kết thúc
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 1));  // Merge ô cho ngày bắt đầu
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));  // Merge ô cho ngày kết thúc

        // Tiêu đề header chính
        Row headerRow = sheet.createRow(2);
        String[] headers = {"Tổng Số Hóa Đơn", "Tổng Doanh Thu", "Doanh Thu Trung Bình", "Tổng Số Vé"};

        // Tạo kiểu dáng cho header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Tạo dòng dữ liệu
        Row dataRow = sheet.createRow(3);
        dataRow.createCell(0).setCellValue(thongKe.getTongSoHoaDon());
        dataRow.createCell(1).setCellValue(thongKe.getTongDoanhThu());
        dataRow.createCell(2).setCellValue(thongKe.getDoanhThuTrungBinh());
        dataRow.createCell(3).setCellValue(thongKe.getTongSoVe());

        // Cài đặt chiều rộng của cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Lưu file Excel vào ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }


}
