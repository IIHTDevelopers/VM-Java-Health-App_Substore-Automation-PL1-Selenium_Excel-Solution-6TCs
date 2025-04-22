package pages;
// this one

//this one
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class substore_page extends StartupPage {

	//TC-1 Locators
		public By getUsernameTextfieldLocator = By.id("username_id");
		public By getPasswordTextboxLocator = By.xpath("//input[@id='password']");
		public By getSignInButtonLocator = By.xpath("//button[@id='login']");
		public By getDropDownLocater = By.xpath("//a[@href='#/WardSupply']");
//		TC-2 Locators
		public By getCounterButtonFourth = By.xpath("//a[@class='report_list']");
//		TC-3 Locators
		public By getAnchorTagLocatorInventory = By.xpath("//a[contains(text(),'Inventory')]");
		public By getModuleSignoutLocator = By.xpath("//i[contains(@class,'sign-out')]");
		public By getHoverText = By.xpath("//h6[contains(text(),'To change, you can always click here.')]");
//		TC-4 Locators
		public By getAnchorTagLocatorPharmacy = By.xpath("//a[contains(text(),'Pharmacy')]");
//		TC-5 Locators
		public By getSubModuleLocator = By.xpath("//ul[contains(@class,'nav-tabs')]//li//a");
//		TC-6 Locators
		public By getAnchorTagLocatorStock = By.xpath("//a[contains(text(),'Stock')]");
		public By getAnchorTagLocatorByTextInventoryRequisition = By.xpath("//a[contains(text(),'Inventory Requisition')]");
		public By getAnchorTagLocatorConsumption = By.xpath("//a[contains(text(),'Consumption')]");
		public By getAnchorTagLocatorReports = By.xpath("//a[contains(text(),'Reports')]");
		public By getAnchorTagLocatorPatientConsumption = By.xpath("//a[contains(text(),'Patient Consumption')]");
		public By getAnchorTagLocatorReturn = By.xpath("//a[contains(text(),'Return')]");

		
	public substore_page(WebDriver driver) {
		super(driver);
	}


	/**
	 * @Test 1.1 : about the method loginToHealthAppByGivenValidCredetial( )
	 * @param : Map<String, String>
	 * @description : fill usernameTextbox & passwordTextbox and click on sign in button
	 * @return : Boolean
	 * @author : Yaksha
	 */
	public boolean loginToHealthAppByGivenValidCredetial(Map<String, String> expectedData) throws Exception {
		Boolean textIsDisplayed = false;
		try {
			WebElement usernametextFieldWebElement = commonEvents.findElement(getUsernameTextfieldLocator);
			commonEvents.highlightElement(usernametextFieldWebElement);
			commonEvents.sendKeys(getUsernameTextfieldLocator, expectedData.get("username"));

			WebElement passwordtextFieldWebElement = commonEvents.findElement(getPasswordTextboxLocator);
			commonEvents.highlightElement(passwordtextFieldWebElement);
			commonEvents.sendKeys(getPasswordTextboxLocator, expectedData.get("password"));

			WebElement signinButtonWebElement = commonEvents.findElement(getPasswordTextboxLocator);
			commonEvents.highlightElement(signinButtonWebElement);
			commonEvents.click(getSignInButtonLocator);
			textIsDisplayed = true;
		} catch (Exception e) {
			throw e;
		}
		return textIsDisplayed;

	}

	/**
	 * @Test 1.2 about this method scrollDownAndClickSubstoreTab()
	 * @param : null
	 * @description : verify the Substore tab, scroll to it, and click it
	 * @return : boolean
	 * @author : YAKSHA
	 */
	public boolean scrollDownAndClickSubstoreTab() throws Exception {
		boolean scrolledTillElement = false;
		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			WebElement SubstoreTab = commonEvents.findElement(getDropDownLocater);
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", SubstoreTab);
			jsExecutor.executeScript("window.scrollBy(0, -50)");
			commonEvents.highlight(SubstoreTab);
			commonEvents.click(SubstoreTab);

			// Wait for the URL to contain "WardSupply"
			commonEvents.waitForUrlContains("WardSupply", 30);

			scrolledTillElement = true;
		} catch (Exception e) {
			throw e;
		}
		return scrolledTillElement;
	}

	/**
	 * @Test1.3 about this method verifySubstorePageUrl()
	 * @param : null
	 * @description : return the URL of the current page. 
	 * @return : String
	 * @author : YAKSHA
	 */

	public String verifySubstorePageUrl() throws Exception {
		
		try {
			String titleToVerify = commonEvents.getCurrentUrl();
			return titleToVerify;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test2 about this method clickFourthCounterIfAvailable( )
	 * @param : null
	 * @description : Clicks Counter modules
	 * @return : Boolean
	 * @throws : YAKSHA
	 */
	public boolean clickFourthCounterIfAvailable() throws Exception {
		try {
			List<WebElement> counterElements = commonEvents.getWebElements(getCounterButtonFourth);
			System.out.println("Elemets size >> " + counterElements.size());
			int numberOfCounterElements = counterElements.size();
			if (numberOfCounterElements > 0) {
				commonEvents.highlight(counterElements.get(0)).click(counterElements.get(0));
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @Test3 about this method verifyModuleSignoutHoverText()
	 * @param substoreExpectedData : Map<String, String> - Contains expected hover
	 *                             text
	 * @description : This method verifies that the hover text on the "Sign Out"
	 *              module matches the expected value.
	 * @return : boolean - true if the hover text matches the expected value, false
	 *         otherwise.
	 * @throws : Exception - if there is an issue finding the hover text or any
	 *           other operation.
	 * @author : YAKSHA
	 */
	public boolean verifyModuleSignoutHoverText(Map<String, String> substoreExpectedData) throws Exception {
		try {
			// Click on the "Inventory" section.
			commonEvents.click(getAnchorTagLocatorInventory);

			// Locate the "Sign Out" module for hover action.
			WebElement elementToHover = commonEvents.findElement(getModuleSignoutLocator);

			// Create an instance of Actions class to perform hover action.
			Actions actions = new Actions(driver);

			// Perform the hover action on the "Sign Out" module.
			actions.moveToElement(elementToHover).perform();

			// Retrieve the hover text displayed.
			String elementText = commonEvents.findElement(getHoverText).getText();
			System.out.println("Element text -->  " + elementText);

			// Check if the hover text matches the expected value.
			if (elementText.contains(substoreExpectedData.get("moduleSignOutHoverText"))) {
				return true;
			} else {
				throw new Exception("Hover text did not match the expected value.");
			}

		} catch (Exception e) {
			// Throw a meaningful exception indicating what failed.
			throw new Exception("Failed to verify the hover text on the 'Sign Out' module: " + e.getMessage(), e);
		}
	}

	/**
	 * @Test4 about this method verifySubstoreSubModule()
	 * @param substoreExpectedData : Map<String, String> - A map containing expected
	 *                             substore data, such as URLs or other related
	 *                             information.
	 * @description : This method verifies that the substore module's sub-modules
	 *              (e.g., Inventory, Pharmacy) are visible and interactable.
	 * @return : boolean - true if the sub-modules are visible and clickable, false
	 *         otherwise.
	 * @throws : Exception - if there is an issue finding or interacting with the
	 *           sub-modules.
	 * @author : YAKSHA
	 */
	public boolean verifySubstoreSubModule(Map<String, String> substoreExpectedData) throws Exception {
		try {
			System.out.println("Substore Page URL: " + substoreExpectedData.get("URL"));

			// Find the Inventory and Pharmacy sub-modules
			WebElement inventorySubModule = commonEvents.findElement(getAnchorTagLocatorInventory);
			WebElement pharmacySubModule = commonEvents.findElement(getAnchorTagLocatorPharmacy);

			// Highlight and click on the Inventory sub-module
			commonEvents.highlight(inventorySubModule).click(inventorySubModule);

			// Highlight and click on the Pharmacy sub-module
			commonEvents.highlight(pharmacySubModule).click(pharmacySubModule);

			return true;
		} catch (Exception e) {
			throw new Exception("Failed to verify substore sub-modules due to: " + e.getMessage(), e);
		}
	}

	/**
	 * @Test5 about this method subModulePresent()
	 * 
	 * @param moduleName : String - The name of the module to verify.
	 * @description : This method verifies if the specified module's sub-modules are
	 *              present and visible.
	 * @return : boolean - true if the sub-modules are displayed, false otherwise.
	 * @throws : Exception - if there is an issue finding the sub-modules or if no
	 *           elements are found.
	 * @author : YAKSHA
	 */
	public boolean subModulePresentInventory() throws Exception {
		boolean areModulesDisplayed = false;
		try {
			// Click on the specified module
			commonEvents.click(getAnchorTagLocatorInventory);

			// Get the list of sub-module elements
			List<WebElement> subModuleElements = commonEvents.getWebElements(getSubModuleLocator);
			System.out.println("Sub-module count: " + subModuleElements.size());

			// Check if the sub-modules are displayed
			if (!subModuleElements.isEmpty()) {
				for (WebElement subModule : subModuleElements) {
					boolean isDisplayed = commonEvents.isDisplayed(subModule);
					System.out.println("Sub-module displayed: " + isDisplayed);
					areModulesDisplayed = areModulesDisplayed || isDisplayed;
				}
			} else {
				System.out.println("No sub-modules found under the specified module.");
			}

		} catch (Exception e) {
			throw new Exception("Failed to find elements: " + e.getMessage(), e);
		}
		return areModulesDisplayed;
	}

	/**
	 * @Test6 about this method verifyNavigationBetweenSubmodules()
	 * 
	 * @param : null
	 * @description : This method verifies that the user is able to navigate between
	 *              the sub modules.
	 * @return : boolean
	 * @author : YAKSHA
	 */
	public boolean verifyNavigationBetweenSubmodules() throws Exception {
		try {
			// Clicking on the "Inventory" submodule to start navigation.
			commonEvents.click(getAnchorTagLocatorInventory);

			// Navigating to the "Stock" submodule and waiting for the URL to update.
			commonEvents.click(getAnchorTagLocatorStock);
			commonEvents.waitForUrlContains("Inventory/Stock", 5000);

			// Navigating to the "Inventory Requisition" submodule and waiting for the URL
			// to update.
			commonEvents.click(getAnchorTagLocatorByTextInventoryRequisition);
			commonEvents.waitForUrlContains("Inventory/InventoryRequisitionList", 5000);

			// Navigating to the "Consumption" submodule and waiting for the URL to update.
			commonEvents.click(getAnchorTagLocatorConsumption);
			commonEvents.waitForUrlContains("Inventory/Consumption/ConsumptionList", 5000);

			// Navigating to the "Reports" submodule and waiting for the URL to update.
			commonEvents.click(getAnchorTagLocatorReports);
			commonEvents.waitForUrlContains("Inventory/Reports", 5000);

			// Navigating to the "Patient Consumption" submodule and waiting for the URL to
			// update.
			commonEvents.click(getAnchorTagLocatorPatientConsumption);
			commonEvents.waitForUrlContains("Inventory/PatientConsumption/PatientConsumptionList", 5000);

			// Navigating to the "Return" submodule and waiting for the URL to update.
			commonEvents.click(getAnchorTagLocatorReturn);
			commonEvents.waitForUrlContains("Inventory/Return", 5000);

			// Finally, navigating back to the "Stock" submodule.
			commonEvents.click(getAnchorTagLocatorStock);

			// Return true if all navigations are successful.
			return true;
		} catch (Exception e) {
			// Throwing the exception if any issue occurs during navigation.
			throw e;
		}
	}
}
