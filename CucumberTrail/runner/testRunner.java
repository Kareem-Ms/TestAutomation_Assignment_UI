package org.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src\\main\\resources", glue = {"org/Steps", "org/Hooks"})
public class testRunner extends AbstractTestNGCucumberTests {

}
