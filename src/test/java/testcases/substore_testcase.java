package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import coreUtilities.testutils.ApiHelper;
import coreUtilities.utils.FileOperations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import pages.StartupPage;
import pages.substore_page;
import testBase.AppTestBase;
import testBase.UserActions;
import testdata.LocatorsFactory;

public class substore_testcase extends AppTestBase {

	Map<String, String> configData;
	Map<String, String> loginCredentials;
	String expectedDataFilePath = testDataFilePath + "expected_data.xlsx";
	String loginFilePath = loginDataFilePath + "Login.xlsx";
	StartupPage startupPage;
	String randomInvoiceNumber;
	LocatorsFactory locatorsFactoryInstance;
	UserActions userActionsInstance;
	substore_page substorePageInstance;

	@Parameters({ "browser", "environment" })
	@BeforeClass(alwaysRun = true)
	public void initBrowser(String browser, String environment) throws Exception {
		configData = new FileOperations().readExcelPOI(config_filePath, environment);
		configData.put("url", configData.get("url").replaceAll("[\\\\]", ""));
		configData.put("browser", browser);

		boolean isValidUrl = new ApiHelper().isValidUrl(configData.get("url"));
		Assert.assertTrue(isValidUrl,
				configData.get("url") + " might be Server down at this moment. Please try after sometime.");
		initialize(configData);
		startupPage = new StartupPage(driver);
	}

	@Test(priority = 1, groups = {
			"sanity" }, description = "Precondition: User should be logged in and on the healthapp section\n"
					+ "1. Login in the healthapp application\n" + "2. Scroll down menu till Substore\n"
					+ "3. Click on the Substore" + "4. SubStore module should be present")

	public void verifySubstoreCounterSubModule() throws Exception {
		substorePageInstance = new substore_page(driver);

		Map<String, String> substoreExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "substore");
		Map<String, String> loginData = new FileOperations().readExcelPOI(loginFilePath, "credentials");

		Assert.assertTrue(substorePageInstance.loginToHealthAppByGivenValidCredetial(loginData),
				"Login failed, Invalid credentials ! Please check manually");
		Assert.assertTrue(substorePageInstance.scrollDownAndClickSubstoreTab());
		System.out.println("Substore Page url : " + substoreExpectedData.get("url2"));
		Assert.assertEquals(substorePageInstance.verifySubstorePageUrl(), substoreExpectedData.get("url2"));
	}

	@Test(priority = 2, groups = { "sanity" }, description = "1. Login in the healthapp application\r\n"
			+ "2. Click on the substore \r\n" + "3. \"Select your Substore\" pop up "
			+ "4. Expected value that should be present in \"Select your Substore\" modal\r\n"
			+ "Expected sub-modals button:  Accounts,male ward SubStore,SubStore1,SubStore1")

	public void verifySubstoreSubModules() {
		try {
			substorePageInstance = new substore_page(driver);

			Assert.assertTrue(substorePageInstance.clickFourthCounterIfAvailable());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 3, groups = {
			"sanity" }, description = "Precondition: User should be logged in and on the Verification section\\n\"\r\n"
					+ "	+ \"1. Click on the Inventory Module\r\n"
					+ "	+ \"2. Hover over on module signout button and get text"
					+ "3. Verify text To change, you can always click here.")

	public void verifySubstoreModule() throws Exception {
		substorePageInstance = new substore_page(driver);
		Map<String, String> substoreExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "substore");
		System.out.println("SignOut Hovertext is : " + substoreExpectedData.get("moduleSignOutHoverText"));
		Assert.assertTrue(substorePageInstance.verifyModuleSignoutHoverText(substoreExpectedData));
	}

	@Test(priority = 4, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on SubStore module\r\n"
					+ "1. Login in the healthapp application\r\n" + "2. Click on the Substore\r\n"
					+ "3. \"Select your Substore\" pop up \r\n" + "4. Click on \"Account\" sub-modal\r\n"
					+ "5. Click on the \"Inventory\" sub-module\r\n" + "6. Click on\" Pharmacy\" sub-module\r\n"
					+ "7. All sub-modules should be displayed correctly.\r\n"
					+ "8. Expected Sub modules are : Pharmacy, Inventory")

	public void verifySubstoreSubModule() throws Exception {
		substorePageInstance = new substore_page(driver);

		Map<String, String> substoreExpectedData = new FileOperations().readExcelPOI(expectedDataFilePath, "substore");

		Assert.assertTrue(substorePageInstance.verifySubstoreSubModule(substoreExpectedData));
	}

	@Test(priority = 5, groups = {
			"sanity" }, description = "Pre condition: User should be logged in and it is on SubStore module\r\n"
					+ "1. Click on the SubStore Module drop-down arrow   \r\n"
					+ "2. Click on Inventory module. Expected Sub modules are : Stock, Inventory Requisition, Consumption, Reports, Patient Consumption,Return")

	public void verifyAllSubstoreModulesAreDisplayedInSublist() throws Exception {
		substorePageInstance = new substore_page(driver);

		Assert.assertTrue(substorePageInstance.subModulePresent("Inventory"));
	}

	@Test(priority = 6, groups = { "sanity" }, description = "Pre condition: User should be logged in \r\n"
			+ "1. Click on the Substore Module drop-down arrow" + "2. Verify navigation between sub-modules")

	public void verifyNavigationBetweenSubmodules() throws Exception {

		substorePageInstance = new substore_page(driver);
		Assert.assertTrue(substorePageInstance.verifyNavigationBetweenSubmodules());
	}

	
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		System.out.println("before closing the browser");
		browserTearDown();
	}

	@AfterMethod
	public void retryIfTestFails() throws Exception {
		startupPage.navigateToUrl(configData.get("url"));
	}

}
