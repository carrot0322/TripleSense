package me.carrot0322.triplesense.features.modules.movement;

import me.carrot0322.triplesense.features.modules.Module;

public class Sprint extends Module {
    public Sprint() {
        super("Sprint", "", Category.MOVEMENT, true, false, false);
    }

    @Override public void onDisable() {
        if (nullCheck()) return;

        mc.player.setSprinting(false);
    }

    public static boolean isMoving() {
        return mc.player.input.movementForward != 0.0 || mc.player.input.movementSideways != 0.0;
    }

    @Override public void onUpdate() {
        if (nullCheck()) return;

        mc.player.setSprinting(mc.player.getHungerManager().getFoodLevel() > 6 && !mc.player.horizontalCollision & !(mc.player.input.movementForward < 0.1) && isMoving());
    }
}