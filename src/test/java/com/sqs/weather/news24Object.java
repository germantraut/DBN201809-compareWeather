package com.sqs.weather;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class news24Object {
    WebDriver driver;

    private By sevenDaysLocator = By.id("ext-gen32");
    private By townFoundLocator = By.id("cityInfo");
    private By tableLocator = By.xpath("//*[@id=\"forecastContent\"]/table/tbody");

    news24Object(WebDriver driver){
        this.driver = driver;
    }

    public int[] populate(String town, String param) throws InterruptedException {
        driver.get("http://weather.news24.com/sa/george");
        driver.findElement(sevenDaysLocator).click();

        // page with temp for the city
        Thread.sleep(2000);
        Assert.assertTrue("Wrong Town", driver.findElement(townFoundLocator).getText().contains(town));
        return getTemps(driver.findElement(tableLocator), param);
    }
    public int[] getParam(String param){
        return getTemps(driver.findElement(tableLocator), param);
    }

    private int[] getTemps(WebElement table, String param){
        int[] temp = new int[7];
        int indexOfColumn = -1;
        List<WebElement> tableRows = table.findElements(By.tagName("tr"));
        //System.out.println(tableRows.get(1).getText());
        List<WebElement> rowColumns = tableRows.get(1).findElements(By.tagName("td"));
        for(int i=0;i<rowColumns.size();i++){
            if(rowColumns.get(i).getText().contains(param)){
                //System.out.println(param);
                indexOfColumn = i+1;
            }
        }
        if ((indexOfColumn != -1)){
            for (int i=2;i<tableRows.size()-2;i++){
                rowColumns = tableRows.get(i).findElements(By.tagName("td"));
                temp[i-2] = Integer.parseInt(rowColumns.get(indexOfColumn).getText().replace("°C", ""));
            }
        }
        return temp;
    }

    public boolean compareTemp(int temp){
        boolean same = false;
        return same;
    }
}
