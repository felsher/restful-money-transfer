package my.restful.homework.moneytransfer.service;

import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.error.AccountServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class AccountServiceTest {

    private final static int INITIAL_ACCOUNTS_BALANCE = 1_000;
    private final static int BALANCE_SUM = 1_000_000;
    private final static int ACCOUNTS_AMOUNT = 1_000;

    private List<Integer> accountIds;

    private AccountService accountService;

    @Before
    public void before() {
        accountService = new AccountService();
        accountIds = new ArrayList<>(ACCOUNTS_AMOUNT);

        createAccounts();
    }

    @Test
    public void testParallelTransferUsingStreamApi() {
        Random random = new Random();

        IntStream.range(0, ACCOUNTS_AMOUNT)
                .parallel()
                .forEach(e -> {
                    try {
                        accountService.transfer(
                                accountIds.get(random.nextInt(ACCOUNTS_AMOUNT)),
                                accountIds.get(random.nextInt(ACCOUNTS_AMOUNT)),
                                new BigDecimal(random.nextInt(50))
                        );
                    } catch (AccountServiceException ex) {
//                        System.err.println("Error Message: " + ex.getMessage()); //debug print
                    }
                });

        int sum = accountService.getTotalBalance();
        assertEquals(sum, BALANCE_SUM);
    }

    @Test
    public void testCreate_dummy() {
        BigDecimal accountBalance = new BigDecimal(1234.1234);
        int accountId;
        Account account = null;
        try {
            accountId = accountService.create(accountBalance);
            account = accountService.get(accountId);
        } catch (AccountServiceException e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        assertNotNull(account);
        assertNotNull(account.getBalance());
        assertEquals(accountBalance, account.getBalance());
    }

    @Test(expected = AccountServiceException.class)
    public void testCreateValidation() throws AccountServiceException {
        accountService.create(null);
    }

    @Test
    public void testList() throws AccountServiceException {
        List<Account> createdAccounts = accountService.list();
        assertEquals(1000, createdAccounts.size());
    }

    @Test
    public void testDeposit() {
        Optional<Account> optionalAccount = accountService.list().stream().findAny();

        assertTrue(optionalAccount.isPresent());
        assertNotNull(optionalAccount.get());

        int existingAccountId = optionalAccount.get().getId();
        BigDecimal existingAccountBalance = optionalAccount.get().getBalance();
        BigDecimal depositAmount = new BigDecimal(1234.1234);

        accountService.deposit(existingAccountId, depositAmount);

        Account updatedAccount = null;
        try {
            updatedAccount = accountService.get(existingAccountId);
        } catch (AccountServiceException e) {
            System.err.println(e.getMessage());
        }

        assertNotNull(updatedAccount);
        assertEquals(
                existingAccountBalance.add(depositAmount, new MathContext(16)),
                updatedAccount.getBalance()
        );
    }

    @Test(expected = AccountServiceException.class)
    public void testDelete() throws AccountServiceException {
        Optional<Account> optionalAccount = accountService.list().stream().findAny();

        assertTrue(optionalAccount.isPresent());
        assertNotNull(optionalAccount.get());

        int existingAccountId = optionalAccount.get().getId();

        accountService.delete(existingAccountId);

        accountService.get(existingAccountId);
    }

    @Test
    public void testGetTotalBalance() {
        int expectedTotalBalance = ACCOUNTS_AMOUNT * INITIAL_ACCOUNTS_BALANCE;
        int actualTotalBalance = accountService.getTotalBalance();
        assertEquals(expectedTotalBalance, actualTotalBalance);
    }

    private void createAccounts() {
        for (int i = 0; i < ACCOUNTS_AMOUNT; i++) {
            try {
                accountIds.add(accountService.create(new BigDecimal(INITIAL_ACCOUNTS_BALANCE)));
            } catch (AccountServiceException e) {
                e.printStackTrace();
            }
        }
    }
}
