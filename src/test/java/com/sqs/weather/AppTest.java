package com.sqs.weather;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private static WebDriver driver;


    @Test // http://www.weathersa.co.za/city-pages/ && //http://weather.news24.com/sa/george
    public void sameWeatherForecast() throws InterruptedException {
        System.out.println("In testSetup");
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);

        int[] tempWeatherSAMax;
        int[] tempWeatherSAMin;
        int[] tempnews24Max;
        int[] tempnews24Min;

        // check if weatherSA is online
        String baseUrl = "http://www.weathersa.co.za/";
        String expectedTitle = "Forecast";
        driver.get(baseUrl);
        String actualTitle = driver.getTitle();
        System.out.println("Actual Title is: " + actualTitle);
        Assert.assertEquals(expectedTitle, actualTitle);

        // get weather from weatherSA
        weatherSAObject weatherSA = new weatherSAObject(driver);
        tempWeatherSAMax = weatherSA.populate("George", "Max temperature");
        tempWeatherSAMin = weatherSA.getParam("Min temperature");

        System.out.println(Arrays.toString(tempWeatherSAMax));
        System.out.println(Arrays.toString(tempWeatherSAMin));

        // news24
        baseUrl = "http://weather.news24.com/";
        expectedTitle = "";
        driver.get(baseUrl);
        actualTitle = driver.getTitle();
        System.out.println("Actual Title is: " + actualTitle);
        Assert.assertEquals(expectedTitle, actualTitle);

        // get weather from news24
        news24Object news24 = new news24Object(driver);
        tempnews24Max = news24.populate("George", "High Temp");
        tempnews24Min = news24.getParam("Low Temp");
        System.out.println(Arrays.toString(tempnews24Max));
        System.out.println(Arrays.toString(tempnews24Min));



        // compare temperature
        String[] nextDays = {"Tomorrow: ", "In Two days: ", "In Three days: ", "In four days: "};
        for (int i = 1; i<5;i++){
            if (tempnews24Max[i] == tempWeatherSAMax[i] && tempnews24Min[i] == tempWeatherSAMin[i]){
                System.out.println(nextDays[i-1]+"same");
            } else {
                System.out.println(nextDays[i-1]+"different");
            }
        }

    }


    @After
    public void tearDown() {
        driver.close();
    }
}
