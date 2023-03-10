

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class MainDriver {

	public static void main(String[] args) {
		class Local {};
		String methodName = Local.class.getEnclosingMethod().getName();
				
		// read input file from Excel sheet		
		File inputFile = new File(".\\TestData\\TestData.xlsx");
		try {
			XSSFWorkbook workbook;
			XSSFSheet sMain, sTS;
			XSSFRow rMain, rTS;
			FileInputStream fileIn = new FileInputStream(inputFile);
			
			workbook = new XSSFWorkbook(fileIn);
			sMain = workbook.getSheet("Main");
			int rowCountMain = sMain.getLastRowNum();
			Method method = null;
			boolean testCasePassed = false;
					 
			// for each line in Main sheet have a Yes to run
			for (int i=1;i<=rowCountMain;i++) {
				try {
					rMain = sMain.getRow(i);
					rMain.getCell(0);
				} catch (Exception e) {
					break;
				}				
				
				boolean runTestSuite = false;
				if ((rMain.getCell(0) != null) && (rMain.getCell(0).toString().equalsIgnoreCase("y"))) 
					runTestSuite = true;
								
				// read test suite info and environment info if set to Run
				if ((rMain != null) && (runTestSuite)) {					
					// read Json Data
					JSONParser parserMain = new JSONParser();
					JSONObject jsonMain = new JSONObject();					
					jsonMain = (JSONObject) parserMain.parse(rMain.getCell(1).toString());			 		
					if (Global.checkPointOn) System.out.println(methodName + "> run TestSuiteName: " + jsonMain.get("TestSuiteName").toString());
					if (Global.checkPointOn) System.out.println(methodName + "> EnvName: " + jsonMain.get("EnvName").toString());
					
					sTS = workbook.getSheet(jsonMain.get("TestSuiteName").toString());
					jsonMain.put("OutputFileName", jsonMain.get("TestSuiteName").toString() + "." + jsonMain.get("EnvName").toString() + ".TestResult." + Utilities.GetTimeStamp(0) + ".xlsx");
					if (Global.checkPointOn) System.out.println(methodName + "> OutputFileName: " + jsonMain.get("OutputFileName").toString());
					
					int rowCountTS = sTS.getLastRowNum();					
					for (int j=1;j<=rowCountTS;j++) {						
						try {
							rTS = sTS.getRow(j);
							rTS.getCell(0);
						} catch (Exception e) {
							break;
						}
						boolean runTestCase = false;
						if ((rTS.getCell(0) != null) && (rTS.getCell(0).toString().trim().equalsIgnoreCase("y")))
							runTestCase = true;
						
						// read test suite info and environment info if set to Run
						if ((rTS != null) && (runTestCase)) {							
							// read Json Data
							JSONParser parserTS = new JSONParser();
							JSONObject json = new JSONObject();					
							json = (JSONObject) parserTS.parse(rTS.getCell(1).toString());
							json.putAll(jsonMain);
							if (Global.checkPointOn) System.out.println(methodName + "> run TestCaseName: " + json.get("TestCaseName").toString());
							if (Global.checkPointOn) System.out.println(methodName + "> TestCaseDesc: " + json.get("TestCaseDesc").toString());

							switch (json.get("TestSuiteName").toString().toLowerCase()) {
							case "regression": //run regression test suite
								try {
									method = RegressionTestCase.class.getMethod(json.get("TestCaseName").toString().trim(), JSONObject.class);
									if (Global.checkPointOn) System.out.println(methodName + "> Test case found in Regression repository");
									testCasePassed = (Boolean) method.invoke(RegressionTestCase.class, json);
									if (testCasePassed)
										System.out.println("\n" + methodName + ">" + json.get("TestCaseName").toString() + "> Passed \n");
									else
										System.err.println("\n" + methodName + ">" + json.get("TestCaseName").toString() + "> Failed \n");
											
								} catch (Exception e) {
									System.err.println(methodName + "=>>>" + Global.errMsg);
									testCasePassed = false;
								}
								break;
							default: 
								// error found: test suite name is invalid
							}
							
							// save status
							json.put("IsStatusPassed",testCasePassed);
							Report.SaveTestCaseStatus(json);
							
							if (Global.checkPointOn) System.out.println("============================================================================================");
						}						
					}
				} // end of for Test Suite Sheet loop					
			}// end of for Main Sheet loop			
		} catch (Exception e) {
			if (Global.checkPointOn) System.err.println(methodName+ "> Error found: " + e.toString());
		}
	}

}
