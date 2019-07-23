package tests;
import java.io.File;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.Homepage;
import pages.Loginpage;
import pages.Mailboxpage;
public class Test105
{
	public static void main(String[] args) 
			                      throws Exception
	{
		//Open Excel file for test data reading
		File f=new File("gmailtestdata.xls");
		Workbook rwb=Workbook.getWorkbook(f);
		Sheet rsh=rwb.getSheet(0); //0 for Sheet1
		int nour=rsh.getRows();
		int nouc=rsh.getColumns();
		//Open same excel file for results writing
		WritableWorkbook wwb=Workbook.createWorkbook(f,rwb);
		WritableSheet wsh=wwb.getSheet(0);//0 for Sheet1
		//Data driven testing in POM
		//1st row(index is 0) have columns headings
		for(int i=1;i<nour;i++)
		{
			String u=rsh.getCell(0,i).getContents();
			String uc=rsh.getCell(1,i).getContents();
			String p=null;
			String pc=null;
			if(uc.equalsIgnoreCase("valid"))
			{
				p=rsh.getCell(2,i).getContents();
				pc=rsh.getCell(3,i).getContents();
			}
			//Launch site
			System.setProperty("webdriver.chrome.driver",
							     "E:\\batch235\\chromedriver.exe");           
			WebDriver driver=new ChromeDriver();
			driver.get("http://www.gmail.com");
			Thread.sleep(5000);
			//create objects to page classes
			Homepage hp=new Homepage(driver);
			Loginpage lp=new Loginpage(driver);
			Mailboxpage mp=new Mailboxpage(driver);
			//Fill user-id and click next
			hp.filluid(u);
			hp.clickuidnext();
			Thread.sleep(5000);
			//User-id validations
			try
			{
				if(u.length()==0 && 
				hp.uidblankerr.isDisplayed())
				{
					Label l=new Label(nouc,i,
							     "Test passed for blank uid");
					wsh.addCell(l);
				}
				else if(uc.equalsIgnoreCase("invalid") &&
				hp.uidinvaliderr.isDisplayed())
				{
					Label l=new Label(nouc,i,
							"Test passed for invalid uid");
					wsh.addCell(l);
				}
				else if(uc.equalsIgnoreCase("valid") &&
				lp.pwd.isDisplayed())
				{
					//Enter password and click next
					lp.fillpwd(p);
					lp.clickpwd();
					Thread.sleep(10000);
					//Password validations
					if(p.length()==0 && 
					lp.pwdblankerr.isDisplayed())
					{
						Label l=new Label(nouc,i,
								"Test passed for blank pwd");
						wsh.addCell(l);
					}
					else if(pc.equalsIgnoreCase("invalid") && 
					lp.pwdinvaliderr.isDisplayed())
					{
						Label l=new Label(nouc,i,
								"Test passed for invalid pwd");
						wsh.addCell(l);
					}
					else if(pc.equalsIgnoreCase("valid") &&
					mp.comp.isDisplayed())
					{
						Label l=new Label(nouc,i,
									"Test passed for valid pwd");
						wsh.addCell(l);
					}
					else
					{
						Label l=new Label(nouc,i,"Test failed for pwd");
						wsh.addCell(l);
					}
				}
				else
				{
					Label l=new Label(nouc,i,"Test failed for userid");
					wsh.addCell(l);
				}
			}
			catch(Exception ex)
			{
				Label l=new Label(nouc,i,"Test interrupted");
				wsh.addCell(l);
			}
			//Close site
			driver.close();
		}
		//Save and close excel file
		wwb.write(); //save
		wwb.close();
		rwb.close();
	}
}




