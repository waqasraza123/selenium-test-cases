import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.base.Predicate;

public class Tests {
	//class members ==========================
	public String main_url;
	public WebDriver webDriver;
	public HashMap<String, String> saveResultsToExcel;
	public String searchString;
	public WebElement webElement;
	public String searchFieldId;
	public String searchButtonId;
	public String reallyLongString;
	public String stressEmailAddress;
	public String limitEmailAddress;
	public String title;
	
	public Tests(String s){}
	
	//default constructor ====================================
	public Tests(){
		main_url = "https://foursquare.com/";
		System.out.println(main_url);
		saveResultsToExcel = new HashMap<String, String>();
		searchString = "PC";
		searchFieldId = "headerBarSearch";
		searchButtonId = "submitButton";
		reallyLongString = "dkhasda64dasdhadaisdd64dasdhsauida:a6sdasiudhasdhiausd,.asiduasd;;;asdiuasdasdiuasdasdiusadasdiuasd"
							+ "aiusdhausdasdiuasdaiduwduiwqdqwdiuqwd";
		stressEmailAddress = "wapfvsdjagffgdkeasndjandjkandkasdnajsdnajsdnajksdnasdjknajskdnasdjddsajdnaksdnaskdnajsdnasdnsajkdnsjkdnakjsdnaskjdnakdjnasjdnasdsakd@gmail.com";
		limitEmailAddress = "waqpvldlandknlsndjandjkandkasdnajsdnajsd@gmail.com";
		title = "Islamabad | Food, Nightlife, Entertainment";
		
		
		//configure for chrome ===============================
		System.setProperty("webdriver.chrome.driver", "C:/Users/Chaudhry Waqas/Desktop/chromedriver_win32/chromedriver.exe");
		webDriver = new ChromeDriver();
		webDriver.manage().window().setSize(new Dimension(1024,768));
	}
	
	//search field test================================================
	/**
	 * Test 1
	 * @param searchString
	 */
	@Test
	public void searchTest(String searchString){
		System.out.println("Here it is "+main_url);
		webDriver.get(main_url);
		waitForLoad(webDriver);
		
		webElement = webDriver.findElement(By.id(searchFieldId)); //get search field
		webElement.sendKeys(searchString);
		webElement.submit();
		
		
		//check if passed or failed 
		if(webDriver.getCurrentUrl().contains(searchString)){
			saveResultsToExcel.put("Search Test Passed", "passed");
			System.out.println("Search Test Passed");
		}
		else{
			saveResultsToExcel.put("Search Test Passed", "failed");
			System.out.println("Search Test Passed");
		}
		
		//webDriver.quit();
		for(Map.Entry<String, String> m : saveResultsToExcel.entrySet()){
			System.out.println(m.getKey());
			System.out.println(m.getValue());
		}
		return;
	}
	
	
	//searchTest under stress testing =======================================
	/**
	 * 2nd test
	 * @param reallyLongString
	 */
	@Test
	public void searchStressTesting(String reallyLongString){
		webDriver.get(main_url);
		waitForLoad(webDriver);
		
		webElement = webDriver.findElement(By.id(searchFieldId)); //get seach field
		webElement.sendKeys(reallyLongString);
		webElement.submit();
		
		//look for the appropriate user message to handle such long string
		webDriver.getCurrentUrl();
		System.out.println("current title is " + webDriver.getTitle());
		
		if(webDriver.findElement(By.className("message")).getText().contains("Sorry")){
			saveResultsToExcel.put("searchStressTest", "passed");
			System.out.println("searchStressTest Passed");
		}
		else{
			saveResultsToExcel.put("searchStressTest", "failed");
			System.out.println("searchStressTest failed");
		}
		
		//webDriver.quit();
		for(Map.Entry<String, String> m : saveResultsToExcel.entrySet()){
			System.out.println(m.getKey());
			System.out.println(m.getValue());
		}
		return;
	}
	
	
	/**
	 * 3rd Test
	 * @param searchString
	 * @throws InterruptedException 
	 */
	//search button submission test ===========================================
	@Test
	public void searchButtonSubmissionStressTesting(String searchString) throws InterruptedException{
		webDriver.get(main_url);
		waitForLoad(webDriver);
		
		webElement = webDriver.findElement(By.id(searchFieldId)); //get seach field
		
		//click the search button multiple times ===============================
		
		try {
			for(int i=0; i<100; i++){
				webDriver.findElement(By.id("searchFieldId")).submit();			
			}
		} catch (Exception e) {
			System.out.println();
		}
		
		
		
		//look for the appropriate user message to handle such long string
		webDriver.getCurrentUrl();
		if(webDriver.getPageSource().contains("Error 503 backend read error"))
		{
			saveResultsToExcel.put("searchButtonSubmissionStressTesting", "failed");
			System.out.println("searchButtonSubmissionStressTesting failed");
		}

		else
		{
			saveResultsToExcel.put("searchButtonSubmissionStressTesting", "passed");
			System.out.println("searchButtonSubmissionStressTesting Passed");
		}
		//webDriver.quit();
		
		return;
	}
	
	
	/**
	 * 4th Test
	 */
	//login test ===========================================
	@Test
	public void loginTest(String searchString){
		webDriver.get(main_url);
		waitForLoad(webDriver);
		
		//press login
		(webDriver.findElement(By.className("log"))).click(); //get seach field
		waitForLoad(webDriver);
		
		
		//get input form fields and send keys
		webDriver.findElement(By.id("username")).sendKeys("waqas");
		webDriver.findElement(By.id("password")).sendKeys("adaskdhaks");
		webDriver.findElement(By.id("loginToFoursquare")).submit();
		waitForLoad(webDriver);
		
		
		//look for the appropriate user message to handle error message
		webDriver.getCurrentUrl();
		if(webDriver.findElement(By.className("errorMsg")).findElement(By.tagName("p")).findElement(By.tagName("strong")).getText().contains("The email/phone you entered is incorrect. Please try again.")){
			saveResultsToExcel.put("loginTest", "passed");
			System.out.println("loginTest passed");
		}
		else{
			saveResultsToExcel.put("loginTest", "failed");
			System.out.println("loginTest failed");
		}
		
		//webDriver.quit();
		
		return;
	}
	
	
	
