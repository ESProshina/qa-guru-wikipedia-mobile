package wikipedia.screens;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.appium.SelenideAppium.$;
import static com.codeborne.selenide.appium.SelenideAppium.$$;

public class SearchScreen {

    private final SelenideElement searchField1 =
            $(AppiumBy.id("org.wikipedia.alpha:id/search_text_view"));
    private final SelenideElement searchField2 =
            $(AppiumBy.id("org.wikipedia.alpha:id/search_src_text"));

    private final SelenideElement closeTooltipButton =
            $(AppiumBy.xpath("//*[@content-desc='Close']"));

    @Step("Закрываем подсказку (если появилась)")
    public SearchScreen closeTooltipIfVisible() {
        sleep(2000);
        if (closeTooltipButton.exists()) {
            closeTooltipButton.click();
            sleep(1000);
        }
        return this;
    }

    @Step("Вводим поисковый запрос: {query}")
    public SearchScreen typeQuery(String query) {
        searchField1.shouldBe(visible).click();
        searchField2.shouldBe(visible).setValue(query).pressEnter();
        sleep(3000); // Ждем загрузки списка
        return this;
    }

    @Step("Кликаем по первому результату поиска")
    public SearchScreen clickFirstSnippet() {

        $$(AppiumBy.id("org.wikipedia.alpha:id/page_list_item_title"))
                .first()
                .shouldBe(visible, Duration.ofSeconds(30))
                .click();
        return this;
    }

    @Step("Проверяем заголовок статьи: {expected}")
    public SearchScreen checkArticleTitle(String expected) {

        sleep(3000);
        $(AppiumBy.xpath("//*[contains(@text, '" + expected + "')]"))
                .shouldBe(visible, Duration.ofSeconds(20));
        return this;
    }
}