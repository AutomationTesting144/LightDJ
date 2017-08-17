package com.example.a310287808.lightdj;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

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
 * Created by 310287808 on 8/15/2017.
 */

public class LightDeletionHue {
    MobileElement listItem;
    public int lightCounter=0;
    public String ActualResult;
    public String ExpectedResult;
    public String Status;
    public String Comments;
    public String newString1;
    public String LightNameBefore;
    public String LightNameAfter;
    Dimension size;

    public void LightDeletionHue (AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException  {

        driver.navigate().back();
        driver.navigate().back();

        //Opening Hue applictaion
        driver.findElement(By.xpath("//android.widget.TextView[@text='Hue']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on settings button
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1218,322][1302,406]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Selecting light setup
        driver.findElement(By.xpath("//android.widget.TextView[@text='Light setup']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Choosing light to remane
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[1230,679][1440,889]']")).click();

        //editing the name
        driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).click();
        LightNameBefore=driver.findElement(By.id("com.philips.lighting.hue2:id/form_field_text")).getText();
        driver.hideKeyboard();


        //Clicking on delete button
        driver.findElement(By.id("com.philips.lighting.hue2:id/details_delete_device_button")).click();
        TimeUnit.SECONDS.sleep(2);

        //Confirming the message
        driver.findElement(By.id("android:id/button1")).click();
        TimeUnit.SECONDS.sleep(2);

        //Going back from the application
        driver.navigate().back();
        driver.navigate().back();
        driver.navigate().back();
        System.out.println("Waiting for 5 minutes to update the changes ");
        TimeUnit.MINUTES.sleep(1);

        //opening Light DJ
        driver.findElement(By.xpath("//android.widget.TextView[@text='Light DJ']")).click();
        TimeUnit.SECONDS.sleep(2);

        //clicking bulb icon
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Light Selection']")).click();
        TimeUnit.SECONDS.sleep(2);
        driver.navigate().back();
        driver.navigate().back();
        System.out.println("Waiting for 5 minutes to update the changes ");
        TimeUnit.MINUTES.sleep(5);

        //opening recent applications
        driver.pressKeyCode(187);
        TimeUnit.SECONDS.sleep(2);

        //Get the size of screen.
        size = driver.manage().window().getSize();

        //Find swipe start and end point from screen's with and height.
        //Find starty point which is at bottom side of screen.
        int starty = (int) (size.height * 0.80);
        //Find endy point which is at top side of screen.
        int endy = (int) (size.height * 0.20);
        //Find horizontal point where you wants to swipe. It is in middle of screen width.
        int startx = size.width / 2;

        //Swipe from Top to Bottom.
        driver.swipe(startx, endy, startx, starty, 3000);
        Thread.sleep(2000);

        //Clearing all the recent applications
        driver.findElement(By.xpath("//android.widget.TextView[@text='CLEAR ALL']")).click();
        TimeUnit.SECONDS.sleep(2);


        //opening Light DJ
        driver.findElement(By.xpath("//android.widget.TextView[@text='Light DJ']")).click();
        TimeUnit.SECONDS.sleep(2);

        //clicking bulb icon
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Light Selection']")).click();
        TimeUnit.SECONDS.sleep(2);

        List dropList = driver.findElements(By.id("com.lightdjapp.lightdj.demo:id/lightName"));
        StringBuffer sb = new StringBuffer();
        for(int i=0; i< dropList.size(); i++){
            listItem = (MobileElement) dropList.get(i);
            System.out.println("List Elements: "+listItem.getText());
            Boolean result=listItem.getText().equals(LightNameBefore);
            System.out.println("Result is: "+result);
            if (result==true){
                lightCounter++;

                sb.append(LightNameBefore);

                sb.append("\n");

            }
            else{
                continue;
            }
        }
        if (lightCounter==0)

        {
            Status = "1";
            ActualResult = "Light " + LightNameBefore + " is deleted from Hue and Light DJ";
            Comments = "NA";
            ExpectedResult= "Light " + LightNameBefore + " should be deleted from Hue and Light DJ";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        } else {
            Status = "0";
            ActualResult = "Light " + LightNameBefore + " is deleted from Hue but not from Light DJ";
            Comments = "Light Status of " + LightNameBefore + " is : " + newString1;
            ExpectedResult= "Light " + LightNameBefore + " should be deleted from Hue and Light DJ";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }

        driver.navigate().back();
        driver.navigate().back();
        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);
    }

    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel (String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult, String resultAPIVersion, String resultSWVersion) throws IOException {
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
        r2c2.setCellValue("11");

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

