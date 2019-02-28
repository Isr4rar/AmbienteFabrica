package testes;

import org.testng.annotations.Test;

import pages.ExemploEstruturaPage;

public class GoogleTestExemplo {
	
	@Test
	public void pesquisaGoogle(){
		ExemploEstruturaPage page = new ExemploEstruturaPage();
		page.setPesquisaGoogle("Selenium WebDriver");
	}

}
