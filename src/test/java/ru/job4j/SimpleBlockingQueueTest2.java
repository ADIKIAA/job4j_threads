package ru.job4j;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class SimpleBlockingQueueTest2 {

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(8);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            (v) -> {
                                try {
                                    queue.offer(v);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }

    @Test
    void whenFetchAllThenTakeAll() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i <= 6; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        producer.start();
        Thread consumer = new Thread(() -> {
            while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                try {
                    buffer.add(queue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4, 5, 6);
    }

}