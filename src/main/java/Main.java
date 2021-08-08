import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    public static void main (String args[]){
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://google.com");
        driver.switchTo();
        WebElement input = driver.findElement(By.cssSelector("input[class='gLFyf gsfi']"));
        input.click();
        //driver.quit();
    }
}
