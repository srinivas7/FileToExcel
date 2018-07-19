package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(Demo1Application.class, args);
		searchForName();
	}

	public static void searchForName() throws FileNotFoundException {
		
        String fileName = "xmldebugger.log";
        String line = null;
        int count = 0;
        String inputValue = "55091";
        int rowCount = 0;
        try {
            FileReader fileReader = new FileReader(fileName);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");  

            HSSFRow rowhead = sheet.createRow((short)rowCount);
            rowhead.createCell(0).setCellValue("phrase");
            rowhead.createCell(1).setCellValue("count");
            
            do {
            	//System.out.println("helloo....");
            	while((line = bufferedReader.readLine()) != null) {
                    //System.out.println(line);
                    
                    if(line.contains(inputValue)) {
                    	count++;
                    	//System.out.println("yessss......"+ count);
                    }
                }   
                

                HSSFRow rowhead1 = sheet.createRow((short) ++rowCount);
                rowhead1.createCell(0).setCellValue(inputValue);
                rowhead1.createCell(1).setCellValue(count);
               //System.out.println(count);
                for(int i =0 ; i< count; i++) {
                	HSSFCell cell = rowhead1.createCell(i+2);
                	//System.out.println(cell);
                	cell.setCellValue(inputValue);
                }
                int num = Integer.parseInt(inputValue);
                num = num+1;
                inputValue = num + "";
                //System.out.println(num+"sfasfasd"+inputValue);
            }while(Integer.parseInt(inputValue) % 10 != 0);
            
            
            File excelFile = new File("abc.xls");
            FileOutputStream fileOut = new FileOutputStream(excelFile);
            workbook.write(fileOut);
            fileOut.close();
            //workbook.clone();
            
            System.out.println("Your excel file has been generated!");
            
            
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(ex);                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
    }
}
