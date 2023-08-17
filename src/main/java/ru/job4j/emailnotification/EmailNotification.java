package ru.job4j.emailnotification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String subject = String.format("Notification %s to email %s.", user.getName(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getName());
        pool.submit(() -> {
           send(subject, body, user.getEmail());
        });
    }

    public void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
        System.out.println("method send..." + Thread.currentThread().getName());
    }

}
