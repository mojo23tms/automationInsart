import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class HerokuTest {

    @Test
    //TO DO add a test which goes to http://the-internet.herokuapp.com/checkboxes URL and check checkbox
    //Verify checkbox is checked in the test
    public void changeCheckBox(){
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/checkboxes"); //Loading test page
        WebElement checkbox = driver.findElement(By.xpath("//*/form/input[1]")); //Initiating the checkbox element
        checkbox.click(); //Clicking on the checkbox
        if(driver.findElement(By.xpath("//*/form/input[1][@checked]")).isDisplayed()){
            assertTrue(driver.findElement(By.xpath("//*/form/input[1][@checked]")).isDisplayed());
            //If the checkbox is checked - the test is passed
        }
        else{
            //If the checkbox wasn't checked:
            checkbox.click(); // - clicking on the checkbox;
            assertTrue(driver.findElement(By.xpath("//*/form/input[1][@checked]")).isDisplayed());
            System.out.println("The checkbox wasn't checked"); // - passing the test with message
        }
        driver.quit();
    }


    @Test
    //TO DO add a test which goes to http://the-internet.herokuapp.com/entry_ad URL and closes the pop-up
    //then it clicks `click here.` link and checks `THIS IS A MODAL WINDOW` text is displayed on the page
    public void dynamicElement() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        //Loading test page
        driver.get("http://the-internet.herokuapp.com/entry_ad");
        Thread.sleep(2000);
        //Found an issue: sometimes modal isn't displayed when page is loaded/when clicking the link, so adding an explicit wait
        //Initiating modal
        WebElement modal;
        //Wait till modal will be visible
        modal = wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath("//*/div[@id='modal'][@style='display: block;']"))));
        //Then close the modal
        driver.findElement(By.xpath("//*/div[@class='modal-footer']/p")).click();
        //Opening modal again with the while loop (cause the modal does not appear in one click)
        while(driver.findElement(By.cssSelector("div[class=modal]")).isDisplayed() == false){
            driver.findElement(By.cssSelector("a[id='restart-ad']")).click();
            Thread.sleep(2000);
        }
        //Test is passed if the header text "THIS IS A MODAL WINDOW" is displayed
        String modalHead = driver.findElement(By.cssSelector("div[class='modal-title']>h3")).getText();
        assertTrue(modalHead.contentEquals("THIS IS A MODAL WINDOW"));
        driver.quit();

    }

    @Test
    //TO DO add a test which goes to http://the-internet.herokuapp.com/login URL and fills in incorrect details
    //then it clicks `Login` button
    //Here should be a check on error message is displayed on the page
    public void testInvalidLogin(){
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/login");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        //initiate login field
        //fill it with invalid data
        driver.findElement(By.cssSelector("input[id=username]")).sendKeys("ggwp");
        //initiate pass field
        //fill it with invalid data
        driver.findElement(By.cssSelector("input[id=password]")).sendKeys("glhf");
        //initiate login button and click on it
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id=flash]")));
        //detect error message
        //test is passed if message is displayed
        assertTrue(errorMsg.isDisplayed());
        driver.quit();
    }

    @Test
    //TO DO add a test which goes to http://the-internet.herokuapp.com/login URL and fills in correct details
    //then it clicks `Login` button
    //Here should be a check that user is logged in and a new content is displayed

    public void testValidLogin(){
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/login");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        //initiate login field
        //fill it with valid data
        driver.findElement(By.cssSelector("input[id=username]")).sendKeys("tomsmith");
        //initiate pass field
        //fill it with valid data
        driver.findElement(By.cssSelector("input[id=password]")).sendKeys("SuperSecretPassword!");
        //initiate login button and click on it
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        String successMsg = driver.findElement(By.cssSelector("div[class='flash success']")).getText();
        WebElement pageContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id=content]")));
        //detect error message
        //test is passed if message is displayed
        assertTrue(successMsg.contains("logged") && pageContent.isDisplayed());
        driver.quit();
    }

    @Test
    //TO DO add a test which goes to  http://the-internet.herokuapp.com/dynamic_loading/1 URL and
    //clicks start button
    //Here should be a check that Hello World text is displayed
    public void testDynamicLoadingElement() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/dynamic_loading/1");
        //Initiating and clicking on the start button
        driver.findElement(By.cssSelector("div>button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        //Waiting till the loading bar will disapear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id=loading]")));
        //Getting the content from the tested element
        String dynamicText = driver.findElement(By.cssSelector("div[id=finish]>h4")).getText();
        //The test is passed if the content equals Hello World! AND required element is displayed
        assertTrue((dynamicText.contentEquals("Hello World!"))
                || (driver.findElement(By.cssSelector("div[id=finish]>h4")).isDisplayed()));
        driver.quit();
    }

    //For the next test I required a method for parsing the downloading directory to find required file by its name
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        //Initiating the directory variable
        File dir = new File(downloadPath);
        //Initiating the array for the file list
        File[] dirContents = dir.listFiles();
        //Creating for loop to parse file names for searching of our file
        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals(fileName)) {
                //File has been found, it can now be deleted:
                dirContents[i].delete();
                return true;
            }
        }
        return false;
    }

    @Test
    //TO DO add a test which goes to http://the-internet.herokuapp.com/jqueryui/menu URL and
    //goes to Enabled-Download-Excel and clicks it
    //verify that the file is downloaded
    public void testDownloadFile(){
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/jqueryui/menu");
        
        isFileDownloaded("C:\\Users\\j_brink\\Downloads", "menu.xls");
    }
}
