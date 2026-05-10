package com.banking.base;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import java.nio.file.Paths;
import com.banking.utils.DBUtils; // Naya import for Database cleanup

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void launchBrowser() {
        playwright = Playwright.create();
        // Headless false rakha hai taaki aap execution dekh sakein
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(250));
    }

    @BeforeMethod
    public void setupContext() {
        // Tracing start: Yeh har failure ka detailed 'Post-mortem' report dega
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("target/videos/")));
        
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        
        page = context.newPage();
    }

    @AfterMethod
    public void tearDown(org.testng.ITestResult result) {
        // Agar test fail hota hai, toh AI Service call hogi aur Trace file save hogi
        if (!result.isSuccess()) {
            // Failure ke waqt AI Service call hogi (Keeping your existing logic)
            com.banking.utils.AIService.analyzeFailure(result.getName(), result.getThrowable().getMessage());
            
            String tracePath = "target/traces/" + result.getName() + ".zip";
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(tracePath)));
            
            System.out.println("Test Failed! Trace saved at: " + tracePath);
        } else {
            context.tracing().stop();
        }
        context.close();
    }

    @AfterClass
    public void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    /**
     * NEW LOGIC: Suite level par DB connection close karna.
     * Isse aapka existing browser logic disturb nahi hoga.
     */
    @AfterSuite(alwaysRun = true)
    public void tearDownDB() {
        DBUtils.closeConnection();
        System.out.println("Global Cleanup: Database connection closed successfully.");
    }
}