import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class registrationTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.browserPosition = "0x0";
    }

    @Test
    void RegistrationTestSuccessful() {

        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        //убрать баннеры
        executeJavaScript("$('#RightSide_Advertisement').remove()");
        executeJavaScript("$('footer').remove()");

        //ввод данных
        $("#firstName").setValue("Alexandra");
        $("#lastName").setValue("Saveleva");
        $("#userEmail").setValue("alex@gmail.com");
        $("#genterWrapper").$(byText("Female")).click();
        $("#userNumber").setValue("1234567890");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("March");
        $(".react-datepicker__year-select").selectOption("2009");
        $(".react-datepicker__day--005:not(.react-datepicker__day--outside-month)").click();
        $("#subjectsInput").setValue("Economics").pressEnter();
        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#uploadPicture").uploadFromClasspath("img/1.png");
        $("#currentAddress").setValue("Some address 1");

        //не помещается в экран форма - прокрутить до поля stateCity-wrapper
        $("#stateCity-wrapper").scrollIntoView(true);

        $("#state").click();
        $("#stateCity-wrapper").$(byText("NCR")).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText("Delhi")).click();

        //не помещается в экран форма - прокрутить до поля конца страницы
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        $("#submit").click();

        // проверка
        $(".modal-dialog").should(appear);
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
        $(".table-responsive").shouldHave(
                text("Alexandra"),
                text("Saveleva"),
                text("alex@gmail.com"),
                text("1234567890"),
                text("05 March,2009"),
                text("Economics"),
                text("Sports"),
                text("1.png"),
                text("Some address 1"),
                text("NCR Delhi")
                );
    }
}