	/**
	 * 5th Test
	 */
	//signup test stress===========================================
	@Test
	public void signUpTest(){
		webDriver.get(main_url);
		waitForLoad(webDriver);
		
		//press signup
		(webDriver.findElement(By.className("emailSignup"))).click(); //get seach field
		waitForLoad(webDriver);
		
		//get input form fields and send keys
		webDriver.findElement(By.id("inputEmail")).sendKeys(stressEmailAddress);
		webDriver.findElement(By.id("inputPassword")).sendKeys("daniodnadiadiandoadn");
		webDriver.findElement(By.id("inputFirstName")).sendKeys("daniodnadiadiandoadn");
		webDriver.findElement(By.id("inputBirthDate")).sendKeys("daniodnadiadiandoadn");
		webDriver.findElement(By.id("inputBirthMonth")).sendKeys("daniodnadiadiandoadn");
		webDriver.findElement(By.id("inputBirthYear")).sendKeys("daniodnadiadiandoadn");
		
		webDriver.findElement(By.id("signupButton")).submit();
		waitForLoad(webDriver);
		
		
		//look for the appropriate user message to handle error message
		webDriver.getCurrentUrl();
		if(webDriver.findElement(By.className("signUp")).findElement(By.className("error")).getText().contains("something went wrong")){
			saveResultsToExcel.put("signupStressTesting", "passed");
			System.out.println("signup stress testing passed");
		}
		else{
			saveResultsToExcel.put("signupStressTesting", "failed");
			System.out.println("signup stress testing failed");
		}
		
		//webDriver.quit();
		for(Map.Entry<String, String> m : saveResultsToExcel.entrySet()){
			System.out.println(m.getKey());
			System.out.println(m.getValue());
		}
		return;
	}
	
	
	/**
	 * 6th Test
	 */
	//signup limit test ===========================================
	@Test
	public void signUpLimitTesting(){
		webDriver.get(main_url);
		waitForLoad(webDriver);
		
		//press signup
		(webDriver.findElement(By.className("emailSignup"))).click(); //get seach field
		waitForLoad(webDriver);
		
		//get input form fields and send keys
		webDriver.findElement(By.id("inputEmail")).sendKeys(limitEmailAddress);
		webDriver.findElement(By.id("inputPassword")).sendKeys("daniodnadiadiandoadn");
		webDriver.findElement(By.id("inputFirstName")).sendKeys("waqassdjandkjasndjandjkandkasdnajsdnajsd@gmail.comwaqassdjandkjasndjandjkandkasdnajsdnajsd@gmail.com");
		webDriver.findElement(By.id("inputBirthDate")).sendKeys("01");
		webDriver.findElement(By.id("inputBirthMonth")).sendKeys("02");
		webDriver.findElement(By.id("inputBirthYear")).sendKeys("1995");
		
		webDriver.findElement(By.id("signupButton")).submit();
		waitForLoad(webDriver);
		
		
		//look for the appropriate user message to handle error message
		//webDriver.getCurrentUrl();

		ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs.get(0));
		if(webDriver.findElement(By.className("popup")).findElements(By.id("tastesOnboardingEdu")).size()>0){
			saveResultsToExcel.put("signupLimitTesting", "passed");
			System.out.println("signup limit testing passed");
		}
		else{
			saveResultsToExcel.put("signupLimitTesting", "failed");
			System.out.println("signup limit testing failed");
		}
		
