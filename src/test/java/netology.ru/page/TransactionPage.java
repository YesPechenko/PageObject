package netology.ru.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import netology.ru.data.DataHelper;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransactionPage {
    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement fromField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");


    public  DashboardPage transactionCard (DataHelper.CardInfo CardInfo, int amount) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(CardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

    public void getNotification () {
        $(withText("На балансе недостаточно средств")).shouldBe(Condition.visible);
    }


}
