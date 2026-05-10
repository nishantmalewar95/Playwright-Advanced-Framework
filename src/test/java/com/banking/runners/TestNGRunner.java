package com.banking.runners;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

/**
 * TestNG Runner class for programmatically running tests
 * This runner provides flexibility to configure and execute tests dynamically
 */
public class TestNGRunner {
    
    public static void main(String[] args) {
        // Create TestNG instance
        TestNG testNG = new TestNG();
        
        // Create Suite
        XmlSuite suite = new XmlSuite();
        suite.setName("Banking Application Test Suite");
        suite.setThreadCount(1);
        suite.setParallel(XmlSuite.ParallelMode.TESTS);
        
        // Create Test
        XmlTest test = new XmlTest(suite);
        test.setName("Banking Login Tests");
        
        // Add test classes
        List<XmlClass> classList = new ArrayList<>();
        classList.add(new XmlClass("com.banking.tests.LoginTest"));
        
        test.setXmlClasses(classList);
        
        // Add suite to TestNG
        List<XmlSuite> suiteList = new ArrayList<>();
        suiteList.add(suite);
        testNG.setXmlSuites(suiteList);
        
        // Run tests
        testNG.run();
    }
}
