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

	public static HSSFRow constructOrderListHeader(HSSFWorkbook wb) {
		// 声明一个sheet并命名
		HSSFSheet sheet = wb.createSheet("订单列表");
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
		cell.setCellValue(new HSSFRichTextString("订单编号"));
		cell.setCellStyle(style);

		cell = row.createCell(2);
		// 设置列宽
		sheet.setColumnWidth(2, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("订单金额"));
		cell.setCellStyle(style);

		cell = row.createCell(3);
		sheet.setColumnWidth(3, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("订单状态"));
		cell.setCellStyle(style);

		cell = row.createCell(4);
		sheet.setColumnWidth(4, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("客户姓名"));
		cell.setCellStyle(style);

		cell = row.createCell(5);
		sheet.setColumnWidth(5, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("客户手机号"));
		cell.setCellStyle(style);

		cell = row.createCell(6);
		sheet.setColumnWidth(6, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("分配状态"));
		cell.setCellStyle(style);

		cell = row.createCell(7);
		sheet.setColumnWidth(7, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("派送员"));
		cell.setCellStyle(style);

		cell = row.createCell(8);
		sheet.setColumnWidth(8, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("派送员电话"));
		cell.setCellStyle(style);

		cell = row.createCell(9);
		sheet.setColumnWidth(9, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("派送状态"));
		cell.setCellStyle(style);

		cell = row.createCell(10);
		sheet.setColumnWidth(10, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("下单时间"));
		cell.setCellStyle(style);

		return row;
	}

	public static HSSFRow constructDeliverStatisticsHeader(HSSFWorkbook wb) {
		// 声明一个sheet并命名
		HSSFSheet sheet = wb.createSheet("派送统计");
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
		cell.setCellValue(new HSSFRichTextString("派送员姓名"));
		cell.setCellStyle(style);

		cell = row.createCell(2);
		// 设置列宽
		sheet.setColumnWidth(2, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("今日派送完成数量"));
		cell.setCellStyle(style);

		cell = row.createCell(3);
		sheet.setColumnWidth(3, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("今日接单数量"));
		cell.setCellStyle(style);

		cell = row.createCell(4);
		sheet.setColumnWidth(4, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("本月派送完成数量"));
		cell.setCellStyle(style);

		cell = row.createCell(5);
		sheet.setColumnWidth(5, (short) (25 * 256));
		cell.setCellValue(new HSSFRichTextString("累积派送完成总量"));
		cell.setCellStyle(style);

		return row;
	}

}
