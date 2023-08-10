package ru.job4j;

public class DCLSingleton {

    private volatile static DCLSingleton inst;

    public synchronized static DCLSingleton instOf() {
        if (inst == null) {
            inst = new DCLSingleton();
        }
        return inst;
    }

    private DCLSingleton() {
    }
}
