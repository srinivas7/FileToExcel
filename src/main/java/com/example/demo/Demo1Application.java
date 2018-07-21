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
        ArrayList list = new ArrayList();
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
        	
            FileReader fileReader = new FileReader(fileName);
            
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("FirstSheet");  

            HSSFRow rowhead = sheet.createRow((short)rowCount);
            rowhead.createCell(0).setCellValue("phrase");
            rowhead.createCell(1).setCellValue("count");
            rowhead.createCell(3).setCellValue("Message");
            rowhead.createCell(2).setCellValue("Status");
            do {	
            	while((line = bufferedReader.readLine()) != null) {
                    //System.out.println(line);
                    
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
                     	System.out.println(parts[1]);
                     	
                    	while (matcher.find()) {
                    	    //System.out.println("tag is.."+matcher.group(1));
                    		//System.out.println(matcher);
                    		
                    		//System.out.println("line is ..."+line);
                    		list.add(line);
                    	}
                    }
                }   
            	currentIteration++;
            	enteredId = enteredId.add(BigInteger.ONE);
            	inputValue = ">"+enteredId;
            	System.out.println(currentIteration+ "..."+inputValue);
            	
            	 HSSFRow firstRow = sheet.createRow((short) ++rowCount);
                 firstRow.createCell(0).setCellValue(inputValue);
                 firstRow.createCell(1).setCellValue(count);
                 firstRow.createCell(3).setCellValue(parts[1]); //printing count instead of message as getting many messages
                 firstRow.createCell(2).setCellValue(status);
                //System.out.println(count);
                 for(int i =0 ; i< count; i++) {
                 	HSSFRow row = sheet.createRow((short) ++rowCount);
                 	//System.out.println(line);
                 	row.createCell(3).setCellValue(parts[1]);
                 	firstRow.createCell(3).setCellValue(parts[1]); 
                 } 
                 
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
