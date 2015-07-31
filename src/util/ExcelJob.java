package util;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.Avto;
import beans.Drivingschedule;
import beans.Groups;
import beans.Questions;
import beans.Schedules;
import beans.Students;



 
public class ExcelJob {
	private static final transient Logger log = LoggerFactory.getLogger(ExcelJob.class);
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	public static void exelSet(List<Schedules> ls, Groups g, Date date, Date date2) 
	{
		try{
			String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
			String filename="C:/docs/rasp_"+dt+".xls";
			//String filename="/home/rasp_"+dt+".xls";
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("List 1");
	
			
			HSSFCellStyle style = hwb.createCellStyle();
			style.setBorderTop((short) 2); // double lines border
			style.setBorderBottom((short) 1); // single line border
			style.setBorderLeft((short)2);
			style.setBorderRight((short)2);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			
			HSSFFont font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style.setFont(font);
			
			HSSFCellStyle style2 = hwb.createCellStyle();
			
			style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style2.setFillForegroundColor(HSSFColor.WHITE.index);
			style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setFont(font);
			
			
			
			HSSFRow rowhead1 = sheet.createRow(1);
			rowhead1.createCell(2).setCellValue("РАСПИСАНИЕ");
			rowhead1.getCell(2).setCellStyle(style2);
			
			HSSFRow rowhead5 = sheet.createRow(2);
			rowhead5.createCell(2).setCellValue("занятий учебной группы № " + g.getGroupName() + " по подготовке");
			rowhead5.getCell(2).setCellStyle(style2);
			
			rowhead5 = sheet.createRow(3);
			rowhead5.createCell(2).setCellValue("водителей транспортных средств категории \"B\"");
			rowhead5.getCell(2).setCellStyle(style2);
			
			rowhead5 = sheet.createRow(4);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
			rowhead5.createCell(2).setCellValue("с " + dateFormat1.format(date) +" по "+ dateFormat1.format(date2));
			rowhead5.getCell(2).setCellStyle(style2);
			
			
			sheet.addMergedRegion(new CellRangeAddress( 1, //first row (0-based)
		            1, //last row  (0-based)
		            2, //first column (0-based)
		            3  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 2, //first row (0-based)
		            2, //last row  (0-based)
		            2, //first column (0-based)
		            3  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 3, //first row (0-based)
		            3, //last row  (0-based)
		            2, //first column (0-based)
		            3  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 4, //first row (0-based)
		            4, //last row  (0-based)
		            2, //first column (0-based)
		            3  //last column  (0-based)
		    ));
			
			HSSFRow rowhead = sheet.createRow(6);
			rowhead.createCell(1).setCellValue("Начало занятий");
			rowhead.getCell(1).setCellStyle(style);
			rowhead.createCell(2).setCellValue("Конец занятий");
			rowhead.getCell(2).setCellStyle(style);
			rowhead.createCell(3).setCellValue("Предмет и наименование тем занятий");
			rowhead.getCell(3).setCellStyle(style);
			rowhead.createCell(4).setCellValue("Преподаватель");
			rowhead.getCell(4).setCellStyle(style);
			
			HSSFCellStyle style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			
			for(int i=0; i<ls.size(); i++) {
				Schedules bts = ls.get(i);
				HSSFRow row=   sheet.createRow(i+7);
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				row.createCell(1).setCellValue(dateFormat.format(bts.getBeginTime()));
				row.getCell(1).setCellStyle(style1);
				row.createCell(2).setCellValue(dateFormat.format(bts.getEndTime()));
				row.getCell(2).setCellStyle(style1);
				row.createCell(3).setCellValue(bts.getThemes().getSubjects().getNameOfSubject() + " " + bts.getThemes().getNameOfTheme());
				row.getCell(3).setCellStyle(style1);
				row.createCell(4).setCellValue(bts.getTeachers().getPrepodfam() + " " + bts.getTeachers().getPrepodim());
				row.getCell(4).setCellStyle(style1);
			
			}
			int d1 = ls.size()+10;
			rowhead1 = sheet.createRow(d1);
			rowhead1.createCell(3).setCellValue("Зам. директора по УР ______________");
			rowhead1.getCell(3).setCellStyle(style2);
			
			rowhead1 = sheet.createRow(++d1);
			rowhead1.createCell(3).setCellValue("\"__\" _________ 20__ г.");
			rowhead1.getCell(3).setCellStyle(style2);
			
			sheet.autoSizeColumn(0); 
		    sheet.autoSizeColumn(1);
		    sheet.autoSizeColumn(2);
		    sheet.autoSizeColumn(3);
		    sheet.autoSizeColumn(4);

		    
		 
			
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			log.info("Табличный файл был создан успешно по пути: " + filename);
			javax.swing.JOptionPane.showMessageDialog(null, "Табличный файл был создан успешно по пути: " + filename);

			} catch ( java.lang.IndexOutOfBoundsException ex ) {
			    ex.printStackTrace();
			    log.info(ex.getMessage());
			} catch (Exception exc) {
			
			}
	}
	
