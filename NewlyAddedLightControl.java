package com.example.a310287808.lightdj;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

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
import java.util.concurrent.TimeUnit;

import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 8/15/2017.
 */

public class NewlyAddedLightControl {
    public String IPAddress = "192.168.86.23/api";
    public String HueUserName = "YDU6nYRzaMmKqqzS5lmdb3s9roIfo7R5AZbq9JY4";
    public String HueBridgeParameterTypeGroup = "groups/0";
    public String finalURL;
    public String ActualResult;
    public String ExpectedResult;
    public String Status;
    public String Comments;
    public String lightName;
    public String newString1;
    StringBuffer br;
        Dimension size;


    public void NewlyAddedLightControl (AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException  {

        driver.navigate().back();
        driver.navigate().back();
        HttpURLConnection connection;

        //Checking whether the group light is ON/OFF
    finalURL = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterTypeGroup;
    URL url = new URL(finalURL);
    connection = (HttpURLConnection) url.openConnection();
    connection.connect();
    InputStream stream = connection.getInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    br = new StringBuffer();
    String line = " ";
    while ((line = reader.readLine()) != null) {
        br.append(line);
    }

    String output1 = br.toString();
    JSONObject jsonObject = new JSONObject(output1);

    Object ob = jsonObject.get("state");
    String newString = ob.toString();
    JSONObject jsonObject1 = new JSONObject(newString);
    Object ob1 = jsonObject1.get("any_on");

    //If the lights in the group are already ON then turn them off
    if (ob1.toString() == "true") {
        URL url1 = new URL("http://192.168.86.23/api/YDU6nYRzaMmKqqzS5lmdb3s9roIfo7R5AZbq9JY4/groups/0/action");
        String content = "{" + "\"on\"" + ":" + "false" + "}";
        HttpURLConnection httpCon = (HttpURLConnection) url1.openConnection();
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
        out.write(content);
        out.close();
        httpCon.getInputStream();
        System.out.println(httpCon.getResponseCode());
        TimeUnit.SECONDS.sleep(5);
        System.out.println("Lights are switched off");
        TimeUnit.SECONDS.sleep(5);

    }

        //opening Light DJ
        driver.findElement(By.xpath("//android.widget.TextView[@text='Light DJ']")).click();
        TimeUnit.SECONDS.sleep(2);

        //clicking bulb icon
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Light Selection']")).click();
        TimeUnit.SECONDS.sleep(2);
        driver.navigate().back();
        driver.navigate().back();

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

        //Selecting all the lights
        String Status1=driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,421][1384,631]']")).getAttribute("checked");
        if (Status1.equals("false")){
        driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,421][1384,631]']")).click();}
        else
            {
                driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,421][1384,631]']")).click();
                driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,421][1384,631]']")).click();
            }
        TimeUnit.SECONDS.sleep(2);

        String Status2=driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,635][1384,845]']")).getAttribute("checked");
        if (Status2.equals("false")){
        driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,635][1384,845]']")).click();}
        else
        {
            driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,635][1384,845]']")).click();
            driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,635][1384,845]']")).click();
        }
        TimeUnit.SECONDS.sleep(2);

        String Status3=driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,849][1384,1059]']")).getAttribute("checked");
        if (Status3.equals("false")){
        driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,849][1384,1059]']")).click();}
        else
        {
            driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,849][1384,1059]']")).click();
            driver.findElement(By.xpath("//android.widget.CheckBox[@bounds='[1226,849][1384,1059]']")).click();
        }
        TimeUnit.SECONDS.sleep(2);
        driver.navigate().back();

        String status= driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/startStopButton")).getText();
        System.out.println("Trigger: "+status);

        if (status.equals("Start"))
        {driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/startStopButton")).click();}
        else
        {
            driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/startStopButton")).click();
            driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/startStopButton")).click();
        }
        TimeUnit.SECONDS.sleep(2);

        //Choosing the Mellow effect
        driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/mellowSpinner")).click();
        TimeUnit.SECONDS.sleep(2);

        //choosing swirl
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@text='Swirl']")).click();
        TimeUnit.SECONDS.sleep(2);
        driver.navigate().back();
        TimeUnit.SECONDS.sleep(20);

for (int i=0; i<11; i++) {
    //getting the status of  group from API
    finalURL = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeParameterTypeGroup;
    URL urlstatus = new URL(finalURL);
    connection = (HttpURLConnection) urlstatus.openConnection();
    connection.connect();
    InputStream streamStatus = connection.getInputStream();
    BufferedReader readerStatus = new BufferedReader(new InputStreamReader(streamStatus));
    StringBuffer brStatus = new StringBuffer();
    String lineStatus = " ";
    while ((lineStatus = readerStatus.readLine()) != null) {
        brStatus.append(lineStatus);
    }

    String outputStatus = brStatus.toString();

    JSONObject jsonObject2 = new JSONObject(outputStatus);
    Object ob2 = jsonObject2.get("state");
    newString1 = ob2.toString();
    JSONObject jsonObject3 = new JSONObject(newString1);
    Object lightNameObject = jsonObject3.get("all_on");
    lightName = lightNameObject.toString();
    System.out.println("Status: " + lightName);
    if(lightName.equals("true")){break;}
    else{i++;
    System.out.println(i);}
}
        if (lightName.equals("true"))

        {
            Status = "1";
            ActualResult = "Newly added light is controlled by the application";
            Comments = "NA";
            ExpectedResult= "Newly added light should be controlled by the application";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        } else {
            Status = "0";
            ActualResult = "Newly added light is not controlled by the application";
            Comments = "FAIL: Light is not controlled and status of all the lights is: "+newString1;
            ExpectedResult= "Newly added light should be controlled by the application";
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
        r2c2.setCellValue("15");

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
