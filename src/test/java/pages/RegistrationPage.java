package pages;

import com.codeborne.selenide.SelenideElement;
import pages.components.CalendarComponent;

import java.io.File;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class RegistrationPage {
    private final String TITLE_TEXT = "Student Registration Form";
    private CalendarComponent calendarComponent = new CalendarComponent();
    private RegistrationResultsModal registrationResultsModal = new RegistrationResultsModal();

    private SelenideElement
            firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            emailInput = $("#userEmail"),
            genderSection = $("#genterWrapper"),
            phoneInput = $("#userNumber"),
            calendarSection = $("#dateOfBirthInput"),
            subjectsContainerInput = $("#subjectsContainer input"),
            subjectsContainerAutoCompleteList = $(".subjects-auto-complete__menu"),
            hobbiesSection = $("#hobbiesWrapper"),
            imageUpdateField = $("input#uploadPicture"),
            currentAddressInput = $("#currentAddress"),
            stateField = $(byText("Select State")),
            cityField = $("#city"),
            submitButton = $("button#submit");

    public RegistrationPage openPage() {
        open("/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text(TITLE_TEXT));
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        return this;
    }

    public RegistrationPage setFirstName(String values) {
        firstNameInput.setValue(values);
        return this;
    }

    public RegistrationPage setLastName(String values) {
        lastNameInput.setValue(values);
        return this;
    }

    public RegistrationPage setEmail(String values) {
        emailInput.setValue(values);
        return this;
    }

    public RegistrationPage setGender(String values) {
        genderSection.$(byText(values)).click();
        return this;
    }

    public RegistrationPage setPhone(String values) {
        phoneInput.setValue(values);
        return this;
    }

    public RegistrationPage setCurrentAddressInput(String value) {
        currentAddressInput.setValue(value);
        return this;
    }

    public RegistrationPage setUserBirth(String day, String month, String year) {
        calendarSection.click();
        new CalendarComponent().setBirth(day, month, year);
        return this;
    }

    public RegistrationPage setSubjects(String... value) {
        for (String subject : value) {
            subjectsContainerInput.setValue(subject);
            subjectsContainerAutoCompleteList.$(byText(subject)).click();
        }
        return this;
    }

    public RegistrationPage setHobbies(@org.jetbrains.annotations.NotNull String... value) {
        for (String hobby : value) {
            hobbiesSection.$(byText(hobby)).click();
        }
        return this;
    }

    public RegistrationPage setImage(String fileName) {
        imageUpdateField.uploadFile(new File("src/test/resources/img/" + fileName));
        return this;
    }

    public RegistrationPage setState(String state) {
        stateField.click();
        $(byText(state)).click();
        return this;
    }

    public RegistrationPage setCity(String city) {
        //не помещается в экран форма - прокрутить до поля stateCity-wrapper
        $("#stateCity-wrapper").scrollIntoView(true);
        cityField.click();
        $(byText(city)).click();
        return this;
    }

    public RegistrationPage clickSubmitButton() {
        executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
        submitButton.click();
        return this;
    }

    public RegistrationPage verifyTableValues(String key, String value) {
        $(".table-responsive").$(byText(key)).sibling(0).shouldHave(text(value));
        return this;
    }

    public RegistrationPage RegistrationResultsModal() {
        registrationResultsModal.verifyModalAppears();
        return this;
    }


}
