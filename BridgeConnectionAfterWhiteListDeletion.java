package com.example.a310287808.lightdj;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 8/14/2017.
 */


public class BridgeConnectionAfterWhiteListDeletion {

    public String whiteListString;
    public String [] parts ;
    public int counter=0;
    public String WLname;
    public String Status;
    public String Comments;
    public String ActualResult;
    public String ExpectedResult;
    public boolean ob3;

    public void BridgeConnectionAfterWhiteListDeletion(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws JSONException, IOException, InterruptedException {
        driver.navigate().back();
        driver.navigate().back();
        HttpURLConnection connection;
        URL url = new URL("http://192.168.86.23/api/YDU6nYRzaMmKqqzS5lmdb3s9roIfo7R5AZbq9JY4/config");
        connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuffer br = new StringBuffer();
        String line = " ";
        while ((line = reader.readLine()) != null) {
            br.append(line);
        }
        String output = br.toString();
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String DateToStr = format.format(curDate);

        JSONObject jsonObject = new JSONObject(output);
        Object whitelistObject = jsonObject.get("whitelist");
        whiteListString = whitelistObject.toString();

        parts = whiteListString.split("}");
        for(int i =0;i<parts.length;i++){
            if(parts[i].contains("Light DJ")){
                counter++;
                WLname = parts[i].substring(2,42);
                System.out.println(WLname);
                URL url1 = new URL("http://192.168.86.23/api/YDU6nYRzaMmKqqzS5lmdb3s9roIfo7R5AZbq9JY4/config/whitelist/"+WLname);
                HttpURLConnection httpCon = (HttpURLConnection) url1.openConnection();
                httpCon.setDoOutput(true);
                httpCon.setRequestMethod("DELETE");
                OutputStreamWriter out = new OutputStreamWriter(
                        httpCon.getOutputStream());
                System.out.println(httpCon.getResponseCode());
                System.out.println(httpCon.getResponseMessage());
                out.close();
//                }

            }
        }
        driver.navigate().back();

        //Opening Harmony App
        driver.findElement(By.xpath("//android.widget.TextView[@text='Harmony']")).click();
        TimeUnit.SECONDS.sleep(10);

//        Clicking on side menu
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[35,133][105,203]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on Harmony Setup option
        driver.findElement(By.xpath("//android.widget.TextView[@text='Harmony Setup']")).click();
        TimeUnit.SECONDS.sleep(2);

        //clicking on Add/Edit devices
        driver.findElement(By.xpath("//android.widget.TextView[@text='Add/Edit Devices & Activities']")).click();
        TimeUnit.SECONDS.sleep(30);

        //Clicking on Devices option
        driver.findElement(By.xpath("//android.view.View[@content-desc='DEVICES']")).click();
        TimeUnit.SECONDS.sleep(2);

        //clicking on Hue
        driver.findElement(By.xpath("//android.view.View[@content-desc='hue']")).click();
        TimeUnit.SECONDS.sleep(5);

        //click on refresh button
        driver.findElement(By.id("btnRefresh")).click();
        TimeUnit.SECONDS.sleep(5);

        //click on relogin
        driver.findElement(By.id("btnRetry")).click();
        TimeUnit.SECONDS.sleep(5);


        //looking for pushlink
        ob3= driver.findElement(By.xpath("//android.view.View[@content-desc='Philips Hue bridge detected in your network.']")).isDisplayed();

        Boolean result=(counter==0 && ob3==true);

        if(result==true) {

            Status = "0";
            ActualResult = "There is no white list created today to be deleted";
            Comments = "Fail: White list is not available to delete";
            ExpectedResult = "Application should ask user to press bridge pushlink after deleting whitelist";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

        } else {
            Status = "1";
            ActualResult = "Application is asking user to press bridge pushlink after deleting whitelist";
            Comments = "NA";
            ExpectedResult = "Application should ask user to press bridge pushlink after deleting whitelist";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);
        }
        //Clicking on Philips Hue
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='CANCEL']")).click();
        TimeUnit.SECONDS.sleep(2);

        //going back
        driver.navigate().back();
        driver.navigate().back();

        driver.findElement(By.xpath("//android.widget.Button[@bounds='[1298,154][1440,241]']")).click();
        TimeUnit.SECONDS.sleep(2);

        driver.navigate().back();

        storeResultsExcel(Status, ActualResult, Comments, fileName, ExpectedResult,APIVersion,SWVersion);
    }
    public String CurrentdateTime;
    public int nextRowNumber;
    public void storeResultsExcel(String excelStatus, String excelActualResult, String excelComments, String resultFileName, String ExcelExpectedResult
            ,String resultAPIVersion, String resultSWVersion) throws IOException {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        CurrentdateTime = sdf.format(cal.getTime());
        FileInputStream fsIP = new FileInputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        HSSFWorkbook workbook = new HSSFWorkbook(fsIP);
        nextRowNumber=workbook.getSheetAt(0).getLastRowNum();
        nextRowNumber++;
        HSSFSheet sheet = workbook.getSheetAt(0);

        HSSFRow row2 = sheet.createRow(nextRowNumber);
        HSSFCell r2c1 = row2.createCell((short)0);
        r2c1.setCellValue(CurrentdateTime);

        HSSFCell r2c2 = row2.createCell((short)1);
        r2c2.setCellValue("4");

        HSSFCell r2c3 = row2.createCell((short)2);
        r2c3.setCellValue(excelStatus);

        HSSFCell r2c4 = row2.createCell((short)3);
        r2c4.setCellValue(excelActualResult);

        HSSFCell r2c5 = row2.createCell((short)4);
        r2c5.setCellValue(excelComments);

        HSSFCell r2c6 = row2.createCell((short)5);
        r2c6.setCellValue(resultAPIVersion);

        HSSFCell r2c7 = row2.createCell((short)6);
        r2c7.setCellValue(resultSWVersion);

        fsIP.close();
        FileOutputStream out =
                new FileOutputStream(new File("C:\\Users\\310287808\\AndroidStudioProjects\\AnkitasTrial\\" + resultFileName));
        workbook.write(out);
        out.close();


    }
}
