package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    private char[] process = new char[] {'-', '\\', '|', '/', '-', '\\', '|', '/'};

    @Override
    public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    for (int i = 0; i < 8; i++) {
                        Thread.sleep(500);
                        System.out.print("\r load: " + process[i]);
                        if (i == 8) {
                            i = 0;
                        }
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
