package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.DSL;

public class ExemploEstruturaPage extends DSL {
	
	@FindBy(xpath = "//input[@title='Pesquisar']")
	private WebElement pesquisarGoogle;

	public void setPesquisaGoogle(String value) {
		preencherCampoEnter(pesquisarGoogle, value);
	}
	
	
}
