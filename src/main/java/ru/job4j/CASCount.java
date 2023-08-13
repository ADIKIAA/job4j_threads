package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {

    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int ref;
        do {
            ref = count.get() + 1;
            count.set(ref);
        } while (!count.compareAndSet(count.get(), ref));
    }

    public int get() {
        return count.get();
    }

}
