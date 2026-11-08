package wikipedia.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wikipedia.screens.OnboardingScreen;

import static io.qameta.allure.Allure.step;

@DisplayName("Онбординг Wikipedia")
public class OnboardingTest extends TestBase {

    @Test
    @DisplayName("Прохождение 4 экранов онбординга с проверками")
    public void onboardingTest() {
        OnboardingScreen onboarding = new OnboardingScreen();

        step("Проверить все 4 экрана онбординга и пропустить его", () -> {
            onboarding
                    .checkKnowledgeScreen()
                    .clickForward()
                    .checkDataPrivacyScreen()
                    .clickForward()
                    .checkLanguagesScreen()
                    .clickForward()
                    .checkCuriosityScreen()
                    .clickSkip()
                    .checkOpened();
        });
    }
}