		//webDriver.quit();
		for(Map.Entry<String, String> m : saveResultsToExcel.entrySet()){
			System.out.println(m.getKey());
			System.out.println(m.getValue());
		}
		return;
	}
	
	
	/**
	 * 7th Test
	 * Navigate to www.foursquare.com.
	 * Set the form data.
	 * Click on the search button.
	 * View the Result in the next page.
	 * Click on the first suggestion link.
	 * View the details.
	 * If all of the above work, the black box test case is considered successful.
	 */
	//black box testing ===========================================
	@Test
	public void blackBoxTesting(){
		webDriver.get(main_url);
		waitForLoad(webDriver);
		
		webElement = webDriver.findElement(By.id(searchFieldId)); //get search field
		webElement.sendKeys(searchString);
		webElement.submit();
		
		//wait for the reults page to load ==============================
		waitForLoad(webDriver);
		webDriver.getCurrentUrl();
		
		System.out.println("current url is " + webDriver.getCurrentUrl());
		
		String venueName = webDriver.findElement(By.id("results")).findElement(By.className("venueName")).findElement(By.tagName("a")).getText();
		webDriver.findElement(By.id("results")).findElement(By.className("venueName")).findElement(By.tagName("a")).click();
		
		System.out.println("name is " +venueName);
		
		waitForLoad(webDriver);
		//webDriver.findElement(By.className("contents")).findElement(By.className("venueName")).findElement(By.tagName("h1")).click();
		
		
		ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs.get(1));
		webDriver.getCurrentUrl();
		
		
		if(webDriver.findElement(By.className("contents")).findElement(By.className("venueName")).getText().equals(venueName)){
			saveResultsToExcel.put("BBT", "passed");
			System.out.println("BBT Passed");
		}
		else{
			saveResultsToExcel.put("BBT", "failed");
			System.out.println("BBT Failed");
		}
		
		//webDriver.quit();
		for(Map.Entry<String, String> m : saveResultsToExcel.entrySet()){
			System.out.println(m.getKey());
			System.out.println(m.getValue());
		}
		return;
	}
	
	
	/**
	 * test 8
	 */
	public void sanityTesting(){
		System.out.println("For Example in a project there are five "
				+ "modules like login page, home page\n, "
				+ "user detail page, new user creation\n, "
				+ "and task creation etc. So we have the\n "
				+ "bug in login page like on login page username field\n "
				+ "accepts the less than six alpha-numeric characters\n "
				+ "which are against the requirements as in requirements\n "
				+ "it is specified that username should not be below\n "
				+ "than six characters but as username accepts the\n "
				+ "less than six characters it is the bug.\n"
				+ "So now the bug is reported by the testing team\n"
				+ " to the developer team to fix it. When the developing team\n"
				+ " fixes the bug and passed it to testing team than the testing team\n"
				+ " checks the other modules of the application means checks\n"
				+ " that fix bug does not affect the functionality of the other modules\n"
				+ "*****************************************************************"
				+ "So, sanity testing is not possible in current senario");
	
	return;
	}
	
	
	/**
	 * 9th Test
	 */
	//facebook signup testing ===========================================
	@Test
	public void facebookSignUpTest(){
		webDriver.get(main_url);
		waitForLoad(webDriver);
		
		webDriver.findElement(By.className("facebookButton")).click(); //get facebook field
		

		ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
		webDriver.switchTo().window(tabs.get(1));
		System.out.println(webDriver.getTitle());
		
		//webDriver.findElement(By.id("resultsView")).findElement(By.className("venuename")).findElement(By.tagName("a")).click();
		
		
		
		if(webDriver.getTitle().contains("Facebook")){
			saveResultsToExcel.put("Facebook", "passed");
			System.out.println("Facebook Passed");
		}
		else{
			saveResultsToExcel.put("Facebook", "failed");
			System.out.println("Facebook Failed");
		}
		
		//webDriver.quit();
		for(Map.Entry<String, String> m : saveResultsToExcel.entrySet()){
			System.out.println(m.getKey());
			System.out.println(m.getValue());
		}
		return;
	}
	
	/**
	 * 10th test
	 * cookies test
	 */
	//this test checks if cookies are being saved. run it after the login method.
	@Test
	public void cookiesTest(){
		//get all cookies
		Set<Cookie> cookies =  webDriver.manage().getCookies();
		if (cookies.size() > 0 ){
			//test passed
			saveResultsToExcel.put("CookiesTest", "Passed");
			System.out.println("Cookies Test Passed");
		}else{
			//test failed
			saveResultsToExcel.put("CookiesTest", "Failed");
			System.out.println("Cookies Test Failed");
		}
		for(Map.Entry<String, String> map : saveResultsToExcel.entrySet()){
			System.out.println("Key " +map.getKey());
			System.out.println("value "+map.getValue());
		}	
	}

		
	/**
	 * {@link Test} 11
	 * @param title
	 */
	//checks if title matches given title
	public void titleTest(String title){
		//go to website
		System.out.println(main_url);
        webDriver.get(main_url); 
        // get the title
        String actualTitle = webDriver.getTitle();

        //if title equal
        if (actualTitle.contentEquals(title)){
        	//test passed
        	saveResultsToExcel.put("TitleTest", "Passed");
            System.out.println("Title Test Passed!");
        } else {
        	//test failed
        	saveResultsToExcel.put("TitleTest", "Failed");
            System.out.println("Title Test Failed");
        }
        
        for(Map.Entry<String, String> map : saveResultsToExcel.entrySet()){
			System.out.println("Key " +map.getKey());
			System.out.println("value "+map.getValue());
		} 
	}
	public void capitalLoginTest(){
		
		webDriver.get(main_url); 
		waitForLoad(webDriver);
		
		//press login
		(webDriver.findElement(By.className("log"))).click(); //get seach field
		waitForLoad(webDriver);
		
		
		//get input form fields and send keys
		webDriver.findElement(By.id("username")).sendKeys("TEMP123@GMAIL.COM");
		webDriver.findElement(By.id("password")).sendKeys("TEMP123@GMAIL.COM");
		webDriver.findElement(By.id("loginToFoursquare")).submit();
		waitForLoad(webDriver);
		
		
		//look for the appropriate user message to handle error message
		webDriver.getCurrentUrl();
		if(webDriver.findElements(By.className("userPathLink")).size()>0){
			saveResultsToExcel.put("login Capital Test", "passed");
			System.out.println("login Capital Test passed");
		}
		else{
			saveResultsToExcel.put("login Capital Test", "failed");
			System.out.println("login Capital Test failed");
		}
	}
		
	
	//execute related test =======================================
	public void executeTest(int testNumber) throws InterruptedException{
		switch (testNumber) {
		case 1:
			searchTest(searchString);
			break;
		case 2:
			searchStressTesting(reallyLongString);
			break;
			
		case 3:
			searchButtonSubmissionStressTesting(searchString);
			break;
			
		case 4:
			loginTest(searchString);
			break;
			
		case 5:
			signUpTest();
			break;
			
		case 6:
			signUpLimitTesting();
			break;
			
		case 7:
			blackBoxTesting();
			break;
			
		case 8:
			sanityTesting();
			break;
			
		case 9:
			facebookSignUpTest();
			break;
			
		case 10:
			cookiesTest();
			break;
			
		case 11:
			titleTest(title);
			break;
			
		case 12:
			capitalLoginTest();
			break;
			
		case 0:
			searchTest(searchString);
			searchStressTesting(reallyLongString);
			searchButtonSubmissionStressTesting(searchString);
			loginTest(searchString);
			signUpTest();
			signUpLimitTesting();
			blackBoxTesting();
			sanityTesting();
			facebookSignUpTest();
			cookiesTest();
			titleTest(title);
			capitalLoginTest();
			break;
		default:
			break;
		}
	}
	
	//wait for page load by running javascript that checks if document is loaded.
	void waitForLoad(WebDriver driver) {
		
	    new WebDriverWait(driver, 10000).until( new Predicate<WebDriver>() {
            public boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        }
    );
	}
	
}
