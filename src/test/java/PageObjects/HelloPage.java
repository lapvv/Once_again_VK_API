package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloPage {

//    WebDriver driver = new ChromeDriver();

    WebElement loginLink = driver.findElement(By.xpath("//a[contains(@class,'btn-link')]"));
}
