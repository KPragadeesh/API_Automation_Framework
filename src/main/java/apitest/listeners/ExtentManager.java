package apitest.listeners;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentManager {
	
	private static ExtentReports extent;
	 
    public synchronized static ExtentReports getReporter() {
        if (extent == null) {
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
            	String path = workingDir+"\\extendReports\\indexWindows.html";
        		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        		reporter.config().setTheme(Theme.DARK);
        		reporter.config().setReportName("Test API Automation");
        		reporter.config().setDocumentTitle("Test Results");
        		extent = new ExtentReports();
        		extent.attachReporter(reporter);
        		extent.setSystemInfo("Pragadeesh", "SDET");
            }
            else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            	String path = workingDir+"\\extendReports\\indexMac.html";
        		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        		reporter.config().setReportName("Test API Automation");
        		reporter.config().setDocumentTitle("Test Results");
        		extent = new ExtentReports();
        		extent.attachReporter(reporter);
        		extent.setSystemInfo("Pragadeesh", "SDET");
            }
        }
        return extent;
    }
}
