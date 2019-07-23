package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class Mailboxpage
{
	public WebDriver driver;
	
	@FindBy(xpath="//*[text()='COMPOSE']")
	public WebElement comp;
	
	public Mailboxpage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
}
