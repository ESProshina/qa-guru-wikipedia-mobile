package wikipedia.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wikipedia.screens.OnboardingScreen;

import static io.qameta.allure.Allure.step;

@DisplayName("Поиск в Википедии")
public class SearchTest extends TestBase {

    @Test
    @DisplayName("Успешный поиск статьи 'Java'")
    public void successfulSearchJavaTest() {
        step("Пропустить онбординг и найти статью Java", () -> {
            new OnboardingScreen()
                    .skipOnboarding()
                    .clickSearch()
                    .closeTooltipIfVisible()
                    .typeQuery("Java")
                    .clickFirstSnippet() // <-- Убрали параметр "Java"
                    .checkArticleTitle("Java");
        });
    }

    @Test
    @DisplayName("Успешный поиск статьи 'Python'")
    public void successfulSearchPythonTest() {
        step("Пропустить онбординг и найти статью Python", () -> {
            new OnboardingScreen()
                    .skipOnboarding()
                    .clickSearch()
                    .closeTooltipIfVisible()
                    .typeQuery("Python")
                    .clickFirstSnippet() // <-- Убрали параметр "Python"
                    .checkArticleTitle("Python");
        });
    }
}