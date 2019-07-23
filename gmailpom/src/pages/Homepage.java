package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public class Homepage 
{
	public WebDriver driver;
	
	@FindBy(name="identifier")
	public WebElement uid;
	
	@FindBy(id="identifierNext")
	public WebElement uidnext;
	
	@FindBy(xpath="//*[contains(text(),'Enter an email')]")
	public WebElement uidblankerr;
	
	@FindBy(xpath=
		   "(//*[contains(text(),'find your Google')])[2]")
	public WebElement uidinvaliderr;
	
	public Homepage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	public void filluid(String x)
	{
		uid.sendKeys(x);
	}
	
	public void clickuidnext()
	{
		uidnext.click();
	}
}
