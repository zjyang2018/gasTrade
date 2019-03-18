package com.zach.gasTrade.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {

	public static HSSFRow constructOrderStatisticsHeader(HSSFWorkbook wb) {
		// 声明一个sheet并命名
		HSSFSheet sheet = wb.createSheet("订单统计");
		// 生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		// 创建第二行
		HSSFRow row = sheet.createRow(0);
		// 样式字体居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 给表头第一行一次创建单元格
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString("序号"));
		cell.setCellStyle(style);

		cell = row.createCell(1);
		// 设置列宽
		sheet.setColumnWidth(1, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("订单日期"));
		cell.setCellStyle(style);

		cell = row.createCell(2);
		// 设置列宽
		sheet.setColumnWidth(2, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("下单人数"));
		cell.setCellStyle(style);

		cell = row.createCell(3);
		sheet.setColumnWidth(3, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("订单数量"));
		cell.setCellStyle(style);

		cell = row.createCell(4);
		sheet.setColumnWidth(4, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("支付订单数量"));
		cell.setCellStyle(style);

		cell = row.createCell(5);
		sheet.setColumnWidth(5, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("派送订单数量"));
		cell.setCellStyle(style);

		cell = row.createCell(6);
		sheet.setColumnWidth(6, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("客单价"));
		cell.setCellStyle(style);

		cell = row.createCell(7);
		sheet.setColumnWidth(7, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("订单金额"));
		cell.setCellStyle(style);

		return row;
	}

	public static HSSFRow constructDeliverStatisticsHeader(HSSFWorkbook wb) {
		// 声明一个sheet并命名
		HSSFSheet sheet = wb.createSheet("订单统计");
		// 生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		// 创建第二行
		HSSFRow row = sheet.createRow(0);
		// 样式字体居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 给表头第一行一次创建单元格
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(new HSSFRichTextString("序号"));
		cell.setCellStyle(style);

		cell = row.createCell(1);
		// 设置列宽
		sheet.setColumnWidth(1, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("订单日期"));
		cell.setCellStyle(style);

		cell = row.createCell(2);
		// 设置列宽
		sheet.setColumnWidth(2, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("下单人数"));
		cell.setCellStyle(style);

		cell = row.createCell(3);
		sheet.setColumnWidth(3, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("订单数量"));
		cell.setCellStyle(style);

		cell = row.createCell(4);
		sheet.setColumnWidth(4, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("支付订单数量"));
		cell.setCellStyle(style);

		cell = row.createCell(5);
		sheet.setColumnWidth(5, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("派送订单数量"));
		cell.setCellStyle(style);

		cell = row.createCell(6);
		sheet.setColumnWidth(6, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("客单价"));
		cell.setCellStyle(style);

		cell = row.createCell(7);
		sheet.setColumnWidth(7, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("订单金额"));
		cell.setCellStyle(style);

		return row;
	}

}
