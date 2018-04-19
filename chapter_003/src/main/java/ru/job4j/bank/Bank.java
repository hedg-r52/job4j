package ru.job4j.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Банк
 *
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Bank {
    private Map<User, List<Account>> users;

    public Bank() {
        this.users = new TreeMap<User, List<Account>>();
    }

    public void addUser(User user) {
        this.users.putIfAbsent(user, new ArrayList<>());
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }

    public void addAccountToUser(String passport, Account account) {
        User user = getUserByPassport(passport);
        if (!user.isNull()) {
            this.users.get(user).add(account);
        }
    }

    public void deleteAccountFromUser(String passport, Account account) {
        User user = getUserByPassport(passport);
        if (!user.isNull()) {
            this.users.get(user).remove(account);
        }
    }

    public List<Account> getUserAccounts(String passport) {
        List<Account> result = new ArrayList<Account>();
        User user = getUserByPassport(passport);
        if (!user.isNull()) {
            result = this.users.get(user);
        }
        return result;
    }

    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String dstPassport, String dstRequisite, double amount) {
        User srcUser = getUserByPassport(srcPassport);
        User dstUser = getUserByPassport(dstPassport);
        Account srcAccount = getAccountByRequisite(srcUser, srcRequisite);
        Account dstAccount = getAccountByRequisite(dstUser, dstRequisite);
        boolean result = false;
        if (!(srcUser.isNull() || dstUser.isNull() || srcAccount.isNull() || dstAccount.isNull())) {
            if (srcAccount.getValues() >= amount) {
                srcAccount.setValues(srcAccount.getValues() - amount);
                dstAccount.setValues(dstAccount.getValues() + amount);
                result = true;
            }
        }
        return result;
    }

    protected User getUserByPassport(String passport) {
        User result = new NullUser();
        for (User user : this.users.keySet()) {
            if (passport.equals(user.getPassport())) {
                result = user;
            }
        }
        return result;
    }

    protected Account getAccountByRequisite(User user, String requisite) {
        Account result = new NullAccount(0D, "");
        for (Account account : this.users.get(user)) {
            if (requisite.equals(account.getRequisites())) {
                result = account;
                break;
            }
        }
        return result;
    }
}
