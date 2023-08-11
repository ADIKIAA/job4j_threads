package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        boolean rsl = !accounts.containsKey(account.id());
        if (rsl) {
            accounts.put(account.id(), account);
        }
        return rsl;
    }

    public synchronized boolean update(Account account) {
        boolean rsl = accounts.containsKey(account.id());
        if (rsl) {
            accounts.put(account.id(), account);
        }
        return rsl;
    }

    public synchronized boolean delete(int id) {
        accounts.remove(id);
        return true;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        boolean rsl = !from.isEmpty() && !to.isEmpty();
        if (rsl) {
            update(new Account(fromId, from.get().amount() - amount));
            update(new Account(toId, to.get().amount() + amount));
        }
        return rsl;
    }
}
