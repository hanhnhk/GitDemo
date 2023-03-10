

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;

public class Report {
	private static void CreateOutputFileRegression(JSONObject json) {
		//		private static void CreateOutputFileRegression(String TestSuiteName, File fileOut) {
		class Local {};
		String methodName = Local.class.getEnclosingMethod().getName();

		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		XSSFRow row = null;
		FileOutputStream outFile = null;
		File outputFile = new File(Global.PathToTestResult + json.get("OutputFileName").toString());
		
		try {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet(json.get("TestSuiteName").toString());
			row = sheet.createRow(0);
			if (Global.checkPointOn) System.out.println(methodName + "> Output file sheet created");

			row.createCell(Global.StatusCol).setCellValue("Status");
			row.createCell(Global.StepCol).setCellValue("Step");
			row.createCell(Global.DescriptionCol).setCellValue("Description");
			row.createCell(Global.BrowserTypeCol).setCellValue("BrowserType");
			row.createCell(Global.EnvironmentCol).setCellValue("Environment");
			row.createCell(Global.TimerSecCol).setCellValue("Timer(Sec)");
			row.createCell(Global.TimeStampCol).setCellValue("TimeStamp");
			if (Global.checkPointOn) System.out.println(methodName + "> Output file columns created");

			outFile = new FileOutputStream(outputFile);
			workbook.write(outFile);
			outFile.close();
			if (Global.checkPointOn) System.out.println(methodName + "> Output file saved");
		} catch (Exception e) {
			Global.errMsg = methodName + "> Exception error ";
			if (Global.checkPointOn) System.err.println(Global.errMsg);
		}

		row = null;
		sheet = null;
		workbook = null;		
		outFile = null;
	}
	public static void SaveTestCaseStatus(JSONObject json) {
		class Local {};
		String methodName = Local.class.getEnclosingMethod().getName();
		
		FileOutputStream outFile = null;
		FileInputStream inFile = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		int sheetRow = 0;	
		XSSFRow row = null; 
		
		File fileIn = new File (Global.PathToTestResult + json.get("OutputFileName").toString());
		if (Global.checkPointOn) System.out.println(methodName + "> set OutputFileName: " + json.get("OutputFileName").toString());
		
		if (!fileIn.exists()) { 
			if (Global.checkPointOn) System.out.println(methodName + "> creating OutputFile");
			CreateOutputFileRegression(json);
		}
		
		try {
			inFile = new FileInputStream(fileIn);
			if (Global.checkPointOn) System.out.println(methodName + "> open OutputFile");
		} catch (FileNotFoundException e) {
			Global.errMsg = methodName + "> cannot read output file";
			if (Global.checkPointOn) System.err.println(Global.errMsg );
		}
		
		try {
			workbook = new XSSFWorkbook(inFile);
			if (Global.checkPointOn) System.out.println(methodName + "> open worksheet");
		} catch (Exception e) {
			Global.errMsg = methodName + "> cannot read workbook";
			if (Global.checkPointOn) System.err.println(Global.errMsg );
		}

		sheet = workbook.getSheet(json.get("TestSuiteName").toString());

		sheetRow = sheet.getLastRowNum() + 1;	
		row = sheet.createRow(sheetRow);

		// Status
		if ((boolean)json.get("IsStatusPassed"))
			row.createCell(Global.StatusCol).setCellValue("Passed");
		else
			row.createCell(Global.StatusCol).setCellValue("Failed: " + Global.errMsg);
		if (Global.checkPointOn) System.out.println(methodName + "> Status done");
		
		// Step
		if (json.get("Step") ==  null)
			row.createCell(Global.StepCol).setCellValue("000");
		else 
			row.createCell(Global.StepCol).setCellValue(json.get("Step").toString());
		if (Global.checkPointOn) System.out.println(methodName + "> Step done");
		
		// Description
		if (json.get("TestCaseDesc") ==  null)
			row.createCell(Global.DescriptionCol).setCellValue("");
		else 
			row.createCell(Global.DescriptionCol).setCellValue(json.get("TestCaseDesc").toString());
		if (Global.checkPointOn) System.out.println(methodName + "> Description done");
		
		// BrowserType
		if (json.get("BrowserType") ==  null)
			row.createCell(Global.BrowserTypeCol).setCellValue("");
		else 
			row.createCell(Global.BrowserTypeCol).setCellValue(json.get("BrowserType").toString());
		if (Global.checkPointOn) System.out.println(methodName + "> BrowserType done");
		
		// Environment
		if (json.get("EnvName") ==  null)
			row.createCell(Global.EnvironmentCol).setCellValue("");
		else 
			row.createCell(Global.EnvironmentCol).setCellValue(json.get("EnvName").toString());
		if (Global.checkPointOn) System.out.println(methodName + "> Environment done");
		
		// TimerSecCol
		if (json.get("Timer") ==  null)
			row.createCell(Global.TimerSecCol).setCellValue("");
		else 
			row.createCell(Global.TimerSecCol).setCellValue(json.get("Timer").toString());
		if (Global.checkPointOn) System.out.println(methodName + "> Timer done");
		
		// TimeStamp
		if (json.get("TimeStamp") ==  null)
			row.createCell(Global.TimeStampCol).setCellValue("");
		else 
			row.createCell(Global.TimeStampCol).setCellValue(json.get("TimeStamp").toString());
		if (Global.checkPointOn) System.out.println(methodName + "> TimeStamp done");
		
		try {
			Thread.sleep(Global.SleepTimeMid);
			if (inFile != null)
				try {
					inFile.close();
				} catch (Exception e1) {
					Global.errMsg = methodName + "> cannot close input file";
					if (Global.checkPointOn) System.err.println(Global.errMsg );
				}
			
			try {
				outFile = new FileOutputStream(fileIn);
			} catch (Exception e) {
				Global.errMsg = methodName + "> cannot open output file";
				if (Global.checkPointOn) System.err.println(Global.errMsg );
			}	

		} catch (Exception e) {			
			Global.errMsg = methodName + "> " + e.toString();
			if (Global.checkPointOn) System.err.println(Global.errMsg );
		}
				
		try {
			workbook.write(outFile);
		}catch (Exception e) {
			try {
				workbook.write(outFile);
			} catch (Exception e2) {
				Global.errMsg = methodName + "> " + e2.toString();
				if (Global.checkPointOn) System.err.println(Global.errMsg );
			}
		}

		Global.errMsg = "";
		row = null;
		sheet = null;
		try {
			workbook.close();
		} catch (Exception e) {
			Global.errMsg = methodName + "> " + e.toString();
			if (Global.checkPointOn) System.err.println(Global.errMsg );
		}
		workbook = null;
		try {
			outFile.close();
		} catch (Exception e) {
			Global.errMsg = methodName + "> " + e.toString();
			if (Global.checkPointOn) System.err.println(Global.errMsg );
		}		
		outFile = null;		
	}
}
