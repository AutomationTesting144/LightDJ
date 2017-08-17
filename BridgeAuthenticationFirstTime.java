package com.example.a310287808.lightdj;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 8/14/2017.
 */

public class BridgeAuthenticationFirstTime {
    public String ActualResult;
    public String Comments;
    public String ExpectedResult;
    public String Status;
    public boolean ob3;
    public boolean ob4;

    public void BridgeAuthenticationFirstTime(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {
        //Checking the state of lights from API and turning them OFf if they are ON
        driver.navigate().back();
        driver.navigate().back();

        //Opening Harmony App
        driver.findElement(By.xpath("//android.widget.TextView[@text='Light DJ']")).click();
        TimeUnit.SECONDS.sleep(5);

        //Clicking on right side 3 dots
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1300,98][1440,266]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on configuration
        driver.findElement(By.xpath("//android.widget.TextView[@text='Configuration']")).click();
        TimeUnit.SECONDS.sleep(2);

        //clicking on Search for Hue
        driver.findElement(By.xpath("//android.widget.Button[@text='Search for Hue']")).click();
        TimeUnit.SECONDS.sleep(20);
//
//        ob4= driver.findElement(By.xpath("//android.widget.TextView[@text='Please select a bridge to use with this app:']")).isDisplayed();
//        System.out.println(ob4);
//        System.exit(0);
        //Locate all drop down list elements
//        WebElement baseElement = driver.findElement(By.className("android.widget.ListView"));
//
//        Actions clicker = new Actions(driver);
//
//        clicker.moveToElement(baseElement).moveByOffset(10, 20).click().perform();
//        System.exit(0);
        System.out.println("Clicking");

        List dropList = driver.findElements(By.id("android:id/content"));
        System.out.println("List count: "+dropList.size());
        //Extract text from each element of drop down list one by one.
        for(int i=0; i< dropList.size(); i++){
            MobileElement listItem = (MobileElement) dropList.get(i);
            System.out.println("Clicking");
            System.out.println(listItem.getText());
        }
        System.exit(0);



        //Choosing IP
        System.out.println("Clicking1");
        driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/linear1")).click();
        System.out.println("Clicking2");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Clicking3");



        //looking for pushlink
        ob3= driver.findElement(By.xpath("//android.widget.TextView[@text='Press the link button on the bridge']")).isDisplayed();

        if (ob3==true)

        {
            Status = "1";
            ActualResult = "Application is asking user to press bridge pushlink while connecting for first time";
            Comments = "NA";
            ExpectedResult= "Application should ask user to press bridge pushlink while connecting for first time";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        } else {
            Status = "0";
            ActualResult = "Application is not asking user to press bridge pushlink while connecting for first time";
            Comments = "FAIL: Bridge is already connected to the application";
            ExpectedResult= "Application should ask user to press bridge pushlink while connecting for first time";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }


        //going back
        driver.navigate().back();
        driver.navigate().back();
        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);

    }
    //WRITING THE RESULT IN EXCEL FILE
    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel (String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult, String resultAPIVersion, String resultSWVersion) throws IOException
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CurrentdateTime = sdf.format(cal.getTime());
        FileInputStream fsIP = new FileInputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        nextRowNumber = workbook.getSheetAt(0).getLastRowNum();
        nextRowNumber++;
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row2 = sheet.createRow(nextRowNumber);
        HSSFCell r2c1 = row2.createCell((short) 0);
        r2c1.setCellValue(CurrentdateTime);

        HSSFCell r2c2 = row2.createCell((short) 1);
        r2c2.setCellValue("1");

        HSSFCell r2c3 = row2.createCell((short) 2);
        r2c3.setCellValue(excelStatus);

        HSSFCell r2c4 = row2.createCell((short) 3);
        r2c4.setCellValue(excelActualResult);

        HSSFCell r2c5 = row2.createCell((short) 4);
        r2c5.setCellValue(excelComments);

        HSSFCell r2c6 = row2.createCell((short) 5);
        r2c6.setCellValue(resultAPIVersion);

        HSSFCell r2c7 = row2.createCell((short) 6);
        r2c7.setCellValue(resultSWVersion);

        fsIP.close();
        FileOutputStream out =new FileOutputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        workbook.write(out);
        out.close();

    }
}
