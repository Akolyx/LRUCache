package main.java;

import java.util.HashMap;
import java.util.Optional;

public class LRUCache<K, V> implements Cache<K, V> {
    private final int capacity;
    private final HashMap<K, Node<K, V>> map;
    private Node<K, V> head;
    private final Node<K, V> tail;

    public LRUCache(final int capacity) {
        assert capacity > 0;

        this.capacity = capacity;
        this.map = new HashMap<>();
        this.tail = this.head = new Node<>();
    }

    @Override
    public Optional<V> get(K key) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);

            wasUsed(node);

            return Optional.of(node.value);
        }

        return Optional.empty();
    }

    @Override
    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;

            wasUsed(node);
        } else {
            if (size() == capacity) {
                remove(tail.next.key);
            }

            int prevSize = size();

            Node<K, V> node = new Node<>(key, value);
            addFirst(node);

            assert this.head == node;

            map.put(key, node);

            assert size() == prevSize + 1;

        }
    }

    @Override
    public void remove(K key) {
        if (map.containsKey(key)) {
            int prevSize = size();

            remove(map.get(key));
            map.remove(key);

            assert prevSize == size() + 1;

        }
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        map.clear();

        this.head = new Node<>();
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> prev, next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }

        Node() {
            this(null, null);
        }
    }

    private void remove(Node<K, V> node) {
        Node<K, V> prev = node.prev, next = node.next;

        if (prev != null) {
            prev.next = next;
        }

        if (next != null) {
            next.prev = prev;
        } else {
            this.head = prev;
        }
    }

    private void wasUsed(Node<K, V> node) {
        int prevSize = size();

        remove(node);
        addFirst(node);

        assert prevSize == size();
        assert this.head == node;
    }

    private void addFirst(Node<K, V> node) {
        node.prev = this.head;
        node.next = null;

        this.head.next = node;
        this.head = node;
    }
}
