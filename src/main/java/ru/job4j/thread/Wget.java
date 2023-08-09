package ru.job4j.thread;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Wget implements Runnable {

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        File file = new File("tmp.xml");
        try (InputStream in = new URL(this.url).openStream();
             OutputStream out = new FileOutputStream(file)) {
            byte[] buffer = new byte[512];
            int bytesRead;
            int sleepTime;
            while ((bytesRead = in.read(buffer, 0, buffer.length)) != -1) {
                long download = System.nanoTime();
                out.write(buffer, 0, buffer.length);
                sleepTime = (int) (512 / (System.nanoTime() - download)) / this.speed;
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validate(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        validate(url);
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
