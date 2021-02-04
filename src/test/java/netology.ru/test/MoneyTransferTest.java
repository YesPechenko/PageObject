package netology.ru.test;

import lombok.val;
import netology.ru.data.DataHelper;
import netology.ru.page.DashboardPage;
import netology.ru.page.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static netology.ru.data.DataHelper.getBalanceOfFirstCardAfterTransfer;
import static netology.ru.data.DataHelper.getBalanceOfSecondCardAfterTransfer;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

  @BeforeEach
  void setUp () {
    val loginPage = open("http://localhost:9999", LoginPage.class);
    val authInfo = DataHelper.getAuthInfo();
    val verificationPage = loginPage.validLogin(authInfo);
    val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
  }

  @Test
   void shouldTransactionFromFirstToSecond() {
     val dashboardPage = new DashboardPage();
     val amount = 500;
     val balanceOfFirstCardBefore = dashboardPage.getCardBalanceFirstCard();
     val balanceOfSecondCardBefore = dashboardPage.getCardBalanceSecondCard();
     val transactionPage = dashboardPage.pushSecondCard();
     val cardInfo = DataHelper.getFirstCardInfo();
     transactionPage.transactionCard(cardInfo, amount);
     val balanceAfterTransactionFirstCard = getBalanceOfSecondCardAfterTransfer (balanceOfSecondCardBefore, amount);
     val balanceAfterTransactionSecondCard = getBalanceOfFirstCardAfterTransfer(balanceOfFirstCardBefore, amount);
     val balanceOfFirstCardAfter = dashboardPage.getCardBalanceFirstCard();
     val balanceOfSecondCardAfter = dashboardPage.getCardBalanceSecondCard();
     assertEquals(balanceAfterTransactionFirstCard, balanceOfFirstCardAfter);
     assertEquals(balanceAfterTransactionSecondCard, balanceOfSecondCardAfter);
  }

   @Test
   void shouldTransactionFromSecondToFirst() {
     val dashboardPage = new DashboardPage();
     val amount = 1000;
     val balanceOfFirstCardBefore = dashboardPage.getCardBalanceFirstCard();
     val balanceOfSecondCardBefore = dashboardPage.getCardBalanceSecondCard();
     val transactionPage = dashboardPage.pushSecondCard();
     val cardInfo = DataHelper.getSecondCardInfo();
     transactionPage.transactionCard(cardInfo, amount);
     val balanceAfterTransferFirstCard = DataHelper.getBalanceOfSecondCardAfterTransfer(balanceOfSecondCardBefore, amount);
     val balanceAfterTransferSecondCard = DataHelper.getBalanceOfFirstCardAfterTransfer(balanceOfFirstCardBefore, amount);
     val balanceOfFirstCardAfter = dashboardPage.getCardBalanceFirstCard();
     val balanceOfSecondCardAfter = dashboardPage.getCardBalanceSecondCard();;
     assertEquals(balanceAfterTransferFirstCard, balanceOfFirstCardAfter);
     assertEquals(balanceAfterTransferSecondCard, balanceOfSecondCardAfter);
  }

   @Test
   void shouldNotTransferMoreThanRestOfBalance() {
     val dashboardPage = new DashboardPage();
     val amount = 10000;
     val transactionPage = dashboardPage.pushFirstCard();
     val cardInfo = DataHelper.getSecondCardInfo();
     transactionPage.transactionCard(cardInfo, amount);
     transactionPage.getNotification();
  }

}