	public static void exelSet2(List<Students> ls, Groups g) 
	{
		try{
			String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
			String filename="C:/docs/pricaz_"+dt+".xls";
			//String filename="/home/anatoly/rasp_"+dt+".xls";
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("List 1");
	
			
			HSSFCellStyle style = hwb.createCellStyle();
			//style.setBorderTop((short) 6); // double lines border
			//style.setBorderBottom((short) 1); // single line border
			//style.setBorderLeft((short)2);
			//style.setBorderRight((short)2);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setAlignment(CellStyle.ALIGN_LEFT);
			
			HSSFCellStyle style3 = hwb.createCellStyle();
			style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style3.setFillForegroundColor(HSSFColor.WHITE.index);
			style3.setAlignment(CellStyle.ALIGN_CENTER);
			HSSFFont font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style3.setFont(font);
			
			font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			style.setFont(font);
			
			HSSFRow rowhead1 = sheet.createRow(1);
			rowhead1.createCell(2).setCellValue("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ");
			rowhead1.getCell(2).setCellStyle(style3);
			
			HSSFRow rowhead5 = sheet.createRow(2);
			rowhead5.createCell(2).setCellValue("«МЕЖШКОЛЬНЫЙ УЧЕБНЫЙ КОМБИНАТ»");
			rowhead5.getCell(2).setCellStyle(style3);
			
			rowhead5 = sheet.createRow(4);
			rowhead5.createCell(2).setCellValue("ПРИКАЗ ");
			rowhead5.getCell(2).setCellStyle(style3);
			
			rowhead5 = sheet.createRow(6);
			rowhead5.createCell(2).setCellValue("«О зачислении граждан в списки учащихся для подготовки водителей");
			rowhead5.getCell(2).setCellStyle(style3);
			
			rowhead5 = sheet.createRow(7);
			rowhead5.createCell(2).setCellValue("транспортных средств категории «B»");
			rowhead5.getCell(2).setCellStyle(style3);
			
			rowhead5 = sheet.createRow(9);
			rowhead5.createCell(2).setCellValue("В соответствии с лицензией А 266401 регистрационный номер № 390 от 1 сентября 2008 г  и на основании ");
			rowhead5.getCell(2).setCellStyle(style);
			
			rowhead5 = sheet.createRow(10);
			rowhead5.createCell(2).setCellValue("заявлений граждан ");
			rowhead5.getCell(2).setCellStyle(style);
			
			rowhead5 = sheet.createRow(12);
			rowhead5.createCell(2).setCellValue("ПРИКАЗЫВАЮ: ");
			rowhead5.getCell(2).setCellStyle(style3);
			
			rowhead5 = sheet.createRow(14);
			rowhead5.createCell(2).setCellValue("1. Нижепоименованных граждан, прибывших для обучения по программе подготовки водителей транспортных  ");
			rowhead5.getCell(2).setCellStyle(style);
			
			rowhead5 = sheet.createRow(15);
			rowhead5.createCell(2).setCellValue("средств категории «B», с "+dateFormat.format(g.getGruopstartdate())+ " зачислить в списки переменного состава МУК, именовать ");
			rowhead5.getCell(2).setCellStyle(style);
			
			rowhead5 = sheet.createRow(16);
			rowhead5.createCell(2).setCellValue("учащимися и сформировать группу «"+ g.getGroupName()+"» в составе: ");
			rowhead5.getCell(2).setCellStyle(style);
			
			HSSFCellStyle style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style1.setFont(font);
			
			HSSFRow rowhead = sheet.createRow(17);
			rowhead.createCell(2).setCellValue("№ п.п.");
			rowhead.getCell(2).setCellStyle(style1);
			rowhead.createCell(3).setCellValue("Фамилия, имя, отчество");
			rowhead.getCell(3).setCellStyle(style1);
			rowhead.createCell(4).setCellValue("Дата рождения");
			rowhead.getCell(4).setCellStyle(style1);
			
			
			sheet.addMergedRegion(new CellRangeAddress( 1, //first row (0-based)
		            1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 2, //first row (0-based)
		            2, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 4, //first row (0-based)
		            4, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 6, //first row (0-based)
		            6, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 7, //first row (0-based)
		            7, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 9, //first row (0-based)
		            9, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 10, //first row (0-based)
		            10, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 12, //first row (0-based)
		            12, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 14, //first row (0-based)
		            14, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 15, //first row (0-based)
		            15, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 16, //first row (0-based)
		            16, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			
			style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setFont(font);
			
			for(int i=0; i<ls.size(); i++) {
				Students bts = ls.get(i);
				HSSFRow row=   sheet.createRow(i+18);
				row.createCell(2).setCellValue(i+1);
				row.getCell(2).setCellStyle(style1);
				row.createCell(3).setCellValue(bts.getSurname()+ " "+ bts.getName()+ " "+ bts.getPatronymicname());
				row.getCell(3).setCellStyle(style1);
				
				//row.createCell(1).setCellValue(dateFormat.format(bts.getBeginTime()));
				row.createCell(4).setCellValue(dateFormat.format(bts.getDatebirth()));
				row.getCell(4).setCellStyle(style1);
			
			}
			int d1 = ls.size()+19;
			rowhead5 = sheet.createRow(d1);
			rowhead5.createCell(2).setCellValue("2. Учебный процесс организовать в период с "+dateFormat.format(g.getGruopstartdate()) +" по " +dateFormat.format(g.getGroupenddate()) +" в соответствии с Примерной ");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1, //first row (0-based)
		            d1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			rowhead5 = sheet.createRow(d1+1);
			rowhead5.createCell(2).setCellValue("программой подготовки водителей транспортных средств категории «B» от 18 июня  2010 г, утвержденной ");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1+1, //first row (0-based)
		            d1+1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			rowhead5 = sheet.createRow(d1+2);
			rowhead5.createCell(2).setCellValue("Министерством образования и науки Российской Федерации. Программа обучения 206 часов, вождение 32 часа, ");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1+2, //first row (0-based)
		            d1+2, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			rowhead5 = sheet.createRow(d1+3);
			rowhead5.createCell(2).setCellValue("режим теоретических занятий – вечерний с 18.00 каждый день кроме воскресенья, место проведения – класс 4.  ");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1+3, //first row (0-based)
		            d1+3, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			rowhead5 = sheet.createRow(d1+4);
			rowhead5.createCell(2).setCellValue("Практические занятия по вождению автомобилей проводить ежедневно с 8.00 до 19.00 по графику.");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1+4, //first row (0-based)
		            d1+4, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			rowhead5 = sheet.createRow(d1+5);
			rowhead5.createCell(2).setCellValue("3. Приказ довести всему постоянному и переменному составу МУК.");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1+5, //first row (0-based)
		            d1+5, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			rowhead5 = sheet.createRow(d1+6);
			rowhead5.createCell(2).setCellValue("4. Контроль за исполнением приказа возложить на заместителя директора по учебной работе.");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1+6, //first row (0-based)
		            d1+6, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			rowhead5 = sheet.createRow(d1+8);
			rowhead5.createCell(2).setCellValue("Директор ____________________ Л.А.Банька");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1+8, //first row (0-based)
		            d1+8, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			
			sheet.setColumnWidth(4, 5000); 
		    //sheet.autoSizeColumn(1);
		    sheet.setColumnWidth(2,5500);
		    sheet.setColumnWidth(3,25300);
		    ///sheet.autoSizeColumn(4);

		    
		 
			
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			log.info("Табличный файл был создан успешно по пути: " + filename);
			javax.swing.JOptionPane.showMessageDialog(null, "Табличный файл был создан успешно по пути: " + filename);

			} catch ( java.lang.IndexOutOfBoundsException ex ) {
			    ex.printStackTrace();
			    log.info(ex.getMessage());
			} catch (Exception exc) {
			
			}
	}
	
	public static void exelSet3(List<Students> ls, Groups g) 
	{
		try{
			String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
			String filename="C:/docs/spisok_"+dt+".xls";
			//String filename="/home/rasp_"+dt+".xls";
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("List 1");
	
			
			HSSFCellStyle style = hwb.createCellStyle();
			//style.setBorderTop((short) 6); // double lines border
			//style.setBorderBottom((short) 1); // single line border
			//style.setBorderLeft((short)2);
			//style.setBorderRight((short)2);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setAlignment(CellStyle.ALIGN_LEFT);
			
			HSSFCellStyle style3 = hwb.createCellStyle();
			style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style3.setFillForegroundColor(HSSFColor.WHITE.index);
			style3.setAlignment(CellStyle.ALIGN_CENTER);
			HSSFFont font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style3.setFont(font);
			
			font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			style.setFont(font);
			
			HSSFRow rowhead1 = sheet.createRow(1);
			rowhead1.createCell(2).setCellValue("ГОСУДАРСТВЕННОЕ БЮДЖЕТНОЕ ОБРАЗОВАТЕЛЬНОЕ УЧРЕЖДЕНИЕ");
			rowhead1.getCell(2).setCellStyle(style3);
			
			HSSFRow rowhead5 = sheet.createRow(2);
			rowhead5.createCell(2).setCellValue("«МЕЖШКОЛЬНЫЙ УЧЕБНЫЙ КОМБИНАТ»");
			rowhead5.getCell(2).setCellStyle(style3);
			
			rowhead5 = sheet.createRow(4);
			rowhead5.createCell(2).setCellValue("Список учащихся  группы: "+ g.getGroupName() + " категории \"B\"");
			rowhead5.getCell(2).setCellStyle(style3);
		
			HSSFCellStyle style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style1.setFont(font);
			
			HSSFRow rowhead = sheet.createRow(5);
			rowhead.createCell(2).setCellValue("№");
			rowhead.getCell(2).setCellStyle(style1);
			rowhead.createCell(3).setCellValue("Фамилия, имя, отчество");
			rowhead.getCell(3).setCellStyle(style1);
			rowhead.createCell(4).setCellValue("Дата рождения");
			rowhead.getCell(4).setCellStyle(style1);
			rowhead.createCell(5).setCellValue("Паспортные данные");
			rowhead.getCell(5).setCellStyle(style1);
			rowhead.createCell(6).setCellValue("Место работы/учебы");
			rowhead.getCell(6).setCellStyle(style1);
			rowhead.createCell(7).setCellValue("Адрес и телефон");
			rowhead.getCell(7).setCellStyle(style1);
			
			
			sheet.addMergedRegion(new CellRangeAddress( 1, //first row (0-based)
		            1, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 2, //first row (0-based)
		            2, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 4, //first row (0-based)
		            4, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			
			
			style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setFont(font);
			
			for(int i=0; i<ls.size(); i++) {
				Students bts = ls.get(i);
				HSSFRow row=   sheet.createRow(i+6);
				row.createCell(2).setCellValue(i+1);
				row.getCell(2).setCellStyle(style1);
				row.createCell(3).setCellValue(bts.getSurname()+ " "+ bts.getName()+ " "+ bts.getPatronymicname());
				row.getCell(3).setCellStyle(style1);
				
				//row.createCell(1).setCellValue(dateFormat.format(bts.getBeginTime()));
				row.createCell(4).setCellValue(dateFormat.format(bts.getDatebirth()));
				row.getCell(4).setCellStyle(style1);
				row.createCell(5).setCellValue(bts.getTypeDocum()+ " " + bts.getDocnumber() + " "+ bts.getDocseries() + " "+ bts.getDoinfo());
				row.getCell(5).setCellStyle(style1);
				if(!bts.getJobplace().equals("")){
					row.createCell(6).setCellValue(bts.getJobplace());
					row.getCell(6).setCellStyle(style1);
				}
				if(!bts.getEducationinfo().equals("")){
					row.createCell(6).setCellValue(bts.getEducationinfo());
					row.getCell(6).setCellStyle(style1);
				}
				row.createCell(7).setCellValue(bts.getAddress()+ " "+bts.getPhone());
				row.getCell(7).setCellStyle(style1);
			
			}
			int d1 = ls.size()+8;
			rowhead5 = sheet.createRow(d1);
			rowhead5.createCell(2).setCellValue("Всего количество - "+ (ls.size()+1)+" человек");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1, //first row (0-based)
		            d1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			rowhead5 = sheet.createRow(d1+1);
			rowhead5.createCell(2).setCellValue("Директор ____________________ Л.А.Банька");
			rowhead5.getCell(2).setCellStyle(style);
			sheet.addMergedRegion(new CellRangeAddress( d1+1, //first row (0-based)
		            d1+1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			rowhead5.createCell(5).setCellValue("\" \" ____________ 20__ г.");
			rowhead5.getCell(5).setCellStyle(style);
			
			sheet.setColumnWidth(4, 5000); 
		    //sheet.autoSizeColumn(1);
		    sheet.setColumnWidth(2,3000);
		    sheet.setColumnWidth(3,12300);
		    sheet.autoSizeColumn(5);
		    sheet.autoSizeColumn(6);
		    sheet.autoSizeColumn(7);

		    
		 
			
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			log.info("Табличный файл был создан успешно по пути: " + filename);
			javax.swing.JOptionPane.showMessageDialog(null, "Табличный файл был создан успешно по пути: " + filename);

			} catch ( java.lang.IndexOutOfBoundsException ex ) {
			    ex.printStackTrace();
			    log.info(ex.getMessage());
			} catch (Exception exc) {
			
			}
	}
	
	public static void exelSet4(List<Drivingschedule> ls, Groups g, Date date, Date date2) 
	{
		try{
			String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
			String filename="C:/docs/vozhdenie_"+dt+".xls";
			//String filename="/home/rasp_"+dt+".xls";
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("List 1");
	
			
			HSSFCellStyle style = hwb.createCellStyle();
			//style.setBorderTop((short) 6); // double lines border
			//style.setBorderBottom((short) 1); // single line border
			//style.setBorderLeft((short)2);
			//style.setBorderRight((short)2);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			
			
			HSSFFont font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style.setFont(font);
			
			HSSFRow rowhead1 = sheet.createRow(1);
			rowhead1.createCell(2).setCellValue("ГРАФИК");
			rowhead1.getCell(2).setCellStyle(style);
			
			HSSFRow rowhead5 = sheet.createRow(2);
			rowhead5.createCell(2).setCellValue("очередности вождения ТС категории \"B\" группы " + g.getGroupName());
			rowhead5.getCell(2).setCellStyle(style);
			
			
			rowhead5 = sheet.createRow(3);
			rowhead5.createCell(2).setCellValue("с " + dateFormat1.format(date) +" по "+ dateFormat1.format(date2));
			rowhead5.getCell(2).setCellStyle(style);
			
			
			sheet.addMergedRegion(new CellRangeAddress( 1, //first row (0-based)
		            1, //last row  (0-based)
		            2, //first column (0-based)
		            5  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 2, //first row (0-based)
		            2, //last row  (0-based)
		            2, //first column (0-based)
		            5  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 3, //first row (0-based)
		            3, //last row  (0-based)
		            2, //first column (0-based)
		            5  //last column  (0-based)
		    ));
			
			HSSFCellStyle style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 2); // double lines border
			style1.setBorderBottom((short) 2); // single line border
			style1.setBorderLeft((short)2);
			style1.setBorderRight((short)2);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style1.setFont(font);
			
			HSSFRow rowhead = sheet.createRow(5);
			rowhead.createCell(1).setCellValue("Учащийся");
			rowhead.getCell(1).setCellStyle(style1);
			rowhead.createCell(2).setCellValue("Мастер ПО");
			rowhead.getCell(2).setCellStyle(style1);
			rowhead.createCell(3).setCellValue("Автомобиль");
			rowhead.getCell(3).setCellStyle(style1);
			rowhead.createCell(4).setCellValue("Время начала");
			rowhead.getCell(4).setCellStyle(style1);
			rowhead.createCell(5).setCellValue("Количество часов");
			rowhead.getCell(5).setCellStyle(style1);
			rowhead.createCell(6).setCellValue("Выполнено");
			rowhead.getCell(6).setCellStyle(style1);
			
			style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setFont(font);
			
			for(int i=0; i<ls.size(); i++) {
				Drivingschedule bts = ls.get(i);
				HSSFRow row=   sheet.createRow(i+6);
				
				row.createCell(1).setCellValue(bts.getStudents().getSurname() + " " + bts.getStudents().getName());
				row.getCell(1).setCellStyle(style1);
				row.createCell(2).setCellValue(bts.getTeachers().getPrepodfam() + " " + bts.getTeachers().getPrepodim());
				row.getCell(2).setCellStyle(style1);
				row.createCell(3).setCellValue(bts.getAvto().getModel());
				row.getCell(3).setCellStyle(style1);
				row.createCell(4).setCellValue(dateFormat.format(bts.getDatebegin()));
				row.getCell(4).setCellStyle(style1);
				row.createCell(5).setCellValue(bts.getNumberOfHours());
				row.getCell(5).setCellStyle(style1);
				if(bts.isDoneFlag()) {
					row.createCell(6).setCellValue("Да");
					row.getCell(6).setCellStyle(style1);
				}
				if(!bts.isDoneFlag()) {
					row.createCell(6).setCellValue("Нет");
					row.getCell(6).setCellStyle(style1);
				}
				
			
			}
			int d1 = ls.size()+8;
			rowhead1 = sheet.createRow(d1);
			rowhead1.createCell(2).setCellValue("Заведующий отделением \"Автодело\"  __________  А.В. Радионов");
			rowhead1.getCell(2).setCellStyle(style);
			
			rowhead1 = sheet.createRow(d1+1);
			rowhead1.createCell(2).setCellValue("\"__\" _________ 20__ г.");
			rowhead1.getCell(2).setCellStyle(style);
			
			sheet.addMergedRegion(new CellRangeAddress( d1, //first row (0-based)
		            d1, //last row  (0-based)
		            2, //first column (0-based)
		            5  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( d1+1, //first row (0-based)
		            d1+1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.autoSizeColumn(0); 
		    sheet.autoSizeColumn(1);
		    sheet.autoSizeColumn(2);
		    sheet.autoSizeColumn(3);
		    sheet.autoSizeColumn(4);
		    sheet.autoSizeColumn(5);
		    sheet.autoSizeColumn(6);

		    
		 
			
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			log.info("Табличный файл был создан успешно по пути: " + filename);
			javax.swing.JOptionPane.showMessageDialog(null, "Табличный файл был создан успешно по пути: " + filename);

			} catch ( java.lang.IndexOutOfBoundsException ex ) {
			    ex.printStackTrace();
			    log.info(ex.getMessage());
			} catch (Exception exc) {
			
			}
	}

	public static void exelSet5(List<Questions> curQuestions, int[] arr, Students st, Timestamp curTime) {
			//System.out.println(st.toString()+curTime);
			try{
				String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
				String filename="C:/docs/testResult_"+dt+".xls";
				//String filename="/home/rasp_"+dt+".xls";
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
				HSSFWorkbook hwb = new HSSFWorkbook();
				HSSFSheet sheet = hwb.createSheet("List 1");
		
				
				HSSFCellStyle style = hwb.createCellStyle();
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style.setFillForegroundColor(HSSFColor.WHITE.index);
				style.setAlignment(CellStyle.ALIGN_CENTER);
				
				
				HSSFFont font = hwb.createFont();
				font.setFontName("Times New Roman");
				font.setFontHeightInPoints((short) 14);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				//font.setColor(HSSFColor.ROYAL_BLUE.index);
				style.setFont(font);
				
				HSSFRow rowhead1 = sheet.createRow(1);
				rowhead1.createCell(2).setCellValue("Результаты тестирования");
				rowhead1.getCell(2).setCellStyle(style);
				
				HSSFRow rowhead5 = sheet.createRow(2);
				rowhead5.createCell(2).setCellValue("учащегося  " + st.toString());
				rowhead5.getCell(2).setCellStyle(style);
				
				
				rowhead5 = sheet.createRow(3);
				rowhead5.createCell(2).setCellValue("от " + dateFormat.format(curTime));
				rowhead5.getCell(2).setCellStyle(style);
				
				
				
				HSSFCellStyle style1 = hwb.createCellStyle();
				style1.setBorderTop((short) 2); // double lines border
				style1.setBorderBottom((short) 2); // single line border
				style1.setBorderLeft((short)2);
				style1.setBorderRight((short)2);
				style1.setAlignment(CellStyle.ALIGN_CENTER);
				font = hwb.createFont();
				font.setFontName("Times New Roman");
				font.setFontHeightInPoints((short) 14);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				//font.setColor(HSSFColor.ROYAL_BLUE.index);
				style1.setFont(font);
				
				HSSFRow rowhead = sheet.createRow(8);
				rowhead.createCell(1).setCellValue("№");
				rowhead.getCell(1).setCellStyle(style1);
				rowhead.createCell(2).setCellValue("Вопрос");
				rowhead.getCell(2).setCellStyle(style1);
				rowhead.createCell(3).setCellValue("Результат");
				rowhead.getCell(3).setCellStyle(style1);
				
				
				style1 = hwb.createCellStyle();
				style1.setBorderTop((short) 1); // double lines border
				style1.setBorderBottom((short) 1); // single line border
				style1.setBorderLeft((short)1);
				style1.setBorderRight((short)1);
				style1.setAlignment(CellStyle.ALIGN_CENTER);
				style1.setFont(font);
				
				for(int i=0; i<curQuestions.size(); i++) {
					Questions bts = curQuestions.get(i);
					HSSFRow row=   sheet.createRow(i+6);
					
					row.createCell(1).setCellValue(i+1);
					row.getCell(1).setCellStyle(style1);
					row.createCell(2).setCellValue(bts.getQuestionText());
					row.getCell(2).setCellStyle(style1);
					if (arr[i]==0){
						row.createCell(3).setCellValue("Неправильно");
						row.getCell(3).setCellStyle(style1);
					} 
					if (arr[i]==1){
						row.createCell(3).setCellValue("Правильно");
						row.getCell(3).setCellStyle(style1);
					} 
				
					
				
				}
				/*int d1 = curQuestions.size()+4;
				rowhead1 = sheet.createRow(d1);
				rowhead1.createCell(2).setCellValue("Заведующий отделением \"Автодело\"  __________  А.В. Радионов");
				rowhead1.getCell(2).setCellStyle(style);
				
				rowhead1 = sheet.createRow(d1+1);
				rowhead1.createCell(2).setCellValue("\"__\" _________ 20__ г.");
				rowhead1.getCell(2).setCellStyle(style);
				
				sheet.addMergedRegion(new CellRangeAddress( d1, //first row (0-based)
			            d1, //last row  (0-based)
			            2, //first column (0-based)
			            5  //last column  (0-based)
			    ));
				
				sheet.addMergedRegion(new CellRangeAddress( d1+1, //first row (0-based)
			            d1+1, //last row  (0-based)
			            2, //first column (0-based)
			            4  //last column  (0-based)
			    ));*/
				
				//sheet.autoSizeColumn(0); 
			    sheet.autoSizeColumn(1);
			   sheet.autoSizeColumn(2);
			    sheet.autoSizeColumn(3);
			    //sheet.autoSizeColumn(4);


			    
			 
				
				FileOutputStream fileOut =  new FileOutputStream(filename);
				hwb.write(fileOut);
				fileOut.close();
				log.info("Табличный файл был создан успешно по пути: " + filename);
				javax.swing.JOptionPane.showMessageDialog(null, "Документ был создан успешно по пути: " + filename);

				} catch ( java.lang.IndexOutOfBoundsException ex ) {
				    ex.printStackTrace();
				    log.info(ex.getMessage());
				} catch (Exception exc) {
				
				}
		
	}

	public static void exelSet6(List<Avto> lst, List<Avto> lst2) {
		try{
			String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
			String filename="C:/docs/otchetTO_"+dt+".xls";
			//String filename="/home/rasp_"+dt+".xls";
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("List 1");
	
			
			HSSFCellStyle style = hwb.createCellStyle();
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			
			
			HSSFFont font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style.setFont(font);
			
			HSSFRow rowhead1 = sheet.createRow(1);
			rowhead1.createCell(2).setCellValue("Отчет о контроле");
			rowhead1.getCell(2).setCellStyle(style);
			
			HSSFRow rowhead5 = sheet.createRow(2);
			rowhead5.createCell(2).setCellValue("технического обслуживания автомобилей");
			rowhead5.getCell(2).setCellStyle(style);
			
			
			rowhead5 = sheet.createRow(6);
			rowhead5.createCell(2).setCellValue("Автомобили, которые должны пройти ТО в ближайший месяц");
			rowhead5.getCell(2).setCellStyle(style);
			
			
			sheet.addMergedRegion(new CellRangeAddress( 1, //first row (0-based)
		            1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 2, //first row (0-based)
		            2, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 3, //first row (0-based)
		            3, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 6, //first row (0-based)
		            6, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			HSSFCellStyle style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 2); // double lines border
			style1.setBorderBottom((short) 2); // single line border
			style1.setBorderLeft((short)2);
			style1.setBorderRight((short)2);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style1.setFont(font);
			
			HSSFRow rowhead = sheet.createRow(8);
			rowhead.createCell(1).setCellValue("№");
			rowhead.getCell(1).setCellStyle(style1);
			rowhead.createCell(2).setCellValue("Модель автомобиля");
			rowhead.getCell(2).setCellStyle(style1);
			rowhead.createCell(3).setCellValue("Гос. номер");
			rowhead.getCell(3).setCellStyle(style1);
			rowhead.createCell(4).setCellValue("Дата прохождения ТО");
			rowhead.getCell(4).setCellStyle(style1);
			
			
			style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setFont(font);
			
			for(int i=0; i<lst.size(); i++) {
				Avto bts = lst.get(i);
				HSSFRow row =   sheet.createRow(i+10);
				
				row.createCell(1).setCellValue(i+1);
				row.getCell(1).setCellStyle(style1);
				row.createCell(2).setCellValue(bts.getModel());
				row.getCell(2).setCellStyle(style1);
				row.createCell(3).setCellValue(bts.getStatenumber());
				row.getCell(3).setCellStyle(style1);
				row.createCell(4).setCellValue(dateFormat.format(bts.getDatanextto()));
				row.getCell(4).setCellStyle(style1);
				
			
			}
			int d1 = lst.size()+12;
			rowhead1 = sheet.createRow(d1);
			rowhead1.createCell(2).setCellValue("Автомобили, которые не проходят ТО в ближайший месяц");
			rowhead1.getCell(2).setCellStyle(style);
			
			sheet.addMergedRegion(new CellRangeAddress( d1, //first row (0-based)
		            d1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			int d2 = d1+1;
			rowhead1 = sheet.createRow(d2);
			rowhead.createCell(1).setCellValue("№");
			rowhead.getCell(1).setCellStyle(style1);
			rowhead.createCell(2).setCellValue("Модель автомобиля");
			rowhead.getCell(2).setCellStyle(style1);
			rowhead.createCell(3).setCellValue("Гос. номер");
			rowhead.getCell(3).setCellStyle(style1);
			rowhead.createCell(4).setCellValue("Дата прохождения ТО");
			rowhead.getCell(4).setCellStyle(style1);
			
			int d3 = d2+1;
			for(int i=0; i<lst2.size(); i++) {
				Avto bts = lst2.get(i);
				HSSFRow row =   sheet.createRow(i+d3);
				
				row.createCell(1).setCellValue(i+1);
				row.getCell(1).setCellStyle(style1);
				row.createCell(2).setCellValue(bts.getModel());
				row.getCell(2).setCellStyle(style1);
				row.createCell(3).setCellValue(bts.getStatenumber());
				row.getCell(3).setCellStyle(style1);
				row.createCell(4).setCellValue(dateFormat.format(bts.getDatanextto()));
				row.getCell(4).setCellStyle(style1);
				
			
			}
			int d4 = lst2.size()+(d3+1);
			rowhead1 = sheet.createRow(d4);
			rowhead1.createCell(2).setCellValue("Заведующий отделением \"Автодело\"  __________  А.В. Радионов");
			rowhead1.getCell(2).setCellStyle(style);
			
			rowhead1 = sheet.createRow(d4+1);
			rowhead1.createCell(2).setCellValue("\"__\" _________ 20__ г.");
			rowhead1.getCell(2).setCellStyle(style);
			
			//sheet.autoSizeColumn(0); 
			
			sheet.addMergedRegion(new CellRangeAddress( d4, //first row (0-based)
					d4, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( d4+1, //first row (0-based)
					d4+1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
		    sheet.autoSizeColumn(1);
		    sheet.setColumnWidth(2,9000);
		    sheet.setColumnWidth(3,5300);
		    sheet.autoSizeColumn(4);


		    
		 
			
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			log.info("Табличный файл был создан успешно по пути: " + filename);
			javax.swing.JOptionPane.showMessageDialog(null, "Документ был создан успешно по пути: " + filename);

			} catch ( java.lang.IndexOutOfBoundsException ex ) {
			    ex.printStackTrace();
			    log.info(ex.getMessage());
			} catch (Exception exc) {
			
			}
		
	}

	public static void exelSet7(List<Students> lst) {
		try{
			String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
			String filename="C:/docs/reestr_"+dt+".xls";
			//filename="/home/reestr_"+dt+".xls";
			
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("List 1");
	
			
			HSSFCellStyle style = hwb.createCellStyle();
			//style.setBorderTop((short) 6); // double lines border
			//style.setBorderBottom((short) 1); // single line border
			//style.setBorderLeft((short)2);
			//style.setBorderRight((short)2);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			
			
			HSSFFont font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style.setFont(font);
			
			HSSFRow rowhead1 = sheet.createRow(1);
			rowhead1.createCell(2).setCellValue("РЕЕСТР");
			rowhead1.getCell(2).setCellStyle(style);
			
			HSSFRow rowhead5 = sheet.createRow(2);
			rowhead5.createCell(2).setCellValue("выдачи свидетельств об окончании ГБОУ \"МУК\" ");
			rowhead5.getCell(2).setCellStyle(style);
			
			
			
			
			sheet.addMergedRegion(new CellRangeAddress( 1, //first row (0-based)
		            1, //last row  (0-based)
		            2, //first column (0-based)
		            5  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 2, //first row (0-based)
		            2, //last row  (0-based)
		            2, //first column (0-based)
		            5  //last column  (0-based)
		    ));
			
			
			HSSFCellStyle style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 2); // double lines border
			style1.setBorderBottom((short) 2); // single line border
			style1.setBorderLeft((short)2);
			style1.setBorderRight((short)2);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style1.setFont(font);
			
			HSSFRow rowhead = sheet.createRow(5);
			rowhead.createCell(1).setCellValue("№");
			rowhead.getCell(1).setCellStyle(style1);
			rowhead.createCell(2).setCellValue("Ф.И.О. учащегося");
			rowhead.getCell(2).setCellStyle(style1);
			rowhead.createCell(3).setCellValue("Группа");
			rowhead.getCell(3).setCellStyle(style1);
			rowhead.createCell(4).setCellValue("Свидетельство");
			rowhead.getCell(4).setCellStyle(style1);
			
			style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setFont(font);
			
			for(int i=0; i<lst.size(); i++) {
				Students bts = lst.get(i);
				HSSFRow row=   sheet.createRow(i+6);
				
				row.createCell(1).setCellValue(i+1);
				row.getCell(1).setCellStyle(style1);
				row.createCell(2).setCellValue(bts.getSurname() + " " + bts.getName() + " " + bts.getPatronymicname());
				row.getCell(2).setCellStyle(style1);
				row.createCell(3).setCellValue(bts.getGroups().getGroupName() + " "+ bts.getGroups().getYearOfEducation().getNumberOfYear()+" г.");
				row.getCell(3).setCellStyle(style1);
				row.createCell(4).setCellValue(bts.getSvidInfo());
				row.getCell(4).setCellStyle(style1);
				
			
			}
			int d1 = lst.size()+7;
			rowhead1 = sheet.createRow(d1);
			rowhead1.createCell(2).setCellValue("Заместитель директора по ПР  __________  И.В.Шестакова");
			rowhead1.getCell(2).setCellStyle(style);
			
			rowhead1 = sheet.createRow(d1+1);
			rowhead1.createCell(2).setCellValue("\"__\" _________ 20__ г.");
			rowhead1.getCell(2).setCellStyle(style);
			
			sheet.addMergedRegion(new CellRangeAddress( d1, //first row (0-based)
		            d1, //last row  (0-based)
		            2, //first column (0-based)
		            5  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( d1+1, //first row (0-based)
		            d1+1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.autoSizeColumn(0); 
		    sheet.autoSizeColumn(1);
		    sheet.autoSizeColumn(2);
		    sheet.autoSizeColumn(3);
		    sheet.autoSizeColumn(4);
		   

		    
		 
			
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			log.info("Табличный файл был создан успешно по пути: " + filename);
			javax.swing.JOptionPane.showMessageDialog(null, "Табличный файл был создан успешно по пути: " + filename);

			} catch ( java.lang.IndexOutOfBoundsException ex ) {
			    ex.printStackTrace();
			    log.info(ex.getMessage());
			} catch (Exception exc) {
			
			}
		
	}
	
	public static void exelSet8(List<Students> lst) {
		try{
			System.out.println(lst.size());
			String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
			String filename="C:/docs/protocol_"+dt+".xls";
			//filename="/home/reestr_"+dt+".xls";
			
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet("List 1");
	
			
			HSSFCellStyle style = hwb.createCellStyle();
			//style.setBorderTop((short) 6); // double lines border
			//style.setBorderBottom((short) 1); // single line border
			//style.setBorderLeft((short)2);
			//style.setBorderRight((short)2);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.WHITE.index);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			
			
			HSSFFont font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style.setFont(font);
			
			HSSFRow rowhead1 = sheet.createRow(1);
			rowhead1.createCell(2).setCellValue("Протокол № ______");
			rowhead1.getCell(2).setCellStyle(style);
			
			HSSFRow rowhead5 = sheet.createRow(2);
			rowhead5.createCell(2).setCellValue("Экзаменационная комиссия в составе: ");
			rowhead5.getCell(2).setCellStyle(style);
			
			rowhead5 = sheet.createRow(3);
			rowhead5.createCell(2).setCellValue("Председатель экзаменационной комиссии: А.В. Радионов ");
			rowhead5.getCell(2).setCellStyle(style);
			
			rowhead5 = sheet.createRow(4);
			rowhead5.createCell(2).setCellValue("Члены комиссии: _____________________________________ ");
			rowhead5.getCell(2).setCellStyle(style);
			
			rowhead5 = sheet.createRow(5);
			rowhead5.createCell(2).setCellValue("                  _____________________________________ ");
			rowhead5.getCell(2).setCellStyle(style);
			
			rowhead5 = sheet.createRow(6);
			rowhead5.createCell(2).setCellValue("Приняла выпускные экзамены у граждан, подготовленных по специальности \"Водитель ТС категории B\" ");
			rowhead5.getCell(2).setCellStyle(style);
			
			
			sheet.addMergedRegion(new CellRangeAddress( 1, //first row (0-based)
		            1, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( 2, //first row (0-based)
		            2, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 3, //first row (0-based)
		            3, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 4, //first row (0-based)
		            4, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 5, //first row (0-based)
		            5, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( 6, //first row (0-based)
		            6, //last row  (0-based)
		            2, //first column (0-based)
		            7  //last column  (0-based)
		    ));
			
			
			HSSFCellStyle style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 2); // double lines border
			style1.setBorderBottom((short) 2); // single line border
			style1.setBorderLeft((short)2);
			style1.setBorderRight((short)2);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			font = hwb.createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 14);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			//font.setColor(HSSFColor.ROYAL_BLUE.index);
			style1.setFont(font);
			style1.setWrapText(true);
			
			HSSFRow rowhead = sheet.createRow(8);
			rowhead.createCell(1).setCellValue("№");
			rowhead.getCell(1).setCellStyle(style1);
			rowhead.createCell(2).setCellValue("Ф.И.О. учащегося");
			rowhead.getCell(2).setCellStyle(style1);
			rowhead.createCell(3).setCellValue("Основы законодательства в сфере дорожного движения");
			rowhead.getCell(3).setCellStyle(style1);
			rowhead.createCell(4).setCellValue("Основы безопасного управления ТС");
			rowhead.getCell(4).setCellStyle(style1);
			rowhead.createCell(5).setCellValue("Вождение");
			rowhead.getCell(5).setCellStyle(style1);
			rowhead.createCell(6).setCellValue("Свидетельство");
			rowhead.getCell(6).setCellStyle(style1);
			rowhead.createCell(7).setCellValue("Роспись учащегося в получении свидетельства");
			rowhead.getCell(7).setCellStyle(style1);
			
			style1 = hwb.createCellStyle();
			style1.setBorderTop((short) 1); // double lines border
			style1.setBorderBottom((short) 1); // single line border
			style1.setBorderLeft((short)1);
			style1.setBorderRight((short)1);
			style1.setAlignment(CellStyle.ALIGN_CENTER);
			style1.setFont(font);
			int b=1;
			int r =1;
			for(int i=0; i<lst.size(); i++) {
				Students bts = lst.get(i);
				HSSFRow row=   sheet.createRow(i+9);
				
				row.createCell(1).setCellValue(i+1);
				row.getCell(1).setCellStyle(style1);
				row.createCell(2).setCellValue(bts.getSurname() + " " + bts.getName() + " " + bts.getPatronymicname());
				row.getCell(2).setCellStyle(style1);
				row.createCell(3).setCellValue("");
				row.getCell(3).setCellStyle(style1);
				row.createCell(4).setCellValue("");
				row.getCell(4).setCellStyle(style1);
				row.createCell(5).setCellValue("");
				row.getCell(5).setCellStyle(style1);
				row.createCell(7).setCellValue("");
				row.getCell(7).setCellStyle(style1);
				//if(bts.getSvidInfo().equals("")){
				//} else{
					
					row.createCell(6).setCellValue(bts.getSvidInfo());
					row.getCell(6).setCellStyle(style1);
					r++;
				//}
				
				b++;
			
			}
			int d1 = lst.size()+10;
			rowhead1 = sheet.createRow(d1);
			rowhead1.createCell(2).setCellValue("Всего обучалось " + b + " человек");
			rowhead1.getCell(2).setCellStyle(style);
			
			rowhead1 = sheet.createRow(d1+1);
			rowhead1.createCell(2).setCellValue("Сдали экзамены ____ человек");
			rowhead1.getCell(2).setCellStyle(style);
			
			rowhead1 = sheet.createRow(d1+2);
			rowhead1.createCell(2).setCellValue("Директор ГБОУ \"МУК\"_____________ Л.А.Банька");
			rowhead1.getCell(2).setCellStyle(style);
			
			rowhead1 = sheet.createRow(d1+3);
			rowhead1.createCell(2).setCellValue("\"___\" _____________ 20__ г.");
			rowhead1.getCell(2).setCellStyle(style);
			
			sheet.addMergedRegion(new CellRangeAddress( d1, //first row (0-based)
		            d1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			sheet.addMergedRegion(new CellRangeAddress( d1+1, //first row (0-based)
		            d1+1, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( d1+2, //first row (0-based)
		            d1+2, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			sheet.addMergedRegion(new CellRangeAddress( d1+3, //first row (0-based)
		            d1+3, //last row  (0-based)
		            2, //first column (0-based)
		            4  //last column  (0-based)
		    ));
			
			
			sheet.autoSizeColumn(0); 
		    sheet.autoSizeColumn(1);
		    sheet.autoSizeColumn(2);
		    //sheet.autoSizeColumn(3);
		    //sheet.autoSizeColumn(4);
		    sheet.autoSizeColumn(5);
		    sheet.autoSizeColumn(6);
		    //sheet.autoSizeColumn(7);
		    sheet.setColumnWidth(3,6000);
		    sheet.setColumnWidth(7,6300);
		    sheet.setColumnWidth(4,5300);
		    
		 
			
			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			log.info("Табличный файл был создан успешно по пути: " + filename);
			javax.swing.JOptionPane.showMessageDialog(null, "Табличный файл был создан успешно по пути: " + filename);

			} catch ( java.lang.IndexOutOfBoundsException ex ) {
			    ex.printStackTrace();
			    log.info(ex.getMessage());
			} catch (Exception exc) {
			
			}
		
	}
	
	
 
}
