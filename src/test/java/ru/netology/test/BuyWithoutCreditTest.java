package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DBHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyWithoutCreditTest {
    MainPage mainPage;
    PaymentPage paymentPage;

    @BeforeEach
    void shouldOpenWeb() {
        DBHelper.cleanDataBase();
        mainPage = open("http://localhost:8080", MainPage.class);
        paymentPage = mainPage.buyWithoutCredit();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void shouldApproveFirstCard() {  /* Покупка тура при вводе валидных данных карты*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectApprovalFromBank();
        val expected = DataHelper.getFirstCardExpectedStatus();
        val actual = DBHelper.getStatusPaymentWithoutCredit();
        assertEquals(expected, actual);

    }

    @Test
    void shouldRejectSecondCard() { /*Покупка тура при вводе невалидных данных карты*/
        val cardNumber = DataHelper.getSecondCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectRejectionFromBank();
        val expected = DataHelper.getSecondCardExpectedStatus();
        val actual = DBHelper.getStatusPaymentWithoutCredit();
        assertEquals(expected, actual);
    }

    @Test
    void сheckingIncompleteData() { /*Покупка тура при вводе неполных данных*/
        val cardNumber = DataHelper.getCardNumberIncomplete();
        val month = DataHelper.getIncompleteMonth();
        val year = DataHelper.getIncompleteYear();
        val owner = DataHelper.getIncompleteOwner();
        val cvс = DataHelper.getIncompleteCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void сheckingСardNumberRequestedData() { /*Покупка тура при вводе одинаковых цифр номера карты*/
        val cardNumber = DataHelper.getCardNumberRequestedData();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectRejectionFromBank();
    }

    @Test
    void сheckingСardNumberZero() { /*Покупка тура при вводе нулей в поле номер карты */
        val cardNumber = DataHelper.getCardNumberZero();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingСardNumberUnderLimit() { /*Покупка тура при вводе 4 цифр в поле номер карты */
        val cardNumber = DataHelper.getCardNumberUnderLimit();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingСardNumberOverLimit() { /*Покупка тура при вводе 17 цифр в поле номер карты */
        val cardNumber = DataHelper.getCardNumberOverLimit();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.expectApprovalFromBank();
    }

    @Test
    void сheckingСardWithText() { /*Покупка тура при вводе текста в поле номер карты */
        val cardNumber = DataHelper.getValueText();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectInvalidMonth() { /*Покупка тура при вводе невалидного месяца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getInvalidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void checkingMonthWithText() { /*Покупка тура при вводе букв в поле месяца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper. getValueText();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingMonthOverLimit() { /*Покупка тура при вводе трех цифр в поле месяца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getDurationOverLimit();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidDuration();
    }

    @Test
    void shouldRejectZeroMonth() { /*Покупка тура при вводе нулевого месяца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getZeroValue();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }


    @Test
    void shouldRejectPastMonth() {  /*Покупка тура по карте с истекшим сроком годности*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getPastMonth();
        val year = DataHelper.getThisYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldRejectInvalidYear() { /*Покупка тура при вводе невалидного года*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidYear();
    }

    @Test
    void shouldRejectZeroYear() { /*Покупка тура при вводе нулевого года*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getZeroValue();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingYearWithText() { /*Покупка тура при вводе букв в поле года*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper. getValidMonth();
        val year = DataHelper.getValueText();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingYearOverLimit() { /*Покупка тура при вводе трех цифр в поле года*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getDurationOverLimit();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingOwnerUnderLimit() { /*Покупка тура при вводе одной буквы в поле владельца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getUnderLimitOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingOwnerOverLimit() { /*Покупка тура при вводе 20 букв слитно в поле владельца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getOverLimitOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerWithoutSpaces() { /*Покупка тура при вводе имени и фамилии владельца слитно*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getDataWithoutSpaces();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerWithNumbers() { /*Покупка тура при вводе цифр в поле владелец*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getOwnerNumbers();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerRus() { /*Покупка тура при вводе владельца на русском языке*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwnerRus();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void сheckingOwnerOnlySurname() { /*Покупка тура при вводе только фамилии в поле владельца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getOwnerOnlySurname();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerLowerCase() { /*Покупка тура при вводе строчных букв в поле владельца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getOwnerLowerCase();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingOwnerInvalid() { /*Покупка тура при вводе невалидного владельца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getInvalidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectInvalidCvс() { /*Покупка тура при вводе невалидного cvc*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getInvalidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectZeroCvc() { /*Покупка тура при вводе нулевого cvc*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getZeroCvv();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingCVVOverLimit() { /*Покупка тура при вводе cvc сверх лимита*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getCvcOverLimit();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void checkingCVVWithText() { /*Покупка тура при вводе текста в поле cvc*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValueText();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitInvalidFormat();
    }

    @Test
    void shouldRejectEmptyNumberCard() { /*Покупка тура при отсутствии ввода номера карты*/
        val cardNumber = DataHelper.getEmptyValue();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyMonth() { /*Покупка тура при отсутствии ввода месяца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getEmptyValue();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyYear() { /*Покупка тура при отсутствии ввода года*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getEmptyValue();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyCvс() { /*Покупка тура при отсутствии ввода cvc*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvс = DataHelper.getEmptyValue();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

    @Test
    void shouldRejectEmptyOwner() { /*Покупка тура при отсутствии ввода владельца*/
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getEmptyValue();
        val cvс = DataHelper.getValidCvс();
        paymentPage.fillOutFields(cardNumber, month, year, owner, cvс);
        paymentPage.waitNecessaryFillOutField();
    }

}
