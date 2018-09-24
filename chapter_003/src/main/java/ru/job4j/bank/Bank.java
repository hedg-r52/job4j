package ru.job4j.bank;

import java.util.*;

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
        this.users = new TreeMap<>();
    }

    public void addUser(User user) {
        this.users.putIfAbsent(user, new ArrayList<>());
    }

    public void deleteUser(User user) {
        this.users.remove(user);
    }

    public void addAccountToUser(String passport, Account account) {
        Optional<User> user = getUserByPassport(passport);
        user.ifPresent(u -> this.users.get(u).add(account));
    }

    public void deleteAccountFromUser(String passport, Account account) {
        Optional<User> user = getUserByPassport(passport);
        user.ifPresent(u -> this.users.get(u).remove(account));
    }

    public List<Account> getUserAccounts(String passport) {
        List<Account> result = new ArrayList<>();
        Optional<User> user = getUserByPassport(passport);
        if (user.isPresent()) {
            result = this.users.get(user.get());
        }
        return result;
    }

    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String dstPassport, String dstRequisite, double amount) {
        Optional<User> srcUser = getUserByPassport(srcPassport);
        Optional<User> dstUser = getUserByPassport(dstPassport);
        boolean result = false;
        if (srcUser.isPresent() && dstUser.isPresent()) {
            Optional<Account> srcAccount = getAccountByRequisite(srcUser.get(), srcRequisite);
            Optional<Account> dstAccount = getAccountByRequisite(dstUser.get(), dstRequisite);
            if (srcAccount.isPresent() && dstAccount.isPresent() && (srcAccount.get().getValues() >= amount)) {
                srcAccount.get().setValues(srcAccount.get().getValues() - amount);
                dstAccount.get().setValues(dstAccount.get().getValues() + amount);
                result = true;
            }
        }
        return result;
    }

    protected Optional<User> getUserByPassport(String passport) {
        return this.users.keySet().stream().filter(u -> passport.equals(u.getPassport())).findFirst();
    }

    protected Optional<Account> getAccountByRequisite(User user, String requisite) {
        return this.users.get(user).stream().filter(account -> requisite.equals(account.getRequisites())).findFirst();
    }
}
