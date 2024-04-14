package me.carrot0322.triplesense.event.impl;

import me.carrot0322.triplesense.event.Event;
import me.carrot0322.triplesense.features.Feature;
import me.carrot0322.triplesense.features.settings.Setting;

public class ClientEvent extends Event {
    private Feature feature;
    private Setting<?> setting;
    private int stage;

    public ClientEvent(int stage, Feature feature) {
        this.stage = stage;
        this.feature = feature;
    }

    public ClientEvent(Setting<?> setting) {
        this.stage = 2;
        this.setting = setting;
    }

    public Feature getFeature() {
        return this.feature;
    }

    public Setting<?> getSetting() {
        return this.setting;
    }

    public int getStage() {
        return stage;
    }
}
