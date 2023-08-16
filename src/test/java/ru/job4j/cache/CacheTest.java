package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CacheTest {

    @Test
    void addToCache() {
        Cache cache = new Cache();
        Base b1 = new Base(1, 1);
        cache.add(b1);
        assertThat(cache.get(1)).isEqualTo(b1);
    }

    @Test
    void add3ItemThenDelete1() {
        Cache cache = new Cache();
        Base b1 = new Base(1, 1);
        Base b2 = new Base(2, 1);
        Base b3 = new Base(3, 1);
        cache.add(b1);
        cache.add(b2);
        cache.add(b3);
        cache.delete(b1);
        cache.delete(b3);
        assertThat(cache.get(2)).isEqualTo(b2);
    }

    @Test
    void add2ItemThenUpdateOne() {
        Cache cache = new Cache();
        Base b1 = new Base(1, 1);
        Base b2 = new Base(2, 1);
        cache.add(b1);
        cache.add(b2);
        Base b2v = new Base(2, 1);
        cache.update(b2v);
        assertThat(cache.get(2).getVersion()).isEqualTo(2);
    }

    @Test
    void add2ItemThenUpdateOneThenThrowException() {
        Cache cache = new Cache();
        Base b1 = new Base(1, 1);
        Base b2 = new Base(2, 1);
        cache.add(b1);
        cache.add(b2);
        Base b2v2 = new Base(2, 2);
        assertThatThrownBy(() -> cache.update(b2v2))
                .isInstanceOf(OptimisticException.class)
                .hasMessageContaining("Version are not equal.");
    }

    @Test
    void add2ItemThenUpdateTwiceThenSuccessful() {
        Cache cache = new Cache();
        Base b1 = new Base(1, 1);
        Base b2 = new Base(2, 1);
        cache.add(b1);
        cache.add(b2);
        Base b2v2 = new Base(2, 1);
        b2v2.setName("version 2");
        cache.update(b2v2);
        Base b2v3 = new Base(2, 2);
        b2v3.setName("version 3");
        cache.update(b2v3);
        assertThat(cache.get(2).getName()).isEqualTo("version 3");
        assertThat(cache.get(2).getVersion()).isEqualTo(3);
    }

}