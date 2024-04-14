package me.carrot0322.triplesense.mixin;

import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.features.gui.ClickGui;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.carrot0322.triplesense.util.etc.Util.mc;

@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onKey(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        boolean whitelist = mc.currentScreen == null || mc.currentScreen instanceof ClickGui;
        if (!whitelist) return;

        if (action == 0) TripleSense.moduleManager.onKeyReleased(key);
        if (action == 1) TripleSense.moduleManager.onKeyPressed(key);
        if (action == 2) action = 1;
    }
}