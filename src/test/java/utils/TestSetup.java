package utils;

import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.nio.file.*;

public class TestSetup
{
    @BeforeSuite
    public void cleanAllureResults() {
        try {
            Path allureDir = Paths.get("target/allure-results");
            if (Files.exists(allureDir)) {
                Files.walk(allureDir)
                        .map(Path::toFile)
                        .forEach(File::delete);
                System.out.println("✅ Cleaned old Allure results.");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Failed to clean Allure results: " + e.getMessage());
        }
    }
}
