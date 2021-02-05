package netology.ru.test;

import lombok.val;
import netology.ru.data.DataHelper;
import netology.ru.page.DashboardPage;
import netology.ru.page.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static netology.ru.data.DataHelper.getBalanceOfSecondCardAfterTransfer;
import static netology.ru.data.DataHelper.getBalanceOfFirstCardAfterTransfer;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

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
     val balanceAfterTransactionFirstCard = getBalanceOfFirstCardAfterTransfer (balanceOfFirstCardBefore, amount);
     val balanceAfterTransactionSecondCard = getBalanceOfSecondCardAfterTransfer(balanceOfSecondCardBefore, amount);
     val balanceOfFirstCardAfter = dashboardPage.getCardBalanceFirstCard();
     val balanceOfSecondCardAfter = dashboardPage.getCardBalanceSecondCard();
     assertEquals(balanceAfterTransactionFirstCard, balanceOfFirstCardAfter);
     assertEquals(balanceAfterTransactionSecondCard, balanceOfSecondCardAfter);
  }

   @Test
   void shouldTransactionFromSecondToFirst() {
     val dashboardPage = new DashboardPage();
     val amount = 800;
     val balanceOfFirstCardBefore = dashboardPage.getCardBalanceFirstCard();
     val balanceOfSecondCardBefore = dashboardPage.getCardBalanceSecondCard();
     val transactionPage = dashboardPage.pushFirstCard();
     val cardInfo = DataHelper.getSecondCardInfo();
     transactionPage.transactionCard(cardInfo, amount);
     val balanceAfterTransactionFirstCard = getBalanceOfSecondCardAfterTransfer(balanceOfFirstCardBefore, amount);
     val balanceAfterTransactionSecondCard = getBalanceOfFirstCardAfterTransfer(balanceOfSecondCardBefore, amount);
     val balanceOfFirstCardAfter = dashboardPage.getCardBalanceFirstCard();
     val balanceOfSecondCardAfter = dashboardPage.getCardBalanceSecondCard();
     assertEquals(balanceAfterTransactionFirstCard, balanceOfFirstCardAfter);
     assertEquals(balanceAfterTransactionSecondCard, balanceOfSecondCardAfter);
  }

  @Test
  void shouldNotTransactionMoreThanRestOfBalance() {
    val dashboardPage = new DashboardPage();
    val amount = 10500;
    val transactionPage = dashboardPage.pushFirstCard();
    val cardInfo = DataHelper.getSecondCardInfo();
    transactionPage.transactionCard(cardInfo, amount);
    transactionPage.getNotification();
  }








}

