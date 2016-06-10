import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class WriteExcelFile {
    //We are making use of a single instance to prevent multiple write access to same file.
    private static final WriteExcelFile INSTANCE = new WriteExcelFile();

    public static WriteExcelFile getInstance() {
        return INSTANCE;
    }

    WriteExcelFile() {
    }

    public static void writeStudentsListToExcel(Map<String, String> map){


        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
    	
        //configure the cell styles ==============================
    	CellStyle style = workbook.createCellStyle();
        style.setBorderTop((short) 6); // double lines border
        style.setBorderBottom((short) 1); // single line border
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 15);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(font);
    	

        Sheet sheet = workbook.createSheet("Results" + new Date().toString().replace(":", " "));

        int rowIndex1 = 0;
        Row row1 = sheet.createRow(rowIndex1);
        row1.createCell(0).setCellValue("Test Name");
        
        row1.createCell(1).setCellValue("Result");
        row1.setRowStyle(style);
        row1.getCell(0).setCellStyle(style);
        row1.getCell(1).setCellStyle(style);
        
        int rowIndex = 1;
        for(Map.Entry<String, String> it : map.entrySet()){
            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            //first place in row
            row.createCell(cellIndex++).setCellValue(it.getKey());

            //second place in row
            row.createCell(cellIndex++).setCellValue(it.getValue());

        }
        
        //set auto size to fit the data to the cell ===================
        sheet.autoSizeColumn(0);
        //write this workbook in excel file.
        try {
        	File file = new File((new Date().toString()).replace(":", " ")+" results "+".xlsx");
        	file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);
            fos.close();

            System.out.println("File is successfully written");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
