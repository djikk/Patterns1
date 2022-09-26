package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
//import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully plan and replan meeting")
    void shouldSuccessfullyPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("span[data-test-id='city'] input").setValue(validUser.getCity());
        $("span[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("span[data-test-id='date'] input").setValue(firstMeetingDate);
        $("span[data-test-id='name'] input").setValue(validUser.getName());
        $("span[data-test-id='phone'] input").setValue(validUser.getPhone());
        $x("//label[@data-test-id='agreement']/span").click();
        $("button .button__text").click();

        $("div[data-test-id='success-notification']").should(Condition.visible, Duration.ofSeconds(15));
        String notification__content = "Встреча успешно запланирована на ";
        $("div.notification__content").shouldHave(Condition.text(notification__content + firstMeetingDate));

        $("span[data-test-id='date'] input").doubleClick().sendKeys(Keys.DELETE);
        $("span[data-test-id='date'] input").setValue(secondMeetingDate);
        $("button .button__text").click();
        $("div[data-test-id='replan-notification']").should(Condition.visible);
        String replan__button__text = "Перепланировать";
        $("div[data-test-id='replan-notification'] .button__text").shouldHave(Condition.text(replan__button__text));
        $("div[data-test-id='replan-notification'] .button__text").click();

        $("div[data-test-id='success-notification']").should(Condition.visible, Duration.ofSeconds(15));
        $("div.notification__content").shouldHave(Condition.text(notification__content + secondMeetingDate));
    }

}
// TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе




