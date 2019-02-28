package utils;

import static aqui.fica.o.driver.DriverFactory.getDriver;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DSL {

	public DSL() {
		PageFactory.initElements(getDriver(), this);
	}
	
	public void preencherCampoEnter(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);
		element.sendKeys(Keys.ENTER);
	}
	
	public void clickButton(WebElement element) {
		element.click();
	}
}
