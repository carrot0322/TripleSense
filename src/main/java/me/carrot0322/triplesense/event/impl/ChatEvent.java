package me.carrot0322.triplesense.event.impl;

import me.carrot0322.triplesense.event.Event;

public class ChatEvent extends Event {
    private final String content;

    public ChatEvent(String content) {
        this.content = content;
    }

    public String getMessage() {
        return content;
    }
}
