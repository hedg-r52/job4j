package ru.job4j.bank;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Класс для тестирования класса Bank
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class BankTest {
    @Test
    public void whenAddUserThenFindThatUserByPassport() {
        Bank bank = new Bank();
        User expected = new User("user", "2200 123321");
        bank.addUser(expected);
        User result = bank.getUserByPassport("2200 123321");
        assertThat(result, is(expected));
    }

    @Test
    public void whenDelUserThenNotFindThatUserByPassword() {
        Bank bank = new Bank();
        User user = new User("user", "2200 123321");
        bank.addUser(user);
        bank.deleteUser(user);
        User result = bank.getUserByPassport("2200 123321");
        assertThat(result.isNull(), is(true));
    }

    @Test
    public void whenAddAccountToUserThenFindAccountByRequisite() {
        Bank bank = new Bank();
        User user = new User("user", "2200 123321");
        bank.addUser(user);
        Account expected = new Account(0.0, "0123456789");
        bank.addAccountToUser("2200 123321", expected);
        Account result = bank.getAccountByRequisite(user, "0123456789");
        assertThat(result, is(expected));
    }

    @Test
    public void whenDelUserAccountThenNotFindAccountByRequisites() {
        Bank bank = new Bank();
        User user = new User("user", "2200 123321");
        bank.addUser(user);
        Account account = new Account(0.0, "0123456789");
        bank.addAccountToUser("2200 123321", account);
        bank.deleteAccountFromUser("2200 123321", account);
        Account result = bank.getAccountByRequisite(user, "0123456789");
        assertThat(result.isNull(), is(true));
    }

    @Test
    public void whenGetUserAccounts() {
        Bank bank = new Bank();
        String passport = "2200 123321";
        User user = new User("user", passport);
        bank.addUser(user);
        Account account1 = new Account(0.0, "0123456789");
        Account account2 = new Account(0.0, "1234567890");
        bank.addAccountToUser(passport, account1);
        bank.addAccountToUser(passport, account2);
        List<Account> list = bank.getUserAccounts(passport);
        assertThat(list.toArray(), is(new Account[] {account1, account2}));
    }

    @Test
    public void whenTransferMoneyFromOneAccountToTwoThenOneLessTwoMore() {
        Bank bank = new Bank();
        String srcPassport = "2200 123321";
        String dstPassport = "2200 987789";
        String srcRequisite = "0000000001";
        String dstRequisite = "0000000002";
        User srcUser = new User("src user", srcPassport);
        bank.addUser(srcUser);
        bank.addAccountToUser(srcPassport, new Account(100.0D, srcRequisite));
        User dstUser = new User("dst user", dstPassport);
        bank.addUser(dstUser);
        bank.addAccountToUser(dstPassport, new Account(0.0D, dstRequisite));
        bank.transferMoney(srcPassport, srcRequisite, dstPassport, dstRequisite, 50.0D);
        assertThat(bank.getAccountByRequisite(srcUser, srcRequisite).getValues(), is(50.0D));
        assertThat(bank.getAccountByRequisite(dstUser, dstRequisite).getValues(), is(50.0D));
    }

}