package my.restful.homework.moneytransfer.service;

import my.restful.homework.moneytransfer.entity.Account;
import my.restful.homework.moneytransfer.error.AccountServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class AccountService {

    private final AtomicInteger dummyCounter = new AtomicInteger(0);

    private final ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();

    private static AccountService accountService;


    public void transfer(int from, int to, BigDecimal amount) throws AccountServiceException {
        if (from == to)
            throw new AccountServiceException(String.format("Transfer failed -> Account identifiers are the same -> from[%s], to[%s]", from, to));

        Account sourceAccount = accounts.get(from);
        Account targetAccount = accounts.get(to);

        if (sourceAccount == null)
            throw new AccountServiceException(String.format("Transfer failed -> source Account[id:%s] not exists", from));

        if (targetAccount == null)
            throw new AccountServiceException(String.format("Transfer failed -> target Account[id:%s] not exists", to));

        Object lock1 = (from < to) ? sourceAccount : targetAccount;
        Object lock2 = (from < to) ? targetAccount : sourceAccount;

        synchronized (lock1) {
            synchronized (lock2) {
                if (sourceAccount.hasSufficientBalance(amount)) {
                    sourceAccount.withdraw(amount);
                    targetAccount.deposit(amount);

//                    debugPrint(from, to, amount);

                } else {
                    throw new AccountServiceException(String.format("Transfer failed -> Account[id:%s] do not have sufficient balance", sourceAccount.getId()));
                }
            }
        }
    }

    /**
     * You can use this method, if you wanna see how parallel transfers test working
     * */
    private void debugPrint(int from, int to, BigDecimal amount) {
        String message = "Thread: %s transfered amount %s from %s to %s. Total balance at the moment: %s\n";
        String threadName = Thread.currentThread().getName();
        System.out.println(String.format(
                message,
                threadName,
                amount,
                from,
                to,
                getTotalBalance())
        );
    }

    public int getTotalBalance() {
        return list().stream().mapToInt(a -> a.getBalance().intValue()).sum();
    }

    public int create(BigDecimal initialBalance) throws AccountServiceException {
        int id = generateAccountId();
        Account account = accounts.putIfAbsent(
                id,
                new Account(id, initialBalance)
        );
        if (account != null) {
            throw new AccountServiceException(String.format("Account[id:%s] already exists", id));
        }
        return id;
    }

    public Account get(int id) throws AccountServiceException {
        Account account = accounts.get(id);
        if (account == null) {
            throw new AccountServiceException("Account[id:%s] not exists");
        }
        return account;
    }

    public List<Account> list() {
        return accounts.values()
                .stream()
                .collect(Collectors.toList());
    }

    public void delete(int id) {
        accounts.remove(id);
    }

    public void deposit(int id, BigDecimal amount) {
        accounts.computeIfPresent(id, (key, account) -> {
            account.deposit(amount);
            return account;
        });
    }

    private int generateAccountId() {
        return dummyCounter.getAndIncrement();
    }

    public static synchronized AccountService getInstance() {
        if (accountService == null) {
            accountService = new AccountService();
        }
        return accountService;
    }
}
