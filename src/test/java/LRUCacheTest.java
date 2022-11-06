package test.java;

import main.java.LRUCache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class LRUCacheTest {
    LRUCache<Integer, Integer> lru;

    @BeforeEach
    public void init() {
        lru = new LRUCache<>(5);
    }

    @Test
    void simplePutAndGetTest() {
        lru.put(1, 30);

        assertEquals(lru.get(1), Optional.of(30));
    }

    @Test
    void capacityTest() {
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        lru.put(4, 4);
        lru.put(5, 5);
        lru.put(6, 6);

        assertEquals(lru.get(1), Optional.empty());
        assertEquals(lru.get(2), Optional.of(2));
    }

    @Test
    void updatingTest() {
        lru.put(1, 30);
        lru.put(1, 42);

        assertEquals(lru.get(1), Optional.of(42));
    }

    @Test
    void removalTest() {
        lru.put(1, 30);
        lru.put(2, 42);

        lru.remove(1);

        assertEquals(lru.get(1), Optional.empty());
        assertEquals(lru.get(2), Optional.of(42));
    }

    @Test
    void sizeTest() {
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);

        assertEquals(lru.size(), 3);
    }

    @Test
    void isEmptyTest() {
        lru.put(1, 2);
        lru.put(2, 3);
        lru.put(3, 4);

        lru.remove(1);
        lru.remove(2);
        lru.remove(3);

        assertTrue(lru.isEmpty());
    }

    @Test
    void clearTest() {
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);

        lru.clear();

        assertTrue(lru.isEmpty());
    }

    @Test
    void bigTest() {
        lru.put(1, 30);
        lru.put(2, 42);
        lru.put(3, 52);

        assertEquals(lru.size(), 3);

        lru.remove(2);

        assertEquals(lru.get(1), Optional.of(30));
        assertEquals(lru.get(2), Optional.empty());
        assertEquals(lru.size(), 2);

        lru.put(1, 15);

        assertEquals(lru.get(1), Optional.of(15));
        assertEquals(lru.get(3), Optional.of(52));
        assertEquals(lru.size(), 2);

        assertFalse(lru.isEmpty());

        lru.put(4, 4);
        lru.put(5, 4);
        lru.put(6, 4);
        lru.put(7, 4);

        assertEquals(lru.get(1), Optional.empty());
        assertEquals(lru.get(2), Optional.empty());
        assertEquals(lru.get(3), Optional.of(52));
        assertEquals(lru.get(4), Optional.of(4));
        assertEquals(lru.get(5), Optional.of(4));
        assertEquals(lru.get(6), Optional.of(4));
        assertEquals(lru.get(7), Optional.of(4));
        assertEquals(lru.size(), 5);

        lru.clear();

        assertEquals(lru.get(1), Optional.empty());
        assertEquals(lru.get(2), Optional.empty());
        assertEquals(lru.get(3), Optional.empty());
        assertEquals(lru.get(4), Optional.empty());
        assertEquals(lru.get(5), Optional.empty());
        assertEquals(lru.get(6), Optional.empty());
        assertEquals(lru.get(7), Optional.empty());
        assertEquals(lru.size(), 0);
        assertTrue(lru.isEmpty());
    }
}
