import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

public class Main {
    public static void main(String[] args) throws IOException {

//        @BeforeAll
        System.setProperty("webdriver.chrome.driver", "D:\\SoftDev\\ITproj\\Lanit\\Once_again_VK_API\\src\\resources");
        WebDriver driver = new ChromeDriver();
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        System.getProperties().load(ClassLoader.getSystemResourceAsStream("user.properties"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);

//        @Test
        driver.get(System.getProperty("siteURL_Trello"));
    }
}
