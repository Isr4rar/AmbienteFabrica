package aqui.fica.o.driver;

import static java.lang.System.getProperty;
import static utils.ExtentManager.getCurrentPlatform;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import commons.OptionsManager;

public class DriverFactory {
	
	private static OptionsManager optionsManager = new OptionsManager();
	private static ThreadLocal<RemoteWebDriver> tlDriver = new ThreadLocal<>();

	public static synchronized RemoteWebDriver getDriver() {
		return tlDriver.get();
	}

	public static synchronized void setDriver(String browser) {
		switch (getCurrentPlatform()) {
		case WINDOWS:
			if (browser.startsWith("firefox")) {
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions(browser)));
			} else if (browser.startsWith("chrome")) {
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions(browser)));
			}
			break;
		case LINUX:
			if (browser.startsWith("firefox")) {
				System.setProperty("webdriver.gecko.driver", getProperty("user.dir") + "/src/test/resources/Drivers/geckodriver");
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions(browser)));
			} else if (browser.startsWith("chrome")) {
				System.setProperty("webdriver.chrome.driver", getProperty("user.dir") + "/src/test/resources/Drivers/chromedriver");
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions(browser)));
			}
			break;
		default:
			if (browser.startsWith("firefox")) {
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions(browser)));
			} else if (browser.startsWith("chrome")) {
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions(browser)));
			}
			break;
		}
	}

	public static void resetDriver() {
		RemoteWebDriver driver = getDriver();
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	public static void startUrl(String url) {
		getDriver().get(url);
	}

	public static void setSizeAndPosition(String browserWindowsSize) {
		switch (browserWindowsSize) {
		case "maximized":
			getDriver().manage().window().maximize();
			break;
			
		case "windowed":
			getDriver().manage().window().setSize(new Dimension(1280,720));
			getDriver().manage().window().setPosition(new Point(310, 10));
			break;
		
		default:
			getDriver().manage().window().maximize();
			break;
		}
	}
	public static void setSizeAndPosition() {
			getDriver().manage().window().maximize();
	}
	

}
