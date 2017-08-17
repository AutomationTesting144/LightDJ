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
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

/**
 * Created by 310287808 on 8/14/2017.
 */

public class LightAdditionHue {
    public int lightCounter=0;
    public List iftttList;
    List dropListHueOld;
    List dropListHueNew;
    public String lightNameFromHue;
    public MobileElement listItemHueNew;
    public MobileElement listItemHueOld;
    public int oldCounter = 0;
    public int newCounter = 0;
    public int counterForNewLights=1;
    public int hashmapcounter=0;
    public String ActualResult;
    public String ExpectedResult;
    public String Status;
    public String Comments;
    Dimension size;


    public void LightAdditionHue(AndroidDriver driver, String fileName, String APIVersion, String SWVersion) throws IOException, JSONException, InterruptedException {

        driver.navigate().back();
        driver.navigate().back();
        //Opening Hue application
        driver.findElement(By.xpath("//android.widget.TextView[@text='Hue']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Clicking on settings button
        driver.findElement(By.xpath("//android.widget.ImageView[@bounds='[1218,322][1302,406]']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Selecting light setup
        driver.findElement(By.xpath("//android.widget.TextView[@text='Light setup']")).click();

        HashMap<String,Integer> oldlist = new HashMap<>();
        HashMap<String,Integer> newList = new HashMap<>();

        //Locate all drop down list elements
        dropListHueOld = driver.findElements(By.id("com.philips.lighting.hue2:id/list_item_title"));

        //Extract text from each element of drop down list one by one.
        for(int i=0; i< dropListHueOld.size(); i++){
            listItemHueOld = (MobileElement) dropListHueOld.get(i);
            lightNameFromHue = listItemHueOld.getText();

            if(lightNameFromHue.contains("Living room")==true){
                break;
            }else{
                oldlist.put(lightNameFromHue,oldCounter);
                oldCounter++;
            }
        }

        //clicking on Add button
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[1174,2126][1370,2322]']")).click();
        //Opening Add Lights Page and clicking on SEARCH button
        driver.findElement(By.id("com.philips.lighting.hue2:id/start_search_button")).click();
        TimeUnit.SECONDS.sleep(60);

        dropListHueNew = driver.findElements(By.id("com.philips.lighting.hue2:id/list_item_title"));

        //Extract text from each element of drop down list one by one.
        for(int i=0; i< dropListHueNew.size(); i++)
        {
            listItemHueNew = (MobileElement) dropListHueNew.get(i);
            lightNameFromHue = listItemHueNew .getText();
            if(lightNameFromHue.contains("Living room")==true){
                break;
            }
            else
            {
                newList.put(lightNameFromHue,newCounter);
                newCounter++;
            }
            //System.out.println("List of All Elements on Page:"+listItemHueNew.getText());
        }

        HashMap<String,Integer> setOfNewLights = new HashMap<>();
        if(newList.size()>oldlist.size()){
            for(String newKey : newList.keySet()){
                for(String oldKey : oldlist.keySet()){
//                    System.out.println("New Key:"+newKey);
//                    System.out.println("Old Key:"+oldKey);

                    if(newKey.contains(oldKey)==true)
                    {
                        counterForNewLights=0;
                        break;
                    }
                    else
                    {
                        counterForNewLights++;
                    }
                }
                //System.out.println(counterForNewLights);
                if(counterForNewLights!=0){
                    setOfNewLights.put(newKey,hashmapcounter);
                    hashmapcounter++;
                }
            }
        }

        //System.out.println(setOfNewLights.toString());
        //Going back from Hue application to home screen
        driver.findElement(By.xpath("//android.widget.ImageButton[@bounds='[0,84][196,280]']")).click();
        driver.navigate().back();

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
        System.out.println("Waiting for few minutes to update the changes ");
        TimeUnit.MINUTES.sleep(5);

        //opening Light DJ
        driver.findElement(By.xpath("//android.widget.TextView[@text='Light DJ']")).click();
        TimeUnit.SECONDS.sleep(2);

        //clicking bulb icon
        driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Light Selection']")).click();
        TimeUnit.SECONDS.sleep(2);

        //Locate all drop down list elements
        iftttList = driver.findElements(By.id("com.lightdjapp.lightdj.demo:id/lightName"));
        StringBuffer sb = new StringBuffer();
        //Extract text from each element of drop down list one by one.
        for (int l = 0; l < iftttList.size(); l++) {
            MobileElement listItemNew = (MobileElement) iftttList.get(l);
            for(String compareName : setOfNewLights.keySet()){
                if(compareName.contains(listItemNew.getText())==true){
                    lightCounter++;
                    System.out.println("Light Counter: "+lightCounter);
                    sb.append(compareName);
                    sb.append("\n");
                }
                else {continue;}
            }
        }

        if (lightCounter==0) {

            Status = "0";
            ActualResult = "Light: "+setOfNewLights.toString()+" is added in Hue applications but not in Light DJ";
            Comments = "Fail: Same lights are not available";
            ExpectedResult = "Light: "+setOfNewLights.toString()+" should be added in Hue applications and Light DJ";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);

           }
        else{
            Status = "1";
            ActualResult = "Light: "+setOfNewLights.toString()+" is added in Hue applications and Light DJ";
            Comments = "NA";
            ExpectedResult = "Light: "+setOfNewLights.toString()+" should be added in Hue applications and Light DJ";
            System.out.println("Result: " + Status + "\n" + "Comment: " + Comments + "\n" + "Actual Result: " + ActualResult + "\n" + "Expected Result: " + ExpectedResult);
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
        r2c2.setCellValue("9");

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
