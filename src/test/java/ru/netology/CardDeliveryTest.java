package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private static GregorianCalendar today;
    private static GregorianCalendar date;
    private static SimpleDateFormat formater;

    @BeforeAll
    static void setUp(){
        Configuration.headless = true;
        Configuration.browser = "firefox";
        today = new GregorianCalendar();
        date = new GregorianCalendar();
        date.add(Calendar.DATE, 3);

        formater = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Test
    void shouldTest() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Вася");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();

        $("[data-test-id = 'notification']").waitUntil(Condition.cssClass("notification_visible"), 15_000);
    }

    @Test
    void shouldTestEmptyCity() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("");
        $(".button__content").click();
        $("[data-test-id = 'city'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWrongCity() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Кань");
        $(".button__content").click();
        $("[data-test-id = 'city'] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestEmptyDate() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue("");
        $(".button__content").click();
        $("[data-test-id = 'date'] .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void shouldTestWrongDate() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(today.getTime()));
        $(".button__content").click();
        $("[data-test-id = 'date'] .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestNonexistentDate() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue("45.00.2021");
        $(".button__content").click();
        $("[data-test-id = 'date'] .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void shouldTestEmptyName() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("");

        $(".button__content").click();
        $("[data-test-id = 'name'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWrongName() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Pertov Petr");
        $(".button__content").click();
        $("[data-test-id = 'name'] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestEmptyPhone() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Олег");
        $("[data-test-id = 'phone'] input").setValue("");
        $(".button__content").click();
        $("[data-test-id = 'phone'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWrongPhone() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Олег");
        $("[data-test-id = 'phone'] input").setValue("91245");
        $(".button__content").click();
        $("[data-test-id = 'phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestEmptyAgreement() {
        open("http://localhost:9999/");
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Олег");
        $("[data-test-id = 'phone'] input").setValue("+79012345678");
        $(".button__content").click();
        $("[data-test-id = 'agreement']").shouldHave(Condition.cssClass("input_invalid"));
    }

}
