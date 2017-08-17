package com.example.a310287808.lightdj;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 8/16/2017.
 */

public class ColorChangeAll {

    public String IPAddress = "192.168.86.23/api";
    public String HueUserName = "YDU6nYRzaMmKqqzS5lmdb3s9roIfo7R5AZbq9JY4";
    public String HueBridgeParameterTypeGroup = "groups/0";
    public String HueBridgeIndLightType = "lights";
    public String finalURL;
    public String ActualResult;
    public String ExpectedResult;
    public String Status;
    public String Comments;
    public String lightName;
    public String newString1;
    public String status;
    StringBuffer br;
    public String AllLightIDs;
    public String finalURLIndLight;
    public int lightCounter=0;
    public String x;
    public String lightName1;

    public void ColorChangeAll (AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException  {

        driver.navigate().back();
        driver.navigate().back();

        //Stopping light DJ if started already
        //opening Light DJ
        driver.findElement(By.xpath("//android.widget.TextView[@text='Light DJ']")).click();
        TimeUnit.SECONDS.sleep(2);

        status= driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/startStopButton")).getText();

        if (status.equals("Stop"))
        {driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/startStopButton")).click();}
        TimeUnit.SECONDS.sleep(2);

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
        driver.navigate().back();
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
        driver.navigate().back();

        status= driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/startStopButton")).getText();

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

        //changing color to green
        TouchAction action = new TouchAction(driver);
        WebElement color=driver.findElement(By.id("com.lightdjapp.lightdj.demo:id/greenDot"));
        action.longPress(color).perform();
        TimeUnit.SECONDS.sleep(3);

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
        Object ob2 = jsonObject2.get("action");
        newString1 = ob2.toString();
        JSONObject jsonObject3 = new JSONObject(newString1);
        Object lightNameObject = jsonObject3.get("xy");
        lightName = lightNameObject.toString();

        LightIdsFromGroup0 AllLights=new LightIdsFromGroup0();
        AllLightIDs=AllLights.LightIdsFromGroup0(outputStatus);

        String[] Final = AllLightIDs.substring(1,AllLightIDs.length()-1).split(",");

        HashMap<String,Integer> lightIDs = new HashMap<>();

        for(int i=0;i<Final.length;i++) {
            if (Final[i].length() < 4) {
                String IDs=String.valueOf((Final[i].charAt(1)));
                lightIDs.put(IDs,i);
            } else {
                String IDs=String.valueOf(Final[i].substring(1, 3));
                lightIDs.put(IDs,i);
            }
        }
        StringBuffer sb = new StringBuffer();

        String Xval=lightName.substring(1,6);
        String Yval=lightName.substring(7,12);
        String Xred="0.408";
        String Yred="0.517";

        boolean finalResult=(Xval.equals(Xred)) && (Yval.equals(Yred));
        if (finalResult==true){
            Status = "1";
            ActualResult = "Color changed to Green for all lights";
            Comments = "NA";
            ExpectedResult= "Lights color should change to Green";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments+ "\n"+"Actual Result: "+ActualResult+ "\n"+"Expected Result: "+ExpectedResult);
        }
        else
        {
            for(Map.Entry<String,Integer> lights : lightIDs.entrySet()) {
                finalURLIndLight = "http://" + IPAddress + "/" + HueUserName + "/" + HueBridgeIndLightType
                        + "/" + lights.getKey();

                URL url1 = new URL(finalURLIndLight);
                connection = (HttpURLConnection) url1.openConnection();
                connection.connect();
                InputStream stream1 = connection.getInputStream();
                BufferedReader reader1 = new BufferedReader(new InputStreamReader(stream1));
                StringBuffer br1 = new StringBuffer();
                String line1 = " ";
                while ((line1 = reader1.readLine()) != null) {
                    br1.append(line1);
                }
                String output17 = br1.toString();
                //System.out.println(output1);
                JSONObject jsonObject7 = new JSONObject(output17);
                Object ob7 = jsonObject7.get("state");
                String newString7 = ob7.toString();
                Object lightNameObject7 = jsonObject7.get("name");
                lightName1 = lightNameObject7.toString();
                JSONObject jsonObject17 = new JSONObject(newString7);
                if (jsonObject1.has("xy") == true) {
                    Object ob17 = jsonObject17.get("xy");
                    x = ob17.toString();
                    String Xval1 = lightName1.substring(1, 6);
                    String Yval1 = lightName1.substring(8, 13);
                    String Xred1 = "0.408";
                    String Yred1 = "0.517";

                    Boolean result = (Xval1.equals(Xred1)) && (Yval1.equals(Yred1));
                    if (result == false) {
                        lightCounter++;
                        sb.append(lightName1);
                        sb.append("\n");
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            Status = "0";
            ActualResult ="Following Lights are not Green in color:"+"\n"+lightName1.toString();
            Comments = "FAIL:Lights are not changed to Green color";
            ExpectedResult="Lights color should change to Green";
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
        r2c2.setCellValue("14");

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
