# TestAutomation_Assignment_UI

## The main Frameworks included in the project:
- Selenium
- TestNG
- Allure Report
- Json Reader for Data management

## Important Note
I tried to implelement the test cases using cucumber but id doesn't work perfectly as i am still learning cucumber so I included a folder named "Cucumber trail" contain the files that I have worked on it but it will not run and it's not part of the project

## Project Design:
- Page Object Model (POM) design pattern
- Data Driven framework
- Parrarel test case execution
- Have a supporting Utilities package in src/main/java file path, named "utils" that includes many wrapper methods which services the project like ElementsAction class

## Steps to Execute Code
- Clone the code from the Repository 
- Open POM.xml file then reload that file to install dependecies
- Go to src/main/resources/saucedemo.properties and modify the Browser name choosing between "chrome", "firefox"
- Go to testng.xml file then execute the file
- You can access allure report by executing the following command "allure serve" in terminal after running the code

## Code Explanation
- in the src/main/java/org you will find a package called "pages" this package used to include all the pages that will be used in testing so for example the "LoginPage" class contain methods and locators that exist in login page in order to apply POM design pattern
- in the src/main/java/org folder there is a package called "utils" this package contain helper classes like:
    - "ElementsAction" which is designed to handel find element after applying waits then make interactions with that element.
    - "PropertiesManager" this class contains methods to read from a property file which exist under src/main/resources to access something like BaseUrl and BrowserName
    - "JsonFileManager" this class is used to read data from json file to inject these data in the test classes 
- in the src/test/java/org you will find a package called "testData" this package contains one json file per each test case to achieve isolation
- in the src/test/java/org you will find a package called "tests" this package contain two test classes
  - "CheckoutTest.java" this class have mainly one E2E test scenario which is the checkout test scenario
  - "InvalidLoginTest.java" this class have one test case related to invalid login
