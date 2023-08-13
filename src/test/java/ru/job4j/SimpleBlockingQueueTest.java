package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void testQueueSize5() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Producer");
        List<Integer> rsl = new ArrayList<>();
        Thread consumer = new Thread(() -> {
            while (rsl.size() < 5) {
                try {
                    rsl.add(queue.poll());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "consumer");
        producer.start();
        consumer.start();
        try {
            consumer.join();
            producer.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertThat(rsl).hasSize(5);
    }

}