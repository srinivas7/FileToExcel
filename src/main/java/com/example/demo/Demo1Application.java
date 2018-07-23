package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Demo1Application {

	public static void main(String[] args) throws FileNotFoundException, InvocationTargetException {
		SpringApplication.run(Demo1Application.class, args);
		searchForName();
		
	}

	public static void searchForName() throws FileNotFoundException {
		
        String fileName = "xmldebugger.log";
        String line = null;
        int count = 0;
        String inputValue = ">1531121083199";
        int rowCount = 0;
        String status = "";
        String[] parts = null; 
        int currentIteration = 0;
        StringBuilder str = new StringBuilder(inputValue);
        System.out.println(str.substring(1));
        BigInteger  enteredId = new BigInteger(str.substring(1));
        
        System.out.println("Enter number of iterations: ");
        Scanner scanner = new Scanner(System.in);
        int iterations = scanner.nextInt();
        System.out.println("entered iterations are... " + iterations);
        
        
        try {
        	
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");  

            HSSFRow rowhead = sheet.createRow((short)rowCount);
            rowhead.createCell(0).setCellValue("phrase");
            rowhead.createCell(1).setCellValue("count");
            rowhead.createCell(3).setCellValue("Message");
            rowhead.createCell(2).setCellValue("Status");
            do {	
            	fileReader =  new FileReader(fileName);
            	bufferedReader = new BufferedReader(fileReader);
            	System.out.println(bufferedReader.readLine());
            	while((line = bufferedReader.readLine()) != null) {
                    
                    if(line.contains(inputValue)) {
                    	count++;
                    	if(!line.contains("type=\"error\"") && count == 6) {
                    		status = "success";
                    	}else {
                    		status = "fail";
                    	}
                    
                    	Pattern pattern = Pattern.compile("<(.*?)>");
                    	Matcher matcher = pattern.matcher(line);
                    	parts = line.split(": "); 
                    }
                }   
            	
            	
            	 HSSFRow firstRow = sheet.createRow((short) ++rowCount);
                 firstRow.createCell(0).setCellValue(inputValue);
                 firstRow.createCell(1).setCellValue(count);
                 firstRow.createCell(3).setCellValue(parts[1]); //printing count instead of message as getting many messages
                 firstRow.createCell(2).setCellValue(status);
                 
                 currentIteration++;
                 enteredId = enteredId.add(BigInteger.ONE);
             	 inputValue = ">"+enteredId;
             	
                 for(int i =0 ; i< count; i++) {
                 	HSSFRow row = sheet.createRow((short) ++rowCount);
                 	row.createCell(3).setCellValue(parts[1]);
                 	firstRow.createCell(3).setCellValue(parts[1]); 
                 } 
                 count = 0;
            }while(currentIteration < iterations);
               
                
            
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
        catch (Exception ex) {
        	System.out.println("cant create more than 256 columns or something went wrong"+ ex);
        }
    }
}
