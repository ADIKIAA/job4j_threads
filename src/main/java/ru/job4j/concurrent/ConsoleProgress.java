package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    private char[] process = new char[] {'-', '\\', '|', '/', '-', '\\', '|', '/'};

    @Override
    public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    for (char ch : process) {
                        Thread.sleep(500);
                        System.out.print("\r load: " + ch);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args0) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }

}
