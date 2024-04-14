package me.carrot0322.triplesense.event.impl;

import me.carrot0322.triplesense.event.Event;

public class KeyEvent extends Event {
    private final int key;

    public KeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
