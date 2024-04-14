package me.carrot0322.triplesense.event.impl;

import me.carrot0322.triplesense.event.Event;
import me.carrot0322.triplesense.event.Stage;

public class UpdateWalkingPlayerEvent extends Event {
    private final Stage stage;

    public UpdateWalkingPlayerEvent(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }
}
