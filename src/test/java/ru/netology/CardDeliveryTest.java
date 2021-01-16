package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private static GregorianCalendar today;
    private static GregorianCalendar date;
    private static SimpleDateFormat formater;
    private static LocalDate date1;

    @BeforeAll
    static void setAll(){
        Configuration.headless = true;
        Configuration.browser = "firefox";
        today = new GregorianCalendar();
        date = new GregorianCalendar();
        date.add(Calendar.DATE, 3);

        formater = new SimpleDateFormat("dd.MM.yyyy");
    }

    @BeforeEach
    void setUp(){
        open("http://localhost:9999/");
    }

    @Test
    void shouldSuccessfulSendForm() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Вася");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'notification']").waitUntil(Condition.visible, 15_000);
        //$("[data-test-id = 'notification']").waitUntil(Condition.cssClass("notification_visible"), 15_000);
    }

    @Test
    void shouldTestEmptyCity() {
        $("[data-test-id = 'city'] input").setValue("");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Вася");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'city'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWrongCity() {
        $("[data-test-id = 'city'] input").setValue("Кань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Вася");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'city'] .input__sub").shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestEmptyDate() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue("");
        $("[data-test-id = 'name'] input").setValue("Петров Вася");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'date'] .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void shouldTestWrongDate() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(today.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Вася");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'date'] .input__sub").shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void shouldTestNonexistentDate() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue("45.00.2021");
        $("[data-test-id = 'name'] input").setValue("Петров Вася");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'date'] .input__sub").shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    void shouldTestEmptyName() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'name'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWrongName() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Pertov Petr");
        $("[data-test-id = 'phone'] input").setValue("+79567891245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'name'] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldTestEmptyPhone() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Олег");
        $("[data-test-id = 'phone'] input").setValue("");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'phone'] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestWrongPhone() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Олег");
        $("[data-test-id = 'phone'] input").setValue("91245");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button__content").click();
        $("[data-test-id = 'phone'] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldTestEmptyAgreement() {
        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").setValue(formater.format(date.getTime()));
        $("[data-test-id = 'name'] input").setValue("Петров Олег");
        $("[data-test-id = 'phone'] input").setValue("+79012345678");
        $(".button__content").click();
        $("[data-test-id = 'agreement']").shouldHave(Condition.cssClass("input_invalid"));
    }

}
