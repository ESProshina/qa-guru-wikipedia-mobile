package wikipedia.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import wikipedia.drivers.AndroidDriverProvider;

import java.io.ByteArrayInputStream;
import java.util.Base64;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        if (System.getProperty("deviceHost") == null) {
            System.setProperty("deviceHost", "emulation");
        }
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        Configuration.pageLoadTimeout = -1;
        Configuration.browser = AndroidDriverProvider.class.getName();
        Configuration.browserSize = null;
        Configuration.timeout = 30000;
        Configuration.screenshots = true;
        Configuration.savePageSource = false;
    }

    //    @BeforeEach
//    void beforeEach() {
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
//                .screenshots(true)
//                .savePageSource(false));
//
//        com.codeborne.selenide.Selenide.open();
//
//        try {
//            ((AndroidDriver) WebDriverRunner.getWebDriver()).startRecordingScreen();
//        } catch (Exception e) {
//            System.out.println("Не удалось начать запись видео: " + e.getMessage());
//        }
//    }
//
//    @AfterEach
//    void afterEach() {
//        if (WebDriverRunner.hasWebDriverStarted()) {
//            try {
//
//                String videoBase64 = ((AndroidDriver) WebDriverRunner.getWebDriver()).stopRecordingScreen();
//                if (videoBase64 != null && !videoBase64.isEmpty()) {
//                    Allure.addAttachment("Video", "video/mp4",
//                            new ByteArrayInputStream(Base64.getDecoder().decode(videoBase64)), ".mp4");
//                }
//
//
//                byte[] screenshotBytes = com.codeborne.selenide.Selenide.screenshot("final_screenshot").getBytes();
//                Allure.addAttachment("Final Screenshot", new ByteArrayInputStream(screenshotBytes));
//            } catch (Exception e) {
//                System.out.println("Не удалось остановить запись видео или сделать скриншот: " + e.getMessage());
//            }
//        }
//        closeWebDriver();
//    }
//}
    @BeforeEach
    void beforeEach() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));

        com.codeborne.selenide.Selenide.open();

        if (!"browserstack".equals(System.getProperty("deviceHost"))) {
            try {
                ((AndroidDriver) WebDriverRunner.getWebDriver()).startRecordingScreen();
            } catch (Exception e) {
                System.out.println("Не удалось начать запись видео: " + e.getMessage());
            }
        }
    }

    @AfterEach
    void afterEach() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            try {
                // НЕ ОСТАНАВЛИВАЕМ ВИДЕО, ЕСЛИ МЫ В БРАУЗЕРСТЕКЕ
                if (!"browserstack".equals(System.getProperty("deviceHost"))) {
                    String videoBase64 = ((AndroidDriver) WebDriverRunner.getWebDriver()).stopRecordingScreen();
                    if (videoBase64 != null && !videoBase64.isEmpty()) {
                        Allure.addAttachment("Video", "video/mp4",
                                new ByteArrayInputStream(Base64.getDecoder().decode(videoBase64)), ".mp4");
                    }
                }

                byte[] screenshotBytes = com.codeborne.selenide.Selenide.screenshot("final_screenshot").getBytes();
                Allure.addAttachment("Final Screenshot", new ByteArrayInputStream(screenshotBytes));
            } catch (Exception e) {
                System.out.println("Не удалось остановить запись видео или сделать скриншот: " + e.getMessage());
            }
        }
        closeWebDriver();
    }
}