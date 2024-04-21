package me.carrot0322.triplesense.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.carrot0322.triplesense.TripleSense;
import me.carrot0322.triplesense.manager.CommandManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Screen.class)
public abstract class MixinScreen {
    @Inject(method = "handleTextClick", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", ordinal = 1, remap = false), cancellable = true)
    private void onRunCommand(Style style, CallbackInfoReturnable<Boolean> cir) {
        if (Objects.requireNonNull(style.getClickEvent()).getValue().startsWith(TripleSense.commandManager.getPrefix()))
            try {
                CommandManager manager = TripleSense.commandManager;
                manager.getDispatcher().execute(style.getClickEvent().getValue().substring(TripleSense.commandManager.getPrefix().length()), manager.getSource());
                cir.setReturnValue(true);
            } catch (CommandSyntaxException ignored) {
            }
    }
}