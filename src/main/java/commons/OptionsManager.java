package commons;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager {
	// Get Chrome Options
	public ChromeOptions getChromeOptions(String browser) {
		ChromeOptions options = new ChromeOptions();
		if(browser.equals("chrome")) {
			options.addArguments("--start-maximized");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--disable-popup-blocking");
			/*
			 * ChromeDriverService service = new ChromeDriverService.Builder()
			 * .usingAnyFreePort() .build(); ChromeDriver driver = new ChromeDriver(service,
			 * options);
			 */
		}
		return options;
	}

	// Get Firefox Options
	public FirefoxOptions getFirefoxOptions(String browser) {
		FirefoxOptions options = new FirefoxOptions();
		if(browser.equals("firefox")) {
			options.addArguments("--start-maximized");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("--disable-popup-blocking");      
		}
		return options;

	}
}
