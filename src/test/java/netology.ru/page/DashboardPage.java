package netology.ru.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");
  private ElementsCollection cardsInfo =$$(".list__item [data-test-id]");
  private static SelenideElement firstBalance = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] .button");
  private static SelenideElement secondBalance = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] .button");
  private final String balanceStart = "баланс: ";
  private final String balanceFinish = " р.";


  public DashboardPage() {
    heading.shouldBe(visible);
  }


  public TransactionPage pushFirstCard() {
    firstBalance.click();
    return new TransactionPage();
  }

  public TransactionPage pushSecondCard() {
    secondBalance.click();
    return new TransactionPage();
  }

  public int getCardBalanceFirstCard () {
    val cardBalance = $(".list__item [data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0']").getText();
    return getExtractBalance(cardBalance);
  }

  public int getCardBalanceSecondCard () {
    val cardBalance = $(".list__item [data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d']").getText();
    return getExtractBalance(cardBalance);
  }

  private int getExtractBalance(String cardBalance) {
    val start = cardBalance.indexOf(balanceStart);
    val finish = cardBalance.indexOf(balanceFinish);
    val value = cardBalance.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }

}
