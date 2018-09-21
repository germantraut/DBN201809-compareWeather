package com.sqs.weather;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.List;

public class weatherSAObject {

    WebDriver driver;

    private By citySearchLocator = By.id("mod-search-searchword");
    private By searchIconLocator = By.id("searchicolink");
    private By townFoundLocator = By.id("forecasttitle");
    private By tableLocator = By.xpath("//*[@id=\"akeeba-renderjoomla\"]/div[3]/table[1]");

    weatherSAObject(WebDriver driver){
        this.driver = driver;
    }

    public int[] populate(String town, String param) throws InterruptedException {
        driver.findElement(citySearchLocator).sendKeys(town);
        Thread.sleep(2000); // wait for website, else city location doesn't work
        driver.findElement(citySearchLocator).sendKeys(Keys.DOWN);
        driver.findElement(citySearchLocator).sendKeys(Keys.ENTER);
        driver.findElement(searchIconLocator).click();

        // page with temp for the city
        Assert.assertTrue("Wrong Town, Search unsuccessful", driver.findElement(townFoundLocator).getText().contains(town));
        // get temp from table
        return getTemps(driver.findElement(tableLocator), param);
    }

    public int[] getParam(String param){
        return getTemps(driver.findElement(tableLocator), param);
    }

    private int[] getTemps(WebElement table, String param) throws NumberFormatException{
        int[] temp = new int[7];
        //System.out.println(table.toString()); //Path of the table
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> tableRows = tbody.findElements(By.tagName("tr"));
        for(int i=0; i<tableRows.size();i++){
            if(tableRows.get(i).getText().contains(param)){
                //System.out.println(tableRows.get(i).getText());
                List<WebElement> rowColumns = tableRows.get(i).findElements(By.tagName("td"));
                for (int j=0; j<rowColumns.size();j++){
                    //System.out.println(rowColumns.get(j).getText());
                    temp[j] = Integer.parseInt(rowColumns.get(j).getText());
                }

            }
        }
        return temp;
    }

    public boolean compareTemp(int temp){
        boolean same = false;
        return same;
    }

}
