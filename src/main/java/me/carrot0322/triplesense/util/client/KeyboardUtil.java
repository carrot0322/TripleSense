package me.carrot0322.triplesense.util.client;

import me.carrot0322.triplesense.features.settings.Bind;

public class KeyboardUtil {
    public static String getKeyName(int key) {
        String str = new Bind(key).toString().toUpperCase();
        str = str.replace("KEY.KEYBOARD", "").replace(".", " ");
        return str;
    }
}
